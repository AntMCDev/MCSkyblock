package com.ant.mcskyblock.common.advancements;

public class Skyblock implements IAdvancement {
    @Override
    public boolean enabled() {
        return true;
    }

    @Override
    public String getJSON() {
        return """
                {
                    "display":
                    {
                        "icon": {"item": "minecraft:grass_block"},
                        "title": {"translate": "The Sky's the Limit"},
                        "description": {"translate": "Start a new skyblock world"},
                        "frame": "goal"
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
