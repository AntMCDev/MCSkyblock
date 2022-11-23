package com.ant.mcskyblock.common.crafting.shaped.grassblock;

import com.ant.mcskyblock.common.SkyBlock;
import com.ant.mcskyblock.common.crafting.shaped.BasicShapedIngredient;
import com.ant.mcskyblock.common.crafting.shaped.IShapedIngredient;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.List;

public class DarkOakGrassBlockRecipe extends GrassBlockRecipe {
    @Override
    public Collection<IShapedIngredient> getIngredients() {
        return List.of(
                new BasicShapedIngredient("minecraft:dark_oak_leaves", "L"),
                new BasicShapedIngredient("minecraft:dirt", "D")
        );
    }
    @Override
    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(SkyBlock.MOD_NAME, "dark_oak_grass_block");
    }
}
