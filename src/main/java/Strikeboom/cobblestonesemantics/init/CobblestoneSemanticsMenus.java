package Strikeboom.cobblestonesemantics.init;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.guis.menus.*;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CobblestoneSemanticsMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, CobblestoneSemantics.MOD_ID);

    public static final RegistryObject<MenuType<CobblestoneMelterMenu>> COBBLESTONE_MELTER_MENU = MENUS.register("cobblestone_melter",
            () -> IForgeMenuType.create((windowId, inv, data) -> new CobblestoneMelterMenu(windowId, data.readBlockPos(), inv, inv.player)));
    public static final RegistryObject<MenuType<LavaGeneratorMenu>> LAVA_GENERATOR_MENU = MENUS.register("lava_generator",
            () -> IForgeMenuType.create((windowId, inv, data) -> new LavaGeneratorMenu(windowId, data.readBlockPos(), inv, inv.player)));
    public static final RegistryObject<MenuType<AllInOneGeneratorMenu>> ALL_IN_ONE_GENERATOR_MENU = MENUS.register("all_in_one_generator",
            () -> IForgeMenuType.create((windowId, inv, data) -> new AllInOneGeneratorMenu(windowId, data.readBlockPos(), inv, inv.player)));
    public static final RegistryObject<MenuType<CobblestoneBagMenu>> COBBLESTONE_BAG_MENU = MENUS.register("cobblestone_bag",
            () -> IForgeMenuType.create((windowId, inv, data) -> new CobblestoneBagMenu(windowId, inv)));
    public static final RegistryObject<MenuType<CobblestoneInfusedObsidianBagMenu>> COBBLESTONE_INFUSED_OBSIDIAN_BAG_MENU = MENUS.register("cobblestone_infused_obsidian_bag",
            () -> IForgeMenuType.create((windowId, inv, data) -> new CobblestoneInfusedObsidianBagMenu(windowId, inv)));
}
