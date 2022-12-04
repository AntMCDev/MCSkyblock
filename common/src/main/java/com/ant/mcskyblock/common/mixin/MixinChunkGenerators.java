package com.ant.mcskyblock.common.mixin;

import com.ant.mcskyblock.common.SkyBlock;
import com.ant.mcskyblock.common.world.level.levelgen.SkyBlockChunkGenerator;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkGenerators;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkGenerators.class)
public class MixinChunkGenerators {
    @Inject(at = @At("RETURN"), method = "bootstrap", cancellable = true)
    public void bootstrap(Registry<Codec<? extends ChunkGenerator>> registry, CallbackInfoReturnable<Codec<? extends ChunkGenerator>> cir) {
        cir.setReturnValue(Registry.register(registry, new ResourceLocation(SkyBlock.MOD_NAME, SkyBlock.CHUNK_GENERATOR_ID), SkyBlockChunkGenerator.CODEC));
    }
}
