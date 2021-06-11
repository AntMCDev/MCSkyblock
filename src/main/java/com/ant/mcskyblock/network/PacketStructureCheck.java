package com.ant.mcskyblock.network;

import com.ant.mcskyblock.MCSkyblock;
import com.ant.mcskyblock.world.StructureInfo;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketStructureCheck {

    private StructureInfo.SupportedStructures structure;

    public PacketStructureCheck() {
        this(StructureInfo.SupportedStructures.NONE);
    }

    public PacketStructureCheck(StructureInfo.SupportedStructures structure) {
        this.structure = structure;
    }

    public static void encode(PacketStructureCheck packet, PacketBuffer buffer) {
        if (packet.structure != null) {
            buffer.writeByte(packet.structure.ordinal());
        }
    }

    public static PacketStructureCheck decode(PacketBuffer buffer) {
        return new PacketStructureCheck(StructureInfo.SupportedStructures.values()[buffer.readByte()]);
    }

    public static void handle(PacketStructureCheck packet, Supplier<NetworkEvent.Context> context) {
        if (context.get().getDirection().getReceptionSide().isServer()) {
            context.get().enqueueWork(() -> {
                StructureInfo.SupportedStructures structure = LocationPredicate.inFeature(Structure.NETHER_BRIDGE).matches(context.get().getSender().getLevel(), context.get().getSender().position().x, context.get().getSender().position().y, context.get().getSender().position().z) ? StructureInfo.SupportedStructures.FORTRESS : StructureInfo.SupportedStructures.NONE;
                if (structure == StructureInfo.SupportedStructures.NONE) {
                    structure = LocationPredicate.inFeature(Structure.OCEAN_MONUMENT).matches(context.get().getSender().getLevel(), context.get().getSender().position().x, context.get().getSender().position().y, context.get().getSender().position().z) ? StructureInfo.SupportedStructures.MONUMENT : StructureInfo.SupportedStructures.NONE;
                    if (structure == StructureInfo.SupportedStructures.NONE) {
                        structure = LocationPredicate.inFeature(Structure.SWAMP_HUT).matches(context.get().getSender().getLevel(), context.get().getSender().position().x, context.get().getSender().position().y, context.get().getSender().position().z) ? StructureInfo.SupportedStructures.SWAMP_HUT : StructureInfo.SupportedStructures.NONE;
                        if (structure == StructureInfo.SupportedStructures.NONE) {
                            structure = LocationPredicate.inFeature(Structure.PILLAGER_OUTPOST).matches(context.get().getSender().getLevel(), context.get().getSender().position().x, context.get().getSender().position().y, context.get().getSender().position().z) ? StructureInfo.SupportedStructures.PILLAGER_OUTPOST : StructureInfo.SupportedStructures.NONE;
                        }
                    }
                }
                PacketHandler.sendTo(context.get().getSender(), new PacketStructureCheck(structure));
            });
        } else {
            context.get().enqueueWork(() -> {
                StructureInfo.structure = packet.structure;
            });
        }
        context.get().setPacketHandled(true);
    }
}
