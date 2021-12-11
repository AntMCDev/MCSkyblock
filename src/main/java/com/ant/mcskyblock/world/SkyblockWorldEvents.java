package com.ant.mcskyblock.world;

import com.ant.mcskyblock.MCSkyBlock;
import com.ant.mcskyblock.config.ConfigHandler;
import com.ant.mcskyblock.network.PacketHander;
import com.ant.mcskyblock.utils.loot.LootTableUtils;
import com.ant.mcskyblock.utils.trade.TradingUtils;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.block.Blocks;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SkyblockWorldEvents {
    public static boolean isServerSkyblock = false;
    public static boolean isClientSkyblock = false;

    public static void init() {
        TradingUtils.register();
        LootTableUtils.register();

        onServerWorldLoad();
        onServerPlayerJoin();
    }

    private static void onServerWorldLoad() {
        ServerWorldEvents.LOAD.register((server, world) -> {
            isServerSkyblock = MCSkyBlock.isSkyblockWorld(world.getChunkManager().getChunkGenerator());
            if (MCSkyBlock.isLogicalServer(world) && isServerSkyblock) {
                PacketHander.registerServerListener();
            }
        });
    }

    private static void onServerPlayerJoin() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            if (MCSkyBlock.isSkyblockWorld(server.getOverworld().getChunkManager().getChunkGenerator())) {
                PacketHander.sendTo(handler.getPlayer(), PacketHander.WORLDTYPE_PACKET.getIdentifier(), PacketByteBufs.empty());
                spawnPlayer(handler.getPlayer());
            }
        });
    }

    private static void spawnPlayer(ServerPlayerEntity player) {
        SkyblockSavedData skyblockSavedData = SkyblockSavedData.get((ServerWorld) player.world);
        if (!skyblockSavedData.generated) {
            buildSkyblock(player.world, new BlockPos(0, 64, 0));
            skyblockSavedData.setGenerated();
            player.teleport(0.5, 65.6, 0);
            player.setSpawnPoint(player.world.getRegistryKey(), new BlockPos(0, 65.6, 0), 0, true, false);
        }
    }

    private static void buildSkyblock(World world, BlockPos pos) {
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
                        world.setBlockState(new BlockPos(pos.getX() - x - offset, pos.getY() - y - 1, pos.getZ() - z - offset), Blocks.OAK_LEAVES.getDefaultState());
                    } else if (tree[y][x][z] == 'W') {
                        world.setBlockState(new BlockPos(pos.getX() - x - offset, pos.getY() - y - 1, pos.getZ() - z - offset), Blocks.OAK_LOG.getDefaultState());
                    }
                }
            }
        }

        for (long x = -(ConfigHandler.Common.ISLAND_RADIUS-1); x <= ConfigHandler.Common.ISLAND_RADIUS-1; x++) {
            for (long z = -(ConfigHandler.Common.ISLAND_RADIUS-1); z <= ConfigHandler.Common.ISLAND_RADIUS-1; z++) {
                world.setBlockState(new BlockPos(pos.getX() - x, pos.getY() - tree.length - 1, pos.getZ() - z), Blocks.DIRT.getDefaultState());
            }
        }
    }
}
