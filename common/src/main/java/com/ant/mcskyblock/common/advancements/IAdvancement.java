package com.ant.mcskyblock.common.advancements;

import com.ant.mcskyblock.common.SkyBlock;
import com.ant.mcskyblock.common.config.Config;
import net.minecraft.resources.ResourceLocation;

import java.util.Locale;

/**
 * [COMMON] INTERFACE - Achievement interface, all new achievements should implement this
 */
public interface IAdvancement {
    default boolean enabled() { return Config.INSTANCE.achievementsEnabled; }
    String getJSON();
    String getName();

    default ResourceLocation getResourceLocation() {
        return new ResourceLocation(SkyBlock.MOD_NAME, getName().toLowerCase(Locale.ROOT).replace(" ", "_"));
    }
}
