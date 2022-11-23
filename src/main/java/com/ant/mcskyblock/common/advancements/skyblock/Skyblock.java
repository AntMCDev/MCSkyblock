package com.ant.mcskyblock.common.advancements.skyblock;

import com.ant.mcskyblock.common.advancements.IAdvancement;

public class Skyblock implements IAdvancement {
    @Override
    public String getJSON() {
        return """
                {
                    "display":
                    {
                        "icon": {"item": "minecraft:grass_block"},
                        "title": {"translate": "Skyblock"},
                        "description": {"translate": "Start a new skyblock world"},
                        "background": "minecraft:textures/block/snow.png",
                        "show_toast": true,
                        "announce_to_chat": false
                    },
                    "criteria":
                    {
                        "tick":
                        {
                            "trigger": "minecraft:tick"
                        }
                    }
                }
                """;
    }

    @Override
    public String getName() {
        return "Skyblock";
    }
}
