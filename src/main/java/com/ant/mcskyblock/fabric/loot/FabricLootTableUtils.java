package com.ant.mcskyblock.fabric.loot;

import com.ant.mcskyblock.common.loot.LootTableUtils;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;

// REQ FABRIC

public class FabricLootTableUtils {
    public static void register() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            LootTableUtils.register(lootManager, id, tableBuilder);
        });
    }
}
