package ioann.uwu.aeronautics_additions;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import ioann.uwu.aeronautics_additions.blocks.turbine.TurbineBlockEntity;
import ioann.uwu.aeronautics_additions.blocks.turbine.TurbineRenderer;
import ioann.uwu.aeronautics_additions.blocks.turbine.TurbineVisual;
import ioann.uwu.aeronautics_additions.blocks.redstone_cable_connector.RedstoneCableConnectorBlockEntity;
import ioann.uwu.aeronautics_additions.blocks.redstone_cable_connector.RedstoneCableConnectorRenderer;

public class AABlockEntityTypes {

    private static final CreateRegistrate REGISTRATE = AeronauticsAdditions.getRegistrate();

    public static final BlockEntityEntry<TurbineBlockEntity> TURBINE = REGISTRATE
            .blockEntity("turbine", TurbineBlockEntity::new)
            .visual(() -> TurbineVisual::new)
            .validBlocks(AABlocks.TURBINE)
            .renderer(() -> TurbineRenderer::new)
            .register();

    public static final BlockEntityEntry<RedstoneCableConnectorBlockEntity> REDSTONE_CABLE_CONNECTOR = REGISTRATE
            .blockEntity("redstone_cable_connector", RedstoneCableConnectorBlockEntity::new)
            .validBlocks(AABlocks.REDSTONE_CABLE_CONNECTOR)
            .renderer(() -> RedstoneCableConnectorRenderer::new)
            .register();

    public static void init() {}
}
