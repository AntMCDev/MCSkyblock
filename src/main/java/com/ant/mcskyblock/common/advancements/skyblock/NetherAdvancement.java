package com.ant.mcskyblock.common.advancements.skyblock;

import com.ant.mcskyblock.common.advancements.IAdvancement;

/**
 * [COMMON] ACHIEVEMENT - Make a nether portal (travel to the nether)
 */
public class NetherAdvancement implements IAdvancement {
    @Override
    public String getJSON() {
        return """
                {
                    "display":
                    {
                        "icon": {"item": "minecraft:obsidian"},
                        "frame": "task",
                        "title": {"translate": "Nethermind"},
                        "description": {"translate": "You sure you want to do that?"}
                    },
                    "parent": "mcskyblock:lava",
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
    public String getName() { return "nether"; }
}
