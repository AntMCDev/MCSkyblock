package com.ant.mcskyblock.common.advancements.skyblock;

import com.ant.mcskyblock.common.advancements.IAdvancement;

/**
 * [COMMON] ACHIEVEMENT - Make a nether portal (travel to the nether)
 */
public class EndAdvancement implements IAdvancement {
    @Override
    public String getJSON() {
        return """
                {
                    "display":
                    {
                        "icon": {"item": "minecraft:end_portal_frame"},
                        "frame": "task",
                        "title": {"translate": "The end of the world"},
                        "description": {"translate": "Don't look down"}
                    },
                    "parent": "mcskyblock:warden",
                    "criteria":
                    {
                        "end":
                        {
                            "trigger": "minecraft:changed_dimension",
                            "conditions":
                            {
                                "to": "the_end"
                            }
                        }
                    }
                }
                """;
    }

    @Override
    public String getName() { return "end"; }
}
