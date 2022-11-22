package com.ant.mcskyblock.common.advancements;

import com.ant.mcskyblock.common.SkyBlock;
import net.minecraft.resources.ResourceLocation;

import java.util.Locale;

public interface IAdvancement {
    boolean enabled();
    String getJSON();
    String getName();

    default ResourceLocation getResourceLocation() {
        return new ResourceLocation(SkyBlock.MOD_NAME, getName().toLowerCase(Locale.ROOT).replace(" ", "_"));
    }
}
