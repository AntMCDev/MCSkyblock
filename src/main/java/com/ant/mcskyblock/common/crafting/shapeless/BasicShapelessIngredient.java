package com.ant.mcskyblock.common.crafting.shapeless;

public class BasicShapelessIngredient implements IShapelessIngredient {
    private final String block;
    private final int count;

    public BasicShapelessIngredient(String block, int count) {
        this.block = block;
        this.count = count;
    }

    @Override
    public String getBlock() {
        return block;
    }

    @Override
    public int getCount() {
        return count;
    }
}
