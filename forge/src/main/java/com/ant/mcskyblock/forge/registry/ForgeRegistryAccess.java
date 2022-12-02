package com.ant.mcskyblock.forge.registry;

import com.ant.mcskyblock.common.registry.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class ForgeRegistryAccess extends RegistryAccess {
    @Override
    public Block getBlock(ResourceLocation rs) {
        Block block = super.getBlock(rs);
        return block == null ? ForgeRegistries.BLOCKS.getValue(rs) : block;
    }

    @Override
    public Item getItem(ResourceLocation rs) {
        Item item = super.getItem(rs);
        return item == null ? ForgeRegistries.ITEMS.getValue(rs) : item;
    }

    @Override
    public Biome getBiome(ResourceLocation rs) {
        Biome biome = super.getBiome(rs);
        return biome == null ? ForgeRegistries.BIOMES.getValue(rs) : biome;
    }
}
