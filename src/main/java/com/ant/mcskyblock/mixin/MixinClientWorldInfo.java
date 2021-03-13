package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.world.SkyblockWorldInfo;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Code adapted from Vaskii's Botania GoG source
// https://github.com/Vazkii/Botania
@Mixin(ClientWorld.ClientWorldInfo.class)
public abstract class MixinClientWorldInfo implements SkyblockWorldInfo {
    private boolean skyblock;

    @Override
    public boolean isSkyblock() {
        return skyblock;
    }

    @Override
    public void markSkyblock() {
        skyblock = true;
    }

    @Inject(at = @At("HEAD"), method = "getVoidFogHeight", cancellable = true)
    private void horizon(CallbackInfoReturnable<Double> cir) {
        if (skyblock) {
            cir.setReturnValue(0.0);
        }
    }

    @Inject(at = @At("HEAD"), method = "getFogDistance", cancellable = true)
    private void fog(CallbackInfoReturnable<Double> cir) {
        if (skyblock) {
            cir.setReturnValue(1.0);
        }
    }
}
