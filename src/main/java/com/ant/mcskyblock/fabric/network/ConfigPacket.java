package com.ant.mcskyblock.fabric.network;

import com.ant.mcskyblock.common.SkyBlock;
import com.ant.mcskyblock.common.config.SkyBlockConfig;
import com.ant.mcskyblock.fabric.SkyBlockWorldEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

public class ConfigPacket extends AbstractPacket {
    private static final ResourceLocation IDENTIFIER = new ResourceLocation(SkyBlock.MOD_NAME + ":config");

    @Override
    public ResourceLocation getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public void executeOnClient(Minecraft client, ClientGamePacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        client.execute(() -> {
                SkyBlock.LOGGER.info("Config packet received from server");
                SkyBlockConfig.loadConfigBytes(SkyBlockConfig.toBytes(SkyBlockConfig.INSTANCE));
        });
    }

    @Override
    public void executeOnServer(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler,
                                FriendlyByteBuf buf, PacketSender responseSender) {
        SkyBlock.LOGGER.info("Config packet received from client");
        FriendlyByteBuf responseBuffer = PacketByteBufs.create();
        responseBuffer.writeBytes(SkyBlockConfig.toBytes(SkyBlockConfig.INSTANCE));
        PacketHander.sendTo(player, getIdentifier(), responseBuffer);
    }
}
