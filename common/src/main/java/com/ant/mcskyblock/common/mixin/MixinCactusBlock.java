package com.ant.mcskyblock.common.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;

@Mixin(CactusBlock.class)
public class MixinCactusBlock {
    @Inject(at = @At("RETURN"), method = "canSurvive", cancellable = true)
    public void canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue()) {
            Iterator var4 = Direction.Plane.HORIZONTAL.iterator();

            Direction direction;
            BlockState blockState2;
            do {
                if (!var4.hasNext()) {
                    BlockState blockState3 = levelReader.getBlockState(blockPos.below());
                    cir.setReturnValue(
                            (
                                    blockState3.is(Blocks.CACTUS) ||
                                    blockState3.is(BlockTags.SAND) ||
                                    blockState3.is(Blocks.RED_SAND) ||
                                    blockState3.is(com.ant.mcskyblock.common.world.level.block.Blocks.staticSand()) ||
                                    blockState3.is(com.ant.mcskyblock.common.world.level.block.Blocks.staticRedSand())
                            ) && !levelReader.getBlockState(blockPos.above()).liquid());
                }

                direction = (Direction)var4.next();
                blockState2 = levelReader.getBlockState(blockPos.relative(direction));
            } while(!blockState2.isSolid() && !levelReader.getFluidState(blockPos.relative(direction)).is(FluidTags.LAVA));

            cir.setReturnValue(false);
        }
    }
}
