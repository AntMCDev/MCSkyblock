package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.world.SkyblockChunkGenerator;
import com.google.common.collect.Lists;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import net.minecraft.world.biome.source.TheEndBiomeSource;
import net.minecraft.world.dimension.DimensionOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;

@Mixin(DimensionOptions.class)
public class MixinDimensionOptions {
    @Inject(at = @At("RETURN"), method = "hasDefaultSettings", cancellable = true)
    private static void hasDefaultSettings(long seed, SimpleRegistry<DimensionOptions> options, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValueZ()) {
            List<Map.Entry<RegistryKey<DimensionOptions>, DimensionOptions>> lvt_3_1_ = Lists.newArrayList(options.getEntries());
            Map.Entry<RegistryKey<DimensionOptions>, DimensionOptions> lvt_5_1_ = (Map.Entry)lvt_3_1_.get(1);
            Map.Entry<RegistryKey<DimensionOptions>, DimensionOptions> lvt_6_1_ = (Map.Entry)lvt_3_1_.get(2);

            if (((DimensionOptions)lvt_5_1_.getValue()).getChunkGenerator() instanceof SkyblockChunkGenerator && ((DimensionOptions)lvt_6_1_.getValue()).getChunkGenerator() instanceof SkyblockChunkGenerator) {
                SkyblockChunkGenerator lvt_7_1_ = (SkyblockChunkGenerator)((DimensionOptions)lvt_5_1_.getValue()).getChunkGenerator();
                SkyblockChunkGenerator lvt_8_1_ = (SkyblockChunkGenerator)((DimensionOptions)lvt_6_1_.getValue()).getChunkGenerator();
                if (!(lvt_7_1_.getBiomeSource() instanceof MultiNoiseBiomeSource)) {
                    cir.setReturnValue(false);
                } else {
                    MultiNoiseBiomeSource lvt_9_1_ = (MultiNoiseBiomeSource)lvt_7_1_.getBiomeSource();
                    if (!(lvt_8_1_.getBiomeSource() instanceof TheEndBiomeSource)) {
                        cir.setReturnValue(false);
                    } else {
                        TheEndBiomeSource lvt_10_1_ = (TheEndBiomeSource)lvt_8_1_.getBiomeSource();
                        cir.setReturnValue(true);
                    }
                }
            }
        }
    }
}
