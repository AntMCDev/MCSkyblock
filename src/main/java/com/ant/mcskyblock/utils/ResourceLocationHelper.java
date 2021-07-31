package com.ant.mcskyblock.utils;

import com.ant.mcskyblock.MCSkyblock;
import net.minecraft.resources.ResourceLocation;

public class ResourceLocationHelper {
    public static ResourceLocation prefix(String path) {
        return new ResourceLocation(MCSkyblock.MOD_ID, path);
    }
}
