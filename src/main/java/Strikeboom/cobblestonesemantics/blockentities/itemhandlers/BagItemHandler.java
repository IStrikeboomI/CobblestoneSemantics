package Strikeboom.cobblestonesemantics.blockentities.itemhandlers;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

public class BagItemHandler extends ItemStackHandler {
    Item item;
    public BagItemHandler(int size, Item item) {
        super(size);
        this.item = item;
    }
    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (stack.getItem() == item) {
            return stack;
        }
        return super.insertItem(slot, stack, simulate);
    }
}
