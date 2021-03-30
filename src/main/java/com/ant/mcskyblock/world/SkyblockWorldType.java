package com.ant.mcskyblock.world;

import com.ant.mcskyblock.config.ConfigHandler;
import com.mojang.serialization.Lifecycle;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.EndBiomeProvider;
import net.minecraft.world.biome.provider.NetherBiomeProvider;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraftforge.common.world.ForgeWorldType;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.function.Supplier;

// Code heavily adapted from Vaskii's Botania GoG source
// https://github.com/Vazkii/Botania
public class SkyblockWorldType extends ForgeWorldType {
    public static final SkyblockWorldType INSTANCE = new SkyblockWorldType();

    public SkyblockWorldType() {
        super((IComplexChunkGeneratorFactory) SkyblockWorldType::getChunkGenerator);
    }

    public static ChunkGenerator getChunkGenerator(@Nonnull Registry<Biome> biomeRegistry, @Nonnull Registry<DimensionSettings> dimensionSettingsRegistry, long seed) {
        return new SkyblockChunkGenerator(new OverworldBiomeProvider(seed, false, false, biomeRegistry), seed, () -> dimensionSettingsRegistry.getOrThrow(DimensionSettings.field_242734_c));
    }

    @Override
    public String getTranslationKey() {
        return "generator.mcskyblock";
    }

    public interface IComplexChunkGeneratorFactory extends IBasicChunkGeneratorFactory {
        @Override
        default DimensionGeneratorSettings createSettings(DynamicRegistries dynamicRegistries, long seed, boolean generateStructures, boolean bonusChest, String generatorSettings) {
            Registry<Biome> biomeRegistry = dynamicRegistries.getRegistry(Registry.BIOME_KEY);
            Registry<DimensionType> dimensionTypeRegistry = dynamicRegistries.getRegistry(Registry.DIMENSION_TYPE_KEY);
            Registry<DimensionSettings> dimensionSettingsRegistry = dynamicRegistries.getRegistry(Registry.NOISE_SETTINGS_KEY);
            return new DimensionGeneratorSettings(seed, generateStructures, bonusChest,
                    DimensionGeneratorSettings.func_242749_a(dimensionTypeRegistry,
                            getSkyblockSimpleRegistry(dimensionTypeRegistry, biomeRegistry, dimensionSettingsRegistry, seed),
                            createChunkGenerator(biomeRegistry, dimensionSettingsRegistry, seed, generatorSettings)));
        }
    }

    private static SimpleRegistry<Dimension> getSkyblockSimpleRegistry(Registry<DimensionType> p_242718_0_, Registry<Biome> p_242718_1_, Registry<DimensionSettings> p_242718_2_, long p_242718_3_) {
        SimpleRegistry<Dimension> lvt_5_1_ = new SimpleRegistry(Registry.DIMENSION_KEY, Lifecycle.stable());
        lvt_5_1_.register(Dimension.THE_NETHER, new Dimension(() -> {
            return (DimensionType)p_242718_0_.getOrThrow(DimensionType.THE_NETHER);
        }, ConfigHandler.COMMON.voidNether.get() ? getNetherChunkGenerator(p_242718_1_, p_242718_2_, p_242718_3_) : getDefaultNetherChunkGenerator(p_242718_1_, p_242718_2_, p_242718_3_)), Lifecycle.stable());
        lvt_5_1_.register(Dimension.THE_END, new Dimension(() -> {
            return (DimensionType)p_242718_0_.getOrThrow(DimensionType.THE_END);
        }, ConfigHandler.COMMON.voidNether.get() ? getEndChunkGenerator(p_242718_1_, p_242718_2_, p_242718_3_) : getDefaultEndChunkGenerator(p_242718_1_, p_242718_2_, p_242718_3_)), Lifecycle.stable());
        return lvt_5_1_;
    }

    private static ChunkGenerator getEndChunkGenerator(Registry<Biome> p_242717_0_, Registry<DimensionSettings> p_242717_1_, long p_242717_2_) {
        return new SkyblockChunkGenerator(new EndBiomeProvider(p_242717_0_, p_242717_2_), p_242717_2_, () -> {
            return (DimensionSettings)p_242717_1_.getOrThrow(DimensionSettings.field_242737_f);
        });
    }

    private static ChunkGenerator getNetherChunkGenerator(Registry<Biome> p_242720_0_, Registry<DimensionSettings> p_242720_1_, long p_242720_2_) {
        return new SkyblockChunkGenerator(NetherBiomeProvider.Preset.DEFAULT_NETHER_PROVIDER_PRESET.build(p_242720_0_, p_242720_2_), p_242720_2_, () -> {
            return (DimensionSettings)p_242720_1_.getOrThrow(DimensionSettings.field_242736_e);
        });
    }

    private static ChunkGenerator getDefaultEndChunkGenerator(Registry<Biome> lookUpRegistryBiome, Registry<DimensionSettings> settingsRegistry, long seed) {
        return new NoiseChunkGenerator(new EndBiomeProvider(lookUpRegistryBiome, seed), seed, () -> {
            return settingsRegistry.getOrThrow(DimensionSettings.field_242737_f);
        });
    }

    private static ChunkGenerator getDefaultNetherChunkGenerator(Registry<Biome> lookUpRegistryBiome, Registry<DimensionSettings> lookUpRegistryDimensionType, long seed) {
        return new NoiseChunkGenerator(NetherBiomeProvider.Preset.DEFAULT_NETHER_PROVIDER_PRESET.build(lookUpRegistryBiome, seed), seed, () -> {
            return lookUpRegistryDimensionType.getOrThrow(DimensionSettings.field_242736_e);
        });
    }
}
