package Strikeboom.cobblestonesemantics.integrations.tconstruct;

import Strikeboom.cobblestonesemantics.init.ModBlocks;
import Strikeboom.cobblestonesemantics.init.ModFluids;
import Strikeboom.cobblestonesemantics.init.ModItems;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.TinkerRegistry;

public class MelterRecipes {
    public static void init() {
        TinkerRegistry.registerBasinCasting(new ItemStack(ModBlocks.cobblestoneInfusedObsidian),ItemStack.EMPTY, ModFluids.moltenCobblestoneInfusedObsidian,144);

        TinkerRegistry.registerMelting(ModItems.cobblestoneInfusedObsidianHelmet,ModFluids.moltenCobblestoneInfusedObsidian,144 * 5);
        TinkerRegistry.registerMelting(ModItems.cobblestoneInfusedObsidianChestplate,ModFluids.moltenCobblestoneInfusedObsidian,144 * 8);
        TinkerRegistry.registerMelting(ModItems.cobblestoneInfusedObsidianLeggings,ModFluids.moltenCobblestoneInfusedObsidian,144 * 7);
        TinkerRegistry.registerMelting(ModItems.cobblestoneInfusedObsidianBoots,ModFluids.moltenCobblestoneInfusedObsidian,144 * 4);

        TinkerRegistry.registerMelting(ModItems.cobblestoneInfusedObsidianPickaxe,ModFluids.moltenCobblestoneInfusedObsidian,144 * 3);
        TinkerRegistry.registerMelting(ModItems.cobblestoneInfusedObsidianAxe,ModFluids.moltenCobblestoneInfusedObsidian,144 * 3);
        TinkerRegistry.registerMelting(ModItems.cobblestoneInfusedObsidianShovel,ModFluids.moltenCobblestoneInfusedObsidian, 144);
        TinkerRegistry.registerMelting(ModItems.cobblestoneInfusedObsidianSword,ModFluids.moltenCobblestoneInfusedObsidian,144 * 2);
        TinkerRegistry.registerMelting(ModItems.cobblestoneInfusedObsidianHoe,ModFluids.moltenCobblestoneInfusedObsidian,144 * 2);

        TinkerRegistry.registerMelting(ModBlocks.cobblestoneInfusedObsidian,ModFluids.moltenCobblestoneInfusedObsidian,144);
    }
}
