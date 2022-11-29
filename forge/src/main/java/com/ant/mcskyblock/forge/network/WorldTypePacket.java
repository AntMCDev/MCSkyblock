package com.ant.mcskyblock.forge.network;

import com.ant.mcskyblock.common.SkyBlock;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class WorldTypePacket implements IForgePacket {
    private static final ResourceLocation IDENTIFIER = new ResourceLocation(SkyBlock.MOD_NAME + ":" + SkyBlock.NET_WORLDTYPE_ID);
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
        ctx.get().enqueueWork(() -> {
            if (ctx.get().getSender() == null) {
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                    SkyBlock.IS_CLIENT_SKYBLOCK = true;
                });
            }
        });
        ctx.get().setPacketHandled(true);
    }

    @Override
    public void executeOnServer(FriendlyByteBuf buf, Supplier<NetworkEvent.Context> ctx) {
        throw new RuntimeException("Server network execution not needed on Forge");
    }
}
