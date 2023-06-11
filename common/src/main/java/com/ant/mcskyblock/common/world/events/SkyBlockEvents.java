package com.ant.mcskyblock.common.world.events;

import com.ant.mcskyblock.common.config.Config;
import com.ant.mcskyblock.common.spawning.SpawnUtils;
import com.ant.mcskyblock.common.world.level.block.Blocks;
import com.ant.mcskyblock.common.world.level.levelgen.IslandGenerator;
import com.ant.mcskyblock.common.world.level.levelgen.SkyBlockChunkGenerator;
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
        if (Config.INSTANCE.worldGen.GENERATE_MAIN_ISLAND && !IslandGenerator.hasPlayerIsland(player.getStringUUID()) && player.getServer() != null && player.getServer().overworld().getChunkSource().getGenerator() instanceof SkyBlockChunkGenerator && !((SkyBlockChunkGenerator) player.getServer().overworld().getChunkSource().getGenerator()).doSuper()) {
            // Default spawn co-ords
            double spawnOffsetY = 1.6 + (Config.INSTANCE.worldGen.MAIN_ISLAND_CHEST ? 1 : 0);
            double[] pos = new double[] {
                    0, Config.INSTANCE.worldGen.MAIN_ISLAND_Y, 0
            };

            for (long i = IslandGenerator.playerIslandCount(), j = Config.INSTANCE.worldGen.MAIN_ISLAND_COUNT; i < j; i++) {
                final double angle = Math.toRadians(((double)i/j) * 360d);
                pos[0] = Math.floor(Math.cos(angle) * Config.INSTANCE.worldGen.MAIN_ISLAND_DISTANCE) - 0.5;
                pos[2] = Math.floor(Math.sin(angle) * Config.INSTANCE.worldGen.MAIN_ISLAND_DISTANCE) - 0.5;
                if (IslandGenerator.generatePlayerIsland(player.getServer().overworld(), new BlockPos(pos[0], pos[1], pos[2]), player.getStringUUID())) {
                    if (Config.INSTANCE.worldGen.MAIN_ISLAND_CHEST) {
                        SpawnUtils.spawnChest(player.getServer().overworld(), new BlockPos(pos[0], IslandGenerator.islandTop(), pos[2]));
                    }
                    player.teleportTo(pos[0], IslandGenerator.islandTop() + spawnOffsetY + (Config.INSTANCE.worldGen.MAIN_ISLAND_CHEST ? 1 : 0), pos[2]);
                    return;
                }
            }

            // Wasn't able to generate a suitable island - try to find spawn on other islands
            ServerLevel level = player.getServer().overworld();
            List<IslandGenerator.Island> islands = IslandGenerator.otherPlayerIslands(player.getStringUUID());
            boolean otherIslandFound = false;
            for (IslandGenerator.Island i : islands) {
                Optional<BlockPos> bPos = SpawnUtils.findValidBoundingBox(level, ((IslandGenerator.PlayerIsland)i).boundingBox());
                if (bPos.isPresent()) {
                    player.teleportTo(bPos.get().getX(), bPos.get().getY() + 1.6, bPos.get().getZ());
                    return;
                }
            }

            if (!otherIslandFound) {
                // If in here, ALL starter islands are mined, so generate a non-lootable spawn block at the world origin
                level.setBlockAndUpdate(new BlockPos(0, Config.INSTANCE.worldGen.MAIN_ISLAND_Y, 0), Blocks.spawnBlock().defaultBlockState());
                player.teleportTo(0.5, Config.INSTANCE.worldGen.MAIN_ISLAND_Y + 1.6, 0.5);
            }
        }
    }
}
