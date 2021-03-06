package com.ant.mcskyblock.setup;

import com.ant.mcskyblock.world.SkyblockWorldType;
import net.minecraftforge.common.world.ForgeWorldType;
import net.minecraftforge.event.RegistryEvent;

import static com.ant.mcskyblock.utils.ResourceLocationHelper.prefix;

// Code adapted from Vaskii's Botania GoG source
// https://github.com/Vazkii/Botania
public class Setup {
    public static void registerWorldType(RegistryEvent.Register<ForgeWorldType> event) {
        event.getRegistry().register(SkyblockWorldType.INSTANCE.setRegistryName(prefix("skyblock")));
    }
}
