package Strikeboom.cobblestonesemantics;

import Strikeboom.cobblestonesemantics.client.setup.ClientSetup;
import Strikeboom.cobblestonesemantics.init.*;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
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

    public static final ItemGroup CREATIVE_MODE_TAB = new ItemGroup(CobblestoneSemantics.MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(CobblestoneSemanticsBlocks.COBBLESTONE_GENERATOR_1.get());
        }
    };

    public CobblestoneSemantics() {
        IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();

        CobblestoneSemanticsBlocks.BLOCKS.register(modbus);
        CobblestoneSemanticsItems.ITEMS.register(modbus);
        CobblestoneSemanticsFluids.FLUIDS.register(modbus);
        CobblestoneSemanticsContainers.CONTAINERS.register(modbus);
        CobblestoneSemanticsTileEntities.BLOCK_ENTITIES.register(modbus);
        CobblestoneSemanticsCustomRecipes.RECIPES.register(modbus);

        modbus.addListener(this::init);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modbus.addListener(ClientSetup::init));

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CobblestoneSemanticsConfig.COMMON);
    }
    private void init(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
        });
    }
}
