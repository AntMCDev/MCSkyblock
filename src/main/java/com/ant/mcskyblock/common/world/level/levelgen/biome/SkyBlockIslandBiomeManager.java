package com.ant.mcskyblock.common.world.level.levelgen.biome;



//  Map< resourceKey<Biome>, SkyBlockIslandVegitation>
//
//  SkyBlockIslandVegitation. map;
//       "resourceKey"  Pair<Block,Block> ";      saplings slash single plant" , "floorBlock"
//
//
//

import com.ant.mcskyblock.common.MCSkyBlock;
import com.ant.mcskyblock.common.config.SkyBlockConfigManager;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;

public class SkyBlockIslandBiomeManager {
// map is as follows
// String      = ResourceKey.location.value()
// Pair.first  = The Floor Block
// Pair.secon   d = The Sapling
    public static Map<String, Pair<Block,Block>> biomeDataMap = Map.ofEntries(
        entry( "the_void"                  , new Pair<Block,Block>( Blocks.AIR,                Blocks.AIR )    ),
        entry( "plains"                    , new Pair<Block,Block>( Blocks.GRASS_BLOCK,        Blocks.POTTED_OAK_SAPLING )    ),
        entry( "sunflower_plains"          , new Pair<Block,Block>( Blocks.GRASS_BLOCK,        Blocks.SUNFLOWER )    ),
        entry( "snowy_plains"              , new Pair<Block,Block>( Blocks.SNOW_BLOCK,         Blocks.SNOW )    ),
        entry( "ice_spikes"                , new Pair<Block,Block>( Blocks.BLUE_ICE,           Blocks.PACKED_ICE )    ),
        entry( "desert"                    , new Pair<Block,Block>( Blocks.SANDSTONE,          Blocks.CACTUS )    ),
        entry( "swamp"                     , new Pair<Block,Block>( Blocks.MUD,                Blocks.POTTED_CORNFLOWER )    ), // used to get vines
        entry( "mangrove_swamp"            , new Pair<Block,Block>( Blocks.GRASS_BLOCK,        Blocks.POTTED_MANGROVE_PROPAGULE )    ),
        entry( "forest"                    , new Pair<Block,Block>( Blocks.PODZOL,             Blocks.POTTED_SPRUCE_SAPLING )    ),
        entry( "flower_forest"             , new Pair<Block,Block>( Blocks.GRASS_BLOCK,        Blocks.LILAC )    ),
        entry( "birch_forest"              , new Pair<Block,Block>( Blocks.GRASS_BLOCK,        Blocks.BIRCH_SAPLING )    ),
        entry( "dark_forest"               , new Pair<Block,Block>( Blocks.GRASS_BLOCK,        Blocks.POTTED_DARK_OAK_SAPLING )    ),
        entry( "old_growth_birch_forest"   , new Pair<Block,Block>( Blocks.AIR,        Blocks.AIR )    ),
        entry( "old_growth_pine_taiga"     , new Pair<Block,Block>( Blocks.AIR,        Blocks.AIR )    ),
        entry( "old_growth_spruce_taiga"   , new Pair<Block,Block>( Blocks.AIR,        Blocks.AIR )    ),
        entry( "taiga"                     , new Pair<Block,Block>( Blocks.AIR,        Blocks.AIR )    ),
        entry( "snowy_taiga"               , new Pair<Block,Block>( Blocks.AIR,        Blocks.AIR )    ),
        entry( "savanna"                   , new Pair<Block,Block>( Blocks.GRASS_BLOCK,              Blocks.POTTED_ACACIA_SAPLING )    ),
        entry( "savanna_plateau"           , new Pair<Block,Block>( Blocks.AIR,        Blocks.AIR )    ),
        entry( "windswept_hills"           , new Pair<Block,Block>( Blocks.AIR,        Blocks.AIR )    ),
        entry( "windswept_gravelly_hills"  , new Pair<Block,Block>( Blocks.AIR,        Blocks.AIR )    ),
        entry( "windswept_forest"          , new Pair<Block,Block>( Blocks.AIR,        Blocks.AIR )    ),
        entry( "windswept_savanna"         , new Pair<Block,Block>( Blocks.AIR,        Blocks.AIR )    ),
        entry( "jungle"                    , new Pair<Block,Block>( Blocks.GRASS_BLOCK,              Blocks.POTTED_JUNGLE_SAPLING )    ),
        entry( "sparse_jungle"             , new Pair<Block,Block>( Blocks.GRASS_BLOCK,              Blocks.POTTED_JUNGLE_SAPLING )    ),
        entry( "bamboo_jungle"             , new Pair<Block,Block>( Blocks.GRASS_BLOCK,              Blocks.BAMBOO_SAPLING )    ),
        entry( "badlands"                  , new Pair<Block,Block>( Blocks.TERRACOTTA,               Blocks.POTTED_DEAD_BUSH )    ),
        entry( "eroded_badlands"           , new Pair<Block,Block>( Blocks.RED_TERRACOTTA,           Blocks.POTTED_DEAD_BUSH )    ),
        entry( "wooded_badlands"           , new Pair<Block,Block>( Blocks.ORANGE_TERRACOTTA,        Blocks.POTTED_OAK_SAPLING )    ),
        entry( "meadow"                    , new Pair<Block,Block>( Blocks.GRASS_BLOCK,              Blocks.POTTED_OAK_SAPLING )    ),
        entry( "grove"                     , new Pair<Block,Block>( Blocks.GRASS_BLOCK,              Blocks.POTTED_OAK_SAPLING )    ),
        entry( "snowy_slopes"              , new Pair<Block,Block>( Blocks.SNOW_BLOCK,               Blocks.POTTED_WITHER_ROSE )    ),
        entry( "frozen_peaks"              , new Pair<Block,Block>( Blocks.PACKED_ICE,               Blocks.POWDER_SNOW )    ),
        entry( "jagged_peaks"              , new Pair<Block,Block>( Blocks.AIR,        Blocks.AIR )    ),
        entry( "stony_peaks"               , new Pair<Block,Block>( Blocks.AIR,        Blocks.AIR )    ),
        entry( "river"                     , new Pair<Block,Block>( Blocks.WATER,      Blocks.AIR )    ),
        entry( "frozen_river"              , new Pair<Block,Block>( Blocks.ICE,        Blocks.AIR )    ),
        entry( "beach"                     , new Pair<Block,Block>( Blocks.SAND,       Blocks.AIR )    ),
        entry( "snowy_beach"               , new Pair<Block,Block>( Blocks.AIR,        Blocks.AIR )    ),
        entry( "stony_shore"               , new Pair<Block,Block>( Blocks.AIR,        Blocks.AIR )    ),
        entry( "warm_ocean"                , new Pair<Block,Block>( Blocks.AIR,        Blocks.AIR )    ),
        entry( "lukewarm_ocean"            , new Pair<Block,Block>( Blocks.AIR,        Blocks.AIR )    ),
        entry( "deep_lukewarm_ocean"       , new Pair<Block,Block>( Blocks.AIR,        Blocks.AIR )    ),
        entry( "ocean"                     , new Pair<Block,Block>( Blocks.AIR,        Blocks.AIR )    ),
        entry( "deep_ocean"                , new Pair<Block,Block>( Blocks.AIR,        Blocks.AIR )    ),
        entry( "cold_ocean"                , new Pair<Block,Block>( Blocks.AIR,        Blocks.AIR )    ),
        entry( "deep_cold_ocean"           , new Pair<Block,Block>( Blocks.AIR,        Blocks.AIR )    ),
        entry( "frozen_ocean"              , new Pair<Block,Block>( Blocks.AIR,        Blocks.AIR )    ),
        entry( "deep_frozen_ocean"         , new Pair<Block,Block>( Blocks.AIR,        Blocks.AIR )    ),
        entry( "mushroom_fields"           , new Pair<Block,Block>( Blocks.MYCELIUM,              Blocks.POTTED_RED_MUSHROOM )    ),
        entry( "dripstone_caves"           , new Pair<Block,Block>( Blocks.DRIPSTONE_BLOCK,       Blocks.POINTED_DRIPSTONE )    ),
        entry( "lush_caves"                , new Pair<Block,Block>( Blocks.MOSS_BLOCK,            Blocks.POTTED_FLOWERING_AZALEA )    ),
        entry( "deep_dark"                 , new Pair<Block,Block>( Blocks.SCULK,                 Blocks.SCULK_VEIN   )    ),
        entry( "nether_wastes"             , new Pair<Block,Block>( Blocks.NETHERRACK,            Blocks.POTTED_BROWN_MUSHROOM ) ),
        entry( "warped_forest"             , new Pair<Block,Block>( Blocks.WARPED_NYLIUM,         Blocks.POTTED_WARPED_FUNGUS ) ),
        entry( "crimson_forest"            , new Pair<Block,Block>( Blocks.CRIMSON_NYLIUM,        Blocks.POTTED_CRIMSON_FUNGUS ) ),
        entry( "soul_sand_valley"          , new Pair<Block,Block>( Blocks.SOUL_SOIL,             Blocks.BONE_BLOCK )    ),
        entry( "basalt_deltas"             , new Pair<Block,Block>( Blocks.BASALT,                Blocks.MAGMA_BLOCK )    ),
        entry( "the_end"                   , new Pair<Block,Block>( Blocks.AIR,        Blocks.AIR )    ),
        entry( "end_highlands"             , new Pair<Block,Block>( Blocks.AIR,        Blocks.AIR )    ),
        entry( "end_midlands"              , new Pair<Block,Block>( Blocks.AIR,        Blocks.AIR )    ),
        entry( "small_end_islands"         , new Pair<Block,Block>( Blocks.AIR,        Blocks.AIR )    ), // never generates
        entry( "end_barrens"               , new Pair<Block,Block>( Blocks.END_STONE, Blocks.CHORUS_FLOWER )    )
    );


