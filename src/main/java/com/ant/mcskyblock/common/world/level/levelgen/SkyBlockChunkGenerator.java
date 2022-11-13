package com.ant.mcskyblock.common.world.level.levelgen;

import com.ant.mcskyblock.common.MCSkyBlock;
import com.google.common.annotations.VisibleForTesting;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.core.*;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeResolver;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.StructureAccess;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.placement.ConcentricRingsStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.apache.logging.log4j.Level;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.Stream;


// NON FABRIC
public class SkyBlockChunkGenerator extends NoiseBasedChunkGenerator {
    public static final Codec<SkyBlockChunkGenerator> CODEC = RecordCodecBuilder.create((instance) -> {
        return commonCodec(instance)
                .and(
                        instance.group(
                                RegistryOps.retrieveRegistry(Registry.NOISE_REGISTRY).forGetter( (SkyBlockChunkGenerator) -> {
                                    return SkyBlockChunkGenerator.noises;
                                }),
                                BiomeSource.CODEC.fieldOf("biome_source")
                                        .forGetter( (SkyBlockChunkGenerator) -> {
                                            return SkyBlockChunkGenerator.biomeSource;
                                        }),
                                NoiseGeneratorSettings.CODEC.fieldOf("settings")
                                        .forGetter( (SkyBlockChunkGenerator) -> {
                                            return SkyBlockChunkGenerator.settings;
                                        })
                        )
                ).apply( instance, instance.stable( SkyBlockChunkGenerator::new ));
    });

    private static final BlockState AIR;
    protected final BlockState defaultBlock;
    private final Registry<NormalNoise.NoiseParameters> noises;
    protected final Holder<NoiseGeneratorSettings> settings;
    private final Aquifer.FluidPicker globalFluidPicker;
    private static int seaLevel = 63;

    public SkyBlockChunkGenerator(Registry<StructureSet> registry,
                                  Registry<NormalNoise.NoiseParameters> registry2,
                                  BiomeSource biomeSource,
                                  Holder<NoiseGeneratorSettings> holder)
    {
        super(registry, registry2, biomeSource, holder);
        this.noises = registry2;
        this.settings = holder;
        NoiseGeneratorSettings noiseGeneratorSettings = this.settings.value();
        this.defaultBlock = noiseGeneratorSettings.defaultBlock();



        Aquifer.FluidStatus fluidStatus = new Aquifer.FluidStatus(-54, Blocks.LAVA.defaultBlockState());
        this.seaLevel = noiseGeneratorSettings.seaLevel();
        Aquifer.FluidStatus fluidStatus2 = new Aquifer.FluidStatus( seaLevel, noiseGeneratorSettings.defaultFluid());

        // FIXME
        this.globalFluidPicker = (j, k, l) -> {
            return k < Math.min(-54, seaLevel) ? fluidStatus : fluidStatus2;
        };



    }

    @Override
    public int getGenDepth() {
        return this.settings.value().noiseSettings().height();
    }

    @Override
    public int getSeaLevel() {
        return this.settings.value().seaLevel();
    }

    @Override
    public int getMinY() {
        return this.settings.value().noiseSettings().minY();
    }

