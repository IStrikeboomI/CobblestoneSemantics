package Strikeboom.cobblestonesemantics.init;

import net.minecraftforge.common.ForgeConfigSpec;

public class CobblestoneSemanticsConfig {
    public static final ForgeConfigSpec COMMON;

    public static final ForgeConfigSpec.IntValue COBBLESTONE_MELTER_DELAY;
    public static final ForgeConfigSpec.IntValue COBBLESTONE_MELTER_LAVA_PER_COBBLESTONE;

    public static final ForgeConfigSpec.IntValue LAVA_GENERATOR_DELAY;
    public static final ForgeConfigSpec.IntValue LAVA_GENERATOR_POWER_PER_LAVA_BUCKET;

    static {
        final ForgeConfigSpec.Builder common = new ForgeConfigSpec.Builder();
        common.push("Cobblestone Melter");
        COBBLESTONE_MELTER_DELAY = common.comment("Cobblestone Melter Delay").defineInRange("cobblestone_melter_delay",200,2,10000);
        COBBLESTONE_MELTER_LAVA_PER_COBBLESTONE = common.comment("Lava Per Cobblestone").defineInRange("cobblestone_melter_lava_per_cobblestone",500,1,10000);
        common.pop();

        common.push("Lava Generator");
        LAVA_GENERATOR_DELAY = common.comment("Lava Generator Delay").defineInRange("lava_generator_delay",200,2,10000);
        LAVA_GENERATOR_POWER_PER_LAVA_BUCKET = common.comment("Power Per Lava Bucket").defineInRange("lava_generator_power_per_lava_bucket",100000,1,100000);
        common.pop();

        COMMON = common.build();
    }
}
