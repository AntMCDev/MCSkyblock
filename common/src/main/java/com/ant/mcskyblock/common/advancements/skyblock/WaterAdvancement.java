package com.ant.mcskyblock.common.advancements.skyblock;

import com.ant.mcskyblock.common.advancements.IAdvancement;

public class WaterAdvancement implements IAdvancement {
    @Override
    public String getJSON() {
        return """
                {
                    "display":
                    {
                        "icon": {"item": "minecraft:water_bucket"},
                        "frame": "task",
                        "title": {"translate": "Water nice bucket!"},
                        "description": {"translate": "Try not to drown"}
                    },
                    "parent": "mcskyblock:iron",
                    "criteria":
                    {
                        "water":
                        {
                            "trigger": "minecraft:inventory_changed",
                            "conditions": {"items":[{"items": ["minecraft:water_bucket"]}]}
                        }
                    }
                }
                """;
    }

    @Override
    public String getName() {
        return "water";
    }
}
