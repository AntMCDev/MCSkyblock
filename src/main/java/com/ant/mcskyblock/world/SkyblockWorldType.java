package com.ant.mcskyblock.world;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.WorldPreset;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;

public class SkyblockWorldType {
    public static final RegistryKey<ChunkGeneratorSettings> MC_SKYBLOCK_CHUNK_GEN = RegistryKey.of(Registry.CHUNK_GENERATOR_SETTINGS_KEY, new Identifier("mcskyblock"));
    public static final RegistryKey<WorldPreset> MC_SKYBLOCK_PRESET = RegistryKey.of(Registry.WORLD_PRESET_KEY, new Identifier("mcskyblock"));

    public SkyblockWorldType(String string) {

    }
}
