package com.ant.mcskyblock.skyblock;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;

public class SkyblockSavedData extends PersistentState {
    private static final String NAME = "skyblock_islands";
    private static final String GENERATED_KEY = "generated";

    public boolean generated;

    public SkyblockSavedData() {
        generated = false;
    }

    public static SkyblockSavedData get(ServerWorld world) {
        return world.getServer().getOverworld().getPersistentStateManager().getOrCreate(SkyblockSavedData::readNbt, SkyblockSavedData::new, NAME);
    }

    private static SkyblockSavedData readNbt(NbtCompound nbt) {
        SkyblockSavedData skyblockSavedData = new SkyblockSavedData();
        skyblockSavedData.generated = nbt.getBoolean(GENERATED_KEY);
        return skyblockSavedData;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putBoolean(GENERATED_KEY, true);
        return nbt;
    }

    public void setGenerated() {
        generated = true;
        this.markDirty();
    }
}
