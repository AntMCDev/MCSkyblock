package com.ant.mcskyblock.common.advancements.skyblock;

import com.ant.mcskyblock.common.advancements.IAdvancement;

public class VillagerAdvancement implements IAdvancement {
    @Override
    public String getJSON() {
        return """
                {
                    "display":
                    {
                        "icon": {"item": "minecraft:golden_apple"},
                        "frame": "task",
                        "title": {"translate": "My Hero! Oh, you just want my money?"},
                        "description": {"translate": "Off to the emerald city!"}
                    },
                    "parent": "mcskyblock:gold",
                    "criteria":
                    {
                        "villager":
                        {
                            "trigger": "minecraft:cured_zombie_villager"
                        }
                    }
                }
                """;
    }

    @Override
    public String getName() {
        return "villager";
    }
}
