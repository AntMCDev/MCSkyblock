package com.ant.mcskyblock.network;

import com.ant.mcskyblock.MCSkyBlock;
import com.ant.mcskyblock.world.StructureInfo;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class StructureCheckPacket extends AbstractPacket {
    private static final Identifier IDENTIFIER = new Identifier(MCSkyBlock.MOD_NAME + ":structurecheck");

    @Override
    public Identifier getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public void executeOnClient(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        final StructureInfo.SupportedStructures structure = buf.readEnumConstant(StructureInfo.SupportedStructures.class);

        client.execute(() -> {
            StructureInfo.structure = structure;
        });
    }

    @Override
    public void executeOnServer(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        server.execute(() -> {
            /*StructureInfo.SupportedStructures structure = LocationPredicate.feature(StructureFeature.FORTRESS).test((ServerWorld)player.world, player.getPos().x, player.getPos().y, player.getPos().z) ? StructureInfo.SupportedStructures.FORTRESS : StructureInfo.SupportedStructures.NONE;
            if (structure == StructureInfo.SupportedStructures.NONE) {
                structure = LocationPredicate.feature(StructureFeature.MONUMENT).test((ServerWorld)player.world, player.getPos().x, player.getPos().y, player.getPos().z) ? StructureInfo.SupportedStructures.MONUMENT : StructureInfo.SupportedStructures.NONE;
                if (structure == StructureInfo.SupportedStructures.NONE) {
                    structure = LocationPredicate.feature(StructureFeature.SWAMP_HUT).test((ServerWorld)player.world, player.getPos().x, player.getPos().y, player.getPos().z) ? StructureInfo.SupportedStructures.SWAMP_HUT : StructureInfo.SupportedStructures.NONE;
                    if (structure == StructureInfo.SupportedStructures.NONE) {
                        structure = LocationPredicate.feature(StructureFeature.PILLAGER_OUTPOST).test((ServerWorld)player.world, player.getPos().x, player.getPos().y, player.getPos().z) ? StructureInfo.SupportedStructures.PILLAGER_OUTPOST : StructureInfo.SupportedStructures.NONE;
                    }
                }
            }*/
            StructureInfo.SupportedStructures structure = StructureInfo.SupportedStructures.NONE;

            PacketByteBuf responseBuffer = PacketByteBufs.create();
            responseBuffer.writeEnumConstant(structure);
            PacketHander.sendTo(player, getIdentifier(), responseBuffer);
        });
    }
}
