package com.ant.mcskyblock.world;

import com.ant.mcskyblock.config.ConfigHandler;
import com.ant.mcskyblock.network.PacketHandler;
import com.ant.mcskyblock.network.PacketSkyblockWorld;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public final class SkyblockWorldEvents {
    private SkyblockWorldEvents() {}

    public static void syncStatus(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof ServerPlayer) {
            boolean isSkyblock = SkyblockChunkGenerator.isWorldSkyblock(event.getWorld());
            if (isSkyblock) {
                PacketHandler.sendTo((ServerPlayer)event.getEntity(), new PacketSkyblockWorld());
            }
        }
    }

    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        Level world = event.getPlayer().level;
        if (SkyblockChunkGenerator.isWorldSkyblock(world)) {
            SkyblockSavedData data = SkyblockSavedData.get((ServerLevel)world);
            if (!data.generated && world.dimension() == Level.OVERWORLD) {
                IslandPosition islandPosition = data.getSpawn();
                ((ServerLevel)world).setDefaultSpawnPos(islandPosition.getCenter(), 0);
                spawnPlayer(event.getPlayer(), islandPosition);
            }
        }
    }

    public static void spawnPlayer(Player player, IslandPosition islandPosition) {
        BlockPos pos = islandPosition.getCenter();
        createSkyblock(player.level, pos);

        if (player instanceof ServerPlayer) {
            ServerPlayer pmp = (ServerPlayer) player;
            pmp.moveTo(pos.getX() + 0.5, pos.getY() + 1.6, pos.getZ() + 0.5);
            pmp.setRespawnPosition(pmp.level.dimension(), pos, 0, true, false);
        }
    }

    public static void createSkyblock(Level world, BlockPos pos) {
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
                        world.setBlockAndUpdate(new BlockPos(pos.getX() - x - offset, pos.getY() - y - 1, pos.getZ() - z - offset), Blocks.OAK_LEAVES.defaultBlockState());
                    } else if (tree[y][x][z] == 'W') {
                        world.setBlockAndUpdate(new BlockPos(pos.getX() - x - offset, pos.getY() - y - 1, pos.getZ() - z - offset), Blocks.OAK_LOG.defaultBlockState());
                    }
                }
            }
        }

        for (int x = -(ConfigHandler.COMMON.islandRadius.get()-1); x <= ConfigHandler.COMMON.islandRadius.get()-1; x++) {
            for (int z = -(ConfigHandler.COMMON.islandRadius.get()-1); z <= ConfigHandler.COMMON.islandRadius.get()-1; z++) {
                world.setBlockAndUpdate(new BlockPos(pos.getX() - x, pos.getY() - tree.length - 1, pos.getZ() - z), Blocks.DIRT.defaultBlockState());
            }
        }
    }
}
