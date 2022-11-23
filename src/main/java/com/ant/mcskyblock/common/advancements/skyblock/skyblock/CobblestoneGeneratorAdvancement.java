package com.ant.mcskyblock.common.advancements.skyblock.skyblock;

import com.ant.mcskyblock.common.advancements.IAdvancement;

public class CobblestoneGeneratorAdvancement implements IAdvancement {
    @Override
    public String getJSON() {
        return """
                    {
                    "display":
                    {
                        "icon": {"item": "minecraft:cobblestone"},
                        "title": {"translate": "Cobblestone Generator"},
                        "description": {"translate": "Make a cobblestone generator with water and lava."}},
                        "parent": "mcskyblock:skyblock",
                        "criteria":
                        {
                            "cobblestone":
                            {
                                "trigger": "minecraft:inventory_changed",
                                "conditions" : {"items": [{"items": ["minecraft:cobblestone"]}]}
                            }
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
