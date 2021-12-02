package com.ant.mcskyblock.world;

import com.ant.mcskyblock.mixin.MixinPoolAccessor;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.dynamic.RegistryLookupCodec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.NoiseColumnSampler;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.random.AtomicSimpleRandom;
import net.minecraft.world.gen.random.ChunkRandom;
import net.minecraft.world.gen.random.RandomSeed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

public class SkyblockChunkGenerator extends ChunkGenerator {
    public static final Codec<SkyblockChunkGenerator> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(BiomeSource.CODEC.fieldOf("biome_source").forGetter((skyblockChunkGenerator) -> {
            return skyblockChunkGenerator.populationSource;
        }), Codec.LONG.fieldOf("seed").stable().forGetter((skyblockChunkGenerator) -> {
            return skyblockChunkGenerator.seed;
        }), ChunkGeneratorSettings.REGISTRY_CODEC.fieldOf("settings").forGetter((skyblockChunkGenerator) -> {
            return skyblockChunkGenerator.settings;
        }), RegistryLookupCodec.of(Registry.NOISE_WORLDGEN).forGetter((noiseChunkGenerator) -> {
            return noiseChunkGenerator.noiseRegistry;
        })).apply(instance, instance.stable(SkyblockChunkGenerator::new));
    });
    private final long seed;
    private final Supplier<ChunkGeneratorSettings> settings;
    private final Registry<DoublePerlinNoiseSampler.NoiseParameters> noiseRegistry;
    private final NoiseColumnSampler noiseColumnSampler;

    public SkyblockChunkGenerator(BiomeSource biomeSource, long seed, Supplier<ChunkGeneratorSettings> settings, Registry<DoublePerlinNoiseSampler.NoiseParameters> noiseRegistry) {
        super(biomeSource, new StructuresConfig(true));
        this.seed = seed;
        this.settings = settings;
        this.noiseRegistry = noiseRegistry;
        ChunkGeneratorSettings chunkGeneratorSettings = (ChunkGeneratorSettings)this.settings.get();
        GenerationShapeConfig generationShapeConfig = chunkGeneratorSettings.getGenerationShapeConfig();
        this.noiseColumnSampler = new NoiseColumnSampler(generationShapeConfig, chunkGeneratorSettings.hasNoiseCaves(), seed, noiseRegistry, chunkGeneratorSettings.getRandomProvider());
    }

    @Override
    protected Codec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }

    @Override
    public ChunkGenerator withSeed(long seed) {
        return new SkyblockChunkGenerator(this.populationSource.withSeed(seed), seed, this.settings, this.noiseRegistry);
    }

    @Override
    public MultiNoiseUtil.MultiNoiseSampler getMultiNoiseSampler() {
        return this.noiseColumnSampler;
    }

    @Override
    public void carve(ChunkRegion chunkRegion, long seed, BiomeAccess biomeAccess, StructureAccessor structureAccessor, Chunk chunk, GenerationStep.Carver generationStep) {

    }

    @Override
    public void buildSurface(ChunkRegion region, StructureAccessor structures, Chunk chunk) {

    }

    @Override
    public CompletableFuture<Chunk> populateNoise(Executor executor, Blender blender, StructureAccessor structureAccessor, Chunk chunk) {
        return CompletableFuture.completedFuture(chunk);
    }

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmap, HeightLimitView world) {
        return 0;
    }

    @Override
    public VerticalBlockSample getColumnSample(int x, int z, HeightLimitView world) {
        return new VerticalBlockSample(0, new BlockState[0]);
    }

    @Override
    public int getSeaLevel() {
        return 0;
    }

    @Override
    public int getMinimumY() {
        return ((ChunkGeneratorSettings)this.settings.get()).getGenerationShapeConfig().minimumY();
    }

    @Override
    public Pool<SpawnSettings.SpawnEntry> getEntitySpawnList(Biome biome, StructureAccessor accessor, SpawnGroup group, BlockPos pos) {
        Pool<SpawnSettings.SpawnEntry> result = null;

        if (accessor.getStructureAt(pos, StructureFeature.SWAMP_HUT).hasChildren()) {
            if (group == SpawnGroup.MONSTER) {
                result = SwampHutFeature.MONSTER_SPAWNS;
            }

            if (group == SpawnGroup.CREATURE) {
                result = SwampHutFeature.CREATURE_SPAWNS;
            }
        }

        if (group == SpawnGroup.MONSTER && result == null) {
            if (accessor.getStructureAt(pos, StructureFeature.PILLAGER_OUTPOST).hasChildren()) {
                result = PillagerOutpostFeature.MONSTER_SPAWNS;
            }

            if (accessor.getStructureAt(pos, StructureFeature.MONUMENT).hasChildren() && result == null) {
                result = OceanMonumentFeature.MONSTER_SPAWNS;
            }

            if (accessor.getStructureAt(pos, StructureFeature.FORTRESS).hasChildren() && result == null) {
                result = NetherFortressFeature.MONSTER_SPAWNS;
            }
        }

        if (result == null) {
            result = group == SpawnGroup.UNDERGROUND_WATER_CREATURE && accessor.getStructureAt(pos, StructureFeature.MONUMENT).hasChildren() ? SpawnSettings.EMPTY_ENTRY_POOL : super.getEntitySpawnList(biome, accessor, group, pos);
        }
        List<SpawnSettings.SpawnEntry> mutableList = new ArrayList<>();
        for (SpawnSettings.SpawnEntry entry : result.getEntries()) {
            if (entry.type != EntityType.BAT) {
                mutableList.add(entry);
            }
        }
        ((MixinPoolAccessor<SpawnSettings.SpawnEntry>)result).setEntries(ImmutableList.copyOf((mutableList)));
        return result;
    }

    @Override
    public void populateEntities(ChunkRegion region) {
        ChunkPos chunkPos = region.getCenterPos();
        Biome biome = region.getBiome(chunkPos.getStartPos());
        ChunkRandom chunkRandom = new ChunkRandom(new AtomicSimpleRandom(RandomSeed.getSeed()));
        chunkRandom.setPopulationSeed(region.getSeed(), chunkPos.getStartX(), chunkPos.getStartZ());
        SpawnHelper.populateEntities(region, biome, chunkPos, chunkRandom);
    }

    @Override
    public int getWorldHeight() {
        return ((ChunkGeneratorSettings)this.settings.get()).getGenerationShapeConfig().height();
    }
}
