package Strikeboom.cobblestonesemantics.guis.tileentity.customs.itemhandlers;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class CobblestoneMelterItemHandler extends ItemStackHandler {
    public CobblestoneMelterItemHandler(int size) {
        super(size);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (stack.getItem() != Item.getItemFromBlock(Blocks.COBBLESTONE)) {
            return stack;
        }
        return super.insertItem(slot, stack, simulate);
    }
}
