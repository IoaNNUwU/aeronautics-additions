package ioann.uwu.aeronautics_additions.blocks.redstone_cable_connector;

import com.mojang.serialization.MapCodec;
import com.simibubi.create.api.contraption.BlockMovementChecks;
import com.simibubi.create.content.redstone.link.RedstoneLinkBlock;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.impl.contraption.BlockMovementChecksImpl;
import dev.ryanhcode.sable.api.block.BlockSubLevelAssemblyListener;
import dev.ryanhcode.sable.api.block.BlockSubLevelCollisionShape;
import dev.simulated_team.simulated.content.blocks.rope.RopeHolderBlock;
import dev.simulated_team.simulated.content.blocks.rope.rope_connector.RopeConnectorBlock;
import dev.simulated_team.simulated.content.blocks.util.AbstractDirectionalAxisBlock;
import dev.simulated_team.simulated.index.SimBlockShapes;
import dev.simulated_team.simulated.index.SimTags;
import dev.simulated_team.simulated.util.DirectionalAxisShaper;
import ioann.uwu.aeronautics_additions.AABlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class RedstoneCableConnectorBlock extends AbstractDirectionalAxisBlock implements
        IBE<RedstoneCableConnectorBlockEntity>,
        RopeHolderBlock<RedstoneCableConnectorBlockEntity>,
        BlockSubLevelAssemblyListener,
        BlockSubLevelCollisionShape
{

    public static final BooleanProperty SOURCE = BooleanProperty.create("source");

    static {
        BlockMovementChecksImpl.registerAttachedCheck(
                (state, world, pos, direction) -> {
                    final BlockState relativeState = world.getBlockState(pos.relative(direction));
                    if (state.getBlock() instanceof RedstoneCableConnectorBlock && state.getValue(RopeConnectorBlock.FACING) == direction.getOpposite()) {
                        return BlockMovementChecks.CheckResult.SUCCESS;
                    }
                    if (relativeState.getBlock() instanceof RedstoneCableConnectorBlock && relativeState.getValue(RopeConnectorBlock.FACING) == direction) {
                        return BlockMovementChecks.CheckResult.SUCCESS;
                    }
                    return BlockMovementChecks.CheckResult.PASS;
                }
        );
    }

    public RedstoneCableConnectorBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(SOURCE, false));
    }

    public static final MapCodec<RedstoneCableConnectorBlock> CODEC = simpleCodec(RedstoneCableConnectorBlock::new);

    @Override
    protected MapCodec<? extends DirectionalBlock> codec() {
        return CODEC;
    }

    private static final DirectionalAxisShaper SHAPE = DirectionalAxisShaper.make(
            new SimBlockShapes.Builder(box(6, 2, 3, 10, 6, 13))
                    .add(box(0, 0, 0, 16, 2, 16)).build()
    );

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE.get(state.getValue(FACING), state.getValue(AXIS_ALONG_FIRST_COORDINATE));
    }

    private static final DirectionalAxisShaper PHYSICS_COLLIDER = DirectionalAxisShaper.make(SimBlockShapes.ROPE_CONNECTOR_COLLIDER);

    @Override
    public VoxelShape getSubLevelCollisionShape(BlockGetter blockGetter, BlockState state) {
        return PHYSICS_COLLIDER.get(state.getValue(FACING), state.getValue(AXIS_ALONG_FIRST_COORDINATE));
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        IBE.onRemove(state, level, pos, newState);
    }

    @Override
    public Class<RedstoneCableConnectorBlockEntity> getBlockEntityClass() {
        return RedstoneCableConnectorBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends RedstoneCableConnectorBlockEntity> getBlockEntityType() {
        return AABlockEntityTypes.REDSTONE_CABLE_CONNECTOR.get();
    }

    @Override
    protected Direction getFacingForPlacement(BlockPlaceContext context) {
        return context.getClickedFace();
    }

    @Override
    protected boolean getAxisAlignmentForPlacement(BlockPlaceContext context) {
        return context.getHorizontalDirection().getAxis() != Direction.Axis.X;
    }

    @Override
    protected ItemInteractionResult useItemOn(final ItemStack stack, final BlockState state, final Level level, final BlockPos pos, final Player player, final InteractionHand hand, final BlockHitResult hitResult) {
        if (!level.isClientSide() && stack.is(SimTags.Items.DESTROYS_ROPE)) {
            return RopeHolderBlock.shearRope(this, level, pos, (ServerPlayer) player);
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(SOURCE);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block p_60512_, BlockPos p_60513_, boolean p_60514_) {

        if (level instanceof ServerLevel serverLevel) {

            // TODO

            /*
            int bestSignal = calculateTargetStrength(level, pos);

            if (level.getBlockEntity(pos) instanceof RedstoneCableConnectorBlockEntity conn) {
                conn.updateOtherSide(bestSignal, this, serverLevel);
            }
             */
        }
    }
}
