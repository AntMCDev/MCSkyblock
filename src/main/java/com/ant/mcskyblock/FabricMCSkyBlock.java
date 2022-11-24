package com.ant.mcskyblock;

import com.ant.mcskyblock.common.SkyBlock;

import com.ant.mcskyblock.common.network.PacketHandler;
import com.ant.mcskyblock.fabric.SkyBlockWorldEvents;
import com.ant.mcskyblock.common.command.LocateIslandCommand;
import com.ant.mcskyblock.fabric.network.FabricPacketHandler;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class FabricMCSkyBlock implements ModInitializer {
	@Override
	public void onInitialize() {
		SkyBlock.init();
		if (isPhysicalClient()) {
			PacketHandler.INSTANCE = new FabricPacketHandler().init().registerClientListener();
		}
		SkyBlockWorldEvents.init();
	}

	public static boolean isPhysicalClient() {
		return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
	}
}
