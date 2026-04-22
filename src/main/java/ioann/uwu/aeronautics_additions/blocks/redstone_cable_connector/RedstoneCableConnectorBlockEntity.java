package ioann.uwu.aeronautics_additions.blocks.redstone_cable_connector;

import com.google.common.collect.Sets;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import dev.ryanhcode.sable.api.sublevel.ServerSubLevelContainer;
import dev.ryanhcode.sable.api.sublevel.SubLevelContainer;
import dev.ryanhcode.sable.sublevel.SubLevel;
import dev.simulated_team.simulated.content.blocks.rope.RopeStrandHolderBehavior;
import dev.simulated_team.simulated.content.blocks.rope.RopeStrandHolderBlockEntity;
import dev.simulated_team.simulated.content.blocks.rope.rope_connector.RopeConnectorBlock;
import dev.simulated_team.simulated.content.blocks.rope.rope_connector.RopeConnectorBlockEntity;
import dev.simulated_team.simulated.content.blocks.rope.strand.client.ClientRopeStrand;
import dev.simulated_team.simulated.content.blocks.rope.strand.server.RopeAttachment;
import dev.simulated_team.simulated.content.blocks.rope.strand.server.RopeAttachmentPoint;
import dev.simulated_team.simulated.content.blocks.rope.strand.server.ServerRopeStrand;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Set;

public class RedstoneCableConnectorBlockEntity extends SmartBlockEntity implements RopeStrandHolderBlockEntity {

    public static final double RENDER_BOUNDING_BOX_INFLATION = 3.0;

    private RopeStrandHolderBehavior ropeHolder;

    public RedstoneCableConnectorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public RopeStrandHolderBehavior getRopeHolder() {
        return this.ropeHolder;
    }

    @Override
    public void addBehaviours(final List<BlockEntityBehaviour> behaviours) {
        behaviours.add(this.ropeHolder = new RopeStrandHolderBehavior(this));
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level.isClientSide) {
            this.invalidateRenderBoundingBox();
        }
    }

    @Override
    public AABB getRenderBoundingBox() {
        final ClientRopeStrand rope = this.ropeHolder.getClientStrand();
        if (rope != null && this.ropeHolder.ownsRope()) {
            final AABB bounds = rope.getBounds();

            if (bounds == null) {
                return super.getRenderBoundingBox();
            }

            return bounds.inflate(RENDER_BOUNDING_BOX_INFLATION);
        } else {
            return super.getRenderBoundingBox();
        }
    }

    @Override
    public RopeStrandHolderBehavior getBehavior() {
        return this.ropeHolder;
    }

    @Override
    public Vec3 getAttachmentPoint(final BlockPos pos, final BlockState state) {
        final Direction facing = state.getValue(RopeConnectorBlock.FACING);
        final double offset = -3.0 / 16.0;

        return pos.getCenter().add(facing.getStepX() * offset, facing.getStepY() * offset, facing.getStepZ() * offset);
    }

    public Vec3 getVisualAttachmentPoint(final BlockPos pos, final BlockState state) {
        final Direction facing = state.getValue(RopeConnectorBlock.FACING);
        final double offset = -4.0 / 16.0;

        return pos.getCenter().add(facing.getStepX() * offset, facing.getStepY() * offset, facing.getStepZ() * offset);
    }

    public void updateOtherSide(int newSignalLevel, Block block, ServerLevel level) {

        if (true) {
            return;
        }

        var thisRopeHolder = this.getRopeHolder();

        ServerRopeStrand attachedStrand = thisRopeHolder.getAttachedStrand();
        if (attachedStrand == null) {
            return;
        }

        RopeAttachment otherSide;
        if (thisRopeHolder.ownsRope()) {
            return;
            // otherSide = attachedStrand.getAttachment(RopeAttachmentPoint.END);
        } else {
            if (true) {
                return;
            }
            // otherSide = attachedStrand.getAttachment(RopeAttachmentPoint.START);
        }
        if (otherSide == null) {
            return;
        }

        ServerSubLevelContainer subLevelContainer = SubLevelContainer.getContainer(level);
        if (subLevelContainer == null) {
            return;
        }

        SubLevel subLevel = subLevelContainer.getSubLevel(otherSide.subLevelID());

        Level levelAccess;
        if (subLevel == null) {
            levelAccess = level;
        } else {
            levelAccess = subLevel.getLevel();
        }

        BlockState existingState = levelAccess.getBlockState(otherSide.blockAttachment());

        BlockState newBlockState = existingState.setValue(RedstoneCableConnectorBlock.POWER, newSignalLevel);

        levelAccess.setBlock(otherSide.blockAttachment(), newBlockState, Block.UPDATE_ALL);

        Set<BlockPos> set = Sets.newHashSet();
        // set.add(otherSide.blockAttachment());

        for (Direction direction : Direction.values()) {
            set.add(otherSide.blockAttachment().relative(direction));
        }

        for (BlockPos blockPos : set) {
            levelAccess.updateNeighborsAt(blockPos, block);
        }

        levelAccess.updateNeighborsAt(otherSide.blockAttachment(), newBlockState.getBlock());
    }

    // TODO: modify getBestNeighborSignal to use additional remote BlockPos from Block Entity.
}
