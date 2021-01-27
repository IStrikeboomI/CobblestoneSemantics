package Strikeboom.cobblestonesemantics.integrations.jei.lavagenerator;

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

public class LavaGeneratorRecipeCategory implements IRecipeCategory<LavaGeneratorRecipeWrapper> {
    private final IDrawable BACKGROUND;
    private final IDrawableAnimated ARROW;
    public LavaGeneratorRecipeCategory(IGuiHelper helper) {
        BACKGROUND = helper.createDrawable(new ResourceLocation(CobblestoneSemantics.MOD_ID, "textures/gui/container/lavagenerator.png"),8,3,160,80);
        final IDrawableStatic STATIC_ARROW = helper.createDrawable(new ResourceLocation(CobblestoneSemantics.MOD_ID, "textures/gui/container/lavagenerator.png"),176,0,24,16);
        ARROW = helper.createAnimatedDrawable(STATIC_ARROW,200, IDrawableAnimated.StartDirection.LEFT,false);
    }

    @Override
    public String getUid() {
        return CobblestoneSemanticsJeiPlugin.LAVAGENERATOR;
    }

    @Override
    public String getTitle() {
        return ModBlocks.lavaGenerator.getLocalizedName();
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
        ARROW.draw(minecraft,72,29);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, LavaGeneratorRecipeWrapper recipeWrapper, IIngredients ingredients) {
        recipeLayout.getFluidStacks().init(0,true,13,6,24,66,5000,true,null);
        recipeLayout.getFluidStacks().set(ingredients);
    }
}
