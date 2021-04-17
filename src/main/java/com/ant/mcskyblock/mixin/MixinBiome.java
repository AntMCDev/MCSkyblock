package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.world.SkyblockChunkGenerator;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.ReportedException;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Mixin(Biome.class)
public class MixinBiome {
    @Shadow
    @Final
    private Map<Integer, List<Structure<?>>> biomeStructures;

    @Shadow
    @Final
    private BiomeGenerationSettings biomeGenerationSettings;

    @Inject(at = @At("HEAD"), method = "generateFeatures", cancellable = true)
    public void generateFeatures(StructureManager structureManager, ChunkGenerator chunkGenerator, WorldGenRegion worldGenRegion, long seed, SharedSeedRandom rand, BlockPos pos, CallbackInfo ci) {
        if (chunkGenerator instanceof SkyblockChunkGenerator) {
            List<List<Supplier<ConfiguredFeature<?, ?>>>> list = this.biomeGenerationSettings.getFeatures();
            int i = GenerationStage.Decoration.values().length;

            for (int j = 0; j < i; ++j) {
                int k = 0;
                if (structureManager.canGenerateFeatures()) {
                    for (Structure<?> structure : this.biomeStructures.getOrDefault(j, Collections.emptyList())) {
                        rand.setFeatureSeed(seed, k, j);
                        int l = pos.getX() >> 4;
                        int i1 = pos.getZ() >> 4;
                        int j1 = l << 4;
                        int k1 = i1 << 4;

                        try {
                            structureManager.func_235011_a_(SectionPos.from(pos), structure).forEach((structureStart) -> {
                                structureStart.func_230366_a_(worldGenRegion, structureManager, chunkGenerator, rand, new MutableBoundingBox(j1, k1, j1 + 15, k1 + 15), new ChunkPos(l, i1));
                            });
                        } catch (Exception exception) {
                            CrashReport crashreport = CrashReport.makeCrashReport(exception, "Feature placement");
                            crashreport.makeCategory("Feature").addDetail("Id", Registry.STRUCTURE_FEATURE.getKey(structure)).addDetail("Description", () -> {
                                return structure.toString();
                            });
                            throw new ReportedException(crashreport);
                        }

                        ++k;
                    }
                }
            }

            ci.cancel();
        }
    }
}
