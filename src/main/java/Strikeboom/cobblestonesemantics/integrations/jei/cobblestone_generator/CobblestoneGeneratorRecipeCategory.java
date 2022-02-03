package Strikeboom.cobblestonesemantics.integrations.jei.cobblestone_generator;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlocks;
import Strikeboom.cobblestonesemantics.integrations.jei.CobblestoneSemanticsJeiPlugin;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
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
        ICON = helper.createDrawableIngredient(new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_10.get()));

    }

    @Override
    public ResourceLocation getUid() {
        return CobblestoneSemanticsJeiPlugin.COBBLESTONE_GENERATOR;
    }

    @Override
    public Class<? extends CobblestoneGeneratorRecipe> getRecipeClass() {
        return CobblestoneGeneratorRecipe.class;
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
    public void draw(CobblestoneGeneratorRecipe recipe, PoseStack stack, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, stack, mouseX, mouseY);
        ARROW.draw(stack,40,15);
    }

    @Override
    public void setIngredients(CobblestoneGeneratorRecipe recipe, IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.ITEM,recipe.getInput());
        ingredients.setOutput(VanillaTypes.ITEM,recipe.getOutput());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, CobblestoneGeneratorRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
        stacks.init(0,true,10,15);
        stacks.init(1,false,70,15);
        stacks.set(ingredients);
    }
}
