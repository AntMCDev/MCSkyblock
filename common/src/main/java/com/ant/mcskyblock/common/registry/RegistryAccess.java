package com.ant.mcskyblock.common.registry;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

/**
 * This call is used for registry accessing - it's designed to be overridden in platforms such as Forge, with
 * doesn't use the vanilla registries for modded blocks etc
 */
public class RegistryAccess {
    public static RegistryAccess INSTANCE = new RegistryAccess();

    public Block getBlock(ResourceLocation rs) {
        return Registry.BLOCK.get(rs);
    }

    public Item getItem(ResourceLocation rs) {
        return Registry.ITEM.get(rs);
    }

    public Biome getBiome(ResourceLocation rs) {
        return BuiltinRegistries.BIOME.get(rs);
    }
}
