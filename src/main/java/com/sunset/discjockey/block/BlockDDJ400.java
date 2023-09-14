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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class BlockDDJ400 extends HorizontalDirectionalBlock implements EntityBlock
{

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
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {return new BlockEntityDDJ400(pos, state);}

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.getBlockEntity(pos) instanceof BlockEntityDDJ400 blockEntityDDJ400) {
            return blockEntityDDJ400.use(state, level, pos, player, hand, hit);
        } else {
            return InteractionResult.PASS;
        }
    }

    //blockState
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateDefinitionBuilder) {
        stateDefinitionBuilder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite());
    }

    //render
    @Override
    public VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        Direction direction = blockState.getValue(FACING);

        PlaneRange planeRange = new PlaneRange(new Vec2Plane(0, 0.2), new Vec2Plane(1, 0.75)).rotate(direction, new Vec2Plane(0.5, 0.5));

        return Shapes.box(planeRange.p1.x, 0, planeRange.p1.z, planeRange.p2.x, 0.125, planeRange.p2.z);

//        return switch (direction) {
//            case NORTH -> Shapes.box(0, 0, 0.2, 1, 0.125, 0.75);
//            case SOUTH -> Shapes.box(0, 0, 0.25, 1, 0.125, 0.8);
//            case EAST -> Shapes.box(0.25, 0, 0, 0.8, 0.125, 1);
//            default -> Shapes.box(0.2, 0, 0, 0.75, 0.125, 1);
//        };
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        Direction direction = blockState.getValue(FACING);

        PlaneRange planeRange = new PlaneRange(new Vec2Plane(0, 0.2), new Vec2Plane(1, 0.75)).rotate(direction, new Vec2Plane(0.5, 0.5));

        return Shapes.box(planeRange.p1.x, 0, planeRange.p1.z, planeRange.p2.x, 0.125, planeRange.p2.z);

//        return switch (direction) {
//            case NORTH -> Shapes.box(0, 0, 0.2D, 1, 0.125D, 0.75D);
//            case SOUTH -> Shapes.box(0, 0, 0.25D, 1, 0.125D, 0.8D);
//            case EAST -> Shapes.box(0.25D, 0, 0, 0.8D, 0.125D, 1);
//            default -> Shapes.box(0.2D, 0, 0, 0.75D, 0.125D, 1);
//        };
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {return RenderShape.ENTITYBLOCK_ANIMATED;}

    public static void clientSetup(FMLClientSetupEvent event) {
//        event.enqueueWork(()-> ItemBlockRenderTypes.setRenderLayer(BlockCollection.BLOCK_DDJ400.get(), RenderType.translucent()));
    }
}
