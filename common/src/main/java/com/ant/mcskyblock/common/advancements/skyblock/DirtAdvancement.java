package com.ant.mcskyblock.common.advancements.skyblock;

import com.ant.mcskyblock.common.advancements.IAdvancement;

/**
 * [COMMON] ACHIEVEMENT - Dig up a dirt block
 */
public class DirtAdvancement implements IAdvancement {
    @Override
    public String getJSON() {
        return """
                {
                    "display":
                    {
                        "icon":{"item": "minecraft:grass_block"},
                        "frame": "task",
                        "title": {"translate": "Relocation"},
                        "description": {"translate": "Not a fan of this dirt block placement!"},
                        "background": "minecraft:textures/block/snow.png",
                        "show_toast": true,
                        "announce_to_chat": false
                    },
                    "parent": "mcskyblock:skyblock",
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
    public String getName() { return "relocation"; }
}
