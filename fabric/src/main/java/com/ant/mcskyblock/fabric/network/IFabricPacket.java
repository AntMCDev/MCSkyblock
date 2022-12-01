package com.ant.mcskyblock.fabric.network;

import com.ant.mcskyblock.common.network.IPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

public interface IFabricPacket extends IPacket {
    @Override
    default void registerOnClient() {
        ClientPlayNetworking.registerGlobalReceiver(getIdentifier(), this::executeOnClient);
    }

    void executeOnClient(Minecraft client, ClientGamePacketListener handler, FriendlyByteBuf buf, PacketSender responseSender);

    @Override
    default void registerOnServer() {
        ServerPlayNetworking.registerGlobalReceiver(getIdentifier(), this::executeOnServer);
    }

    void executeOnServer(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender);
}
