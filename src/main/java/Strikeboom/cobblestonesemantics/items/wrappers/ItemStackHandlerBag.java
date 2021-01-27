package Strikeboom.cobblestonesemantics.items.wrappers;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class ItemStackHandlerBag extends ItemStackHandler {
    Item item;
    public ItemStackHandlerBag(int size, Item item) {
        super(size);
        this.item = item;
    }
    @Override
    public ItemStack insertItem(int slot,  ItemStack stack, boolean simulate) {
        if (stack.getItem() == item) {
            return stack;
        }
        return super.insertItem(slot, stack, simulate);
    }
}
