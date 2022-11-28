package com.ant.mcskyblock.common.crafting.shaped;

import com.ant.mcskyblock.common.config.Config;

import java.util.Collection;
import java.util.List;

/**
 * [COMMON] CRAFTING - Heart of the Sea, crafted by: 4 sea lanterns, 4 dark prismarine, 1 nether start
 */
public class HeartOfTheSeaRecipe implements IShapedRecipe{
    @Override
    public boolean enabled() {return Config.INSTANCE.crafting.HEART_OF_THE_SEA;}
    @Override
    public String getBlock() {return "minecraft:heart_of_the_sea";}
    @Override
    public Collection<IShapedIngredient> getIngredients() {
       return List.of(
                new BasicShapedIngredient("minecraft:sea_lantern", "L"),
                new BasicShapedIngredient("minecraft:dark_prismarine", "P"),
                new BasicShapedIngredient("minecraft:nether_star", "S")
        );
    }
    @Override
    public String[] getPattern() {
        return new String[]{
            "LPL",
            "PSP",
            "LPL"
        };
    }
}