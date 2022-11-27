package com.ant.mcskyblock.common;

import com.ant.mcskyblock.common.config.Config;
import com.ant.mcskyblock.common.world.entity.npc.TradingUtils;
import com.ant.mcskyblock.common.world.level.block.Blocks;
import com.ant.mcskyblock.common.world.level.levelgen.presets.SkyBlockWorldPreset;
import com.ant.mcskyblock.common.world.level.structure.SkyBlockStructureTracker;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * [COMMON] MAIN - This is the common entry point for shared functionality. All platform-specific initializers should
 * call the init method of this class
 */
public class SkyBlock {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_NAME = "mcskyblock";
    public static boolean IS_SERVER_SKYBLOCK = false;
    public static boolean IS_CLIENT_SKYBLOCK = false;

    // resource id's
    public static final String PRESET_ID = "skyblock";
    public static final String NET_WORLDTYPE_ID =  "worldtype";
    public static final String NET_STRUCTURECHECK_ID = "structurecheck";
    public static final String NET_CONFIG_ID         =  "config";
    public static final String CHUNK_GENERATOR_ID = "skyblock_generator";
    public static final String END_BIOMESOURCE_ID = "skyblock_endbiomes";



    private SkyBlock(){}

    public static void init() {
        Blocks.register();
        TradingUtils.register();
        SkyBlockStructureTracker.rescan();
        SkyBlockWorldPreset.register();
        if (Config.INSTANCE.spawning.SPAWN_PIGLIN_BRUTES) {
            SpawnPlacements.register(EntityType.PIGLIN_BRUTE, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        }
    }

    public static boolean isLogicalClient(Level level) { return level.isClientSide; }

    public static boolean isLogicalServer(Level level) {
        return !isLogicalClient(level);
    }

}
