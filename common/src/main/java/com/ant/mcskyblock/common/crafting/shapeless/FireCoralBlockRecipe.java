package com.ant.mcskyblock.common.crafting.shapeless;

import com.ant.mcskyblock.common.config.Config;
import java.util.Collection;
import java.util.Collections;

/**
 * [COMMON] CRAFTING - Fire Coral Block, crafted by: 4 fire coral
 */
public class FireCoralBlockRecipe implements IShapelessRecipe {
    @Override
    public boolean enabled() {return Config.INSTANCE.crafting.CORAL_BLOCKS;}
    @Override
    public String getBlock() {
        return "minecraft:fire_coral_block";
    }
    @Override
    public Collection<IShapelessIngredient> getIngredients() {return Collections.singletonList(new BasicShapelessIngredient("minecraft:fire_coral", 4));}
}
