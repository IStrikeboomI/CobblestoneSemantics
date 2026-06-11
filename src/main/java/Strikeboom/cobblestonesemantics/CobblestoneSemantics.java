package Strikeboom.cobblestonesemantics;

import Strikeboom.cobblestonesemantics.init.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CobblestoneSemantics.MOD_ID)
public class CobblestoneSemantics
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "cobblestonesemantics";

    public CobblestoneSemantics(IEventBus modbus, ModContainer modContainer) {
        CobblestoneSemanticsCreativeModeTabs.CREATIVE_MODE_TABS.register(modbus);
        CobblestoneSemanticsBlocks.BLOCKS.register(modbus);
        CobblestoneSemanticsItems.ITEMS.register(modbus);
        CobblestoneSemanticsFluids.FLUID_TYPES.register(modbus);
        CobblestoneSemanticsFluids.FLUIDS.register(modbus);
        CobblestoneSemanticsMenus.MENUS.register(modbus);
        CobblestoneSemanticsBlockEntities.BLOCK_ENTITIES.register(modbus);

        //modbus.addListener(this::init);

        modContainer.registerConfig(ModConfig.Type.COMMON, CobblestoneSemanticsConfig.BUILDER.build());

    }
}
