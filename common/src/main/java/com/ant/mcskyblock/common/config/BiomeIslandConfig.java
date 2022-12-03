package com.ant.mcskyblock.common.config;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class BiomeIslandConfig {
    public static class Island {
        public String mod = null;
        public ResourceLocation base = null;
        public ResourceLocation fluid = null;
        public ResourceLocation accessory = null;

        public Island() {}

        public Island(String mod, ResourceLocation base, ResourceLocation fluid, ResourceLocation accessory) {
            this.mod = mod;
            this.base = base;
            this.fluid = fluid;
            this.accessory = accessory;
        }
    }

    public static Map<ResourceLocation, Island> SETTINGS = new HashMap<>();

    public static void defaults() {
        SETTINGS.put(new ResourceLocation("minecraft:plains")                   , toIsland("vanilla", "grass_block"               , "water" , "potted_oak_sapling"));
        SETTINGS.put(new ResourceLocation("minecraft:sunflower_plains")         , toIsland("vanilla", "grass_block"               , null    , "sunflower"));
        SETTINGS.put(new ResourceLocation("minecraft:snowy_plains")             , toIsland("vanilla", "snow_block"                , null    , "snow"));
        SETTINGS.put(new ResourceLocation("minecraft:ice_spikes")               , toIsland("vanilla", "blue_ice"                  , null    , "packed_ice"));
        SETTINGS.put(new ResourceLocation("minecraft:desert")                   , toIsland("vanilla", "sandstone"                 , null    , "cactus"));
        SETTINGS.put(new ResourceLocation("minecraft:swamp")                    , toIsland("vanilla", "mud"                       , null    , "peony"));
        SETTINGS.put(new ResourceLocation("minecraft:mangrove_swamp")           , toIsland("vanilla", "muddy_mangrove_roots"      , null    , "potted_mangrove_propagule"));
        SETTINGS.put(new ResourceLocation("minecraft:forest")                   , toIsland("vanilla", "podzol"                    , null    , "potted_spruce_sapling"));
        SETTINGS.put(new ResourceLocation("minecraft:flower_forest")            , toIsland("vanilla", "grass_block"               , null    , "lilac"));
        SETTINGS.put(new ResourceLocation("minecraft:birch_forest")             , toIsland("vanilla", "grass_block"               , null    , "birch_sapling"));
        SETTINGS.put(new ResourceLocation("minecraft:dark_forest")              , toIsland("vanilla", "grass_block"               , null    , "potted_dark_oak_sapling"));
        SETTINGS.put(new ResourceLocation("minecraft:old_growth_birch_forest")  , toIsland("vanilla", "grass_block"               , null    , "potted_dark_oak_sapling"));
        SETTINGS.put(new ResourceLocation("minecraft:old_growth_pine_taiga")    , toIsland("vanilla", "grass_block"               , null    , "potted_dark_oak_sapling"));
        SETTINGS.put(new ResourceLocation("minecraft:old_growth_spruce_taiga")  , toIsland("vanilla", "grass_block"               , null    , "potted_dark_oak_sapling"));
        SETTINGS.put(new ResourceLocation("minecraft:taiga")                    , toIsland("vanilla", "grass_block"               , null    , "rose_bush"));
        SETTINGS.put(new ResourceLocation("minecraft:snowy_taiga")              , toIsland("vanilla", "grass_block"               , null    , "potted_spruce_sapling"));
        SETTINGS.put(new ResourceLocation("minecraft:savanna")                  , toIsland("vanilla", "grass_block"               , null    , "potted_acacia_sapling"));
        SETTINGS.put(new ResourceLocation("minecraft:savanna_plateau")          , toIsland("vanilla", "grass_block"               , null    , "potted_acacia_sapling"));
        SETTINGS.put(new ResourceLocation("minecraft:windswept_hills")          , toIsland("vanilla", "grass_block"               , null    , "raw_copper_block"));
        SETTINGS.put(new ResourceLocation("minecraft:windswept_gravelly_hills") , toIsland("vanilla", "mcskyblock:static_gravel"  , null    , "gravel"));
        SETTINGS.put(new ResourceLocation("minecraft:windswept_forest")         , toIsland("vanilla", "grass_block"               , null    , "raw_iron_block"));
        SETTINGS.put(new ResourceLocation("minecraft:windswept_savanna")        , toIsland("vanilla", "coarse_dirt"               , null    , "potted_acacia_sapling"));
        SETTINGS.put(new ResourceLocation("minecraft:jungle")                   , toIsland("vanilla", "grass_block"               , null    , "potted_jungle_sapling"));
        SETTINGS.put(new ResourceLocation("minecraft:sparse_jungle")            , toIsland("vanilla", "grass_block"               , null    , "potted_jungle_sapling"));
        SETTINGS.put(new ResourceLocation("minecraft:bamboo_jungle")            , toIsland("vanilla", "grass_block"               , null    , "bamboo_sapling"));
        SETTINGS.put(new ResourceLocation("minecraft:badlands")                 , toIsland("vanilla", "terracotta"                , null    , "potted_dead_bush"));
        SETTINGS.put(new ResourceLocation("minecraft:eroded_badlands")          , toIsland("vanilla", "red_terracotta"            , null    , "potted_dead_bush"));
        SETTINGS.put(new ResourceLocation("minecraft:wooded_badlands")          , toIsland("vanilla", "orange_terracotta"         , null    , "potted_oak_sapling"));
        SETTINGS.put(new ResourceLocation("minecraft:meadow")                   , toIsland("vanilla", "grass_block"               , null    , "bee_nest"));
        SETTINGS.put(new ResourceLocation("minecraft:grove")                    , toIsland("vanilla", "snow_block"                , null    , "potted_spruce_sapling"));
        SETTINGS.put(new ResourceLocation("minecraft:snowy_slopes")             , toIsland("vanilla", "snow_block"                , null    , "powder_snow_cauldron"));
        SETTINGS.put(new ResourceLocation("minecraft:frozen_peaks")             , toIsland("vanilla", "packed_ice"                , null    , "blue_ice"));
        SETTINGS.put(new ResourceLocation("minecraft:jagged_peaks")             , toIsland("vanilla", "ice"                       , null    , "stone"));
        SETTINGS.put(new ResourceLocation("minecraft:stony_peaks")              , toIsland("vanilla", "andesite"                  , null    , "calcite"));
        SETTINGS.put(new ResourceLocation("minecraft:river")                    , toIsland("vanilla", "mcskyblock:static_sand"    , "water" , null));
        SETTINGS.put(new ResourceLocation("minecraft:frozen_river")             , toIsland("vanilla", "ice"                       , null    , null));
        SETTINGS.put(new ResourceLocation("minecraft:beach")                    , toIsland("vanilla", "mcskyblock:static_sand"    , null    , "sugar_cane"));
        SETTINGS.put(new ResourceLocation("minecraft:snowy_beach")              , toIsland("vanilla", "mcskyblock:static_sand"    , null    , "snow"));
        SETTINGS.put(new ResourceLocation("minecraft:stony_shore")              , toIsland("vanilla", "mcskyblock:static_sand"    , null    , "stone"));
        SETTINGS.put(new ResourceLocation("minecraft:warm_ocean")               , toIsland("vanilla", "brain_coral_block"         , null    , "water"));
        SETTINGS.put(new ResourceLocation("minecraft:lukewarm_ocean")           , toIsland("vanilla", "bubble_coral_block"        , null    , "water"));
        SETTINGS.put(new ResourceLocation("minecraft:deep_lukewarm_ocean")      , toIsland("vanilla", "fire_coral_block"          , null    , "water"));
        SETTINGS.put(new ResourceLocation("minecraft:ocean")                    , toIsland("vanilla", "clay"                      , null    , "water"));
        SETTINGS.put(new ResourceLocation("minecraft:deep_ocean")               , toIsland("vanilla", "mcskyblock:static_gravel"  , null    , "dried_kelp_block"));
        SETTINGS.put(new ResourceLocation("minecraft:cold_ocean")               , toIsland("vanilla", "mcskyblock:static_gravel"  , null    , "dried_kelp_block"));
        SETTINGS.put(new ResourceLocation("minecraft:deep_cold_ocean")          , toIsland("vanilla", "mcskyblock:static_gravel"  , null    , "dried_kelp_block"));
        SETTINGS.put(new ResourceLocation("minecraft:frozen_ocean")             , toIsland("vanilla", "blue_ice"                  , null    , null));
        SETTINGS.put(new ResourceLocation("minecraft:deep_frozen_ocean")        , toIsland("vanilla", "packed_ice"                , null    , null));
        SETTINGS.put(new ResourceLocation("minecraft:mushroom_fields")          , toIsland("vanilla", "mycelium"                  , null    , "potted_red_mushroom"));
        SETTINGS.put(new ResourceLocation("minecraft:dripstone_caves")          , toIsland("vanilla", "dripstone_block"           , null    , "pointed_dripstone"));
        SETTINGS.put(new ResourceLocation("minecraft:lush_caves")               , toIsland("vanilla", "moss_block"                , null    , "potted_flowering_azalea"));
        SETTINGS.put(new ResourceLocation("minecraft:deep_dark")                , toIsland("vanilla", "sculk_catalyst"            , null    , "sculk_shrieker"));
        SETTINGS.put(new ResourceLocation("minecraft:nether_wastes")            , toIsland("vanilla", "netherrack"                , null    , "potted_brown_mushroom"));
        SETTINGS.put(new ResourceLocation("minecraft:warped_forest")            , toIsland("vanilla", "warped_nylium"             , null    , "potted_warped_fungus"));
        SETTINGS.put(new ResourceLocation("minecraft:crimson_forest")           , toIsland("vanilla", "crimson_nylium"            , null    , "potted_crimson_fungus"));
        SETTINGS.put(new ResourceLocation("minecraft:soul_sand_valley")         , toIsland("vanilla", "soul_soil"                 , null    , "bone_block"));
        SETTINGS.put(new ResourceLocation("minecraft:basalt_deltas")            , toIsland("vanilla", "basalt"                    , null    , "magma_block"));
        SETTINGS.put(new ResourceLocation("minecraft:the_end")                  , toIsland("vanilla", "void"                      , null    , null));
        SETTINGS.put(new ResourceLocation("minecraft:end_highlands")            , toIsland("vanilla", "end_stone"                 , null    , "chorus_flower"));
        SETTINGS.put(new ResourceLocation("minecraft:end_midlands")             , toIsland("vanilla", "end_stone"                 , null    , "chorus_flower"));
        SETTINGS.put(new ResourceLocation("minecraft:small_end_islands")        , toIsland("vanilla", "void"                      , null    , null));
        SETTINGS.put(new ResourceLocation("minecraft:end_barrens")              , toIsland("vanilla", "end_stone"                 , null    , "chorus_flower"));
    }

    private static Island toIsland(String mod, String base, String fluid, String accessory) {
        return new Island(
                mod,
                base == null ? null : new ResourceLocation(base),
                fluid == null ? null : new ResourceLocation(fluid),
                accessory == null ? null : new ResourceLocation(accessory)
        );
    }
}
