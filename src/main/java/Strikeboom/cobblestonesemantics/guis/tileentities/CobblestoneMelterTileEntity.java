package Strikeboom.cobblestonesemantics.guis.tileentities;

import Strikeboom.cobblestonesemantics.guis.tileentities.fluidtanks.CobblestoneMelterFluidTank;
import Strikeboom.cobblestonesemantics.guis.tileentities.itemhandlers.CobblestoneMelterItemHandler;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsConfig;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.Objects;

public class CobblestoneMelterTileEntity extends TileEntity implements ITickableTileEntity {
    ItemStackHandler itemStackHandler;
    private final LazyOptional<IItemHandler> itemHandlerLazyOptional;
    FluidTank fluidTank;
    private final LazyOptional<IFluidHandler> fluidHandlerLazyOptional;
    int cooldown;
    int delay;
    public CobblestoneMelterTileEntity() {
        super(CobblestoneSemanticsTileEntities.COBBLESTONE_MELTER_BLOCK_ENTITY.get());
        itemStackHandler = new CobblestoneMelterItemHandler(1)  {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                level.sendBlockUpdated(worldPosition,getBlockState(),getBlockState(), 3);
            }
        };
        fluidTank = new CobblestoneMelterFluidTank(FluidAttributes.BUCKET_VOLUME * 10)  {
            @Override
            protected void onContentsChanged() {
                setChanged();
                level.sendBlockUpdated(worldPosition,getBlockState(),getBlockState(), 3);
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
    public CompoundNBT save(CompoundNBT pTag) {
        pTag.put("ItemStackHandler",itemStackHandler.serializeNBT());
        fluidTank.writeToNBT(pTag);
        CompoundNBT infoTag = new CompoundNBT();
        infoTag.putInt("Cooldown", cooldown);
        infoTag.putInt("DelayUntilNextCobbleStone", delay);
        pTag.put("Info", infoTag);
        return super.save(pTag);
    }

    @Override
    public void load(BlockState state, CompoundNBT pTag) {
        if (pTag.contains("ItemStackHandler")) {
            itemStackHandler.deserializeNBT(pTag.getCompound("ItemStackHandler"));
        }
        fluidTank.readFromNBT(pTag);
        if (pTag.contains("Info")) {
            cooldown = pTag.getCompound("Info").getInt("Cooldown");
            delay = pTag.getCompound("Info").getInt("DelayUntilNextCobbleStone");
        }
        super.load(state,pTag);
    }

    public void tick() {
        if (!level.isClientSide) {
            delay = CobblestoneSemanticsConfig.COBBLESTONE_MELTER_DELAY.get();
            boolean shouldUpdate = false;
            if ((fluidTank.getFluid().getAmount() + CobblestoneSemanticsConfig.COBBLESTONE_MELTER_LAVA_PER_COBBLESTONE.get() <= fluidTank.getCapacity())
                    && !itemStackHandler.getStackInSlot(0).isEmpty()) {
                cooldown++;
                level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(BlockStateProperties.POWERED, true));
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
                    fluidTank.setFluid(new FluidStack(Fluids.LAVA, CobblestoneSemanticsConfig.COBBLESTONE_MELTER_LAVA_PER_COBBLESTONE.get()));
                }
                itemStackHandler.getStackInSlot(0).shrink(1);
                if (itemStackHandler.getStackInSlot(0).isEmpty() || fluidTank.getFluid().getAmount() >= fluidTank.getCapacity()) {
                    level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(BlockStateProperties.POWERED, false));
                }
            }
            if (shouldUpdate) {
                setChanged();
                this.level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            }
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
    public CompoundNBT getUpdateTag() {
        CompoundNBT tag = super.getUpdateTag();
        save(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(BlockState state,CompoundNBT tag) {
        if (tag != null) {
            load(state,tag);
        }
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(worldPosition,3,getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        // This is called client side: remember the current state of the values that we're interested in
        int oldCooldown = cooldown;
        int oldDelay = delay;
        ItemStackHandler oldItemStackHandler = itemStackHandler;
        FluidTank oldFluidTank = fluidTank;

        CompoundNBT tag = pkt.getTag();
        // This will call loadClientData()
        handleUpdateTag(null,tag);

        // If any of the values was changed we request a refresh of our model data and send a block update
        if (oldCooldown != cooldown || oldDelay != delay ||
                !Objects.equals(oldItemStackHandler,itemStackHandler) ||
                !Objects.equals(oldFluidTank, fluidTank)) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }
}
