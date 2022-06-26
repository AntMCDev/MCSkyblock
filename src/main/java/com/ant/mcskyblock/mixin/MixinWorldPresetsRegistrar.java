package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.MCSkyBlock;
import com.ant.mcskyblock.world.SkyblockChunkGenerator;
import com.ant.mcskyblock.world.SkyblockWorldType;
import net.minecraft.structure.StructureSet;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import net.minecraft.world.biome.source.TheEndBiomeSource;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
import net.minecraft.world.gen.WorldPreset;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(targets = "net.minecraft.world.gen.WorldPresets$Registrar")
public class MixinWorldPresetsRegistrar {
    @Shadow @Final private Registry<WorldPreset> worldPresetRegistry;
    @Shadow @Final private Registry<DimensionType> dimensionTypeRegistry;
    @Shadow @Final private Registry<Biome> biomeRegistry;
    @Shadow @Final private Registry<StructureSet> structureSetRegistry;
    @Shadow @Final private Registry<ChunkGeneratorSettings> chunkGeneratorSettingsRegistry;
    @Shadow @Final private Registry<DoublePerlinNoiseSampler.NoiseParameters> noiseParametersRegistry;
    @Shadow @Final private RegistryEntry<DimensionType> overworldDimensionType;
    @Shadow @Final private RegistryEntry<DimensionType> theNetherDimensionType;
    @Shadow @Final private RegistryEntry<ChunkGeneratorSettings> netherChunkGeneratorSettings;
    @Shadow @Final private DimensionOptions netherDimensionOptions;
    @Shadow @Final private RegistryEntry<DimensionType> theEndDimensionType;
    @Shadow @Final private RegistryEntry<ChunkGeneratorSettings> endChunkGeneratorSettings;
    @Shadow @Final private DimensionOptions endDimensionOptions;

    private RegistryEntry<WorldPreset> register(RegistryKey<WorldPreset> key, DimensionOptions dimensionOptions, DimensionOptions netherDimensionOptions, DimensionOptions endDimensionOptions) {
        return BuiltinRegistries.add(this.worldPresetRegistry, key, new WorldPreset(Map.of(DimensionOptions.OVERWORLD, dimensionOptions, DimensionOptions.NETHER, netherDimensionOptions, DimensionOptions.END, endDimensionOptions)));
    }

    @Inject(at = @At("RETURN"), method = "initAndGetDefault", cancellable = true)
    public void initAndGetDefault(CallbackInfoReturnable<RegistryEntry<WorldPreset>> cir) {
        RegistryEntry<ChunkGeneratorSettings> registryEntry = this.chunkGeneratorSettingsRegistry.getOrCreateEntry(SkyblockWorldType.MC_SKYBLOCK_CHUNK_GEN);
        cir.setReturnValue(register(SkyblockWorldType.MC_SKYBLOCK_PRESET,
                new DimensionOptions(this.overworldDimensionType, new SkyblockChunkGenerator(this.structureSetRegistry, this.noiseParametersRegistry, MultiNoiseBiomeSource.Preset.OVERWORLD.getBiomeSource(this.biomeRegistry), registryEntry)),
                new DimensionOptions(this.theNetherDimensionType, new SkyblockChunkGenerator(this.structureSetRegistry, this.noiseParametersRegistry, MultiNoiseBiomeSource.Preset.NETHER.getBiomeSource(this.biomeRegistry), registryEntry)),
                new DimensionOptions(this.theEndDimensionType, new SkyblockChunkGenerator(this.structureSetRegistry, this.noiseParametersRegistry, new TheEndBiomeSource(this.biomeRegistry), registryEntry))
        ));
    }
}
