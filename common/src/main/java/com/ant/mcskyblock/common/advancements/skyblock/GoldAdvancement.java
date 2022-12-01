package com.ant.mcskyblock.common.advancements.skyblock;

import com.ant.mcskyblock.common.advancements.IAdvancement;

public class GoldAdvancement implements IAdvancement {
    @Override
    public String getJSON() {
        return """
                {
                    "display":
                    {
                        "icon": {"item": "minecraft:gold_ingot"},
                        "frame": "task",
                        "title": {"translate": "$$$"},
                        "description": {"translate": "Midas would be proud"}
                    },
                    "parent": "mcskyblock:water",
                    "criteria":
                    {
                        "gold":
                        {
                            "trigger": "minecraft:inventory_changed",
                            "conditions": {"items":[{"items": ["minecraft:gold_ingot"]}]}
                        }
                    }
                }
                """;
    }

    @Override
    public String getName() {
        return "gold";
    }
}
