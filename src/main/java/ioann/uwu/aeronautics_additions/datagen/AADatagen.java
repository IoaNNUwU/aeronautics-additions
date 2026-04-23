package ioann.uwu.aeronautics_additions.datagen;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import dev.simulated_team.simulated.content.blocks.util.AbstractDirectionalAxisBlock;
import ioann.uwu.aeronautics_additions.blocks.redstone_cable_connector.RedstoneCableConnectorBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import org.apache.commons.lang3.function.TriFunction;

public class AADatagen {

    public static void directionalConnectorBlock(
            DataGenContext<Block, RedstoneCableConnectorBlock> ctx,
            RegistrateBlockstateProvider registrate
    ) {
        directionalConnectorBlock(ctx, registrate, (blockState, vertical, source) -> registrate.models()
                .getExistingFile(registrate.modLoc("block/" + ctx.getName() + "/block_"
                        + (vertical ? "vertical" : "horizontal")
                        + (source ? "_source" : "")
                )));
    }

    private static <T extends AbstractDirectionalAxisBlock> void directionalConnectorBlock(
            DataGenContext<Block, T> ctx,
            RegistrateBlockstateProvider registrate,
            TriFunction<BlockState, Boolean, Boolean, Object> modelFunc) {

        registrate.getVariantBuilder(ctx.getEntry())
                .forAllStates(state -> {

                    boolean source = state.getValue(RedstoneCableConnectorBlock.SOURCE);
                    boolean alongFirst = state.getValue(AbstractDirectionalAxisBlock.AXIS_ALONG_FIRST_COORDINATE);
                    Direction direction = state.getValue(AbstractDirectionalAxisBlock.FACING);

                    final boolean vertical = direction.getAxis()
                            .isHorizontal() && (direction.getAxis() == Direction.Axis.X) == alongFirst;
                    final int xRot = direction == Direction.DOWN ? 270 : direction == Direction.UP ? 90 : 0;
                    final int yRot = direction.getAxis()
                            .isVertical() ? alongFirst ? 0 : 90 : (int) direction.toYRot();

                    final Object model = modelFunc.apply(state, vertical, source);
                    if (!(model instanceof final ModelFile m))
                        throw new AssertionError("Required Model file!");

                    return ConfiguredModel.builder()
                            .modelFile(m)
                            .rotationX(xRot)
                            .rotationY(yRot)
                            .build();
                });
    }
}
