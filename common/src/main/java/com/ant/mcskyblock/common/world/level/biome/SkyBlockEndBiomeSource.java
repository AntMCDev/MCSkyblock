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
public class SkyBlockEndBiomeSource extends TheEndBiomeSource {
    public static final Codec<SkyBlockEndBiomeSource> CODEC = RecordCodecBuilder.create((RecordCodecBuilder.Instance<SkyBlockEndBiomeSource> instance) -> instance.group(RegistryOps.retrieveElement(Biomes.THE_END), RegistryOps.retrieveElement(Biomes.END_HIGHLANDS), RegistryOps.retrieveElement(Biomes.END_MIDLANDS), RegistryOps.retrieveElement(Biomes.SMALL_END_ISLANDS), RegistryOps.retrieveElement(Biomes.END_BARRENS)).apply(instance, instance.stable(SkyBlockEndBiomeSource::new)));
    private final Holder<Biome> barrens;

    public static SkyBlockEndBiomeSource create(HolderGetter<Biome> holderGetter) {
        return new SkyBlockEndBiomeSource(holderGetter.getOrThrow(Biomes.THE_END), holderGetter.getOrThrow(Biomes.END_HIGHLANDS), holderGetter.getOrThrow(Biomes.END_MIDLANDS), holderGetter.getOrThrow(Biomes.SMALL_END_ISLANDS), holderGetter.getOrThrow(Biomes.END_BARRENS));
    }

    private SkyBlockEndBiomeSource(Holder<Biome> holder, Holder<Biome> holder2, Holder<Biome> holder3, Holder<Biome> holder4, Holder<Biome> holder5) {
        super(holder, holder2, holder3, holder4, holder5);
        this.barrens = holder5;
    }

    @Override
    protected Codec<? extends BiomeSource> codec() {
        return CODEC;
    }

    @Override
    public Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler sampler) {
        Holder<Biome> biome = super.getNoiseBiome(x, y, z, sampler);
        return biome.is(Biomes.SMALL_END_ISLANDS) ? barrens : biome;
    }
}
