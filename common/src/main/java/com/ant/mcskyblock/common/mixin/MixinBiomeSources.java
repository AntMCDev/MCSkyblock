package com.ant.mcskyblock.common.mixin;

import com.ant.mcskyblock.common.SkyBlock;
import com.ant.mcskyblock.common.world.level.biome.SkyBlockEndBiomeSource;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.BiomeSources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BiomeSources.class)
public class MixinBiomeSources {
    @Inject(at = @At("RETURN"), method = "bootstrap", cancellable = true)
    private static void bootstrap(Registry<Codec<? extends BiomeSource>> registry, CallbackInfoReturnable<Codec<? extends BiomeSource>> cir) {
        cir.setReturnValue(Registry.register(registry, SkyBlock.END_BIOMESOURCE_ID, SkyBlockEndBiomeSource.CODEC));
    }
}
