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
    @Inject(at = @At("RETURN"), method = "func_236060_a_", cancellable = true)
    private static void func_236060_a_(long p_236060_0_, SimpleRegistry<Dimension> p_236060_2_, CallbackInfoReturnable cir) {
        if (!cir.getReturnValueZ()) {
            List<Map.Entry<RegistryKey<Dimension>, Dimension>> lvt_3_1_ = Lists.newArrayList(p_236060_2_.getEntries());
            Map.Entry<RegistryKey<Dimension>, Dimension> lvt_5_1_ = (Map.Entry)lvt_3_1_.get(1);
            Map.Entry<RegistryKey<Dimension>, Dimension> lvt_6_1_ = (Map.Entry)lvt_3_1_.get(2);

            if (((Dimension)lvt_5_1_.getValue()).getChunkGenerator() instanceof SkyblockChunkGenerator && ((Dimension)lvt_6_1_.getValue()).getChunkGenerator() instanceof SkyblockChunkGenerator) {
                SkyblockChunkGenerator lvt_7_1_ = (SkyblockChunkGenerator)((Dimension)lvt_5_1_.getValue()).getChunkGenerator();
                SkyblockChunkGenerator lvt_8_1_ = (SkyblockChunkGenerator)((Dimension)lvt_6_1_.getValue()).getChunkGenerator();
                if (!(lvt_7_1_.getBiomeProvider() instanceof NetherBiomeProvider)) {
                    cir.setReturnValue(false);
                } else {
                    NetherBiomeProvider lvt_9_1_ = (NetherBiomeProvider)lvt_7_1_.getBiomeProvider();
                    if (!lvt_9_1_.isDefaultPreset(p_236060_0_)) {
                        cir.setReturnValue(false);
                    } else if (!(lvt_8_1_.getBiomeProvider() instanceof EndBiomeProvider)) {
                        cir.setReturnValue(false);
                    } else {
                        EndBiomeProvider lvt_10_1_ = (EndBiomeProvider)lvt_8_1_.getBiomeProvider();
                        cir.setReturnValue(lvt_10_1_.areProvidersEqual(p_236060_0_));
                    }
                }
            }
        }
    }
}
