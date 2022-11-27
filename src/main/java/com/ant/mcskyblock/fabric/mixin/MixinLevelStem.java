package com.ant.mcskyblock.fabric.mixin;

import com.ant.mcskyblock.common.world.level.levelgen.SkyBlockChunkGenerator;
import net.minecraft.core.Registry;
import net.minecraft.world.level.dimension.LevelStem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

// REMOVE
@Mixin(LevelStem.class)
public class MixinLevelStem {
    @Inject(at = @At("RETURN"), method = "stable", cancellable = true)
    private static void stable(Registry<LevelStem> registry, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValueZ()) {
            Optional<LevelStem> optional = registry.getOptional(LevelStem.OVERWORLD);
            if (optional.isPresent() && optional.get().generator() instanceof SkyBlockChunkGenerator) {
                cir.setReturnValue(true);
            }
        }
    }
}
