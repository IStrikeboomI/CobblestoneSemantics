package Strikeboom.cobblestonesemantics.init;

import net.neoforged.neoforge.common.ModConfigSpec;

public class CobblestoneSemanticsConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue COBBLESTONE_MELTER_DELAY = BUILDER.push("melter").translation("cobblestonesemantics.configuration.melter.delay").defineInRange("cobblestone_melter_delay",200,2,10000);
    public static final ModConfigSpec.IntValue COBBLESTONE_MELTER_LAVA_PER_COBBLESTONE = BUILDER.translation("cobblestonesemantics.configuration.melter.lava").defineInRange("cobblestone_melter_lava_per_cobblestone",500,1,10000);;
    public static final ModConfigSpec.IntValue COBBLESTONE_MELTER_POWER_USAGE = BUILDER.translation("cobblestonesemantics.configuration.melter.power").defineInRange("cobblestone_melter_power_usage",10000,1,100000);

    public static final ModConfigSpec.IntValue LAVA_GENERATOR_DELAY = BUILDER.pop().push("generator").translation("cobblestonesemantics.configuration.generator.delay").defineInRange("lava_generator_delay",200,2,10000);
    public static final ModConfigSpec.IntValue LAVA_GENERATOR_POWER_PER_LAVA_BUCKET = BUILDER.translation("cobblestonesemantics.configuration.generator.power").defineInRange("lava_generator_power_per_lava_bucket",100000,1,100000);;

}
