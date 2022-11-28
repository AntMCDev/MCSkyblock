package com.ant.mcskyblock.forge;

import com.ant.mcskyblock.common.SkyBlock;

import com.ant.mcskyblock.common.config.ConfigFileAccessor;
import com.ant.mcskyblock.common.network.PacketHandler;
import com.ant.mcskyblock.common.world.events.SkyBlockEvents;
import com.ant.mcskyblock.forge.config.ClothConfig;
import com.ant.mcskyblock.forge.loot.ForgeLootTableUtils;
import com.ant.mcskyblock.forge.network.ForgePacketHandler;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.logging.log4j.Level;


@Mod(SkyBlock.MOD_NAME)
@Mod.EventBusSubscriber(modid = SkyBlock.MOD_NAME, bus = Mod.EventBusSubscriber.Bus.MOD)
public  class ForgeMCSkyBlock {
    public ForgeMCSkyBlock(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        SkyBlock.LOGGER.log(Level.INFO, "HELLO FORGE");

        bus.addListener(this::setup);
        bus.addListener(this::client);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> ClothConfig.getBuilder().build()));

            ConfigFileAccessor.path = FMLPaths.CONFIGDIR.get();
            ConfigFileAccessor.load();
            ConfigFileAccessor.save();
            SkyBlock.init();
            ForgeLootTableUtils.register();
            SkyBlockEvents.INSTANCE = new ForgeSkyBlockEvents().register();
        });
    }

    private void client(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            PacketHandler.INSTANCE = new ForgePacketHandler().init().registerClientListener();
        });
    }

    @SubscribeEvent
    public void register(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.BLOCKS, helper -> SkyBlock.registerBlocks());
    }
}
