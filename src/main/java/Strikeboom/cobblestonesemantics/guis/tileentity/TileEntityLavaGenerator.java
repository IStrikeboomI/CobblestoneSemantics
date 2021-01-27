package Strikeboom.cobblestonesemantics.guis.tileentity;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.blocks.CobblestoneMelter;
import Strikeboom.cobblestonesemantics.guis.tileentity.customs.CobblestoneSemanticsEnergyStorage;
import Strikeboom.cobblestonesemantics.guis.tileentity.customs.fluidtanks.LavaGeneratorFluidTank;
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
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class TileEntityLavaGenerator extends TileEntity implements ITickable {
    private final FluidTank fluidTank;
    private final CobblestoneSemanticsEnergyStorage energyStorage;
    private int cooldown;
    private int delay;
    public TileEntityLavaGenerator() {
        fluidTank = new LavaGeneratorFluidTank(5000,this);
        energyStorage = new CobblestoneSemanticsEnergyStorage(1000000,false,true);
        cooldown = 0;
        delay = ConfigHandler.BLOCKS.LAVA_GENERATOR.generatorDelay;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("cooldown",cooldown);
        nbt.setInteger("delay",delay);
        fluidTank.writeToNBT(nbt);
        nbt.setInteger("energy",energyStorage.getEnergyStored());
        return super.writeToNBT(nbt);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        cooldown = nbt.getInteger("cooldown");
        delay = nbt.getInteger("delay");
        fluidTank.readFromNBT(nbt);
        energyStorage.setEnergy(nbt.getInteger("energy"));
        super.readFromNBT(nbt);
    }


    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return (T) fluidTank;
        }
        if (capability == CapabilityEnergy.ENERGY) {
            return (T) energyStorage;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentTranslation("tile." + CobblestoneSemantics.MOD_ID +".lavagenerator.name");
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
            delay = ConfigHandler.BLOCKS.LAVA_GENERATOR.generatorDelay;
            if (fluidTank.getFluid() != null
                && fluidTank.getFluid().amount >= Fluid.BUCKET_VOLUME
                && energyStorage.getEnergyStored() + ConfigHandler.BLOCKS.LAVA_GENERATOR.powerPerLavaBucket <= energyStorage.getMaxEnergyStored()) {
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
                fluidTank.getFluid().amount -= Fluid.BUCKET_VOLUME;
                energyStorage.addEnergy(ConfigHandler.BLOCKS.LAVA_GENERATOR.powerPerLavaBucket);
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
    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }
}
