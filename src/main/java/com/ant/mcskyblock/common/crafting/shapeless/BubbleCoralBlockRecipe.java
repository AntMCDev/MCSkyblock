package com.ant.mcskyblock.common.crafting.shapeless;

import com.ant.mcskyblock.common.config.SkyBlockConfig;
import java.util.Collection;
import java.util.Collections;

public class BubbleCoralBlockRecipe implements IShapelessRecipe {
    @Override
    public boolean enabled() {return SkyBlockConfig.CRAFTING.CORAL_BLOCKS;}
    @Override
    public String getBlock() {
        return "minecraft:bubble_coral_block";
    }
    @Override
    public Collection<IShapelessIngredient> getIngredients() {return Collections.singletonList(new BasicShapelessIngredient("minecraft:bubble_coral", 4));}
}
