package ioann.uwu.aeronautics_additions.blocks.turbine;

import dev.eriksonn.aeronautics.content.blocks.propeller.small.BasePropellerBlockEntity;
import ioann.uwu.aeronautics_additions.config.AAConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TurbineBlockEntity extends BasePropellerBlockEntity {

    public TurbineBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    public double getConfigThrust() {
        return AAConfig.TURBINE_THRUST.get();
    }

    @Override
    public double getConfigAirflow() {
        return AAConfig.TURBINE_AIRFLOW.get();
    }

    @Override
    public float getRadius() {
        return AAConfig.TURBINE_RADIUS.get();
    }

    @Override
    public float getOffset() {
        return 3 / 16f;
    }
}
