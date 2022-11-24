package com.ant.mcskyblock.common.config.preset;

import com.ant.mcskyblock.common.config.Config;

/**
 * [COMMON] CONFIG PRESET - Main island that has no outer edge islands. No structures spawn. Only custom loot that is
 * dropped is to progress to the ender dragon
 */
public class Classic extends Config {
    public Classic() {
        preset                                      = Preset.Classic;
        achievementsEnabled                         = true;

        worldGen.IS_OVERWORLD_SKYBLOCK              = true;
        worldGen.IS_NETHER_SKYBLOCK                 = true;
        worldGen.IS_END_SKYBLOCK                    = true;
        worldGen.GENERATE_MAIN_ISLAND               = true;
        worldGen.MAIN_ISLAND_RADIUS                 = 12;
        worldGen.MAIN_ISLAND_DEPTH                  = 5;
        worldGen.MAIN_ISLAND_TREE                   = true;
        worldGen.GENERATE_SUB_ISLANDS               = false;
        worldGen.SUB_ISLAND_DISTANCE                = 128;
        worldGen.SUB_ISLAND_RADIUS                  = 7;
        worldGen.SUB_ISLAND_DEPTH                   = 5;
        worldGen.APPLY_BIOME_DECORATIONS            = true;

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

        drops.PHANTOM_ELYTRA                        = true;
        drops.ENDER_DRAGON_HEAD                     = true;
        drops.DROWNED_GOLD                          = true;
        drops.HUSK_SAND                             = true;
        drops.RAVENGER_SCULK_SHRIEKER               = true;
        drops.TROPICAL_FISH_CORAL                   = true;
        drops.BRUTE_ANCIENT_DEBRIS                  = true;
        drops.FISHING_COCO_BEANS                    = true;

        trading.WANDERING_TRADER_SPONGE             = true;
        trading.WANDERING_TRADER_GLOW_LICHEN        = true;
        trading.WANDERING_TRADER_SCULK_CATALYST     = true;
        trading.WANDERING_TRADER_LAVA_BUCKET        = true;
        trading.WANDERING_TRADER_GLOW_BERRIES       = true;
        trading.WANDERING_TRADER_CHORUS_PLANT       = true;
        trading.WANDERING_TRADER_DISC_OTHERSIDE     = true;
        trading.WANDERING_TRADER_DISC_5             = true;
        trading.WANDERING_TRADER_DISC_PIGSTEP       = true;
        trading.WANDERING_TRADER_TWO_TALL_FLOWERS   = true;
        trading.MASON_DEEPSLATE                     = true;
        trading.MASON_TUFF                          = true;
        trading.MASON_CALCITE                       = true;
        trading.CLERIC_SHULKER_SHELL                = true;
        trading.CLERIC_ECHO_SHARD                   = true;
        trading.PIGLIN_WEEPING_VINES                = true;
        trading.PIGLIN_NYLIUM                       = true;
        trading.HOTV_CLERIC_BUDDING_AMETHYST        = true;
        trading.HOTV_CLERIC_DIAMOND                 = true;

        spawning.STOP_BAT_SPAWNS                    = true;
        spawning.SAPLINGS_DIE_ON_SAND               = true;
        spawning.ENDERMITES_SPAWN_ENDSTONE          = true;
        spawning.SPAWN_PIGLIN_BRUTES                = true;

        crafting.CORAL_BLOCKS                       = true;
        crafting.KELP                               = true;
        crafting.NETHERRACK                         = true;
        crafting.SPORE_BLOSSOM                      = true;
        crafting.END_PORTAL_FRAME                   = true;
        crafting.HEART_OF_THE_SEA                   = true;
        crafting.BUNDLE                             = true;
        crafting.RED_SAND                           = true;
        crafting.COBWEB                             = false;
        crafting.MYCELIUM                           = true;
        crafting.GRASS_BLOCK                        = true;
    }
}
