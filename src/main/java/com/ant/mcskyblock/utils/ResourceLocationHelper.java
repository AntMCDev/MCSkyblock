package com.ant.mcskyblock.utils;

import com.ant.mcskyblock.MCSkyblock;
import net.minecraft.resources.ResourceLocation;

// Code adapted from Vaskii's Botania source
// https://github.com/Vazkii/Botania
public class ResourceLocationHelper {
    public static ResourceLocation prefix(String path) {
        return new ResourceLocation(MCSkyblock.MOD_ID, path);
    }
}
