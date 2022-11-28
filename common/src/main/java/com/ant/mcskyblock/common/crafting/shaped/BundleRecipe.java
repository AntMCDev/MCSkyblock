package com.ant.mcskyblock.common.crafting.shaped;

import com.ant.mcskyblock.common.config.Config;

import java.util.Collection;
import java.util.List;

/**
 * [COMMON] CRAFTING - Bundle, crafted by: 6 rabbit hide, 2 string
 */
public class BundleRecipe implements IShapedRecipe {
    @Override
    public boolean enabled() { return Config.INSTANCE.crafting.BUNDLE; }

    @Override
    public String getBlock() {return "minecraft:bundle";}

    @Override
    public Collection<IShapedIngredient> getIngredients() {
        return List.of(
                new BasicShapedIngredient("minecraft:rabbit_hide", "R"),
                new BasicShapedIngredient("minecraft:string", "S")
        );
    }
    @Override
    public String[] getPattern() {
        return new String[]{
                "SRS",
                "R R",
                "RRR"
        };
    }
}