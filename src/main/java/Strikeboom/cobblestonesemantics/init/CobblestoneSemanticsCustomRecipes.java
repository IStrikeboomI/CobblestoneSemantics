package Strikeboom.cobblestonesemantics.init;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.recipes.CobblestoneInfusedObsidianBagRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CobblestoneSemanticsCustomRecipes {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, CobblestoneSemantics.MOD_ID);

    public static final RegistryObject<IRecipeSerializer<CobblestoneInfusedObsidianBagRecipe>> COBBLESTONE_INFUSED_OBSIDIAN_BAG_RECIPE = RECIPES.register("cobblestone_infused_obsidian_bag_2", () -> new SpecialRecipeSerializer<>(CobblestoneInfusedObsidianBagRecipe::new));
}
