package Strikeboom.cobblestonesemantics.init;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.guis.containers.*;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CobblestoneSemanticsContainers {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, CobblestoneSemantics.MOD_ID);

    public static final RegistryObject<ContainerType<CobblestoneMelterContainer>> COBBLESTONE_MELTER_MENU = CONTAINERS.register("cobblestone_melter",
            () -> IForgeContainerType.create((windowId, inv, data) -> new CobblestoneMelterContainer(windowId, data.readBlockPos(), inv, inv.player, IWorldPosCallable.NULL)));
    public static final RegistryObject<ContainerType<LavaGeneratorContainer>> LAVA_GENERATOR_MENU = CONTAINERS.register("lava_generator",
            () -> IForgeContainerType.create((windowId, inv, data) -> new LavaGeneratorContainer(windowId, data.readBlockPos(), inv, inv.player, IWorldPosCallable.NULL)));
    public static final RegistryObject<ContainerType<AllInOneGeneratorContainer>> ALL_IN_ONE_GENERATOR_MENU = CONTAINERS.register("all_in_one_generator",
            () -> IForgeContainerType.create((windowId, inv, data) -> new AllInOneGeneratorContainer(windowId, data.readBlockPos(), inv, inv.player, IWorldPosCallable.NULL)));
    public static final RegistryObject<ContainerType<CobblestoneBagContainer>> COBBLESTONE_BAG_MENU = CONTAINERS.register("cobblestone_bag",
            () -> IForgeContainerType.create((windowId, inv, data) -> new CobblestoneBagContainer(windowId, data.readBlockPos(), inv, inv.player)));
    public static final RegistryObject<ContainerType<CobblestoneInfusedObsidianBagContainer>> COBBLESTONE_INFUSED_OBSIDIAN_BAG_MENU = CONTAINERS.register("cobblestone_infused_obsidian_bag",
            () -> IForgeContainerType.create((windowId, inv, data) -> new CobblestoneInfusedObsidianBagContainer(windowId, data.readBlockPos(), inv, inv.player)));
}
