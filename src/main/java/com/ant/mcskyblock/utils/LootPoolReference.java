package com.ant.mcskyblock.utils;

import net.minecraft.loot.LootEntry;

public class LootPoolReference {
    private String poolName;
    private LootEntry[] entries;

    public LootPoolReference(String poolName, LootEntry[] entries) {
        this.poolName = poolName;
        this.entries = entries;
    }

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public LootEntry[] getEntries() {
        return entries;
    }

    public void setEntries(LootEntry[] entries) {
        this.entries = entries;
    }
}
