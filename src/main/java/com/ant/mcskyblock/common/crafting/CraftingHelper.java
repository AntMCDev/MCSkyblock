package com.ant.mcskyblock.common.crafting;

import com.ant.mcskyblock.common.crafting.shaped.IShapedRecipe;
import com.ant.mcskyblock.common.crafting.shapeless.IShapelessRecipe;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * [COMMON] HELPER CLASS - convert crafting recipes to JsonObject instances
 */
public abstract class CraftingHelper {
    public static JsonObject toJSON(IShapedRecipe recipe) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "minecraft:crafting_shaped");

        JsonArray pattern = new JsonArray();
        for (String s : recipe.getPattern()) {
            pattern.add(s);
        }
        json.add("pattern", pattern);

        JsonObject key = new JsonObject();
        recipe.getIngredients().forEach(i -> {
            JsonObject ingredient = new JsonObject();
            ingredient.addProperty(i.isTag() ? "tag" : "item", i.getBlock());
            key.add(i.getKey(), ingredient);
        });
        json.add("key", key);

        JsonObject result = new JsonObject();
        result.addProperty("item", recipe.getBlock());
        if( recipe.getCount() > 1 ){
            result.addProperty("count", recipe.getCount());
        }
        json.add("result", result);

        return json;
    }

    public static JsonObject toJSON(IShapelessRecipe recipe) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "minecraft:crafting_shapeless");

        JsonArray ingredients = new JsonArray();
        recipe.getIngredients().forEach(i -> {
            for (int idx = 0, c = i.getCount(); idx < c; idx++) {
                JsonObject ingredient = new JsonObject();
                ingredient.addProperty(i.isTag() ? "tag" : "item", i.getBlock());
                ingredients.add(ingredient);
            }
        });
        json.add("ingredients", ingredients);

        JsonObject result = new JsonObject();
        result.addProperty("item", recipe.getBlock());
        if( recipe.getCount() > 1 ){
            result.addProperty("count", recipe.getCount());
        }
        json.add("result", result);

        return json;
    }
}
