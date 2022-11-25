package com.ant.mcskyblock.common.config.preset;

import com.ant.mcskyblock.common.config.Config;

/**
 * [COMMON] CONFIG PRESET - Main island with outer edge islands, based on three biome and where you travel. Almost
 * all structures spawn. Husks drop sand and many other custom mob drops and custom wandering trader trades. Pesky
 * bats don't spawn as much. Dead bushes can be gathered by placing saplings on sand.
 */
public class Hybrid extends Config {
    public Hybrid() {
        preset                                      = Preset.Hybrid;
        achievementsEnabled                         = true;

        worldGen.IS_OVERWORLD_SKYBLOCK              = true;
        worldGen.IS_NETHER_SKYBLOCK                 = true;
        worldGen.IS_END_SKYBLOCK                    = true;
        worldGen.GENERATE_MAIN_ISLAND               = true;
        worldGen.MAIN_ISLAND_RADIUS                 = 6;
        worldGen.MAIN_ISLAND_DEPTH                  = 3;
        worldGen.MAIN_ISLAND_TREE                   = true;
        worldGen.GENERATE_SUB_ISLANDS               = true;
        worldGen.SUB_ISLAND_DISTANCE                = 128;
        worldGen.SUB_ISLAND_RADIUS                  = 6;
        worldGen.SUB_ISLAND_DEPTH                   = 3;
        worldGen.APPLY_BIOME_DECORATIONS            = true;

        structures.GEN_ANCIENT_CITY                  = true;
        structures.GEN_BASTION_REMNANT               = true;
        structures.GEN_BURIED_TREASURE               = true;
        structures.GEN_DESERT_PYRAMID                = true;
        structures.GEN_END_CITY                      = true;
        structures.GEN_FORTRESS                      = true;
        structures.GEN_IGLOO                         = true;
        structures.GEN_JUNGLE_TEMPLE                 = true;
        structures.GEN_MINESHAFT                     = true;
        structures.GEN_MINESHAFT_MESA                = true;
        structures.GEN_NETHER_FOSSIL                 = true;
        structures.GEN_OCEAN_MONUMENT                = true;
        structures.GEN_OCEAN_RUIN                    = true;
        structures.GEN_PILLAGER_OUTPOST              = true;
        structures.GEN_RUINED_PORTAL                 = true;
        structures.GEN_SHIPWRECK                     = true;
        structures.GEN_STRONGHOLD                    = true;
        structures.GEN_SWAMP_HUT                     = true;
        structures.GEN_VILLAGE                       = true;
        structures.GEN_WOODLAND_MANSION              = true;
        structures.GEN_ICEBERGS                      = true;
        structures.GEN_GEODES                        = true;

        drops.PHANTOM_ELYTRA                         = false;
        drops.ENDER_DRAGON_HEAD                      = false;
        drops.DROWNED_GOLD                           = false;
        drops.HUSK_SAND                              = true;
        drops.RAVENGER_SCULK_SHRIEKER                = true;
        drops.TROPICAL_FISH_CORAL                    = true;
        drops.BRUTE_ANCIENT_DEBRIS                   = true;
        drops.FISHING_COCO_BEANS                     = true;

        trading.WANDERING_TRADER_SPONGE              = false;
        trading.WANDERING_TRADER_GLOW_LICHEN         = true;
        trading.WANDERING_TRADER_SCULK_CATALYST      = false;
        trading.WANDERING_TRADER_LAVA_BUCKET         = true;
        trading.WANDERING_TRADER_GLOW_BERRIES        = true;
        trading.WANDERING_TRADER_CHORUS_PLANT        = false;
        trading.WANDERING_TRADER_DISC_OTHERSIDE      = false;
        trading.WANDERING_TRADER_DISC_5              = false;
        trading.WANDERING_TRADER_DISC_PIGSTEP        = false;
        trading.WANDERING_TRADER_TWO_TALL_FLOWERS    = false;
        trading.MASON_DEEPSLATE                      = true;
        trading.MASON_TUFF                           = true;
        trading.MASON_CALCITE                        = true;
        trading.CLERIC_SHULKER_SHELL                 = false;
        trading.CLERIC_ECHO_SHARD                    = false;
        trading.LIBRARIAN_SWIFT_SNEAK                = false;
        trading.PIGLIN_WEEPING_VINES                 = false;
        trading.PIGLIN_NYLIUM                        = false;
        trading.HOTV_CLERIC_BUDDING_AMETHYST         = false;
        trading.HOTV_CLERIC_DIAMOND                  = false;

        spawning.STOP_BAT_SPAWNS                     = true;
        spawning.SAPLINGS_DIE_ON_SAND                = true;
        spawning.ENDERMITES_SPAWN_ENDSTONE           = true;
        spawning.SPAWN_PIGLIN_BRUTES                 = true;

        crafting.CORAL_BLOCKS                        = true;
        crafting.KELP                                = true;
        crafting.NETHERRACK                          = true;
        crafting.SPORE_BLOSSOM                       = true;
        crafting.END_PORTAL_FRAME                    = true;
        crafting.HEART_OF_THE_SEA                    = true;
        crafting.BUNDLE                              = true;
        crafting.RED_SAND                            = true;
        crafting.COBWEB                              = false;
        crafting.MYCELIUM                            = false;
        crafting.GRASS_BLOCK                         = false;
    }
}
