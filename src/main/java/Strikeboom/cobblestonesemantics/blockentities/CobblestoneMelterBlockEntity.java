package Strikeboom.cobblestonesemantics.blockentities;

import Strikeboom.cobblestonesemantics.blockentities.energystorage.CobblestoneSemanticsEnergyStorage;
import Strikeboom.cobblestonesemantics.blockentities.fluidtanks.CobblestoneMelterFluidTank;
import Strikeboom.cobblestonesemantics.blockentities.itemhandlers.CobblestoneMelterItemHandler;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlockEntities;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
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
import net.neoforged.neoforge.transfer.transaction.Transaction;

import javax.annotation.Nullable;
import java.util.Objects;

public class CobblestoneMelterBlockEntity extends BlockEntity {
    public CobblestoneMelterItemHandler itemStackHandler;
    public CobblestoneMelterFluidTank fluidTank;
    public CobblestoneSemanticsEnergyStorage energyStorage;
    int cooldown;
    int delay;
    public CobblestoneMelterBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(CobblestoneSemanticsBlockEntities.COBBLESTONE_MELTER_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
        itemStackHandler = new CobblestoneMelterItemHandler()  {
            @Override
            protected void onContentsChanged(int index, ItemStack previousContents) {
                if (level != null) {
                    setChanged();
                    level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
                }
            }
        };
        fluidTank = new CobblestoneMelterFluidTank()  {
            @Override
            protected void onContentsChanged(int index, FluidStack previousContents) {
                if (level != null) {
                    setChanged();
                    level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
                }
            }
        };
        energyStorage = new CobblestoneSemanticsEnergyStorage(100000) {
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
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("energy",energyStorage.getAmountAsInt());
        itemStackHandler.serialize(output);
        ValueOutput fluidStacks = output.child("fluidStacks");
        fluidTank.serialize(fluidStacks);
        output.putInt("cooldown", cooldown);
        output.putInt("delay", delay);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        energyStorage.set(input.getInt("energy").orElseThrow());
        itemStackHandler.deserialize(input);
        fluidTank.deserialize(input.childOrEmpty("fluidStacks"));
        cooldown = input.getInt("cooldown").orElseThrow();
        delay = input.getInt("delay").orElseThrow();
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

            try (Transaction tx = Transaction.openRoot()) {
                int energy = energyStorage.extract(CobblestoneSemanticsConfig.COBBLESTONE_MELTER_POWER_USAGE.get(),tx);
                if (energy != 0) {
                    tx.commit();
                }
            }

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
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ValueInput valueInput) {
        super.onDataPacket(net, valueInput);
        int oldCooldown = cooldown;
        int oldDelay = delay;
        CobblestoneMelterItemHandler oldItemStackHandler = itemStackHandler;
        CobblestoneMelterFluidTank oldFluidTank = fluidTank;
        CobblestoneSemanticsEnergyStorage oldEnergyStorage = energyStorage;

        // This will call loadClientData()
        handleUpdateTag(valueInput);

        // If any of the values was changed we request a refresh of our model data and send a block update
        if (oldCooldown != cooldown || oldDelay != delay ||
                !Objects.equals(oldItemStackHandler,itemStackHandler) ||
                !Objects.equals(oldEnergyStorage,energyStorage) ||
                !Objects.equals(oldFluidTank, fluidTank)) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }
}
