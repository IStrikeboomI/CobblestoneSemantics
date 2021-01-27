package Strikeboom.cobblestonesemantics.integrations.jei.cobblegenerator;

import Strikeboom.cobblestonesemantics.blocks.CobblestoneGenerator;
import Strikeboom.cobblestonesemantics.init.ModBlocks;
import Strikeboom.cobblestonesemantics.integrations.jei.CobblestoneSemanticsJeiPlugin;
import mezz.jei.api.IModRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CobbleGeneratorRecipes {

    public static List<CobbleGeneratorRecipeWrapper> recipes(IModRegistry registry) {
        List<CobbleGeneratorRecipeWrapper> recipes = new ArrayList<>();
        for (Block block : ModBlocks.BLOCKS) {
            if (block instanceof CobblestoneGenerator) {
                recipes.add(new CobbleGeneratorRecipeWrapper(new ItemStack(block)));
                registry.addRecipeCatalyst(new ItemStack(block), CobblestoneSemanticsJeiPlugin.COBBLEGENERATOR);
            }
        }
        return recipes;
    }
}
