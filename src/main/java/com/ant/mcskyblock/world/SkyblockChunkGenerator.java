package com.ant.mcskyblock.world;

import com.ant.mcskyblock.mixin.MixinChunkNoiseSamplerAccessor;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.structure.StructureSet;
import net.minecraft.util.Util;
import net.minecraft.util.dynamic.RegistryOps;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.RandomSeed;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.BiomeSupplier;
import net.minecraft.world.chunk.BelowZeroRetrogen;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.StructureWeightSampler;
import net.minecraft.world.gen.chunk.*;
import net.minecraft.world.gen.densityfunction.DensityFunction;
import net.minecraft.world.gen.densityfunction.DensityFunctions;
import net.minecraft.world.gen.noise.NoiseConfig;
import net.minecraft.world.gen.noise.NoiseRouter;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class SkyblockChunkGenerator extends ChunkGenerator {
    public static final Codec<SkyblockChunkGenerator> CODEC = RecordCodecBuilder.create((instance) -> {
        return createStructureSetRegistryGetter(instance).and(instance.group(RegistryOps.createRegistryCodec(Registry.NOISE_KEY).forGetter((generator) -> {
            return generator.noiseRegistry;
        }), BiomeSource.CODEC.fieldOf("biome_source").forGetter((generator) -> {
            return generator.biomeSource;
        }), ChunkGeneratorSettings.REGISTRY_CODEC.fieldOf("settings").forGetter((generator) -> {
            return generator.settings;
        }))).apply(instance, instance.stable(SkyblockChunkGenerator::new));
    });
    private static final BlockState AIR;
    protected final BlockState defaultBlock;
    private final Registry<DoublePerlinNoiseSampler.NoiseParameters> noiseRegistry;
    protected final RegistryEntry<ChunkGeneratorSettings> settings;
    private final AquiferSampler.FluidLevelSampler fluidLevelSampler;

    static {
        AIR = Blocks.AIR.getDefaultState();
    }

    public SkyblockChunkGenerator(Registry<StructureSet> structureSetRegistry, Registry<DoublePerlinNoiseSampler.NoiseParameters> noiseRegistry, BiomeSource populationSource, RegistryEntry<ChunkGeneratorSettings> registryEntry) {
        super(structureSetRegistry, Optional.empty(), populationSource);
        this.noiseRegistry = noiseRegistry;
        this.settings = registryEntry;
        ChunkGeneratorSettings chunkGeneratorSettings = (ChunkGeneratorSettings)this.settings.value();
        this.defaultBlock = chunkGeneratorSettings.defaultBlock();
        AquiferSampler.FluidLevel fluidLevel = new AquiferSampler.FluidLevel(DimensionType.MIN_HEIGHT * 2, Blocks.AIR.getDefaultState());
        this.fluidLevelSampler = (x, y, z) -> fluidLevel;
    }

    @Override
    protected Codec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }

    @Override
    public void carve(ChunkRegion chunkRegion, long seed, NoiseConfig noiseConfig, BiomeAccess world, StructureAccessor structureAccessor, Chunk chunk, GenerationStep.Carver carverStep) {

    }

    @Override
    public void buildSurface(ChunkRegion region, StructureAccessor structures, NoiseConfig noiseConfig, Chunk chunk) {

    }

    @Override
    public void populateEntities(ChunkRegion region) {
        ChunkPos chunkPos = region.getCenterPos();
        RegistryEntry<Biome> registryEntry = region.getBiome(chunkPos.getStartPos().withY(region.getTopY() - 1));
        ChunkRandom chunkRandom = new ChunkRandom(new CheckedRandom(RandomSeed.getSeed()));
        chunkRandom.setPopulationSeed(region.getSeed(), chunkPos.getStartX(), chunkPos.getStartZ());
        SpawnHelper.populateEntities(region, registryEntry, chunkPos, chunkRandom);
    }

    @Override
    public int getWorldHeight() {
        return this.settings.value().generationShapeConfig().height();
    }

    @Override
    public CompletableFuture<Chunk> populateNoise(Executor executor, Blender blender, NoiseConfig noiseConfig, StructureAccessor structureAccessor, Chunk chunk) {
        return CompletableFuture.completedFuture(chunk);
    }

    @Override
    public int getSeaLevel() {
        return 0;
    }

    @Override
    public int getMinimumY() {
        return ((ChunkGeneratorSettings)this.settings.value()).generationShapeConfig().minimumY();
    }

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmap, HeightLimitView world, NoiseConfig noiseConfig) {
        return 0;
    }

    @Override
    public VerticalBlockSample getColumnSample(int x, int z, HeightLimitView world, NoiseConfig noiseConfig) {
        return new VerticalBlockSample(0, new BlockState[0]);
    }

    @Override
    public void getDebugHudText(List<String> text, NoiseConfig noiseConfig, BlockPos pos) {
        DecimalFormat decimalFormat = new DecimalFormat("0.000");
        NoiseRouter noiseRouter = noiseConfig.getNoiseRouter();
        DensityFunction.UnblendedNoisePos unblendedNoisePos = new DensityFunction.UnblendedNoisePos(pos.getX(), pos.getY(), pos.getZ());
        double d = noiseRouter.ridges().sample(unblendedNoisePos);
        String var10001 = decimalFormat.format(noiseRouter.temperature().sample(unblendedNoisePos));
        text.add("NoiseRouter T: " + var10001 + " V: " + decimalFormat.format(noiseRouter.vegetation().sample(unblendedNoisePos)) + " C: " + decimalFormat.format(noiseRouter.continents().sample(unblendedNoisePos)) + " E: " + decimalFormat.format(noiseRouter.erosion().sample(unblendedNoisePos)) + " D: " + decimalFormat.format(noiseRouter.depth().sample(unblendedNoisePos)) + " W: " + decimalFormat.format(d) + " PV: " + decimalFormat.format((double) DensityFunctions.method_41546((float)d)) + " AS: " + decimalFormat.format(noiseRouter.initialDensityWithoutJaggedness().sample(unblendedNoisePos)) + " N: " + decimalFormat.format(noiseRouter.finalDensity().sample(unblendedNoisePos)));
    }

    public RegistryEntry<ChunkGeneratorSettings> getSettings() {
        return this.settings;
    }

    @Override
    public CompletableFuture<Chunk> populateBiomes(Registry<Biome> biomeRegistry, Executor executor, NoiseConfig noiseConfig, Blender blender, StructureAccessor structureAccessor, Chunk chunk) {
        return CompletableFuture.supplyAsync(Util.debugSupplier("init_biomes", () -> {
            this.populateBiomes(blender, noiseConfig, structureAccessor, chunk);
            return chunk;
        }), Util.getMainWorkerExecutor());
    }

    private void populateBiomes(Blender blender, NoiseConfig noiseConfig, StructureAccessor structureAccessor, Chunk chunk2) {
        ChunkNoiseSampler chunkNoiseSampler = chunk2.getOrCreateChunkNoiseSampler(chunk -> this.method_41537((Chunk)chunk, structureAccessor, blender, noiseConfig));
        BiomeSupplier biomeSupplier = BelowZeroRetrogen.getBiomeSupplier(blender.getBiomeSupplier(this.biomeSource), chunk2);
        chunk2.populateBiomes(biomeSupplier, ((MixinChunkNoiseSamplerAccessor)chunkNoiseSampler).createMultiNoiseSampler(noiseConfig.getNoiseRouter(), this.settings.value().spawnTarget()));
    }

    private ChunkNoiseSampler method_41537(Chunk chunk, StructureAccessor structureAccessor, Blender blender, NoiseConfig noiseConfig) {
        return ChunkNoiseSampler.create(chunk, noiseConfig, StructureWeightSampler.method_42695(structureAccessor, chunk.getPos()), this.settings.value(), this.fluidLevelSampler, blender);
    }
}
