package com.ant.mcskyblock.network;

import com.ant.mcskyblock.world.SkyblockWorldInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSkyblockWorld {
    public static void encode(PacketSkyblockWorld packet, FriendlyByteBuf buffer) {

    }

    public static PacketSkyblockWorld decode(FriendlyByteBuf buffer) {
        return new PacketSkyblockWorld();
    }

    public static void handle(PacketSkyblockWorld packet, Supplier<NetworkEvent.Context> context) {
        if (context.get().getDirection().getReceptionSide().isClient()) {
            context.get().enqueueWork(() -> {
                ClientLevel.ClientLevelData info = Minecraft.getInstance().level.getLevelData();
                if (info instanceof SkyblockWorldInfo) {
                    ((SkyblockWorldInfo)info).markSkyblock();
                }
            });
        }
        context.get().setPacketHandled(true);
    }
}
