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

    private static ResourceKey<Biome> biomeKey;
    private static Integer radius = 4; //SkyBlockConfigManager.mainIslandRadius();
    private static Block treeSapling = Blocks.OAK_SAPLING;
    private static Block floorBlock = Blocks.GRASS_BLOCK;

    private static boolean generated = false;

    /**
     * Creates a New and uniq island that only should generate once in the map.
     * @param biomeKey which biome island is this. the key is used to check against a map
     * @param radius   how wide should the top of the island be. At the moment this does nothing.
     * @param floorBlock The upper most layer Block
     * @param treeSapling The block that you want to place on the on top the island.
     */
    public SkyBlockIsland(ResourceKey<Biome> biomeKey, Integer radius, Block floorBlock, Block treeSapling ){
        this.biomeKey = biomeKey;
        this.radius = radius;
        this.treeSapling = treeSapling;
        this.floorBlock = floorBlock;
    }

    /**
     *
     * @param level The world generation region that is getting a island.
     * @param pos Where to spawn the Island
     */
    public static void createIsland(WorldGenRegion level, BlockPos pos){
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
         level.setBlock(
                new BlockPos( pos.getX(), level.getSeaLevel()+1, pos.getZ() ),
                treeSapling.defaultBlockState(), 0 );

        setGenerated(true);
    }

    /**
     * Function for the future that uses create Island again.
     * @param hasGenerated sets the generation to false as if it has never been generated.
     */
    private static void setGenerated(boolean hasGenerated){
        generated = hasGenerated;
    }

    /**
     * Checks to see if the Island has been generated.
     * @return
     */
    public static boolean hasGenerated(){return generated; }
}
