package com.ant.mcskyblock.common.crafting.shapeless;

import com.ant.mcskyblock.common.SkyBlock;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;

/**
 * [COMMON] INTERFACE - Shapeless recipe interface, all shapeless recipes should implement this class
 */
public interface IShapelessRecipe {
    boolean enabled();
    String getBlock();
    Collection<IShapelessIngredient> getIngredients();
    default int getCount(){return 1;}
    default ResourceLocation getResourceLocation() {
        return new ResourceLocation(SkyBlock.MOD_NAME, getBlock().substring(getBlock().indexOf(":")+1));
    }
}
