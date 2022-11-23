package com.ant.mcskyblock.common.crafting.shaped;

import com.ant.mcskyblock.common.config.SkyBlockConfig;

import java.util.Collection;
import java.util.List;

public class CobWebRecipe implements IShapedRecipe {
    @Override
    public boolean enabled() {return SkyBlockConfig.CRAFTING.COBWEB;}

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
