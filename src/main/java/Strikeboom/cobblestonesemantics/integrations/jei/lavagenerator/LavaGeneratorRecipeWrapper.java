package Strikeboom.cobblestonesemantics.integrations.jei.lavagenerator;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.handlers.ConfigHandler;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class LavaGeneratorRecipeWrapper implements IRecipeWrapper {
    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(FluidStack.class,new FluidStack(FluidRegistry.LAVA,1000));
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        String rfText = I18n.format("tile." + CobblestoneSemantics.MOD_ID + ".tooltip.produces") + " "+ ConfigHandler.BLOCKS.LAVA_GENERATOR.powerPerLavaBucket + " RF/FE ";
        minecraft.fontRenderer.drawString(rfText, recipeWidth /2 - minecraft.fontRenderer.getStringWidth(rfText) / 2,90,4210752);
        String tickText = I18n.format("tile." + CobblestoneSemantics.MOD_ID + ".tooltip.every") + " " +ConfigHandler.BLOCKS.LAVA_GENERATOR.generatorDelay + " " + I18n.format("tile." + CobblestoneSemantics.MOD_ID + ".tooltip.ticks");
        minecraft.fontRenderer.drawString( tickText,recipeWidth / 2 - minecraft.fontRenderer.getStringWidth(tickText) / 2,100,4210752);
    }
}
