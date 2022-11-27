package com.ant.mcskyblock.fabric.network;

import com.ant.mcskyblock.common.SkyBlock;
import com.ant.mcskyblock.common.network.PacketHandler;
import com.ant.mcskyblock.common.world.level.structure.ClientStructureTracker;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.client.Minecraft;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class StructureCheckPacket implements IFabricPacket {
    private static final ResourceLocation IDENTIFIER = new ResourceLocation(SkyBlock.MOD_NAME + ":structurecheck");

    @Override
    public ResourceLocation getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public void executeOnClient(Minecraft client, ClientGamePacketListener handler, FriendlyByteBuf buf,
                                PacketSender responseSender) {
        final String structures = new String(buf.readByteArray(), StandardCharsets.UTF_8);
        client.execute(() -> ClientStructureTracker.structures = structures.length() > 0 ? structures : null);
    }

    @Override
    public void executeOnServer(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf,
                                PacketSender responseSender) {
        server.execute(() -> {
            String structures = BuiltinRegistries.STRUCTURES.registryKeySet().stream().filter(s -> LocationPredicate.inStructure(s).matches((ServerLevel) player.level, player.position().x, player.position().y, player.position().z)).map(s -> s.location().toString()).collect(Collectors.joining(", "));

            FriendlyByteBuf responseBuffer = PacketByteBufs.create();
            responseBuffer.writeBytes(structures.getBytes(StandardCharsets.UTF_8));
            PacketHandler.INSTANCE.sendTo(player, getIdentifier(), FabricPacketHandler.byteBufToBytes(responseBuffer));
        });
    }
}