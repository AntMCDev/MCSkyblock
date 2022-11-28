package com.ant.mcskyblock.forge.network;

import com.ant.mcskyblock.common.SkyBlock;
import com.ant.mcskyblock.common.network.PacketHandler;
import com.ant.mcskyblock.common.world.level.structure.ClientStructureTracker;
import io.netty.buffer.Unpooled;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.client.Minecraft;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

import java.util.stream.Collectors;

public class StructureCheckPacket implements IForgePacket {
    private static final ResourceLocation IDENTIFIER = new ResourceLocation(SkyBlock.MOD_NAME + ":" + SkyBlock.NET_STRUCTURECHECK_ID);

    @Override
    public ResourceLocation getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public void executeOnClient(Minecraft client, ClientGamePacketListener handler, FriendlyByteBuf buf) {
        final String structures = buf.readUtf();
        client.execute(() -> ClientStructureTracker.structures = structures.length() > 0 ? structures : null);
    }

    @Override
    public void executeOnServer(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf) {
        server.execute(() -> {
            String structures = BuiltinRegistries.STRUCTURES.registryKeySet().stream().filter(s -> LocationPredicate.inStructure(s).matches((ServerLevel) player.level, player.position().x, player.position().y, player.position().z)).map(s -> s.location().toString()).collect(Collectors.joining(", "));
            FriendlyByteBuf responseBuffer = new FriendlyByteBuf(Unpooled.buffer());
            responseBuffer.writeUtf(structures);
            PacketHandler.INSTANCE.sendTo(player, getIdentifier(), ForgePacketHandler.byteBufToBytes(responseBuffer));
        });
    }
}