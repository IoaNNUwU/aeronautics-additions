package ioann.uwu.aeronautics_additions.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class AAConfig {

    public static final ModConfigSpec CONFIG_SPEC;
    public static final AAConfig CONFIG;

    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final FloatConfigValue TURBINE_THRUST =
            new FloatConfigValue("turbine_thrust", 4.0f, BUILDER.gameRestart());

    public static final FloatConfigValue TURBINE_AIRFLOW =
            new FloatConfigValue("turbine_airflow", 0.5f, BUILDER.gameRestart());

    public static final FloatConfigValue TURBINE_RADIUS =
            new FloatConfigValue("turbine_radius", 2.0f, BUILDER.gameRestart());

    static {
        var pair = BUILDER.configure(AAConfig::new);
        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }

    private AAConfig(ModConfigSpec.Builder builder) {
    }
}
