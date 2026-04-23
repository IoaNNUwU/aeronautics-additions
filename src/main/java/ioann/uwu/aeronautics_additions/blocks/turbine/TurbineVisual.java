package ioann.uwu.aeronautics_additions.blocks.turbine;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.OrientedRotatingVisual;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visual.DynamicVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.OrientedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import dev.eriksonn.aeronautics.content.blocks.propeller.small.andesite.AndesitePropellerBlock;
import dev.simulated_team.simulated.util.SimMathUtils;
import ioann.uwu.aeronautics_additions.AAPartialModels;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.function.Consumer;

import static com.simibubi.create.content.kinetics.base.DirectionalKineticBlock.FACING;
import static dev.eriksonn.aeronautics.content.blocks.propeller.small.BasePropellerBlock.REVERSED;

public class TurbineVisual extends OrientedRotatingVisual<TurbineBlockEntity> implements SimpleDynamicVisual {

    protected final Vector3f rotationAxis;
    protected final Quaternionf blockOrientation;
    private float lastRotation;

    private final OrientedInstance propellerClockwise;
    private final OrientedInstance propellerAntiClockwise;

    public TurbineVisual(VisualizationContext context, TurbineBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick, Direction.SOUTH, blockEntity.getBlockState().getValue(FACING).getOpposite(), Models.partial(AllPartialModels.SHAFT_HALF));

        Direction facing = this.blockState.getValue(BlockStateProperties.FACING);

        final Vec3i normal = facing.getNormal();
        final Vec3 normalPos = new Vec3(normal.getX(), normal.getY(), normal.getZ());
        final Vector3f pos = Vec3.atLowerCornerOf(this.getVisualPosition()).add(normalPos.scale(3 / 16f)).toVector3f();

        this.rotationAxis = Direction.get(Direction.AxisDirection.POSITIVE, this.rotationAxis()).step();
        this.blockOrientation = SimMathUtils.getBlockStateOrientation(facing);

        this.propellerClockwise = this.instancerProvider().instancer(InstanceTypes.ORIENTED, Models.partial(this.clockwiseModel(blockEntity.getBlockState()))).createInstance();
        this.propellerClockwise.position(pos).rotation(this.blockOrientation).setChanged();

        this.propellerAntiClockwise = this.instancerProvider().instancer(InstanceTypes.ORIENTED, Models.partial(this.anticlockwiseModel(blockEntity.getBlockState()))).createInstance();
        this.propellerAntiClockwise.position(pos).rotation(this.blockOrientation).setChanged();
    }

    public PartialModel clockwiseModel(BlockState state) {
        return state.getValue(REVERSED) ? AAPartialModels.TURBINE_BLADES : AAPartialModels.TURBINE_BLADES;
    }

    public PartialModel anticlockwiseModel(BlockState state) {
        return state.getValue(REVERSED) ? AAPartialModels.TURBINE_BLADES_COUNTER : AAPartialModels.TURBINE_BLADES_COUNTER;
    }

    @Override
    public void beginFrame(final DynamicVisual.Context context) {
        final float angle = this.getAngle(context.partialTick());
        if (this.lastRotation == angle) {
            return;
        }

        this.lastRotation = angle;

        this.propellerClockwise.identityRotation()
                .rotate(Mth.DEG_TO_RAD * angle, this.rotationAxis.x, this.rotationAxis.y, this.rotationAxis.z)
                .rotate(this.blockOrientation)
                .setChanged();

        this.propellerAntiClockwise.identityRotation()
                .rotate(Mth.DEG_TO_RAD * -angle, this.rotationAxis.x, this.rotationAxis.y, this.rotationAxis.z)
                .rotate(this.blockOrientation)
                .setChanged();
    }

    public float getAngle(final float partialTicks) {
        final BlockState state = this.blockEntity.getBlockState();
        final BlockPos pos = this.blockEntity.getBlockPos();

        float superAngle = 2.0F * (this.blockEntity.getPreviousAngle() * (1f - partialTicks) + this.blockEntity.getAngle() * partialTicks);
        return superAngle + rotationOffset(state, state.getValue(AndesitePropellerBlock.FACING).getAxis(), pos);
    }

    @Override
    public void updateLight(float partialTick) {
        super.updateLight(partialTick);
        this.relight(this.pos, this.propellerClockwise, this.propellerAntiClockwise);
    }

    @Override
    protected void _delete() {
        super._delete();

        this.propellerClockwise.delete();
        this.propellerAntiClockwise.delete();
    }

    @Override
    public void collectCrumblingInstances(Consumer<Instance> consumer) {
        super.collectCrumblingInstances(consumer);

        consumer.accept(this.propellerClockwise);
        consumer.accept(this.propellerAntiClockwise);
    }
}
