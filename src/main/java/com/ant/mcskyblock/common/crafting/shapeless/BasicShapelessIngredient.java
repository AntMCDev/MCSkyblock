package com.ant.mcskyblock.common.crafting.shapeless;

/**
 * [COMMON] HELPER CLASS - This is a wrapper around IShapelessIngredient to make it simpler to assign
 */
public class BasicShapelessIngredient implements IShapelessIngredient {
    private final String block;
    private final int count;
    private final boolean tag;

    public BasicShapelessIngredient(String block, int count) {
        this(block, count, false);
    }

    public BasicShapelessIngredient(String block, int count, boolean tag) {
        this.block = block;
        this.count = count;
        this.tag = tag;
    }

    @Override
    public String getBlock() {
        return block;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public boolean isTag() { return tag; }
}
