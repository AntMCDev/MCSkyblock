package com.ant.mcskyblock.common.config.preset;

import com.ant.mcskyblock.common.config.SkyBlockConfig;

/**
 * You spawn on a single grass block with no tree ! There is no outer edge islands ! Strongholds and
 * End Cities along with a couple of other random structures spawn. How are you going to get to the end cities ?
 */
public class OneBlock implements IPreset {
    @Override
    public void convert(SkyBlockConfig config) {
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
        config.worldGen.APPLY_BIOME_DECORATIONS             = true;

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
        config.structures.GEN_STRONGHOLD                    = true;
        config.structures.GEN_SWAMP_HUT                     = false;
        config.structures.GEN_VILLAGE                       = false;
        config.structures.GEN_WOODLAND_MANSION              = false;
        config.structures.GEN_ICEBERGS                      = false;
        config.structures.GEN_GEODES                        = false;

        config.drops.PHANTOM_ELYTRA                         = true;
        config.drops.ENDER_DRAGON_HEAD                      = true;
        config.drops.DROWNED_GOLD                           = true;
        config.drops.HUSK_SAND                              = true;
        config.drops.RAVENGER_SCULK_SHRIEKER                = true;
        config.drops.TROPICAL_FISH_CORAL                    = true;
        config.drops.BRUTE_ANCIENT_DEBRIS                   = true;
        config.drops.FISHING_COCO_BEANS                     = true;

        config.trading.WANDERING_TRADER_TREES               = true;
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
    }
}
