package com.ant.mcskyblock.common.crafting.shapeless;

import com.ant.mcskyblock.common.config.SkyBlockConfig;

import java.util.Collection;
import java.util.Collections;

public class BrainCoralBlockRecipe implements IShapelessRecipe {
    @Override
    public boolean enabled() {return SkyBlockConfig.CRAFTING.BRAIN_CORAL_BLOCK;}

    @Override
    public String getBlock() {
        return "minecraft:brain_coral_block";
    }

    @Override
    public Collection<IShapelessIngredient> getIngredients() {
        return Collections.singletonList(new BasicShapelessIngredient("minecraft:brain_coral", 4));
    }
}
