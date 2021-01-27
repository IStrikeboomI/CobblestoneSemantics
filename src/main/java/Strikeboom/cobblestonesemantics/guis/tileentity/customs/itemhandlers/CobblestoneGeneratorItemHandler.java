package Strikeboom.cobblestonesemantics.guis.tileentity.customs.itemhandlers;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class CobblestoneGeneratorItemHandler extends ItemStackHandler {

    public CobblestoneGeneratorItemHandler(int slots) {
        super(slots);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        return stack;
    }
    public ItemStack getLargestSlotThenRemove() {
        ItemStack stack = getStackInSlot(getLargestSlotIndex()).copy();
        setStackInSlot(getLargestSlotIndex(),ItemStack.EMPTY);
        return stack.isEmpty() ? ItemStack.EMPTY : stack;
    }
    public int getLargestSlotIndex() {
        int l = 0;
        ItemStack prev = ItemStack.EMPTY;
        for (int i = 0;i < getSlots();i++) {
            if (!getStackInSlot(i).isEmpty()) {
                if (getStackInSlot(i).getCount() > prev.getCount()) {
                    prev = getStackInSlot(i);
                    l = i;
                }
            }
        }
        return l;
    }

}
