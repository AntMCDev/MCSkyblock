package com.ant.mcskyblock.fabric.network;

import com.ant.mcskyblock.common.network.PacketHandler;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class FabricPacketHandler extends PacketHandler {
    @Override
    public PacketHandler init() {
        PacketHandler.WORLD_TYPE_PACKET = new WorldTypePacket();
        PacketHandler.STRUCTURE_CHECK_PACKET = new StructureCheckPacket();
        PacketHandler.CONFIG_PACKET = new ConfigPacket();
        return this;
    }

    @Override
    public void sendTo(ServerPlayer player, ResourceLocation identifier, byte[] data) {
        ServerPlayNetworking.send(player, identifier, bytesToByteBuf(data));
    }

    @Override
    public void sendToServer(ResourceLocation identifier, byte[] data) {
        if (ClientPlayNetworking.canSend(identifier)) {
            ClientPlayNetworking.send(identifier, bytesToByteBuf(data));
        }
    }

    public static FriendlyByteBuf bytesToByteBuf(byte[] data) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeBytes(data);
        return buf;
    }

    public static byte[] byteBufToBytes(FriendlyByteBuf buf) {
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        return bytes;
    }
}
