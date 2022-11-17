package com.ant.mcskyblock.common.world.level.levelgen;

import com.ant.mcskyblock.common.MCSkyBlock;
import com.ant.mcskyblock.common.config.SkyBlockConfigManager;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.ArrayList;
import java.util.Collection;

public class IslandGenerator {
    private static IslandSavedData islandSavedData = new IslandSavedData();

    private IslandGenerator () {}

    public static void generate(WorldGenRegion region, BlockPos pos) {
        if (region.getServer() != null) {
            islandSavedData = region.getServer().overworld().getDataStorage().computeIfAbsent(IslandSavedData::load, IslandSavedData::new, IslandSavedData.IDENTIFIER);
        }

        String biome = region.getBiome(pos).unwrapKey().orElseThrow().location().getPath();
        if (canGenerate(biome, pos)) {
            islandSavedData.put(new Island(biome, pos.getX(), pos.getY(), pos.getZ()).generate(region));
        }
    }

    private static boolean canGenerate(String biome, BlockPos pos) {
        return SkyBlockConfigManager.generateIslands() && (Math.abs(pos.getX()) > 1024 || Math.abs(pos.getZ()) > 1024) && IslandSavedData.ISLANDS.stream().map(island -> island.biome).noneMatch(s -> s.equals(biome));
    }

    public static BlockPos nearest(BlockPos pos, String biome) {
        int x = pos.getX();
        int z = pos.getZ();

        int min = Integer.MAX_VALUE;
        Island minIsland = null;
        Collection<Island> islands = IslandSavedData.ISLANDS.stream().filter(island -> island.biome.equals(biome)).toList();
        for (Island i : islands) {
            int dist = Math.abs(i.x - x) + Math.abs(i.z - z);
            if (dist < min) {
                min = dist;
                minIsland = i;
            }
        }
        return minIsland == null ? null : new BlockPos(minIsland.x, minIsland.y, minIsland.z).immutable();
    }

    public record Island(String biome, int x, int y, int z) {
        public Tag toCompoundTag() {
            CompoundTag tag = new CompoundTag();
            tag.putString("biome", biome);
            tag.putIntArray("pos", new int[]{x, y, z});
            return tag;
        }

        public static Island fromCompoundTag(CompoundTag compoundTag) {
            int[] pos = compoundTag.getIntArray("pos");
            return new Island(compoundTag.getString("biome"), pos[0], pos[1], pos[2]);
        }

        public Island generate(WorldGenRegion region) {
            Pair<Block, Block> b = BiomeIslands.SETTINGS.getOrDefault(biome, new Pair<>(Blocks.AIR, Blocks.AIR));
            int r = SkyBlockConfigManager.subIslandRadius();
            for (int i = 0, d = SkyBlockConfigManager.subIslandDepth(); i < d; ++i) {
                for (int j = -r+i; j <= r-i; ++j) {
                    for (int k = -r+i; k <= r-i; ++k) {
                        if (Math.pow(j, 2) + Math.pow(k, 2) < Math.pow(r-i, 2)) {
                            region.setBlock(new BlockPos(x + j, y - i, z + k), b.getFirst().defaultBlockState(), 0);
                        }
                    }
                }
            }
            region.setBlock(new BlockPos(x, y+1, z), b.getSecond().defaultBlockState(), 0);
            return this;
        }
    }

    public static class IslandSavedData extends SavedData {
        private static final Collection<Island> ISLANDS = new ArrayList<>();
        private static final String IDENTIFIER = "islands";

        public void put(Island island) {
            ISLANDS.add(island);
            this.setDirty();
        }

        @Override
        public CompoundTag save(CompoundTag compoundTag) {
            ListTag islands = new ListTag();
            for (Island island : ISLANDS) {
                islands.add(island.toCompoundTag());
            }
            compoundTag.put(IDENTIFIER, islands);
            return compoundTag;
        }

        private static IslandSavedData load(CompoundTag compoundTag) {
            ISLANDS.clear();
            ListTag islands = compoundTag.getList(IDENTIFIER, 0);
            islands.forEach(island -> {
                ISLANDS.add(Island.fromCompoundTag((CompoundTag)island));
            });
            return new IslandSavedData();
        }
    }
}
