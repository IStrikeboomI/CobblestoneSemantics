package Strikeboom.cobblestonesemantics.guis.tileentity;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.guis.tileentity.customs.itemhandlers.CobblestoneGeneratorItemHandler;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityCobbleGenerator extends TileEntity implements ITickable {
    ItemStackHandler handler;
    int tier = 1;
    int delayUntilNextCobbleStone = 1;
    int cooldown = 0;

    public TileEntityCobbleGenerator(int tier,int storageSlots,int delayUntilNextCobbleStone) {
        handler = new CobblestoneGeneratorItemHandler(storageSlots);
        this.tier = tier;
        this.delayUntilNextCobbleStone = delayUntilNextCobbleStone;

    }

    public TileEntityCobbleGenerator() {handler = new CobblestoneGeneratorItemHandler(1);}

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        cooldown = nbt.getInteger("cooldown");
        tier = nbt.getInteger("tier");
        delayUntilNextCobbleStone = nbt.getInteger("delayUntilNextCobbleStone");
        handler.deserializeNBT(nbt.getCompoundTag("ItemStackHandler"));
        super.readFromNBT(nbt);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setTag("ItemStackHandler",this.handler.serializeNBT());
        nbt.setInteger("delayUntilNextCobbleStone",delayUntilNextCobbleStone);
        nbt.setInteger("tier",tier);
        nbt.setInteger("cooldown",cooldown);

        return super.writeToNBT(nbt);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T) handler;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentTranslation("tile." + CobblestoneSemantics.MOD_ID +".cobblegenerator" + tier + ".name");
    }


    @Override
    public void update() {
        if (!world.isRemote) {
            cooldown++;
            if (cooldown % delayUntilNextCobbleStone == 0) {
                for (int i = 0; i<handler.getSlots();i++) {
                    if (!handler.getStackInSlot(i).isEmpty()) {
                        if (handler.getStackInSlot(i).getCount() < handler.getSlotLimit(i)) {
                            handler.getStackInSlot(i).grow(1);
                            markDirty();
                            break;
                        }
                    } else {
                        handler.setStackInSlot(i,new ItemStack(Blocks.COBBLESTONE));
                        markDirty();
                        break;
                    }
                }
            }

            //inputs cobble to the slot above or below
            TileEntity upTE = world.getTileEntity(this.pos.up());
            if (upTE != null) {
                if (upTE.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,EnumFacing.DOWN)) {
                    IItemHandler upHandler = upTE.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,EnumFacing.DOWN);
                    if (!handler.getStackInSlot(0).isEmpty()) {
                        for (int i = 0;i < upHandler.getSlots();i++) {
                            if (upHandler.getStackInSlot(i).getCount() < upHandler.getSlotLimit(i)) {
                                upHandler.insertItem(i,handler.extractItem(((CobblestoneGeneratorItemHandler)handler).getLargestSlotIndex(),64,false),false);
                            }
                        }
                    }
                }
            }
            TileEntity downTE = world.getTileEntity(this.pos.down());
            if (downTE != null) {
                if (downTE.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,EnumFacing.UP)) {
                    IItemHandler downHandler = downTE.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,EnumFacing.UP);
                    if (!handler.getStackInSlot(0).isEmpty()) {
                        for (int i = 0;i < downHandler.getSlots();i++) {
                            if (downHandler.getStackInSlot(i).getCount() < downHandler.getSlotLimit(i)) {
                                downHandler.insertItem(i,handler.extractItem(((CobblestoneGeneratorItemHandler)handler).getLargestSlotIndex(),64,false),false);
                            }
                        }
                    }
                }
            }
        }
    }
    public int getCobblestoneAmount() {
        int amount = 0;
        for (int i = 0;i < handler.getSlots();i++) {
            amount += handler.getStackInSlot(i).getCount();
        }
        return amount;
    }

}
