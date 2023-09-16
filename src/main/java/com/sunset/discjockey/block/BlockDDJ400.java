package com.sunset.discjockey.block;

import com.sunset.discjockey.block.BlockEntity.BlockEntityDDJ400;
import com.sunset.discjockey.util.TouchMap.Vec2Type.PlaneRange;
import com.sunset.discjockey.util.TouchMap.Vec2Type.Vec2Plane;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.jetbrains.annotations.Nullable;

public class BlockDDJ400 extends AbstractControllerBlock {

//    private static DirectionProperty FACING = DirectionProperty.create("facing",
//            Direction.NORTH,
//            Direction.SOUTH,
//            Direction.EAST,
//            Direction.WEST
//    );

    public BlockDDJ400() {
        super(BlockBehaviour.Properties.of()
                .noOcclusion());
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BlockEntityDDJ400(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isLoaded(pos) && level.getBlockEntity(pos) instanceof BlockEntityDDJ400 blockEntityDDJ400) {
            return blockEntityDDJ400.use(state, level, pos, player, hand, hit);
        } else {
            return InteractionResult.PASS;
        }
    }

    //render
    @Override
    public VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        Direction direction = blockState.getValue(FACING);

        PlaneRange planeRange = new PlaneRange(new Vec2Plane(0, 3 / 16D), new Vec2Plane(1, 12 / 16D)).rotate(direction, new Vec2Plane(0.5, 0.5));

        return Shapes.box(planeRange.p1.x, 0, planeRange.p1.z, planeRange.p2.x, 0.125, planeRange.p2.z);

    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        Direction direction = blockState.getValue(FACING);

        PlaneRange planeRange = new PlaneRange(new Vec2Plane(0, 3 / 16D), new Vec2Plane(1, 12 / 16D)).rotate(direction, new Vec2Plane(0.5, 0.5));

        return Shapes.box(planeRange.p1.x, 0, planeRange.p1.z, planeRange.p2.x, 0.125, planeRange.p2.z);

    }

    public static void clientSetup(FMLClientSetupEvent event) {
//        event.enqueueWork(()-> ItemBlockRenderTypes.setRenderLayer(BlockCollection.BLOCK_DDJ400.get(), RenderType.translucent()));
    }

}
