package com.ant.mcskyblock.common.crafting.shapeless;

import com.ant.mcskyblock.common.config.SkyBlockConfig;

import java.util.Collection;
import java.util.Collections;

public class NetherRackRecipe implements  IShapelessRecipe{
    public boolean enabled() { return SkyBlockConfig.CRAFTING.NETHERRACK;}

    @Override
    public String getBlock() {return "minecraft:netherrack";}

    @Override
    public Collection<IShapelessIngredient> getIngredients() {return Collections.singletonList(new BasicShapelessIngredient("minecraft:weeping_vines", 4));}
}
