package com.ant.mcskyblock.common.advancements.skyblock;

import com.ant.mcskyblock.common.advancements.IAdvancement;

public class ShriekerAdvancement implements IAdvancement {
    @Override
    public String getJSON() {
        return """
                {
                    "display":
                    {
                        "icon": {"item": "minecraft:sculk_shrieker"},
                        "frame": "task",
                        "title": {"translate": "Cheer up, Stop Sculking!"},
                        "description": {"translate": "Not sure that you really want to place that down..."}
                    },
                    "parent": "mcskyblock:skyblock",
                    "criteria":
                    {
                        "shrieker":
                        {
                            "trigger": "minecraft:inventory_changed",
                            "conditions": {"items":[{"items": ["mcskyblock:active_sculk_shrieker"]}]}
                        }
                    }
                }
                """;
    }

    @Override
    public String getName() {
        return "shrieker";
    }
}
