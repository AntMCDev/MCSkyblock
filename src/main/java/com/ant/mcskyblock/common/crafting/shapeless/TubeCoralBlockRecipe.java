package com.ant.mcskyblock.common.crafting.shapeless;

import com.ant.mcskyblock.common.config.SkyBlockConfig;
import java.util.Collection;
import java.util.Collections;

public class TubeCoralBlockRecipe implements IShapelessRecipe{
    @Override
    public boolean enabled() {return SkyBlockConfig.CRAFTING.CORAL_BLOCKS;}
    @Override
    public String getBlock() {
        return "minecraft:tube_coral_block";
    }
    @Override
    public Collection<IShapelessIngredient> getIngredients() {return Collections.singletonList(new BasicShapelessIngredient("minecraft:tube_coral", 4));}

}
