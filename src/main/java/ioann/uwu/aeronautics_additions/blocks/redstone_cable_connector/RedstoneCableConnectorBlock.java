package ioann.uwu.aeronautics_additions.blocks.redstone_cable_connector;

import dev.simulated_team.simulated.content.blocks.rope.rope_connector.RopeConnectorBlock;
import dev.simulated_team.simulated.content.blocks.rope.rope_connector.RopeConnectorBlockEntity;
import dev.simulated_team.simulated.index.SimBlockShapes;
import dev.simulated_team.simulated.util.DirectionalAxisShaper;
import ioann.uwu.aeronautics_additions.AABlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class RedstoneCableConnectorBlock extends RopeConnectorBlock {

    public static final IntegerProperty POWER = RedStoneWireBlock.POWER;

    private boolean shouldSignal = true;

    public RedstoneCableConnectorBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(POWER, 0));
    }

    private static final DirectionalAxisShaper SHAPE = DirectionalAxisShaper.make(
            new SimBlockShapes.Builder(box(6, 2, 3, 10, 6, 13))
                    .add(box(0, 0, 0, 16, 2, 16)).build()
    );

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE.get(state.getValue(FACING), state.getValue(AXIS_ALONG_FIRST_COORDINATE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(POWER);
    }

    @Override
    public BlockEntityType<? extends RopeConnectorBlockEntity> getBlockEntityType() {
        return AABlockEntityTypes.REDSTONE_CABLE_CONNECTOR.get();
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block p_60512_, BlockPos p_60513_, boolean p_60514_) {

        if (level instanceof ServerLevel serverLevel) {

            int bestSignal = calculateTargetStrength(level, pos);

            if (level.getBlockEntity(pos) instanceof RedstoneCableConnectorBlockEntity conn) {
                conn.updateOtherSide(bestSignal, this, serverLevel);
            }
        }
    }

    private int calculateTargetStrength(Level level, BlockPos blockPos) {
        this.shouldSignal = false;
        int i = level.getBestNeighborSignal(blockPos);
        this.shouldSignal = true;

        int j = 0;
        if (i < 15) {

            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockPos blockpos = blockPos.relative(direction);
                BlockState blockstate = level.getBlockState(blockpos);
                j = Math.max(j, this.getWireSignal(blockstate));
                BlockPos blockpos1 = blockpos.above();

                if (blockstate.isRedstoneConductor(level, blockpos) && !level.getBlockState(blockpos1).isRedstoneConductor(level, blockpos1)) {
                    j = Math.max(j, this.getWireSignal(level.getBlockState(blockpos.above())));
                } else if (!blockstate.isRedstoneConductor(level, blockpos)) {
                    j = Math.max(j, this.getWireSignal(level.getBlockState(blockpos.below())));
                }
            }
        }

        return Math.max(i, j - 1);
    }

    private int getWireSignal(BlockState blockState) {
        return blockState.is(this) ? blockState.getValue(POWER) : 0;
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        return true;
    }

    @Override
    protected int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {

        if (this.shouldSignal) {
            return state.getValue(POWER);
        }
        return 0;
    }

    @Override
    protected int getDirectSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return !this.shouldSignal ? 0 : state.getSignal(level, pos, direction);
    }

    @Override
    protected boolean isSignalSource(BlockState state) {
        return this.shouldSignal;
    }
}
