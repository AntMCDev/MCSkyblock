package com.ant.mcskyblock.common.world.level.levelgen.islandtype;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

/**
 * [COMMON] WORLD GENERATION - This is the interface used to define island generation. The child classes should be
 * used where possible instead of instantiating this directly
 *
 * @see VoidIslandType
 * @see SimpleIslandType
 * @see FluidIslandType
 * @see AccessoryIslandType
 * @see FluidAccessoryIslandType
 */
public interface IslandType {
    default Block getBase() { return Blocks.AIR; }
    default Block getFluid() { return Blocks.AIR; }
    default Block getAccessory() { return Blocks.AIR; }
}
