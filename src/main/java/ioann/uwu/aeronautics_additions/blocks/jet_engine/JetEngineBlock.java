package ioann.uwu.aeronautics_additions.blocks.jet_engine;

import dev.eriksonn.aeronautics.content.blocks.propeller.small.BasePropellerBlock;
import dev.eriksonn.aeronautics.content.blocks.propeller.small.BasePropellerBlockEntity;
import dev.eriksonn.aeronautics.index.AeroBlockShapes;
import ioann.uwu.aeronautics_additions.AABlockEntityTypes;
import net.createmod.catnip.math.VoxelShaper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class JetEngineBlock extends BasePropellerBlock {

    public JetEngineBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntityType<? extends BasePropellerBlockEntity> getBlockEntityType() {
        return AABlockEntityTypes.JET_ENGINE.get();
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return JET_ENGINE.get(pState.getValue(FACING));
    }

    // TODO: Fix black shadow appearing randomly
    private static final VoxelShaper JET_ENGINE = new AeroBlockShapes.Builder(box(0, 0, 0, 16, 16, 16)).forDirectional();

    private static final VoxelShaper JET_ENGINE1 = new AeroBlockShapes.Builder(box(2, 8, 2, 14, 18, 14))
            .add(box(3, 8, 1, 13, 18, 15))
            .add(box(1, 8, 3, 15, 18, 13))
            .add(box(5, 8, 0, 11, 18, 16))
            .add(box(0, 8, 5, 16, 18, 11))

            .add(box(5, 1, 1, 11, 8, 15))
            .add(box(1, 1, 5, 15, 8, 11))
            .add(box(3, 1, 2, 13, 8, 14))
            .add(box(2, 1, 3, 14, 8, 13))

            .add(box(3, -2, 3, 13, 1, 13))
            .add(box(2, -2, 5, 14, 1, 11))
            .add(box(5, -2, 2, 11, 1, 14))

            .forDirectional();
}
