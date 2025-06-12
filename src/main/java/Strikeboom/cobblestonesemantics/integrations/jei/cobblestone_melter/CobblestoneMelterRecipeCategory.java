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
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class CobblestoneMelterRecipeCategory implements IRecipeCategory<CobblestoneMelterRecipe> {
    private final IDrawable BACKGROUND;
    private final IDrawable ICON;
    private final IDrawableAnimated ARROW;
    public CobblestoneMelterRecipeCategory(IGuiHelper helper) {
        BACKGROUND = helper.createDrawable(ResourceLocation.fromNamespaceAndPath(CobblestoneSemantics.MOD_ID,"textures/gui/container/cobblestone_melter.png"),4,4,150,75);
        final IDrawableStatic STATIC_ARROW = helper.createDrawable(ResourceLocation.fromNamespaceAndPath(CobblestoneSemantics.MOD_ID,"textures/gui/container/cobblestone_melter.png"),176,0,24,16);
        ARROW = helper.createAnimatedDrawable(STATIC_ARROW,200, IDrawableAnimated.StartDirection.LEFT,false);
        ICON = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_MELTER.get()));
    }

    @Override
    public IRecipeType<CobblestoneMelterRecipe> getRecipeType() {
        return CobblestoneSemanticsJeiPlugin.COBBLESTONE_MELTER;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("item."+CobblestoneSemantics.MOD_ID+".cobblestone_melter");
    }

    @Override
    public IDrawable getIcon() {
        return ICON;
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
    public void draw(CobblestoneMelterRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        BACKGROUND.draw(guiGraphics);
        ARROW.draw(guiGraphics,77,28);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CobblestoneMelterRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT,49,29).add(recipe.getInput());
        builder.addSlot(RecipeIngredientRole.OUTPUT,123,5).add(recipe.getOutput().getFluid()).setFluidRenderer(1000,true,24,66);
    }
}
