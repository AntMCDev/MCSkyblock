package com.ant.mcskyblock.common.advancements.skyblock;

import com.ant.mcskyblock.common.advancements.IAdvancement;

public class WardenAdvancement implements IAdvancement {
    @Override
    public String getJSON() {
        return """
                {
                    "display":
                    {
                        "icon": {"item": "minecraft:sculk_catalyst"},
                        "frame": "goal",
                        "title": {"translate": "Warden it be a shame"},
                        "description": {"translate": "So, how many times did you die?"}
                    },
                    "parent": "mcskyblock:shrieker",
                    "criteria":
                    {
                        "warden":
                        {
                            "trigger": "minecraft:player_killed_entity",
                            "conditions": {"entity":{"type":"minecraft:warden"}}
                        }
                    }
                }
                """;
    }

    @Override
    public String getName() {
        return "warden";
    }
}
