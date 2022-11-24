package com.ant.mcskyblock.common.advancements.skyblock;

import com.ant.mcskyblock.common.advancements.IAdvancement;

/**
 * [COMMON] ACHIEVEMENT - Make a nether portal (travel to the nether)
 */
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
