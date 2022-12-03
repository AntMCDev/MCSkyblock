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
        SETTINGS.put(new ResourceLocation("minecraft:plains"), toIsland("vanilla", "grass_block", "water", "sunflower"));
        //SETTINGS.put(new ResourceLocation("minecraft:plains"), toIsland("vanilla", "grass_block", "water", "sunflower"));
        //SETTINGS.put(new ResourceLocation("minecraft:plains"), toIsland("vanilla", "grass_block", "water", "sunflower"));
        //SETTINGS.put(new ResourceLocation("minecraft:plains"), toIsland("vanilla", "grass_block", "water", "sunflower"));
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
