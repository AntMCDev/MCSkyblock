package com.ant.mcskyblock.mixin;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.dimension.DimensionOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DimensionOptions.class)
public class MixinDimensionOptions {
    @Inject(at = @At("RETURN"), method = "hasDefaultSettings", cancellable = true)
    private static void hasDefaultSettings(Registry<DimensionOptions> registry, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValueZ()) {

        }
    }
}
