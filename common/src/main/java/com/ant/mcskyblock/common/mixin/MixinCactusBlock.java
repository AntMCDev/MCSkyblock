package com.ant.mcskyblock.common.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CactusBlock.class)
public class MixinCactusBlock {
    @Inject(at = @At("RETURN"), method = "canSurvive", cancellable = true)
    public void canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue()) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockState blockState2 = levelReader.getBlockState(blockPos.relative(direction));
                Material material = blockState2.getMaterial();
                if (!material.isSolid() && !levelReader.getFluidState(blockPos.relative(direction)).is(FluidTags.LAVA)) continue;
                cir.setReturnValue(false); return;
            }
            BlockState blockState3 = levelReader.getBlockState(blockPos.below());
            cir.setReturnValue((blockState3.is(Blocks.CACTUS) || blockState3.is(Blocks.SAND) ||
                    blockState3.is(Blocks.RED_SAND) ||
                    blockState3.is(com.ant.mcskyblock.common.world.level.block.Blocks.staticSand()) ||
                    blockState3.is(com.ant.mcskyblock.common.world.level.block.Blocks.staticRedSand())) &&
                    !levelReader.getBlockState(blockPos.above())
                            .getMaterial()
                            .isLiquid());
        }
    }
}
