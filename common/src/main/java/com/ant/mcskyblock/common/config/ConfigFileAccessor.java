package com.ant.mcskyblock.common.config;

import com.ant.mcskyblock.common.SkyBlock;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ConfigFileAccessor {
    public static Path path = null;

    public static void save() {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final Path configPath = path.resolve(SkyBlock.MOD_NAME + ".json");

        try {
            Config.INSTANCE.updateToPreset();
            Files.createDirectories(configPath.getParent());
            BufferedWriter writer = Files.newBufferedWriter(configPath);
            gson.toJson(Config.INSTANCE, writer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void load() {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final Path configPath = path.resolve(SkyBlock.MOD_NAME + ".json");

        if (Files.exists(configPath)) {
            try {
                BufferedReader reader = Files.newBufferedReader(configPath);
                Config.INSTANCE = gson.fromJson(reader, Config.class);
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void saveBiomes() {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final Path configPath = path.resolve(SkyBlock.MOD_NAME + "-biomes.json");

        try {
            Files.createDirectories(configPath.getParent());
            BufferedWriter writer = Files.newBufferedWriter(configPath);
            JsonArray arr = new JsonArray();
            for (ResourceLocation rs : BiomeIslandConfig.SETTINGS.keySet()) {
                JsonObject obj = new JsonObject();
                BiomeIslandConfig.Island island = BiomeIslandConfig.SETTINGS.get(rs);
                obj.addProperty("biome", rs.toString());
                if (island.base != null) { obj.addProperty("base", island.base.toString()); }
                if (island.fluid != null) { obj.addProperty("fluid", island.fluid.toString()); }
                if (island.accessory != null) { obj.addProperty("accessory", island.accessory.toString()); }
                arr.add(obj);
            }
            gson.toJson(arr, writer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadBiomes() {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final Path configPath = path.resolve(SkyBlock.MOD_NAME + "-biomes.json");

        if (Files.exists(configPath)) {
            try {
                BufferedReader reader = Files.newBufferedReader(configPath);
                JsonArray arr = gson.fromJson(reader, JsonArray.class);
                arr.forEach(j -> {
                    JsonObject obj = (JsonObject)j;
                    ResourceLocation biome = new ResourceLocation(obj.get("biome").getAsString());
                    if (BuiltinRegistries.BIOME.containsKey(biome)) {
                        BiomeIslandConfig.Island island = new BiomeIslandConfig.Island();
                        ResourceLocation base = new ResourceLocation(obj.get("base").getAsString());
                        if (Registry.BLOCK.containsKey(base)) {
                            island.base = base;
                        }
                        ResourceLocation fluid = new ResourceLocation(obj.get("fluid").getAsString());
                        if (Registry.BLOCK.containsKey(fluid)) {
                            island.fluid = fluid;
                        }
                        ResourceLocation accessory = new ResourceLocation(obj.get("accessory").getAsString());
                        if (Registry.BLOCK.containsKey(accessory)) {
                            island.accessory = accessory;
                        }
                        BiomeIslandConfig.SETTINGS.put(biome, island);
                    }
                });
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
