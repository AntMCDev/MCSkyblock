package com.ant.mcskyblock.common.config;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class BiomeIslandConfig {
    public static class Island {
        public ResourceLocation base = null;
        public ResourceLocation fluid = null;
        public ResourceLocation accessory = null;
    }

    public static Map<ResourceLocation, Island> SETTINGS = new HashMap<>();
}
