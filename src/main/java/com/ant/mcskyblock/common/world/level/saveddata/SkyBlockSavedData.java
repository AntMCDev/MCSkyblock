package com.ant.mcskyblock.common.world.level.saveddata;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;


// NON FABRIC
public class SkyBlockSavedData extends SavedData {
    private static final String NAME = "skyblock_islands";
    private static final String GENERATED_KEY = "generated";

    public boolean generated;

    public SkyBlockSavedData() {
        generated = false;
    }

    public static SkyBlockSavedData get(ServerLevel world) {
        return world.getServer().overworld().getDataStorage().computeIfAbsent(
                SkyBlockSavedData::readNbt,
                SkyBlockSavedData::new,
                NAME
        );    }

    private static SkyBlockSavedData readNbt(CompoundTag nbt) {
        SkyBlockSavedData savedData = new SkyBlockSavedData();
        savedData.generated = nbt.getBoolean(GENERATED_KEY);
        return savedData;
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        nbt.putBoolean(GENERATED_KEY, true);
        return nbt;
    }

    public void setGenerated() {
        generated = true;
        this.setDirty();
    }
}