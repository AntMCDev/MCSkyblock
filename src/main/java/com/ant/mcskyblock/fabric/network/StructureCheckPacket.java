package com.ant.mcskyblock.fabric.network;

import com.ant.mcskyblock.common.SkyBlock;
import com.ant.mcskyblock.common.network.PacketHandler;
import com.ant.mcskyblock.common.world.level.structure.ClientStructureTracker;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;

public class StructureCheckPacket implements IFabricPacket {
    private static final ResourceLocation IDENTIFIER = new ResourceLocation(SkyBlock.MOD_NAME + ":structurecheck");

    @Override
    public ResourceLocation getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public void executeOnClient(Minecraft client, ClientGamePacketListener handler, FriendlyByteBuf buf,
                                PacketSender responseSender) {
        final ClientStructureTracker.SupportedStructures structure = buf.readEnum(ClientStructureTracker.SupportedStructures.class);

        client.execute(() -> {
            ClientStructureTracker.structure = structure;
        });
    }

    @Override
    public void executeOnServer(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf,
                                PacketSender responseSender) {
        server.execute(() -> {
            ClientStructureTracker.SupportedStructures structure = LocationPredicate.inStructure(BuiltinStructures.FORTRESS)
                    .matches((ServerLevel) player.level, player.position().x, player.position().y, player.position().z) ?
                    ClientStructureTracker.SupportedStructures.FORTRESS : ClientStructureTracker.SupportedStructures.NONE;

            if (structure == ClientStructureTracker.SupportedStructures.NONE) {
                structure = LocationPredicate.inStructure(BuiltinStructures.BASTION_REMNANT)
                        .matches((ServerLevel) player.level, player.position().x, player.position().y, player.position().z) ?
                        ClientStructureTracker.SupportedStructures.BASTION : ClientStructureTracker.SupportedStructures.NONE;

                if (structure == ClientStructureTracker.SupportedStructures.NONE) {
                    structure = LocationPredicate.inStructure(BuiltinStructures.OCEAN_MONUMENT)
                            .matches((ServerLevel) player.level, player.position().x, player.position().y, player.position().z) ?
                            ClientStructureTracker.SupportedStructures.MONUMENT : ClientStructureTracker.SupportedStructures.NONE;

                    if (structure == ClientStructureTracker.SupportedStructures.NONE) {
                        structure = LocationPredicate.inStructure(BuiltinStructures.SWAMP_HUT)
                                .matches((ServerLevel) player.level, player.position().x, player.position().y, player.position().z) ?
                                ClientStructureTracker.SupportedStructures.SWAMP_HUT : ClientStructureTracker.SupportedStructures.NONE;

                        if (structure == ClientStructureTracker.SupportedStructures.NONE) {
                            structure = LocationPredicate.inStructure(BuiltinStructures.PILLAGER_OUTPOST)
                                    .matches((ServerLevel) player.level, player.position().x, player.position().y, player.position().z) ?
                                    ClientStructureTracker.SupportedStructures.PILLAGER_OUTPOST : ClientStructureTracker.SupportedStructures.NONE;
                        }
                    }
                }
            }
            FriendlyByteBuf responseBuffer = PacketByteBufs.create();
            responseBuffer.writeEnum(structure);
            PacketHandler.INSTANCE.sendTo(player, getIdentifier(), FabricPacketHandler.byteBufToBytes(responseBuffer));
        });
    }
}