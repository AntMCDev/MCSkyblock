package com.ant.mcskyblock.fabric.network;

import com.ant.mcskyblock.common.SkyBlock;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

public class WorldTypePacket implements IFabricPacket {
    private static final ResourceLocation IDENTIFIER = new ResourceLocation(SkyBlock.MOD_NAME + ":" + SkyBlock.NET_WORLDTYPE_ID);

    /**
     *
     * @return
     */
    @Override
    public ResourceLocation getIdentifier() {
        return IDENTIFIER;
    }

    /**
     *
     * @param client
     * @param handler
     * @param buf
     * @param responseSender
     */
    @Override
    public void executeOnClient(Minecraft client, ClientGamePacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        client.execute(() -> {
            SkyBlock.IS_CLIENT_SKYBLOCK = true;
        });
    }

    /**
     *
     * @param server
     * @param player
     * @param handler
     * @param buf
     * @param responseSender
     */
    @Override
    public void executeOnServer(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler,
                                FriendlyByteBuf buf, PacketSender responseSender)
    {}
}
