package Strikeboom.cobblestonesemantics.integrations.jei.cobblestone_generator;

import Strikeboom.cobblestonesemantics.blocks.CobblestoneGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;

public class CobblestoneGeneratorRecipe {
    ItemStack stack;
    public CobblestoneGeneratorRecipe(ItemStack stack) {
        this.stack = stack;
    }
    public ItemStack getInput() {
        return stack;
    }
    public ItemStack getOutput() {
        return new ItemStack(Blocks.COBBLESTONE,((CobblestoneGenerator) Block.byItem(stack.getItem())).getAmountOfCobblestoneEachOperation());
    }
}
