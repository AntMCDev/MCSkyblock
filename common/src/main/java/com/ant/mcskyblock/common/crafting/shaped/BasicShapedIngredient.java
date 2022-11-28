package com.ant.mcskyblock.common.crafting.shaped;

/**
 * [COMMON] HELPER CLASS - This is a wrapper around IShapedIngredient to make it simpler to assign
 */
public class BasicShapedIngredient implements IShapedIngredient {
    private final String block;
    private final String key;
    private final boolean tag;

    public BasicShapedIngredient(String block, String key) {
        this(block, key, false);
    }

    public BasicShapedIngredient(String block, String key, boolean tag) {
        this.block = block;
        this.key = key;
        this.tag = tag;
    }

    @Override
    public String getBlock() {
        return block;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public boolean isTag() { return tag; }
}
