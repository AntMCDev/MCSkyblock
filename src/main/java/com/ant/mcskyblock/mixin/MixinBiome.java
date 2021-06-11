package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.MCSkyblock;
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
import net.minecraft.world.gen.feature.structure.StructureStart;
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
    private Map<Integer, List<Structure<?>>> structuresByStep;

    @Shadow
    @Final
    private BiomeGenerationSettings generationSettings;

    @Inject(at = @At("HEAD"), method = "generate", cancellable = true)
    public void generate(StructureManager p_242427_1_, ChunkGenerator p_242427_2_, WorldGenRegion p_242427_3_, long p_242427_4_, SharedSeedRandom p_242427_6_, BlockPos p_242427_7_, CallbackInfo ci) {
        if (p_242427_2_ instanceof SkyblockChunkGenerator) {
            int i = GenerationStage.Decoration.values().length;

            for (int j = 0; j < i; ++j) {
                int k = 0;
                if (p_242427_1_.shouldGenerateFeatures()) {
                    for (Structure<?> structure : this.structuresByStep.getOrDefault(j, Collections.emptyList())) {
                        p_242427_6_.setFeatureSeed(p_242427_4_, k, j);
                        int l = p_242427_7_.getX() >> 4;
                        int i1 = p_242427_7_.getZ() >> 4;
                        int j1 = l << 4;
                        int k1 = i1 << 4;

                        try {
                            p_242427_1_.startsForFeature(SectionPos.of(p_242427_7_), structure).forEach((structureStart) -> {
                                structureStart.placeInChunk(p_242427_3_, p_242427_1_, p_242427_2_, p_242427_6_, new MutableBoundingBox(j1, k1, j1 + 15, k1 + 15), new ChunkPos(l, i1));
                            });
                        } catch (Exception exception) {
                            CrashReport crashreport = CrashReport.forThrowable(exception, "Feature placement");
                            crashreport.addCategory("Feature").setDetail("Id", Registry.STRUCTURE_FEATURE.getKey(structure)).setDetail("Description", () -> {
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
