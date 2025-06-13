package Strikeboom.cobblestonesemantics.blockentities;

import Strikeboom.cobblestonesemantics.blockentities.energystorage.CobblestoneSemanticsEnergyStorage;
import Strikeboom.cobblestonesemantics.blockentities.fluidtanks.CobblestoneMelterFluidTank;
import Strikeboom.cobblestonesemantics.blockentities.itemhandlers.CobblestoneMelterItemHandler;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlockEntities;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;


import javax.annotation.Nullable;
import java.util.Objects;

public class CobblestoneMelterBlockEntity extends BlockEntity {
    public ItemStackHandler itemStackHandler;
    public FluidTank fluidTank;
    public CobblestoneSemanticsEnergyStorage energyStorage;
    int cooldown;
    int delay;
    public CobblestoneMelterBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(CobblestoneSemanticsBlockEntities.COBBLESTONE_MELTER_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
        itemStackHandler = new CobblestoneMelterItemHandler(1)  {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                level.sendBlockUpdated(worldPosition,getBlockState(),getBlockState(), Block.UPDATE_ALL);
            }
        };
        fluidTank = new CobblestoneMelterFluidTank(10000)  {
            @Override
            protected void onContentsChanged() {
                setChanged();
                level.sendBlockUpdated(worldPosition,getBlockState(),getBlockState(), Block.UPDATE_ALL);
            }
        };
        energyStorage = new CobblestoneSemanticsEnergyStorage(100000,true,false) {
            @Override
            protected void onEnergyChanged() {
                if (level != null) {
                    setChanged();
                    level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
                }
            }
        };
        cooldown = 0;
        delay = CobblestoneSemanticsConfig.COBBLESTONE_MELTER_DELAY.get();
    }
    @Override
    public void setRemoved() {
        super.setRemoved();
        level.invalidateCapabilities(getBlockPos());
        invalidateCapabilities();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag,HolderLookup.Provider registries) {
        pTag.putInt("energy",energyStorage.getEnergyStored());
        pTag.put("ItemStackHandler",itemStackHandler.serializeNBT(registries));
        fluidTank.writeToNBT(registries,pTag);
        CompoundTag infoTag = new CompoundTag();
        infoTag.putInt("Cooldown", cooldown);
        infoTag.putInt("DelayUntilNextCobbleStone", delay);
        pTag.put("Info", infoTag);
    }

    @Override
    public void loadAdditional(CompoundTag pTag,HolderLookup.Provider registries) {
        if (pTag.contains("energy")) {
            energyStorage.setEnergy(pTag.getInt("energy").orElseThrow());
        }
        if (pTag.contains("ItemStackHandler")) {
            itemStackHandler.deserializeNBT(registries, pTag.getCompound("ItemStackHandler").orElseThrow());
        }
        fluidTank.readFromNBT(registries, pTag);
        if (pTag.contains("Info")) {
            cooldown = pTag.getCompound("Info").orElseThrow().getInt("Cooldown").orElseThrow();
            delay = pTag.getCompound("Info").orElseThrow().getInt("DelayUntilNextCobbleStone").orElseThrow();
        }
        super.loadAdditional(pTag,registries);
    }

    public void tickServer() {
        delay = CobblestoneSemanticsConfig.COBBLESTONE_MELTER_DELAY.get();
        boolean shouldUpdate = false;
        if ((fluidTank.getFluid().getAmount() + CobblestoneSemanticsConfig.COBBLESTONE_MELTER_LAVA_PER_COBBLESTONE.get() <= fluidTank.getCapacity())
                        && !itemStackHandler.getStackInSlot(0).isEmpty()
                        && energyStorage.getEnergyStored() >= CobblestoneSemanticsConfig.COBBLESTONE_MELTER_POWER_USAGE.get()) {
            cooldown++;
            level.setBlockAndUpdate(getBlockPos(),getBlockState().setValue(BlockStateProperties.POWERED,true));
            shouldUpdate = true;
        } else {
            if (cooldown != 0) {
                cooldown = 0;
                if (itemStackHandler.getStackInSlot(0).isEmpty()) {
                    level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(BlockStateProperties.POWERED, false));
                }
                shouldUpdate = true;
            }
        }
        if (cooldown % delay == 0 && cooldown != 0) {
            cooldown = 0;
            if (!fluidTank.getFluid().isEmpty()) {
                fluidTank.getFluid().grow(CobblestoneSemanticsConfig.COBBLESTONE_MELTER_LAVA_PER_COBBLESTONE.get());
            } else {
                fluidTank.setFluid(new FluidStack(Fluids.LAVA,CobblestoneSemanticsConfig.COBBLESTONE_MELTER_LAVA_PER_COBBLESTONE.get()));
            }
            itemStackHandler.getStackInSlot(0).shrink(1);
            energyStorage.addEnergy(-CobblestoneSemanticsConfig.COBBLESTONE_MELTER_POWER_USAGE.get());
            if (itemStackHandler.getStackInSlot(0).isEmpty()) {
                level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(BlockStateProperties.POWERED, false));
            }
        }
        if (shouldUpdate) {
            setChanged();
            this.level.sendBlockUpdated(worldPosition,getBlockState(),getBlockState(),Block.UPDATE_ALL);
        }
    }


    public int getCooldown() {
        return cooldown;
    }

    public int getDelay() {
        return delay;
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        saveAdditional(tag,registries);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag,HolderLookup.Provider registries) {
        super.handleUpdateTag(tag, registries);
        loadAdditional(tag, registries);
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
        // This is called client side: remember the current state of the values that we're interested in
        int oldCooldown = cooldown;
        int oldDelay = delay;
        ItemStackHandler oldItemStackHandler = itemStackHandler;
        FluidTank oldFluidTank = fluidTank;
        CobblestoneSemanticsEnergyStorage oldEnergyStorage = energyStorage;

        CompoundTag tag = pkt.getTag();
        // This will call loadClientData()
        handleUpdateTag(tag,lookupProvider);

        // If any of the values was changed we request a refresh of our model data and send a block update
        if (oldCooldown != cooldown || oldDelay != delay ||
                !Objects.equals(oldItemStackHandler,itemStackHandler) ||
                !Objects.equals(oldEnergyStorage,energyStorage) ||
                !Objects.equals(oldFluidTank, fluidTank)) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }
}
