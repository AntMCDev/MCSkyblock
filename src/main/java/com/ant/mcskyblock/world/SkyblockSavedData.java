package com.ant.mcskyblock.world;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

public class SkyblockSavedData extends SavedData {
    private static final String NAME = "skyblock_islands";

    public boolean generated;

    public SkyblockSavedData() {

    }

    public static SkyblockSavedData get(ServerLevel world) {
        return world.getDataStorage().computeIfAbsent(SkyblockSavedData::load, SkyblockSavedData::new, NAME);
    }

    public IslandPosition getSpawn() {
        IslandPosition pos = new IslandPosition(0, 0);
        generated = true;
        setDirty();
        return pos;
    }

    public static SkyblockSavedData load(CompoundTag nbt) {
        SkyblockSavedData skyblockSavedData = new SkyblockSavedData();
        skyblockSavedData.generated = nbt.getBoolean("generated");
        return skyblockSavedData;
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        nbt.putBoolean("generated", generated);
        return nbt;
    }
}
