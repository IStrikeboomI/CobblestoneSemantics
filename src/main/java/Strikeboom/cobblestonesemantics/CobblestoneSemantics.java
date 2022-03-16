package Strikeboom.cobblestonesemantics;

import Strikeboom.cobblestonesemantics.client.setup.ClientSetup;
import Strikeboom.cobblestonesemantics.init.*;
import com.google.common.collect.Lists;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.TierSortingRegistry;
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

    public static final CreativeModeTab CREATIVE_MODE_TAB = new CreativeModeTab(CobblestoneSemantics.MOD_ID) {
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
        CobblestoneSemanticsMenus.MENUS.register(modbus);
        CobblestoneSemanticsBlockEntities.BLOCK_ENTITIES.register(modbus);
        CobblestoneSemanticsCustomRecipes.RECIPES.register(modbus);

        modbus.addListener(this::init);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modbus.addListener(ClientSetup::init));

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CobblestoneSemanticsConfig.COMMON);
    }
    private void init(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            TierSortingRegistry.registerTier(CobblestoneSemanticsItems.COBBLESTONE_INFUSED_OBSIDIAN_TIER,new ResourceLocation(CobblestoneSemantics.MOD_ID,"cobblestone_infused_obsidian"),
                    Lists.newArrayList(Tiers.WOOD,Tiers.GOLD,Tiers.IRON,Tiers.DIAMOND,Tiers.STONE),Lists.newArrayList(Tiers.NETHERITE));
        });
    }
}
