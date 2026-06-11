package Strikeboom.cobblestonesemantics.integrations.jei.cobblestone_generator;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlocks;
import Strikeboom.cobblestonesemantics.integrations.jei.CobblestoneSemanticsJeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public class CobblestoneGeneratorRecipeCategory implements IRecipeCategory<CobblestoneGeneratorRecipe> {

    private final IDrawable BACKGROUND;
    private final IDrawableAnimated ARROW;
    private final IDrawable ICON;
    public CobblestoneGeneratorRecipeCategory(IGuiHelper helper) {
        BACKGROUND = helper.createDrawable(Identifier.fromNamespaceAndPath(CobblestoneSemantics.MOD_ID,"textures/gui/container/cobblestone_generator_jei.png"),40,20,100,50);
        IDrawableStatic STATIC_ARROW = helper.createDrawable(Identifier.fromNamespaceAndPath(CobblestoneSemantics.MOD_ID,"textures/gui/container/cobblestone_generator_jei.png"),176,0,24,17);
        ARROW = helper.createAnimatedDrawable(STATIC_ARROW,60, IDrawableAnimated.StartDirection.LEFT,false);
        ICON = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_10.get()));

    }

    @Override
    public IRecipeType<CobblestoneGeneratorRecipe> getRecipeType() {
        return CobblestoneSemanticsJeiPlugin.COBBLESTONE_GENERATOR;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("item."+CobblestoneSemantics.MOD_ID+".cobblestone_generator");
    }

    @Override
    public int getHeight() {
        return BACKGROUND.getHeight();
    }

    @Override
    public int getWidth() {
        return BACKGROUND.getWidth();
    }

    @Override
    public IDrawable getIcon() {
        return ICON;
    }

    @Override
    public void draw(CobblestoneGeneratorRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        ARROW.draw(guiGraphics,40,15);
        BACKGROUND.draw(guiGraphics);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CobblestoneGeneratorRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT,10,15).add(recipe.getInput());
        builder.addSlot(RecipeIngredientRole.OUTPUT,70,15).add(recipe.getOutput());
    }

}
