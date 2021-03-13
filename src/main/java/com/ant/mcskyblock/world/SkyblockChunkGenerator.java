package com.ant.mcskyblock.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.*;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.server.ServerChunkProvider;

import static com.ant.mcskyblock.utils.ResourceLocationHelper.prefix;

import java.util.function.Supplier;

// Code adapted from Vaskii's Botania GoG source
// https://github.com/Vazkii/Botania
public class SkyblockChunkGenerator extends ChunkGenerator {
    public static final Codec<SkyblockChunkGenerator> CODEC = RecordCodecBuilder.create((instance) -> instance.group(BiomeProvider.CODEC.fieldOf("biome_source").forGetter((gen) -> gen.biomeProvider), Codec.LONG.fieldOf("seed").stable().forGetter((gen) -> gen.seed), DimensionSettings.field_236098_b_.fieldOf("settings").forGetter((gen) -> gen.settings)).apply(instance, instance.stable(SkyblockChunkGenerator::new)));

    public static void init() {
        Registry.register(Registry.CHUNK_GENERATOR_CODEC, prefix("skyblock"), SkyblockChunkGenerator.CODEC);
    }

    private final long seed;
    private final Supplier<DimensionSettings> settings;

    public SkyblockChunkGenerator(BiomeProvider provider, long seed, Supplier<DimensionSettings> settings) {
        super(provider, provider, settings.get().getStructures(), seed);
        this.seed = seed;
        this.settings = settings;
    }

    public static boolean isWorldSkyblock(World world) {
        return world.getChunkProvider() instanceof ServerChunkProvider && ((ServerChunkProvider)world.getChunkProvider()).getChunkGenerator() instanceof SkyblockChunkGenerator;
    }

    @Override
    protected Codec<? extends ChunkGenerator> func_230347_a_() {
        return CODEC;
    }

    @Override
    public ChunkGenerator func_230349_a_(long newSeed) {
        return new SkyblockChunkGenerator(this.biomeProvider.getBiomeProvider(newSeed), newSeed, settings);
    }

    @Override
    public void func_230352_b_(IWorld p_230352_1_, StructureManager p_230352_2_, IChunk p_230352_3_) {

    }

    @Override
    public void generateSurface(WorldGenRegion p_225551_1_, IChunk p_225551_2_) {

    }

    @Override
    public void func_230350_a_(long p_230350_1_, BiomeManager p_230350_3_, IChunk p_230350_4_, GenerationStage.Carving p_230350_5_) {

    }

    @Override
    public void func_230351_a_(WorldGenRegion p_230351_1_, StructureManager p_230351_2_) {

    }

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmapType) {
        return 0;
    }

    @Override
    public IBlockReader func_230348_a_(int p_230348_1_, int p_230348_2_) {
        return new Blockreader(new BlockState[0]);
    }
}
