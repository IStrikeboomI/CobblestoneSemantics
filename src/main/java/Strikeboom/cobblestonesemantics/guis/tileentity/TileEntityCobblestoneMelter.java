package Strikeboom.cobblestonesemantics.guis.tileentity;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.blocks.CobblestoneMelter;
import Strikeboom.cobblestonesemantics.guis.tileentity.customs.fluidtanks.CobblestoneMelterFluidTank;
import Strikeboom.cobblestonesemantics.guis.tileentity.customs.itemhandlers.CobblestoneMelterItemHandler;
import Strikeboom.cobblestonesemantics.handlers.ConfigHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityCobblestoneMelter extends TileEntity implements ITickable {
    private final ItemStackHandler handler;
    private final FluidTank fluidTank;
    private int cooldown;
    private int delay;
    public TileEntityCobblestoneMelter() {
        handler = new CobblestoneMelterItemHandler(1);
        fluidTank = new CobblestoneMelterFluidTank(Fluid.BUCKET_VOLUME * 10,this);
        cooldown = 0;
        delay = ConfigHandler.BLOCKS.MELTER.melterDelay;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        delay = nbt.getInteger("delay");
        cooldown = nbt.getInteger("cooldown");
        fluidTank.readFromNBT(nbt);
        this.handler.deserializeNBT(nbt.getCompoundTag("ItemStackHandler"));
        super.readFromNBT(nbt);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("cooldown",cooldown);
        nbt.setInteger("delay",delay);
        nbt.setTag("ItemStackHandler",this.handler.serializeNBT());
        fluidTank.writeToNBT(nbt);


        return super.writeToNBT(nbt);
    }


    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentTranslation("tile." + CobblestoneSemantics.MOD_ID +".cobblestonemelter.name");
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T) handler;
        }
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return (T) fluidTank;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
    }
    @Override
    public void update() {
        if (!world.isRemote) {
            delay = ConfigHandler.BLOCKS.MELTER.melterDelay;
            if (
                    (fluidTank.getFluid() == null || fluidTank.getFluid().amount + ConfigHandler.BLOCKS.MELTER.lavaPerCobblestone <= fluidTank.getCapacity())
                && !handler.getStackInSlot(0).isEmpty()) {
                cooldown++;
                this.world.setBlockState(pos,world.getBlockState(pos).withProperty(CobblestoneMelter.RUNNING,true));
                markDirty();
                this.world.notifyBlockUpdate(pos,world.getBlockState(pos),world.getBlockState(pos),3);
            } else {
                if (cooldown != 0) {
                    cooldown = 0;
                    markDirty();
                    this.world.notifyBlockUpdate(pos,world.getBlockState(pos),world.getBlockState(pos),3);
                }
                this.world.setBlockState(pos,world.getBlockState(pos).withProperty(CobblestoneMelter.RUNNING,false));
            }
            if (cooldown % delay == 0 && cooldown != 0) {
                cooldown = 0;
                if (fluidTank.getFluid() != null) {
                    fluidTank.getFluid().amount += ConfigHandler.BLOCKS.MELTER.lavaPerCobblestone;
                } else {
                    fluidTank.setFluid(new FluidStack(FluidRegistry.LAVA,500));
                }
                handler.getStackInSlot(0).shrink(1);
                markDirty();
                this.world.notifyBlockUpdate(pos,world.getBlockState(pos),world.getBlockState(pos),3);
            }
        }
    }
    public int getCoolDown() {
        return cooldown;
    }

    public int getDelay() {
        return delay;
    }

    //this method makes it so that the tile entity doesn't get deleted after you change blockstate
    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }
}
