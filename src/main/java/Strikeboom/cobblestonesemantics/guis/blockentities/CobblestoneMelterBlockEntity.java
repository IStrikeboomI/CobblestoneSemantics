package Strikeboom.cobblestonesemantics.guis.blockentities;

import Strikeboom.cobblestonesemantics.guis.blockentities.fluidtanks.CobblestoneMelterFluidTank;
import Strikeboom.cobblestonesemantics.guis.blockentities.itemhandlers.CobblestoneMelterItemHandler;
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
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.Objects;

public class CobblestoneMelterBlockEntity extends BlockEntity {
    ItemStackHandler itemStackHandler;
    private final LazyOptional<IItemHandler> itemHandlerLazyOptional;
    FluidTank fluidTank;
    private final LazyOptional<IFluidHandler> fluidHandlerLazyOptional;
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
        itemHandlerLazyOptional = LazyOptional.of(() -> itemStackHandler);
        fluidHandlerLazyOptional = LazyOptional.of(() -> fluidTank);
        cooldown = 0;
        delay = CobblestoneSemanticsConfig.COBBLESTONE_MELTER_DELAY.get();
    }
    @Override
    public void setRemoved() {
        super.setRemoved();
        itemHandlerLazyOptional.invalidate();
        fluidHandlerLazyOptional.invalidate();
    }
    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("ItemStackHandler",itemStackHandler.serializeNBT());
        fluidTank.writeToNBT(pTag);
        CompoundTag infoTag = new CompoundTag();
        infoTag.putInt("Cooldown", cooldown);
        infoTag.putInt("DelayUntilNextCobbleStone", delay);
        pTag.put("Info", infoTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        if (pTag.contains("ItemStackHandler")) {
            itemStackHandler.deserializeNBT(pTag.getCompound("ItemStackHandler"));
        }
        fluidTank.readFromNBT(pTag);
        if (pTag.contains("Info")) {
            cooldown = pTag.getCompound("Info").getInt("Cooldown");
            delay = pTag.getCompound("Info").getInt("DelayUntilNextCobbleStone");
        }
        super.load(pTag);
    }

    public void tickServer() {
        delay = CobblestoneSemanticsConfig.COBBLESTONE_MELTER_DELAY.get();
        boolean shouldUpdate = false;
        if ((fluidTank.getFluid().getAmount() + CobblestoneSemanticsConfig.COBBLESTONE_MELTER_LAVA_PER_COBBLESTONE.get() <= fluidTank.getCapacity())
                        && !itemStackHandler.getStackInSlot(0).isEmpty()) {
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
            if (itemStackHandler.getStackInSlot(0).isEmpty()) {
                level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(BlockStateProperties.POWERED, false));
            }
        }
        if (shouldUpdate) {
            setChanged();
            this.level.sendBlockUpdated(worldPosition,getBlockState(),getBlockState(),Block.UPDATE_ALL);
        }
    }
    
    @Override
    public <T> LazyOptional<T> getCapability( Capability<T> cap, final @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemHandlerLazyOptional.cast();
        }
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return fluidHandlerLazyOptional.cast();
        }
        return super.getCapability(cap,side);
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getDelay() {
        return delay;
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
        ItemStackHandler oldItemStackHandler = itemStackHandler;
        FluidTank oldFluidTank = fluidTank;

        CompoundTag tag = pkt.getTag();
        // This will call loadClientData()
        handleUpdateTag(tag);

        // If any of the values was changed we request a refresh of our model data and send a block update
        if (oldCooldown != cooldown || oldDelay != delay ||
                !Objects.equals(oldItemStackHandler,itemStackHandler) ||
                !Objects.equals(oldFluidTank, fluidTank)) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }
}
