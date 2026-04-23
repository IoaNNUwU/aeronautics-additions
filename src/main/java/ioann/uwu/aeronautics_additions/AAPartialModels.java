package ioann.uwu.aeronautics_additions;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.resources.ResourceLocation;

public class AAPartialModels {

    public static final PartialModel TURBINE_BLADES = PartialModel.of(ResourceLocation.tryBuild(
            AeronauticsAdditions.MOD_ID,
            "block/turbine/turbine_blades"
    ));

    public static final PartialModel TURBINE_BLADES_COUNTER = PartialModel.of(ResourceLocation.tryBuild(
            AeronauticsAdditions.MOD_ID,
            "block/turbine/turbine_blades_counter"
    ));

    public static final PartialModel REDSTONE_CABLE = PartialModel.of(ResourceLocation.tryBuild(
            AeronauticsAdditions.MOD_ID,
            "block/redstone_cable_connector/cable"
    ));

    public static final PartialModel REDSTONE_CABLE_KNOT = PartialModel.of(ResourceLocation.tryBuild(
            AeronauticsAdditions.MOD_ID,
            "block/redstone_cable_connector/knot"
    ));

    public static final PartialModel REDSTONE_HANDLE_KNOT = PartialModel.of(ResourceLocation.tryBuild(
            AeronauticsAdditions.MOD_ID,
            "block/redstone_cable_connector/handle_knot"
    ));

    public static void init() {}
}
