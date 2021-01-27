package Strikeboom.cobblestonesemantics.items.wrappers;

import net.minecraft.item.Item;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class ItemItemStackHandlerCapabilityWrapper implements ICapabilitySerializable {
    ItemStackHandler handler;
    public ItemItemStackHandlerCapabilityWrapper(int size, Item item) {
        handler = new ItemStackHandlerBag(size,item);
    }
    @Override
    public boolean hasCapability( Capability<?> capability,  EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }


    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T)handler;
        }
        return null;
    }


    @Override
    public NBTBase serializeNBT() {
        return handler.serializeNBT();
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        handler.deserializeNBT((NBTTagCompound) nbt);
    }
}
