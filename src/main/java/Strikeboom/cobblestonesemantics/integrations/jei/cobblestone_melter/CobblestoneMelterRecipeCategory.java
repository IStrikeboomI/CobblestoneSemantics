package Strikeboom.cobblestonesemantics.integrations.jei.cobblestone_melter;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlocks;
import Strikeboom.cobblestonesemantics.integrations.jei.CobblestoneSemanticsJeiPlugin;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class CobblestoneMelterRecipeCategory implements IRecipeCategory<CobblestoneMelterRecipe> {
    private final IDrawable BACKGROUND;
    private final IDrawable ICON;
    private final IDrawableAnimated ARROW;
    public CobblestoneMelterRecipeCategory(IGuiHelper helper) {
        BACKGROUND = helper.createDrawable(new ResourceLocation(CobblestoneSemantics.MOD_ID,"textures/gui/container/cobblestone_melter.png"),4,4,150,75);
        final IDrawableStatic STATIC_ARROW = helper.createDrawable(new ResourceLocation(CobblestoneSemantics.MOD_ID,"textures/gui/container/cobblestone_melter.png"),176,0,24,16);
        ARROW = helper.createAnimatedDrawable(STATIC_ARROW,200, IDrawableAnimated.StartDirection.LEFT,false);
        ICON = helper.createDrawableIngredient(VanillaTypes.ITEM,new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_MELTER.get()));
    }

    @Override
    public RecipeType<CobblestoneMelterRecipe> getRecipeType() {
        return CobblestoneSemanticsJeiPlugin.COBBLESTONE_MELTER;
    }

    @Override
    public Component getTitle() {
        return new TranslatableComponent("block."+CobblestoneSemantics.MOD_ID+".cobblestone_melter");
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
    public void draw(CobblestoneMelterRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
        ARROW.draw(stack,77,28);
    }

    @SuppressWarnings("removal")
    @Override
    public ResourceLocation getUid() {
        return getRecipeType().getUid();
    }

    @SuppressWarnings("removal")
    @Override
    public Class<? extends CobblestoneMelterRecipe> getRecipeClass() {
        return getRecipeType().getRecipeClass();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CobblestoneMelterRecipe recipe, IFocusGroup focuses) {
        IRecipeCategory.super.setRecipe(builder, recipe, focuses);
        builder.addSlot(RecipeIngredientRole.INPUT,49,29).addItemStack(recipe.getInput());
        builder.addSlot(RecipeIngredientRole.OUTPUT,123,5).addIngredient(VanillaTypes.FLUID,recipe.getOutput()).setFluidRenderer(1000,true,24,66);
    }
}
