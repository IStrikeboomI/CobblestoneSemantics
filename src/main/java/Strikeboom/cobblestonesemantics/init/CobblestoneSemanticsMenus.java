package Strikeboom.cobblestonesemantics.init;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.menus.AllInOneGeneratorMenu;
import Strikeboom.cobblestonesemantics.menus.CobblestoneMelterMenu;
import Strikeboom.cobblestonesemantics.menus.LavaGeneratorMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


public class CobblestoneSemanticsMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(BuiltInRegistries.MENU, CobblestoneSemantics.MOD_ID);

    public static final Supplier<MenuType<CobblestoneMelterMenu>> COBBLESTONE_MELTER_MENU = MENUS.register("cobblestone_melter",
            () -> IMenuTypeExtension.create(CobblestoneMelterMenu::new));
    public static final Supplier<MenuType<LavaGeneratorMenu>> LAVA_GENERATOR_MENU = MENUS.register("lava_generator",
            () -> IMenuTypeExtension.create(LavaGeneratorMenu::new));
    public static final Supplier<MenuType<AllInOneGeneratorMenu>> ALL_IN_ONE_GENERATOR_MENU = MENUS.register("all_in_one_generator",
            () -> IMenuTypeExtension.create(AllInOneGeneratorMenu::new));

}
