package com.ant.mcskyblock.common.advancements.skyblock;

import com.ant.mcskyblock.common.advancements.IAdvancement;

public class WitherAdvancement implements IAdvancement {
    @Override
    public String getJSON() {
        return """
                {
                    "display":
                    {
                        "icon": {"item": "minecraft:nether_star"},
                        "frame": "goal",
                        "title": {"translate": "Wither me timbers"},
                        "description": {"translate": "Hope you had insurance"}
                    },
                    "parent": "mcskyblock:nether",
                    "criteria":
                    {
                        "wither":
                        {
                            "trigger": "minecraft:player_killed_entity",
                            "conditions": {"entity":{"type":"minecraft:wither"}}
                        }
                    }
                }
                """;
    }

    @Override
    public String getName() {
        return "wither";
    }
}
