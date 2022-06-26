package com.ant.mcskyblock._build;

import com.ant.mcskyblock.skyblock.SkyblockWorldPreset;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.tag.WorldPresetTags;
import net.minecraft.util.registry.BuiltinRegistries;

public class SkyblockDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.addProvider(new FabricTagProvider.DynamicRegistryTagProvider<>(fabricDataGenerator, BuiltinRegistries.WORLD_PRESET.getKey()) {
            @Override
            protected void generateTags() {
                getOrCreateTagBuilder(WorldPresetTags.NORMAL).add(SkyblockWorldPreset.SKYBLOCK);
            }
        });
    }
}
