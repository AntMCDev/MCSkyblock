package com.ant.mcskyblock.common;

import com.ant.mcskyblock.common.config.SkyBlockConfig;
import com.ant.mcskyblock.common.world.level.levelgen.presets.SkyBlockWorldPreset;
import com.ant.mcskyblock.common.world.level.structure.SkyBlockStructureTracker;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SkyBlock {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_NAME = "mcskyblock";

    public static final String PRESET_ID = "skyblock_preset";
    public static final String CHUNK_GENERATOR_ID = "skyblock_generator";
    public static final String END_BIOMESOURCE_ID = "skyblock_endbiomes";

    private SkyBlock(){}

    public static void init() {
        SkyBlockConfig.register();
        SkyBlockStructureTracker.rescan();
        SkyBlockWorldPreset.register();
    }

    public static boolean isLogicalClient(Level level) { return level.isClientSide; }

    public static boolean isLogicalServer(Level level) {
        return !isLogicalClient(level);
    }

}