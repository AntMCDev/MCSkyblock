package com.ant.mcskyblock.common.crafting.shapeless;

import com.ant.mcskyblock.common.config.Config;

import java.util.Collection;
import java.util.Collections;

/**
 * [COMMON] CRAFTING - Brain Coral Block, crafted by: 4 brain coral
 */
public class BrainCoralBlockRecipe implements IShapelessRecipe {
    @Override
    public boolean enabled() {return Config.INSTANCE.crafting.CORAL_BLOCKS;}

    @Override
    public String getBlock() {
        return "minecraft:brain_coral_block";
    }

    @Override
    public Collection<IShapelessIngredient> getIngredients() {
        return Collections.singletonList(new BasicShapelessIngredient("minecraft:brain_coral", 4));
    }
}
