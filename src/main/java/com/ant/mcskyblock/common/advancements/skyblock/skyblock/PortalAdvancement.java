package com.ant.mcskyblock.common.advancements.skyblock.skyblock;

import com.ant.mcskyblock.common.advancements.IAdvancement;

public class PortalAdvancement implements IAdvancement {
    @Override
    public String getJSON() {
        return """
                {
                    "display":
                    {
                        "icon": {"item": "minecraft:obsidian"},
                        "title": {"translate": "The Portal"},
                        "description": {"translate": "Light a nether portal somehow"}
                    },
                    "parent": "mcskyblock:skyblock",
                    "criteria":
                    {
                        "nether":
                        {
                            "trigger": "minecraft:changed_dimension",
                            "conditions":
                            {
                                "to": "the_nether"
                            }
                        }
                    }
                }
                """;
    }

    @Override
    public String getName() {return "01portal";}
}
