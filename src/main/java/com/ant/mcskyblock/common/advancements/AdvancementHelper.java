package com.ant.mcskyblock.common.advancements;

import com.google.gson.JsonObject;
import net.minecraft.util.GsonHelper;

public class AdvancementHelper {
    public static JsonObject toJSON(IAdvancement advancement) {
        return GsonHelper.parse(advancement.getJSON());
    }
}
