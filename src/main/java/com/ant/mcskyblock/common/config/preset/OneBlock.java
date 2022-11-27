package com.ant.mcskyblock.common.config.preset;

import com.ant.mcskyblock.common.config.Config;

/**
 * [COMMON] CONFIG PRESET - You spawn on a single grass block with no tree ! There is no outer edge islands !
 * Strongholds and End Cities along with a couple of other random structures spawn. How are you going to get to
 * the end cities ?
 */
public class OneBlock extends Config {
    public OneBlock() {
        preset                                      = Preset.OneBlock;

        worldGen.MAIN_ISLAND_RADIUS                 = 1;
        worldGen.MAIN_ISLAND_DEPTH                  = 1;
        worldGen.MAIN_ISLAND_TREE                   = false;
        worldGen.GENERATE_SUB_ISLANDS               = false;
        worldGen.SUB_ISLAND_DISTANCE                = 64;
        worldGen.SUB_ISLAND_RADIUS                  = 1;
        worldGen.SUB_ISLAND_DEPTH                   = 1;

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
        structures.GEN_SWAMP_HUT                    = false;
        structures.GEN_VILLAGE                      = false;
        structures.GEN_WOODLAND_MANSION             = false;
        structures.GEN_ICEBERGS                     = false;
        structures.GEN_GEODES                       = false;

        crafting.BUNDLE                             = false;
        crafting.COBWEB                             = false;
        crafting.GRASS_BLOCK                        = false;
    }
}
