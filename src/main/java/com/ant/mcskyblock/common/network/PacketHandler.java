package com.ant.mcskyblock.common.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

/**
 * [COMMON] HELPER CLASS - This class is used to hold platform-independent packet types, make the initialization calls
 * and provides abstracted methods that should be overridden for platform-specific functionality
 */
public abstract class PacketHandler {
    public static PacketHandler INSTANCE;

    public static IPacket WORLD_TYPE_PACKET;
    public static IPacket CONFIG_PACKET;
    public static IPacket STRUCTURE_CHECK_PACKET;

    public abstract PacketHandler init();

    public PacketHandler registerClientListener() {
        WORLD_TYPE_PACKET.registerOnClient();
        CONFIG_PACKET.registerOnClient();
        STRUCTURE_CHECK_PACKET.registerOnClient();
        return this;
    }

    public PacketHandler registerServerListener() {
        WORLD_TYPE_PACKET.registerOnServer();
        CONFIG_PACKET.registerOnServer();
        STRUCTURE_CHECK_PACKET.registerOnServer();
        return this;
    }

    abstract public void sendTo(ServerPlayer player, ResourceLocation identifier, byte[] data);
    abstract public void sendToServer(ResourceLocation identifier, byte[] data);
}
