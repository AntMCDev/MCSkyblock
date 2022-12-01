package com.ant.mcskyblock;

import com.ant.mcskyblock.common.SkyBlock;

import com.ant.mcskyblock.common.config.ConfigFileAccessor;
import com.ant.mcskyblock.common.network.PacketHandler;
import com.ant.mcskyblock.common.world.events.SkyBlockEvents;
import com.ant.mcskyblock.common.world.level.block.Blocks;
import com.ant.mcskyblock.fabric.FabricSkyBlockEvents;
import com.ant.mcskyblock.fabric.loot.FabricLootTableUtils;
import com.ant.mcskyblock.fabric.network.FabricPacketHandler;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class FabricMCSkyBlock implements ModInitializer {
	@Override
	public void onInitialize() {
		ConfigFileAccessor.path = FabricLoader.getInstance().getConfigDir();
		ConfigFileAccessor.load();
		ConfigFileAccessor.save();
		SkyBlock.init();
		Blocks.registerBlocks((key, block) -> Registry.register(Registry.BLOCK, new ResourceLocation(SkyBlock.MOD_NAME, key), block));
		Blocks.registerItems((key, item) -> Registry.register(Registry.ITEM, new ResourceLocation(SkyBlock.MOD_NAME, key), item));
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
