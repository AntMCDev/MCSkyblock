package com.ant.mcskyblock.common.crafting.shapeless;

/**
 * [COMMON] INTERFACE - Shapeless ingredient interface, while this can be used directly - it's better to use the
 * BasicShapelessIngredient utility class.
 *
 * @see BasicShapelessIngredient
 */
public interface IShapelessIngredient {
    String getBlock();
    int getCount();
    boolean isTag();
}
