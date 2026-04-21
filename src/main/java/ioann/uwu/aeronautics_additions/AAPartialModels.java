package ioann.uwu.aeronautics_additions;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.resources.ResourceLocation;

public class AAPartialModels {

    public static final PartialModel TURBINE_BLADES = PartialModel.of(ResourceLocation.tryBuild(
            AeronauticsAdditions.MOD_ID,
            "block/jet_engine/turbine_blades"
    ));

    public static final PartialModel TURBINE_BLADES_COUNTER = PartialModel.of(ResourceLocation.tryBuild(
            AeronauticsAdditions.MOD_ID,
            "block/jet_engine/turbine_blades_counter"
    ));

    public static void init() {}
}
