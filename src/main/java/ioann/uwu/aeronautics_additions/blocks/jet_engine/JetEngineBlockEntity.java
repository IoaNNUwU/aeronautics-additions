package ioann.uwu.aeronautics_additions.blocks.jet_engine;

import dev.eriksonn.aeronautics.content.blocks.propeller.small.BasePropellerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class JetEngineBlockEntity extends BasePropellerBlockEntity {

    public JetEngineBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    public double getConfigThrust() {
        return 3.0f; // default 1.0
    }

    @Override
    public double getConfigAirflow() {
        return 1.0f; // default 0.1
    }

    @Override
    public float getRadius() {
        return 2.0f;
    }

    @Override
    public float getOffset() {
        return 3 / 16f;
    }
}
