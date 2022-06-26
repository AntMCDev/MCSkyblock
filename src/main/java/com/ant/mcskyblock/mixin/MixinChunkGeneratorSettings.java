package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.world.SkyblockWorldType;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkGeneratorSettings.class)
public class MixinChunkGeneratorSettings {
    private static RegistryEntry<ChunkGeneratorSettings> register(Registry<ChunkGeneratorSettings> registry, RegistryKey<ChunkGeneratorSettings> key, ChunkGeneratorSettings chunkGeneratorSettings) {
        return BuiltinRegistries.add(registry, key.getValue(), chunkGeneratorSettings);
    }

    @Invoker("createSurfaceSettings")
    private static ChunkGeneratorSettings createSurfaceSettings(boolean amplified, boolean largeBiomes) {
        throw new AssertionError();
    }

    @Inject(at = @At("RETURN"), method = "initAndGetDefault", cancellable = true)
    private static void initAndGetDefault(Registry<ChunkGeneratorSettings> registry, CallbackInfoReturnable<RegistryEntry<ChunkGeneratorSettings>> cir) {
        cir.setReturnValue(register(registry, SkyblockWorldType.MC_SKYBLOCK_CHUNK_GEN, createSurfaceSettings(false, false)));
    }
}
