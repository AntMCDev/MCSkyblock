package com.ant.mcskyblock.common.config.preset;

import com.ant.mcskyblock.common.config.Config;

/**
 * [COMMON] CONFIG PRESET - Main island that has no outer edge islands. No structures spawn. Only custom loot that is
 * dropped is to progress to the ender dragon
 */
public class Classic extends Config {
    public Classic() {
        preset                                      = Preset.Classic;

        worldGen.MAIN_ISLAND_RADIUS                 = 12;
        worldGen.MAIN_ISLAND_DEPTH                  = 5;
        worldGen.GENERATE_SUB_ISLANDS               = false;
        worldGen.SUB_ISLAND_RADIUS                  = 7;
        worldGen.SUB_ISLAND_DEPTH                   = 5;
    }
}
