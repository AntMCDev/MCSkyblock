package com.ant.mcskyblock.common.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

/**
 * [COMMON] BLOCK - This is a floating variation of the regular concrete powder blocks
 */
public class StaticConcretePowderBlock extends Block {
    private final BlockState concrete;

    /**
     *
     * @param block
     */
    StaticConcretePowderBlock(Block block, DyeColor color) {
        super(Properties.of().mapColor(color).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sound(SoundType.SAND));
        this.concrete = block.defaultBlockState();
    }

    /**
     *
     * @param blockPlaceContext
     * @return
     */
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

    /**
     *
     * @param blockGetter
     * @param blockPos
     * @param blockState
     * @return
     */
    private static boolean shouldSolidify(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState) {
        return canSolidify(blockState) || touchesLiquid(blockGetter, blockPos);
    }

    /**
     *
     * @param blockGetter
     * @param blockPos
     * @return
     */
    private static boolean touchesLiquid(BlockGetter blockGetter, BlockPos blockPos) {
        boolean ret = false;
        BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();
        for (Direction direction : Direction.values()) {
            BlockState blockState = blockGetter.getBlockState(mutableBlockPos);
            if (direction == Direction.DOWN && !canSolidify(blockState)) continue;
            mutableBlockPos.setWithOffset((Vec3i)blockPos, direction);
            blockState = blockGetter.getBlockState(mutableBlockPos);
            if (!canSolidify(blockState) || blockState.isFaceSturdy(blockGetter, blockPos, direction.getOpposite())) continue;
            ret = true;
            break;
        }
        return ret;
    }


    /**
     *
     * @param blockState
     * @return
     */
    private static boolean canSolidify(BlockState blockState) {
        return blockState.getFluidState().is(FluidTags.WATER);
    }

    /**
     *
     * @param blockState
     * @param direction
     * @param blockState2
     * @param levelAccessor
     * @param blockPos
     * @param blockPos2
     * @return
     */
    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        if (touchesLiquid(levelAccessor, blockPos)) {
            return this.concrete;
        }
        return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
    }
}
