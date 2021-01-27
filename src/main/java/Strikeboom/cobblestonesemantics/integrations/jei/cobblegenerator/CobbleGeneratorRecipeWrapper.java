package Strikeboom.cobblestonesemantics.integrations.jei.cobblegenerator;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class CobbleGeneratorRecipeWrapper implements IRecipeWrapper {
    ItemStack generator;
    public CobbleGeneratorRecipeWrapper(ItemStack generator) {
        this.generator = generator;
    }
    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.ITEM, generator);
        ingredients.setOutput(VanillaTypes.ITEM,new ItemStack(Blocks.COBBLESTONE));
    }
}
