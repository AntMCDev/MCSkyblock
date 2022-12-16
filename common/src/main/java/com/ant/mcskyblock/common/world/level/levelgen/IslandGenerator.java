package com.ant.mcskyblock.common.world.level.levelgen;

import com.ant.mcskyblock.common.config.BiomeIslandConfig;
import com.ant.mcskyblock.common.config.Config;
import com.ant.mcskyblock.common.registry.RegistryAccess;
import com.ant.mcskyblock.common.utils.BiomeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeResolver;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.saveddata.SavedData;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
        String biome = region.getBiome(new BlockPos(pos.getX(), pos.getY(), pos.getZ())).unwrapKey().orElseThrow().location().toString();
        if (canGenerateSubIsland(biome, pos)) {
            islandSavedData.put(new Island(biome, pos.getX(), pos.getY(), pos.getZ()).generate(region));
        } else {
            boolean generated = false;
            // Iterate downward
            for (int y = pos.getY() - 16; y >= region.getMinBuildHeight(); y -= 16) {
                biome = region.getBiome(new BlockPos(pos.getX(), y - (Config.INSTANCE.worldGen.MAIN_ISLAND_TREE ? PlayerIsland.tree.length : 0), pos.getZ())).unwrapKey().orElseThrow().location().toString();
                if (canGenerateSubIsland(biome, pos)) {
                    generated = true;
                    islandSavedData.put(new Island(biome, pos.getX(), y - (Config.INSTANCE.worldGen.MAIN_ISLAND_TREE ? PlayerIsland.tree.length : 0), pos.getZ()).generate(region));
                    break;
                }
            }

            // Iterate upward
            if (!generated) {
                for (int y = pos.getY() + 16; y >= region.getMaxBuildHeight(); y -= 16) {
                    biome = region.getBiome(new BlockPos(pos.getX(), y - (Config.INSTANCE.worldGen.MAIN_ISLAND_TREE ? PlayerIsland.tree.length : 0), pos.getZ())).unwrapKey().orElseThrow().location().toString();
                    if (canGenerateSubIsland(biome, pos)) {
                        islandSavedData.put(new Island(biome, pos.getX(), y - (Config.INSTANCE.worldGen.MAIN_ISLAND_TREE ? PlayerIsland.tree.length : 0), pos.getZ()).generate(region));
                        break;
                    }
                }
            }
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
        return Config.INSTANCE.worldGen.GENERATE_SUB_ISLANDS &&
                (Math.pow(pos.getX(), 2) + Math.pow(pos.getZ(), 2) > Math.pow(Config.INSTANCE.worldGen.SUB_ISLAND_DISTANCE, 2)) &&
                IslandSavedData.ISLANDS.stream().filter(island -> !(island instanceof PlayerIsland)).map(island -> island.uuid).noneMatch(s -> s.equals(uuid));
    }

    private static boolean canGeneratePlayerIsland(Level level, BlockPos pos, String uuid) {
        return Config.INSTANCE.worldGen.GENERATE_MAIN_ISLAND && IslandSavedData.ISLANDS.stream().filter(island -> island instanceof PlayerIsland).map(island -> island.uuid).noneMatch(s -> s.equals(uuid)) && !doesCollide(level, pos);
    }

    public static Optional<Island> playerIsland(String uuid) {
        Optional<Island> island = IslandSavedData.ISLANDS.stream().filter(i -> i instanceof PlayerIsland && i.uuid.equals(uuid)).findFirst();
        if (island.isEmpty()) {
            island = IslandSavedData.ISLANDS.stream().filter(i -> i instanceof PlayerIsland).findFirst();
        }
        return island;
    }

    public static List<Island> otherPlayerIslands(String uuid) {
        return IslandSavedData.ISLANDS.stream().filter(i -> i instanceof PlayerIsland && !i.uuid.equals(uuid)).collect(Collectors.toList());
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
            BiomeIslandConfig.Island b = BiomeIslandConfig.SETTINGS.getOrDefault(new ResourceLocation(uuid), null);
            if (b == null) { return this; }

            BlockState base = b.base == null ? Blocks.AIR.defaultBlockState() : RegistryAccess.INSTANCE.getBlock(b.base).defaultBlockState();
            BlockState fluid = b.fluid == null ? Blocks.AIR.defaultBlockState() : RegistryAccess.INSTANCE.getBlock(b.fluid).defaultBlockState();
            BlockState accessory = b.accessory == null ? Blocks.AIR.defaultBlockState() : RegistryAccess.INSTANCE.getBlock(b.accessory).defaultBlockState();

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
                region.setBlock(new BlockPos(x, y, z), base, 0);
                if (accessory.getBlock() instanceof DoublePlantBlock) {
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
            if (Config.INSTANCE.worldGen.TAIGA_SPAWN_RADIUS > 0) {
                int r = Config.INSTANCE.worldGen.TAIGA_SPAWN_RADIUS;
                BlockPos blockPos3 = BiomeUtils.quantize(new BlockPos(x - r, region.getMinBuildHeight(), z - r));
                BlockPos blockPos4 = BiomeUtils.quantize(new BlockPos(x + r, region.getMaxBuildHeight(), z + r));
                BoundingBox boundingBox = BoundingBox.fromCorners(blockPos3, blockPos4);
                List<ChunkAccess> list = new ArrayList<>();

                ChunkAccess chunkAccess;
                for(int j = SectionPos.blockToSectionCoord(boundingBox.minZ()); j <= SectionPos.blockToSectionCoord(boundingBox.maxZ()); ++j) {
                    for(int k = SectionPos.blockToSectionCoord(boundingBox.minX()); k <= SectionPos.blockToSectionCoord(boundingBox.maxX()); ++k) {
                        chunkAccess = region.getChunk(k, j, ChunkStatus.FULL, false);
                        if (chunkAccess != null) {
                            list.add(chunkAccess);
                        }
                    }
                }

                MutableInt mutableInt = new MutableInt(0);

                Holder<Biome> biomeHolder = region.registryAccess().registryOrThrow(Registries.BIOME).wrapAsHolder(Objects.requireNonNull(region.registryAccess().registryOrThrow(Registries.BIOME).get(new ResourceLocation("minecraft:snowy_taiga"))));
                for (ChunkAccess c : list) {
                    c.fillBiomesFromNoise(BiomeUtils.makeResolver(mutableInt, c, boundingBox, biomeHolder, (holder) -> true), ((ServerLevel)region).getChunkSource().randomState().sampler());
                    c.setUnsaved(true);
                    ((ServerLevel)region).getChunkSource().chunkMap.resendChunk(c);
                }
            }

            int offset = -2;

            if(Config.INSTANCE.worldGen.MAIN_ISLAND_TREE) {
                for (int i = 0; i < tree.length; i++) {
                    for (int j = 0; j < tree[i].length; j++) {
                        for (int k = 0; k < tree[i][j].length; k++) {
                            switch (tree[i][j][k]) {
                                case 'L' -> region.setBlockAndUpdate(new BlockPos(x - j - offset, y - i - 1, z - k - offset), Blocks.OAK_LEAVES.defaultBlockState());
                                case 'W' -> region.setBlockAndUpdate(new BlockPos(x - j - offset, y - i - 1, z - k - offset), Blocks.OAK_LOG.defaultBlockState());
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

        public BoundingBox boundingBox() {
            return new BoundingBox(
                    x - Config.INSTANCE.worldGen.MAIN_ISLAND_RADIUS,
                    y - (Config.INSTANCE.worldGen.MAIN_ISLAND_TREE ? PlayerIsland.tree.length : 0) - 1 - Config.INSTANCE.worldGen.MAIN_ISLAND_DEPTH,
                    z - Config.INSTANCE.worldGen.MAIN_ISLAND_RADIUS,
                    x + Config.INSTANCE.worldGen.MAIN_ISLAND_RADIUS,
                    y,
                    z + Config.INSTANCE.worldGen.MAIN_ISLAND_RADIUS
            );
        }
    }

    public static class IslandSavedData extends SavedData {
        private static final List<Island> ISLANDS = new ArrayList<>();
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
