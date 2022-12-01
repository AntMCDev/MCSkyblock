package com.ant.mcskyblock.fabric;

import com.ant.mcskyblock.common.SkyBlock;
import com.ant.mcskyblock.common.network.PacketHandler;
import com.ant.mcskyblock.common.world.events.SkyBlockEvents;
import com.ant.mcskyblock.common.world.level.levelgen.IslandGenerator;
import com.ant.mcskyblock.common.world.level.structure.SkyBlockStructureTracker;
import com.ant.mcskyblock.fabric.loot.FabricLootTableUtils;
import com.ant.mcskyblock.fabric.network.FabricPacketHandler;
import com.ant.mcskyblock.common.world.entity.npc.TradingUtils;
import com.ant.mcskyblock.common.world.level.levelgen.SkyBlockChunkGenerator;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.world.level.Level;

public class FabricSkyBlockEvents extends SkyBlockEvents {
    @Override
    public SkyBlockEvents register() {

        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            SkyBlockStructureTracker.rescan();
        });

        ServerWorldEvents.LOAD.register((server, level) -> {
            SkyBlock.IS_SERVER_SKYBLOCK = level.getChunkSource().getGenerator() instanceof SkyBlockChunkGenerator;
            if (SkyBlock.isLogicalServer(level) && SkyBlock.IS_SERVER_SKYBLOCK && level.dimension().equals(Level.OVERWORLD)) {
                PacketHandler.INSTANCE = new FabricPacketHandler().init().registerServerListener();
                IslandGenerator.init(server.overworld());
            }
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            if (server.overworld().getChunkSource().getGenerator() instanceof SkyBlockChunkGenerator) {
                PacketHandler.INSTANCE.sendTo(handler.getPlayer(), PacketHandler.WORLD_TYPE_PACKET.getIdentifier(), new byte[0]);
                INSTANCE.spawnPlayer(handler.getPlayer());
            }
        });

        return this;
    }

}
