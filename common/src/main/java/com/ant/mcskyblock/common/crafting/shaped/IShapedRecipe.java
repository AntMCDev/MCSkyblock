package com.ant.mcskyblock.common.crafting.shaped;

import com.ant.mcskyblock.common.SkyBlock;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;

/**
 * [COMMON] INTERFACE - Shaped recipe interface, all shaped recipes should implement this class
 */
public interface IShapedRecipe {
    boolean enabled();
    String getBlock();
    Collection<IShapedIngredient> getIngredients();
    String[] getPattern();

    default int getCount(){ return 1;};

    default ResourceLocation getResourceLocation() {
        return new ResourceLocation(SkyBlock.MOD_NAME, getBlock().substring(getBlock().indexOf(":")+1));
    }
}
