package com.ant.mcskyblock.common.advancements.skyblock;

import com.ant.mcskyblock.common.advancements.IAdvancement;

/**
 * [COMMON] ACHIEVEMENT - Make a cobblestone generator (obtain cobblestone)
 */
public class CobblestoneGeneratorAdvancement implements IAdvancement {
    @Override
    public String getJSON() {
        return """
                {
                    "display":
                    {
                        "icon": {"item": "minecraft:cobblestone"},
                        "frame": "task",
                        "title": {"translate": "Something I cobbled together"},
                        "description": {"translate": "Make a cobblestone generator"}
                    },
                    "parent": "mcskyblock:lava",
                    "criteria":
                    {
                        "cobblestone":
                        {
                            "trigger": "minecraft:inventory_changed",
                            "conditions" : {"items": [{"items": ["minecraft:cobblestone"]}]}
                        }
                    }
                }
                """;
    }

    @Override
    public String getName() {
        return "cobblestone_generator";
    }
}
