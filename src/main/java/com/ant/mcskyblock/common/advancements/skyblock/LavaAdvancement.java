package com.ant.mcskyblock.common.advancements.skyblock;

import com.ant.mcskyblock.common.advancements.IAdvancement;

public class LavaAdvancement implements IAdvancement {
    @Override
    public String getJSON() {
        return """
                {
                    "display":
                    {
                        "icon": {"item": "minecraft:lava_bucket"},
                        "frame": "task",
                        "title": {"translate": "Ahhh, that's hot"},
                        "description": {"translate": "Seriously though, how is the bucket not melting..."}
                    },
                    "parent": "mcskyblock:villager",
                    "criteria":
                    {
                        "lava":
                        {
                            "trigger": "minecraft:inventory_changed",
                            "conditions": {"items":[{"items": ["minecraft:lava_bucket"]}]}
                        }
                    }
                }
                """;
    }

    @Override
    public String getName() {
        return "lava";
    }
}
