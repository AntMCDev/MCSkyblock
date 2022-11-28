package com.ant.mcskyblock.fabric.config;

import com.ant.mcskyblock.common.SkyBlock;
import com.ant.mcskyblock.common.config.Config;
import com.ant.mcskyblock.common.config.ConfigFileAccessor;
import com.ant.mcskyblock.common.network.PacketHandler;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * [COMMON] CONFIG GUI - This is the ClothConfig-specific GUI wrapper around the generic Config class
 */
@Environment(EnvType.CLIENT)
public class ClothConfig {
    public static ConfigBuilder getBuilder(Path path) {

        ConfigBuilder builder = ConfigBuilder.create()
                .setTitle(Component.translatable("config." + SkyBlock.MOD_NAME + ".title"))
                .setDefaultBackgroundTexture(new ResourceLocation("minecraft:textures/block/dirt.png"))
                .setDoesConfirmSave(false)
                .transparentBackground();
        builder.setGlobalized(false);
        builder.setGlobalizedExpanded(false);
        builder.setSavingRunnable(() -> {
            ConfigFileAccessor.save();
            PacketHandler.INSTANCE.sendToServer(PacketHandler.CONFIG_PACKET.getIdentifier(), Config.INSTANCE.toBytes());
        });

        ConfigFileAccessor.load();

        ConfigCategory defaultCategory = builder.getOrCreateCategory(Component.translatable("config." + SkyBlock.MOD_NAME + ".category.default"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        defaultCategory.addEntry(entryBuilder.startTextDescription(Component.translatable("config." + SkyBlock.MOD_NAME + ".description")).build());
        List<Class<?>> classes = Arrays.stream(Config.class.getDeclaredClasses()).toList();
        for (Field field : Config.class.getFields()) {
            if (classes.stream().anyMatch(c -> c.isAssignableFrom(field.getType()) && !c.isEnum())) {
                Class<?> clazz = classes.stream().filter(c -> c.isAssignableFrom(field.getType())).findFirst().orElseThrow();
                Optional<Field> configCategoryField = Arrays.stream(Config.class.getFields()).filter(f -> f.getType().isAssignableFrom(clazz)).findFirst();
                if (configCategoryField.isPresent()) {
                    try {
                        Object configCategoryObject = configCategoryField.get().get(Config.INSTANCE);
                        String categoryString = clazz.getSimpleName().substring(0, 1).toLowerCase(Locale.ROOT) + clazz.getSimpleName().substring(1);
                        ConfigCategory category = builder.getOrCreateCategory(Component.translatable("config." + SkyBlock.MOD_NAME + ".category." + categoryString));
                        for (Field field2 : configCategoryObject.getClass().getFields()) {
                            AbstractConfigListEntry<?> entry = fieldToEntry(entryBuilder, field2, configCategoryObject, categoryString);
                            if (entry != null) { category.addEntry(entry); }
                        }
                    } catch (IllegalAccessException ignore) { }
                }
            } else {
                AbstractConfigListEntry<?> entry = fieldToEntry(entryBuilder, field, Config.INSTANCE, "default");
                if (entry != null) { defaultCategory.addEntry(entry); }
            }
        }

        return builder;
    }

    private static AbstractConfigListEntry<?> fieldToEntry(ConfigEntryBuilder entryBuilder, Field field, Object obj, String category) {
        try {
            Component fieldComponent = Component.translatable("config." + SkyBlock.MOD_NAME + ".option." + category + "." + field.getName());
            if (boolean.class.isAssignableFrom(field.getType())) {
                Boolean val = field.getBoolean(obj);
                return entryBuilder.startBooleanToggle(fieldComponent, val).setDefaultValue(val).setSaveConsumer(v -> { try { field.setBoolean(obj, v); } catch (IllegalAccessException ignored) { } } ).build();
            } else if (int.class.isAssignableFrom(field.getType())) {
                Integer val = field.getInt(obj);
                IntData intData = getIntData(field);
                if (intData.isSlider) {
                    return entryBuilder.startIntSlider(fieldComponent, val, intData.min, intData.max).setDefaultValue(val).setSaveConsumer(v -> { try { field.setInt(obj, v); } catch (IllegalAccessException ignored) { } } ).build();
                } else {
                    return entryBuilder.startIntField(fieldComponent, val).setDefaultValue(val).setSaveConsumer(v -> { try { field.setInt(obj, v); } catch (IllegalAccessException ignored) { } } ).setMin(intData.min).setMax(intData.max).build();
                }
            } else if (field.getType().isArray() && String.class.isAssignableFrom(field.getType().getComponentType())) {
                List<String> val = Arrays.stream(((String[])field.get(obj))).toList();
                return entryBuilder.startStrList(fieldComponent, val).setDefaultValue(val).setSaveConsumer(v -> { try { field.set(obj, v.toArray(new String[0])); } catch (IllegalAccessException ignored) { } } ).build();
            } else if (field.getType().isEnum()) {
                Class<Enum<?>> eclazz = (Class<Enum<?>>)field.getType();
                Enum<?> val = (Enum<?>)field.get(obj);
                return entryBuilder.startEnumSelector(fieldComponent, eclazz, val).setSaveConsumer(v -> { try { field.set(obj, v); } catch (IllegalAccessException ignored) { } } ).build();
            }
        } catch (IllegalAccessException ignore) { }
        return null;
    }

    private record IntData(boolean isSlider, Integer min, Integer max) {}

    private static IntData getIntData(Field f) {
        switch (f.getName()) {
            case "MAIN_ISLAND_RADIUS" -> { return new IntData(true, 1, 10); }
            case "SUB_ISLAND_RADIUS" -> { return new IntData(true, 1, 7); }
            case "MAIN_ISLAND_DEPTH", "SUB_ISLAND_DEPTH" -> { return new IntData(true, 1, 5); }
            case "SUB_ISLAND_DISTANCE" -> { return new IntData(false, 32, null); }
        }
        return new IntData(false, null, null);
    }
}
