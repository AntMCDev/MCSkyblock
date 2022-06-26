package com.ant.mcskyblock.structurecheck;

public class ClientStructureTracker {
    public enum SupportedStructures {
        NONE, FORTRESS, MONUMENT, SWAMP_HUT, PILLAGER_OUTPOST
    }

    public static SupportedStructures structure = SupportedStructures.NONE;
}
