package com.ant.mcskyblock.common.world.level.levelgen.islandtype;

import net.minecraft.world.level.block.Block;

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
