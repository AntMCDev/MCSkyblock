package com.ant.mcskyblock.network;

import com.ant.mcskyblock.MCSkyBlock;
import com.ant.mcskyblock.world.SkyblockWorldEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class WorldTypePacket extends AbstractPacket {
    private static final Identifier IDENTIFIER = new Identifier(MCSkyBlock.MOD_NAME + ":worldtype");

    @Override
    public Identifier getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public void executeOnClient(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        client.execute(() -> {
            SkyblockWorldEvents.isClientSkyblock = true;
        });
    }

    @Override
    public void executeOnServer(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {

    }
}
