package com.ant.mcskyblock.common.crafting.shapeless;

import com.ant.mcskyblock.common.config.Config;
import java.util.Collection;
import java.util.Collections;

/**
 * [COMMON] CRAFTING - Tube Coral Block, crafted by: 4 tube coral
 */
public class TubeCoralBlockRecipe implements IShapelessRecipe{
    @Override
    public boolean enabled() {return Config.INSTANCE.crafting.CORAL_BLOCKS;}
    @Override
    public String getBlock() {
        return "minecraft:tube_coral_block";
    }
    @Override
    public Collection<IShapelessIngredient> getIngredients() {return Collections.singletonList(new BasicShapelessIngredient("minecraft:tube_coral", 4));}

}
