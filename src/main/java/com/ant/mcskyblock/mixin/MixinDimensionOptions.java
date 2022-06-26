package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.skyblock.SkyblockChunkGenerator;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.dimension.DimensionOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(DimensionOptions.class)
public class MixinDimensionOptions {
    @Inject(at = @At("RETURN"), method = "hasDefaultSettings", cancellable = true)
    private static void hasDefaultSettings(Registry<DimensionOptions> registry, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValueZ()) {
            Optional<DimensionOptions> optional = registry.getOrEmpty(DimensionOptions.OVERWORLD);
            if (optional.isPresent() && optional.get().getChunkGenerator() instanceof SkyblockChunkGenerator) {
                cir.setReturnValue(true);
            }
        }
    }
}
