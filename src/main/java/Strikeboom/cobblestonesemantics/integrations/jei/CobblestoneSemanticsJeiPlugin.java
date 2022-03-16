package Strikeboom.cobblestonesemantics.integrations.jei;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.guis.menus.CobblestoneMelterMenu;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlocks;
import Strikeboom.cobblestonesemantics.integrations.jei.cobblestone_generator.CobblestoneGeneratorRecipe;
import Strikeboom.cobblestonesemantics.integrations.jei.cobblestone_generator.CobblestoneGeneratorRecipeCategory;
import Strikeboom.cobblestonesemantics.integrations.jei.cobblestone_melter.CobblestoneMelterRecipe;
import Strikeboom.cobblestonesemantics.integrations.jei.cobblestone_melter.CobblestoneMelterRecipeCategory;
import Strikeboom.cobblestonesemantics.integrations.jei.lava_generator.LavaGeneratorRecipe;
import Strikeboom.cobblestonesemantics.integrations.jei.lava_generator.LavaGeneratorRecipeCategory;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@JeiPlugin
public class CobblestoneSemanticsJeiPlugin implements IModPlugin {

    public static final RecipeType<CobblestoneMelterRecipe> COBBLESTONE_MELTER = RecipeType.create(CobblestoneSemantics.MOD_ID,"cobblestone_melter",CobblestoneMelterRecipe.class);
    public static final RecipeType<LavaGeneratorRecipe>  LAVA_GENERATOR = RecipeType.create(CobblestoneSemantics.MOD_ID,"lava_generator",LavaGeneratorRecipe.class);
    public static final RecipeType<CobblestoneGeneratorRecipe>  COBBLESTONE_GENERATOR = RecipeType.create(CobblestoneSemantics.MOD_ID,"cobblestone_generator",CobblestoneGeneratorRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(CobblestoneSemantics.MOD_ID,"jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IModPlugin.super.registerCategories(registration);
        IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new CobblestoneMelterRecipeCategory(helper),
                                        new LavaGeneratorRecipeCategory(helper),
                                        new CobblestoneGeneratorRecipeCategory(helper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        IModPlugin.super.registerRecipes(registration);
        List<CobblestoneMelterRecipe> cobblestoneMelterRecipes = new ArrayList<>();
        Registry.ITEM.getTagOrEmpty(Tags.Items.COBBLESTONE).forEach(itemHolder -> cobblestoneMelterRecipes.add(new CobblestoneMelterRecipe(new ItemStack(itemHolder.value()))));
        Registry.ITEM.getTagOrEmpty(Tags.Items.STONE).forEach(itemHolder -> cobblestoneMelterRecipes.add(new CobblestoneMelterRecipe(new ItemStack(itemHolder.value()))));
        registration.addRecipes(COBBLESTONE_MELTER, cobblestoneMelterRecipes);
        registration.addRecipes(LAVA_GENERATOR,Collections.singletonList(new LavaGeneratorRecipe()));
        registration.addRecipes(COBBLESTONE_GENERATOR,List.of(new CobblestoneGeneratorRecipe(new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_1.get())),
                                        new CobblestoneGeneratorRecipe(new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_2.get())),
                                        new CobblestoneGeneratorRecipe(new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_3.get())),
                                        new CobblestoneGeneratorRecipe(new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_4.get())),
                                        new CobblestoneGeneratorRecipe(new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_5.get())),
                                        new CobblestoneGeneratorRecipe(new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_6.get())),
                                        new CobblestoneGeneratorRecipe(new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_7.get())),
                                        new CobblestoneGeneratorRecipe(new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_8.get())),
                                        new CobblestoneGeneratorRecipe(new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_9.get())),
                                        new CobblestoneGeneratorRecipe(new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_10.get()))));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        IModPlugin.super.registerRecipeCatalysts(registration);
        registration.addRecipeCatalyst(new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_MELTER.get()),COBBLESTONE_MELTER);
        registration.addRecipeCatalyst(new ItemStack(CobblestoneSemanticsBlocks.LAVA_GENERATOR.get()),LAVA_GENERATOR);

    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        IModPlugin.super.registerRecipeTransferHandlers(registration);
        registration.addRecipeTransferHandler(CobblestoneMelterMenu.class,COBBLESTONE_MELTER,0,1,0,36);
    }
}
