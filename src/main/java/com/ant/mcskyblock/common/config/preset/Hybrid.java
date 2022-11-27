package com.ant.mcskyblock.common.config.preset;

import com.ant.mcskyblock.common.config.Config;

/**
 * [COMMON] CONFIG PRESET - A very generous starting area and surrounded by biome-based sub-islands. All structures spawn
 * naturally, however to account for that - certain custom recipes and drops that are otherwise unobtainable outside
 * of structures are disabled in this mode.
 */
public class Hybrid extends Config {
    public Hybrid() {
        preset                                      = Preset.Hybrid;

        worldGen.MAIN_ISLAND_RADIUS                 = 6;
        worldGen.SUB_ISLAND_RADIUS                  = 6;

        drops.PHANTOM_ELYTRA                        = false;
        drops.ENDER_DRAGON_HEAD                     = false;
        drops.DROWNED_GOLD                          = false;

        trading.WANDERING_TRADER_SPONGE             = false;
        trading.WANDERING_TRADER_CHORUS_FLOWER      = false;
        trading.WANDERING_TRADER_TWO_TALL_FLOWERS   = false;
        trading.CLERIC_SHULKER_SHELL                = false;
        trading.CLERIC_ECHO_SHARD                   = false;
        trading.PIGLIN_WEEPING_VINES                = false;
        trading.PIGLIN_NYLIUM                       = false;
        trading.HOTV_CLERIC_BUDDING_AMETHYST        = false;
        trading.HOTV_CLERIC_DIAMOND                 = false;

        crafting.COBWEB                             = false;
        crafting.MYCELIUM                           = false;
        crafting.GRASS_BLOCK                        = false;
    }
}
