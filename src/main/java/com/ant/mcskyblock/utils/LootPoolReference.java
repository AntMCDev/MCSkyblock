package com.ant.mcskyblock.utils;

import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;

import java.util.List;

public class LootPoolReference {
    private String poolName;
    private List<LootPoolEntryContainer> entries;

    public LootPoolReference(String poolName, List<LootPoolEntryContainer> entries) {
        this.poolName = poolName;
        this.entries = entries;
    }

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public List<LootPoolEntryContainer> getEntries() {
        return entries;
    }

    public void setEntries(List<LootPoolEntryContainer> entries) {
        this.entries = entries;
    }
}
