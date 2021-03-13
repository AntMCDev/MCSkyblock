package com.ant.mcskyblock.world;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Util;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.UUID;

// Code adapted from Vaskii's Botania GoG source
// https://github.com/Vazkii/Botania
public class SkyblockSavedData extends WorldSavedData {
    private static final String NAME = "skyblock_islands";

    public boolean generated;

    public SkyblockSavedData() {
        super(NAME);
    }

    public static SkyblockSavedData get(ServerWorld world) {
        return world.getSavedData().getOrCreate(SkyblockSavedData::new, NAME);
    }

    public IslandPosition getSpawn() {
        IslandPosition pos = new IslandPosition(0, 0);
        generated = true;
        markDirty();
        return pos;
    }

    @Override
    public void read(CompoundNBT nbt) {
        generated = nbt.getBoolean("generated");
    }

    @Nonnull
    @Override
    public CompoundNBT write(@Nonnull CompoundNBT nbt) {
        nbt.putBoolean("generated", generated);
        return nbt;
    }
}
