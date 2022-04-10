package Strikeboom.cobblestonesemantics.guis.tileentities.itemhandlers;

import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class ItemItemStackHandlerCapabilityWrapper implements ICapabilitySerializable<CompoundNBT> {
    ItemStackHandler handler;
    private final LazyOptional<IItemHandler> itemHandlerLazyOptional;
    public ItemItemStackHandlerCapabilityWrapper(int size, Item item) {
        handler = new BagItemHandler(size,item);
        itemHandlerLazyOptional = LazyOptional.of(() -> handler);
    }


    @Override
    public <T> LazyOptional<T> getCapability( Capability<T> cap, Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemHandlerLazyOptional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.put("ItemStackHandler",handler.serializeNBT());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (nbt.contains("ItemStackHandler")) {
            handler.deserializeNBT(nbt.getCompound("ItemStackHandler"));
        }
    }
}
