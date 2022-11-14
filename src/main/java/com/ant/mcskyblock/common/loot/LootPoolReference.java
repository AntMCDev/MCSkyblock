package com.ant.mcskyblock.common.loot;

import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;

import java.util.List;

public class LootPoolReference {
    private int poolIndex;
    private List<LootPoolEntryContainer> entries;

    /**
     *
     * @param poolIndex
     * @param entries
     */
    public LootPoolReference(int poolIndex, List<LootPoolEntryContainer> entries) {
        this.poolIndex = poolIndex;
        this.entries = entries;
    }

    /**
     *
     * @return
     */
    public int getPoolIndex() {
        return poolIndex;
    }

    /**
     *
     * @param poolName
     */
    public void setPoolIndex(int poolName) {
        this.poolIndex = poolName;
    }

    /**
     *
     * @return
     */
    public List<LootPoolEntryContainer> getEntries() {
        return entries;
    }

    /**
     *
     * @param entries
     */
    public void setEntries(List<LootPoolEntryContainer> entries) {
        this.entries = entries;
    }
}
