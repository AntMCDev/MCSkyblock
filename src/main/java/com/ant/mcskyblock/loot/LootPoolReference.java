package com.ant.mcskyblock.loot;

import net.minecraft.loot.entry.LootPoolEntry;

import java.util.List;

public class LootPoolReference {
    private int poolIndex;
    private List<LootPoolEntry> entries;

    public LootPoolReference(int poolIndex, List<LootPoolEntry> entries) {
        this.poolIndex = poolIndex;
        this.entries = entries;
    }

    public int getPoolIndex() {
        return poolIndex;
    }

    public void setPoolIndex(int poolName) {
        this.poolIndex = poolName;
    }

    public List<LootPoolEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<LootPoolEntry> entries) {
        this.entries = entries;
    }
}
