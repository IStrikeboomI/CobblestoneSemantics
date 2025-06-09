package Strikeboom.cobblestonesemantics.blockentities;

import Strikeboom.cobblestonesemantics.blockentities.energystorage.CobblestoneSemanticsEnergyStorage;
import Strikeboom.cobblestonesemantics.blockentities.fluidtanks.LavaGeneratorFluidTank;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlockEntities;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nullable;
import java.util.Objects;

public class LavaGeneratorBlockEntity extends BlockEntity {
    public final FluidTank fluidTank;
    public final CobblestoneSemanticsEnergyStorage energyStorage;
    private int cooldown;
    private int delay;
    public LavaGeneratorBlockEntity( BlockPos pWorldPosition, BlockState pBlockState) {
        super(CobblestoneSemanticsBlockEntities.LAVA_GENERATOR_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
        fluidTank = new LavaGeneratorFluidTank(5000) {
            @Override
            protected void onContentsChanged() {
                setChanged();
                level.sendBlockUpdated(worldPosition,getBlockState(),getBlockState(), Block.UPDATE_ALL);
            }
        };
        energyStorage = new CobblestoneSemanticsEnergyStorage(1000000,false,true) {
            @Override
            protected void onEnergyChanged() {
                if (level != null) {
                    setChanged();
                    level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
                }            }
        };
        cooldown = 0;
        delay = CobblestoneSemanticsConfig.LAVA_GENERATOR_DELAY.get();
    }
    @Override
    public void setRemoved() {
        super.setRemoved();
        level.invalidateCapabilities(getBlockPos());
        invalidateCapabilities();
    }
    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        pTag.putInt("energy",energyStorage.getEnergyStored());
        fluidTank.writeToNBT(registries, pTag);
        CompoundTag infoTag = new CompoundTag();
        infoTag.putInt("Cooldown", cooldown);
        infoTag.putInt("Delay", delay);
        pTag.put("Info", infoTag);
    }

    @Override
    public void loadAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        if (pTag.contains("energy")) {
            energyStorage.setEnergy(pTag.getInt("energy").orElseThrow());
        }
        fluidTank.readFromNBT(registries, pTag);
        if (pTag.contains("Info")) {
            cooldown = pTag.getCompound("Info").orElseThrow().getInt("Cooldown").orElseThrow();
            delay = pTag.getCompound("Info").orElseThrow().getInt("Delay").orElseThrow();
        }
        super.loadAdditional(pTag,registries);
    }
    public void tickServer() {
        delay = CobblestoneSemanticsConfig.COBBLESTONE_MELTER_DELAY.get();
        boolean shouldUpdate = false;
        if (!fluidTank.isEmpty()
                && fluidTank.getFluid().getAmount() >= 1000
                && energyStorage.getEnergyStored() + CobblestoneSemanticsConfig.LAVA_GENERATOR_POWER_PER_LAVA_BUCKET.get() <= energyStorage.getMaxEnergyStored()) {
            cooldown++;
            level.setBlockAndUpdate(getBlockPos(),getBlockState().setValue(BlockStateProperties.POWERED,true));
            shouldUpdate = true;
        } else {
            if (cooldown != 0) {
                cooldown = 0;
                if (fluidTank.getFluid().isEmpty()) {
                    level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(BlockStateProperties.POWERED, false));
                }
                shouldUpdate = true;
            }
        }
        if (cooldown % delay == 0 && cooldown != 0) {
            cooldown = 0;
            fluidTank.getFluid().shrink(1000);
            if (fluidTank.getFluid().isEmpty()) {
                level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(BlockStateProperties.POWERED, false));
            }
            energyStorage.addEnergy(CobblestoneSemanticsConfig.LAVA_GENERATOR_POWER_PER_LAVA_BUCKET.get());
        }
        if (shouldUpdate) {
            setChanged();
            this.level.sendBlockUpdated(worldPosition,getBlockState(),getBlockState(),Block.UPDATE_ALL);
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        saveAdditional(tag,registries);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider registries) {
        loadAdditional(tag,registries);
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider registries) {
        // This is called client side: remember the current state of the values that we're interested in
        int oldCooldown = cooldown;
        int oldDelay = delay;
        CobblestoneSemanticsEnergyStorage oldEnergyStorage = energyStorage;
        FluidTank oldFluidTank = fluidTank;

        CompoundTag tag = pkt.getTag();
        // This will call loadClientData()
        handleUpdateTag(tag,registries);

        // If any of the values was changed we request a refresh of our model data and send a block update
        if (oldCooldown != cooldown || oldDelay != delay ||
                !Objects.equals(oldEnergyStorage,energyStorage) ||
                !Objects.equals(oldFluidTank, fluidTank)) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getDelay() {
        return delay;
    }
}
