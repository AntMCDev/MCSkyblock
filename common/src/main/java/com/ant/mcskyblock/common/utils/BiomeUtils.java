package com.ant.mcskyblock.common.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeResolver;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.function.Predicate;

public class BiomeUtils {
    private static int quantize(int i) {
        return QuartPos.toBlock(QuartPos.fromBlock(i));
    }

    public static BlockPos quantize(BlockPos blockPos) {
        return new BlockPos(quantize(blockPos.getX()), quantize(blockPos.getY()), quantize(blockPos.getZ()));
    }

    public static BiomeResolver makeResolver(MutableInt mutableInt, ChunkAccess chunkAccess, BoundingBox boundingBox, Holder<Biome> holder, Predicate<Holder<Biome>> predicate) {
        return (i, j, k, sampler) -> {
            int l = QuartPos.toBlock(i);
            int m = QuartPos.toBlock(j);
            int n = QuartPos.toBlock(k);
            Holder<Biome> holder2 = chunkAccess.getNoiseBiome(i, j, k);
            if (boundingBox.isInside(l, m, n) && predicate.test(holder2)) {
                mutableInt.increment();
                return holder;
            } else {
                return holder2;
            }
        };
    }
}
