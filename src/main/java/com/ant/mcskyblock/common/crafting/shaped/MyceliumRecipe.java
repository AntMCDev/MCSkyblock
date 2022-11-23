package com.ant.mcskyblock.common.crafting.shaped;

import com.ant.mcskyblock.common.config.SkyBlockConfig;

import java.util.Collection;
import java.util.List;

public class MyceliumRecipe implements IShapedRecipe{
    @Override
    public boolean enabled() {return SkyBlockConfig.CRAFTING.MYCELIUM;}

    @Override
    public String getBlock() {return "minecraft:mycelium";}

    @Override
    public Collection<IShapedIngredient> getIngredients() {
        return List.of(
                new BasicShapedIngredient( "minecraft:red_mushroom", "R"),
                new BasicShapedIngredient( "minecraft:brown_mushroom", "B"),
                new BasicShapedIngredient( "minecraft:dirt", "D")

        );
    }
    @Override
    public String[] getPattern() {
        return new String[]{
                "RBR",
                "BDB",
                "RBR"
        };
    }
}
