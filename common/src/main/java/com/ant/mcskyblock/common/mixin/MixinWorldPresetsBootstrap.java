package com.ant.mcskyblock.common.mixin;

import com.ant.mcskyblock.common.SkyBlock;
import com.ant.mcskyblock.common.config.Config;
import com.ant.mcskyblock.common.world.level.biome.SkyBlockEndBiomeSource;
import com.ant.mcskyblock.common.world.level.levelgen.SkyBlockChunkGenerator;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.TheEndBiomeSource;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(targets = "net.minecraft.world.level.levelgen.presets.WorldPresets$Bootstrap")
public class MixinWorldPresetsBootstrap {
    @Final
    @Shadow
    private HolderGetter<Biome> biomes;

    @Final
    @Shadow
    private HolderGetter<NoiseGeneratorSettings> noiseSettings;

    @Final
    @Shadow
    Holder<DimensionType> overworldDimensionType;

    @Final
    @Shadow
    private BootstapContext<WorldPreset> context;

    @Inject(at = @At("RETURN"), method = "run")
    public void run(CallbackInfo ci) {
        // Dimension holder
        HolderGetter<DimensionType> dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);

        // Dimension types
        Holder.Reference<DimensionType> overworldDimensionType = dimensionTypes.getOrThrow(BuiltinDimensionTypes.OVERWORLD);
        Holder.Reference<DimensionType> netherDimensionType = dimensionTypes.getOrThrow(BuiltinDimensionTypes.NETHER);
        Holder.Reference<DimensionType> endDimensionType = dimensionTypes.getOrThrow(BuiltinDimensionTypes.END);

        // Noise settings
        Holder.Reference<NoiseGeneratorSettings> overworldNoise = this.noiseSettings.getOrThrow(NoiseGeneratorSettings.OVERWORLD);
        Holder.Reference<NoiseGeneratorSettings> netherNoise = this.noiseSettings.getOrThrow(NoiseGeneratorSettings.NETHER);
        Holder.Reference<NoiseGeneratorSettings> endNoise = this.noiseSettings.getOrThrow(NoiseGeneratorSettings.END);

        // Create level stems for skyblock
        LevelStem overworld = new LevelStem(overworldDimensionType, Config.INSTANCE.worldGen.IS_OVERWORLD_SKYBLOCK ?
                new SkyBlockChunkGenerator(MultiNoiseBiomeSource.Preset.OVERWORLD.biomeSource(this.biomes), overworldNoise) :
                new NoiseBasedChunkGenerator(MultiNoiseBiomeSource.Preset.OVERWORLD.biomeSource(this.biomes), overworldNoise)
        );
        LevelStem nether = new LevelStem(netherDimensionType, Config.INSTANCE.worldGen.IS_NETHER_SKYBLOCK ?
                new SkyBlockChunkGenerator(MultiNoiseBiomeSource.Preset.NETHER.biomeSource(this.biomes), netherNoise) :
                new NoiseBasedChunkGenerator(MultiNoiseBiomeSource.Preset.NETHER.biomeSource(this.biomes), netherNoise)
        );
        LevelStem end = new LevelStem(endDimensionType, Config.INSTANCE.worldGen.IS_END_SKYBLOCK ?
                new SkyBlockChunkGenerator(SkyBlockEndBiomeSource.create(this.biomes), endNoise) :
                new NoiseBasedChunkGenerator(TheEndBiomeSource.create(this.biomes), endNoise)
        );

        // Register preset
        SkyBlock.SB_PRESET_HOLDER = this.context.register(SkyBlock.SB_PRESET_KEY, new WorldPreset(Map.of(LevelStem.OVERWORLD, overworld, LevelStem.NETHER, nether, LevelStem.END, end)));
    }
}
