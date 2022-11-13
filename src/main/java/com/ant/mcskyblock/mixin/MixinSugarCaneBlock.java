package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.common.block.StaticBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SugarCaneBlock.class)
public class MixinSugarCaneBlock {
    @Inject(at = @At("RETURN"), method = "canSurvive", cancellable = true)
    public void canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue()) {
            BlockState blockState2 = levelReader.getBlockState(blockPos.below());
            if (blockState2.is(Blocks.SUGAR_CANE)) {
                cir.setReturnValue(true); return;
            }
            if (blockState2.is(BlockTags.DIRT) || blockState2.is(Blocks.SAND) || blockState2.is(Blocks.RED_SAND) || blockState2.is(StaticBlock.SAND) || blockState2.is(StaticBlock.RED_SAND)) {
                BlockPos blockPos2 = blockPos.below();
                for (Direction direction : Direction.Plane.HORIZONTAL) {
                    BlockState blockState3 = levelReader.getBlockState(blockPos2.relative(direction));
                    FluidState fluidState = levelReader.getFluidState(blockPos2.relative(direction));
                    if (!fluidState.is(FluidTags.WATER) && !blockState3.is(Blocks.FROSTED_ICE)) continue;
                    cir.setReturnValue(true); return;
                }
            }
            cir.setReturnValue(false);
        }
    }
}
