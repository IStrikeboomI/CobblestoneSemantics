package Strikeboom.cobblestonesemantics.integrations.jei;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlocks;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsMenus;
import Strikeboom.cobblestonesemantics.integrations.jei.cobblestone_generator.CobblestoneGeneratorRecipe;
import Strikeboom.cobblestonesemantics.integrations.jei.cobblestone_generator.CobblestoneGeneratorRecipeCategory;
import Strikeboom.cobblestonesemantics.integrations.jei.cobblestone_melter.CobblestoneMelterRecipe;
import Strikeboom.cobblestonesemantics.integrations.jei.cobblestone_melter.CobblestoneMelterRecipeCategory;
import Strikeboom.cobblestonesemantics.integrations.jei.lava_generator.LavaGeneratorRecipe;
import Strikeboom.cobblestonesemantics.integrations.jei.lava_generator.LavaGeneratorRecipeCategory;
import Strikeboom.cobblestonesemantics.menus.CobblestoneMelterMenu;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.types.IRecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.Tags;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@JeiPlugin
public class CobblestoneSemanticsJeiPlugin implements IModPlugin {

    public static final IRecipeType<CobblestoneMelterRecipe> COBBLESTONE_MELTER = IRecipeType .create(CobblestoneSemantics.MOD_ID,"cobblestone_melter",CobblestoneMelterRecipe.class);
    public static final IRecipeType <LavaGeneratorRecipe>  LAVA_GENERATOR = IRecipeType .create(CobblestoneSemantics.MOD_ID,"lava_generator",LavaGeneratorRecipe.class);
    public static final IRecipeType <CobblestoneGeneratorRecipe>  COBBLESTONE_GENERATOR = IRecipeType .create(CobblestoneSemantics.MOD_ID,"cobblestone_generator",CobblestoneGeneratorRecipe.class);

    @Override
    public Identifier getPluginUid() {
        return Identifier.fromNamespaceAndPath(CobblestoneSemantics.MOD_ID,"jei_plugin");
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
        BuiltInRegistries.ITEM.getTagOrEmpty(Tags.Items.COBBLESTONES).forEach(itemHolder -> cobblestoneMelterRecipes.add(new CobblestoneMelterRecipe(new ItemStack(itemHolder.value()))));
        BuiltInRegistries.ITEM.getTagOrEmpty(Tags.Items.STONES).forEach(itemHolder -> cobblestoneMelterRecipes.add(new CobblestoneMelterRecipe(new ItemStack(itemHolder.value()))));
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
        registration.addCraftingStation(COBBLESTONE_MELTER,new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_MELTER.get()));
        registration.addCraftingStation(LAVA_GENERATOR,new ItemStack(CobblestoneSemanticsBlocks.LAVA_GENERATOR.get()));

    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        IModPlugin.super.registerRecipeTransferHandlers(registration);
        registration.addRecipeTransferHandler(CobblestoneMelterMenu.class, CobblestoneSemanticsMenus.COBBLESTONE_MELTER_MENU.get(),COBBLESTONE_MELTER,0,1,0,36);
    }
}
