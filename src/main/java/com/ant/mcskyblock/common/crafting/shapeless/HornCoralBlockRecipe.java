package com.ant.mcskyblock.common.crafting.shapeless;

import com.ant.mcskyblock.common.config.Config;
import java.util.Collection;
import java.util.Collections;

/**
 * [COMMON] CRAFTING - Horn Coral Block, crafted by: 4 horn coral
 */
public class HornCoralBlockRecipe implements IShapelessRecipe {
    @Override
    public boolean enabled() {return Config.INSTANCE.crafting.CORAL_BLOCKS;}
    @Override
    public String getBlock() {
        return "minecraft:horn_coral_block";
    }
    @Override
    public Collection<IShapelessIngredient> getIngredients() {return Collections.singletonList(new BasicShapelessIngredient("minecraft:horn_coral", 4));}
}