package com.ant.mcskyblock.common.world.level.levelgen;

import com.ant.mcskyblock.common.world.level.levelgen.islandtype.*;
import net.minecraft.world.level.block.Blocks;

import java.util.Map;

import static java.util.Map.entry;

public class BiomeIslands {
    public static final Map<String, IslandType> SETTINGS = Map.<String, IslandType>ofEntries(
            entry("the_void"                 , new VoidIslandType()),
            entry("plains"                   , new FluidAccessoryIslandType(Blocks.GRASS_BLOCK, Blocks.WATER, Blocks.POTTED_OAK_SAPLING)),
            entry("sunflower_plains"         , new AccessoryIslandType(Blocks.GRASS_BLOCK, Blocks.SUNFLOWER)),
            entry("snowy_plains"             , new AccessoryIslandType(Blocks.SNOW_BLOCK, Blocks.SNOW)),
            entry("ice_spikes"               , new AccessoryIslandType(Blocks.BLUE_ICE, Blocks.PACKED_ICE)),
            entry("desert"                   , new AccessoryIslandType(Blocks.SANDSTONE, Blocks.CACTUS)),
            entry("swamp"                    , new AccessoryIslandType(Blocks.MUD, Blocks.PEONY)),
            entry("mangrove_swamp"           , new AccessoryIslandType(Blocks.MUDDY_MANGROVE_ROOTS, Blocks.POTTED_MANGROVE_PROPAGULE)),
            entry("forest"                   , new AccessoryIslandType(Blocks.PODZOL, Blocks.POTTED_SPRUCE_SAPLING)),
            entry("flower_forest"            , new AccessoryIslandType(Blocks.GRASS_BLOCK, Blocks.LILAC)),
            entry("birch_forest"             , new AccessoryIslandType(Blocks.GRASS_BLOCK, Blocks.BIRCH_SAPLING)),
            // below are the four places to get dark oak
            entry("dark_forest"              , new AccessoryIslandType(Blocks.GRASS_BLOCK, Blocks.POTTED_DARK_OAK_SAPLING)),
            entry("old_growth_birch_forest"  , new AccessoryIslandType(Blocks.GRASS_BLOCK, Blocks.POTTED_DARK_OAK_SAPLING)),
            entry("old_growth_pine_taiga"    , new AccessoryIslandType(Blocks.GRASS_BLOCK, Blocks.POTTED_DARK_OAK_SAPLING)),
            entry("old_growth_spruce_taiga"  , new AccessoryIslandType(Blocks.GRASS_BLOCK, Blocks.POTTED_DARK_OAK_SAPLING)),
            entry("taiga"                    , new AccessoryIslandType(Blocks.GRASS_BLOCK, Blocks.ROSE_BUSH)),
            entry("snowy_taiga"              , new AccessoryIslandType(Blocks.SNOW_BLOCK, Blocks.POTTED_SPRUCE_SAPLING)),
            entry("savanna"                  , new AccessoryIslandType(Blocks.GRASS_BLOCK, Blocks.POTTED_ACACIA_SAPLING)),
            entry("savanna_plateau"          , new AccessoryIslandType(Blocks.GRASS_BLOCK, Blocks.POTTED_ACACIA_SAPLING)),
            entry("windswept_hills"          , new AccessoryIslandType(Blocks.GRASS_BLOCK, Blocks.RAW_COPPER_BLOCK)),
            entry("windswept_gravelly_hills" , new AccessoryIslandType(com.ant.mcskyblock.common.world.level.block.Blocks.STATIC_GRAVEL, Blocks.GRAVEL)),
            entry("windswept_forest"         , new AccessoryIslandType(Blocks.GRASS_BLOCK, Blocks.RAW_IRON_BLOCK)),
            entry("windswept_savanna"        , new AccessoryIslandType(Blocks.COARSE_DIRT, Blocks.POTTED_ACACIA_SAPLING)),
            entry("jungle"                   , new AccessoryIslandType(Blocks.GRASS_BLOCK, Blocks.POTTED_JUNGLE_SAPLING)),
            entry("sparse_jungle"            , new AccessoryIslandType(Blocks.GRASS_BLOCK, Blocks.POTTED_JUNGLE_SAPLING)),
            entry("bamboo_jungle"            , new AccessoryIslandType(Blocks.GRASS_BLOCK, Blocks.BAMBOO_SAPLING)),
            entry("badlands"                 , new AccessoryIslandType(Blocks.TERRACOTTA, Blocks.POTTED_DEAD_BUSH)),
            entry("eroded_badlands"          , new AccessoryIslandType(Blocks.RED_TERRACOTTA, Blocks.POTTED_DEAD_BUSH)),
            entry("wooded_badlands"          , new AccessoryIslandType(Blocks.ORANGE_TERRACOTTA, Blocks.POTTED_OAK_SAPLING)),
            entry("meadow"                   , new AccessoryIslandType(Blocks.GRASS_BLOCK, Blocks.BEE_NEST)),
            entry("grove"                    , new AccessoryIslandType(Blocks.SNOW_BLOCK, Blocks.POTTED_SPRUCE_SAPLING)),
            entry("snowy_slopes"             , new AccessoryIslandType(Blocks.SNOW_BLOCK, Blocks.POWDER_SNOW_CAULDRON)),
            entry("frozen_peaks"             , new AccessoryIslandType(Blocks.PACKED_ICE, Blocks.BLUE_ICE)),
            entry("jagged_peaks"             , new AccessoryIslandType(Blocks.ICE, Blocks.STONE)),
            entry("stony_peaks"              , new AccessoryIslandType(Blocks.ANDESITE, Blocks.CALCITE)),
            entry("river"                    , new AccessoryIslandType(com.ant.mcskyblock.common.world.level.block.Blocks.STATIC_SAND, Blocks.WATER)),
            entry("frozen_river"             , new SimpleIslandType(Blocks.ICE)),
            entry("beach"                    , new SimpleIslandType(com.ant.mcskyblock.common.world.level.block.Blocks.STATIC_SAND)),
            entry("snowy_beach"              , new AccessoryIslandType(com.ant.mcskyblock.common.world.level.block.Blocks.STATIC_SAND, Blocks.SNOW)),
            entry("stony_shore"              , new AccessoryIslandType(com.ant.mcskyblock.common.world.level.block.Blocks.STATIC_SAND, Blocks.STONE)),
            entry("warm_ocean"               , new AccessoryIslandType(Blocks.BRAIN_CORAL_BLOCK, Blocks.WATER)),
            entry("lukewarm_ocean"           , new AccessoryIslandType(Blocks.BUBBLE_CORAL_BLOCK, Blocks.WATER)),
            entry("deep_lukewarm_ocean"      , new AccessoryIslandType(Blocks.FIRE_CORAL_BLOCK, Blocks.WATER)),
            entry("ocean"                    , new AccessoryIslandType(Blocks.CLAY, Blocks.WATER)),
            entry("deep_ocean"               , new AccessoryIslandType(com.ant.mcskyblock.common.world.level.block.Blocks.STATIC_GRAVEL, Blocks.DRIED_KELP_BLOCK)),
            entry("cold_ocean"               , new AccessoryIslandType(com.ant.mcskyblock.common.world.level.block.Blocks.STATIC_GRAVEL, Blocks.DRIED_KELP_BLOCK)),
            entry("deep_cold_ocean"          , new AccessoryIslandType(com.ant.mcskyblock.common.world.level.block.Blocks.STATIC_GRAVEL, Blocks.DRIED_KELP_BLOCK)),
            entry("frozen_ocean"             , new SimpleIslandType(Blocks.BLUE_ICE)),
            entry("deep_frozen_ocean"        , new SimpleIslandType(Blocks.PACKED_ICE)),
            entry("mushroom_fields"          , new AccessoryIslandType(Blocks.MYCELIUM, Blocks.POTTED_RED_MUSHROOM)),
            entry("dripstone_caves"          , new AccessoryIslandType(Blocks.DRIPSTONE_BLOCK, Blocks.POINTED_DRIPSTONE)),
            entry("lush_caves"               , new AccessoryIslandType(Blocks.MOSS_BLOCK, Blocks.POTTED_FLOWERING_AZALEA)),
            entry("deep_dark"                , new AccessoryIslandType(Blocks.SCULK_CATALYST, Blocks.SCULK_SHRIEKER)),
            entry("nether_wastes"            , new AccessoryIslandType(Blocks.NETHERRACK, Blocks.POTTED_BROWN_MUSHROOM)),
            entry("warped_forest"            , new AccessoryIslandType(Blocks.WARPED_NYLIUM, Blocks.POTTED_WARPED_FUNGUS)),
            entry("crimson_forest"           , new AccessoryIslandType(Blocks.CRIMSON_NYLIUM, Blocks.POTTED_CRIMSON_FUNGUS)),
            entry("soul_sand_valley"         , new AccessoryIslandType(Blocks.SOUL_SOIL, Blocks.BONE_BLOCK)),
            entry("basalt_deltas"            , new AccessoryIslandType(Blocks.BASALT, Blocks.MAGMA_BLOCK)),
            entry("the_end"                  , new VoidIslandType()),
            entry("end_highlands"            , new AccessoryIslandType(Blocks.END_STONE, Blocks.CHORUS_FLOWER)),
            entry("end_midlands"             , new AccessoryIslandType(Blocks.END_STONE, Blocks.CHORUS_FLOWER)),
            entry("small_end_islands"        , new VoidIslandType()), // do nothing this biome has bugs
            entry("end_barrens"              , new AccessoryIslandType(Blocks.END_STONE, Blocks.CHORUS_FLOWER))
    );
}
