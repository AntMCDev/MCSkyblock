package com.ant.mcskyblock.forge.network;

import com.ant.mcskyblock.common.network.IPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public interface IForgePacket extends IPacket {
    abstract SimpleChannel getChannel();

    @Override
    default void registerOnClient() {
        getChannel().registerMessage(0, FriendlyByteBuf.class, (msg, buf) -> buf.writeBytes(msg), (buf) -> buf, this::executeOnClient);
    }

    void executeOnClient(FriendlyByteBuf buf, Supplier<NetworkEvent.Context> ctx);

    @Override
    default void registerOnServer() {
        throw new RuntimeException("Server network registration not needed on Forge");
    }

    void executeOnServer(FriendlyByteBuf buf, Supplier<NetworkEvent.Context> ctx);
}
