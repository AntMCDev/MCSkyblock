package com.ant.mcskyblock.common.world.level.levelgen;

import com.ant.mcskyblock.common.SkyBlock;
import com.ant.mcskyblock.common.config.Config;
import com.ant.mcskyblock.common.world.level.structure.SkyBlockStructureTracker;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.minecraft.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.*;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.feature.GeodeFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * [COMMON] WORLD GENERATION - This is the chunk generator for skyblock worlds
 */
public class SkyBlockChunkGenerator extends NoiseBasedChunkGenerator {
    /**
     * CODEC for the skyblock chunk generator
     */
    public static final Codec<SkyBlockChunkGenerator> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                    BiomeSource.CODEC.fieldOf("biome_source").forGetter((skyBlockChunkGenerator) -> skyBlockChunkGenerator.biomeSource),
                    NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter((skyBlockChunkGenerator) -> skyBlockChunkGenerator.settings)
            ).apply(instance, instance.stable(SkyBlockChunkGenerator::new)));

    /**
     * This is the constructor for the skyblock chunk generator
     * @param biomeSource This is used to access biome information for the current level
     * @param holder This is used to contain the noise generation settings for the current level
     */
    public SkyBlockChunkGenerator(BiomeSource biomeSource, Holder<NoiseGeneratorSettings> holder) {
        super(biomeSource, holder);
    }

    /**
     * This returns a reference to the CODEC for this class instance
     *
     * REASON FOR OVERRIDING:
     * We have a custom CODEC for this chunk generator, so we need to return that.
     *
     * @return A pointer to the CODEC of this class instance
     */
    @Override
    protected Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    /**
     * This is used to get the base height of the level for the given heightmap type
     *
     * REASON FOR OVERRIDING:
     * The level generation is empty by default - so we want features and structures that make use of this to determine
     * placement to appear around the same Y co-ordinate for consistency.
     *
     * @param i X Co-ordinate
     * @param j Z Co-ordinate
     * @param types The type to use when calculating the base height (e.g: MOTION_BLOCKING)
     * @param levelHeightAccessor This is used to access level height information
     * @param randomState An instance of a random generator - used for generating randomness
     *
     * @return The base height of the level for the given heightmap type as a 32-bit integer
     */
    @Override
    public int getBaseHeight(int i, int j, Heightmap.Types types, LevelHeightAccessor levelHeightAccessor, RandomState randomState) {
        return this.settings.value().noiseSettings().height();
    }

    /**
     * This is used to build a base noise column for further modification down the line
     *
     * REASON FOR OVERRIDING:
     * We know all blocks will be air in a given chunk while building the noise chunk - so we can just return
     * an empty noise column for performance purposes.
     *
     * @param i X Co-ordinate
     * @param j Z Co-ordinate
     * @param levelHeightAccessor This is used to access level height information
     * @param randomState An instance of a random generator - used for generating randomness
     *
     * @return Returns a noise column that can be further modified in future steps
     */
    @Override
    public NoiseColumn getBaseColumn(int i, int j, LevelHeightAccessor levelHeightAccessor, RandomState randomState) {
        return new NoiseColumn(0, new BlockState[0]);
    }

    /**
     * This is used to carve out tunnels and caverns in the level
     *
     * REASON FOR OVERRIDING:
     * There's nothing to make tunnels in for a skyblock world as all blocks are air
     *
     * @param worldGenRegion This is used to access the server level information
     * @param l This is the world seed
     * @param randomState An instance of a random generator - used for generating randomness
     * @param biomeManager This is used to access biome information (e.g: biome at a given position)
     * @param structureManager This is used to access structure information
     * @param chunkAccess This is used to access information in a chunk
     * @param carving This is used to keep track of the current carving type: AIR / LIQUID
     */
    @Override
    public void applyCarvers(WorldGenRegion worldGenRegion, long l, RandomState randomState, BiomeManager biomeManager, StructureManager structureManager, ChunkAccess chunkAccess, GenerationStep.Carving carving) {}

    /**
     * This is used for building surface blocks at the very top of the solid chunk blocks
     *
     * REASON FOR OVERRIDING:
     * We don't need to have a surface in a skyblock world - but we can hook into here to generate our sub-islands.
     *
     * @param worldGenRegion This is used to access the server level information
     * @param structureManager This is used to access structure information
     * @param randomState An instance of a random generator - used for generating randomness
     * @param chunkAccess This is used to access information in a chunk
     */
    @Override
    public void buildSurface(WorldGenRegion worldGenRegion, StructureManager structureManager, RandomState randomState, ChunkAccess chunkAccess) {
        if (!SharedConstants.debugVoidTerrain(chunkAccess.getPos())) {
            IslandGenerator.generate(worldGenRegion, new BlockPos(
                    chunkAccess.getPos().getMinBlockX() + ((chunkAccess.getPos().getMaxBlockX() - chunkAccess.getPos().getMinBlockX()) / 2),
                    this.getSeaLevel(),
                    chunkAccess.getPos().getMinBlockZ() + ((chunkAccess.getPos().getMaxBlockZ() - chunkAccess.getPos().getMinBlockZ()) / 2)
            ));
        }
    }

    /**
     * This is used for building surface blocks at the very top of the solid chunk blocks
     *
     * REASON FOR OVERRIDING:
     * We don't need to have a surface in a skyblock world.
     *
     * @param chunkAccess This is used to access information in a chunk
     * @param worldGenerationContext A simple class that contains worldgen information such as min and max height
     * @param randomState An instance of a random generator - used for generating randomness
     * @param structureManager This is used to access structure information
     * @param biomeManager This is used to access biome information (e.g: biome at a given position)
     * @param registry This is the biome registry of the current world
     * @param blender This is used to create the noise chunk in NoiseChunk::forChunk
     */
    @Override
    public void buildSurface(ChunkAccess chunkAccess, WorldGenerationContext worldGenerationContext, RandomState randomState, StructureManager structureManager, BiomeManager biomeManager, Registry<Biome> registry, Blender blender) {}

    /**
     * This method is used to fill a chunk with blocks - using the supplied noise settings
     *
     * REASON FOR OVERRIDING:
     * We don't need to fill in the chunk with any blocks at all - outside of feature/structure generation and island
     * generation - so we can just return the CompletableFuture of the chunk without any noise modifications.
     *
     * @param executor This is passed into the executor service to generate the CompletableFuture
     * @param blender This is used to create the noise chunk in NoiseChunk::forChunk
     * @param randomState An instance of a random generator - used for generating randomness
     * @param structureManager This is used to access structure information
     * @param chunkAccess This is used to access information in a chunk
     *
     * @return A CompletableFuture for the current ChunkAccess position
     */
    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Executor executor, Blender blender, RandomState randomState, StructureManager structureManager, ChunkAccess chunkAccess) {
        return CompletableFuture.completedFuture(chunkAccess);
    }

    /**
     * This method constructs biome decorations for the given chunk. It is primarily responsible for the block placement
     * of features and structures.
     *
     * REASON FOR OVERRIDING:
     * We want to be able to stop the generation of feature and structure blocks, without having the underlying bounding
     * boxes not generate - for that reason, preventing structures any earlier in the generation logic will not suffice.
     *
     * @param worldGenLevel This is used to access the server level information
     * @param chunkAccess This is used to access information in a chunk
     * @param structureManager This is used to access structure information
     */
    @Override
    public void applyBiomeDecoration(WorldGenLevel worldGenLevel, ChunkAccess chunkAccess, StructureManager structureManager) {
        ChunkPos chunkPos2 = chunkAccess.getPos();
        if (SharedConstants.debugVoidTerrain(chunkPos2)) {
            return;
        }
        SectionPos sectionPos = SectionPos.of(chunkPos2, worldGenLevel.getMinSection());
        BlockPos blockPos = sectionPos.origin();
        Registry<Structure> registry = worldGenLevel.registryAccess().registryOrThrow(Registries.STRUCTURE);
        Map<Integer, List<Structure>> map = registry.stream().filter(s -> SkyBlockStructureTracker.isEnabled(registry.getResourceKey(s).orElseThrow())).collect(Collectors.groupingBy(structure -> structure.step().ordinal()));
        List<FeatureSorter.StepFeatureData> list = this.featuresPerStep.get();
        WorldgenRandom worldgenRandom = new WorldgenRandom(new XoroshiroRandomSource(RandomSupport.generateUniqueSeed()));
        long l = worldgenRandom.setDecorationSeed(worldGenLevel.getSeed(), blockPos.getX(), blockPos.getZ());
        ObjectArraySet<Holder<Biome>> set = new ObjectArraySet<>();
        ChunkPos.rangeClosed(sectionPos.chunk(), 1).forEach(chunkPos -> {
            ChunkAccess chunkAccess2 = worldGenLevel.getChunk(chunkPos.x, chunkPos.z);
            for (LevelChunkSection levelChunkSection : chunkAccess2.getSections()) {
                levelChunkSection.getBiomes().getAll(set::add);
            }
        });
        set.retainAll(this.biomeSource.possibleBiomes());
        int i = list.size();
        try {
            Registry<PlacedFeature> registry2 = worldGenLevel.registryAccess().registryOrThrow(Registries.PLACED_FEATURE);
            int j = Math.max(GenerationStep.Decoration.values().length, i);
            for (int k = 0; k < j; ++k) {
                int m = 0;
                if (structureManager.shouldGenerateStructures()) {
                    List<Structure> list2 = map.getOrDefault(k, Collections.emptyList());
                    for (Structure structure2 : list2) {
                        worldgenRandom.setFeatureSeed(l, m, k);
                        Supplier<String> supplier = () -> registry.getResourceKey(structure2).map(Object::toString).orElseGet(structure2::toString);
                        try {
                            worldGenLevel.setCurrentlyGenerating(supplier);
                            ChunkPos chunkPos = chunkAccess.getPos();
                            int bi = chunkPos.getMinBlockX();
                            int bj = chunkPos.getMinBlockZ();
                            LevelHeightAccessor levelHeightAccessor = chunkAccess.getHeightAccessorForGeneration();
                            int bk = levelHeightAccessor.getMinBuildHeight() + 1;
                            int bl = levelHeightAccessor.getMaxBuildHeight() - 1;
                            structureManager.startsForStructure(sectionPos, structure2).forEach(structureStart -> structureStart.placeInChunk(worldGenLevel, structureManager, this, worldgenRandom, new BoundingBox(bi, bk, bj, bi + 15, bl, bj + 15), chunkPos2));
                        }
                        catch (Exception exception) {
                            CrashReport crashReport = CrashReport.forThrowable(exception, "Feature placement");
                            crashReport.addCategory("Feature").setDetail("Description", supplier::get);
                            throw new ReportedException(crashReport);
                        }
                        ++m;
                    }
                }
                if (k >= i) continue;
                IntArraySet intSet = new IntArraySet();
                for (Holder<Biome> holder : set) {
                    List<HolderSet<PlacedFeature>> list3 = this.generationSettingsGetter.apply(holder).features();
                    if (k >= list3.size()) continue;
                    HolderSet<PlacedFeature> holderSet = list3.get(k);
                    FeatureSorter.StepFeatureData stepFeatureData = list.get(k);
                    holderSet.stream().map(Holder::value).forEach(placedFeature -> intSet.add(stepFeatureData.indexMapping().applyAsInt((PlacedFeature)placedFeature)));
                }
                int n = intSet.size();
                int[] is = intSet.toIntArray();
                Arrays.sort(is);
                FeatureSorter.StepFeatureData stepFeatureData2 = list.get(k);
                for (int o = 0; o < n; ++o) {
                    int p = is[o];
                    PlacedFeature placedFeature2 = stepFeatureData2.features().get(p);
                    Supplier<String> supplier2 = () -> registry2.getResourceKey(placedFeature2).map(Object::toString).orElseGet(placedFeature2::toString);
                    if (!(supplier2.get().contains("geode") && Config.INSTANCE.structures.GEODE_WEIGHT > 0)) {
                        continue;
                    }
                    worldgenRandom.setFeatureSeed(l, p, k);
                    try {
                        worldGenLevel.setCurrentlyGenerating(supplier2);
                        placedFeature2.placeWithBiomeCheck(worldGenLevel, this, worldgenRandom, blockPos);
                    }
                    catch (Exception exception2) {
                        CrashReport crashReport2 = CrashReport.forThrowable(exception2, "Feature placement");
                        crashReport2.addCategory("Feature").setDetail("Description", supplier2::get);
                        throw new ReportedException(crashReport2);
                    }
                }
            }
            worldGenLevel.setCurrentlyGenerating(null);
        }
        catch (Exception exception3) {
            CrashReport crashReport3 = CrashReport.forThrowable(exception3, "Biome decoration");
            crashReport3.addCategory("Generation").setDetail("CenterX", chunkPos2.x).setDetail("CenterZ", chunkPos2.z).setDetail("Seed", l);
            throw new ReportedException(crashReport3);
        }
    }
}
