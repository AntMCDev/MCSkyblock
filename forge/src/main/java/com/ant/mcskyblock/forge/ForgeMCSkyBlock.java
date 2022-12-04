package com.ant.mcskyblock.forge;

import com.ant.mcskyblock.common.SkyBlock;

import com.ant.mcskyblock.common.config.ConfigFileAccessor;
import com.ant.mcskyblock.common.network.PacketHandler;
import com.ant.mcskyblock.common.registry.RegistryAccess;
import com.ant.mcskyblock.common.world.events.SkyBlockEvents;
import com.ant.mcskyblock.common.world.level.block.Blocks;
import com.ant.mcskyblock.forge.config.ClothConfig;
import com.ant.mcskyblock.forge.network.ForgePacketHandler;
import com.ant.mcskyblock.forge.registry.ForgeRegistryAccess;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;


@Mod(SkyBlock.MOD_NAME)
@Mod.EventBusSubscriber(modid = SkyBlock.MOD_NAME, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeMCSkyBlock {
    public ForgeMCSkyBlock(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::setup);
        bus.addListener(this::client);
        bus.addListener(this::register);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            PacketHandler.INSTANCE = new ForgePacketHandler().init();
            RegistryAccess.INSTANCE = new ForgeRegistryAccess();
            ConfigFileAccessor.path = FMLPaths.CONFIGDIR.get();
            ConfigFileAccessor.load(); ConfigFileAccessor.save();
            ConfigFileAccessor.loadBiomes(); ConfigFileAccessor.saveBiomes();
            ConfigFileAccessor.loadChestLoot(); ConfigFileAccessor.saveChestLoot();
            SkyBlock.init();
            SkyBlockEvents.INSTANCE = new ForgeSkyBlockEvents().register();
        });
    }

    private void client(final FMLClientSetupEvent event) {
        event.enqueueWork(ClothConfig::screen);
    }

    @SubscribeEvent
    public void register(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.BLOCKS, helper -> Blocks.registerBlocks(helper::register));
        event.register(ForgeRegistries.Keys.ITEMS, helper -> Blocks.registerItems(helper::register));
    }
}
