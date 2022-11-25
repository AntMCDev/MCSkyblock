package com.ant.mcskyblock.common.config;

import com.ant.mcskyblock.common.config.preset.*;
import com.ant.mcskyblock.common.network.PacketHandler;
import com.ant.mcskyblock.common.utils.SerializationUtils;

import java.io.*;

/**
 * [COMMON] CONFIG - This is the generic container class for configurations, it is platform independent - however
 * any GUI libs will have to interpret this dynamically
 */
public class Config implements Serializable {
    public static Config INSTANCE = new Config();

    public enum Preset {
        Classic, Hybrid, OneBlock, Insane, Custom
    }

    public Preset preset = Preset.Classic;
    public boolean achievementsEnabled = true;

    public WorldGen worldGen = new WorldGen();
    public static class WorldGen implements Serializable {
        public boolean IS_OVERWORLD_SKYBLOCK    = true;
        public boolean IS_NETHER_SKYBLOCK       = true;
        public boolean IS_END_SKYBLOCK          = true;
        public boolean GENERATE_MAIN_ISLAND     = true;
        public int MAIN_ISLAND_RADIUS           = 4;
        public int MAIN_ISLAND_DEPTH            = 3;
        public boolean MAIN_ISLAND_TREE         = true;
        public boolean GENERATE_SUB_ISLANDS     = true;
        public int SUB_ISLAND_DISTANCE          = 128;
        public int SUB_ISLAND_RADIUS            = 4;
        public int SUB_ISLAND_DEPTH             = 3;
        public boolean APPLY_BIOME_DECORATIONS  = false;
    }

    public Structures structures = new Structures();
    public static class Structures implements Serializable {
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
    }

    public Drops drops = new Drops();
    public static class Drops implements Serializable {
        public boolean PHANTOM_ELYTRA           = false;
        public boolean ENDER_DRAGON_HEAD        = false;
        public boolean DROWNED_GOLD             = false;
        public boolean HUSK_SAND                = true;
        public boolean RAVENGER_SCULK_SHRIEKER  = true;
        public boolean TROPICAL_FISH_CORAL      = true;

        public boolean BRUTE_ANCIENT_DEBRIS     = true;
        public boolean FISHING_COCO_BEANS       = true;
    }

    public Trading trading = new Trading();
    public static class Trading implements Serializable {
        public boolean WANDERING_TRADER_SPONGE              = false;
        public boolean WANDERING_TRADER_GLOW_LICHEN         = false;
        public boolean WANDERING_TRADER_SCULK_CATALYST      = false;
        public boolean WANDERING_TRADER_LAVA_BUCKET         = false;
        public boolean WANDERING_TRADER_GLOW_BERRIES        = false;
        public boolean WANDERING_TRADER_CHORUS_PLANT        = false;
        public boolean WANDERING_TRADER_DISC_OTHERSIDE      = false;
        public boolean WANDERING_TRADER_DISC_5              = false;
        public boolean WANDERING_TRADER_DISC_PIGSTEP        = false;
        public boolean WANDERING_TRADER_TWO_TALL_FLOWERS    = false;
        public boolean MASON_DEEPSLATE                      = false;
        public boolean MASON_TUFF                           = false;
        public boolean MASON_CALCITE                        = false;
        public boolean CLERIC_SHULKER_SHELL                 = false;
        public boolean CLERIC_ECHO_SHARD                    = false;
        public boolean PIGLIN_WEEPING_VINES                 = false;
        public boolean PIGLIN_NYLIUM                        = false;
        public boolean HOTV_CLERIC_BUDDING_AMETHYST         = false;
        public boolean HOTV_CLERIC_DIAMOND                  = false;
    }

    public Spawning spawning = new Spawning();
    public static class Spawning implements Serializable {
        public String[] SPAWN_COORDS                = { "0", "64", "0" };
        public boolean STOP_BAT_SPAWNS              = true;
        public boolean SAPLINGS_DIE_ON_SAND         = true;
        public boolean ENDERMITES_SPAWN_ENDSTONE    = true;
        public boolean SPAWN_PIGLIN_BRUTES          = true;
    }

    public Crafting crafting = new Crafting();
    public static class Crafting implements Serializable {
        public boolean CORAL_BLOCKS             = true;
        public boolean KELP                     = true;
        public boolean NETHERRACK               = true;

        public boolean SPORE_BLOSSOM            = true;
        public boolean BUNDLE                   = false;
        public boolean HEART_OF_THE_SEA         = true;
        public boolean END_PORTAL_FRAME         = false;
        public boolean RED_SAND                 = true;
        public boolean COBWEB                   = false;

        public boolean MYCELIUM                 = true;
        public boolean GRASS_BLOCK              = true;

        public boolean ANCIENT_CITY_SWIFT_SNEAK = true;
    }

    public byte[] toBytes() {
        return SerializationUtils.serialize(this);
    }

    public Config fromBytes(byte[] data) {
        return SerializationUtils.deserialize(data, Config.class);
    }

    public void preSave() {
        switch (preset) {
            case Classic -> updateToConfig(new Classic());
            case Hybrid -> updateToConfig(new Hybrid());
            case OneBlock -> updateToConfig(new OneBlock());
            case Insane -> updateToConfig(new Insane());
        }
    }

    public void postSave() {
        PacketHandler.INSTANCE.sendToServer(PacketHandler.CONFIG_PACKET.getIdentifier(), toBytes());
    }

    public void download(byte[] data) {
        Config c = fromBytes(data);
        updateToConfig(c);
    }

    private void updateToConfig(Config config) {
        preset = config.preset;
        achievementsEnabled = config.achievementsEnabled;
        worldGen = config.worldGen;
        structures = config.structures;
        drops = config.drops;
        trading = config.trading;
        spawning = config.spawning;
        crafting = config.crafting;
    }
}
