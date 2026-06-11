package Strikeboom.cobblestonesemantics.init;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.blockentities.AllInOneGeneratorBlockEntity;
import net.minecraft.core.Direction;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;

@EventBusSubscriber(modid = CobblestoneSemantics.MOD_ID)
public class CobblestoneSemanticsCapabilities {
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.Item.BLOCK, // capability to register for
                CobblestoneSemanticsBlockEntities.ALL_IN_ONE_GENERATOR_BLOCK_ENTITY.get(),
                // blocks to register for
                (object, context) -> object.itemStackHandler);
        event.registerBlockEntity(
                Capabilities.Energy.BLOCK,
                CobblestoneSemanticsBlockEntities.ALL_IN_ONE_GENERATOR_BLOCK_ENTITY.get(),
                (object, context) -> object.energyStorage
        );
        event.registerBlockEntity(
                Capabilities.Item.BLOCK, // capability to register for
                CobblestoneSemanticsBlockEntities.COBBLESTONE_GENERATOR_BLOCK_ENTITY.get(),
                // blocks to register for
                (object, context) -> object.itemStackHandler);
        event.registerBlockEntity(
                Capabilities.Item.BLOCK, // capability to register for
                CobblestoneSemanticsBlockEntities.COBBLESTONE_MELTER_BLOCK_ENTITY.get(),
                // blocks to register for
                (object, context) -> object.itemStackHandler);
        event.registerBlockEntity(
                Capabilities.Fluid.BLOCK, // capability to register for
                CobblestoneSemanticsBlockEntities.COBBLESTONE_MELTER_BLOCK_ENTITY.get(),
                // blocks to register for
                (object, context) -> object.fluidTank);
        event.registerBlockEntity(
                Capabilities.Energy.BLOCK, // capability to register for
                CobblestoneSemanticsBlockEntities.COBBLESTONE_MELTER_BLOCK_ENTITY.get(),
                // blocks to register for
                (object, context) -> object.energyStorage);
        event.registerBlockEntity(
                Capabilities.Fluid.BLOCK, // capability to register for
                CobblestoneSemanticsBlockEntities.LAVA_GENERATOR_BLOCK_ENTITY.get(),
                // blocks to register for
                (object, context) -> object.fluidTank);
        event.registerBlockEntity(
                Capabilities.Energy.BLOCK, // capability to register for
                CobblestoneSemanticsBlockEntities.LAVA_GENERATOR_BLOCK_ENTITY.get(),
                // blocks to register for
                (object, _) -> object.energyStorage);

    }
}
