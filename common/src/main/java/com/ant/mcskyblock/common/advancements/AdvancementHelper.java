package com.ant.mcskyblock.common.advancements;

import com.google.gson.JsonObject;
import net.minecraft.util.GsonHelper;

/**
 * [COMMON] HELPER CLASS - convert advancement classes to JsonObject instances
 */
public class AdvancementHelper {
    public static JsonObject toJSON(IAdvancement advancement) {
        return GsonHelper.parse(advancement.getJSON());
    }
}
