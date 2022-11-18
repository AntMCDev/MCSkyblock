package com.ant.mcskyblock.common.world.level.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.level.biome.*;

public class SkyBlockEndBiomeSource extends TheEndBiomeSource {
    public static final Codec<SkyBlockEndBiomeSource> CODEC = RecordCodecBuilder.create((instance) -> instance
            .group(RegistryOps.retrieveRegistry(Registry.BIOME_REGISTRY).forGetter((theEndBiomeSource) -> null))
            .apply(instance, instance.stable(SkyBlockEndBiomeSource::new)));

    private final Holder<Biome> barrens;

    public SkyBlockEndBiomeSource(Registry<Biome> registry) {
        super(registry);
        this.barrens = registry.getOrCreateHolderOrThrow(Biomes.END_BARRENS);
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
