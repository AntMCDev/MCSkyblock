package com.ant.mcskyblock.world;

import com.ant.mcskyblock.config.ConfigHandler;
import com.mojang.serialization.Lifecycle;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.OverworldBiomeSource;
import net.minecraft.world.level.biome.TheEndBiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraftforge.common.world.ForgeWorldType;

import javax.annotation.Nonnull;

public class SkyblockWorldType extends ForgeWorldType {
    public static final SkyblockWorldType INSTANCE = new SkyblockWorldType();

    public SkyblockWorldType() {
        super((IComplexChunkGeneratorFactory) SkyblockWorldType::getChunkGenerator);
    }

    public static ChunkGenerator getChunkGenerator(@Nonnull Registry<Biome> biomeRegistry, @Nonnull Registry<NoiseGeneratorSettings> dimensionSettingsRegistry, long seed) {
        return new SkyblockChunkGenerator(new OverworldBiomeSource(seed, false, false, biomeRegistry), seed, () -> dimensionSettingsRegistry.getOrThrow(NoiseGeneratorSettings.OVERWORLD));
    }

    @Override
    public String getTranslationKey() {
        return "generator.mcskyblock";
    }

    public interface IComplexChunkGeneratorFactory extends IBasicChunkGeneratorFactory {
        @Override
        default WorldGenSettings createSettings(RegistryAccess dynamicRegistries, long seed, boolean generateStructures, boolean bonusChest, String generatorSettings) {
            Registry<Biome> biomeRegistry = dynamicRegistries.registryOrThrow(Registry.BIOME_REGISTRY);
            Registry<DimensionType> dimensionTypeRegistry = dynamicRegistries.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY);
            Registry<NoiseGeneratorSettings> dimensionSettingsRegistry = dynamicRegistries.registryOrThrow(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY);
            return new WorldGenSettings(seed, generateStructures, bonusChest,
                    WorldGenSettings.withOverworld(dimensionTypeRegistry,
                            getSkyblockSimpleRegistry(dimensionTypeRegistry, biomeRegistry, dimensionSettingsRegistry, seed),
                            createChunkGenerator(biomeRegistry, dimensionSettingsRegistry, seed, generatorSettings)));
        }
    }

    private static MappedRegistry<LevelStem> getSkyblockSimpleRegistry(Registry<DimensionType> p_242718_0_, Registry<Biome> p_242718_1_, Registry<NoiseGeneratorSettings> p_242718_2_, long p_242718_3_) {
        MappedRegistry<LevelStem> lvt_5_1_ = new MappedRegistry(Registry.DIMENSION_REGISTRY, Lifecycle.stable());
        lvt_5_1_.register(LevelStem.NETHER, new LevelStem(() -> {
            return (DimensionType)p_242718_0_.getOrThrow(DimensionType.NETHER_LOCATION);
        }, ConfigHandler.COMMON.voidNether.get() ? getNetherChunkGenerator(p_242718_1_, p_242718_2_, p_242718_3_) : getDefaultNetherChunkGenerator(p_242718_1_, p_242718_2_, p_242718_3_)), Lifecycle.stable());
        lvt_5_1_.register(LevelStem.END, new LevelStem(() -> {
            return (DimensionType)p_242718_0_.getOrThrow(DimensionType.END_LOCATION);
        }, ConfigHandler.COMMON.voidNether.get() ? getEndChunkGenerator(p_242718_1_, p_242718_2_, p_242718_3_) : getDefaultEndChunkGenerator(p_242718_1_, p_242718_2_, p_242718_3_)), Lifecycle.stable());
        return lvt_5_1_;
    }

    private static ChunkGenerator getEndChunkGenerator(Registry<Biome> p_63918_, Registry<NoiseGeneratorSettings> p_63919_, long p_63920_) {
        return new SkyblockChunkGenerator(new TheEndBiomeSource(p_63918_, p_63920_), p_63920_, () -> {
            return p_63919_.getOrThrow(NoiseGeneratorSettings.END);
        });
    }

    private static ChunkGenerator getNetherChunkGenerator(Registry<Biome> p_63943_, Registry<NoiseGeneratorSettings> p_63944_, long p_63945_) {
        return new SkyblockChunkGenerator(MultiNoiseBiomeSource.Preset.NETHER.biomeSource(p_63943_, p_63945_), p_63945_, () -> {
            return p_63944_.getOrThrow(NoiseGeneratorSettings.NETHER);
        });
    }

    private static ChunkGenerator getDefaultEndChunkGenerator(Registry<Biome> p_63918_, Registry<NoiseGeneratorSettings> p_63919_, long p_63920_) {
        return new NoiseBasedChunkGenerator(new TheEndBiomeSource(p_63918_, p_63920_), p_63920_, () -> {
            return p_63919_.getOrThrow(NoiseGeneratorSettings.END);
        });
    }

    private static ChunkGenerator getDefaultNetherChunkGenerator(Registry<Biome> p_63943_, Registry<NoiseGeneratorSettings> p_63944_, long p_63945_) {
        return new NoiseBasedChunkGenerator(MultiNoiseBiomeSource.Preset.NETHER.biomeSource(p_63943_, p_63945_), p_63945_, () -> {
            return p_63944_.getOrThrow(NoiseGeneratorSettings.NETHER);
        });
    }
}
