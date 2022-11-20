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
        final byte[] bytes = PacketHander.byteBufToBytes(buf);
        client.execute(() -> {
                SkyBlock.LOGGER.info("Config packet received from server");
                SkyBlockConfig.loadConfigBytes(bytes);
        });
    }

    @Override
    public void executeOnServer(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler,
                                FriendlyByteBuf buf, PacketSender responseSender) {
        SkyBlock.LOGGER.info("Config packet received from client");
        if (player.hasPermissions(4)) {
            SkyBlock.LOGGER.info("Syncing server manager config with server");
            SkyBlockConfig.loadConfigBytes(PacketHander.byteBufToBytes(buf));
        }
        PacketHander.sendTo(player, getIdentifier(), PacketHander.bytesToByteBuf(SkyBlockConfig.toBytes(SkyBlockConfig.INSTANCE)));
    }
}
