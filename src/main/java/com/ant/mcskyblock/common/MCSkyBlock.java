package com.ant.mcskyblock.common;

import com.ant.mcskyblock.common.world.level.block.StaticBlock;
import com.ant.mcskyblock.common.config.SkyBlockConfigManager;

import com.ant.mcskyblock.common.world.level.levelgen.presets.SkyBlockWorldPreset;
import com.ant.mcskyblock.common.world.level.structure.SkyBlockStructureTracker;
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

    // the configuration
    public static SkyBlockConfigManager CONFIGS = new SkyBlockConfigManager();

    // The Structures that will or will not get generated in skyblock
    public static SkyBlockStructureTracker STRUCTURE_TRACKER = new SkyBlockStructureTracker();

    // Blocks that would fall but do not and frop the vanilla variant
    public static StaticBlock NON_FALING_BLOCKS = new StaticBlock();

    /**
     *
     */
    public MCSkyBlock(){
        CONFIGS.initialize();
        STRUCTURE_TRACKER.rescan();
        SkyBlockWorldPreset.register();;
    }

    /**
     *
     * @param level
     * @return
     */
    public static boolean isLogicalClient(Level level) {return level.isClientSide;}

    /**
     *
     * @param level
     * @return
     */
    public static boolean isLogicalServer(Level level) {
        return !isLogicalClient(level);
    }

}
