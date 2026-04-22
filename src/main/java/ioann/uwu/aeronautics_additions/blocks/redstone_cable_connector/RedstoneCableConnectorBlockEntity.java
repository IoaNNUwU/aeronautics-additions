package ioann.uwu.aeronautics_additions.blocks.redstone_cable_connector;

import com.google.common.collect.Sets;
import dev.ryanhcode.sable.api.sublevel.ServerSubLevelContainer;
import dev.ryanhcode.sable.api.sublevel.SubLevelContainer;
import dev.ryanhcode.sable.sublevel.SubLevel;
import dev.simulated_team.simulated.content.blocks.rope.rope_connector.RopeConnectorBlockEntity;
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

import java.util.Set;

public class RedstoneCableConnectorBlockEntity extends RopeConnectorBlockEntity {

    public RedstoneCableConnectorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
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
