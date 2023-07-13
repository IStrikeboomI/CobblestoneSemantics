package Strikeboom.cobblestonesemantics.blockentities.itemhandlers;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;
import net.minecraftforge.items.ItemStackHandler;

public class CobblestoneMelterItemHandler extends ItemStackHandler {
    public CobblestoneMelterItemHandler(int size) {
        super(size);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return stack.is(Tags.Items.COBBLESTONE) || stack.is(Tags.Items.STONE);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (!stack.is(Tags.Items.COBBLESTONE) && !stack.is(Tags.Items.STONE)) {
            return stack;
        }
        return super.insertItem(slot, stack, simulate);
    }
}
