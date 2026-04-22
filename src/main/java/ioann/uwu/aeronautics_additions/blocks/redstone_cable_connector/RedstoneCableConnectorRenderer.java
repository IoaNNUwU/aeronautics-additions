package ioann.uwu.aeronautics_additions.blocks.redstone_cable_connector;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import dev.simulated_team.simulated.content.blocks.rope.RopeStrandHolderBehavior;
import dev.simulated_team.simulated.content.blocks.rope.rope_connector.RopeConnectorBlock;
import dev.simulated_team.simulated.content.blocks.rope.strand.client.RopeStrandRenderer;
import dev.simulated_team.simulated.index.SimPartialModels;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class RedstoneCableConnectorRenderer extends SafeBlockEntityRenderer<RedstoneCableConnectorBlockEntity> {

    public RedstoneCableConnectorRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    protected void renderSafe(RedstoneCableConnectorBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay) {

        RopeStrandRenderer.render(blockEntity, blockEntity.getRopeHolder(), partialTicks, poseStack, buffer);

        final RopeStrandHolderBehavior holder = blockEntity.getRopeHolder();

        if ((!holder.isAttached()) && (!blockEntity.isVirtual() || !blockEntity.getRopeHolder().renderAttached)) {
            return;
        }
        final SuperByteBuffer knot = CachedBuffers.partialFacing(SimPartialModels.ROPE_CONNECTOR_KNOT, AllBlocks.ROPE.getDefaultState(), Direction.NORTH);

        final BlockPos blockPos = blockEntity.getBlockPos();
        final BlockState state = blockEntity.getBlockState();

        final Vec3 attachmentPoint = blockEntity.getVisualAttachmentPoint(blockPos, state);
        final Direction facing = state.getValue(RopeConnectorBlock.FACING);

        final SuperByteBuffer knotBuffer = knot.light(light);

        final boolean axisAlongFirstCoordinate = state.getValue(RopeConnectorBlock.AXIS_ALONG_FIRST_COORDINATE);

        final float zRotLast = (axisAlongFirstCoordinate ^ facing.getAxis() == Direction.Axis.Z) ? 90 : 0;
        final float yRot = AngleHelper.horizontalAngle(facing) + (axisAlongFirstCoordinate || facing.getAxis() != Direction.Axis.Y ? 0.0f : 90.0f);
        final float zRot = facing == Direction.UP ? 270 : facing == Direction.DOWN ? 90 : 0;

        knotBuffer.translate(attachmentPoint.subtract(blockPos.getCenter()));
        knotBuffer.rotateCentered((float) ((zRot) / 180 * Math.PI), Direction.SOUTH);
        knotBuffer.rotateCentered((float) ((yRot) / 180 * Math.PI), Direction.UP);
        knotBuffer.rotateCentered((float) ((zRotLast) / 180 * Math.PI), Direction.SOUTH);

        knotBuffer.rotateCentered((float) (Math.PI / 2.0), Direction.UP);
        knotBuffer.renderInto(poseStack, buffer.getBuffer(RenderType.solid()));
    }
}
