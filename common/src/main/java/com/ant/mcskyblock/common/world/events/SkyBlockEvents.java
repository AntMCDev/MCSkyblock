package com.ant.mcskyblock.common.world.events;

import com.ant.mcskyblock.common.config.Config;
import com.ant.mcskyblock.common.spawning.SpawnUtils;
import com.ant.mcskyblock.common.world.level.block.Blocks;
import com.ant.mcskyblock.common.world.level.levelgen.IslandGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;
import java.util.Optional;

public abstract class SkyBlockEvents {
    public static SkyBlockEvents INSTANCE;

    public abstract SkyBlockEvents register();

    public void spawnPlayer(ServerPlayer player) {
        // If not generating main islands, or player already has an island - no further logic needed
        if (Config.INSTANCE.worldGen.GENERATE_MAIN_ISLAND && !IslandGenerator.hasPlayerIsland(player.getStringUUID())) {
            // Default spawn co-ords
            double[] pos = new double[] {
                    0, 64, 0
            };

            for (long i = IslandGenerator.playerIslandCount(), j = Config.INSTANCE.worldGen.MAIN_ISLAND_COUNT; i < j; i++) {
                final double angle = Math.toRadians(((double)i/j) * 360d);
                pos[0] = Math.floor(Math.cos(angle) * Config.INSTANCE.worldGen.MAIN_ISLAND_DISTANCE) - 0.5;
                pos[2] = Math.floor(Math.sin(angle) * Config.INSTANCE.worldGen.MAIN_ISLAND_DISTANCE) - 0.5;
                if (IslandGenerator.generatePlayerIsland(player.getServer().overworld(), new BlockPos(pos[0], pos[1], pos[2]), player.getStringUUID())) {
                    if (Config.INSTANCE.worldGen.MAIN_ISLAND_CHEST) {
                        SpawnUtils.spawnChest(player.getServer().overworld(), new BlockPos(pos[0], pos[1], pos[2]));
                    }
                    player.teleportTo(pos[0], pos[1] + 1.6 + (Config.INSTANCE.worldGen.MAIN_ISLAND_CHEST ? 1 : 0), pos[2]);
                    return;
                }
            }

            // Wasn't able to generate a suitable island - try to find spawn on other islands
            if (player.getServer() != null) {
                ServerLevel level = player.getServer().overworld();
                List<IslandGenerator.Island> islands = IslandGenerator.otherPlayerIslands(player.getStringUUID());
                boolean otherIslandFound = false;
                for (IslandGenerator.Island i : islands) {
                    Optional<BlockPos> bPos = SpawnUtils.findValidBoundingBox(level, ((IslandGenerator.PlayerIsland)i).boundingBox());
                    if (bPos.isPresent()) {
                        if (Config.INSTANCE.worldGen.MAIN_ISLAND_CHEST) {
                            SpawnUtils.spawnChest(player.getServer().overworld(), new BlockPos(pos[0], pos[1], pos[2]));
                        }
                        player.teleportTo(bPos.get().getX(), bPos.get().getY() + 1.6 + (Config.INSTANCE.worldGen.MAIN_ISLAND_CHEST ? 1 : 0), bPos.get().getZ());
                        break;
                    }
                }
                if (!otherIslandFound) {
                    // If in here, ALL starter islands are mined, so generate a non-lootable spawn block at the world origin
                    level.setBlockAndUpdate(new BlockPos(0, 64, 0), Blocks.spawnBlock().defaultBlockState());
                    if (Config.INSTANCE.worldGen.MAIN_ISLAND_CHEST) {
                        SpawnUtils.spawnChest(player.getServer().overworld(), new BlockPos(pos[0], pos[1], pos[2]));
                    }
                    player.teleportTo(0.5, 65.6 + (Config.INSTANCE.worldGen.MAIN_ISLAND_CHEST ? 1 : 0), 0.5);
                }
            }
        }
    }
}
