package Strikeboom.cobblestonesemantics.integrations.jei.cobblestone_melter;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlocks;
import Strikeboom.cobblestonesemantics.integrations.jei.CobblestoneSemanticsJeiPlugin;
import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class CobblestoneMelterRecipeCategory implements IRecipeCategory<CobblestoneMelterRecipe> {
    private final IDrawable BACKGROUND;
    private final IDrawable ICON;
    private final IDrawableAnimated ARROW;
    public CobblestoneMelterRecipeCategory(IGuiHelper helper) {
        BACKGROUND = helper.createDrawable(new ResourceLocation(CobblestoneSemantics.MOD_ID, "textures/gui/container/cobblestone_melter.png"), 4, 4, 150, 75);
        final IDrawableStatic STATIC_ARROW = helper.createDrawable(new ResourceLocation(CobblestoneSemantics.MOD_ID, "textures/gui/container/cobblestone_melter.png"), 176, 0, 24, 16);
        ARROW = helper.createAnimatedDrawable(STATIC_ARROW, 200, IDrawableAnimated.StartDirection.LEFT, false);
        ICON = helper.createDrawableIngredient(new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_MELTER.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return CobblestoneSemanticsJeiPlugin.COBBLESTONE_MELTER;
    }

    @Override
    public ITextComponent getTitleAsTextComponent() {
        return new TranslationTextComponent("block."+CobblestoneSemantics.MOD_ID+".cobblestone_melter");
    }

    @Override
    public IDrawable getBackground() {
        return BACKGROUND;
    }

    @Override
    public IDrawable getIcon() {
        return ICON;
    }

    @Override
    public void draw(CobblestoneMelterRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, matrixStack, mouseX, mouseY);
        ARROW.draw(matrixStack,77,28);
    }

    @Override
    public Class<? extends CobblestoneMelterRecipe> getRecipeClass() {
        return CobblestoneMelterRecipe.class;
    }

    @Override
    public String getTitle() {
        return I18n.get("block."+CobblestoneSemantics.MOD_ID+".cobblestone_melter");
    }

    @Override
    public void setIngredients(CobblestoneMelterRecipe recipe, IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.ITEM,recipe.getInput());
        ingredients.setOutput(VanillaTypes.FLUID,recipe.getOutput());
    }
    @Override
    public void setRecipe(IRecipeLayout recipeLayout, CobblestoneMelterRecipe recipe, IIngredients ingredients) {
        recipeLayout.getFluidStacks().init(0,false,123,5,24,66, 1000,true,null);
        recipeLayout.getFluidStacks().set(ingredients);
        recipeLayout.getItemStacks().init(0,true,48,28);
        recipeLayout.getItemStacks().set(ingredients);
    }
}
