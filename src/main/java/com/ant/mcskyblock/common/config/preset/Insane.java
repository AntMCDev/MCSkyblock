package com.ant.mcskyblock.common.config.preset;

import com.ant.mcskyblock.common.config.Config;

/**
 * [COMMON] CONFIG PRESET - Do you even try ?
 */
public class Insane extends Config {
    public Insane() {
        preset                                      = Preset.Insane;

        worldGen.MAIN_ISLAND_RADIUS                 = 1;
        worldGen.MAIN_ISLAND_DEPTH                  = 1;
        worldGen.MAIN_ISLAND_TREE                   = false;
        worldGen.GENERATE_SUB_ISLANDS               = false;
        worldGen.SUB_ISLAND_DISTANCE                = 64;
        worldGen.SUB_ISLAND_RADIUS                  = 1;
        worldGen.SUB_ISLAND_DEPTH                   = 1;
        worldGen.APPLY_BIOME_DECORATIONS            = false;

        structures.GEN_ANCIENT_CITY                 = false;
        structures.GEN_BASTION_REMNANT              = false;
        structures.GEN_BURIED_TREASURE              = false;
        structures.GEN_DESERT_PYRAMID               = false;
        structures.GEN_END_CITY                     = false;
        structures.GEN_FORTRESS                     = false;
        structures.GEN_IGLOO                        = false;
        structures.GEN_JUNGLE_TEMPLE                = false;
        structures.GEN_MINESHAFT                    = false;
        structures.GEN_MINESHAFT_MESA               = false;
        structures.GEN_NETHER_FOSSIL                = false;
        structures.GEN_OCEAN_MONUMENT               = false;
        structures.GEN_OCEAN_RUIN                   = false;
        structures.GEN_PILLAGER_OUTPOST             = false;
        structures.GEN_RUINED_PORTAL                = false;
        structures.GEN_SHIPWRECK                    = false;
        structures.GEN_STRONGHOLD                   = false;
        structures.GEN_SWAMP_HUT                    = false;
        structures.GEN_VILLAGE                      = false;
        structures.GEN_WOODLAND_MANSION             = false;
        structures.GEN_ICEBERGS                     = false;
        structures.GEN_GEODES                       = false;

        drops.DROWNED_GOLD                          = false;

        crafting.KELP                               = false;
        crafting.BUNDLE                             = false;
        crafting.GRASS_BLOCK                        = false;
    }
}
