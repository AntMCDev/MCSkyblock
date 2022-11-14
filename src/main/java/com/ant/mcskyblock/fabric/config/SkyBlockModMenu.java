package com.ant.mcskyblock.fabric.config;

import com.ant.mcskyblock.common.config.SkyBlockConfigs;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;


/**
 *
 */
//FIXME document after 3.x is in beta
@Environment(EnvType.CLIENT)
public class SkyBlockModMenu  implements ModMenuApi {
    /**
     *
     * @return
     */
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoConfig.getConfigScreen(SkyBlockConfigs.class, parent).get();
    }
}