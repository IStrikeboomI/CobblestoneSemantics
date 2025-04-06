package Strikeboom.cobblestonesemantics.init;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.menus.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;


public class CobblestoneSemanticsMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(BuiltInRegistries.MENU, CobblestoneSemantics.MOD_ID);

    public static final Supplier<MenuType<CobblestoneMelterMenu>> COBBLESTONE_MELTER_MENU = MENUS.register("cobblestone_melter",
            () -> new MenuType<>(CobblestoneMelterMenu::new, FeatureFlags.DEFAULT_FLAGS));
    public static final Supplier<MenuType<LavaGeneratorMenu>> LAVA_GENERATOR_MENU = MENUS.register("lava_generator",
            () -> new MenuType<>(LavaGeneratorMenu::new,FeatureFlags.DEFAULT_FLAGS));
    public static final Supplier<MenuType<AllInOneGeneratorMenu>> ALL_IN_ONE_GENERATOR_MENU = MENUS.register("all_in_one_generator",
            () -> new MenuType<>(AllInOneGeneratorMenu::new,FeatureFlags.DEFAULT_FLAGS));
    public static final Supplier<MenuType<CobblestoneBagMenu>> COBBLESTONE_BAG_MENU = MENUS.register("cobblestone_bag",
            () -> new MenuType<>(CobblestoneBagMenu::new,FeatureFlags.DEFAULT_FLAGS));
    public static final Supplier<MenuType<CobblestoneInfusedObsidianBagMenu>> COBBLESTONE_INFUSED_OBSIDIAN_BAG_MENU = MENUS.register("cobblestone_infused_obsidian_bag",
            () -> new MenuType<>(CobblestoneInfusedObsidianBagMenu::new,FeatureFlags.DEFAULT_FLAGS));
}
