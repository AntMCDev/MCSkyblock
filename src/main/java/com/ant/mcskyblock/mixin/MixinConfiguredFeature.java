package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.MCSkyBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ConfiguredFeature.class)
public class MixinConfiguredFeature {
    @Final
    @Shadow
    public Feature feature;

    @Inject(at = @At("HEAD"), method="generate", cancellable = true)
    public void generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos origin, CallbackInfoReturnable<Boolean> cir) {
        if (MCSkyBlock.isSkyblockWorld(chunkGenerator) &&
                !(feature instanceof EndPortalFeature) &&
                !(feature instanceof TreeFeature) &&
                !(feature instanceof HugeMushroomFeature) &&
                !(feature instanceof HugeFungusFeature)) {
            cir.setReturnValue(true);
        }
    }
}
