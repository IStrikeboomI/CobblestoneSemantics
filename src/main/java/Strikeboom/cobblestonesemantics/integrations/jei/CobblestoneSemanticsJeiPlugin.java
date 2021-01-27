package Strikeboom.cobblestonesemantics.integrations.jei;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.guis.container.ContainerCobblestoneMelter;
import Strikeboom.cobblestonesemantics.init.ModBlocks;
import Strikeboom.cobblestonesemantics.integrations.jei.cobblegenerator.CobbleGeneratorRecipeCategory;
import Strikeboom.cobblestonesemantics.integrations.jei.cobblegenerator.CobbleGeneratorRecipes;
import Strikeboom.cobblestonesemantics.integrations.jei.lavagenerator.LavaGeneratorRecipeCategory;
import Strikeboom.cobblestonesemantics.integrations.jei.lavagenerator.LavaGeneratorRecipeWrapper;
import Strikeboom.cobblestonesemantics.integrations.jei.melter.CobblestoneMelterRecipeCategory;
import Strikeboom.cobblestonesemantics.integrations.jei.melter.CobblestoneMelterRecipeWrapper;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;

import java.util.Collections;

@JEIPlugin
public class CobblestoneSemanticsJeiPlugin implements IModPlugin {

    public static final String COBBLEGENERATOR = CobblestoneSemantics.MOD_ID + ".cobblegenerator";
    public static final String COBBLESTONEMELTER = CobblestoneSemantics.MOD_ID + ".cobblestonemelter";
    public static final String LAVAGENERATOR = CobblestoneSemantics.MOD_ID + ".lavagenerator";

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper helper = registry.getJeiHelpers().getGuiHelper();
        registry.addRecipeCategories(
                new CobbleGeneratorRecipeCategory(helper),
                new CobblestoneMelterRecipeCategory(helper),
                new LavaGeneratorRecipeCategory(helper));
    }

    @Override
    public void register(IModRegistry registry) {
        registry.addRecipes(CobbleGeneratorRecipes.recipes(registry),COBBLEGENERATOR);

        registry.addRecipes(Collections.singletonList(new CobblestoneMelterRecipeWrapper()),COBBLESTONEMELTER);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.cobblestoneMelter),COBBLESTONEMELTER);
        registry.getRecipeTransferRegistry().addRecipeTransferHandler(ContainerCobblestoneMelter.class,COBBLESTONEMELTER,0,1,0,36);

        registry.addRecipes(Collections.singletonList(new LavaGeneratorRecipeWrapper()),LAVAGENERATOR);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.lavaGenerator),LAVAGENERATOR);
    }
}
