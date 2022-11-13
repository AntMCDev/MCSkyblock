package com.ant.mcskyblock.fabric.network;

import com.ant.mcskyblock.common.MCSkyBlock;
import com.ant.mcskyblock.fabric.SkyBlockWorldEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

// REQ FABRIC
public class SkyblockPacket extends AbstractPacket {
    private static final ResourceLocation IDENTIFIER = new ResourceLocation(MCSkyBlock.MOD_NAME + ":worldtype");

    @Override
    public ResourceLocation getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public void executeOnClient(Minecraft client, ClientGamePacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        client.execute(() -> {
            SkyBlockWorldEvents.isClientSkyblock = true;
        });
    }

    @Override
    public void executeOnServer(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler,
                                FriendlyByteBuf buf, PacketSender responseSender)
    {}
}
