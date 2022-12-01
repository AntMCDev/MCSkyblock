package com.ant.mcskyblock.common.crafting.shaped;

/**
 * [COMMON] INTERFACE - Shaped ingredient interface, while this can be used directly - it's better to use the
 * BasicShapedIngredient utility class.
 *
 * @see BasicShapedIngredient
 */
public interface IShapedIngredient {
    String getBlock();
    String getKey();
    boolean isTag();
}
