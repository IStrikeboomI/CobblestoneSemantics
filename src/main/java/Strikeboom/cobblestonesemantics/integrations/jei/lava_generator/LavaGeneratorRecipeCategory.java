package Strikeboom.cobblestonesemantics.integrations.jei.lava_generator;

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

public class LavaGeneratorRecipeCategory implements IRecipeCategory<LavaGeneratorRecipe> {
    private final IDrawable BACKGROUND;
    private final IDrawableAnimated ARROW;
    private final IDrawable ICON;
    public LavaGeneratorRecipeCategory(IGuiHelper helper) {
        BACKGROUND = helper.createDrawable(Identifier.fromNamespaceAndPath(CobblestoneSemantics.MOD_ID, "textures/gui/container/lava_generator.png"),8,3,160,80);
        final IDrawableStatic STATIC_ARROW = helper.createDrawable(Identifier.fromNamespaceAndPath(CobblestoneSemantics.MOD_ID, "textures/gui/container/lava_generator.png"),176,0,24,16);
        ARROW = helper.createAnimatedDrawable(STATIC_ARROW,200, IDrawableAnimated.StartDirection.LEFT,false);
        ICON = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(CobblestoneSemanticsBlocks.LAVA_GENERATOR.get()));
    }

    @Override
    public IRecipeType<LavaGeneratorRecipe> getRecipeType() {
        return CobblestoneSemanticsJeiPlugin.LAVA_GENERATOR;
    }


    @Override
    public Component getTitle() {
        return Component.translatable("item."+CobblestoneSemantics.MOD_ID+".lava_generator");
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
    public void draw(LavaGeneratorRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        ARROW.draw(guiGraphics,72,29);
        BACKGROUND.draw(guiGraphics);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, LavaGeneratorRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT,13,6).add(recipe.getIngredient().getFluid()).setFluidRenderer(5000,false,24,66);
    }
}
