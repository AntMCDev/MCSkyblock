package com.ant.mcskyblock.common.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "mcskyblock" )
public class SkyBlockConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    private WorldGen worldGen = new WorldGen();

    @ConfigEntry.Gui.CollapsibleObject
    private Structures structures = new Structures();

    @ConfigEntry.Gui.CollapsibleObject
    private Drops drops = new Drops();

    @ConfigEntry.Gui.CollapsibleObject
    private Trading trading = new Trading();

    @ConfigEntry.Gui.CollapsibleObject
    private Spawning spawning = new Spawning();

    public static class WorldGen {
        @Comment("This is a ToolTip")
        public boolean IS_OVERWORLD_SKYBLOCK    = true;
        public boolean IS_NETHER_SKYBLOCK       = true;
        public boolean IS_END_SKYBLOCK          = true;
        public boolean GENERATE_MAIN_ISLAND     = true;
        public boolean GENERATE_SUB_ISLANDS     = true;
        public boolean APPLY_BIOME_DECORATIONS  = false;
        public int MAIN_ISLAND_RADIUS           = 4;
        public int MAIN_ISLAND_DEPTH            = 3;
        public int SUB_ISLAND_RADIUS            = 4;
        public int SUB_ISLAND_DEPTH             = 3;
    }

    public static class Structures {
        public boolean GEN_ANCIENT_CITY             = true;
        public boolean GEN_BASTION_REMNANT          = true;
        public boolean GEN_BURIED_TREASURE          = false;
        public boolean GEN_DESERT_PYRAMID           = false;
        public boolean GEN_END_CITY                 = true;
        public boolean GEN_FORTRESS                 = true;
        public boolean GEN_IGLOO                    = true;
        public boolean GEN_JUNGLE_TEMPLE            = true;
        public boolean GEN_MINESHAFT                = true;
        public boolean GEN_MINESHAFT_MESA           = false;
        public boolean GEN_NETHER_FOSSIL            = true;
        public boolean GEN_OCEAN_MONUMENT           = true;
        public boolean GEN_OCEAN_RUIN_COLD          = true;
        public boolean GEN_OCEAN_RUIN_WARM          = true;
        public boolean GEN_PILLAGER_OUTPOST         = true;
        public boolean GEN_RUINED_PORTAL_DESERT     = true;
        public boolean GEN_RUINED_PORTAL_JUNGLE     = true;
        public boolean GEN_RUINED_PORTAL_MOUNTAIN   = true;
        public boolean GEN_RUINED_PORTAL_NETHER     = true;
        public boolean GEN_RUINED_PORTAL_OCEAN      = true;
        public boolean GEN_RUINED_PORTAL_STANDARD   = true;
        public boolean GEN_RUINED_PORTAL_SWAMP      = true;
        public boolean GEN_SHIPWRECK                = false;
        public boolean GEN_SHIPWRECK_BEACHED        = false;
        public boolean GEN_STRONGHOLD               = true;
        public boolean GEN_SWAMP_HUT                = true;
        public boolean GEN_VILLAGE_DESERT           = false;
        public boolean GEN_VILLAGE_PLAINS           = false;
        public boolean GEN_VILLAGE_SAVANNA          = false;
        public boolean GEN_VILLAGE_SNOWY            = false;
        public boolean GEN_VILLAGE_TAIGA            = false;
        public boolean GEN_WOODLAND_MANSION         = true;
        public boolean GEN_ICEBERGS                 = false;
        public boolean GEN_GEODES                   = false;
        public String[] CUSTOM_STRUCTURES           = new String[0];
    };

    public static class Drops {
        public boolean PHANTOM_ELYTRA           = false;
        public boolean ENDER_DRAGON_HEAD        = false;
        public boolean DROWNED_GOLD             = false;
        public boolean HUSK_SAND                = true;
        public boolean WITHER_ANCIENT_DEBRIS    = true;
        public boolean TROPICAL_FISH_CORAL      = true;
    };

    public static class Trading {
        public boolean WANDERING_TRADER_END_PORTAL_FRAME    = false;
        public boolean WANDERING_TRADER_SPONGE              = false;
        public boolean WANDERING_TRADER_GLOW_LICHEN         = false;
        public boolean WANDERING_TRADER_SCULK_CATALYST      = false;
        public boolean WANDERING_TRADER_LAVA_BUCKET         = false;
        public boolean WANDERING_TRADER_GLOW_BERRIES        = false;
        public boolean WANDERING_TRADER_CHORUS_PLANT        = false;
        public boolean WANDERING_TRADER_HEART_OF_THE_SEA    = false;
        public boolean WANDERING_TRADER_DISC_OTHERSIDE      = false;
        public boolean WANDERING_TRADER_DISC_5              = false;
        public boolean WANDERING_TRADER_DISC_PIGSTEP        = false;
        public boolean WANDERING_TRADER_REDSTONE            = false;
        public boolean MASON_DEEPSLATE                      = false;
        public boolean MASON_TUFF                           = false;
        public boolean MASON_CALCITE                        = false;
        public boolean MASON_REINFORCED_DEEPSLATE           = false;
        public boolean CLERIC_SHULKER_SHELL                 = false;
        public boolean CLERIC_ECHO_SHARD                    = false;
        public boolean PIGLIN_NETHERRACK                    = false;
        public boolean PIGLIN_NYLIUM                        = false;
        public boolean HOTV_CLERIC_BUDDING_AMETHYST         = false;
        public boolean HOTV_CLERIC_DIAMOND                  = false;
    }

    public static class Spawning {
        public String[] SPAWN_COORDS    = { "0", "64", "0" };
        public boolean STOP_BAT_SPAWNS  = true;
    }

    public static SkyBlockConfig.WorldGen WORLD_GEN;
    public static SkyBlockConfig.Structures STRUCTURES;
    public static SkyBlockConfig.Drops DROPS;
    public static SkyBlockConfig.Trading TRADING;
    public static SkyBlockConfig.Spawning SPAWNING;

    public static void register() {
        AutoConfig.register(SkyBlockConfig.class, GsonConfigSerializer::new);
        SkyBlockConfig instance = AutoConfig.getConfigHolder(SkyBlockConfig.class).getConfig();
        WORLD_GEN = instance.worldGen;
        STRUCTURES = instance.structures;
        DROPS = instance.drops;
        TRADING = instance.trading;
        SPAWNING = instance.spawning;
    }
}
