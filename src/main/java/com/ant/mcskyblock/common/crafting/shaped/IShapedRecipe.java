package com.ant.mcskyblock.common.crafting.shaped;

import com.ant.mcskyblock.common.SkyBlock;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;

public interface IShapedRecipe {
    boolean enabled();
    String getBlock();
    Collection<IShapedIngredient> getIngredients();
    String[] getPattern();

    default ResourceLocation getResourceLocation() {
        return new ResourceLocation(SkyBlock.MOD_NAME, getBlock().substring(getBlock().indexOf(":")+1));
    }
}
