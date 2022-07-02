package com.ant.mcskyblock;

import com.ant.mcskyblock.config.ConfigHandler;
import com.ant.mcskyblock.network.PacketHander;
import com.ant.mcskyblock.skyblock.SkyblockChunkGenerator;
import com.ant.mcskyblock.skyblock.SkyblockWorldEvents;
import com.ant.mcskyblock.skyblock.SkyblockWorldPreset;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MCSkyBlock implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_NAME = "mcskyblock";
	public static final String MODULE_SKYBLOCK = "skyblock";

	@Override
	public void onInitialize() {
		if (isPhysicalClient()) {
			PacketHander.registerClientListener();
		}

		new ConfigHandler.Common();

		SkyblockWorldPreset.register();

		SkyblockWorldEvents.init();

		Registry.register(Registry.CHUNK_GENERATOR, new Identifier(MCSkyBlock.MOD_NAME, MODULE_SKYBLOCK), SkyblockChunkGenerator.CODEC);
	}

	public static boolean isPhysicalClient() {
		return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
	}

	public static boolean isLogicalClient(World world) {
		return world.isClient;
	}

	public static boolean isPhysicalServer() {
		return !isPhysicalClient();
	}

	public static boolean isLogicalServer(World world) {
		return !isLogicalClient(world);
	}
}