    @Override
    protected Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public void addDebugScreenInfo(List<String> list, RandomState randomState, BlockPos blockPos) {
        DecimalFormat decimalFormat = new DecimalFormat("0.000");
        NoiseRouter noiseRouter = randomState.router();
        DensityFunction.SinglePointContext singlePointContext = new DensityFunction.SinglePointContext(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        double d = noiseRouter.ridges().compute(singlePointContext);
        list.add("Sky Block Generator"
                + " T: " + decimalFormat.format(noiseRouter.temperature().compute(singlePointContext))
                + " V: "  + decimalFormat.format(noiseRouter.vegetation().compute(singlePointContext))
                + " C: "  + decimalFormat.format(noiseRouter.continents().compute(singlePointContext))
                + " E: "  + decimalFormat.format(noiseRouter.erosion().compute(singlePointContext))
                + " D: "  + decimalFormat.format(noiseRouter.depth().compute(singlePointContext))
                + " W: "  + decimalFormat.format(d)
                + " PV: " + decimalFormat.format((double) NoiseRouterData.peaksAndValleys((float) d))
                + " AS: " + decimalFormat.format(noiseRouter.initialDensityWithoutJaggedness().compute(singlePointContext))
                + " N: "  + decimalFormat.format(noiseRouter.finalDensity().compute(singlePointContext)));
    }


    ///////////////////////////////
    // NOISE AND HEIGHTMAP
    ///////////////////////////////

    @Override
    public Holder<NoiseGeneratorSettings> generatorSettings() {
        return this.settings;
    }

    private NoiseChunk createNoiseChunk(ChunkAccess chunkAccess, StructureManager structureManager,
                                        Blender blender, RandomState randomState)
    {
        return NoiseChunk.forChunk(
                chunkAccess,
                randomState,
                Beardifier.forStructuresInChunk(structureManager, chunkAccess.getPos()),
                this.settings.value(),
                this.globalFluidPicker,
                blender
        );
    }

    @Override
    public boolean stable(ResourceKey<NoiseGeneratorSettings> resourceKey) {
        return this.settings.is(resourceKey);
    }

    @Override
    public int getBaseHeight(int i, int j, Heightmap.Types types, LevelHeightAccessor levelHeightAccessor, RandomState randomState) {
        return this.settings.value().noiseSettings().height();
    }
    @Override
    public NoiseColumn getBaseColumn(int i, int j, LevelHeightAccessor levelHeightAccessor, RandomState randomState) {
        return new NoiseColumn(0, new BlockState[0]);
    }

    ////////////////////////////
    // CAVES (DO NOTHING)
    ////////////////////////
    @Override
    public void applyCarvers(WorldGenRegion worldGenRegion, long l, RandomState randomState, BiomeManager biomeManager,
                             StructureManager structureManager, ChunkAccess chunkAccess, GenerationStep.Carving carving) {
    }

    ///////////////////////////////
    // SURFACE (DO NOTHING)
    ///////////////////////////////
    @Override
    public void buildSurface(WorldGenRegion worldGenRegion, StructureManager structureManager, RandomState randomState, ChunkAccess chunkAccess) {}
    @VisibleForTesting
    @Override
    public void buildSurface(ChunkAccess chunkAccess, WorldGenerationContext worldGenerationContext, RandomState randomState,
                             StructureManager structureManager, BiomeManager biomeManager, Registry<Biome> registry, Blender blender)
    {}

    ////////////////////////////////
    // FILL HEIGHTMAP (DO ALMOST NOTHING)
    ///////////////////////////////
    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Executor executor, Blender blender, RandomState randomState,
                                                        StructureManager structureManager, ChunkAccess chunkAccess)
    {
        return CompletableFuture.completedFuture(chunkAccess);
    }


    ///////////////////////////////
    // BIOMES
    ///////////////////////////////
    @Override
    public CompletableFuture<ChunkAccess> createBiomes(Registry<Biome> registry, Executor executor, RandomState randomState, Blender blender,
                                                       StructureManager structureManager, ChunkAccess chunkAccess) {

        return CompletableFuture.supplyAsync(Util.wrapThreadWithTaskName("init_biomes", () -> {
            this.doCreateBiomes(blender, randomState, structureManager, chunkAccess);
            return chunkAccess;
        }), Util.backgroundExecutor());
    }

    private void doCreateBiomes(Blender blender, RandomState randomState, StructureManager structureManager, ChunkAccess chunkAccess) {

        NoiseChunk noiseChunk = chunkAccess.getOrCreateNoiseChunk((chunkAccessx) -> {
            return this.createNoiseChunk(chunkAccessx, structureManager, blender, randomState);
        });
        BiomeResolver biomeResolver = BelowZeroRetrogen.getBiomeResolver(blender.getBiomeResolver(this.biomeSource), chunkAccess);
        chunkAccess.fillBiomesFromNoise(
                biomeResolver,
                noiseChunk.cachedClimateSampler(randomState.router(), this.settings.value().spawnTarget())
        );
    }

