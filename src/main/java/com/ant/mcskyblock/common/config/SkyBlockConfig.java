package com.ant.mcskyblock.common.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "mcskyblock" )
public class SkyBlockConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    public static final WorldGen worldGen = new WorldGen();

    @ConfigEntry.Gui.CollapsibleObject
    public static final Structures structures = new Structures();

    @ConfigEntry.Gui.CollapsibleObject
    public static final Drops drops = new Drops();

    @ConfigEntry.Gui.CollapsibleObject
    public static final Trading trading = new Trading();

    public static class WorldGen {
        public static boolean IS_OVERWORLD_SKYBLOCK       = true;
        public static boolean IS_NETHER_SKYBLOCK          = true;
        public static boolean IS_END_SKYBLOCK             = true;
        public static boolean GENERATE_MAIN_ISLAND        = true;
        public static boolean GENERATE_SUB_ISLANDS        = true;
        public static boolean APPLY_BIOME_DECORATIONS     = false;
        public static int MAIN_ISLAND_RADIUS              = 4;
        public static int MAIN_ISLAND_DEPTH               = 3;
        public static int SUB_ISLAND_RADIUS               = 4;
        public static int SUB_ISLAND_DEPTH                = 3;
        public static String[] SPAWN_COORDS               = { "0", "64", "0" };
        public static boolean STOP_BAT_SPAWNS             = true;
    }

    public static class Structures {
        public static boolean GEN_ANCIENT_CITY            = true;
        public static boolean GEN_BASTION_REMNANT         = true;
        public static boolean GEN_BURIED_TREASURE         = false;
        public static boolean GEN_DESERT_PYRAMID          = false;
        public static boolean GEN_END_CITY                = true;
        public static boolean GEN_FORTRESS                = true;
        public static boolean GEN_IGLOO                   = true;
        public static boolean GEN_JUNGLE_TEMPLE           = true;
        public static boolean GEN_MINESHAFT               = true;
        public static boolean GEN_MINESHAFT_MESA          = false;
        public static boolean GEN_NETHER_FOSSIL           = true;
        public static boolean GEN_OCEAN_MONUMENT          = true;
        public static boolean GEN_OCEAN_RUIN_COLD         = true;
        public static boolean GEN_OCEAN_RUIN_WARM         = true;
        public static boolean GEN_PILLAGER_OUTPOST        = true;
        public static boolean GEN_RUINED_PORTAL_DESERT    = true;
        public static boolean GEN_RUINED_PORTAL_JUNGLE    = true;
        public static boolean GEN_RUINED_PORTAL_MOUNTAIN  = true;
        public static boolean GEN_RUINED_PORTAL_NETHER    = true;
        public static boolean GEN_RUINED_PORTAL_OCEAN     = true;
        public static boolean GEN_RUINED_PORTAL_STANDARD  = true;
        public static boolean GEN_RUINED_PORTAL_SWAMP     = true;
        public static boolean GEN_SHIPWRECK               = false;
        public static boolean GEN_SHIPWRECK_BEACHED       = false;
        public static boolean GEN_STRONGHOLD              = true;
        public static boolean GEN_SWAMP_HUT               = true;
        public static boolean GEN_VILLAGE_DESERT          = false;
        public static boolean GEN_VILLAGE_PLAINS          = false;
        public static boolean GEN_VILLAGE_SAVANNA         = false;
        public static boolean GEN_VILLAGE_SNOWY           = false;
        public static boolean GEN_VILLAGE_TAIGA           = false;
        public static boolean GEN_WOODLAND_MANSION        = true;
        public static String[] CUSTOM_STRUCTURES = new String[0];
    };

    public static class Drops {
        public static boolean PHANTOM_ELYTRA = false;
        public static boolean ENDER_DRAGON_HEAD = false;
        public static boolean DROWNED_GOLD = false;
        public static boolean HUSK_SAND = true;
        public static boolean WITHER_ANCIENT_DEBRIS = true;
        public static boolean TROPICAL_FISH_CORAL = true;
    };

    public static class Trading {
        public static boolean WANDERING_TRADER_END_PORTAL_FRAME = false;
        public static boolean WANDERING_TRADER_SPONGE = false;
        public static boolean WANDERING_TRADER_GLOW_LICHEN = false;
        public static boolean WANDERING_TRADER_SCULK_CATALYST = false;
        public static boolean WANDERING_TRADER_LAVA_BUCKET = false;
        public static boolean WANDERING_TRADER_GLOW_BERRIES = false;
        public static boolean WANDERING_TRADER_CHORUS_PLANT = false;
        public static boolean WANDERING_TRADER_HEART_OF_THE_SEA = false;
        public static boolean WANDERING_TRADER_DISC_OTHERSIDE = false;
        public static boolean WANDERING_TRADER_DISC_5 = false;
        public static boolean WANDERING_TRADER_DISC_PIGSTEP = false;
        public static boolean WANDERING_TRADER_REDSTONE = false;
        public static boolean MASON_DEEPSLATE = false;
        public static boolean MASON_TUFF = false;
        public static boolean MASON_CALCITE = false;
        public static boolean MASON_REINFORCED_DEEPSLATE = false;
        public static boolean CLERIC_SHULKER_SHELL = false;
        public static boolean CLERIC_ECHO_SHARD = false;
        public static boolean PIGLIN_NETHERRACK = false;
        public static boolean PIGLIN_NYLIUM = false;
        public static boolean HOTV_CLERIC_BUDDING_AMETHYST = false;
        public static boolean HOTV_CLERIC_DIAMOND = false;
    };
}
