package com.ant.mcskyblock.common.mixin;

import com.ant.mcskyblock.common.config.Config;
import com.ant.mcskyblock.common.world.level.levelgen.SkyBlockChunkGenerator;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.GeodeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GeodeFeature.class)
public class MixinGeodeFeature {
    @Inject(at = @At("HEAD"), method = "place", cancellable = true)
    public void place(FeaturePlaceContext<GeodeConfiguration> featurePlaceContext, CallbackInfoReturnable<Boolean> cir) {
        if (featurePlaceContext.chunkGenerator() instanceof SkyBlockChunkGenerator && !Config.INSTANCE.structures.GEN_GEODES) {
            cir.setReturnValue(false);
        } else if (Config.INSTANCE.structures.GEN_GEODES) {
            ((MixinGeodeConfigurationAccessor)featurePlaceContext.config()).setInvalidBlocksThreshold(Integer.MAX_VALUE);
        }
    }
}
