package com.ant.mcskyblock.common.advancements.skyblock;

import com.ant.mcskyblock.common.advancements.IAdvancement;

public class IronAdvancement implements IAdvancement {
    @Override
    public String getJSON() {
        return """
                {
                    "display":
                    {
                        "icon": {"item": "minecraft:iron_ingot"},
                        "frame": "task",
                        "title": {"translate": "Oh, the iron-y"},
                        "description": {"translate": "One ingot down, many more to go!"}
                    },
                    "parent": "mcskyblock:skyblock",
                    "criteria":
                    {
                        "iron":
                        {
                            "trigger": "minecraft:inventory_changed",
                            "conditions": {"items":[{"items": ["minecraft:iron_ingot"]}]}
                        }
                    }
                }
                """;
    }

    @Override
    public String getName() {
        return "iron";
    }
}
