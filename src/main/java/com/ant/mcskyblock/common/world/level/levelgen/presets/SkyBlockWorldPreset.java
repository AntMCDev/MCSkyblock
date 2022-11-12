package com.ant.mcskyblock.common.world.level.levelgen.presets;

import com.ant.mcskyblock.common.MCSkyBlock;
import com.ant.mcskyblock.common.world.level.levelgen.SkyBlockChunkGenerator;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.TheEndBiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.presets.WorldPreset;


import java.util.Map;

// NON FABRIC

public class SkyBlockWorldPreset {

    public static final ResourceKey<WorldPreset> SB_PRESET_KEY = ResourceKey.create(
            Registry.WORLD_PRESET_REGISTRY, new ResourceLocation( MCSkyBlock.MOD_NAME, MCSkyBlock.PRESET_ID )
    );

    public static void register() {
        // register the preset
        BuiltinRegistries.register(
                BuiltinRegistries.WORLD_PRESET,
                SB_PRESET_KEY,
                build()
        );
        Registry.register(
                Registry.CHUNK_GENERATOR,
                new ResourceLocation( MCSkyBlock.MOD_NAME, MCSkyBlock.CHUNK_GENERATOR_ID ),
                SkyBlockChunkGenerator.CODEC
        );
        // WE should add a biome for exclude list. Not sure that anyone would want that but whatever.

    }

    private static WorldPreset build() {
        return new WorldPreset(
                Map.of(
                        LevelStem.OVERWORLD, new LevelStem(
                                BuiltinRegistries.DIMENSION_TYPE.getOrCreateHolderOrThrow(BuiltinDimensionTypes.OVERWORLD ),
                                overworldChunkGenerator()
                        ),
                        LevelStem.NETHER, new LevelStem(
                                BuiltinRegistries.DIMENSION_TYPE.getOrCreateHolderOrThrow(BuiltinDimensionTypes.NETHER),
                                netherChunkGenerator()
                        ),
                        LevelStem.END, new LevelStem(
                                BuiltinRegistries.DIMENSION_TYPE.getOrCreateHolderOrThrow(BuiltinDimensionTypes.END),
                                endChunkGenerator()
                        )
                )
        );
    }

    private static ChunkGenerator overworldChunkGenerator() {
        boolean CUSTOM_OVERWORLD = true;
        return CUSTOM_OVERWORLD ? new SkyBlockChunkGenerator(
                BuiltinRegistries.STRUCTURE_SETS,
                BuiltinRegistries.NOISE,
                MultiNoiseBiomeSource.Preset.OVERWORLD.biomeSource(BuiltinRegistries.BIOME),
                // TODO BIOME HERE
                BuiltinRegistries.NOISE_GENERATOR_SETTINGS.getOrCreateHolderOrThrow(NoiseGeneratorSettings.OVERWORLD )
        ) : new NoiseBasedChunkGenerator(
                BuiltinRegistries.STRUCTURE_SETS,
                BuiltinRegistries.NOISE,
                MultiNoiseBiomeSource.Preset.OVERWORLD.biomeSource(BuiltinRegistries.BIOME),
                BuiltinRegistries.NOISE_GENERATOR_SETTINGS.getOrCreateHolderOrThrow(NoiseGeneratorSettings.OVERWORLD )
        );
    }

    private static ChunkGenerator netherChunkGenerator() {
        boolean CUSTOM_NETHER = true;
        return CUSTOM_NETHER ? new SkyBlockChunkGenerator(
                BuiltinRegistries.STRUCTURE_SETS,
                BuiltinRegistries.NOISE,
                MultiNoiseBiomeSource.Preset.NETHER.biomeSource(BuiltinRegistries.BIOME),
                BuiltinRegistries.NOISE_GENERATOR_SETTINGS.getOrCreateHolderOrThrow(NoiseGeneratorSettings.NETHER )
        ) : new NoiseBasedChunkGenerator(
                BuiltinRegistries.STRUCTURE_SETS,
                BuiltinRegistries.NOISE,
                MultiNoiseBiomeSource.Preset.NETHER.biomeSource(BuiltinRegistries.BIOME),
                BuiltinRegistries.NOISE_GENERATOR_SETTINGS.getOrCreateHolderOrThrow( NoiseGeneratorSettings.NETHER)
        );
    }

    // FIXME figure out what to do with the end lol
    private static ChunkGenerator endChunkGenerator() {
        boolean CUSTOM_END = true;
        return CUSTOM_END ? new SkyBlockChunkGenerator(
                BuiltinRegistries.STRUCTURE_SETS,
                BuiltinRegistries.NOISE,
                new TheEndBiomeSource(BuiltinRegistries.BIOME),  // FIXME this is well old.
                BuiltinRegistries.NOISE_GENERATOR_SETTINGS.getOrCreateHolderOrThrow( NoiseGeneratorSettings.END )
        ) : new NoiseBasedChunkGenerator(
                BuiltinRegistries.STRUCTURE_SETS,
                BuiltinRegistries.NOISE,
                new TheEndBiomeSource(BuiltinRegistries.BIOME), // FIXME
                BuiltinRegistries.NOISE_GENERATOR_SETTINGS.getOrCreateHolderOrThrow(NoiseGeneratorSettings.END)
        );
    }
}
