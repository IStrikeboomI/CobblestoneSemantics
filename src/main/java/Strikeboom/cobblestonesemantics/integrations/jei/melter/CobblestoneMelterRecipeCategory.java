package Strikeboom.cobblestonesemantics.integrations.jei.melter;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.init.ModBlocks;
import Strikeboom.cobblestonesemantics.integrations.jei.CobblestoneSemanticsJeiPlugin;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class CobblestoneMelterRecipeCategory implements IRecipeCategory<CobblestoneMelterRecipeWrapper> {
    private final IDrawable BACKGROUND;
    private final IDrawableAnimated ARROW;
    public CobblestoneMelterRecipeCategory(IGuiHelper helper) {
        BACKGROUND = helper.createDrawable(new ResourceLocation(CobblestoneSemantics.MOD_ID,"textures/gui/container/cobblestonemelter.png"),4,4,150,75);
        final IDrawableStatic STATIC_ARROW = helper.createDrawable(new ResourceLocation(CobblestoneSemantics.MOD_ID,"textures/gui/container/cobblestonemelter.png"),176,0,24,16);
        ARROW = helper.createAnimatedDrawable(STATIC_ARROW,200, IDrawableAnimated.StartDirection.LEFT,false);
    }

    @Override
    public String getUid() {
        return CobblestoneSemanticsJeiPlugin.COBBLESTONEMELTER;
    }

    @Override
    public String getTitle() {
        return ModBlocks.cobblestoneMelter.getLocalizedName();
    }

    @Override
    public String getModName() {
        return "Cobblestone Semantics";
    }

    @Override
    public IDrawable getBackground() {
        return BACKGROUND;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        ARROW.draw(minecraft,77,28);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, CobblestoneMelterRecipeWrapper recipeWrapper, IIngredients ingredients) {
        recipeLayout.getFluidStacks().init(0,false,123,5,24,66, 1000,true,null);
        recipeLayout.getFluidStacks().set(ingredients);
        recipeLayout.getItemStacks().init(0,true,48,28);
        recipeLayout.getItemStacks().set(ingredients);
    }
}
