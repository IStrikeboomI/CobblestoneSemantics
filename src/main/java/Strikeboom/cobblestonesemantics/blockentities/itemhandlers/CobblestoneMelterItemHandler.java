package Strikeboom.cobblestonesemantics.blockentities.itemhandlers;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.items.ItemStackHandler;

public class CobblestoneMelterItemHandler extends ItemStackHandler {
    public CobblestoneMelterItemHandler(int size) {
        super(size);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return stack.is(Tags.Items.COBBLESTONES) || stack.is(Tags.Items.STONES);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (!stack.is(Tags.Items.COBBLESTONES) && !stack.is(Tags.Items.STONES)) {
            return stack;
        }
        return super.insertItem(slot, stack, simulate);
    }
}
