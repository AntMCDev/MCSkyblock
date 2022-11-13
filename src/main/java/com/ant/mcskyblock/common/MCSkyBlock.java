package com.ant.mcskyblock.common;

import com.ant.mcskyblock.common.block.StaticBlock;
import com.ant.mcskyblock.common.config.SkyBlockConfigManager;
import com.ant.mcskyblock.common.world.level.levelgen.presets.SkyBlockWorldPreset;
import com.ant.mcskyblock.structurecheck.StructureTracker;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MCSkyBlock {
    public static final Logger LOGGER = LogManager.getLogger();
    // MOVE
    public static final String MOD_NAME = "mcskyblock";
    // MOVE
    public static final String MODULE_SKYBLOCK = "skyblock";

    public static final String PRESET_ID = "skyblock_preset";
    public static final String CHUNK_GENERATOR_ID = "skyblock_generator";
    public static final String END_BIOMESOURCE_ID = "skyblock_endbiomes";

    public static SkyBlockConfigManager CONFIGS = new SkyBlockConfigManager();
    public static StructureTracker STRUCTURE_TRACKER = new StructureTracker();
    public MCSkyBlock(){
        CONFIGS.initialize();
        STRUCTURE_TRACKER.rescan();
        SkyBlockWorldPreset.register();
        new StaticBlock();
    }

    public static boolean isLogicalClient(Level level) {return level.isClientSide;}
    public static boolean isLogicalServer(Level level) {
        return !isLogicalClient(level);
    }

}
