package com.ant.mcskyblock.common.crafting.shapeless;

import com.ant.mcskyblock.common.config.Config;

import java.util.Collection;
import java.util.Collections;

/**
 * [COMMON] CRAFTING - Kelp, crafted by: 4 sea grass
 */
public class KelpRecipe implements IShapelessRecipe{
    @Override
    public boolean enabled() { return Config.INSTANCE.crafting.KELP;}

    @Override
    public String getBlock() {return "minecraft:kelp";}

    @Override
    public Collection<IShapelessIngredient> getIngredients() {return Collections.singletonList(new BasicShapelessIngredient("minecraft:sea_grass", 4));}
}
