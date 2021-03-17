package com.ant.mcskyblock.utils;

import com.ant.mcskyblock.MCSkyblock;
import com.ant.mcskyblock.config.ConfigHandler;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.KilledByPlayer;
import net.minecraft.loot.conditions.RandomChanceWithLooting;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;
import java.util.*;

@Mod.EventBusSubscriber(modid = MCSkyblock.MOD_ID)
public class LootTableUtils {
    private static Map<ResourceLocation, LootPool> newLootPools = new HashMap<>();
    private static Map<ResourceLocation, LootPoolReference> existingLootPools = new HashMap<>();

    static {
        if (ConfigHandler.COMMON.phantomElytra.get()) {
            newLootPools.put(new ResourceLocation("minecraft", "entities/phantom"), LootPool.builder().name("phantom_elytra").rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.ELYTRA)).acceptCondition(KilledByPlayer.builder()).acceptCondition(RandomChanceWithLooting.builder(0.01F, 0.05F)).build());
        }

        if (ConfigHandler.COMMON.witchNetherWart.get()) {
            newLootPools.put(new ResourceLocation("minecraft", "entities/witch"), LootPool.builder().name("witch_netherwart").rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.NETHER_WART).acceptFunction(SetCount.builder(RandomValueRange.of(0.0F, 1.0F))).acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F)))).build());
        }

        if (ConfigHandler.COMMON.enderDragonHead.get()) {
            newLootPools.put(new ResourceLocation("minecraft", "entities/ender_dragon"), LootPool.builder().name("enderdragon_dragonhead").rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.DRAGON_HEAD).acceptFunction(SetCount.builder(ConstantRange.of(1)))).acceptCondition(KilledByPlayer.builder()).build());
        }

        List<LootEntry> lootEntries = new ArrayList<>();
        if (ConfigHandler.COMMON.piglinNetherrack.get()) {
            lootEntries.add(ItemLootEntry.builder(Items.NETHERRACK).acceptFunction(SetCount.builder(RandomValueRange.of(8.0F, 16.0F))).weight(40).build());
        }
        if (ConfigHandler.COMMON.piglinNylium.get()) {
            lootEntries.add(ItemLootEntry.builder(Items.CRIMSON_NYLIUM).acceptFunction(SetCount.builder(RandomValueRange.of(4.0F, 8.0F))).weight(20).build());
            lootEntries.add(ItemLootEntry.builder(Items.WARPED_NYLIUM).acceptFunction(SetCount.builder(RandomValueRange.of(4.0F, 8.0F))).weight(20).build());
        }
        if (ConfigHandler.COMMON.piglinFungus.get()) {
            lootEntries.add(ItemLootEntry.builder(Items.CRIMSON_FUNGUS).acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F))).weight(10).build());
            lootEntries.add(ItemLootEntry.builder(Items.WARPED_FUNGUS).acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F))).weight(10).build());
        }
        if (ConfigHandler.COMMON.piglinAncientDebris.get()) {
            lootEntries.add(ItemLootEntry.builder(Items.ANCIENT_DEBRIS).acceptFunction(SetCount.builder(ConstantRange.of(1))).weight(1).build());
        }
        existingLootPools.put(LootTables.PIGLIN_BARTERING, new LootPoolReference("main", lootEntries));

        if (ConfigHandler.COMMON.heroOfTheVillageClericDiamonds.get()) {
            existingLootPools.put(LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_CLERIC_GIFT, new LootPoolReference("main", Collections.singletonList(ItemLootEntry.builder(Items.DIAMOND).build())));
        }
    }

    @SubscribeEvent
    public static void onLootTableLoadEvent(LootTableLoadEvent event) {
        if (newLootPools.containsKey(event.getName())) {
            event.getTable().addPool(newLootPools.get(event.getName()));
        }

        if (existingLootPools.containsKey(event.getName())) {
            String poolName = existingLootPools.get(event.getName()).getPoolName();
            for (LootEntry l : existingLootPools.get(event.getName()).getEntries()) {
                ((List<LootEntry>)ObfuscationReflectionHelper.getPrivateValue(LootPool.class, event.getTable().getPool(poolName), "field_186453_a")).add(l);
            }
        }
    }
}