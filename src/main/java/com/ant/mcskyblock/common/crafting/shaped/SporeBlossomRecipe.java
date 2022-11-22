package com.ant.mcskyblock.common.crafting.shaped;

import com.ant.mcskyblock.common.config.SkyBlockConfig;

import java.util.Collection;
import java.util.List;

public class SporeBlossomRecipe implements IShapedRecipe {
    @Override
    public boolean enabled() {
        return SkyBlockConfig.CRAFTING.SPORE_BLOSSOM;
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
