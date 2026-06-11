package Strikeboom.cobblestonesemantics.client.setup;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.client.render.screens.AllInOneGeneratorScreen;
import Strikeboom.cobblestonesemantics.client.render.screens.CobblestoneMelterScreen;
import Strikeboom.cobblestonesemantics.client.render.screens.LavaGeneratorScreen;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsFluids;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsMenus;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterFluidModelsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.fluid.FluidTintSource;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.joml.Vector4f;
import org.jspecify.annotations.Nullable;

@Mod(value = CobblestoneSemantics.MOD_ID, dist = Dist.CLIENT)
public class ClientSetup {
    public ClientSetup(IEventBus modBus, ModContainer modContainer) {
        modBus.register(this);
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
    @SubscribeEvent
    private void registerScreens(RegisterMenuScreensEvent event) {
       event.register(CobblestoneSemanticsMenus.COBBLESTONE_MELTER_MENU.get(), CobblestoneMelterScreen::new);
       event.register(CobblestoneSemanticsMenus.LAVA_GENERATOR_MENU.get(), LavaGeneratorScreen::new);
       event.register(CobblestoneSemanticsMenus.ALL_IN_ONE_GENERATOR_MENU.get(), AllInOneGeneratorScreen::new);
    }
    @SubscribeEvent
    private void registerFluidTextures(RegisterFluidModelsEvent event) {
        event.register(new FluidModel.Unbaked(
                new Material(Identifier.fromNamespaceAndPath(CobblestoneSemantics.MOD_ID, "block/molten_cobblestone_infused_obsidian_still")),
                new Material(Identifier.fromNamespaceAndPath(CobblestoneSemantics.MOD_ID, "block/molten_cobblestone_infused_obsidian_flowing")),
                null, state -> 0x7e7e21a6),CobblestoneSemanticsFluids.MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN);
    }
}
