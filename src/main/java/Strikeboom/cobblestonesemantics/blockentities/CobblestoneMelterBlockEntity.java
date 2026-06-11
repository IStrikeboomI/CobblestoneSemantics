package Strikeboom.cobblestonesemantics.blockentities;

import Strikeboom.cobblestonesemantics.blockentities.energystorage.CobblestoneSemanticsEnergyStorage;
import Strikeboom.cobblestonesemantics.blockentities.fluidtanks.CobblestoneMelterFluidTank;
import Strikeboom.cobblestonesemantics.blockentities.itemhandlers.CobblestoneMelterItemHandler;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlockEntities;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.fluid.FluidStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;

import javax.annotation.Nullable;

public class CobblestoneMelterBlockEntity extends BlockEntity {
    public ItemStacksResourceHandler itemStackHandler;
    public FluidStacksResourceHandler fluidTank;
    public CobblestoneSemanticsEnergyStorage energyStorage;
    int cooldown;
    int delay;
    public CobblestoneMelterBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(CobblestoneSemanticsBlockEntities.COBBLESTONE_MELTER_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
        itemStackHandler = new CobblestoneMelterItemHandler(1)  {
            @Override
            protected void onContentsChanged(int index, ItemStack previousContents) {
                setChanged();
                level.sendBlockUpdated(worldPosition,getBlockState(),getBlockState(), Block.UPDATE_ALL);
            }
        };
        fluidTank = new CobblestoneMelterFluidTank(10000)  {
            @Override
            protected void onContentsChanged(int index, FluidStack previousContents) {
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
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("energy",energyStorage.getAmountAsInt());
        itemStackHandler.serialize(output);
        fluidTank.serialize(output);
        output.putInt("cooldown", cooldown);
        output.putInt("delayUntilNextCobbleStone", delay);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        energyStorage.set(input.getInt("energy").orElseThrow());
        itemStackHandler.deserialize(input);
        fluidTank.deserialize(input);
        cooldown = input.getInt("cooldown").orElseThrow();
        delay = input.getInt("delayUntilNextCobbleStone").orElseThrow();
    }

    public void tickServer() {
        delay = CobblestoneSemanticsConfig.COBBLESTONE_MELTER_DELAY.get();
        boolean shouldUpdate = false;
        if ((fluidTank.getAmountAsInt(0) + CobblestoneSemanticsConfig.COBBLESTONE_MELTER_LAVA_PER_COBBLESTONE.get() <= fluidTank.getCapacityAsInt(0,fluidTank.getResource(0)))
                        && !itemStackHandler.getResource(0).isEmpty()
                        && energyStorage.getAmountAsInt() >= CobblestoneSemanticsConfig.COBBLESTONE_MELTER_POWER_USAGE.get()) {
            cooldown++;
            level.setBlockAndUpdate(getBlockPos(),getBlockState().setValue(BlockStateProperties.POWERED,true));
            shouldUpdate = true;
        } else {
            if (cooldown != 0) {
                cooldown = 0;
                if (itemStackHandler.getResource(0).isEmpty()) {
                    level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(BlockStateProperties.POWERED, false));
                }
                shouldUpdate = true;
            }
        }
        if (cooldown % delay == 0 && cooldown != 0) {
            cooldown = 0;
            if (!fluidTank.getResource(0).isEmpty()) {
                fluidTank.set(0, fluidTank.getResource(0),fluidTank.getAmountAsInt(0) + CobblestoneSemanticsConfig.COBBLESTONE_MELTER_LAVA_PER_COBBLESTONE.get());
            } else {
                fluidTank.set(0,FluidResource.of(Fluids.LAVA),CobblestoneSemanticsConfig.COBBLESTONE_MELTER_LAVA_PER_COBBLESTONE.get());
            }
            itemStackHandler.set(0,itemStackHandler.getResource(0),itemStackHandler.getAmountAsInt(0) - 1);
            energyStorage.addEnergy(-CobblestoneSemanticsConfig.COBBLESTONE_MELTER_POWER_USAGE.get(),null);
            if (itemStackHandler.getResource(0).isEmpty()) {
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
        return this.saveWithoutMetadata(registries);
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

}
