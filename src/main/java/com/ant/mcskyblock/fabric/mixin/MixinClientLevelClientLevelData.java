package com.ant.mcskyblock.fabric.mixin;

import com.ant.mcskyblock.fabric.FabricSkyBlockEvents;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.LevelHeightAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// KEEP
// KEEP
@Mixin(ClientLevel.ClientLevelData.class)
public class MixinClientLevelClientLevelData {
    @Inject(at = @At("HEAD"), method = "getHorizonHeight", cancellable = true)
    public void getHorizonHeight(LevelHeightAccessor levelHeightAccessor, CallbackInfoReturnable<Double> cir) {
        if (FabricSkyBlockEvents.IS_CLIENT_SKYBLOCK) {
            cir.setReturnValue(0d);
        }
    }
    @Inject(at = @At("HEAD"), method = "getClearColorScale", cancellable = true)
    public void getClearColorScale(CallbackInfoReturnable<Float> cir) {
        if (FabricSkyBlockEvents.IS_CLIENT_SKYBLOCK) {
            cir.setReturnValue(1.0f);
        }
    }
}
