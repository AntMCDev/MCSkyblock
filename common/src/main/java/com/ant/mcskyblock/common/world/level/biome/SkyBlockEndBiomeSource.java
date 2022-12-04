package com.ant.mcskyblock.common.world.level.biome;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.DensityFunction;

/**
 * [COMMON] WORLD GENERATION - This is used to define a custom end biome provider that omits the small end islands
 */
public class SkyBlockEndBiomeSource extends BiomeSource {
    public static final Codec<SkyBlockEndBiomeSource> CODEC = RecordCodecBuilder.create((RecordCodecBuilder.Instance<SkyBlockEndBiomeSource> instance) -> instance.group(RegistryOps.retrieveElement(Biomes.THE_END), RegistryOps.retrieveElement(Biomes.END_HIGHLANDS), RegistryOps.retrieveElement(Biomes.END_MIDLANDS), RegistryOps.retrieveElement(Biomes.END_BARRENS)).apply(instance, instance.stable(SkyBlockEndBiomeSource::new)));
    private final Holder<Biome> end;
    private final Holder<Biome> highlands;
    private final Holder<Biome> midlands;
    private final Holder<Biome> barrens;

    public static SkyBlockEndBiomeSource create(HolderGetter<Biome> holderGetter) {
        return new SkyBlockEndBiomeSource(holderGetter.getOrThrow(Biomes.THE_END), holderGetter.getOrThrow(Biomes.END_HIGHLANDS), holderGetter.getOrThrow(Biomes.END_MIDLANDS), holderGetter.getOrThrow(Biomes.END_BARRENS));
    }

    private SkyBlockEndBiomeSource(Holder<Biome> holder, Holder<Biome> holder2, Holder<Biome> holder3, Holder<Biome> holder4) {
        super(ImmutableList.of(holder, holder2, holder3, holder4));
        this.end = holder;
        this.highlands = holder2;
        this.midlands = holder3;
        this.barrens = holder4;
    }

    @Override
    protected Codec<? extends BiomeSource> codec() {
        return CODEC;
    }

    @Override
    public Holder<Biome> getNoiseBiome(int i, int j, int k, Climate.Sampler sampler) {
        int p;
        int l = QuartPos.toBlock(i);
        int m = QuartPos.toBlock(j);
        int n = QuartPos.toBlock(k);
        int o = SectionPos.blockToSectionCoord(l);
        if ((long)o * (long)o + (long)(p = SectionPos.blockToSectionCoord(n)) * (long)p <= 4096L) {
            return this.end;
        }
        int q = (SectionPos.blockToSectionCoord(l) * 2 + 1) * 8;
        int r = (SectionPos.blockToSectionCoord(n) * 2 + 1) * 8;
        double d = sampler.erosion().compute(new DensityFunction.SinglePointContext(q, m, r));
        if (d > 0.25) {
            return this.highlands;
        }
        if (d >= -0.0625) {
            return this.midlands;
        }
        return this.barrens;
    }
}
