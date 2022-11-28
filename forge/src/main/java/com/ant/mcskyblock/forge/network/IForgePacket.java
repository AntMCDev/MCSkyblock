package com.ant.mcskyblock.forge.network;

import com.ant.mcskyblock.common.SkyBlock;
import com.ant.mcskyblock.common.network.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

public interface IForgePacket extends IPacket {
    @Override
    default void registerOnClient() {
        SkyBlock.LOGGER.info("TODO - register on client");
    }

    void executeOnClient(Minecraft client, ClientGamePacketListener handler, FriendlyByteBuf buf);

    @Override
    default void registerOnServer() {
        SkyBlock.LOGGER.info("TODO - register on server");
    }

    void executeOnServer(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf);
}
