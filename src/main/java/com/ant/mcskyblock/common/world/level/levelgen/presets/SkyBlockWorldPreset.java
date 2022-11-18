package com.ant.mcskyblock.common.world.level.levelgen.presets;

import com.ant.mcskyblock.common.MCSkyBlock;
import com.ant.mcskyblock.common.config.SkyBlockConfig;
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

    /**
     * Registers all the things that are needed as far as world generation for the world preset.
     */
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

    /**
     * Registers the Map of the skyblock presets into the game for the nether,overworld and end dimentions.
     * @return The world preset map that is available for minecraft to load.
     */
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

    /**
     * This function creates the overworld generator. That is if the configuration of the mod settings for overworldChunkGenerator() are set to true.
     * @return Returns the ChunkGenerator that is used in the world generation of the preset generators. For the overworld
     */
    private static ChunkGenerator overworldChunkGenerator() {
        return SkyBlockConfig.WorldGen.IS_OVERWORLD_SKYBLOCK  ? new SkyBlockChunkGenerator(
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


    /**
     * This function creates the nether generator. That is if the configuration of the mod settings for netherChunkGenerator() are set to true.
     * @return Returns the ChunkGenerator that is used in the world generation of the preset generators for the nether.
     */
    private static ChunkGenerator netherChunkGenerator() {
        return SkyBlockConfig.WorldGen.IS_NETHER_SKYBLOCK ? new SkyBlockChunkGenerator(
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


    /**
     * This function creates the end generator. That is if the configuration of the mod settings for endChunkGenerator() are set to true.
     * @return Returns the ChunkGenerator that is used in the world generation of the preset generators for the end.
     */
    private static ChunkGenerator endChunkGenerator() {
        return SkyBlockConfig.WorldGen.IS_END_SKYBLOCK ? new SkyBlockChunkGenerator(
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
