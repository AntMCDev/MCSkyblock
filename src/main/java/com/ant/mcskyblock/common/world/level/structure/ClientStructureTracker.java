package com.ant.mcskyblock.common.world.level.structure;

/**
 * [COMMON] HELPER CLASS - This is used to hold the state of whether a client is currently within the bounds of a
 * structure
 */
public class ClientStructureTracker {
    public enum SupportedStructures {
        NONE, FORTRESS, BASTION, MONUMENT, SWAMP_HUT, PILLAGER_OUTPOST
    }

    public static SupportedStructures structure = SupportedStructures.NONE;
}