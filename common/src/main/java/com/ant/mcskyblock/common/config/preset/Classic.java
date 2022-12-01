package com.ant.mcskyblock.common.config.preset;

import com.ant.mcskyblock.common.config.Config;

/**
 * [COMMON] CONFIG PRESET - A generous starting area, however no outer islands or structures will spawn (bounding boxes
 * are there, so structure-specific mobs will still spawn). All non-renewable in vanilla will be made accessible in this
 * mode - however some useful, but non-essential recipes/drops will be disabled.
 */
public class Classic extends Config {
    public Classic() {
        preset                          = Preset.Classic;

        worldGen.MAIN_ISLAND_RADIUS     = 5;
        worldGen.MAIN_ISLAND_DEPTH      = 3;
        worldGen.GENERATE_SUB_ISLANDS   = false;

        structures.GEN_ANCIENT_CITY     = false;
        structures.GEN_BASTION_REMNANT  = false;
        structures.GEN_BURIED_TREASURE  = false;
        structures.GEN_DESERT_PYRAMID   = false;
        structures.GEN_END_CITY         = false;
        structures.GEN_FORTRESS         = false;
        structures.GEN_IGLOO            = false;
        structures.GEN_JUNGLE_TEMPLE    = false;
        structures.GEN_MINESHAFT        = false;
        structures.GEN_NETHER_FOSSIL    = false;
        structures.GEN_OCEAN_MONUMENT   = false;
        structures.GEN_OCEAN_RUIN       = false;
        structures.GEN_PILLAGER_OUTPOST = false;
        structures.GEN_RUINED_PORTAL    = false;
        structures.GEN_SHIPWRECK        = false;
        structures.GEN_STRONGHOLD       = false;
        structures.GEN_SWAMP_HUT        = false;
        structures.GEN_VILLAGE          = false;
        structures.GEN_WOODLAND_MANSION = false;
        structures.GEODE_WEIGHT         = 0;

        drops.HUSK_SAND                 = false;
        drops.TROPICAL_FISH_CORAL       = false;
        drops.FISHING_COCOA_BEANS       = false;

        trading.PIGLIN_WEEPING_VINES    = false;

        crafting.CORAL_BLOCKS           = false;
        crafting.KELP                   = false;
        crafting.RED_SAND               = false;
    }
}
