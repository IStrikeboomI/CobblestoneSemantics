package Strikeboom.cobblestonesemantics.integrations.jei.lava_generator;

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

public class LavaGeneratorRecipeCategory implements IRecipeCategory<LavaGeneratorRecipe> {
    private final IDrawable BACKGROUND;
    private final IDrawableAnimated ARROW;
    private final IDrawable ICON;
    public LavaGeneratorRecipeCategory(IGuiHelper helper) {
        BACKGROUND = helper.createDrawable(new ResourceLocation(CobblestoneSemantics.MOD_ID, "textures/gui/container/lava_generator.png"),8,3,160,80);
        final IDrawableStatic STATIC_ARROW = helper.createDrawable(new ResourceLocation(CobblestoneSemantics.MOD_ID, "textures/gui/container/lava_generator.png"),176,0,24,16);
        ARROW = helper.createAnimatedDrawable(STATIC_ARROW,200, IDrawableAnimated.StartDirection.LEFT,false);
        ICON = helper.createDrawableIngredient(new ItemStack(CobblestoneSemanticsBlocks.LAVA_GENERATOR.get()));
    }

    @Override
    public ITextComponent getTitleAsTextComponent() {
        return new TranslationTextComponent("block."+CobblestoneSemantics.MOD_ID+".lava_generator");
    }

    @Override
    public String getTitle() {
        return I18n.get("block."+CobblestoneSemantics.MOD_ID+".lava_generator");
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
    public void setIngredients(LavaGeneratorRecipe recipe, IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.FLUID,recipe.getOutput());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, LavaGeneratorRecipe recipe, IIngredients ingredients) {
        recipeLayout.getFluidStacks().init(0,true,13,6,24,66,5000,true,null);
        recipeLayout.getFluidStacks().set(ingredients);
    }


    @Override
    public void draw(LavaGeneratorRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, matrixStack, mouseX, mouseY);
        ARROW.draw(matrixStack,72,29);
    }

    @Override
    public ResourceLocation getUid() {
        return CobblestoneSemanticsJeiPlugin.LAVA_GENERATOR;
    }

    @Override
    public Class<? extends LavaGeneratorRecipe> getRecipeClass() {
        return LavaGeneratorRecipe.class;
    }
}
