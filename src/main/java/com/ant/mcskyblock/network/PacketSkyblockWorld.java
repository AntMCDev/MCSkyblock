package com.ant.mcskyblock.network;

import com.ant.mcskyblock.world.SkyblockWorldInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSkyblockWorld {
    public static void encode(PacketSkyblockWorld packet, PacketBuffer buffer) {

    }

    public static PacketSkyblockWorld decode(PacketBuffer buffer) {
        return new PacketSkyblockWorld();
    }

    public static void handle(PacketSkyblockWorld packet, Supplier<NetworkEvent.Context> context) {
        if (context.get().getDirection().getReceptionSide().isClient()) {
            context.get().enqueueWork(() -> {
                ClientWorld.ClientWorldInfo info = Minecraft.getInstance().world.getWorldInfo();
                if (info instanceof SkyblockWorldInfo) {
                    ((SkyblockWorldInfo)info).markSkyblock();
                }
            });
        }
        context.get().setPacketHandled(true);
    }
}
