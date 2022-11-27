package com.ant.mcskyblock.common.crafting.shaped;

import com.ant.mcskyblock.common.config.Config;

import java.util.Collection;
import java.util.List;

/**
 * [COMMON] CRAFTING - Cobweb, crafted by: 5 honeycomb blocks, 4 string
 */
public class CobwebRecipe implements IShapedRecipe {
    @Override
    public boolean enabled() {return Config.INSTANCE.crafting.COBWEB;}

    @Override
    public String getBlock() {return "minecraft:cobweb";}
    @Override
    public Collection<IShapedIngredient> getIngredients() {
        return List.of(
                new BasicShapedIngredient("minecraft:string", "S"),
                new BasicShapedIngredient("minecraft:honeycomb_block", "H")
        );
    }
    @Override
    public String[] getPattern() {
        return new String[]{
                "HSH",
                "SHS",
                "HSH"
        };
    }
}
