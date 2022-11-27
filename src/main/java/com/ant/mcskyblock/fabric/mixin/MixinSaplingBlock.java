package com.ant.mcskyblock.fabric.mixin;

import com.ant.mcskyblock.common.config.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SaplingBlock.class)
public class MixinSaplingBlock extends BushBlock {
    public MixinSaplingBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        if (Config.INSTANCE.spawning.SAPLINGS_DIE_ON_SAND) {
            return blockState.is(BlockTags.DIRT) || blockState.is(Blocks.FARMLAND) || blockState.is(Blocks.SAND) || blockState.is(Blocks.RED_SAND) ||
                    blockState.is(com.ant.mcskyblock.common.world.level.block.Blocks.STATIC_SAND) || blockState.is(com.ant.mcskyblock.common.world.level.block.Blocks.STATIC_RED_SAND);
        } else {
            return blockState.is(BlockTags.DIRT) || blockState.is(Blocks.FARMLAND);
        }
    }

    @Inject(at = @At("HEAD"), method = "advanceTree", cancellable = true)
    public void advanceTree(ServerLevel serverLevel, BlockPos blockPos, BlockState blockState, RandomSource randomSource, CallbackInfo ci) {
        if (Config.INSTANCE.spawning.SAPLINGS_DIE_ON_SAND && (
                serverLevel.getBlockState(blockPos.below()).is(Blocks.SAND) ||
                serverLevel.getBlockState(blockPos.below()).is(Blocks.RED_SAND) ||
                serverLevel.getBlockState(blockPos.below()).is(com.ant.mcskyblock.common.world.level.block.Blocks.STATIC_SAND) ||
                serverLevel.getBlockState(blockPos.below()).is(com.ant.mcskyblock.common.world.level.block.Blocks.STATIC_RED_SAND)
        )) {
            serverLevel.setBlock(blockPos, Blocks.DEAD_BUSH.defaultBlockState(), 0);
            ci.cancel();
        }
    }
}
