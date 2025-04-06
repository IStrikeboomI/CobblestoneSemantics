package Strikeboom.cobblestonesemantics.init;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.recipes.CobblestoneInfusedObsidianBagRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


public class CobblestoneSemanticsCustomRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, CobblestoneSemantics.MOD_ID);

    public static final Supplier<RecipeSerializer<CobblestoneInfusedObsidianBagRecipe>> COBBLESTONE_INFUSED_OBSIDIAN_BAG_RECIPE = RECIPES.register("cobblestone_infused_obsidian_bag_2", () -> new ShapelessRecipe().getSerializer());
}
