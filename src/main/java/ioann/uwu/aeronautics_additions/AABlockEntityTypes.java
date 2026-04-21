package ioann.uwu.aeronautics_additions;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import ioann.uwu.aeronautics_additions.blocks.jet_engine.JetEngineBlockEntity;
import ioann.uwu.aeronautics_additions.blocks.jet_engine.JetEngineRenderer;
import ioann.uwu.aeronautics_additions.blocks.jet_engine.JetEngineVisual;

public class AABlockEntityTypes {

    private static final CreateRegistrate REGISTRATE = AeronauticsAdditions.getRegistrate();

    public static final BlockEntityEntry<JetEngineBlockEntity> JET_ENGINE = REGISTRATE
            .blockEntity("jet_engine", JetEngineBlockEntity::new)
            .visual(() -> JetEngineVisual::new)
            .validBlocks(AABlocks.JET_ENGINE)
            .renderer(() -> JetEngineRenderer::new)
            .register();

    public static void init() {}
}
