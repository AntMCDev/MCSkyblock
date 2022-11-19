package com.ant.mcskyblock.common.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "mcskyblock" )
@Config.Gui.Background("minecraft:textures/block/dirt.png")
public class SkyBlockConfig implements ConfigData {
    public enum Preset {
        BEGINNER, TECHNICAL, CUSTOM
    }

    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public Preset preset = Preset.CUSTOM;


    @ConfigEntry.Category("worldGen")
    @ConfigEntry.Gui.TransitiveObject
    private WorldGen worldGen = new WorldGen();

    @ConfigEntry.Category("structures")
    @ConfigEntry.Gui.TransitiveObject
    private Structures structures = new Structures();

    @ConfigEntry.Category("drops")
    @ConfigEntry.Gui.TransitiveObject
    private Drops drops = new Drops();

    @ConfigEntry.Category("trading")
    @ConfigEntry.Gui.TransitiveObject
    private Trading trading = new Trading();

    @ConfigEntry.Category("spawning")
    @ConfigEntry.Gui.TransitiveObject
    private Spawning spawning = new Spawning();

    public static class WorldGen {
        public boolean IS_OVERWORLD_SKYBLOCK    = true;
        public boolean IS_NETHER_SKYBLOCK       = true;
        public boolean IS_END_SKYBLOCK          = true;
        public boolean GENERATE_MAIN_ISLAND     = true;
        public boolean MAIN_ISLAND_TREE         = true;
        public boolean GENERATE_SUB_ISLANDS     = true;
        public boolean APPLY_BIOME_DECORATIONS  = false;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 50)
        public int MAIN_ISLAND_RADIUS           = 4;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 5)
        public int MAIN_ISLAND_DEPTH            = 3;
        @Comment("How far out from the main island will sub islands generate")
        @ConfigEntry.BoundedDiscrete(min = 64, max = 5026)
        public int SUB_ISLAND_DISTANCE          = 64;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 50)
        public int SUB_ISLAND_RADIUS            = 4;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 5)
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
        public boolean GEN_OCEAN_RUIN               = true;
        public boolean GEN_PILLAGER_OUTPOST         = true;
        public boolean GEN_RUINED_PORTAL            = true;
        public boolean GEN_SHIPWRECK                = false;
        public boolean GEN_STRONGHOLD               = true;
        public boolean GEN_SWAMP_HUT                = true;
        public boolean GEN_VILLAGE                  = false;
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

    @ConfigEntry.Gui.Excluded
    public static SkyBlockConfig.WorldGen WORLD_GEN;
    @ConfigEntry.Gui.Excluded
    public static SkyBlockConfig.Structures STRUCTURES;
    @ConfigEntry.Gui.Excluded
    public static SkyBlockConfig.Drops DROPS;
    @ConfigEntry.Gui.Excluded
    public static SkyBlockConfig.Trading TRADING;
    @ConfigEntry.Gui.Excluded
    public static SkyBlockConfig.Spawning SPAWNING;
    @ConfigEntry.Gui.Excluded
    public static Preset PRESET;

    public static void register() {
        AutoConfig.register(SkyBlockConfig.class, GsonConfigSerializer::new);
        SkyBlockConfig instance = AutoConfig.getConfigHolder(SkyBlockConfig.class).getConfig();
        WORLD_GEN = instance.worldGen;
        STRUCTURES = instance.structures;
        DROPS = instance.drops;
        TRADING = instance.trading;
        SPAWNING = instance.spawning;
        PRESET = instance.preset;
    }
}
