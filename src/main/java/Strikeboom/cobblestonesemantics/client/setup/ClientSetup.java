package Strikeboom.cobblestonesemantics.client.setup;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import Strikeboom.cobblestonesemantics.client.render.screens.*;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsFluids;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsMenus;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

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
    private void registerFluidTextures(RegisterClientExtensionsEvent event) {
        event.registerFluidType(new IClientFluidTypeExtensions() {

            @Override
            public int getTintColor() {
                return 0x7e7e21a6;
            }

            @Override
            public ResourceLocation getStillTexture() {
                return ResourceLocation.fromNamespaceAndPath(CobblestoneSemantics.MOD_ID,"block/molten_cobblestone_infused_obsidian_still");
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return ResourceLocation.fromNamespaceAndPath(CobblestoneSemantics.MOD_ID,"block/molten_cobblestone_infused_obsidian_flowing");
            }
        }, CobblestoneSemanticsFluids.MOLTEN_COBBLESTONE_INFUSED_OBSIDIAN_TYPE);

    }
}
