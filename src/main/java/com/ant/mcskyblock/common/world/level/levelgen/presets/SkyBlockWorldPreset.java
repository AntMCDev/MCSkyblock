package com.ant.mcskyblock.common.world.level.levelgen.presets;

import com.ant.mcskyblock.common.MCSkyBlock;
import com.ant.mcskyblock.common.config.SkyBlockConfigManager;
import com.ant.mcskyblock.common.world.level.biome.SkyBlockEndBiomeSource;
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

public class SkyBlockWorldPreset {

    public static final ResourceKey<WorldPreset> SB_PRESET_KEY = ResourceKey.create(
            Registry.WORLD_PRESET_REGISTRY, new ResourceLocation( MCSkyBlock.MOD_NAME, MCSkyBlock.PRESET_ID )
    );

    public static void register() {

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

        Registry.register(
                Registry.BIOME_SOURCE,
                new ResourceLocation(MCSkyBlock.MOD_NAME, MCSkyBlock.END_BIOMESOURCE_ID),
                SkyBlockEndBiomeSource.CODEC
        );

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
        return SkyBlockConfigManager.overworldIsSkyBlock()  ? new SkyBlockChunkGenerator(
                BuiltinRegistries.STRUCTURE_SETS,
                BuiltinRegistries.NOISE,
                // TODO BIOME HERE(SkyBlockBiomeSource)
                MultiNoiseBiomeSource.Preset.OVERWORLD.biomeSource(BuiltinRegistries.BIOME),
                BuiltinRegistries.NOISE_GENERATOR_SETTINGS.getOrCreateHolderOrThrow(NoiseGeneratorSettings.OVERWORLD )
        ) : new NoiseBasedChunkGenerator(
                BuiltinRegistries.STRUCTURE_SETS,
                BuiltinRegistries.NOISE,
                MultiNoiseBiomeSource.Preset.OVERWORLD.biomeSource(BuiltinRegistries.BIOME),
                BuiltinRegistries.NOISE_GENERATOR_SETTINGS.getOrCreateHolderOrThrow(NoiseGeneratorSettings.OVERWORLD )
        );
    }

    private static ChunkGenerator netherChunkGenerator() {
        return SkyBlockConfigManager.endIsSkyblock()  ? new SkyBlockChunkGenerator(
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

    private static ChunkGenerator endChunkGenerator() {
        return SkyBlockConfigManager.endIsSkyblock() ? new SkyBlockChunkGenerator(
                BuiltinRegistries.STRUCTURE_SETS,
                BuiltinRegistries.NOISE,
                new SkyBlockEndBiomeSource(BuiltinRegistries.BIOME),
                BuiltinRegistries.NOISE_GENERATOR_SETTINGS.getOrCreateHolderOrThrow( NoiseGeneratorSettings.END )
        ) : new NoiseBasedChunkGenerator(
                BuiltinRegistries.STRUCTURE_SETS,
                BuiltinRegistries.NOISE,
                new TheEndBiomeSource(BuiltinRegistries.BIOME),
                BuiltinRegistries.NOISE_GENERATOR_SETTINGS.getOrCreateHolderOrThrow(NoiseGeneratorSettings.END)
        );
    }
}
