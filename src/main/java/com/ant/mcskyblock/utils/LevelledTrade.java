package com.ant.mcskyblock.utils;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.BasicTrade;

public class LevelledTrade extends BasicTrade {
    protected int level;

    public LevelledTrade(int level, int emeralds, ItemStack forSale, int maxTrades, int xp, float multi) {
        super(emeralds, forSale, maxTrades, xp, multi);
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
