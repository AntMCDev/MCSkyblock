package com.ant.mcskyblock.common.world.level.levelgen;

import com.ant.mcskyblock.common.config.Config;
import com.ant.mcskyblock.common.world.level.levelgen.islandtype.IslandType;
import com.ant.mcskyblock.common.world.level.levelgen.islandtype.VoidIslandType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.ArrayList;
import java.util.Collection;

/**
 * [COMMON] WORLD GENERATION - This is used to generate the sub-islands, and also controls saving those down into NBT
 * data on change
 */
public class IslandGenerator {
    private static IslandSavedData islandSavedData = new IslandSavedData();

    private IslandGenerator () {}

    public static void generate(WorldGenRegion region, BlockPos pos) {
        String biome = region.getBiome(pos).unwrapKey().orElseThrow().location().getPath();
        if (canGenerate(biome, pos)) {
            if (region.getServer() != null) {
                islandSavedData = region.getServer().overworld().getDataStorage().computeIfAbsent(IslandSavedData::load, IslandSavedData::new, IslandSavedData.IDENTIFIER);
            }
            islandSavedData.put(new Island(biome, pos.getX(), pos.getY(), pos.getZ()).generate(region));
        }
    }

    private static boolean canGenerate(String biome, BlockPos pos) {
        return Config.INSTANCE.worldGen.GENERATE_SUB_ISLANDS && (Math.abs(pos.getX()) > Config.INSTANCE.worldGen.SUB_ISLAND_DISTANCE ||
                Math.abs(pos.getZ()) > Config.INSTANCE.worldGen.SUB_ISLAND_DISTANCE) && IslandSavedData.ISLANDS.stream().map(island -> island.biome).noneMatch(s -> s.equals(biome));
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
            IslandType b = BiomeIslands.SETTINGS.getOrDefault(biome, new VoidIslandType());
            BlockState base = b.getBase().defaultBlockState();
            BlockState fluid = b.getFluid().defaultBlockState();
            BlockState accessory = b.getAccessory().defaultBlockState();

            if (!(base.is(Blocks.AIR))) {
                int r = Config.INSTANCE.worldGen.SUB_ISLAND_RADIUS;
                for (int i = 0, d = Config.INSTANCE.worldGen.SUB_ISLAND_DEPTH; i < d; ++i) {
                    for (int j = -r + i; j <= r - i; ++j) {
                        for (int k = -r + i; k <= r - i; ++k) {
                            if (Math.pow(j, 2) + Math.pow(k, 2) < Math.pow(r - i, 2)) {
                                region.setBlock(new BlockPos(x + j, y - i, z + k), base, 0);
                            }
                        }
                    }
                }
            }

            if (!(fluid.is(Blocks.AIR))) {
                int r = Config.INSTANCE.worldGen.SUB_ISLAND_RADIUS - 1;
                for (int i = -r; i <= r; ++i) {
                    for (int j = -r; j <= r; ++j) {
                        if (Math.pow(i, 2) + Math.pow(j, 2) < Math.pow(r, 2)) {
                            region.setBlock(new BlockPos(x + i, y, z + j), fluid, 0);
                        }
                    }
                }
            }

            if (!(accessory.is(Blocks.AIR))) {
                if (b.getBase() instanceof DoublePlantBlock) {
                    DoublePlantBlock.placeAt(region, accessory, new BlockPos(x, y + 1, z), 0);
                } else {
                    region.setBlock(new BlockPos(x, y + 1, z), accessory, 0);
                }
            }
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
