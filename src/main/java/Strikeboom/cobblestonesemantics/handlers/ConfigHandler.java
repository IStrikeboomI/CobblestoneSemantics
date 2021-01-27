package Strikeboom.cobblestonesemantics.handlers;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = CobblestoneSemantics.MOD_ID)
@Config.LangKey(CobblestoneSemantics.MOD_ID + ".config.name")
public class ConfigHandler {
    @Config.LangKey(CobblestoneSemantics.MOD_ID + ".config.blocks")
    public static final Blocks BLOCKS = new Blocks();
    public static class Blocks {
        @Config.LangKey(CobblestoneSemantics.MOD_ID + ".config.blocks.melter")
        public final Melter MELTER = new Melter();
        public static class Melter {
            @Config.LangKey(CobblestoneSemantics.MOD_ID + ".config.blocks.melter.melterdelay")
            @Config.RangeInt(min = 2, max = 10000)
            public int melterDelay = 200;
            @Config.LangKey(CobblestoneSemantics.MOD_ID + ".config.blocks.melter.lavapercobble")
            @Config.RangeInt(min = 1, max = 10000)
            public int lavaPerCobblestone = 500;
        }
        @Config.LangKey(CobblestoneSemantics.MOD_ID + ".config.blocks.lavagenerator")
        public final LavaGenerator LAVA_GENERATOR = new LavaGenerator();
        public static class LavaGenerator {
            @Config.LangKey(CobblestoneSemantics.MOD_ID + ".config.blocks.lavagenerator.powerperlavabucket")
            @Config.RangeInt(min = 1,max = 1000000)
            public int powerPerLavaBucket = 100000;

            @Config.LangKey(CobblestoneSemantics.MOD_ID + ".config.blocks.lavagenerator.generatordelay")
            @Config.RangeInt(min = 1,max = 10000)
            public int generatorDelay = 200;
        }
    }
    @Mod.EventBusSubscriber(modid = CobblestoneSemantics.MOD_ID)
    public static class ConfigEvent {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(CobblestoneSemantics.MOD_ID)) {
                ConfigManager.sync(CobblestoneSemantics.MOD_ID, Config.Type.INSTANCE);
            }
        }
    }
}
