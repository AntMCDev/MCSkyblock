package com.ant.mcskyblock.common.config;

import com.ant.mcskyblock.common.SkyBlock;
import com.ant.mcskyblock.common.registry.RegistryAccess;
import com.google.gson.*;
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
                    if (RegistryAccess.INSTANCE.getBiome(biome) != null) {
                        BiomeIslandConfig.Island island = new BiomeIslandConfig.Island();

                        JsonElement baseElem = obj.get("base");
                        if (baseElem != null) {
                            ResourceLocation base = new ResourceLocation(baseElem.getAsString());
                            if (RegistryAccess.INSTANCE.getBlock(base) != null) {
                                island.base = base;
                            }
                        }

                        JsonElement baseFluid = obj.get("fluid");
                        if (baseFluid != null) {
                            ResourceLocation fluid = new ResourceLocation(obj.get("fluid").getAsString());
                            if (RegistryAccess.INSTANCE.getBlock(fluid) != null) {
                                island.fluid = fluid;
                            }
                        }

                        JsonElement baseAccessory = obj.get("accessory");
                        if (baseAccessory != null) {
                            ResourceLocation accessory = new ResourceLocation(obj.get("accessory").getAsString());
                            if (RegistryAccess.INSTANCE.getBlock(accessory) != null) {
                                island.accessory = accessory;
                            }
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
