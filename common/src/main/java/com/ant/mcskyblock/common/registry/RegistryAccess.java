package com.ant.mcskyblock.common.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

import java.util.Optional;

/**
 * This call is used for registry accessing - it's designed to be overridden in platforms such as Forge, with
 * doesn't use the vanilla registries for modded blocks etc
 */
public class RegistryAccess {
    public static RegistryAccess INSTANCE = new RegistryAccess();

    public Block getBlock(ResourceLocation rs) {
        return BuiltInRegistries.BLOCK.get(rs);
    }

    public Item getItem(ResourceLocation rs) {
        return BuiltInRegistries.ITEM.get(rs);
    }

    public Biome getBiome(ResourceLocation rs) {
        Optional<ResourceKey<Biome>> optRs = VanillaRegistries.createLookup().lookupOrThrow(Registries.BIOME).listElementIds().filter(p -> p.location().equals(rs)).findFirst();
        if (optRs.isPresent()) {
            Optional<Holder.Reference<Biome>> optBiome = VanillaRegistries.createLookup().lookupOrThrow(Registries.BIOME).get(optRs.get());
            if (optBiome.isPresent()) {
                return optBiome.get().value();
            }
        }
        return null;
    }
}
