package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.world.SkyblockChunkGenerator;
import com.ant.mcskyblock.world.SkyblockWorldType;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.gen.WorldPreset;
import net.minecraft.world.gen.WorldPresets;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(WorldPresets.class)
public class MixinWorldPresets {
    @Invoker("of")
    public static RegistryKey<WorldPreset> of(String id) {
        throw new AssertionError();
    }

    @Inject(at = @At("RETURN"), method = "getWorldPreset", cancellable = true)
    private static void getWorldPreset(GeneratorOptions generatorOptions, CallbackInfoReturnable<Optional<RegistryKey<WorldPreset>>> cir) {
        if (generatorOptions.getChunkGenerator() instanceof SkyblockChunkGenerator) {
            cir.setReturnValue(Optional.of(SkyblockWorldType.MC_SKYBLOCK_PRESET));
        }
    }
}
