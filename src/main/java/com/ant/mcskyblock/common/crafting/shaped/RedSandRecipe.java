package com.ant.mcskyblock.common.crafting.shaped;

import com.ant.mcskyblock.common.config.SkyBlockConfig;
import java.util.Collection;
import java.util.List;

public class RedSandRecipe implements  IShapedRecipe{
    @Override
    public boolean enabled() { return SkyBlockConfig.CRAFTING.RED_SAND;}

    @Override
    public String getBlock() { return "minecraft:red_sand";}

    @Override
    public Collection<IShapedIngredient> getIngredients() {
        return List.of(
                new BasicShapedIngredient("minecraft:sand" , "S") ,
                new BasicShapedIngredient("minecraft:red_dye", "R")
        );
    }

    @Override
    public String[] getPattern() {
        return new String[]{
                "SSS",
                "SRS",
                "SSS"
        };
    }
    @Override
    public int getCount(){ return 8;}
}
