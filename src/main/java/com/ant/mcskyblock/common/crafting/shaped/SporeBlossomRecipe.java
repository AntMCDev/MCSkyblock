package com.ant.mcskyblock.common.crafting.shaped;

import com.ant.mcskyblock.common.config.Config;

import java.util.Collection;
import java.util.List;

/**
 * [COMMON] CRAFTING - Spore Blossom, crafted by: 1 flowering azalea surrounded by 4 moss blocks
 */
public class SporeBlossomRecipe implements IShapedRecipe {
    @Override
    public boolean enabled() {
        return Config.INSTANCE.crafting.SPORE_BLOSSOM;
    }

    @Override
    public String getBlock() {
        return "minecraft:spore_blossom";
    }

    @Override
    public Collection<IShapedIngredient> getIngredients() {
        return List.of(
                new BasicShapedIngredient("minecraft:moss_block", "M"),
                new BasicShapedIngredient("minecraft:flowering_azalea", "F")
        );
    }

    @Override
    public String[] getPattern() {
        return new String[] {
                " M ",
                "MFM",
                " M "
        };
    }
}
