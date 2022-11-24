package com.ant.mcskyblock.common.crafting.shaped;

import com.ant.mcskyblock.common.config.Config;

import java.util.Collection;
import java.util.List;

/**
 * [COMMON] CRAFTING - End Portal Frame, crafted by: 4 endstone, 2 ender eyes, 1 sculk catalyst, 1 nether start and 1 obsidian
 */
public class EndPortalFrameRecipe implements IShapedRecipe{
    @Override
    public boolean enabled() { return Config.INSTANCE.crafting.END_PORTAL_FRAME;}

    @Override
    public String getBlock() {
        return "minecraft:end_portal_frame";
    }
    @Override
    public Collection<IShapedIngredient> getIngredients() {
        return List.of(
                new BasicShapedIngredient("minecraft:obsidian" , "O" ),
                new BasicShapedIngredient("minecraft:nether_star", "N"),
                new BasicShapedIngredient("minecraft:end_stone", "E"),
                new BasicShapedIngredient("minecraft:sculk_catalyst", "S"),
                new BasicShapedIngredient("minecraft:ender_eye", "I")
        );
    }
    @Override
    public String[] getPattern() {
        return new String[]{
                "ISI",
                "ENE",
                "EOE"
        };
    }
}