    ///////////////////////////////
    // MOBS
    ///////////////////////////////
    @Override
    public void spawnOriginalMobs(WorldGenRegion worldGenRegion) {
        if (!((NoiseGeneratorSettings) this.settings.value()).disableMobGeneration()) {
            ChunkPos chunkPos = worldGenRegion.getCenter();
            Holder<Biome> holder = worldGenRegion.getBiome(chunkPos.getWorldPosition().atY(worldGenRegion.getMaxBuildHeight() - 1));
            WorldgenRandom worldgenRandom = new WorldgenRandom(new LegacyRandomSource(RandomSupport.generateUniqueSeed()));
            worldgenRandom.setDecorationSeed(worldGenRegion.getSeed(), chunkPos.getMinBlockX(), chunkPos.getMinBlockZ());
            NaturalSpawner.spawnMobsForChunkGeneration(worldGenRegion, holder, chunkPos, worldgenRandom);
        }
    }


    static {
        AIR = Blocks.AIR.defaultBlockState();
    }


    ////////////////////////////////////////
    // STRUCTURES
    ///////////////////////////////////////

    @Override
    public Stream<Holder<StructureSet>> possibleStructureSets() {
        if (this.structureOverrides.isPresent()) {
            return this.structureOverrides.get().stream();
        }
        return this.structureSets.holders().map(Holder::hackyErase);
    }

    // this is the overall generation it runs at startup once before anything is "placed"
    @Override
    protected void generatePositions(RandomState randomState) {
        Set<Holder<Biome>> set = this.biomeSource.possibleBiomes();
        this.possibleStructureSets().forEach( holder -> {
            StructurePlacement structurePlacement;
            StructureSet structureSet = holder.value();
            boolean vaildStructure = false;
            for (StructureSet.StructureSelectionEntry structureSelectionEntry : structureSet.structures()) {
                Structure structure2 = structureSelectionEntry.structure().value();
                if (!structure2.biomes().stream().anyMatch(set::contains)){
                    continue;
                }
                this.placementsForStructure.computeIfAbsent( structure2, structure ->
                        new ArrayList()).add( structureSet.placement() );
                vaildStructure = true;
            }

            if (vaildStructure && (structurePlacement = structureSet.placement()) instanceof ConcentricRingsStructurePlacement) {
                ConcentricRingsStructurePlacement concentricRingsStructurePlacement = (ConcentricRingsStructurePlacement)structurePlacement;
                this.ringPositions.put(
                        concentricRingsStructurePlacement,
                        this.generateRingPositions(holder, randomState, concentricRingsStructurePlacement)
                );
            }
        });
    }

