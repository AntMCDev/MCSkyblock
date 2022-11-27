package com.ant.mcskyblock;

import com.ant.mcskyblock.common.SkyBlock;

import com.ant.mcskyblock.common.config.ClothConfig;
import com.ant.mcskyblock.common.config.ClothConfigGeneric;
import com.ant.mcskyblock.common.network.PacketHandler;
import com.ant.mcskyblock.common.world.entity.npc.TradingUtils;
import com.ant.mcskyblock.common.world.events.SkyBlockEvents;
import com.ant.mcskyblock.fabric.FabricSkyBlockEvents;
import com.ant.mcskyblock.fabric.loot.FabricLootTableUtils;
import com.ant.mcskyblock.fabric.network.FabricPacketHandler;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class FabricMCSkyBlock implements ModInitializer {
	@Override
	public void onInitialize() {
		ClothConfigGeneric.load(FabricLoader.getInstance().getConfigDir());
		ClothConfigGeneric.save(FabricLoader.getInstance().getConfigDir());
		SkyBlock.init();
		FabricLootTableUtils.register();
		if (isPhysicalClient()) {
			PacketHandler.INSTANCE = new FabricPacketHandler().init().registerClientListener();
		}
		SkyBlockEvents.INSTANCE = new FabricSkyBlockEvents().register();
	}

	public static boolean isPhysicalClient() {
		return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
	}
}
