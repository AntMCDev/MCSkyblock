package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.skyblock.SkyblockChunkGenerator;
import net.minecraft.world.gen.feature.FossilFeature;
import net.minecraft.world.gen.feature.FossilFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FossilFeature.class)
public class MixinFossilFeature {
    @Inject(at = @At("HEAD"), method = "generate", cancellable = true)
    public void generate(FeatureContext<FossilFeatureConfig> context, CallbackInfoReturnable<Boolean> cir) {
        if (context.getWorld().toServerWorld().getChunkManager().getChunkGenerator() instanceof SkyblockChunkGenerator) {
            cir.setReturnValue(true);
        }
    }
}