    @Override
    public void createStructures(RegistryAccess registryAccess,
                                 RandomState randomState,
                                 StructureManager structureManager,
                                 ChunkAccess chunkAccess,
                                 StructureTemplateManager structureTemplateManager,
                                 long seed)
    {
        ChunkPos chunkPos = chunkAccess.getPos();
        SectionPos sectionPos = SectionPos.bottomOf(chunkAccess);
        this.possibleStructureSets().forEach(holder -> {
            StructurePlacement structurePlacement = holder.value().placement();
            List<StructureSet.StructureSelectionEntry> list = holder.value().structures();
            for (StructureSet.StructureSelectionEntry structureSelectionEntry : list) {
                // is the structure in our map and enabled ?
                if (MCSkyBlock.STRUCTURE_TRACKER.isEnabled(structureSelectionEntry.structure())) {
                    StructureStart structureStart = structureManager.getStartForStructure(
                            sectionPos,
                            structureSelectionEntry.structure().value(),
                            chunkAccess
                    );
                    if (structureStart == null || !structureStart.isValid()) {
                        continue;
                    }
                }
                return;
            }
            if (!structurePlacement.isStructureChunk(this, randomState, seed, chunkPos.x, chunkPos.z)) {
                return;
            }

            //////////////////
            //   SINGLE
            //////////////////

            if (list.size() == 1) {
                this.tryGenerateStructure(
                        list.get(0),
                        structureManager,
                        registryAccess,
                        randomState,
                        structureTemplateManager,
                        seed,
                        chunkAccess,
                        chunkPos,
                        sectionPos);
                // MCSkyBlock.LOGGER.log(Level.INFO, "Added(SINGLE) Internal structure" + list.get(0).structure().value().toString());
                return;
            }

            ////////////////
            // Multi
            ///////////////
            ArrayList<StructureSet.StructureSelectionEntry> arrayList = new ArrayList<StructureSet.StructureSelectionEntry>(list.size());
            arrayList.addAll(list);
            WorldgenRandom worldgenRandom = new WorldgenRandom(new LegacyRandomSource(0L));
            worldgenRandom.setLargeFeatureSeed(seed, chunkPos.x, chunkPos.z);
            int structureWeight = 0;
            for (StructureSet.StructureSelectionEntry structureSelectionEntry2 : arrayList) {
                structureWeight += structureSelectionEntry2.weight();
            }
            while (!arrayList.isEmpty()) {
                int randInt = worldgenRandom.nextInt(structureWeight);
                int structureEntryCounter = 0;
                Iterator iterator = arrayList.iterator();
                while (iterator.hasNext() && (randInt -= ((StructureSet.StructureSelectionEntry) iterator.next()).weight()) >= 0) {
                    ++structureEntryCounter;
                }
                StructureSet.StructureSelectionEntry structureSelectionEntry4 = arrayList.get(structureEntryCounter);
                if (this.tryGenerateStructure(
                        structureSelectionEntry4,
                        structureManager,
                        registryAccess,
                        randomState,
                        structureTemplateManager,
                        seed,
                        chunkAccess,
                        chunkPos,
                        sectionPos)
                ) {
                    // MCSkyBlock.LOGGER.log(Level.INFO, "Added(MULTI) Internal structure" + structureSelectionEntry4.structure().value().toString());
                    return;
                }
                arrayList.remove(structureEntryCounter);
                structureWeight -= structureSelectionEntry4.weight();
            }
        });
    }

    @Override
    public void createReferences(WorldGenLevel worldGenLevel, StructureManager structureManager, ChunkAccess chunkAccess){
        ChunkPos chunkPos = chunkAccess.getPos();
        int chunkX = chunkPos.x;
        int chunkZ = chunkPos.z;
        int chunkPosMinBlockX = chunkPos.getMinBlockX();
        int chunkPosMinBlockZ = chunkPos.getMinBlockZ();
        SectionPos sectionPos = SectionPos.bottomOf(chunkAccess);
        for (int n = chunkX - 8; n <= chunkX + 8; ++n) {
            for (int o = chunkZ - 8; o <= chunkZ + 8; ++o) {
                long p = ChunkPos.asLong(n, o);
                for (StructureStart structureStart : worldGenLevel.getChunk(n, o).getAllStarts().values()) {
                    try {
                        if (!structureStart.isValid() || !structureStart.getBoundingBox().intersects(
                                chunkPosMinBlockX,
                                chunkPosMinBlockZ,
                                chunkPosMinBlockX + 15,
                                chunkPosMinBlockZ + 15)) continue;
                        structureManager.addReferenceForStructure(sectionPos, structureStart.getStructure(), p, chunkAccess);
                        DebugPackets.sendStructurePacket(worldGenLevel, structureStart);
                    }
                    catch (Exception exception) {
                        CrashReport crashReport = CrashReport.forThrowable(exception, "Generating structure reference");
                        CrashReportCategory crashReportCategory = crashReport.addCategory("Structure");
                        Optional<Registry<Structure>> optional = (Optional<Registry<Structure>>)worldGenLevel.registryAccess().registry(Registry.STRUCTURE_REGISTRY);
                        crashReportCategory.setDetail("Id", () ->
                                optional.map( registry -> registry.getKey(structureStart.getStructure()).toString())
                                        .orElse("UNKNOWN")
                        );
                        crashReportCategory.setDetail("Name", () ->
                                Registry.STRUCTURE_TYPES.getKey(structureStart.getStructure().type()).toString()
                        );
                        crashReportCategory.setDetail("Class", () ->
                                structureStart.getStructure().getClass().getCanonicalName()
                        );
                        throw new ReportedException(crashReport);
                    }
                }
            }
        }
    }
}
