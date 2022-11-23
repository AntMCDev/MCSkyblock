package com.ant.mcskyblock.common.config.preset;

import com.ant.mcskyblock.common.config.SkyBlockConfig;

/**
 * Do you even try ?
 */
public class Insane implements IPreset {
    @Override
    public void convert(SkyBlockConfig config) {
        config.achievementsEnabled                          = true;

        config.worldGen.IS_OVERWORLD_SKYBLOCK               = true;
        config.worldGen.IS_NETHER_SKYBLOCK                  = true;
        config.worldGen.IS_END_SKYBLOCK                     = true;
        config.worldGen.GENERATE_MAIN_ISLAND                = true;
        config.worldGen.MAIN_ISLAND_RADIUS                  = 1;
        config.worldGen.MAIN_ISLAND_DEPTH                   = 1;
        config.worldGen.MAIN_ISLAND_TREE                    = false;
        config.worldGen.GENERATE_SUB_ISLANDS                = false;
        config.worldGen.SUB_ISLAND_DISTANCE                 = 64;
        config.worldGen.SUB_ISLAND_RADIUS                   = 1;
        config.worldGen.SUB_ISLAND_DEPTH                    = 1;
        config.worldGen.APPLY_BIOME_DECORATIONS             = false;

        config.structures.GEN_ANCIENT_CITY                  = false;
        config.structures.GEN_BASTION_REMNANT               = false;
        config.structures.GEN_BURIED_TREASURE               = false;
        config.structures.GEN_DESERT_PYRAMID                = false;
        config.structures.GEN_END_CITY                      = false;
        config.structures.GEN_FORTRESS                      = false;
        config.structures.GEN_IGLOO                         = false;
        config.structures.GEN_JUNGLE_TEMPLE                 = false;
        config.structures.GEN_MINESHAFT                     = false;
        config.structures.GEN_MINESHAFT_MESA                = false;
        config.structures.GEN_NETHER_FOSSIL                 = false;
        config.structures.GEN_OCEAN_MONUMENT                = false;
        config.structures.GEN_OCEAN_RUIN                    = false;
        config.structures.GEN_PILLAGER_OUTPOST              = false;
        config.structures.GEN_RUINED_PORTAL                 = false;
        config.structures.GEN_SHIPWRECK                     = false;
        config.structures.GEN_STRONGHOLD                    = false;
        config.structures.GEN_SWAMP_HUT                     = false;
        config.structures.GEN_VILLAGE                       = false;
        config.structures.GEN_WOODLAND_MANSION              = false;
        config.structures.GEN_ICEBERGS                      = false;
        config.structures.GEN_GEODES                        = false;

        config.drops.PHANTOM_ELYTRA                         = true;
        config.drops.ENDER_DRAGON_HEAD                      = true;
        config.drops.DROWNED_GOLD                           = false;
        config.drops.HUSK_SAND                              = true;
        config.drops.RAVENGER_SCULK_SHRIEKER                = true;
        config.drops.TROPICAL_FISH_CORAL                    = true;
        config.drops.BRUTE_ANCIENT_DEBRIS                   = true;
        config.drops.FISHING_COCO_BEANS                     = true;

        config.trading.WANDERING_TRADER_SPONGE              = true;
        config.trading.WANDERING_TRADER_GLOW_LICHEN         = true;
        config.trading.WANDERING_TRADER_SCULK_CATALYST      = true;
        config.trading.WANDERING_TRADER_LAVA_BUCKET         = true;
        config.trading.WANDERING_TRADER_GLOW_BERRIES        = true;
        config.trading.WANDERING_TRADER_CHORUS_PLANT        = true;
        config.trading.WANDERING_TRADER_DISC_OTHERSIDE      = true;
        config.trading.WANDERING_TRADER_DISC_5              = true;
        config.trading.WANDERING_TRADER_DISC_PIGSTEP        = true;
        config.trading.WANDERING_TRADER_TWO_TALL_FLOWERS    = true;
        config.trading.MASON_DEEPSLATE                      = true;
        config.trading.MASON_TUFF                           = true;
        config.trading.MASON_CALCITE                        = true;
        config.trading.CLERIC_SHULKER_SHELL                 = true;
        config.trading.CLERIC_ECHO_SHARD                    = true;
        config.trading.PIGLIN_WEEPING_VINES                 = true;
        config.trading.PIGLIN_NYLIUM                        = true;
        config.trading.HOTV_CLERIC_BUDDING_AMETHYST         = true;
        config.trading.HOTV_CLERIC_DIAMOND                  = true;

        config.spawning.STOP_BAT_SPAWNS                     = true;
        config.spawning.SAPLINGS_DIE_ON_SAND                = true;
        config.spawning.ENDERMITES_SPAWN_ENDSTONE           = true;
        config.spawning.SPAWN_PIGLIN_BRUTES                 = true;

        config.crafting.CORAL_BLOCKS                        = true;
        config.crafting.KELP                                = false;
        config.crafting.NETHERRACK                          = true;
        config.crafting.SPORE_BLOSSOM                       = true;
        config.crafting.END_PORTAL_FRAME                    = true;
        config.crafting.HEART_OF_THE_SEA                    = true;
        config.crafting.BUNDLE                              = false;
        config.crafting.RED_SAND                            = true;
        config.crafting.COBWEB                              = true;
        config.crafting.MYCELIUM                            = true;
        config.crafting.GRASS_BLOCK                         = false;
    }
}
