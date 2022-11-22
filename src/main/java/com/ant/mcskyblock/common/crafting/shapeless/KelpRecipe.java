package com.ant.mcskyblock.common.crafting.shapeless;

import com.ant.mcskyblock.common.config.SkyBlockConfig;

import java.util.Collection;
import java.util.Collections;

public class KelpRecipe implements IShapelessRecipe{
    @Override
    public boolean enabled() { return SkyBlockConfig.CRAFTING.KELP;}

    @Override
    public String getBlock() {return "minecraft:kelp";}

    @Override
    public Collection<IShapelessIngredient> getIngredients() {return Collections.singletonList(new BasicShapelessIngredient("minecraft:sea_grass", 4));}
}
