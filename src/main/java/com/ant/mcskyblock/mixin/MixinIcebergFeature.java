package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.common.config.SkyBlockConfig;
import com.ant.mcskyblock.common.world.level.levelgen.SkyBlockChunkGenerator;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.IcebergFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IcebergFeature.class)
public class MixinIcebergFeature {
    @Inject(at = @At("HEAD"), method = "place", cancellable = true)
    public void place(FeaturePlaceContext<BlockStateConfiguration> featurePlaceContext, CallbackInfoReturnable<Boolean> cir) {
        if (featurePlaceContext.chunkGenerator() instanceof SkyBlockChunkGenerator) {
            if(!SkyBlockConfig.Structures.GEN_ICEBERGS){
                cir.setReturnValue(false);
            }
        }
    }
}
