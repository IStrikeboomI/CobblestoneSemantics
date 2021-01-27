package Strikeboom.cobblestonesemantics;

import Strikeboom.cobblestonesemantics.guis.tileentity.TileEntityAllInOneGenerator;
import Strikeboom.cobblestonesemantics.guis.tileentity.TileEntityCobbleGenerator;
import Strikeboom.cobblestonesemantics.guis.tileentity.TileEntityCobblestoneMelter;
import Strikeboom.cobblestonesemantics.guis.tileentity.TileEntityLavaGenerator;
import Strikeboom.cobblestonesemantics.handlers.ConfigHandler;
import Strikeboom.cobblestonesemantics.handlers.GuiHandler;
import Strikeboom.cobblestonesemantics.handlers.OreDictHandler;
import Strikeboom.cobblestonesemantics.handlers.proxy.IProxy;
import Strikeboom.cobblestonesemantics.init.ModBlocks;
import Strikeboom.cobblestonesemantics.init.ModFluids;
import Strikeboom.cobblestonesemantics.init.ModItems;
import Strikeboom.cobblestonesemantics.integrations.tconstruct.CobblestoneInfusedObsidianToolMaterial;
import Strikeboom.cobblestonesemantics.integrations.tconstruct.MelterRecipes;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Logger;

@Mod(modid = CobblestoneSemantics.MOD_ID,name = "Cobblestone Semantics",version = "1.0",acceptedMinecraftVersions = "1.12.2")
public class CobblestoneSemantics {

    public static final String MOD_ID = "cobblestonesemantics";

    public static Logger logger;

    public static final CreativeTabs MOD_TAB = new CreativeTabs(MOD_ID) {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ModBlocks.cobbleGenerator1);
        }
    };

    @Mod.Instance
    public static CobblestoneSemantics instance;

    @SidedProxy(clientSide = "Strikeboom.cobblestonesemantics.handlers.proxy.ClientProxy",serverSide = "Strikeboom.cobblestonesemantics.handlers.proxy.ServerProxy")
    public static IProxy proxy;

    static {
        FluidRegistry.enableUniversalBucket();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        NetworkRegistry.INSTANCE.registerGuiHandler(instance,new GuiHandler());
        ModBlocks.preInit();
        ModItems.preInit();
        ModFluids.preInit();
        if (Loader.isModLoaded("tconstruct")) CobblestoneInfusedObsidianToolMaterial.preInit();
        proxy.preInit();
        logger.info("preInit");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        GameRegistry.registerTileEntity(TileEntityCobbleGenerator.class,new ResourceLocation(MOD_ID,"tileentitycobblestonegenerator"));
        GameRegistry.registerTileEntity(TileEntityCobblestoneMelter.class,new ResourceLocation(MOD_ID,"tileentitycobblestonemelter"));
        GameRegistry.registerTileEntity(TileEntityLavaGenerator.class,new ResourceLocation(MOD_ID,"tileentitylavagenerator"));
        GameRegistry.registerTileEntity(TileEntityAllInOneGenerator.class,new ResourceLocation(MOD_ID,"tileentityallinonegenerator"));
        MinecraftForge.EVENT_BUS.register(ConfigHandler.ConfigEvent.class);
        if (Loader.isModLoaded("tconstruct")) MelterRecipes.init();
        proxy.init();
        OreDictHandler.init();
        logger.info("init");
    }
}
