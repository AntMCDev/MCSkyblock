package com.ant.mcskyblock.skyblock;

import com.ant.mcskyblock.MCSkyBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import net.minecraft.world.biome.source.TheEndBiomeSource;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionTypes;
import net.minecraft.world.gen.WorldPreset;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;

import java.util.Map;

public class SkyblockWorldPreset {
    public static final RegistryKey<WorldPreset> SKYBLOCK = RegistryKey.of(Registry.WORLD_PRESET_KEY, new Identifier(MCSkyBlock.MOD_NAME, MCSkyBlock.MODULE_SKYBLOCK));

    public static void register() {
        BuiltinRegistries.add(BuiltinRegistries.WORLD_PRESET, SKYBLOCK, build());
    }

    private static WorldPreset build() {
        return new WorldPreset(
                Map.of(
                        DimensionOptions.OVERWORLD, new DimensionOptions(BuiltinRegistries.DIMENSION_TYPE.getOrCreateEntry(DimensionTypes.OVERWORLD), overworldChunkGenerator()),
                        DimensionOptions.NETHER, new DimensionOptions(BuiltinRegistries.DIMENSION_TYPE.getOrCreateEntry(DimensionTypes.THE_NETHER), netherChunkGenerator()),
                        DimensionOptions.END, new DimensionOptions(BuiltinRegistries.DIMENSION_TYPE.getOrCreateEntry(DimensionTypes.THE_END), endChunkGenerator())
                )
        );
    }

    private static ChunkGenerator overworldChunkGenerator() {
        return new SkyblockChunkGenerator(
                BuiltinRegistries.STRUCTURE_SET,
                BuiltinRegistries.NOISE_PARAMETERS,
                MultiNoiseBiomeSource.Preset.OVERWORLD.getBiomeSource(BuiltinRegistries.BIOME),
                BuiltinRegistries.CHUNK_GENERATOR_SETTINGS.getOrCreateEntry(ChunkGeneratorSettings.OVERWORLD)
        );
    }

    private static ChunkGenerator netherChunkGenerator() {
        return new SkyblockChunkGenerator(
                BuiltinRegistries.STRUCTURE_SET,
                BuiltinRegistries.NOISE_PARAMETERS,
                MultiNoiseBiomeSource.Preset.NETHER.getBiomeSource(BuiltinRegistries.BIOME),
                BuiltinRegistries.CHUNK_GENERATOR_SETTINGS.getOrCreateEntry(ChunkGeneratorSettings.NETHER)
        );
    }

    private static ChunkGenerator endChunkGenerator() {
        return new SkyblockChunkGenerator(
                BuiltinRegistries.STRUCTURE_SET,
                BuiltinRegistries.NOISE_PARAMETERS,
                new TheEndBiomeSource(BuiltinRegistries.BIOME),
                BuiltinRegistries.CHUNK_GENERATOR_SETTINGS.getOrCreateEntry(ChunkGeneratorSettings.END)
        );
    }
}
