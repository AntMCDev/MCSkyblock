package com.ant.mcskyblock.common.config;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class ChestLootConfig {
    public static class ChestItem {
        public ResourceLocation resourceLocation;
        public Integer count;

        public ChestItem() {}

        public ChestItem(ResourceLocation resourceLocation, Integer count) {
            this.resourceLocation = resourceLocation;
            this.count = count;
        }
    }

    public static Map<Integer, ChestItem> SETTINGS = new HashMap<>();

    public static void defaults() {
        SETTINGS.put(0, new ChestItem(new ResourceLocation("ice"), 1));
        SETTINGS.put(1, new ChestItem(new ResourceLocation("lava_bucket"), 1));
        SETTINGS.put(9, new ChestItem(new ResourceLocation("sugar_cane"), 1));
        SETTINGS.put(10, new ChestItem(new ResourceLocation("sea_pickle"), 1));
        SETTINGS.put(11, new ChestItem(new ResourceLocation("glow_berries"), 1));
        SETTINGS.put(26, new ChestItem(new ResourceLocation("obsidian"), 10));
    }
}
