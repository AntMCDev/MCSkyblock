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
        double[] pos = new double[] {
                0-0.5, 64 + 1.6, 0-0.5
        };

        if (player.getServer() != null) {
            IslandGenerator.sync(player.getServer().overworld());
        }

        if (IslandGenerator.playerIslandCount() > 0) {
            final double angle = Math.toRadians(((double)(IslandGenerator.playerIslandCount() - 1) / (Config.INSTANCE.worldGen.MAIN_ISLAND_COUNT - 1)) * 360d);
            pos[0] = Math.floor(Math.cos(angle) * Config.INSTANCE.worldGen.SUB_ISLAND_DISTANCE) - 0.5;
            pos[2] = Math.floor(Math.sin(angle) * Config.INSTANCE.worldGen.SUB_ISLAND_DISTANCE) - 0.5;
        }

        if (Config.INSTANCE.worldGen.GENERATE_MAIN_ISLAND) {
            if (IslandGenerator.generatePlayerIsland(player.getServer().overworld(), new BlockPos(pos[0], pos[1], pos[2]), player.getStringUUID())) {
                player.teleportTo(pos[0], pos[1], pos[2]);
                player.setRespawnPosition(player.level.dimension(), new BlockPos(pos[0], pos[1], pos[2]), 0, true, false);
            }
        }
    }
}
