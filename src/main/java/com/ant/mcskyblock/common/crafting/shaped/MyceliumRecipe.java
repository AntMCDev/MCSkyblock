package com.ant.mcskyblock.common.crafting.shaped;

import com.ant.mcskyblock.common.config.Config;

import java.util.Collection;
import java.util.List;

/**
 * [COMMON] CRAFTING - Mycelium, crafted by: 4 red mushrooms, 4 brown mushrooms, 1 dirt
 */
public class MyceliumRecipe implements IShapedRecipe{
    @Override
    public boolean enabled() {return Config.INSTANCE.crafting.MYCELIUM;}

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
