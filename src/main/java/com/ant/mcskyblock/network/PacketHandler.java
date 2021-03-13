package com.ant.mcskyblock.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import static com.ant.mcskyblock.utils.ResourceLocationHelper.prefix;

public final class PacketHandler {
    private static final String PROTOCOL = "1";
    public static final SimpleChannel HANDLER = NetworkRegistry.newSimpleChannel(prefix("chan"), () -> PROTOCOL, PROTOCOL::equals, PROTOCOL::equals);

    public static void init() {
        int id = 0;
        HANDLER.registerMessage(id++, PacketSkyblockWorld.class, PacketSkyblockWorld::encode, PacketSkyblockWorld::decode, PacketSkyblockWorld::handle);
        HANDLER.registerMessage(id++, PacketStructureCheck.class, PacketStructureCheck::encode, PacketStructureCheck::decode, PacketStructureCheck::handle);
    }

    public static void sendToNearby(World world, BlockPos blockPos, Object toSend) {
        if (world instanceof ServerWorld) {
            ServerWorld ws = (ServerWorld)world;
            ws.getChunkProvider().chunkManager.getTrackingPlayers(new ChunkPos(blockPos), false).filter(p -> p.getDistanceSq(blockPos.getX(), blockPos.getY(), blockPos.getZ()) < 64 * 64).forEach(p -> HANDLER.send(PacketDistributor.PLAYER.with(() -> p), toSend));
        }
    }

    public static void sendToNearby(World world, Entity entity, Object toSend) {
        sendToNearby(world, entity.getPosition(), toSend);
    }

    public static void sendTo(ServerPlayerEntity player, Object toSend) {
        HANDLER.sendTo(toSend, player.connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendNonLocal(ServerPlayerEntity player, Object toSend) {
        if (player.server.isDedicatedServer() || !player.getGameProfile().getName().equals(player.server.getServerOwner())) {
            sendTo(player, toSend);
        }
    }

    public static void sendToServer(Object message) {
        HANDLER.sendToServer(message);
    }

    private PacketHandler() {}
}
