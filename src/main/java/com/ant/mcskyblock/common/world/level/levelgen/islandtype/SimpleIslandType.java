package com.ant.mcskyblock.common.world.level.levelgen.islandtype;

import net.minecraft.world.level.block.Block;

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
