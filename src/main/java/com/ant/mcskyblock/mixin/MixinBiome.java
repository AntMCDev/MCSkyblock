package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.MCSkyBlock;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;
import java.util.function.Supplier;

@Mixin(Biome.class)
public class MixinBiome {
    @Final
    @Shadow
    private Map<Integer, List<StructureFeature<?>>> structures;

    public void generateFeatureStep(StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, ChunkRegion region, long populationSeed, ChunkRandom random, BlockPos origin, CallbackInfo ci) {
        if (MCSkyBlock.isSkyblockWorld(chunkGenerator)) {
            Registry<ConfiguredFeature<?, ?>> registry = region.getRegistryManager().get(Registry.CONFIGURED_FEATURE_KEY);
            Registry<StructureFeature<?>> registry2 = region.getRegistryManager().get(Registry.STRUCTURE_FEATURE_KEY);
            int i = GenerationStep.Feature.values().length;

            for (int j = 0; j < i; ++j) {
                int k = 0;
                CrashReportSection var10000;
                if (structureAccessor.shouldGenerateStructures()) {
                    List<StructureFeature<?>> list2 = (List) this.structures.getOrDefault(j, Collections.emptyList());

                    for (Iterator var15 = list2.iterator(); var15.hasNext(); ++k) {
                        StructureFeature<?> structureFeature = (StructureFeature) var15.next();
                        random.setDecoratorSeed(populationSeed, k, j);
                        int l = ChunkSectionPos.getSectionCoord(origin.getX());
                        int m = ChunkSectionPos.getSectionCoord(origin.getZ());
                        int n = ChunkSectionPos.getBlockCoord(l);
                        int o = ChunkSectionPos.getBlockCoord(m);
                        Supplier<String> supplier = () -> {
                            Optional var10001 = registry2.getKey(structureFeature).map(Object::toString);
                            Objects.requireNonNull(structureFeature);
                            return (String) var10001.orElseGet(structureFeature::toString);
                        };

                        try {
                            int p = region.getBottomY() + 1;
                            int q = region.getTopY() - 1;
                            region.method_36972(supplier);
                            structureAccessor.getStructuresWithChildren(ChunkSectionPos.from(origin), structureFeature).forEach((structureStart) -> {
                                structureStart.generateStructure(region, structureAccessor, chunkGenerator, random, new BlockBox(n, p, o, n + 15, q, o + 15), new ChunkPos(l, m));
                            });
                        } catch (Exception var24) {
                            CrashReport crashReport = CrashReport.create(var24, "Feature placement");
                            var10000 = crashReport.addElement("Feature");
                            Objects.requireNonNull(supplier);
                            var10000.add("Description", supplier::get);
                            throw new CrashException(crashReport);
                        }
                    }
                }
            }

            region.method_36972((Supplier) null);

            ci.cancel();
        }
    }
}
