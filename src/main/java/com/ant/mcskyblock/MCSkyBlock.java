package com.ant.mcskyblock;

import com.ant.mcskyblock.config.ConfigHandler;
import com.ant.mcskyblock.network.PacketHander;
import com.ant.mcskyblock.world.SkyblockChunkGenerator;
import com.ant.mcskyblock.world.SkyblockWorldEvents;
import com.ant.mcskyblock.world.SkyblockWorldType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.MessageType;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MCSkyBlock implements ModInitializer {
	public static final String MOD_NAME = "mcskyblock";
	public static final String MOD_IDENTIFIER = "mcskyblock:mcskyblock";

	public static final Logger LOGGER = LogManager.getLogger();

	public static SkyblockWorldType skyblockWorldType;

	@Override
	public void onInitialize() {
		if (isPhysicalClient()) {
			skyblockWorldType = new SkyblockWorldType(MOD_NAME);
			PacketHander.registerClientListener();
		}

		new ConfigHandler.Common();

		SkyblockWorldEvents.init();

		Registry.register(Registry.CHUNK_GENERATOR, new Identifier(MOD_IDENTIFIER), SkyblockChunkGenerator.CODEC);
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

	public static boolean isSkyblockWorld(ChunkGenerator chunkGenerator) {
		return chunkGenerator instanceof SkyblockChunkGenerator;
	}

	public static void printToChat(String msg) {
		if (isPhysicalClient()) {
			MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText(msg), MinecraftClient.getInstance().player.getUuid());
		}
	}
}
