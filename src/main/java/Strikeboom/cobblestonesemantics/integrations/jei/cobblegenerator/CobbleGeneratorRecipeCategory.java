package Strikeboom.cobblestonesemantics.integrations.jei.cobblegenerator;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.integrations.jei.CobblestoneSemanticsJeiPlugin;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class CobbleGeneratorRecipeCategory implements IRecipeCategory<CobbleGeneratorRecipeWrapper> {
    private final IDrawable BACKGROUND;
    private final IDrawableAnimated ARROW;
    public CobbleGeneratorRecipeCategory(IGuiHelper helper) {
        BACKGROUND = helper.createDrawable(new ResourceLocation(CobblestoneSemantics.MOD_ID,"textures/gui/container/cobblegeneratorjei.png"),40,20,100,50);
        IDrawableStatic STATIC_ARROW = helper.createDrawable(new ResourceLocation(CobblestoneSemantics.MOD_ID,"textures/gui/container/cobblegeneratorjei.png"),176,0,24,17);
        ARROW = helper.createAnimatedDrawable(STATIC_ARROW,60, IDrawableAnimated.StartDirection.LEFT,false);
    }

    @Override
    public String getUid() {
        return CobblestoneSemanticsJeiPlugin.COBBLEGENERATOR;
    }

    @Override
    public String getTitle() {
        return "Cobblestone Generator";
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
        ARROW.draw(minecraft,40,15);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, CobbleGeneratorRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
        stacks.init(0,true,10,15);
        stacks.init(1,false,70,15);
        stacks.set(ingredients);
    }
}
