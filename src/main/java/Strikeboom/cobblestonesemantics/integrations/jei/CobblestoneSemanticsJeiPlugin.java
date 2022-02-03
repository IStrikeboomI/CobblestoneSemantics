package Strikeboom.cobblestonesemantics.integrations.jei;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.guis.menus.CobblestoneMelterMenu;
import Strikeboom.cobblestonesemantics.guis.menus.LavaGeneratorMenu;
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
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.List;

@JeiPlugin
public class CobblestoneSemanticsJeiPlugin implements IModPlugin {

    public static final ResourceLocation COBBLESTONE_MELTER = new ResourceLocation(CobblestoneSemantics.MOD_ID,"cobblestone_melter");
    public static final ResourceLocation LAVA_GENERATOR = new ResourceLocation(CobblestoneSemantics.MOD_ID,"lava_generator");
    public static final ResourceLocation COBBLESTONE_GENERATOR = new ResourceLocation(CobblestoneSemantics.MOD_ID,"cobblestone_generator");

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
        registration.addRecipes(Collections.singletonList(new CobblestoneMelterRecipe()),COBBLESTONE_MELTER);
        registration.addRecipes(Collections.singletonList(new LavaGeneratorRecipe()),LAVA_GENERATOR);
        registration.addRecipes(List.of(new CobblestoneGeneratorRecipe(new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_1.get())),
                                        new CobblestoneGeneratorRecipe(new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_2.get())),
                                        new CobblestoneGeneratorRecipe(new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_3.get())),
                                        new CobblestoneGeneratorRecipe(new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_4.get())),
                                        new CobblestoneGeneratorRecipe(new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_5.get())),
                                        new CobblestoneGeneratorRecipe(new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_6.get())),
                                        new CobblestoneGeneratorRecipe(new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_7.get())),
                                        new CobblestoneGeneratorRecipe(new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_8.get())),
                                        new CobblestoneGeneratorRecipe(new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_9.get())),
                                        new CobblestoneGeneratorRecipe(new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_10.get()))), COBBLESTONE_GENERATOR);
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
