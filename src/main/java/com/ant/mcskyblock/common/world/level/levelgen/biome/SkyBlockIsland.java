package com.ant.mcskyblock.common.world.level.levelgen.biome;

import com.ant.mcskyblock.common.MCSkyBlock;
import com.ant.mcskyblock.common.config.SkyBlockConfigManager;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.Level;


public class SkyBlockIsland {
    private String biomeKey;
    private Integer radius;
    private Block treeSapling;
    private Block floorBlock;
    private BlockPos blockPos;

    /**
     * Creates a New and uniq island that only should generate once in the map.
     * @param radius   how wide should the top of the island be. At the moment this does nothing.
     * @param floorBlock The upper most layer Block
     * @param treeSapling The block that you want to place on the on top the island.
     */
    public SkyBlockIsland(String biomeKey, Integer radius, Block floorBlock, Block treeSapling, BlockPos blockPos ){
        this.biomeKey = biomeKey;
        this.radius = radius;
        this.treeSapling = treeSapling;
        this.floorBlock = floorBlock;
        this.blockPos = blockPos;
    }

    /**
     *
     * @param level The world generation region that is getting a island.
     * @param pos Where to spawn the Island
     */
    public void createIsland(WorldGenRegion level, BlockPos pos){
        // the floor blocks
        //DDDDDD
        // DDDD
        //  DD
        BlockState blockState = Blocks.DIRT.defaultBlockState();
        int islandLength = 0;
        for(int y = 0 ; y < 3; y++) {
            if (y == 0) {
                islandLength = 4;
                blockState = floorBlock.defaultBlockState();
            } else if (y == 1) {
                islandLength = 3;
                if (floorBlock == Blocks.AIR) {
                    blockState = floorBlock.defaultBlockState();
                } else {
                    blockState = Blocks.DIRT.defaultBlockState();
                }
            } else {
                islandLength = 2;
                if (floorBlock == Blocks.AIR) {
                    blockState = floorBlock.defaultBlockState();
                } else {
                    blockState = Blocks.DIRT.defaultBlockState();
                }
            }

            // so we start out as -(4 -1) = -3 end 4 -1 = 3
            for (long x = -(islandLength - 1); x <= islandLength - 1; x++) {
                for (long z = -(islandLength - 1); z <= islandLength - 1; z++) {
                    // if we are on the 1st level(top)
                    if (y == 0) {
                        // If we are at the edge
                        if ( x == -3 && z == -3 || x == 3 && z == 3 ||  x == -3 && z == 3 || x == 3 && z == -3 ) {
                            blockState = Blocks.AIR.defaultBlockState();
                        } else {
                            blockState = floorBlock.defaultBlockState();
                        }
                    }
                    level.setBlock(
                            new BlockPos(pos.getX() - x, level.getSeaLevel() - y, pos.getZ() - z),
                            blockState, 0);
                } //  z
            } // x
        } // y

        // the tree or whatever
         level.setBlock( new BlockPos( pos.getX(), level.getSeaLevel()+1, pos.getZ() ), treeSapling.defaultBlockState(), 0 );
    }

    public String getBiomeKey() {
        return biomeKey;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }
}
