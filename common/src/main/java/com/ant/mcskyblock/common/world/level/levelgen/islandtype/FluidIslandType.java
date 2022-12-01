package com.ant.mcskyblock.common.world.level.levelgen.islandtype;

import net.minecraft.world.level.block.Block;

/**
 * [COMMON] WORLD GENERATION - This is a wrapper class for islands, that consist of a base block and a fluid
 */
public class FluidIslandType extends SimpleIslandType {
    private final Block fluid;

    public FluidIslandType(Block base, Block fluid) {
        super(base);
        this.fluid = fluid;
    }

    @Override
    public Block getFluid() {
        return fluid;
    }
}
