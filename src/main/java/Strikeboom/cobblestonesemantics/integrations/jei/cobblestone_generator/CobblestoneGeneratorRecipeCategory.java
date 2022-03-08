package Strikeboom.cobblestonesemantics.integrations.jei.cobblestone_generator;

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

public class CobblestoneGeneratorRecipeCategory implements IRecipeCategory<CobblestoneGeneratorRecipe> {

    private final IDrawable BACKGROUND;
    private final IDrawableAnimated ARROW;
    private final IDrawable ICON;
    public CobblestoneGeneratorRecipeCategory(IGuiHelper helper) {
        BACKGROUND = helper.createDrawable(new ResourceLocation(CobblestoneSemantics.MOD_ID,"textures/gui/container/cobblestone_generator_jei.png"),40,20,100,50);
        IDrawableStatic STATIC_ARROW = helper.createDrawable(new ResourceLocation(CobblestoneSemantics.MOD_ID,"textures/gui/container/cobblestone_generator_jei.png"),176,0,24,17);
        ARROW = helper.createAnimatedDrawable(STATIC_ARROW,60, IDrawableAnimated.StartDirection.LEFT,false);
        ICON = helper.createDrawableIngredient(VanillaTypes.ITEM,new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_10.get()));

    }

    @Override
    public RecipeType<CobblestoneGeneratorRecipe> getRecipeType() {
        return CobblestoneSemanticsJeiPlugin.COBBLESTONE_GENERATOR;
    }

    @SuppressWarnings("removal")
    @Override
    public ResourceLocation getUid() {
        return getRecipeType().getUid();
    }

    @SuppressWarnings("removal")
    @Override
    public Class<? extends CobblestoneGeneratorRecipe> getRecipeClass() {
        return getRecipeType().getRecipeClass();
    }

    @Override
    public Component getTitle() {
        return new TranslatableComponent("block."+CobblestoneSemantics.MOD_ID+".cobblestone_generator");
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
    public void draw(CobblestoneGeneratorRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
        ARROW.draw(stack,40,15);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CobblestoneGeneratorRecipe recipe, IFocusGroup focuses) {
        IRecipeCategory.super.setRecipe(builder, recipe, focuses);
        builder.addSlot(RecipeIngredientRole.INPUT,10,15).addItemStack(recipe.getInput());
        builder.addSlot(RecipeIngredientRole.OUTPUT,70,15).addItemStack(recipe.getOutput());
    }

}
