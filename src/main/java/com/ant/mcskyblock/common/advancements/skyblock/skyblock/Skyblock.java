package com.ant.mcskyblock.common.advancements.skyblock.skyblock;

import com.ant.mcskyblock.common.advancements.IAdvancement;

public class Skyblock implements IAdvancement {
    @Override
    public String getJSON() {
        return """
                {
                    "display":
                    {
                        "icon":{"item": "minecraft:grass_block"},
                        "title": {"translate": "Skyblock"},
                        "description": {"translate": "An island in the void."},
                        "background": "minecraft:textures/block/snow.png",
                        "show_toast": true,
                        "announce_to_chat": false
                    },
                    "criteria":
                    {
                        "dirt":
                        {
                            "trigger": "minecraft:inventory_changed",
                            "conditions": {"items":[{"items": ["minecraft:dirt"]}]}
                        }
                    }
                }
                """;
    }

    @Override
    public String getName() {return "skyblock/root";}
}
