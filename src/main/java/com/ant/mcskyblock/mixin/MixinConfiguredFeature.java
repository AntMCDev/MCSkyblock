package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.world.SkyblockChunkGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ConfiguredFeature.class)
public abstract class MixinConfiguredFeature {
    @Shadow
    @Final
    public Feature feature;

    @Inject(at = @At("HEAD"), method = "generate", cancellable = true)
    public void generate(ISeedReader reader, ChunkGenerator chunkGenerator, Random rand, BlockPos pos, CallbackInfoReturnable cir) {
        if (SkyblockChunkGenerator.isWorldSkyblock(reader.getWorld()) && !(feature instanceof EndPodiumFeature) && !(feature instanceof TreeFeature) && !(feature instanceof AbstractBigMushroomFeature) && !(feature instanceof HugeFungusFeature))
            cir.setReturnValue(true);
    }
}
