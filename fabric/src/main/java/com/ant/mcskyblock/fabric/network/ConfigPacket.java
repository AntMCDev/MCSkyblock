package com.ant.mcskyblock.fabric.network;

import com.ant.mcskyblock.common.SkyBlock;
import com.ant.mcskyblock.common.config.Config;
import com.ant.mcskyblock.common.network.PacketHandler;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

public class ConfigPacket implements IFabricPacket {
    private static final ResourceLocation IDENTIFIER = new ResourceLocation(SkyBlock.MOD_NAME + ":" + SkyBlock.NET_CONFIG_ID);

    @Override
    public ResourceLocation getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public void executeOnClient(Minecraft client, ClientGamePacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        final byte[] bytes = FabricPacketHandler.byteBufToBytes(buf);
        client.execute(() -> {
            Config.INSTANCE.download(bytes);
        });
    }

    @Override
    public void executeOnServer(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler,
                                FriendlyByteBuf buf, PacketSender responseSender) {
        final byte[] bytes = FabricPacketHandler.byteBufToBytes(buf);
        server.execute(() -> {
            if (player.hasPermissions(4)) {
                Config.INSTANCE.download(bytes);
            }
            PacketHandler.INSTANCE.sendTo(player, getIdentifier(), Config.INSTANCE.toBytes());
        });
    }
}
