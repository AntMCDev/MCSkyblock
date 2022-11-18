package com.ant.mcskyblock.fabric;

import com.ant.mcskyblock.common.MCSkyBlock;
import com.ant.mcskyblock.common.config.SkyBlockConfig;
import com.ant.mcskyblock.common.world.level.saveddata.SkyBlockSavedData;
import com.ant.mcskyblock.common.world.level.structure.SkyBlockStructureTracker;
import com.ant.mcskyblock.fabric.loot.LootTableUtils;
import com.ant.mcskyblock.fabric.network.PacketHander;
import com.ant.mcskyblock.fabric.world.entity.npc.TradingUtils;
import com.ant.mcskyblock.common.world.level.levelgen.SkyBlockChunkGenerator;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

// REQ FABRIC

/**
 *
 */
public class SkyBlockWorldEvents {
    public static boolean isServerSkyblock = false;
    public static boolean isClientSkyblock = false;


    /**
     *
     */
    public static void init() {
        TradingUtils.register();
        LootTableUtils.register();
        onServerStarting();
        onServerWorldLoad();
        onServerPlayerJoin();
    }

    /**
     *
     */
    // we should really rescan-settings at STARTING and not LOADING
    private static void onServerStarting(){
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            SkyBlockStructureTracker.rescan();
        });
    }

    /**
     *
     */
    private static void onServerWorldLoad() {
        ServerWorldEvents.LOAD.register((server, level) -> {
            isServerSkyblock = level.getChunkSource().getGenerator() instanceof SkyBlockChunkGenerator;
            if (MCSkyBlock.isLogicalServer(level) && isServerSkyblock) {
                PacketHander.registerServerListener();
            }
        });
    }

    /**
     *
     */
    private static void onServerPlayerJoin() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            if (server.overworld().getChunkSource().getGenerator() instanceof SkyBlockChunkGenerator) {
                PacketHander.sendTo(handler.getPlayer(), PacketHander.WORLDTYPE_PACKET.getIdentifier(), PacketByteBufs.empty());
                spawnPlayer( handler.getPlayer() );
            }
        });
    }

    /**
     *
     * @param player
     */
    private static void spawnPlayer(ServerPlayer player) {
        SkyBlockSavedData skyblockSavedData = SkyBlockSavedData.get((ServerLevel) player.level);
        if (!skyblockSavedData.generated) {
            String[] configPos = SkyBlockConfig.WorldGen.SPAWN_COORDS;
                    //ConfigHandler.Common.SPAWN_POSITION.  it(",");
            double[] pos = new double[3];
            try {
                pos[0] = Double.parseDouble(configPos[0]);
                pos[1] = Double.parseDouble(configPos[1]);
                pos[2] = Double.parseDouble(configPos[2]);
            } catch (Exception ex) {
                pos = new double[] { 0, 64, 0 };
            }
            if (SkyBlockConfig.WorldGen.GENERATE_MAIN_ISLAND) {
                buildSkyblock(player.level, new BlockPos(pos[0], pos[1], pos[2]));
            }
            skyblockSavedData.setGenerated();
            player.teleportTo(pos[0]+0.5, pos[1]+1.6, pos[2]+0.5);

            player.setRespawnPosition(
                    player.level.dimension(),
                    new BlockPos(pos[0]+0.5, pos[1]+1.6, pos[2]+0.5),
                    0, true, false
            );

        }
    }


    /**
     *
     * @param world
     * @param pos
     */
    private static void buildSkyblock(Level world, BlockPos pos) {
        int offset = -2;
        char[][][] tree = new char[][][]{
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

        for (int y = 0; y < tree.length; y++) {
            for (int x = 0; x < tree[y].length; x++) {
                for (int z = 0; z < tree[y][x].length; z++) {
                    if (tree[y][x][z] == 'L') {

                        world.setBlockAndUpdate(
                                new BlockPos(pos.getX() - x - offset, pos.getY() - y - 1, pos.getZ() - z - offset),
                                Blocks.OAK_LEAVES.defaultBlockState()
                        );

                    } else if (tree[y][x][z] == 'W') {

                        world.setBlockAndUpdate(
                                new BlockPos(pos.getX() - x - offset, pos.getY() - y - 1, pos.getZ() - z - offset),
                                Blocks.OAK_LOG.defaultBlockState()
                        );

                    }
                }
            }
        }

        int r = SkyBlockConfig.WorldGen.MAIN_ISLAND_RADIUS;
        for (int i = 0, d = SkyBlockConfig.WorldGen.MAIN_ISLAND_DEPTH; i < d; ++i) {
            for (int j = -r+i; j <= r-i; ++j) {
                for (int k = -r+i; k <= r-i; ++k) {
                    if (Math.pow(j, 2) + Math.pow(k, 2) < Math.pow(r-i, 2)) {
                        world.setBlockAndUpdate(
                                new BlockPos(pos.getX() + j, pos.getY() - tree.length - 1 - i, pos.getZ() + k),
                                Blocks.GRASS_BLOCK.defaultBlockState()
                        );
                    }
                }
            }
        }
    }

}
