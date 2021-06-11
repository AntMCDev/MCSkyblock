package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.world.SkyblockChunkGenerator;
import com.google.common.collect.Lists;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.Dimension;
import net.minecraft.world.biome.provider.EndBiomeProvider;
import net.minecraft.world.biome.provider.NetherBiomeProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;

@Mixin(Dimension.class)
public abstract class MixinDimension {
    @Inject(at = @At("RETURN"), method = "stable", cancellable = true)
    private static void stable(long p_236060_0_, SimpleRegistry<Dimension> p_236060_2_, CallbackInfoReturnable cir) {
        if (!cir.getReturnValueZ()) {
            List<Map.Entry<RegistryKey<Dimension>, Dimension>> lvt_3_1_ = Lists.newArrayList(p_236060_2_.entrySet());
            Map.Entry<RegistryKey<Dimension>, Dimension> lvt_5_1_ = (Map.Entry)lvt_3_1_.get(1);
            Map.Entry<RegistryKey<Dimension>, Dimension> lvt_6_1_ = (Map.Entry)lvt_3_1_.get(2);

            if (((Dimension)lvt_5_1_.getValue()).generator() instanceof SkyblockChunkGenerator && ((Dimension)lvt_6_1_.getValue()).generator() instanceof SkyblockChunkGenerator) {
                SkyblockChunkGenerator lvt_7_1_ = (SkyblockChunkGenerator)((Dimension)lvt_5_1_.getValue()).generator();
                SkyblockChunkGenerator lvt_8_1_ = (SkyblockChunkGenerator)((Dimension)lvt_6_1_.getValue()).generator();
                if (!(lvt_7_1_.getBiomeSource() instanceof NetherBiomeProvider)) {
                    cir.setReturnValue(false);
                } else {
                    NetherBiomeProvider lvt_9_1_ = (NetherBiomeProvider)lvt_7_1_.getBiomeSource();
                    if (!lvt_9_1_.stable(p_236060_0_)) {
                        cir.setReturnValue(false);
                    } else if (!(lvt_8_1_.getBiomeSource() instanceof EndBiomeProvider)) {
                        cir.setReturnValue(false);
                    } else {
                        EndBiomeProvider lvt_10_1_ = (EndBiomeProvider)lvt_8_1_.getBiomeSource();
                        cir.setReturnValue(lvt_10_1_.stable(p_236060_0_));
                    }
                }
            }
        }
    }
}
