package com.ant.mcskyblock.common.crafting.shaped.grassblock;

import com.ant.mcskyblock.common.SkyBlock;
import com.ant.mcskyblock.common.config.SkyBlockConfig;
import com.ant.mcskyblock.common.crafting.shaped.BasicShapedIngredient;
import com.ant.mcskyblock.common.crafting.shaped.IShapedIngredient;
import com.ant.mcskyblock.common.crafting.shaped.IShapedRecipe;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.List;

public class GrassBlockRecipe implements IShapedRecipe {
    @Override
    public boolean enabled() {return SkyBlockConfig.CRAFTING.GRASS_BLOCK;}
    @Override
    public String getBlock() {return "minecraft:grass_block";}
    @Override
    public Collection<IShapedIngredient> getIngredients() {
        return List.of(
                new BasicShapedIngredient("minecraft:oak_leaves", "L"),
                new BasicShapedIngredient("minecraft:dirt", "D")
        );
    }
    @Override
    public String[] getPattern() {
        return new String[]{
                "LLL",
                "LDL",
                "LLL"
        };
    }

    @Override
    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(SkyBlock.MOD_NAME, "oak_grass_block");
    }

}
