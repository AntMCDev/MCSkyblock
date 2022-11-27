package com.ant.mcskyblock.common.crafting.shaped.ores.deepslate;

import com.ant.mcskyblock.common.config.Config;
import com.ant.mcskyblock.common.crafting.shaped.BasicShapedIngredient;
import com.ant.mcskyblock.common.crafting.shaped.IShapedIngredient;
import com.ant.mcskyblock.common.crafting.shaped.IShapedRecipe;

import java.util.Collection;
import java.util.List;

public class CopperOreRecipe implements IShapedRecipe {
    @Override
    public boolean enabled() {
        return Config.INSTANCE.crafting.CRAFTABLE_ORES;
    }

    @Override
    public String getBlock() {
        return "minecraft:deepslate_copper_ore";
    }

    @Override
    public Collection<IShapedIngredient> getIngredients() {
        return List.of(
                new BasicShapedIngredient("minecraft:deepslate", "X"),
                new BasicShapedIngredient("minecraft:copper_block", "B")
        );
    }

    @Override
    public String[] getPattern() {
        return new String[] {
                "XXX",
                "XBX",
                "XXX"
        };
    }
}
