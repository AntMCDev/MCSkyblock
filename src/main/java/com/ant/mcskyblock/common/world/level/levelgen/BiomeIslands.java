package com.ant.mcskyblock.common.world.level.levelgen;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Map;

import static java.util.Map.entry;

public class BiomeIslands {
    public static final Map<String, Pair<Block,Block>> SETTINGS = Map.ofEntries(
            entry( "the_void"                  , new Pair<>( Blocks.AIR,                Blocks.AIR )    ),
            entry( "plains"                    , new Pair<>( Blocks.GRASS_BLOCK,        Blocks.POTTED_OAK_SAPLING )    ),
            entry( "sunflower_plains"          , new Pair<>( Blocks.GRASS_BLOCK,        Blocks.SUNFLOWER )    ),
            entry( "snowy_plains"              , new Pair<>( Blocks.SNOW_BLOCK,         Blocks.SNOW )    ),
            entry( "ice_spikes"                , new Pair<>( Blocks.BLUE_ICE,           Blocks.PACKED_ICE )    ),
            entry( "desert"                    , new Pair<>( Blocks.SANDSTONE,          Blocks.CACTUS )    ),
            entry( "swamp"                     , new Pair<>( Blocks.MUD,                Blocks.POTTED_CORNFLOWER )    ), // used to get vines
            entry( "mangrove_swamp"            , new Pair<>( Blocks.GRASS_BLOCK,        Blocks.POTTED_MANGROVE_PROPAGULE )    ),
            entry( "forest"                    , new Pair<>( Blocks.PODZOL,             Blocks.POTTED_SPRUCE_SAPLING )    ),
            entry( "flower_forest"             , new Pair<>( Blocks.GRASS_BLOCK,        Blocks.LILAC )    ),
            entry( "birch_forest"              , new Pair<>( Blocks.GRASS_BLOCK,        Blocks.BIRCH_SAPLING )    ),
            entry( "dark_forest"               , new Pair<>( Blocks.GRASS_BLOCK,        Blocks.POTTED_DARK_OAK_SAPLING )    ),
            entry( "old_growth_birch_forest"   , new Pair<>( Blocks.AIR,        Blocks.AIR )    ),
            entry( "old_growth_pine_taiga"     , new Pair<>( Blocks.AIR,        Blocks.AIR )    ),
            entry( "old_growth_spruce_taiga"   , new Pair<>( Blocks.AIR,        Blocks.AIR )    ),
            entry( "taiga"                     , new Pair<>( Blocks.AIR,        Blocks.AIR )    ),
            entry( "snowy_taiga"               , new Pair<>( Blocks.AIR,        Blocks.AIR )    ),
            entry( "savanna"                   , new Pair<>( Blocks.GRASS_BLOCK,              Blocks.POTTED_ACACIA_SAPLING )    ),
            entry( "savanna_plateau"           , new Pair<>( Blocks.AIR,        Blocks.AIR )    ),
            entry( "windswept_hills"           , new Pair<>( Blocks.AIR,        Blocks.AIR )    ),
            entry( "windswept_gravelly_hills"  , new Pair<>( Blocks.AIR,        Blocks.AIR )    ),
            entry( "windswept_forest"          , new Pair<>( Blocks.AIR,        Blocks.AIR )    ),
            entry( "windswept_savanna"         , new Pair<>( Blocks.AIR,        Blocks.AIR )    ),
            entry( "jungle"                    , new Pair<>( Blocks.GRASS_BLOCK,              Blocks.POTTED_JUNGLE_SAPLING )    ),
            entry( "sparse_jungle"             , new Pair<>( Blocks.GRASS_BLOCK,              Blocks.POTTED_JUNGLE_SAPLING )    ),
            entry( "bamboo_jungle"             , new Pair<>( Blocks.GRASS_BLOCK,              Blocks.BAMBOO_SAPLING )    ),
            entry( "badlands"                  , new Pair<>( Blocks.TERRACOTTA,               Blocks.POTTED_DEAD_BUSH )    ),
            entry( "eroded_badlands"           , new Pair<>( Blocks.RED_TERRACOTTA,           Blocks.POTTED_DEAD_BUSH )    ),
            entry( "wooded_badlands"           , new Pair<>( Blocks.ORANGE_TERRACOTTA,        Blocks.POTTED_OAK_SAPLING )    ),
            entry( "meadow"                    , new Pair<>( Blocks.GRASS_BLOCK,              Blocks.POTTED_OAK_SAPLING )    ),
            entry( "grove"                     , new Pair<>( Blocks.GRASS_BLOCK,              Blocks.POTTED_OAK_SAPLING )    ),
            entry( "snowy_slopes"              , new Pair<>( Blocks.SNOW_BLOCK,               Blocks.POTTED_WITHER_ROSE )    ),
            entry( "frozen_peaks"              , new Pair<>( Blocks.PACKED_ICE,               Blocks.POWDER_SNOW )    ),
            entry( "jagged_peaks"              , new Pair<>( Blocks.AIR,        Blocks.AIR )    ),
            entry( "stony_peaks"               , new Pair<>( Blocks.AIR,        Blocks.AIR )    ),
            entry( "river"                     , new Pair<>( Blocks.WATER,      Blocks.AIR )    ),
            entry( "frozen_river"              , new Pair<>( Blocks.ICE,        Blocks.AIR )    ),
            entry( "beach"                     , new Pair<>( Blocks.SAND,       Blocks.AIR )    ),
            entry( "snowy_beach"               , new Pair<>( Blocks.AIR,        Blocks.AIR )    ),
            entry( "stony_shore"               , new Pair<>( Blocks.AIR,        Blocks.AIR )    ),
            entry( "warm_ocean"                , new Pair<>( Blocks.AIR,        Blocks.AIR )    ),
            entry( "lukewarm_ocean"            , new Pair<>( Blocks.AIR,        Blocks.AIR )    ),
            entry( "deep_lukewarm_ocean"       , new Pair<>( Blocks.AIR,        Blocks.AIR )    ),
            entry( "ocean"                     , new Pair<>( Blocks.AIR,        Blocks.AIR )    ),
            entry( "deep_ocean"                , new Pair<>( Blocks.AIR,        Blocks.AIR )    ),
            entry( "cold_ocean"                , new Pair<>( Blocks.AIR,        Blocks.AIR )    ),
            entry( "deep_cold_ocean"           , new Pair<>( Blocks.AIR,        Blocks.AIR )    ),
            entry( "frozen_ocean"              , new Pair<>( Blocks.AIR,        Blocks.AIR )    ),
            entry( "deep_frozen_ocean"         , new Pair<>( Blocks.AIR,        Blocks.AIR )    ),
            entry( "mushroom_fields"           , new Pair<>( Blocks.MYCELIUM,              Blocks.POTTED_RED_MUSHROOM )    ),
            entry( "dripstone_caves"           , new Pair<>( Blocks.DRIPSTONE_BLOCK,       Blocks.POINTED_DRIPSTONE )    ),
            entry( "lush_caves"                , new Pair<>( Blocks.MOSS_BLOCK,            Blocks.POTTED_FLOWERING_AZALEA )    ),
            entry( "deep_dark"                 , new Pair<>( Blocks.SCULK,                 Blocks.SCULK_VEIN   )    ),
            entry( "nether_wastes"             , new Pair<>( Blocks.NETHERRACK,            Blocks.POTTED_BROWN_MUSHROOM ) ),
            entry( "warped_forest"             , new Pair<>( Blocks.WARPED_NYLIUM,         Blocks.POTTED_WARPED_FUNGUS ) ),
            entry( "crimson_forest"            , new Pair<>( Blocks.CRIMSON_NYLIUM,        Blocks.POTTED_CRIMSON_FUNGUS ) ),
            entry( "soul_sand_valley"          , new Pair<>( Blocks.SOUL_SOIL,             Blocks.BONE_BLOCK )    ),
            entry( "basalt_deltas"             , new Pair<>( Blocks.BASALT,                Blocks.MAGMA_BLOCK )    ),
            entry( "the_end"                   , new Pair<>( Blocks.AIR,        Blocks.AIR )    ),
            entry( "end_highlands"             , new Pair<>( Blocks.AIR,        Blocks.AIR )    ),
            entry( "end_midlands"              , new Pair<>( Blocks.AIR,        Blocks.AIR )    ),
            entry( "small_end_islands"         , new Pair<>( Blocks.AIR,        Blocks.AIR )    ), // never generates
            entry( "end_barrens"               , new Pair<>( Blocks.END_STONE, Blocks.CHORUS_FLOWER )    )
    );
}
