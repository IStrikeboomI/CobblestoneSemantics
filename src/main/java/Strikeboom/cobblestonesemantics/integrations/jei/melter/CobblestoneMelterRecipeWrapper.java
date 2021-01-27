package Strikeboom.cobblestonesemantics.integrations.jei.melter;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class CobblestoneMelterRecipeWrapper implements IRecipeWrapper {
    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.ITEM,new ItemStack(Blocks.COBBLESTONE));
        ingredients.setOutput(VanillaTypes.FLUID,new FluidStack(FluidRegistry.LAVA, 500));
    }
}
