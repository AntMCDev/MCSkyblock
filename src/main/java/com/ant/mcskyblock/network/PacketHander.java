package com.ant.mcskyblock.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class PacketHander {
    public static final IPacket WORLDTYPE_PACKET = new WorldTypePacket();
    public static final IPacket STRUCTURECHECK_PACKET = new StructureCheckPacket();

    private static final IPacket[] PACKETS = new IPacket[] {
            WORLDTYPE_PACKET,
            STRUCTURECHECK_PACKET
    };

    private PacketHander() {
    }

    public static void registerClientListener() {
        for (IPacket packet : PACKETS) {
            packet.registerOnClient();
        }
    }

    public static void registerServerListener() {
        for (IPacket packet : PACKETS) {
            packet.registerOnServer();
        }
    }

    public static void sendTo(ServerPlayerEntity player, Identifier identifier, PacketByteBuf buf) {
        ServerPlayNetworking.send(player, identifier, buf);
    }

    public static void sendToServer(Identifier identifier, PacketByteBuf buf) {
        ClientPlayNetworking.send(identifier, buf);
    }
}
