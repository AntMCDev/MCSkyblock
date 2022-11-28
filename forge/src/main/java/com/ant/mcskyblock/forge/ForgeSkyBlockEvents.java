package com.ant.mcskyblock.forge;

import com.ant.mcskyblock.common.SkyBlock;
import com.ant.mcskyblock.common.network.PacketHandler;
import com.ant.mcskyblock.common.world.events.SkyBlockEvents;
import com.ant.mcskyblock.common.world.level.levelgen.SkyBlockChunkGenerator;
import com.ant.mcskyblock.common.world.level.structure.SkyBlockStructureTracker;
import com.ant.mcskyblock.forge.network.ForgePacketHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeSkyBlockEvents extends SkyBlockEvents {
    @Override
    public SkyBlockEvents register() {
        IEventBus bus = MinecraftForge.EVENT_BUS;
        bus.addListener(this::onServerStart);
        bus.addListener(this::onWorldLoad);
        bus.addListener(this::onPlayerJoin);
        return this;
    }

    @SubscribeEvent
    public void onServerStart(ServerStartedEvent event) {
        SkyBlockStructureTracker.rescan();
    }

    @SubscribeEvent
    public void onWorldLoad(LevelEvent.Load event) {
        if (event.getLevel() instanceof ServerLevel && event.getLevel() != null) {
            SkyBlock.IS_SERVER_SKYBLOCK = ((ServerLevel) event.getLevel()).getChunkSource().getGenerator() instanceof SkyBlockChunkGenerator;
            if (SkyBlock.isLogicalServer((ServerLevel) event.getLevel()) && SkyBlock.IS_SERVER_SKYBLOCK) {
                PacketHandler.INSTANCE = new ForgePacketHandler().init().registerServerListener();
            }
        }
    }

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity().getServer() != null && event.getEntity().getServer().overworld().getChunkSource().getGenerator() instanceof SkyBlockChunkGenerator) {
            PacketHandler.INSTANCE.sendTo((ServerPlayer) event.getEntity(), PacketHandler.WORLD_TYPE_PACKET.getIdentifier(), new byte[0]);
            INSTANCE.spawnPlayer((ServerPlayer) event.getEntity());
        }
    }
}
