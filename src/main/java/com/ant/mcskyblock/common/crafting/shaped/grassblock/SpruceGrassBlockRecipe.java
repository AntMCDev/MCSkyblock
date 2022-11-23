package com.ant.mcskyblock.common.crafting.shaped.grassblock;

import com.ant.mcskyblock.common.crafting.shaped.BasicShapedIngredient;
import com.ant.mcskyblock.common.crafting.shaped.IShapedIngredient;
import com.ant.mcskyblock.common.crafting.shaped.grassblock.GrassBlockRecipe;

import java.util.Collection;
import java.util.List;

public class SpruceGrassBlockRecipe extends GrassBlockRecipe {
    @Override
    public Collection<IShapedIngredient> getIngredients() {
        return List.of(
                new BasicShapedIngredient("minecraft:spruce_leaves", "L"),
                new BasicShapedIngredient("minecraft:dirt", "D")
        );
    }
}
