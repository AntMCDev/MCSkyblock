package com.ant.mcskyblock.common.world.events;

import com.ant.mcskyblock.common.config.Config;
import com.ant.mcskyblock.common.world.level.saveddata.SkyBlockSavedData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public abstract class SkyBlockEvents {
    public static SkyBlockEvents INSTANCE;

    public static boolean IS_SERVER_SKYBLOCK = false;
    public static boolean IS_CLIENT_SKYBLOCK = false;

    public abstract SkyBlockEvents register();

    public void spawnPlayer(ServerPlayer player) {
        SkyBlockSavedData skyblockSavedData = SkyBlockSavedData.get((ServerLevel) player.level);
        if (!skyblockSavedData.generated) {
            String[] configPos = Config.INSTANCE.spawning.SPAWN_COORDS;
            double[] pos = new double[3];
            try {
                pos[0] = Double.parseDouble(configPos[0]);
                pos[1] = Double.parseDouble(configPos[1]);
                pos[2] = Double.parseDouble(configPos[2]);
            } catch (Exception ex) {
                pos = new double[] { 0, 64, 0 };
            }
            if (Config.INSTANCE.worldGen.GENERATE_MAIN_ISLAND) {
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

    private void buildSkyblock(Level world, BlockPos pos) {
        int offset = -2;

        if(Config.INSTANCE.worldGen.MAIN_ISLAND_TREE ) {
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
        }

        int treeHeight = Config.INSTANCE.worldGen.MAIN_ISLAND_TREE ? tree.length : 0;
        int r = Config.INSTANCE.worldGen.MAIN_ISLAND_RADIUS;
        for (int i = 0, d = Config.INSTANCE.worldGen.MAIN_ISLAND_DEPTH; i < d; ++i) {
            for (int j = -r+i; j <= r-i; ++j) {
                for (int k = -r+i; k <= r-i; ++k) {
                    if (Math.pow(j, 2) + Math.pow(k, 2) < Math.pow(r-i, 2)) {
                        world.setBlockAndUpdate(
                                new BlockPos(pos.getX() + j, pos.getY() - treeHeight - 1 - i, pos.getZ() + k),
                                Blocks.GRASS_BLOCK.defaultBlockState()
                        );
                    }
                }
            }
        }
    }
}
