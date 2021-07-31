package com.ant.mcskyblock.network;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

import static com.ant.mcskyblock.utils.ResourceLocationHelper.prefix;

public final class PacketHandler {
    private static final String PROTOCOL = "1";
    public static final SimpleChannel HANDLER = NetworkRegistry.newSimpleChannel(prefix("chan"), () -> PROTOCOL, PROTOCOL::equals, PROTOCOL::equals);

    public static void init() {
        int id = 0;
        HANDLER.registerMessage(id++, PacketSkyblockWorld.class, PacketSkyblockWorld::encode, PacketSkyblockWorld::decode, PacketSkyblockWorld::handle);
        HANDLER.registerMessage(id++, PacketStructureCheck.class, PacketStructureCheck::encode, PacketStructureCheck::decode, PacketStructureCheck::handle);
    }

    public static void sendToNearby(Level world, BlockPos blockPos, Object toSend) {
        if (world instanceof ServerLevel) {
            ServerLevel ws = (ServerLevel)world;
            ws.getChunkSource().chunkMap.getPlayers(new ChunkPos(blockPos), false).filter(p -> p.distanceToSqr(blockPos.getX(), blockPos.getY(), blockPos.getZ()) < 64 * 64).forEach(p -> HANDLER.send(PacketDistributor.PLAYER.with(() -> p), toSend));
        }
    }

    public static void sendToNearby(Level world, Entity entity, Object toSend) {
        sendToNearby(world, entity.blockPosition(), toSend);
    }

    public static void sendTo(ServerPlayer player, Object toSend) {
        HANDLER.sendTo(toSend, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendNonLocal(ServerPlayer player, Object toSend) {
        if (player.server.isDedicatedServer() || !player.server.isSingleplayerOwner(player.getGameProfile())) {
            sendTo(player, toSend);
        }
    }

    public static void sendToServer(Object message) {
        HANDLER.sendToServer(message);
    }

    private PacketHandler() {}
}
