package com.ant.mcskyblock.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class StaticConcretePowderBlock extends Block {
    private final BlockState concrete;

    StaticConcretePowderBlock(Block block, BlockBehaviour.Properties properties) {
        super(properties);
        this.concrete = block.defaultBlockState();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockState blockState;
        BlockPos blockPos;
        Level blockGetter = blockPlaceContext.getLevel();
        if (shouldSolidify(blockGetter, blockPos = blockPlaceContext.getClickedPos(), blockState = blockGetter.getBlockState(blockPos))) {
            return this.concrete;
        }
        return super.getStateForPlacement(blockPlaceContext);
    }

    private static boolean shouldSolidify(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState) {
        return canSolidify(blockState) || touchesLiquid(blockGetter, blockPos);
    }

    private static boolean touchesLiquid(BlockGetter blockGetter, BlockPos blockPos) {
        boolean bl = false;
        BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();
        for (Direction direction : Direction.values()) {
            BlockState blockState = blockGetter.getBlockState(mutableBlockPos);
            if (direction == Direction.DOWN && !canSolidify(blockState)) continue;
            mutableBlockPos.setWithOffset((Vec3i)blockPos, direction);
            blockState = blockGetter.getBlockState(mutableBlockPos);
            if (!canSolidify(blockState) || blockState.isFaceSturdy(blockGetter, blockPos, direction.getOpposite())) continue;
            bl = true;
            break;
        }
        return bl;
    }

    private static boolean canSolidify(BlockState blockState) {
        return blockState.getFluidState().is(FluidTags.WATER);
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        if (touchesLiquid(levelAccessor, blockPos)) {
            return this.concrete;
        }
        return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
    }
}
