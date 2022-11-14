package com.ant.mcskyblock;

import com.ant.mcskyblock.common.MCSkyBlock;

import com.ant.mcskyblock.fabric.SkyBlockWorldEvents;
import com.ant.mcskyblock.fabric.network.PacketHander;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class FabricMCSkyBlock implements ModInitializer {
	public static MCSkyBlock MC_SKY_BLOCK;
	@Override
	public void onInitialize() {
		MC_SKY_BLOCK = new MCSkyBlock();
		if (isPhysicalClient()) {
			PacketHander.registerClientListener();
		}
		SkyBlockWorldEvents.init();
	}
	public static boolean isPhysicalClient() {
		return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
	}
}
