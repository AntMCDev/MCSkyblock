package com.ant.mcskyblock.forge.network;

import com.ant.mcskyblock.common.SkyBlock;
import com.ant.mcskyblock.common.config.Config;
import com.ant.mcskyblock.common.network.PacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class ConfigPacket implements IForgePacket {
    private static final ResourceLocation IDENTIFIER = new ResourceLocation(SkyBlock.MOD_NAME + ":" + SkyBlock.NET_CONFIG_ID);
    private static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(IDENTIFIER, () -> "0", "0"::equals, "0"::equals);

    @Override
    public ResourceLocation getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public SimpleChannel getChannel() {
        return CHANNEL;
    }

    @Override
    public void executeOnClient(FriendlyByteBuf buf, Supplier<NetworkEvent.Context> ctx) {
        final byte[] bytes = ForgePacketHandler.byteBufToBytes(buf);
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                Config.INSTANCE.download(bytes);
            });
        });
        ctx.get().setPacketHandled(true);
    }

    @Override
    public void executeOnServer(FriendlyByteBuf buf, Supplier<NetworkEvent.Context> ctx) {
        final byte[] bytes = ForgePacketHandler.byteBufToBytes(buf);
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null && player.hasPermissions(4)) {
                Config.INSTANCE.download(bytes);
            }
            PacketHandler.INSTANCE.sendTo(player, getIdentifier(), Config.INSTANCE.toBytes());
        });
        ctx.get().setPacketHandled(true);
    }
}