    /**
     * Creates a new biome island manager. The goal of the manager is to keep track of what biomes islands have been generated.
     * What the blocks are used in the generationof a biome and refreshing the island if need be(later)
     */
    public SkyBlockIslandBiomeManager(){}

    /**
     *  The tracking map at run time. In the furture I guess this will use NBT to store the data for presestence.
     */
    private static HashMap<String, SkyBlockIsland> trackingMap = new HashMap<String, SkyBlockIsland>();

    /**
     *  Addes a biome island to the game and also to the tracking system.
     * @param biomeResourceKey The Biome in which to be generated.
     * @param level The world that it is going to be generated on.
     * @param pos   Wherethe biome going to be generated.
     */
    public static void addIsland(ResourceKey<Biome> biomeResourceKey, WorldGenRegion level, BlockPos pos ){
        String uuid = biomeResourceKey.location().getPath();

        // TODO Add a map for each sector and only generate based on Sector.
        // TODO This will make it so that there is a Island in a circle around the center Island
        if( !trackingMap.containsKey( uuid ) && biomeDataMap.containsKey(uuid) &&
                biomeDataMap.get(uuid).getFirst() != Blocks.AIR  ){
            SkyBlockIsland sbi = new SkyBlockIsland(
                    biomeResourceKey,
                    SkyBlockConfigManager.mainIslandRadius() ,
                    biomeDataMap.get(uuid).getFirst(),
                    biomeDataMap.get(uuid).getSecond()
                    );
            trackingMap.put( uuid, sbi );
            MCSkyBlock.LOGGER.log(org.apache.logging.log4j.Level.INFO, uuid + " island generated x " + pos.getX() + " y " + pos.getY() + " z " + pos.getZ() ) ;
            sbi.createIsland( level, pos );
        }
    }
}
