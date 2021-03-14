package com.ant.mcskyblock.utils;

import com.ant.mcskyblock.config.ConfigHandler;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public final class SpawnUtils {
    private static List<EntityType> stopSpawns = new ArrayList<>();

    static {
        if (ConfigHandler.COMMON.stopBatSpawns.get()) {
            stopSpawns.add(EntityType.BAT);
        }
    }

    @SubscribeEvent
    public static void onBiomeLoadingEvent(BiomeLoadingEvent event) {
        for (EntityClassification c : event.getSpawns().getSpawnerTypes()) {
            List<MobSpawnInfo.Spawners> spawns = event.getSpawns().getSpawner(c);
            for (EntityType t : stopSpawns) {
                spawns.removeIf(e -> e.type == t);
            }
        }
    }
}
