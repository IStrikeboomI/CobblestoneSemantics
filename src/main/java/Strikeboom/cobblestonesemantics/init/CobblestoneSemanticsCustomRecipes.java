package Strikeboom.cobblestonesemantics.init;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.recipes.CobblestoneInfusedObsidianBagRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CobblestoneSemanticsCustomRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, CobblestoneSemantics.MOD_ID);

    public static final RegistryObject<RecipeSerializer<CobblestoneInfusedObsidianBagRecipe>> COBBLESTONE_INFUSED_OBSIDIAN_BAG_RECIPE = RECIPES.register("cobblestone_infused_obsidian_bag_2", () -> new SimpleRecipeSerializer<>(CobblestoneInfusedObsidianBagRecipe::new));
}
