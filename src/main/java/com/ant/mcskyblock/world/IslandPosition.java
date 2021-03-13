package com.ant.mcskyblock.world;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;

// Code adapted from Vaskii's Botania GoG source
// https://github.com/Vazkii/Botania
public final class IslandPosition {
    private final int x;
    private final int z;

    public IslandPosition(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public BlockPos getCenter() {
        return new BlockPos(x << 8, 64, z << 8);
    }

    public static IslandPosition fromTag(CompoundNBT tag) {
        return new IslandPosition(tag.getInt("IslandX"), tag.getInt("IslandZ"));
    }

    public CompoundNBT toTag() {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt("IslandX", x);
        tag.putInt("IslandZ", z);
        return tag;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof IslandPosition)) {
            return false;
        }
        IslandPosition islandPosition = (IslandPosition)obj;
        return this.x == islandPosition.x && this.z == islandPosition.z;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + z;
        return result;
    }
}
