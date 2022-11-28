package com.ant.mcskyblock.common.world.level.levelgen.islandtype;

import net.minecraft.world.level.block.Block;

/**
 * [COMMON] WORLD GENERATION - This is a wrapper class for islands, that consist of just a base block
 */
public class SimpleIslandType implements IslandType {
    private final Block base;

    public SimpleIslandType(Block base) {
        this.base = base;
    }

    @Override
    public Block getBase() {
        return base;
    }
}
