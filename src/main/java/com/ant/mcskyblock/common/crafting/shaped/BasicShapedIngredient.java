package com.ant.mcskyblock.common.crafting.shaped;

public class BasicShapedIngredient implements IShapedIngredient {
    private final String block;
    private final String key;

    public BasicShapedIngredient(String block, String key) {
        this.block = block;
        this.key = key;
    }

    @Override
    public String getBlock() {
        return block;
    }

    @Override
    public String getKey() {
        return key;
    }
}
