package Strikeboom.cobblestonesemantics.guis.blockentities;

import Strikeboom.cobblestonesemantics.guis.blockentities.energystorage.CobblestoneSemanticsEnergyStorage;
import Strikeboom.cobblestonesemantics.guis.blockentities.fluidtanks.LavaGeneratorFluidTank;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlockEntities;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;

public class LavaGeneratorBlockEntity extends BlockEntity {
    private final FluidTank fluidTank;
    private final LazyOptional<IFluidHandler> fluidHandlerLazyOptional;
    private final CobblestoneSemanticsEnergyStorage energyStorage;
    private final LazyOptional<IEnergyStorage> energyLazyOptional;
    private int cooldown;
    private int delay;
    public LavaGeneratorBlockEntity( BlockPos pWorldPosition, BlockState pBlockState) {
        super(CobblestoneSemanticsBlockEntities.LAVA_GENERATOR_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
        fluidTank = new LavaGeneratorFluidTank(FluidAttributes.BUCKET_VOLUME * 5);
        fluidHandlerLazyOptional = LazyOptional.of(() -> fluidTank);
        energyStorage = new CobblestoneSemanticsEnergyStorage(1000000,false,true);
        energyLazyOptional = LazyOptional.of(() -> energyStorage);
        cooldown = 0;
        delay = CobblestoneSemanticsConfig.LAVA_GENERATOR_DELAY.get();
    }
    @Override
    public void setRemoved() {
        super.setRemoved();
        energyLazyOptional.invalidate();
        fluidHandlerLazyOptional.invalidate();
    }
    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.putInt("energy",energyStorage.getEnergyStored());
        fluidTank.writeToNBT(pTag);
        CompoundTag infoTag = new CompoundTag();
        infoTag.putInt("Cooldown", cooldown);
        infoTag.putInt("Delay", delay);
        pTag.put("Info", infoTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        if (pTag.contains("energy")) {
            energyStorage.setEnergy(pTag.getInt("energy"));
        }
        fluidTank.readFromNBT(pTag);
        if (pTag.contains("Info")) {
            cooldown = pTag.getCompound("Info").getInt("Cooldown");
            delay = pTag.getCompound("Info").getInt("Delay");
        }
        super.load(pTag);
    }
    public void tickServer() {
        delay = CobblestoneSemanticsConfig.COBBLESTONE_MELTER_DELAY.get();
        boolean shouldUpdate = false;
        if (!fluidTank.isEmpty()
                && fluidTank.getFluid().getAmount() >= FluidAttributes.BUCKET_VOLUME
                && energyStorage.getEnergyStored() + CobblestoneSemanticsConfig.LAVA_GENERATOR_POWER_PER_LAVA_BUCKET.get() <= energyStorage.getMaxEnergyStored()) {
            cooldown++;
            level.setBlockAndUpdate(getBlockPos(),getBlockState().setValue(BlockStateProperties.POWERED,true));
            shouldUpdate = true;
        } else {
            if (cooldown != 0) {
                cooldown = 0;
                level.setBlockAndUpdate(getBlockPos(),getBlockState().setValue(BlockStateProperties.POWERED,false));
                shouldUpdate = true;
            }
        }
        if (cooldown % delay == 0 && cooldown != 0) {
            cooldown = 0;
            fluidTank.getFluid().shrink(FluidAttributes.BUCKET_VOLUME);
            energyStorage.addEnergy(CobblestoneSemanticsConfig.LAVA_GENERATOR_POWER_PER_LAVA_BUCKET.get());
        }
        if (shouldUpdate) {
            setChanged();
            this.level.sendBlockUpdated(worldPosition,getBlockState(),getBlockState(),Block.UPDATE_ALL);
        }
    }
    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, final @Nullable Direction side) {
        if (cap == CapabilityEnergy.ENERGY) {
            return energyLazyOptional.cast();
        }
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return fluidHandlerLazyOptional.cast();
        }
        return super.getCapability(cap,side);
    }
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        if (tag != null) {
            load(tag);
        }
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        // This is called client side: remember the current state of the values that we're interested in
        int oldCooldown = cooldown;
        int oldDelay = delay;
        CobblestoneSemanticsEnergyStorage oldEnergyStorage = energyStorage;
        FluidTank oldFluidTank = fluidTank;

        CompoundTag tag = pkt.getTag();
        // This will call loadClientData()
        handleUpdateTag(tag);

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
