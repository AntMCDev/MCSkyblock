package com.ant.mcskyblock.fabric.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

// REQ FABRIC

public class PacketHander {

    /**
     *
     */
    public static final IPacket WORLDTYPE_PACKET = new SkyblockPacket();
    public static final IPacket CONFIG_PACKET = new ConfigPacket();


    /**
     *
     */
    private static final IPacket[] PACKETS = new IPacket[] {
            WORLDTYPE_PACKET,
            CONFIG_PACKET
    };

    /**
     *
     */
    private PacketHander() {
    }


    /**
     *
     */
    public static void registerClientListener() {
        for (IPacket packet : PACKETS) {
            packet.registerOnClient();
        }
    }

    /**
     *
     */
    public static void registerServerListener() {
        for (IPacket packet : PACKETS) {
            packet.registerOnServer();
        }
    }

    /**
     *
     * @param player
     * @param identifier
     * @param buf
     */
    public static void sendTo(ServerPlayer player, ResourceLocation identifier, FriendlyByteBuf buf) {
        ServerPlayNetworking.send(player, identifier, buf);
    }

    /**
     *
     * @param identifier
     * @param buf
     */
    public static void sendToServer(ResourceLocation identifier, FriendlyByteBuf buf) {
        if (ClientPlayNetworking.canSend(identifier)) {
            ClientPlayNetworking.send(identifier, buf);
        }
    }
}
