package Strikeboom.cobblestonesemantics.blockentities.itemhandlers;

import Strikeboom.cobblestonesemantics.blocks.CobblestoneGenerator;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlocks;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

public class AllInOneGeneratorItemHandler extends ItemStacksResourceHandler {
    public AllInOneGeneratorItemHandler() {
        super(13);
    }

    @Override
    public boolean isValid(int slot, ItemResource resource) {
        if (slot == 0 && Block.byItem(resource.getItem()) instanceof CobblestoneGenerator) {
            return true;
        }
        if (slot >= 1 && slot <= 4 && resource.getItem() == CobblestoneSemanticsBlocks.COBBLESTONE_MELTER.get().asItem()) {
            return true;
        }
        if (slot >= 5 && slot <= 12 && resource.getItem() == CobblestoneSemanticsBlocks.LAVA_GENERATOR.get().asItem() ) {
            return true;
        }
        return false;
    }
    @Override
    protected int getCapacity(int index, ItemResource resource) {
        return 1;
    }
    public int getLavaMelters() {
        int melters = 0;
        for (int i = 1; i <= 4; i++) {
            if (getResource(i).getItem() == CobblestoneSemanticsBlocks.COBBLESTONE_MELTER.get().asItem() ) {
                melters++;
            }
        }
        return melters;
    }
    public int getLavaGenerators() {
        int generators = 0;
        for (int i = 5; i <= 12; i++) {
            if (getResource(i).getItem() == CobblestoneSemanticsBlocks.LAVA_GENERATOR.get().asItem() ) {
                generators++;
            }
        }
        return generators;
    }
    public int getCobbleGenTier() {
        return ((CobblestoneGenerator) Block.byItem(getResource(0).getItem())).getTier();
    }
}
