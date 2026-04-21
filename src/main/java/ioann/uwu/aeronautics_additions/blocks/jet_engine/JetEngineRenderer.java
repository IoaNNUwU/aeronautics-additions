package ioann.uwu.aeronautics_additions.blocks.jet_engine;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.eriksonn.aeronautics.content.blocks.propeller.small.SimplePropellerRenderer;
import dev.eriksonn.aeronautics.index.AeroPartialModels;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

import static dev.eriksonn.aeronautics.content.blocks.propeller.small.BasePropellerBlock.REVERSED;

public class JetEngineRenderer extends SimplePropellerRenderer<JetEngineBlockEntity> {

    public JetEngineRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public PartialModel getCurrentModel(JetEngineBlockEntity be) {
        return be.getBlockState().getValue(REVERSED) ? AeroPartialModels.ANDESITE_PROPELLER_REVERSED : AeroPartialModels.ANDESITE_PROPELLER;
    }

    @Override
    public float getAngle(float partialTicks, Direction dir, JetEngineBlockEntity be) {
        return super.getAngle(partialTicks, dir, be) + getRotationOffsetForPosition(be, be.getBlockPos(), dir.getAxis());
    }
}
