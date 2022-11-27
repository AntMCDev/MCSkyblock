package com.ant.mcskyblock.common.world.level.levelgen.islandtype;

import net.minecraft.world.level.block.Block;

/**
 * [COMMON] WORLD GENERATION - This is a wrapper class for islands, that consist of a base block and a central accessory
 */
public class AccessoryIslandType extends SimpleIslandType {
    private final Block accessory;

    public AccessoryIslandType(Block base, Block accessory) {
        super(base);
        this.accessory = accessory;
    }

    @Override
    public Block getAccessory() {
        return accessory;
    }
}
