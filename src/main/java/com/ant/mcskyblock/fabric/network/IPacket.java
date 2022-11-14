package com.ant.mcskyblock.fabric.network;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

// REQ FABRIC

public interface IPacket {
    ResourceLocation getIdentifier();

    /**
     *
     * @param client
     * @param handler
     * @param buf
     * @param responseSender
     */
    void executeOnClient(Minecraft client, ClientGamePacketListener handler, FriendlyByteBuf buf,
                         PacketSender responseSender);

    /**
     *
     * @param server
     * @param player
     * @param handler
     * @param buf
     * @param responseSender
     */
    void executeOnServer(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf,
                         PacketSender responseSender);

    /**
     *
     */
    void registerOnClient();

    /**
     *
     */
    void registerOnServer();
}