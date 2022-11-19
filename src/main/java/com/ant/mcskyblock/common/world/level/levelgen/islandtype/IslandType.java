package com.ant.mcskyblock.common.world.level.levelgen.islandtype;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public interface IslandType {
    default Block getBase() { return Blocks.AIR; }
    default Block getFluid() { return Blocks.AIR; }
    default Block getAccessory() { return Blocks.AIR; }
}
