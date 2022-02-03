package Strikeboom.cobblestonesemantics;

import Strikeboom.cobblestonesemantics.init.*;
import Strikeboom.cobblestonesemantics.setup.ClientSetup;
import Strikeboom.cobblestonesemantics.setup.ModSetup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CobblestoneSemantics.MOD_ID)
public class CobblestoneSemantics
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "cobblestonesemantics";

    public CobblestoneSemantics() {
        IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();

        CobblestoneSemanticsBlocks.BLOCKS.register(modbus);
        CobblestoneSemanticsItems.ITEMS.register(modbus);
        CobblestoneSemanticsFluids.FLUIDS.register(modbus);
        CobblestoneSemanticsMenus.MENUS.register(modbus);
        CobblestoneSemanticsBlockEntities.BLOCK_ENTITIES.register(modbus);

        modbus.addListener(ModSetup::init);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modbus.addListener(ClientSetup::init));

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CobblestoneSemanticsConfig.COMMON);
    }

}
