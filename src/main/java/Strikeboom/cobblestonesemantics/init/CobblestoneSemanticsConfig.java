package Strikeboom.cobblestonesemantics.init;

import Strikeboom.cobblestonesemantics.CobblestoneSemantics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.stream.Collectors;

public class CobblestoneSemanticsConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue COBBLESTONE_MELTER_DELAY = BUILDER.push("Cobblestone Melter").comment("Cobblestone Melter Delay").defineInRange("cobblestone_melter_delay",200,2,10000);
    public static final ModConfigSpec.IntValue COBBLESTONE_MELTER_LAVA_PER_COBBLESTONE = BUILDER.comment("Lava Per Cobblestone").defineInRange("cobblestone_melter_lava_per_cobblestone",500,1,10000);;

    public static final ModConfigSpec.IntValue LAVA_GENERATOR_DELAY = BUILDER.pop().push("Lava Generator").comment("Lava Generator Delay").defineInRange("lava_generator_delay",200,2,10000);
    public static final ModConfigSpec.IntValue LAVA_GENERATOR_POWER_PER_LAVA_BUCKET = BUILDER.comment("Power Per Lava Bucket").defineInRange("lava_generator_power_per_lava_bucket",100000,1,100000);;

}
