package com.ant.mcskyblock.common.crafting.shapeless;

import com.ant.mcskyblock.common.config.Config;

import java.util.Collection;
import java.util.Collections;

/**
 * [COMMON] CRAFTING - Netherrack, crafted by: 4 weeping vines
 */
public class NetherrackRecipe implements IShapelessRecipe {
    public boolean enabled() { return Config.INSTANCE.crafting.NETHERRACK;}

    @Override
    public String getBlock() { return "minecraft:netherrack"; }

    @Override
    public Collection<IShapelessIngredient> getIngredients() {return Collections.singletonList(new BasicShapelessIngredient("minecraft:weeping_vines", 4));}
}
