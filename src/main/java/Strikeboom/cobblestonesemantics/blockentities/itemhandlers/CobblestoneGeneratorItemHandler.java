package Strikeboom.cobblestonesemantics.blockentities.itemhandlers;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

public class CobblestoneGeneratorItemHandler extends ItemStacksResourceHandler {
    public CobblestoneGeneratorItemHandler(int slots) {
        super(slots);
    }

    @Override
    public int insert(int index, ItemResource resource, int amount, TransactionContext transaction) {
        return 0;
    }

    public ItemStack getLargestSlotThenRemove() {
        ItemStack stack = getResource(getLargestSlotIndex()).toStack(getAmountAsInt(getLargestSlotIndex()));
        set(getLargestSlotIndex(),ItemResource.EMPTY,0);
        return stack.isEmpty() ? ItemStack.EMPTY : stack;
    }
    public int getLargestSlotIndex() {
        int l = 0;
        ItemStack prev = ItemStack.EMPTY;
        for (int i = 0;i < stacks.size();i++) {
            if (!getResource(i).isEmpty()) {
                if (getAmountAsInt(i) > prev.getCount()) {
                    prev = getResource(i).toStack(getAmountAsInt(i));
                    l = i;
                }
            }
        }
        return l;
    }
}
