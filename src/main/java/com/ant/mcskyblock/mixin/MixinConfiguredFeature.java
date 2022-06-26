package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.skyblock.SkyblockChunkGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ConfiguredFeature.class)
public class MixinConfiguredFeature<FC extends FeatureConfig, F extends Feature<FC>> {
    @Shadow
    private F feature;

    @Inject(at = @At("HEAD"), method="generate", cancellable = true)
    public void generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos origin, CallbackInfoReturnable<Boolean> cir) {
        if (chunkGenerator instanceof SkyblockChunkGenerator &&
                !(feature instanceof EndPortalFeature) &&
                !(feature instanceof TreeFeature) &&
                !(feature instanceof HugeMushroomFeature) &&
                !(feature instanceof HugeFungusFeature)) {
            cir.setReturnValue(true);
        }
    }
}
