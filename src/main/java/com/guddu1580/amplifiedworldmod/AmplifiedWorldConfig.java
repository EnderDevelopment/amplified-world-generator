package com.guddu1580.amplifiedworldmod;

import net.minecraftforge.common.ForgeConfigSpec;

public
class AmplifiedWorldConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Double> AMPLIFICATION_FACTOR;

    static {
        BUILDER.push("Amplified World Settings");

        AMPLIFICATION_FACTOR = BUILDER.comment("The factor by which the world will be amplified")
        .define("Amplification Factor", 2.0);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
