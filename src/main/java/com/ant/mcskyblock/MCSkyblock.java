package com.ant.mcskyblock;

import com.ant.mcskyblock.config.ConfigHandler;
import com.ant.mcskyblock.network.PacketHandler;
import com.ant.mcskyblock.setup.Setup;
import com.ant.mcskyblock.utils.SpawnUtils;
import com.ant.mcskyblock.world.SkyblockChunkGenerator;
import com.ant.mcskyblock.world.SkyblockWorldEvents;
import com.ant.mcskyblock.world.SkyblockWorldType;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.ForgeWorldType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(MCSkyblock.MOD_ID)
public class MCSkyblock
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "mcskyblock";

    public MCSkyblock() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.COMMON_SPEC);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(ForgeWorldType.class, Setup::registerWorldType);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        PacketHandler.init();

        MinecraftForge.EVENT_BUS.addListener(SkyblockWorldEvents::syncStatus);
        MinecraftForge.EVENT_BUS.addListener(SkyblockWorldEvents::onPlayerJoin);
        MinecraftForge.EVENT_BUS.addListener(SpawnUtils::onBiomeLoadingEvent);

        event.enqueueWork(() -> {
            SkyblockChunkGenerator.init();
        });
    }
}
