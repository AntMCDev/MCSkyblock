package com.ant.mcskyblock.common.advancements.skyblock;

import com.ant.mcskyblock.common.advancements.IAdvancement;

public class EnderDragonAdvancement implements IAdvancement {
    @Override
    public String getJSON() {
        return """
                {
                    "display":
                    {
                        "icon": {"item": "minecraft:dragon_head"},
                        "frame": "goal",
                        "title": {"translate": "The End!"},
                        "description": {"translate": "Congratulations!"}
                    },
                    "parent": "mcskyblock:end",
                    "criteria":
                    {
                        "enderdragon":
                        {
                            "trigger": "minecraft:player_killed_entity",
                            "conditions": {"entity":{"type":"minecraft:ender_dragon"}}
                        }
                    }
                }
                """;
    }

    @Override
    public String getName() {
        return "enderdragon";
    }
}
