package Strikeboom.cobblestonesemantics.setup;

import Strikeboom.cobblestonesemantics.guis.screens.*;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsBlocks;
import Strikeboom.cobblestonesemantics.init.CobblestoneSemanticsMenus;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {
    public static void init(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_1.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_2.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_3.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_4.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_5.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_6.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_7.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_8.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_9.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_10.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(CobblestoneSemanticsBlocks.ALL_IN_ONE_GENERATOR.get(), RenderType.cutout());
            MenuScreens.register(CobblestoneSemanticsMenus.COBBLESTONE_MELTER_MENU.get(), CobblestoneMelterScreen::new);
            MenuScreens.register(CobblestoneSemanticsMenus.LAVA_GENERATOR_MENU.get(), LavaGeneratorScreen::new);
            MenuScreens.register(CobblestoneSemanticsMenus.ALL_IN_ONE_GENERATOR_MENU.get(), AllInOneGeneratorScreen::new);
            MenuScreens.register(CobblestoneSemanticsMenus.COBBLESTONE_BAG_MENU.get(), CobblestoneBagScreen::new);
            MenuScreens.register(CobblestoneSemanticsMenus.COBBLESTONE_INFUSED_OBSIDIAN_BAG_MENU.get(), CobblestoneInfusedObsidianBagScreen::new);

        });
    }
}
