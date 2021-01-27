package Strikeboom.cobblestonesemantics.guis.tileentity.customs.itemhandlers;

import Strikeboom.cobblestonesemantics.blocks.CobblestoneGenerator;
import Strikeboom.cobblestonesemantics.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class AllInOneGeneratorItemHandler extends ItemStackHandler {
    public AllInOneGeneratorItemHandler(int size) {
        super(size);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (slot == 0 && Block.getBlockFromItem(stack.getItem()) instanceof CobblestoneGenerator) {
            return super.insertItem(slot, stack, simulate);
        }
        if (slot >= 1 && slot <= 4 && stack.getItem() == Item.getItemFromBlock(ModBlocks.cobblestoneMelter) ) {
            return super.insertItem(slot, stack, simulate);
        }
        if (slot >= 5 && slot <= 12 && stack.getItem() == Item.getItemFromBlock(ModBlocks.lavaGenerator) ) {
            return super.insertItem(slot, stack, simulate);
        }
        return stack;
    }

    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }
    public int getLavaMelters() {
        int melters = 0;
        for (int i = 1; i <= 4; i++) {
            if (getStackInSlot(i).getItem() == Item.getItemFromBlock(ModBlocks.cobblestoneMelter) ) {
                melters++;
            }
        }
        return melters;
    }
    public int getLavaGenerators() {
        int generators = 0;
        for (int i = 5; i <= 12; i++) {
            if (getStackInSlot(i).getItem() == Item.getItemFromBlock(ModBlocks.lavaGenerator) ) {
                generators++;
            }
        }
        return generators;
    }
    public int getCobbleGenTier() {
        return ((CobblestoneGenerator)Block.getBlockFromItem(getStackInSlot(0).getItem())).getTier();
    }
}
