package com.ant.mcskyblock.common.loot;



import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;

import java.util.List;

// NON FABRIC
public class LootPoolReference {
    private int poolIndex;
    private List<LootPoolEntryContainer> entries;

    public LootPoolReference(int poolIndex, List<LootPoolEntryContainer> entries) {
        this.poolIndex = poolIndex;
        this.entries = entries;
    }

    public int getPoolIndex() {
        return poolIndex;
    }

    public void setPoolIndex(int poolName) {
        this.poolIndex = poolName;
    }

    public List<LootPoolEntryContainer> getEntries() {
        return entries;
    }

    public void setEntries(List<LootPoolEntryContainer> entries) {
        this.entries = entries;
    }
}
