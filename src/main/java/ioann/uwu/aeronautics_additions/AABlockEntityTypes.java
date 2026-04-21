package ioann.uwu.aeronautics_additions;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import ioann.uwu.aeronautics_additions.blocks.jet_engine.TurbineBlockEntity;
import ioann.uwu.aeronautics_additions.blocks.jet_engine.TurbineRenderer;
import ioann.uwu.aeronautics_additions.blocks.jet_engine.TurbineVisual;

public class AABlockEntityTypes {

    private static final CreateRegistrate REGISTRATE = AeronauticsAdditions.getRegistrate();

    public static final BlockEntityEntry<TurbineBlockEntity> JET_ENGINE = REGISTRATE
            .blockEntity("turbine", TurbineBlockEntity::new)
            .visual(() -> TurbineVisual::new)
            .validBlocks(AABlocks.JET_ENGINE)
            .renderer(() -> TurbineRenderer::new)
            .register();

    public static void init() {}
}
