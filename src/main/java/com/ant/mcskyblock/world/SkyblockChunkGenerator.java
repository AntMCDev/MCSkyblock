package com.ant.mcskyblock.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;

import static com.ant.mcskyblock.utils.ResourceLocationHelper.prefix;

import java.util.List;
import java.util.function.Supplier;

// Code adapted from Vaskii's Botania GoG source
// https://github.com/Vazkii/Botania
public class SkyblockChunkGenerator extends ChunkGenerator {
    public static final Codec<SkyblockChunkGenerator> CODEC = RecordCodecBuilder.create((instance) -> instance.group(BiomeProvider.CODEC.fieldOf("biome_source").forGetter((gen) -> gen.biomeSource), Codec.LONG.fieldOf("seed").stable().forGetter((gen) -> gen.seed), DimensionSettings.CODEC.fieldOf("settings").forGetter((gen) -> gen.settings)).apply(instance, instance.stable(SkyblockChunkGenerator::new)));

    public static void init() {
        Registry.register(Registry.CHUNK_GENERATOR, prefix("skyblock"), SkyblockChunkGenerator.CODEC);
    }

    private final long seed;
    private final Supplier<DimensionSettings> settings;

    public SkyblockChunkGenerator(BiomeProvider provider, long seed, Supplier<DimensionSettings> settings) {
        super(provider, provider, settings.get().structureSettings(), seed);
        this.seed = seed;
        this.settings = settings;
    }

    public static boolean isWorldSkyblock(World world) {
        return ((ServerWorld) world).getChunkSource() instanceof ServerChunkProvider && ((ServerWorld) world).getChunkSource().generator instanceof SkyblockChunkGenerator;
    }

    @Override
    public void applyCarvers(long p_230350_1_, BiomeManager p_230350_3_, IChunk p_230350_4_, GenerationStage.Carving p_230350_5_) {

    }

    @Override
    public List<MobSpawnInfo.Spawners> getMobsAt(Biome p_230353_1_, StructureManager p_230353_2_, EntityClassification p_230353_3_, BlockPos p_230353_4_) {
        List<MobSpawnInfo.Spawners> spawns = net.minecraftforge.common.world.StructureSpawnManager.getStructureSpawns(p_230353_2_, p_230353_3_, p_230353_4_);
        if (spawns != null) return spawns;

        return super.getMobsAt(p_230353_1_, p_230353_2_, p_230353_3_, p_230353_4_);
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
    public void buildSurfaceAndBedrock(WorldGenRegion worldGenRegion, IChunk iChunk) {

    }

    @Override
    public void fillFromNoise(IWorld iWorld, StructureManager structureManager, IChunk iChunk) {

    }

    @Override
    public int getBaseHeight(int i, int i1, Heightmap.Type type) {
        return 0;
    }

    @Override
    public IBlockReader getBaseColumn(int i, int i1) {
        return new Blockreader(new BlockState[0]);
    }
}
