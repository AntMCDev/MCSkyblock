package com.ant.mcskyblock.common.crafting.shaped;

import com.ant.mcskyblock.common.config.SkyBlockConfig;

import java.util.Collection;
import java.util.List;

public class HeartOfTheSeaRecipe implements IShapedRecipe{
    @Override
    public boolean enabled() {return SkyBlockConfig.CRAFTING.HEART_OF_THE_SEA;}
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