package com.ant.mcskyblock.common.config;

import com.ant.mcskyblock.common.config.preset.*;
import com.ant.mcskyblock.fabric.network.PacketHander;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.minecraft.world.InteractionResult;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Config(name = "mcskyblock" )
@Config.Gui.Background("minecraft:textures/block/dirt.png")
public class SkyBlockConfig implements ConfigData, Serializable {
    public enum Preset {
        Classic, Hybrid, OneBlock, Insane, Custom
    }
    @ConfigEntry.Gui.Excluded
    public static final Map<Preset, IPreset> PRESET_CONVERTERS = new HashMap<>();
    static {
        PRESET_CONVERTERS.put(Preset.Classic, new Classic());
        PRESET_CONVERTERS.put(Preset.Hybrid, new Hybrid());
        PRESET_CONVERTERS.put(Preset.OneBlock, new OneBlock());
        PRESET_CONVERTERS.put(Preset.Insane, new Insane());
    }

    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    @ConfigEntry.Gui.PrefixText
    public Preset preset = Preset.Custom;

    @Comment("This enables skyblock specific achievements")
    public boolean achievementsEnabled = true;

    @ConfigEntry.Category("worldGen")
    @ConfigEntry.Gui.TransitiveObject
    public WorldGen worldGen = new WorldGen();

    @ConfigEntry.Category("structures")
    @ConfigEntry.Gui.TransitiveObject
    public Structures structures = new Structures();

    @ConfigEntry.Category("drops")
    @ConfigEntry.Gui.TransitiveObject
    public Drops drops = new Drops();

    @ConfigEntry.Category("trading")
    @ConfigEntry.Gui.TransitiveObject
    public Trading trading = new Trading();

    @ConfigEntry.Category("spawning")
    @ConfigEntry.Gui.TransitiveObject
    public Spawning spawning = new Spawning();

    @ConfigEntry.Category("crafting")
    @ConfigEntry.Gui.TransitiveObject
    public Crafting crafting = new Crafting();

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
        @ConfigEntry.BoundedDiscrete(min = 128, max = 2147483647)
        public int SUB_ISLAND_DISTANCE          = 128;
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
        public boolean RAVENGER_SCULK_SHRIEKER  = true;
        public boolean TROPICAL_FISH_CORAL      = true;

        public boolean BRUTE_ANCIENT_DEBRIS     = true;
        public boolean FISHING_COCO_BEANS       = true;
    };

    public static class Trading implements Serializable {
        public boolean WANDERING_TRADER_TREES               = false;
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

    public static class Spawning implements Serializable {
        public String[] SPAWN_COORDS                = { "0", "64", "0" };
        public boolean STOP_BAT_SPAWNS              = true;
        public boolean SAPLINGS_DIE_ON_SAND         = true;
        public boolean ENDERMITES_SPAWN_ENDSTONE    = true;
        public boolean SPAWN_PIGLIN_BRUTES          = true;
    }

    public static class Crafting implements Serializable {
        public boolean CORAL_BLOCKS         = true;
        public boolean KELP                 = true;
        public boolean NETHERRACK           = true;

        public boolean SPORE_BLOSSOM        = true;
        public boolean BUNDLE               = false;
        public boolean HEART_OF_THE_SEA     = true;
        public boolean END_PORTAL_FRAME     = false;
        public boolean RED_SAND             = true;
        public boolean COBWEB               = false;
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
    public static SkyBlockConfig.Crafting CRAFTING;
    @ConfigEntry.Gui.Excluded
    public static Preset PRESET;

    public static void register() {
        AutoConfig.register(SkyBlockConfig.class, GsonConfigSerializer::new);
        AutoConfig.getConfigHolder(SkyBlockConfig.class).registerSaveListener((a, b) -> {
            if (PRESET_CONVERTERS.containsKey(b.preset)) { PRESET_CONVERTERS.get(b.preset).convert(b); }
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
        CRAFTING = config.crafting;
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
}
