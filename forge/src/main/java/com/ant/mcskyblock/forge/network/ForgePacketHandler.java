package com.ant.mcskyblock.forge.network;

import com.ant.mcskyblock.common.SkyBlock;
import com.ant.mcskyblock.common.network.PacketHandler;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;

public class ForgePacketHandler extends PacketHandler {
    @Override
    public PacketHandler init() {
        PacketHandler.WORLD_TYPE_PACKET = new WorldTypePacket();
        PacketHandler.STRUCTURE_CHECK_PACKET = new StructureCheckPacket();
        PacketHandler.CONFIG_PACKET = new ConfigPacket();
        return this;
    }

    @Override
    public void sendTo(ServerPlayer player, ResourceLocation identifier, byte[] data) {
        if (identifier.equals(PacketHandler.WORLD_TYPE_PACKET.getIdentifier())) {
            ((IForgePacket)PacketHandler.WORLD_TYPE_PACKET).getChannel().send(PacketDistributor.PLAYER.with(() -> player), bytesToByteBuf(data));
        } else if (identifier.equals(PacketHandler.STRUCTURE_CHECK_PACKET.getIdentifier())) {
            ((IForgePacket)PacketHandler.STRUCTURE_CHECK_PACKET).getChannel().send(PacketDistributor.PLAYER.with(() -> player), bytesToByteBuf(data));
        } else if (identifier.equals(PacketHandler.CONFIG_PACKET.getIdentifier())) {
            ((IForgePacket)PacketHandler.CONFIG_PACKET).getChannel().send(PacketDistributor.PLAYER.with(() -> player), bytesToByteBuf(data));
        }
    }

    @Override
    public void sendToServer(ResourceLocation identifier, byte[] data) {
        try {
            if (identifier.equals(PacketHandler.WORLD_TYPE_PACKET.getIdentifier())) {
                ((IForgePacket) PacketHandler.WORLD_TYPE_PACKET).getChannel().sendToServer(bytesToByteBuf(data));
            } else if (identifier.equals(PacketHandler.STRUCTURE_CHECK_PACKET.getIdentifier())) {
                ((IForgePacket) PacketHandler.STRUCTURE_CHECK_PACKET).getChannel().sendToServer(bytesToByteBuf(data));
            } else if (identifier.equals(PacketHandler.CONFIG_PACKET.getIdentifier())) {
                ((IForgePacket) PacketHandler.CONFIG_PACKET).getChannel().sendToServer(bytesToByteBuf(data));
            }
        } catch (NullPointerException ignore) {}
    }

    public static FriendlyByteBuf bytesToByteBuf(byte[] data) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeBytes(data);
        return buf;
    }

    public static byte[] byteBufToBytes(FriendlyByteBuf buf) {
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        return bytes;
    }
}
