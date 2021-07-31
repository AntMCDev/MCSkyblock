package com.ant.mcskyblock.world;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

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

    public static IslandPosition fromTag(CompoundTag tag) {
        return new IslandPosition(tag.getInt("IslandX"), tag.getInt("IslandZ"));
    }

    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
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
