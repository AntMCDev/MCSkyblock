package com.ant.mcskyblock.common.world.events;

import com.ant.mcskyblock.common.config.Config;
import com.ant.mcskyblock.common.world.level.levelgen.IslandGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public abstract class SkyBlockEvents {
    public static SkyBlockEvents INSTANCE;

    public abstract SkyBlockEvents register();

    public void spawnPlayer(ServerPlayer player) {
        // If not generating main islands, or player alrady has an island - no further logic needed
        if (Config.INSTANCE.worldGen.GENERATE_MAIN_ISLAND && !IslandGenerator.hasPlayerIsland(player.getStringUUID())) {
            // Default spawn co-ords
            double[] pos = new double[] {
                    -0.5, 65.6, -0.5
            };

            if (IslandGenerator.playerIslandCount() == 0) {
                // If first island - generate at the world origin
                IslandGenerator.generatePlayerIsland(player.getServer().overworld(), new BlockPos(pos[0], pos[1], pos[2]), player.getStringUUID());
                player.teleportTo(pos[0], pos[1], pos[2]);
                player.setRespawnPosition(player.getServer().overworld().dimension(), new BlockPos(pos[0], pos[1], pos[2]), 0, true, false);
            } else {
                // If not first island - try to spawn in a radius, up to the max number of main islands
                for (long i = IslandGenerator.playerIslandCount() - 1, j = Config.INSTANCE.worldGen.MAIN_ISLAND_COUNT - 1; i <= j; i++) {
                    final double angle = Math.toRadians(((double)i/j) * 360d);
                    pos[0] = Math.floor(Math.cos(angle) * Config.INSTANCE.worldGen.SUB_ISLAND_DISTANCE) - 0.5;
                    pos[2] = Math.floor(Math.sin(angle) * Config.INSTANCE.worldGen.SUB_ISLAND_DISTANCE) - 0.5;
                    if (IslandGenerator.generatePlayerIsland(player.getServer().overworld(), new BlockPos(pos[0], pos[1], pos[2]), player.getStringUUID())) {
                        player.teleportTo(pos[0], pos[1], pos[2]);
                        player.setRespawnPosition(player.getServer().overworld().dimension(), new BlockPos(pos[0], pos[1], pos[2]), 0, true, false);
                        return;
                    }
                }
            }

            // Wasn't able to generate a suitable island - set spawn to world origin
            player.teleportTo(-0.5, 65.6, -0.5);
            player.setRespawnPosition(player.getServer().overworld().dimension(), new BlockPos(-0.5, 65.6, -0.5), 0, true, false);
        }
    }
}
