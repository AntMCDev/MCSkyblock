package com.ant.mcskyblock.common.advancements;

public class Iron implements IAdvancement {
    @Override
    public boolean enabled() {
        return true;
    }

    @Override
    public String getJSON() {
        return """
                {
                    "display":
                    {
                        "icon": {"item": "minecraft:iron_ingot"},
                        "title": {"translate": "Iron Golem Farm"},
                        "description": {"translate": "Get a stack of iron by making an iron golem farm."},
                        "frame": "goal"
                    },
                    "criteria":
                    {
                        "iron":
                        {
                            "trigger": "minecraft:inventory_changed",
                            "conditions" : {"items": [{"items": ["minecraft:iron_ingot"],"count": 64}]}
                        }
                    }
                }
                """;
    }

    @Override
    public String getName() {
        return "Iron Golem Farm";
    }
}
