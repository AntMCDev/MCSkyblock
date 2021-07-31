package com.ant.mcskyblock.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import static com.ant.mcskyblock.utils.ResourceLocationHelper.prefix;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

public class SkyblockChunkGenerator extends ChunkGenerator {
    public static final Codec<SkyblockChunkGenerator> CODEC = RecordCodecBuilder.create((instance) -> instance.group(BiomeSource.CODEC.fieldOf("biome_source").forGetter((gen) -> gen.biomeSource), Codec.LONG.fieldOf("seed").stable().forGetter((gen) -> gen.seed), NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter((gen) -> gen.settings)).apply(instance, instance.stable(SkyblockChunkGenerator::new)));

    public static void init() {
        Registry.register(Registry.CHUNK_GENERATOR, prefix("skyblock"), SkyblockChunkGenerator.CODEC);
    }

    private final long seed;
    private final Supplier<NoiseGeneratorSettings> settings;

    public SkyblockChunkGenerator(BiomeSource provider, long seed, Supplier<NoiseGeneratorSettings> settings) {
        super(provider, provider, settings.get().structureSettings(), seed);
        this.seed = seed;
        this.settings = settings;
    }

    public static boolean isWorldSkyblock(Level world) {
        ((ServerLevel) world).getChunkSource();
        return ((ServerLevel) world).getChunkSource().generator instanceof SkyblockChunkGenerator;
    }

    @Override
    public WeightedRandomList<MobSpawnSettings.SpawnerData> getMobsAt(Biome p_158433_, StructureFeatureManager p_158434_, MobCategory p_158435_, BlockPos p_158436_) {
        WeightedRandomList<MobSpawnSettings.SpawnerData> spawns = net.minecraftforge.common.world.StructureSpawnManager.getStructureSpawns(p_158434_, p_158435_, p_158436_);
        if (spawns != null) return spawns;

        return super.getMobsAt(p_158433_, p_158434_, p_158435_, p_158436_);
    }

    @Override
    protected Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public ChunkGenerator withSeed(long l) {
        return new SkyblockChunkGenerator(this.biomeSource.withSeed(l), l, settings);
    }

    @Override
    public void buildSurfaceAndBedrock(WorldGenRegion worldGenRegion, ChunkAccess iChunk) {

    }

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Executor p_158463_, StructureFeatureManager p_158464_, ChunkAccess p_158465_) {
        return CompletableFuture.completedFuture(p_158465_);
    }

    @Override
    public int getBaseHeight(int p_158405_, int p_158406_, Heightmap.Types p_158407_, LevelHeightAccessor p_158408_) {
        return 0;
    }

    @Override
    public NoiseColumn getBaseColumn(int p_158401_, int p_158402_, LevelHeightAccessor p_158403_) {
        return new NoiseColumn(0, new BlockState[0]);
    }
}
