package Strikeboom.cobblestonesemantics.client.setup;

import Strikeboom.cobblestonesemantics.client.render.screens.*;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlocks;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsContainers;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {
    public static void init(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            RenderTypeLookup.setRenderLayer(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_1.get(), RenderType.cutout());
            RenderTypeLookup .setRenderLayer(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_2.get(), RenderType.cutout());
            RenderTypeLookup .setRenderLayer(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_3.get(), RenderType.cutout());
            RenderTypeLookup .setRenderLayer(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_4.get(), RenderType.cutout());
            RenderTypeLookup .setRenderLayer(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_5.get(), RenderType.cutout());
            RenderTypeLookup .setRenderLayer(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_6.get(), RenderType.cutout());
            RenderTypeLookup .setRenderLayer(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_7.get(), RenderType.cutout());
            RenderTypeLookup .setRenderLayer(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_8.get(), RenderType.cutout());
            RenderTypeLookup .setRenderLayer(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_9.get(), RenderType.cutout());
            RenderTypeLookup .setRenderLayer(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_10.get(), RenderType.cutout());
            RenderTypeLookup .setRenderLayer(CobblestoneSemanticsBlocks.ALL_IN_ONE_GENERATOR.get(), RenderType.cutout());
            ScreenManager.register(CobblestoneSemanticsContainers.COBBLESTONE_MELTER_MENU.get(), CobblestoneMelterScreen::new);
            ScreenManager.register(CobblestoneSemanticsContainers.LAVA_GENERATOR_MENU.get(), LavaGeneratorScreen::new);
            ScreenManager.register(CobblestoneSemanticsContainers.ALL_IN_ONE_GENERATOR_MENU.get(), AllInOneGeneratorScreen::new);
            ScreenManager.register(CobblestoneSemanticsContainers.COBBLESTONE_BAG_MENU.get(), CobblestoneBagScreen::new);
            ScreenManager.register(CobblestoneSemanticsContainers.COBBLESTONE_INFUSED_OBSIDIAN_BAG_MENU.get(), CobblestoneInfusedObsidianBagScreen::new);
        });
    }
}
