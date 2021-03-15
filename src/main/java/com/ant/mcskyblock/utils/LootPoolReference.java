package com.ant.mcskyblock.utils;

import net.minecraft.loot.LootEntry;

import java.util.List;

public class LootPoolReference {
    private String poolName;
    private List<LootEntry> entries;

    public LootPoolReference(String poolName, List<LootEntry> entries) {
        this.poolName = poolName;
        this.entries = entries;
    }

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public List<LootEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<LootEntry> entries) {
        this.entries = entries;
    }
}
