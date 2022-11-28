package com.ant.mcskyblock.common.config;

import com.ant.mcskyblock.common.SkyBlock;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
}
