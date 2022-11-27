package com.ant.mcskyblock.common.network;

import net.minecraft.resources.ResourceLocation;

/**
 * [COMMON] INTERFACE - This is the basic network packet interface that provides a basic structure to access
 * platform-independent packet data.
 */
public interface IPacket {
    ResourceLocation getIdentifier();

    void registerOnClient();
    void registerOnServer();
}
