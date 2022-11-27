package com.ant.mcskyblock.common.config.preset;

import com.ant.mcskyblock.common.config.Config;

/**
 * [COMMON] CONFIG PRESET - You spawn on a single grass block with no tree, snow is the key to progression in the very
 * early game - so better hope you get a cold biome! Other than that, this has the same settings as classic.
 */
public class OneBlock extends Config {
    public OneBlock() {
        preset                          = Preset.OneBlock;

        worldGen.MAIN_ISLAND_RADIUS     = 1;
        worldGen.MAIN_ISLAND_DEPTH      = 1;
        worldGen.MAIN_ISLAND_TREE       = false;
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
        structures.GEN_MINESHAFT_MESA   = false;
        structures.GEN_NETHER_FOSSIL    = false;
        structures.GEN_OCEAN_MONUMENT   = false;
        structures.GEN_OCEAN_RUIN       = false;
        structures.GEN_PILLAGER_OUTPOST = false;
        structures.GEN_RUINED_PORTAL    = false;
        structures.GEN_SHIPWRECK        = false;
        structures.GEN_SWAMP_HUT        = false;
        structures.GEN_VILLAGE          = false;
        structures.GEN_WOODLAND_MANSION = false;
        structures.GEN_ICEBERGS         = false;
        structures.GEN_GEODES           = false;

        drops.HUSK_SAND                 = false;
        drops.TROPICAL_FISH_CORAL       = false;
        drops.FISHING_COCOA_BEANS       = false;

        crafting.CORAL_BLOCKS           = false;
        crafting.KELP                   = false;
        crafting.RED_SAND               = false;
    }
}
