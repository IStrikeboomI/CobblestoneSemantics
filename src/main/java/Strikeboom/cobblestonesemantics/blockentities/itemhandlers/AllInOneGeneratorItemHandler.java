package Strikeboom.cobblestonesemantics.blockentities.itemhandlers;

import Strikeboom.cobblestonesemantics.blocks.CobblestoneGenerator;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.items.ItemStackHandler;

public class AllInOneGeneratorItemHandler extends ItemStackHandler {
    public AllInOneGeneratorItemHandler() {
        super(13);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        if (slot == 0 && Block.byItem(stack.getItem()) instanceof CobblestoneGenerator) {
            return true;
        }
        if (slot >= 1 && slot <= 4 && stack.getItem() == CobblestoneSemanticsBlocks.COBBLESTONE_MELTER.get().asItem()) {
            return true;
        }
        if (slot >= 5 && slot <= 12 && stack.getItem() == CobblestoneSemanticsBlocks.LAVA_GENERATOR.get().asItem() ) {
            return true;
        }
        return false;
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (slot == 0 && Block.byItem(stack.getItem()) instanceof CobblestoneGenerator) {
            return super.insertItem(slot, stack, simulate);
        }
        if (slot >= 1 && slot <= 4 && stack.getItem() == CobblestoneSemanticsBlocks.COBBLESTONE_MELTER.get().asItem()) {
            return super.insertItem(slot, stack, simulate);
        }
        if (slot >= 5 && slot <= 12 && stack.getItem() == CobblestoneSemanticsBlocks.LAVA_GENERATOR.get().asItem() ) {
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
            if (getStackInSlot(i).getItem() == CobblestoneSemanticsBlocks.COBBLESTONE_MELTER.get().asItem() ) {
                melters++;
            }
        }
        return melters;
    }
    public int getLavaGenerators() {
        int generators = 0;
        for (int i = 5; i <= 12; i++) {
            if (getStackInSlot(i).getItem() == CobblestoneSemanticsBlocks.LAVA_GENERATOR.get().asItem() ) {
                generators++;
            }
        }
        return generators;
    }
    public int getCobbleGenTier() {
        return ((CobblestoneGenerator) Block.byItem(getStackInSlot(0).getItem())).getTier();
    }
}
