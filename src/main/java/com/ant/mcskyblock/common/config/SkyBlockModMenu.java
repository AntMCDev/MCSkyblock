package com.ant.mcskyblock.common.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;


// REQ FABRIC
@Environment(EnvType.CLIENT)
public class SkyBlockModMenu  implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoConfig.getConfigScreen(SkyBlockConfigs.class, parent).get();
    }
}