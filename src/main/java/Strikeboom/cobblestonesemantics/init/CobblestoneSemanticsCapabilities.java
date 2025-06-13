package Strikeboom.cobblestonesemantics.init;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.blockentities.AllInOneGeneratorBlockEntity;
import Strikeboom.cobblestonesemantics.blockentities.CobblestoneGeneratorBlockEntity;
import Strikeboom.cobblestonesemantics.blockentities.CobblestoneMelterBlockEntity;
import Strikeboom.cobblestonesemantics.blockentities.LavaGeneratorBlockEntity;
import net.minecraft.core.Direction;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD,modid = CobblestoneSemantics.MOD_ID)
public class CobblestoneSemanticsCapabilities {
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK, // capability to register for
                CobblestoneSemanticsBlockEntities.ALL_IN_ONE_GENERATOR_BLOCK_ENTITY.get(),
                // blocks to register for
                new ICapabilityProvider<>() {
                    @Override
                    public @Nullable IItemHandler getCapability(AllInOneGeneratorBlockEntity object, Direction context) {
                        return object.itemStackHandler;
                    }
                });
        event.registerBlockEntity(
                Capabilities.EnergyStorage.BLOCK,
                CobblestoneSemanticsBlockEntities.ALL_IN_ONE_GENERATOR_BLOCK_ENTITY.get(),
                new ICapabilityProvider<>() {
                    @Override
                    public @Nullable IEnergyStorage getCapability(AllInOneGeneratorBlockEntity object, Direction context) {
                        return object.energyStorage;
                    }
                }
        );
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK, // capability to register for
                CobblestoneSemanticsBlockEntities.COBBLESTONE_GENERATOR_BLOCK_ENTITY.get(),
                // blocks to register for
                new ICapabilityProvider<>() {
                    @Override
                    public @Nullable IItemHandler getCapability(CobblestoneGeneratorBlockEntity object, @Nullable Direction context) {
                        return object.itemStackHandler;
                    }
                });
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK, // capability to register for
                CobblestoneSemanticsBlockEntities.COBBLESTONE_MELTER_BLOCK_ENTITY.get(),
                // blocks to register for
                new ICapabilityProvider<>() {

                    @Override
                    public @Nullable IItemHandler getCapability(CobblestoneMelterBlockEntity object, @Nullable Direction context) {
                        return object.itemStackHandler;
                    }
                });
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK, // capability to register for
                CobblestoneSemanticsBlockEntities.COBBLESTONE_MELTER_BLOCK_ENTITY.get(),
                // blocks to register for
                new ICapabilityProvider<>() {
                    @Override
                    public @Nullable IFluidHandler getCapability(CobblestoneMelterBlockEntity object, @Nullable Direction context) {
                        return object.fluidTank;
                    }
                });
        event.registerBlockEntity(
                Capabilities.EnergyStorage.BLOCK, // capability to register for
                CobblestoneSemanticsBlockEntities.COBBLESTONE_MELTER_BLOCK_ENTITY.get(),
                // blocks to register for
                new ICapabilityProvider<>() {
                    @Override
                    public @Nullable IEnergyStorage getCapability(CobblestoneMelterBlockEntity object, @Nullable Direction context) {
                        return object.energyStorage;
                    }
                });
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK, // capability to register for
                CobblestoneSemanticsBlockEntities.LAVA_GENERATOR_BLOCK_ENTITY.get(),
                // blocks to register for
                new ICapabilityProvider<>() {

                    @Override
                    public @Nullable IFluidHandler getCapability(LavaGeneratorBlockEntity object, @Nullable Direction context) {
                        return object.fluidTank;
                    }
                });
        event.registerBlockEntity(
                Capabilities.EnergyStorage.BLOCK, // capability to register for
                CobblestoneSemanticsBlockEntities.LAVA_GENERATOR_BLOCK_ENTITY.get(),
                // blocks to register for
                new ICapabilityProvider<>() {
                    @Override
                    public @Nullable IEnergyStorage getCapability(LavaGeneratorBlockEntity object, @Nullable Direction context) {
                        return object.energyStorage;
                    }
                });

    }
}
