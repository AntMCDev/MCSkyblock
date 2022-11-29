package com.ant.mcskyblock.forge.network;

import com.ant.mcskyblock.common.SkyBlock;
import com.ant.mcskyblock.common.network.PacketHandler;
import com.ant.mcskyblock.common.world.level.structure.ClientStructureTracker;
import io.netty.buffer.Unpooled;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;
import java.util.stream.Collectors;

public class StructureCheckPacket implements IForgePacket {
    private static final ResourceLocation IDENTIFIER = new ResourceLocation(SkyBlock.MOD_NAME + ":" + SkyBlock.NET_STRUCTURECHECK_ID);
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
        final String structures = buf.readUtf();
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                ClientStructureTracker.structures = structures.length() > 0 ? structures : null;
            });
        });
        ctx.get().setPacketHandled(true);
    }

    @Override
    public void executeOnServer(FriendlyByteBuf buf, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                String structures = BuiltinRegistries.STRUCTURES.registryKeySet().stream().filter(s -> LocationPredicate.inStructure(s).matches((ServerLevel) player.level, player.position().x, player.position().y, player.position().z)).map(s -> s.location().toString()).collect(Collectors.joining(", "));
                FriendlyByteBuf responseBuffer = new FriendlyByteBuf(Unpooled.buffer());
                responseBuffer.writeUtf(structures);
                PacketHandler.INSTANCE.sendTo(player, getIdentifier(), ForgePacketHandler.byteBufToBytes(responseBuffer));
            }
        });
        ctx.get().setPacketHandled(true);
    }
}