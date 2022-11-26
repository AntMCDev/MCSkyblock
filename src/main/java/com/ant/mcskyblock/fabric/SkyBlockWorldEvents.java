package com.ant.mcskyblock.fabric;

import com.ant.mcskyblock.common.SkyBlock;
import com.ant.mcskyblock.common.network.PacketHandler;
import com.ant.mcskyblock.common.world.events.SkyBlockEvents;
import com.ant.mcskyblock.common.world.level.structure.SkyBlockStructureTracker;
import com.ant.mcskyblock.fabric.loot.LootTableUtils;
import com.ant.mcskyblock.fabric.network.FabricPacketHandler;
import com.ant.mcskyblock.common.world.entity.npc.TradingUtils;
import com.ant.mcskyblock.common.world.level.levelgen.SkyBlockChunkGenerator;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

// REQ FABRIC

public class SkyBlockWorldEvents extends SkyBlockEvents {
    @Override
    public void register() {
        TradingUtils.register();
        LootTableUtils.register();

        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            SkyBlockStructureTracker.rescan();
        });

        ServerWorldEvents.LOAD.register((server, level) -> {
            IS_SERVER_SKYBLOCK = level.getChunkSource().getGenerator() instanceof SkyBlockChunkGenerator;
            if (SkyBlock.isLogicalServer(level) && IS_SERVER_SKYBLOCK) {
                PacketHandler.INSTANCE = new FabricPacketHandler().init().registerServerListener();
            }
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            if (server.overworld().getChunkSource().getGenerator() instanceof SkyBlockChunkGenerator) {
                PacketHandler.INSTANCE.sendTo(handler.getPlayer(), PacketHandler.WORLD_TYPE_PACKET.getIdentifier(), new byte[0]);
                INSTANCE.spawnPlayer(handler.getPlayer());
            }
        });
    }

}
