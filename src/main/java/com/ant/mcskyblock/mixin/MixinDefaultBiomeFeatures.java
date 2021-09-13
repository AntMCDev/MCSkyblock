package com.ant.mcskyblock.mixin;

import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DefaultBiomeFeatures.class)
public class MixinDefaultBiomeFeatures {
    @Inject(at = @At("HEAD"), method = "addCaveMobs", cancellable = true)
    private static void addCaveMobs(net.minecraft.world.biome.SpawnSettings.Builder builder, CallbackInfo ci) {
        DefaultBiomeFeatures.addCaveWaterMobs(builder);
        ci.cancel();
    }
}
