package com.ant.mcskyblock.common.world.level.levelgen.islandtype;

import net.minecraft.world.level.block.Block;

/**
 * [COMMON] WORLD GENERATION - This is a wrapper class for islands, that consist of a base block, a fluid and a
 * central accessory
 */
public class FluidAccessoryIslandType extends FluidIslandType {
    private final Block accessory;

    public FluidAccessoryIslandType(Block base, Block fluid, Block accessory) {
        super(base, fluid);
        this.accessory = accessory;
    }

    @Override
    public Block getAccessory() {
        return accessory;
    }
}
