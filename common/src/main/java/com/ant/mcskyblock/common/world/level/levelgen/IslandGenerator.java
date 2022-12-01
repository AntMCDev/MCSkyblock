package com.ant.mcskyblock.common.world.level.levelgen;

import com.ant.mcskyblock.common.config.Config;
import com.ant.mcskyblock.common.world.level.levelgen.islandtype.IslandType;
import com.ant.mcskyblock.common.world.level.levelgen.islandtype.VoidIslandType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * [COMMON] WORLD GENERATION - This is used to generate the sub-islands, and also controls saving those down into NBT
 * data on change
 */
public class IslandGenerator {
    private static IslandSavedData islandSavedData = new IslandSavedData();

    private IslandGenerator () {}

    public static void init(ServerLevel level) {
        IslandSavedData.ISLANDS.clear();
        islandSavedData = level.getDataStorage().computeIfAbsent(IslandSavedData::load, IslandSavedData::new, IslandSavedData.IDENTIFIER);
        islandSavedData.setDirty(true);
    }

    public static void generate(WorldGenRegion region, BlockPos pos) {
        String biome = region.getBiome(pos).unwrapKey().orElseThrow().location().getPath();
        if (canGenerateSubIsland(biome, pos)) {
            islandSavedData.put(new Island(biome, pos.getX(), pos.getY(), pos.getZ()).generate(region));
        }
    }

    public static boolean generatePlayerIsland(Level level, BlockPos pos, String uuid) {
        if (canGeneratePlayerIsland(level, pos, uuid)) {
            islandSavedData.put(new PlayerIsland(uuid, pos.getX(), pos.getY(), pos.getZ()).generate(level));
            return true;
        }
        return false;
    }

    public static long playerIslandCount() {
        return IslandSavedData.ISLANDS.stream().filter(island -> island instanceof PlayerIsland).count();
    }

    public static boolean hasPlayerIsland(String uuid) {
        return IslandSavedData.ISLANDS.stream().anyMatch(island -> island instanceof PlayerIsland && island.uuid.equals(uuid));
    }

    private static boolean canGenerateSubIsland(String uuid, BlockPos pos) {
        return Config.INSTANCE.worldGen.GENERATE_SUB_ISLANDS && (Math.abs(pos.getX()) > Config.INSTANCE.worldGen.SUB_ISLAND_DISTANCE ||
                Math.abs(pos.getZ()) > Config.INSTANCE.worldGen.SUB_ISLAND_DISTANCE) && IslandSavedData.ISLANDS.stream().filter(island -> !(island instanceof PlayerIsland)).map(island -> island.uuid).noneMatch(s -> s.equals(uuid));
    }

    private static boolean canGeneratePlayerIsland(Level level, BlockPos pos, String uuid) {
        return Config.INSTANCE.worldGen.GENERATE_MAIN_ISLAND && IslandSavedData.ISLANDS.stream().filter(island -> island instanceof PlayerIsland).map(island -> island.uuid).noneMatch(s -> s.equals(uuid)) && !doesCollide(level, pos);
    }

    public static BlockPos islandPosition(String uuid) {
        Optional<Island> island = IslandSavedData.ISLANDS.stream().filter(i -> i.uuid.equals(uuid)).findFirst();
        return island.map(value -> new BlockPos(value.x + 0.5, value.y + 1.6, value.z + 0.5)).orElse(null);
    }

    private static boolean doesCollide(Level level, BlockPos pos) {
        boolean collides = false;

        out: for (int x = pos.getX() - Config.INSTANCE.worldGen.MAIN_ISLAND_RADIUS; x <= pos.getX() + Config.INSTANCE.worldGen.MAIN_ISLAND_RADIUS; x++) {
            for (int y = pos.getY() - (Config.INSTANCE.worldGen.MAIN_ISLAND_TREE ? PlayerIsland.tree.length : 0) - 1 - Config.INSTANCE.worldGen.MAIN_ISLAND_DEPTH; y <= pos.getY(); y++) {
                for (int z = pos.getZ() - Config.INSTANCE.worldGen.MAIN_ISLAND_RADIUS; z <= pos.getZ() + Config.INSTANCE.worldGen.MAIN_ISLAND_RADIUS; z++) {
                    collides = !level.getBlockState(new BlockPos(x, y, z)).is(Blocks.AIR);
                    if (collides) { break out; }
                }
            }
        }
        return collides;
    }

    public static BlockPos nearest(BlockPos pos, String biome) {
        int x = pos.getX();
        int z = pos.getZ();

        int min = Integer.MAX_VALUE;
        Island minIsland = null;
        Collection<Island> islands = IslandSavedData.ISLANDS.stream().filter(island -> island.uuid.equals(biome)).toList();
        for (Island i : islands) {
            int dist = Math.abs(i.x - x) + Math.abs(i.z - z);
            if (dist < min) {
                min = dist;
                minIsland = i;
            }
        }
        return minIsland == null ? null : new BlockPos(minIsland.x, minIsland.y, minIsland.z).immutable();
    }

    public static class Island {
        protected String uuid;
        protected int x, y, z;

