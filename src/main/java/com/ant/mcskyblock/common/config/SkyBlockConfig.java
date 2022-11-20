package com.ant.mcskyblock.common.config;

import com.ant.mcskyblock.fabric.network.PacketHander;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.minecraft.world.InteractionResult;

import java.io.*;

@Config(name = "mcskyblock" )
@Config.Gui.Background("minecraft:textures/block/dirt.png")
public class SkyBlockConfig implements ConfigData, Serializable {
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

    public static class WorldGen implements Serializable {
        @Comment("This is a ToolTip")
        public boolean IS_OVERWORLD_SKYBLOCK    = true;
        public boolean IS_NETHER_SKYBLOCK       = true;
        public boolean IS_END_SKYBLOCK          = true;

        public boolean GENERATE_MAIN_ISLAND     = true;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 12)
        public int MAIN_ISLAND_RADIUS           = 4;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 5)
        public int MAIN_ISLAND_DEPTH            = 3;
        public boolean MAIN_ISLAND_TREE         = true;

        public boolean GENERATE_SUB_ISLANDS     = true;
        @Comment("How far out from the main island will sub islands generate")
        @ConfigEntry.BoundedDiscrete(min = 64, max = 5026)
        public int SUB_ISLAND_DISTANCE          = 64;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 7)
        public int SUB_ISLAND_RADIUS            = 4;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 5)
        public int SUB_ISLAND_DEPTH             = 3;

        public boolean APPLY_BIOME_DECORATIONS  = false;
    }

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
    };

    public static class Drops implements Serializable {
        public boolean PHANTOM_ELYTRA           = false;
        public boolean ENDER_DRAGON_HEAD        = false;
        public boolean DROWNED_GOLD             = false;
        public boolean HUSK_SAND                = true;
        public boolean WITHER_ANCIENT_DEBRIS    = true;
        public boolean TROPICAL_FISH_CORAL      = true;
    };

    public static class Trading implements Serializable {
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

    public static class Spawning implements Serializable {
        public String[] SPAWN_COORDS        = { "0", "64", "0" };
        public boolean STOP_BAT_SPAWNS      = true;
        public boolean SAPLINGS_DIE_ON_SAND = true;
    }

    @ConfigEntry.Gui.Excluded
    public static SkyBlockConfig INSTANCE;
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
        AutoConfig.getConfigHolder(SkyBlockConfig.class).registerSaveListener((a, b) -> {
            switch (b.preset) {
                case BEGINNER -> toBeginner(b);
                case TECHNICAL -> toTechnical(b);
            }
            requestSync(toBytes(b));
            return InteractionResult.PASS;
        });
        AutoConfig.getConfigHolder(SkyBlockConfig.class).registerLoadListener((a, b) -> { load(b); return InteractionResult.PASS; });
        load(AutoConfig.getConfigHolder(SkyBlockConfig.class).getConfig());
    }

    private static void load(SkyBlockConfig config) {
        INSTANCE = config;
        WORLD_GEN = config.worldGen;
        STRUCTURES = config.structures;
        DROPS = config.drops;
        TRADING = config.trading;
        SPAWNING = config.spawning;
        PRESET = config.preset;
    }

    private static void requestSync(byte[] data) {
        PacketHander.sendToServer(PacketHander.CONFIG_PACKET.getIdentifier(), PacketHander.bytesToByteBuf(data));
    }

    public static void loadConfigBytes(byte[] data) {
        AutoConfig.getConfigHolder(SkyBlockConfig.class).setConfig(fromBytes(data));
        load(AutoConfig.getConfigHolder(SkyBlockConfig.class).getConfig());
    }

    public static byte[] toBytes(SkyBlockConfig data) {
        byte[] result = null;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(data);
            out.flush();
            result = bos.toByteArray();
        } catch (IOException ignore) {}
        return result;
    }

    private static SkyBlockConfig fromBytes(byte[] data) {
        SkyBlockConfig result = null;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(data)) {
            ObjectInputStream in = new ObjectInputStream(bis);
            result = (SkyBlockConfig)in.readObject();
        } catch (ClassNotFoundException | IOException ignore) {}
        return result;
    }

    private static void toBeginner(SkyBlockConfig config) {
        config.worldGen.IS_OVERWORLD_SKYBLOCK               = true;
        config.worldGen.IS_NETHER_SKYBLOCK                  = true;
        config.worldGen.IS_END_SKYBLOCK                     = true;
        config.worldGen.GENERATE_MAIN_ISLAND                = true;
        config.worldGen.MAIN_ISLAND_RADIUS                  = 12;
        config.worldGen.MAIN_ISLAND_DEPTH                   = 5;
        config.worldGen.MAIN_ISLAND_TREE                    = true;
        config.worldGen.GENERATE_SUB_ISLANDS                = true;
        config.worldGen.SUB_ISLAND_DISTANCE                 = 64;
        config.worldGen.SUB_ISLAND_RADIUS                   = 7;
        config.worldGen.SUB_ISLAND_DEPTH                    = 5;
        config.worldGen.APPLY_BIOME_DECORATIONS             = true;

        config.structures.GEN_ANCIENT_CITY                  = true;
        config.structures.GEN_BASTION_REMNANT               = true;
        config.structures.GEN_BURIED_TREASURE               = true;
        config.structures.GEN_DESERT_PYRAMID                = true;
        config.structures.GEN_END_CITY                      = true;
        config.structures.GEN_FORTRESS                      = true;
        config.structures.GEN_IGLOO                         = true;
        config.structures.GEN_JUNGLE_TEMPLE                 = true;
        config.structures.GEN_MINESHAFT                     = true;
        config.structures.GEN_MINESHAFT_MESA                = true;
        config.structures.GEN_NETHER_FOSSIL                 = true;
        config.structures.GEN_OCEAN_MONUMENT                = true;
        config.structures.GEN_OCEAN_RUIN                    = true;
        config.structures.GEN_PILLAGER_OUTPOST              = true;
        config.structures.GEN_RUINED_PORTAL                 = true;
        config.structures.GEN_SHIPWRECK                     = true;
        config.structures.GEN_STRONGHOLD                    = true;
        config.structures.GEN_SWAMP_HUT                     = true;
        config.structures.GEN_VILLAGE                       = true;
        config.structures.GEN_WOODLAND_MANSION              = true;
        config.structures.GEN_ICEBERGS                      = true;
        config.structures.GEN_GEODES                        = true;

        config.drops.PHANTOM_ELYTRA                         = true;
        config.drops.ENDER_DRAGON_HEAD                      = true;
        config.drops.DROWNED_GOLD                           = true;
        config.drops.HUSK_SAND                              = true;
        config.drops.WITHER_ANCIENT_DEBRIS                  = true;
        config.drops.TROPICAL_FISH_CORAL                    = true;

        config.trading.WANDERING_TRADER_END_PORTAL_FRAME    = true;
        config.trading.WANDERING_TRADER_SPONGE              = true;
        config.trading.WANDERING_TRADER_GLOW_LICHEN         = true;
        config.trading.WANDERING_TRADER_SCULK_CATALYST      = true;
        config.trading.WANDERING_TRADER_LAVA_BUCKET         = true;
        config.trading.WANDERING_TRADER_GLOW_BERRIES        = true;
        config.trading.WANDERING_TRADER_CHORUS_PLANT        = true;
        config.trading.WANDERING_TRADER_HEART_OF_THE_SEA    = true;
        config.trading.WANDERING_TRADER_DISC_OTHERSIDE      = true;
        config.trading.WANDERING_TRADER_DISC_5              = true;
        config.trading.WANDERING_TRADER_DISC_PIGSTEP        = true;
        config.trading.WANDERING_TRADER_REDSTONE            = true;
        config.trading.MASON_DEEPSLATE                      = true;
        config.trading.MASON_TUFF                           = true;
        config.trading.MASON_CALCITE                        = true;
        config.trading.MASON_REINFORCED_DEEPSLATE           = true;
        config.trading.CLERIC_SHULKER_SHELL                 = true;
        config.trading.CLERIC_ECHO_SHARD                    = true;
        config.trading.PIGLIN_NETHERRACK                    = true;
        config.trading.PIGLIN_NYLIUM                        = true;
        config.trading.HOTV_CLERIC_BUDDING_AMETHYST         = true;
        config.trading.HOTV_CLERIC_DIAMOND                  = true;

        config.spawning.STOP_BAT_SPAWNS                     = true;
        config.spawning.SAPLINGS_DIE_ON_SAND                = true;
    }

    private static void toTechnical(SkyBlockConfig config) {
        config.worldGen.IS_OVERWORLD_SKYBLOCK               = true;
        config.worldGen.IS_NETHER_SKYBLOCK                  = true;
        config.worldGen.IS_END_SKYBLOCK                     = true;
        config.worldGen.GENERATE_MAIN_ISLAND                = true;
        config.worldGen.MAIN_ISLAND_RADIUS                  = 1;
        config.worldGen.MAIN_ISLAND_DEPTH                   = 1;
        config.worldGen.MAIN_ISLAND_TREE                    = true;
        config.worldGen.GENERATE_SUB_ISLANDS                = false;
        config.worldGen.SUB_ISLAND_DISTANCE                 = 64;
        config.worldGen.SUB_ISLAND_RADIUS                   = 1;
        config.worldGen.SUB_ISLAND_DEPTH                    = 1;
        config.worldGen.APPLY_BIOME_DECORATIONS             = false;

        config.structures.GEN_ANCIENT_CITY                  = false;
        config.structures.GEN_BASTION_REMNANT               = false;
        config.structures.GEN_BURIED_TREASURE               = false;
        config.structures.GEN_DESERT_PYRAMID                = false;
        config.structures.GEN_END_CITY                      = false;
        config.structures.GEN_FORTRESS                      = false;
        config.structures.GEN_IGLOO                         = false;
        config.structures.GEN_JUNGLE_TEMPLE                 = false;
        config.structures.GEN_MINESHAFT                     = false;
        config.structures.GEN_MINESHAFT_MESA                = false;
        config.structures.GEN_NETHER_FOSSIL                 = false;
        config.structures.GEN_OCEAN_MONUMENT                = false;
        config.structures.GEN_OCEAN_RUIN                    = false;
        config.structures.GEN_PILLAGER_OUTPOST              = false;
        config.structures.GEN_RUINED_PORTAL                 = false;
        config.structures.GEN_SHIPWRECK                     = false;
        config.structures.GEN_STRONGHOLD                    = false;
        config.structures.GEN_SWAMP_HUT                     = false;
        config.structures.GEN_VILLAGE                       = false;
        config.structures.GEN_WOODLAND_MANSION              = false;
        config.structures.GEN_ICEBERGS                      = false;
        config.structures.GEN_GEODES                        = false;

        config.drops.PHANTOM_ELYTRA                         = true;
        config.drops.ENDER_DRAGON_HEAD                      = true;
        config.drops.DROWNED_GOLD                           = true;
        config.drops.HUSK_SAND                              = true;
        config.drops.WITHER_ANCIENT_DEBRIS                  = true;
        config.drops.TROPICAL_FISH_CORAL                    = true;

        config.trading.WANDERING_TRADER_END_PORTAL_FRAME    = true;
        config.trading.WANDERING_TRADER_SPONGE              = true;
        config.trading.WANDERING_TRADER_GLOW_LICHEN         = true;
        config.trading.WANDERING_TRADER_SCULK_CATALYST      = true;
        config.trading.WANDERING_TRADER_LAVA_BUCKET         = true;
        config.trading.WANDERING_TRADER_GLOW_BERRIES        = true;
        config.trading.WANDERING_TRADER_CHORUS_PLANT        = true;
        config.trading.WANDERING_TRADER_HEART_OF_THE_SEA    = true;
        config.trading.WANDERING_TRADER_DISC_OTHERSIDE      = true;
        config.trading.WANDERING_TRADER_DISC_5              = true;
        config.trading.WANDERING_TRADER_DISC_PIGSTEP        = true;
        config.trading.WANDERING_TRADER_REDSTONE            = true;
        config.trading.MASON_DEEPSLATE                      = true;
        config.trading.MASON_TUFF                           = true;
        config.trading.MASON_CALCITE                        = true;
        config.trading.MASON_REINFORCED_DEEPSLATE           = true;
        config.trading.CLERIC_SHULKER_SHELL                 = true;
        config.trading.CLERIC_ECHO_SHARD                    = true;
        config.trading.PIGLIN_NETHERRACK                    = true;
        config.trading.PIGLIN_NYLIUM                        = true;
        config.trading.HOTV_CLERIC_BUDDING_AMETHYST         = true;
        config.trading.HOTV_CLERIC_DIAMOND                  = true;

        config.spawning.STOP_BAT_SPAWNS                     = true;
        config.spawning.SAPLINGS_DIE_ON_SAND                = false;
    }
}
