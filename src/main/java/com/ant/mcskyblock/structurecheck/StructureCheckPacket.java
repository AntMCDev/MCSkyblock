package com.ant.mcskyblock.structurecheck;

import com.ant.mcskyblock.MCSkyBlock;
import com.ant.mcskyblock.network.AbstractPacket;
import com.ant.mcskyblock.network.PacketHander;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.StructureKeys;

public class StructureCheckPacket extends AbstractPacket {
    private static final Identifier IDENTIFIER = new Identifier(MCSkyBlock.MOD_NAME + ":structurecheck");

    @Override
    public Identifier getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public void executeOnClient(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        final ClientStructureTracker.SupportedStructures structure = buf.readEnumConstant(ClientStructureTracker.SupportedStructures.class);

        client.execute(() -> {
            ClientStructureTracker.structure = structure;
        });
    }

    @Override
    public void executeOnServer(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        server.execute(() -> {
            ClientStructureTracker.SupportedStructures structure = LocationPredicate.feature(StructureKeys.FORTRESS).test((ServerWorld) player.world, player.getPos().x, player.getPos().y, player.getPos().z) ? ClientStructureTracker.SupportedStructures.FORTRESS : ClientStructureTracker.SupportedStructures.NONE;
            if (structure == ClientStructureTracker.SupportedStructures.NONE) {
                structure = LocationPredicate.feature(StructureKeys.MONUMENT).test((ServerWorld)player.world, player.getPos().x, player.getPos().y, player.getPos().z) ? ClientStructureTracker.SupportedStructures.MONUMENT : ClientStructureTracker.SupportedStructures.NONE;
                if (structure == ClientStructureTracker.SupportedStructures.NONE) {
                    structure = LocationPredicate.feature(StructureKeys.SWAMP_HUT).test((ServerWorld)player.world, player.getPos().x, player.getPos().y, player.getPos().z) ? ClientStructureTracker.SupportedStructures.SWAMP_HUT : ClientStructureTracker.SupportedStructures.NONE;
                    if (structure == ClientStructureTracker.SupportedStructures.NONE) {
                        structure = LocationPredicate.feature(StructureKeys.PILLAGER_OUTPOST).test((ServerWorld)player.world, player.getPos().x, player.getPos().y, player.getPos().z) ? ClientStructureTracker.SupportedStructures.PILLAGER_OUTPOST : ClientStructureTracker.SupportedStructures.NONE;
                    }
                }
            }

            PacketByteBuf responseBuffer = PacketByteBufs.create();
            responseBuffer.writeEnumConstant(structure);
            PacketHander.sendTo(player, getIdentifier(), responseBuffer);
        });
    }
}