        public Island(String uuid, int x, int y, int z) {
            this.uuid = uuid;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Tag toCompoundTag() {
            CompoundTag tag = new CompoundTag();
            tag.putString("biome", uuid);
            tag.putIntArray("pos", new int[]{x, y, z});
            return tag;
        }

        public static Island fromCompoundTag(CompoundTag compoundTag) {
            int[] pos = compoundTag.getIntArray("pos");
            if (compoundTag.contains("biome")) {
                return new Island(compoundTag.getString("biome"), pos[0], pos[1], pos[2]);
            } else if (compoundTag.contains("player")) {
                return new PlayerIsland(compoundTag.getString("player"), pos[0], pos[1], pos[2]);
            } else {
                return null;
            }
        }

        public Island generate(WorldGenRegion region) {
            IslandType b = BiomeIslands.SETTINGS.getOrDefault(uuid, new VoidIslandType());
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

    public static class PlayerIsland extends Island {
        private static final char[][][] tree = new char[][][]{
                {
                        {' ', ' ', ' ', ' ', ' '},
                        {' ', ' ', 'L', ' ', ' '},
                        {' ', 'L', 'L', 'L', ' '},
                        {' ', ' ', 'L', ' ', ' '},
                        {' ', ' ', ' ', ' ', ' '}
                },
                {
                        {' ', ' ', ' ', ' ', ' '},
                        {' ', 'L', 'L', 'L', ' '},
                        {' ', 'L', 'W', 'L', ' '},
                        {' ', 'L', 'L', 'L', ' '},
                        {' ', ' ', ' ', ' ', ' '}
                },
                {
                        {' ', 'L', 'L', 'L', ' '},
                        {'L', 'L', 'L', 'L', 'L'},
                        {'L', 'L', 'W', 'L', 'L'},
                        {'L', 'L', 'L', 'L', 'L'},
                        {' ', 'L', 'L', 'L', ' '}
                },
                {
                        {'L', 'L', 'L', 'L', 'L'},
                        {'L', 'L', 'L', 'L', 'L'},
                        {'L', 'L', 'W', 'L', 'L'},
                        {'L', 'L', 'L', 'L', 'L'},
                        {'L', 'L', 'L', 'L', 'L'}
                },
                {
                        {' ', ' ', ' ', ' ', ' '},
                        {' ', ' ', ' ', ' ', ' '},
                        {' ', ' ', 'W', ' ', ' '},
                        {' ', ' ', ' ', ' ', ' '},
                        {' ', ' ', ' ', ' ', ' '}
                },
                {
                        {' ', ' ', ' ', ' ', ' '},
                        {' ', ' ', ' ', ' ', ' '},
                        {' ', ' ', 'W', ' ', ' '},
                        {' ', ' ', ' ', ' ', ' '},
                        {' ', ' ', ' ', ' ', ' '}
                }
        };

        public PlayerIsland(String uuid, int x, int y, int z) {
            super(uuid, x, y, z);
        }

        @Override
        public Tag toCompoundTag() {
            CompoundTag tag = new CompoundTag();
            tag.putString("player", uuid);
            tag.putIntArray("pos", new int[]{x, y, z});
            return tag;
        }

        public Island generate(Level region) {
            int offset = -2;

            if(Config.INSTANCE.worldGen.MAIN_ISLAND_TREE) {
                for (int i = 0; i < tree.length; i++) {
                    for (int j = 0; j < tree[i].length; j++) {
                        for (int k = 0; k < tree[i][j].length; k++) {
                            switch (tree[i][j][k]) {
                                case 'L':
                                    region.setBlockAndUpdate(new BlockPos(x - j - offset, y - i - 1, z - k - offset), Blocks.OAK_LEAVES.defaultBlockState());
                                    break;
                                case 'W':
                                    region.setBlockAndUpdate(new BlockPos(x - j - offset, y - i - 1, z - k - offset), Blocks.OAK_LOG.defaultBlockState());
                                    break;
                            }
                        }
                    }
                }
            }

            int treeHeight = Config.INSTANCE.worldGen.MAIN_ISLAND_TREE ? tree.length : 0;
            int r = Config.INSTANCE.worldGen.MAIN_ISLAND_RADIUS;
            for (int i = 0, d = Config.INSTANCE.worldGen.MAIN_ISLAND_DEPTH; i < d; ++i) {
                for (int j = -r + i; j <= r - i; ++j) {
                    for (int k = -r + i; k <= r - i; ++k) {
                        if (Math.pow(j, 2) + Math.pow(k, 2) < Math.pow(r - i, 2)) {
                            region.setBlockAndUpdate(new BlockPos(x + j, y - treeHeight - 1 - i, z + k), i == 0 ? Blocks.GRASS_BLOCK.defaultBlockState() : Blocks.DIRT.defaultBlockState());
                        }
                    }
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
            this.setDirty(true);
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
            ListTag islands = (ListTag)compoundTag.get(IDENTIFIER);
            if (islands != null) {
                islands.forEach(island -> {
                    ISLANDS.add(Island.fromCompoundTag((CompoundTag) island));
                });
            }
            return new IslandSavedData();
        }
    }
}
