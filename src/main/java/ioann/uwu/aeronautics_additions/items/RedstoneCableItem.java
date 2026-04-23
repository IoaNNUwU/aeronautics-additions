package ioann.uwu.aeronautics_additions.items;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import dev.simulated_team.simulated.content.blocks.rope.RopeStrandHolderBehavior;
import dev.simulated_team.simulated.data.advancements.SimAdvancements;
import dev.simulated_team.simulated.index.SimDataComponents;
import ioann.uwu.aeronautics_additions.blocks.redstone_cable_connector.RedstoneCableConnectorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class RedstoneCableItem extends Item {

    public static final DataComponentType<BlockPos> CABLE_ORIGIN = SimDataComponents.ROPE_FIRST_CONNECTION;

    public RedstoneCableItem(Properties properties) {
        super(properties);
    }

    public static boolean isValidRedstoneCableAttachment(Level level, BlockPos blockPos) {
        boolean validLocation = false;

        if (level.getBlockEntity(blockPos) instanceof SmartBlockEntity smartBlockEntity) {
            RopeStrandHolderBehavior behavior = smartBlockEntity.getBehaviour(RopeStrandHolderBehavior.TYPE);

            if (behavior != null && !behavior.isAttached()) {
                validLocation = true;
            }
        }
        return validLocation;
    }

    public static RopeStrandHolderBehavior getRedstoneCableHolder(Level level, BlockPos blockPos) {
        RopeStrandHolderBehavior holder = null;
        if (level.getBlockEntity(blockPos) instanceof SmartBlockEntity smartBlockEntity) {
            RopeStrandHolderBehavior behavior = smartBlockEntity.getBehaviour(RopeStrandHolderBehavior.TYPE);

            if (behavior != null) {
                holder = behavior;
            }
        }
        return holder;
    }

    public static RedstoneCableConnectorBlockEntity getBe(Level level, BlockPos blockPos) {
        if (level.getBlockEntity(blockPos) instanceof RedstoneCableConnectorBlockEntity conn) {
            return conn;
        } else {
            return null;
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        BlockPos blockPos = ctx.getClickedPos();
        Level level = ctx.getLevel();
        ItemStack usedStack = ctx.getItemInHand();
        Player player = ctx.getPlayer();

        if (player != null && player.isShiftKeyDown()) {
            usedStack.remove(CABLE_ORIGIN);
            return InteractionResult.SUCCESS;
        }

        if (isValidRedstoneCableAttachment(level, blockPos)) {

            if (usedStack.has(CABLE_ORIGIN)) {

                if (!level.isClientSide) {
                    if (!this.attachRedstoneCable(level, usedStack.get(CABLE_ORIGIN), blockPos)) {
                        usedStack.remove(CABLE_ORIGIN);
                        return InteractionResult.SUCCESS;
                    } else {
                        SimAdvancements.LEARNING_THE_ROPES.awardTo(player);
                    }
                }

                usedStack.remove(CABLE_ORIGIN);

                if (player != null && !player.isCreative()) {
                    usedStack.shrink(1);
                }

            } else {
                usedStack.set(CABLE_ORIGIN, blockPos);
            }
            return InteractionResult.SUCCESS;
        }

        return super.useOn(ctx);
    }

    private boolean attachRedstoneCable(Level level, BlockPos origin, BlockPos clickedPos) {
        RedstoneCableConnectorBlockEntity originHolder = getBe(level, origin);
        if (originHolder == null) {
            return false;
        }

        RedstoneCableConnectorBlockEntity clickedHolder = getBe(level, clickedPos);
        if (clickedHolder == null) {
            return false;
        }

        if (originHolder.createCable(clickedHolder)) {
            level.playSound(null, origin, SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 0.5f, 1f);
            level.playSound(null, origin, SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 0.5f, 1f);
            return true;
        }
        return false;
    }
}
