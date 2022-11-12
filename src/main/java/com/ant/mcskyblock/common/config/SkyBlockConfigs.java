package com.ant.mcskyblock.common.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

// NON FABRIC
@Config(name = "mcskyblock" )
public class SkyBlockConfigs  implements ConfigData {

    public boolean spawnMainIsland = true;

    public boolean overworldIsSkyBlock = true;
    public boolean netherIsSkyblock = true;
    public boolean endIsSkyblock = true;

    @Comment("Radius of the center island")
    public int mainIslandRadius = 8;
    @Comment("Where to spawn the player")
    public String[] spawnCords = {"0", "64", "0"};

    @Comment("Stop Bat's from spawning")
    public boolean stopBatSpawns = true;

    @ConfigEntry.Gui.CollapsibleObject
    public StructureConfigs structureConfigs = new StructureConfigs();

    @ConfigEntry.Gui.CollapsibleObject
    public MobDropsConfigs mobDropsConfigs = new MobDropsConfigs();

    @ConfigEntry.Gui.CollapsibleObject
    public TradingConfigs tradingConfigs = new TradingConfigs();


    public static class StructureConfigs{
        public boolean GEN_ANCIENT_CITY            = true;
        public boolean GEN_BASTION_REMNANT         = true;
        public boolean GEN_BURIED_TREASURE         = false;
        public boolean GEN_DESERT_PYRAMID          = false;
        public boolean GEN_END_CITY                = true;
        public boolean GEN_FORTRESS                = true;
        public boolean GEN_IGLOO                   = true;
        public boolean GEN_JUNGLE_TEMPLE           = true;
        public boolean GEN_MINESHAFT               = true;
        public boolean GEN_MINESHAFT_MESA          = false;
        public boolean GEN_NETHER_FOSSIL           = true;
        public boolean GEN_OCEAN_MONUMENT          = true;
        public boolean GEN_OCEAN_RUIN_COLD         = true;
        public boolean GEN_OCEAN_RUIN_WARM         = true;
        public boolean GEN_PILLAGER_OUTPOST        = true;
        public boolean GEN_RUINED_PORTAL_DESERT    = true;
        public boolean GEN_RUINED_PORTAL_JUNGLE    = true;
        public boolean GEN_RUINED_PORTAL_MOUNTAIN  = true;
        public boolean GEN_RUINED_PORTAL_NETHER    = true;
        public boolean GEN_RUINED_PORTAL_OCEAN     = true;
        public boolean GEN_RUINED_PORTAL_STANDARD  = true;
        public boolean GEN_RUINED_PORTAL_SWAMP     = true;
        public boolean GEN_SHIPWRECK               = false;
        public boolean GEN_SHIPWRECK_BEACHED       = false;
        public boolean GEN_STRONGHOLD              = true;
        public boolean GEN_SWAMP_HUT               = true;
        public boolean GEN_VILLAGE_DESERT          = false;
        public boolean GEN_VILLAGE_PLAINS          = false;
        public boolean GEN_VILLAGE_SAVANNA         = false;
        public boolean GEN_VILLAGE_SNOWY           = false;
        public boolean GEN_VILLAGE_TAIGA           = false;
        public boolean GEN_WOODLAND_MANSION        = true;

        @Comment("Used For Other Mods. Example: modid:structureName")
        public String[] CUSTOM_STRUCTURES = new String[0];
    };

    public static class MobDropsConfigs{
        public boolean WITCH_NETHER_WART = false;;
        public boolean PHANTOM_ELYTRA = false;;
        public boolean ENDER_DRAGON_HEAD = false;;
        public boolean DROWNED_GOLD = false;;
    };

    public static class TradingConfigs{
        public boolean WANDERING_TRADER_END_PORTAL_FRAME = false;
        public boolean WANDERING_TRADER_SPONGE = false;
        public boolean WANDERING_TRADER_GLOW_LICHEN = false;
        public boolean WANDERING_TRADER_SCULK_CATALYST = false;
        public boolean WANDERING_TRADER_LAVA_BUCKET = false;
        public boolean WANDERING_TRADER_GLOW_BERRIES = false;
        public boolean WANDERING_TRADER_CHORUS_PLANT = false;
        public boolean WANDERING_TRADER_HEART_OF_THE_SEA = false;
        public boolean WANDERING_TRADER_DISC_OTHERSIDE = false;
        public boolean WANDERING_TRADER_DISC_5 = false;
        public boolean WANDERING_TRADER_DISC_PIGSTEP = false;
        public boolean WANDERING_TRADER_REDSTONE = false;
        public boolean MASON_DEEPSLATE = false;
        public boolean MASON_TUFF = false;
        public boolean MASON_CALCITE = false;
        public boolean MASON_REINFORCED_DEEPSLATE = false;
        public boolean CLERIC_SHULKER_SHELL = false;
        public boolean CLERIC_ECHO_SHARD = false;
        /////
        public boolean PIGLIN_ANCIENT_DEBRIS = false;
        public boolean PIGLIN_NETHERRACK = false;
        public boolean PIGLIN_NYLIUM = false;
        public boolean HOTV_CLERIC_BUDDING_AMETHYST = false;
        public boolean HOTV_CLERIC_DIAMOND = false;
    };
}
