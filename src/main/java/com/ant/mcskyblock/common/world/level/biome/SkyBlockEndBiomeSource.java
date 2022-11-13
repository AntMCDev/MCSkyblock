package com.ant.mcskyblock.common.world.level.biome;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.DensityFunction;

public class SkyBlockEndBiomeSource extends BiomeSource {
    public static final Codec<SkyBlockEndBiomeSource> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(RegistryOps.retrieveRegistry(Registry.BIOME_REGISTRY).forGetter((theEndBiomeSource) -> {
            return null;
        })).apply(instance, instance.stable(SkyBlockEndBiomeSource::new));
    });
    private final Holder<Biome> end;
    private final Holder<Biome> highlands;
    private final Holder<Biome> midlands;
    private final Holder<Biome> barrens;

    private final int chunkHalf = 8;

    public SkyBlockEndBiomeSource(Registry<Biome> registry) {
        this( registry.getOrCreateHolderOrThrow(Biomes.THE_END),
                registry.getOrCreateHolderOrThrow(Biomes.END_HIGHLANDS),
                registry.getOrCreateHolderOrThrow(Biomes.END_MIDLANDS),
                registry.getOrCreateHolderOrThrow(Biomes.END_BARRENS)
        );
    }

    private SkyBlockEndBiomeSource(Holder<Biome> end, Holder<Biome> highlands, Holder<Biome> midlands, Holder<Biome> barrens) {
        super(ImmutableList.of(end, highlands, midlands, barrens));
        this.end = end;
        this.highlands = highlands;
        this.midlands = midlands;
        this.barrens = barrens;
    }

    protected Codec<? extends BiomeSource> codec() {
        return CODEC;
    }

    public Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler sampler) {
        int startX = QuartPos.toBlock(x);
        int startY = QuartPos.toBlock(y);
        int startZ = QuartPos.toBlock(z);
        int xBlockSection = SectionPos.blockToSectionCoord(startX);
        int zBlockSection = SectionPos.blockToSectionCoord(startZ);
        // Is it less than 1000 blocks away ?
        if ((long)xBlockSection * (long)xBlockSection + (long)zBlockSection * (long)zBlockSection <= 4096L) {
            return this.end;
        } else {

            int finalX = (SectionPos.blockToSectionCoord(startX) * 2 + 1) * chunkHalf;
            int finalZ = (SectionPos.blockToSectionCoord(startZ) * 2 + 1) * chunkHalf;
            double erosion = sampler.erosion().compute(new DensityFunction.SinglePointContext(finalX, startY, finalZ));
            if (erosion > 0.25) {
                return this.highlands;
            } else if (erosion >= -0.0625) {
                return this.midlands;
            } else {
                // let's never return the islands and jsut reurn barrens ?
                // return erosion < -0.21875 ? this.islands : this.barrens;
                return this.barrens;
            }
        }
    }
}
