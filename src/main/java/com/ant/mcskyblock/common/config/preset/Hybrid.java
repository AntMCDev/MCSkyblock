package com.ant.mcskyblock.common.config.preset;

import com.ant.mcskyblock.common.config.SkyBlockConfig;

/**
 * Main island with outer edge islands, based on three biome and where you travel. Almost all structures
 * spawn. Husks drop sand and many other custom mob drops and custom wandering trader trades. Pesky bats don't
 * spawn as much. Dead bush's can be gathered by placing saplings on sand.
 */
public class Hybrid implements IPreset {
    @Override
    public void convert(SkyBlockConfig config) {
        config.achievementsEnabled                          = true;

        config.worldGen.IS_OVERWORLD_SKYBLOCK               = true;
        config.worldGen.IS_NETHER_SKYBLOCK                  = true;
        config.worldGen.IS_END_SKYBLOCK                     = true;
        config.worldGen.GENERATE_MAIN_ISLAND                = true;
        config.worldGen.MAIN_ISLAND_RADIUS                  = 6;
        config.worldGen.MAIN_ISLAND_DEPTH                   = 3;
        config.worldGen.MAIN_ISLAND_TREE                    = true;
        config.worldGen.GENERATE_SUB_ISLANDS                = true;
        config.worldGen.SUB_ISLAND_DISTANCE                 = 128;
        config.worldGen.SUB_ISLAND_RADIUS                   = 6;
        config.worldGen.SUB_ISLAND_DEPTH                    = 3;
        config.worldGen.APPLY_BIOME_DECORATIONS             = true;

        config.structures.GEN_ANCIENT_CITY                  = true;
        config.structures.GEN_BASTION_REMNANT               = true;
        config.structures.GEN_BURIED_TREASURE               = true;
        config.structures.GEN_DESERT_PYRAMID                = true;
        config.structures.GEN_END_CITY                      = true;
        config.structures.GEN_FORTRESS                      = true;
        config.structures.GEN_IGLOO                         = true;
        config.structures.GEN_JUNGLE_TEMPLE                 = true;
        config.structures.GEN_MINESHAFT                     = true;
        config.structures.GEN_MINESHAFT_MESA                = true;
        config.structures.GEN_NETHER_FOSSIL                 = true;
        config.structures.GEN_OCEAN_MONUMENT                = true;
        config.structures.GEN_OCEAN_RUIN                    = true;
        config.structures.GEN_PILLAGER_OUTPOST              = true;
        config.structures.GEN_RUINED_PORTAL                 = true;
        config.structures.GEN_SHIPWRECK                     = true;
        config.structures.GEN_STRONGHOLD                    = true;
        config.structures.GEN_SWAMP_HUT                     = true;
        config.structures.GEN_VILLAGE                       = true;
        config.structures.GEN_WOODLAND_MANSION              = true;
        config.structures.GEN_ICEBERGS                      = true;
        config.structures.GEN_GEODES                        = true;

        config.drops.PHANTOM_ELYTRA                         = false;
        config.drops.ENDER_DRAGON_HEAD                      = false;
        config.drops.DROWNED_GOLD                           = false;
        config.drops.HUSK_SAND                              = true;
        config.drops.RAVENGER_SCULK_SHRIEKER                = true;
        config.drops.TROPICAL_FISH_CORAL                    = true;
        config.drops.BRUTE_ANCIENT_DEBRIS                   = true;
        config.drops.FISHING_COCO_BEANS                     = true;

        config.trading.WANDERING_TRADER_TREES               = false;
        config.trading.WANDERING_TRADER_SPONGE              = false;
        config.trading.WANDERING_TRADER_GLOW_LICHEN         = true;
        config.trading.WANDERING_TRADER_SCULK_CATALYST      = false;
        config.trading.WANDERING_TRADER_LAVA_BUCKET         = true;
        config.trading.WANDERING_TRADER_GLOW_BERRIES        = true;
        config.trading.WANDERING_TRADER_CHORUS_PLANT        = false;
        config.trading.WANDERING_TRADER_DISC_OTHERSIDE      = false;
        config.trading.WANDERING_TRADER_DISC_5              = false;
        config.trading.WANDERING_TRADER_DISC_PIGSTEP        = false;
        config.trading.WANDERING_TRADER_TWO_TALL_FLOWERS    = false;
        config.trading.MASON_DEEPSLATE                      = true;
        config.trading.MASON_TUFF                           = true;
        config.trading.MASON_CALCITE                        = true;
        config.trading.CLERIC_SHULKER_SHELL                 = false;
        config.trading.CLERIC_ECHO_SHARD                    = false;
        config.trading.PIGLIN_WEEPING_VINES                 = false;
        config.trading.PIGLIN_NYLIUM                        = false;
        config.trading.HOTV_CLERIC_BUDDING_AMETHYST         = false;
        config.trading.HOTV_CLERIC_DIAMOND                  = false;

        config.spawning.STOP_BAT_SPAWNS                     = true;
        config.spawning.SAPLINGS_DIE_ON_SAND                = true;
        config.spawning.ENDERMITES_SPAWN_ENDSTONE           = true;
        config.spawning.SPAWN_PIGLIN_BRUTES                 = true;

        config.crafting.CORAL_BLOCKS                        = true;
        config.crafting.KELP                                = true;
        config.crafting.NETHERRACK                          = true;
        config.crafting.SPORE_BLOSSOM                       = true;
        config.crafting.END_PORTAL_FRAME                    = true;
        config.crafting.HEART_OF_THE_SEA                    = true;
        config.crafting.BUNDLE                              = true;
        config.crafting.RED_SAND                            = true;
        config.crafting.COBWEB                              = false;
    }
}
