package Strikeboom.cobblestonesemantics.guis.tileentity;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.blocks.CobblestoneMelter;
import Strikeboom.cobblestonesemantics.guis.tileentity.customs.CobblestoneSemanticsEnergyStorage;
import Strikeboom.cobblestonesemantics.guis.tileentity.customs.itemhandlers.AllInOneGeneratorItemHandler;
import Strikeboom.cobblestonesemantics.handlers.ConfigHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

public class TileEntityAllInOneGenerator extends TileEntity implements ITickable {
    public AllInOneGeneratorItemHandler handler;
    public CobblestoneSemanticsEnergyStorage energyStorage;
    private int cooldown;
    private int delay;
    public TileEntityAllInOneGenerator() {
        handler = new AllInOneGeneratorItemHandler(13);
        energyStorage = new CobblestoneSemanticsEnergyStorage(10000000,false,true);
        cooldown = 0;
        delay = 100;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setTag("ItemStackHandler",this.handler.serializeNBT());
        nbt.setInteger("delay",this.delay);
        nbt.setInteger("cooldown",this.cooldown);
        nbt.setInteger("energy",energyStorage.getEnergyStored());
        return super.writeToNBT(nbt);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        this.handler.deserializeNBT(nbt.getCompoundTag("ItemStackHandler"));
        this.delay = nbt.getInteger("delay");
        this.cooldown = nbt.getInteger("cooldown");
        this.energyStorage.setEnergy(nbt.getInteger("energy"));
        super.readFromNBT(nbt);
    }

    @Override
    public boolean hasCapability(Capability<?> capability,  EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }


    @Override
    public <T> T getCapability(Capability<T> capability,  EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return (T) energyStorage;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentTranslation("tile." + CobblestoneSemantics.MOD_ID + ".allinonegenerator.name");
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
            if (!handler.getStackInSlot(0).isEmpty() &&
                handler.getLavaGenerators() > 0 &&
                handler.getLavaMelters() > 0 &&
                energyStorage.getEnergyStored() + (int)(Math.floor(handler.getLavaGenerators() / 2f) < handler.getLavaMelters() ? Math.floor(handler.getLavaGenerators() / 2f) :handler.getLavaMelters()) <= energyStorage.getMaxEnergyStored()) {

                cooldown += handler.getCobbleGenTier();
                if (cooldown > delay) {
                    cooldown = delay;
                }
                markDirty();
                this.world.notifyBlockUpdate(pos,world.getBlockState(pos),world.getBlockState(pos),3);
            } else {
                if (cooldown != 0) {
                    cooldown = 0;
                    markDirty();
                    this.world.notifyBlockUpdate(pos,world.getBlockState(pos),world.getBlockState(pos),3);
                }
            }
            if (cooldown % delay == 0 && cooldown != 0) {
                cooldown = 0;
                energyStorage.addEnergy(ConfigHandler.BLOCKS.LAVA_GENERATOR.powerPerLavaBucket * (int)(Math.floor(handler.getLavaGenerators() / 2f) < handler.getLavaMelters() ? Math.floor(handler.getLavaGenerators() / 2f) :handler.getLavaMelters()));
            }
        }
    }

    public int getDelay() {
        return delay;
    }
    public int getCoolDown() {
        return cooldown;
    }
}
