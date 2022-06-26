package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.world.SkyblockWorldEvents;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.HeightLimitView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientWorld.Properties.class)
public class MixinClientWorldProperties {
    @Inject(at = @At("HEAD"), method = "getSkyDarknessHeight", cancellable = true)
    public void getSkyDarknessHeight(HeightLimitView world, CallbackInfoReturnable<Double> cir) {
        if (SkyblockWorldEvents.isClientSkyblock) {
            cir.setReturnValue(0d);
        }
    }
    @Inject(at = @At("HEAD"), method = "getHorizonShadingRatio", cancellable = true)
    public void getHorizonShadingRatio(CallbackInfoReturnable<Float> cir) {
        if (SkyblockWorldEvents.isClientSkyblock) {
            cir.setReturnValue(1.0f);
        }
    }
}
