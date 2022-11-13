package com.ant.mcskyblock.fabric._build;

import com.ant.mcskyblock.common.world.level.levelgen.presets.SkyBlockWorldPreset;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.tags.WorldPresetTags;

// REQ FABRIC
public class SkyblockDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.addProvider(
                new FabricTagProvider.DynamicRegistryTagProvider<>(fabricDataGenerator, BuiltinRegistries.WORLD_PRESET.key() ) {
            @Override
            protected void generateTags() {
                getOrCreateTagBuilder(WorldPresetTags.NORMAL).add( SkyBlockWorldPreset.SB_PRESET_KEY );
            }
        });
    }
}
