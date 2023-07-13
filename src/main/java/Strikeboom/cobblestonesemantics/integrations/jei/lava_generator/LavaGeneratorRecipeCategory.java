package Strikeboom.cobblestonesemantics.integrations.jei.lava_generator;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlocks;
import Strikeboom.cobblestonesemantics.integrations.jei.CobblestoneSemanticsJeiPlugin;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
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
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class LavaGeneratorRecipeCategory implements IRecipeCategory<LavaGeneratorRecipe> {
    private final IDrawable BACKGROUND;
    private final IDrawableAnimated ARROW;
    private final IDrawable ICON;
    public LavaGeneratorRecipeCategory(IGuiHelper helper) {
        BACKGROUND = helper.createDrawable(new ResourceLocation(CobblestoneSemantics.MOD_ID, "textures/gui/container/lava_generator.png"),8,3,160,80);
        final IDrawableStatic STATIC_ARROW = helper.createDrawable(new ResourceLocation(CobblestoneSemantics.MOD_ID, "textures/gui/container/lava_generator.png"),176,0,24,16);
        ARROW = helper.createAnimatedDrawable(STATIC_ARROW,200, IDrawableAnimated.StartDirection.LEFT,false);
        ICON = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(CobblestoneSemanticsBlocks.LAVA_GENERATOR.get()));
    }

    @Override
    public RecipeType<LavaGeneratorRecipe> getRecipeType() {
        return CobblestoneSemanticsJeiPlugin.LAVA_GENERATOR;
    }


    @Override
    public Component getTitle() {
        return Component.translatable("block."+CobblestoneSemantics.MOD_ID+".lava_generator");
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
    public void draw(LavaGeneratorRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        ARROW.draw(guiGraphics,72,29);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, LavaGeneratorRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT,13,6).addIngredient(ForgeTypes.FLUID_STACK,recipe.getIngredient()).setFluidRenderer(5000,false,24,66);
    }
}
