package Strikeboom.cobblestonesemantics.guis.blockentities.itemhandlers;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class CobblestoneMelterItemHandler extends ItemStackHandler {
    public CobblestoneMelterItemHandler(int size) {
        super(size);
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return Tags.Items.COBBLESTONE.contains(stack.getItem());
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (!Tags.Items.COBBLESTONE.contains(stack.getItem())) {
            return stack;
        }
        return super.insertItem(slot, stack, simulate);
    }
}
