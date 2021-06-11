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
            newLootPools.put(new ResourceLocation("minecraft", "entities/phantom"), LootPool.lootPool().name("phantom_elytra").setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(Items.ELYTRA)).when(KilledByPlayer.killedByPlayer()).when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.01F, 0.05F)).build());
        }

        if (ConfigHandler.COMMON.witchNetherWart.get()) {
            newLootPools.put(new ResourceLocation("minecraft", "entities/witch"), LootPool.lootPool().name("witch_netherwart").setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(Items.NETHER_WART).apply(SetCount.setCount(RandomValueRange.between(0.0F, 1.0F))).apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 1.0F)))).build());
        }

        if (ConfigHandler.COMMON.enderDragonHead.get()) {
            newLootPools.put(new ResourceLocation("minecraft", "entities/ender_dragon"), LootPool.lootPool().name("enderdragon_dragonhead").setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(Items.DRAGON_HEAD).apply(SetCount.setCount(ConstantRange.exactly(1)))).when(KilledByPlayer.killedByPlayer()).build());
        }

        List<LootEntry> lootEntries = new ArrayList<>();
        if (ConfigHandler.COMMON.piglinNetherrack.get()) {
            lootEntries.add(ItemLootEntry.lootTableItem(Items.NETHERRACK).apply(SetCount.setCount(RandomValueRange.between(8.0F, 16.0F))).setWeight(40).build());
        }
        if (ConfigHandler.COMMON.piglinSoulSoil.get()) {
            lootEntries.add(ItemLootEntry.lootTableItem(Items.SOUL_SOIL).apply(SetCount.setCount(RandomValueRange.between(4.0F, 8.0F))).setWeight(20).build());
        }
        if (ConfigHandler.COMMON.piglinNylium.get()) {
            lootEntries.add(ItemLootEntry.lootTableItem(Items.CRIMSON_NYLIUM).apply(SetCount.setCount(RandomValueRange.between(4.0F, 8.0F))).setWeight(20).build());
            lootEntries.add(ItemLootEntry.lootTableItem(Items.WARPED_NYLIUM).apply(SetCount.setCount(RandomValueRange.between(4.0F, 8.0F))).setWeight(20).build());
        }
        if (ConfigHandler.COMMON.piglinFungus.get()) {
            lootEntries.add(ItemLootEntry.lootTableItem(Items.CRIMSON_FUNGUS).apply(SetCount.setCount(RandomValueRange.between(1.0F, 2.0F))).setWeight(10).build());
            lootEntries.add(ItemLootEntry.lootTableItem(Items.WARPED_FUNGUS).apply(SetCount.setCount(RandomValueRange.between(1.0F, 2.0F))).setWeight(10).build());
        }
        if (ConfigHandler.COMMON.piglinAncientDebris.get()) {
            lootEntries.add(ItemLootEntry.lootTableItem(Items.ANCIENT_DEBRIS).apply(SetCount.setCount(ConstantRange.exactly(1))).setWeight(1).build());
        }
        existingLootPools.put(LootTables.PIGLIN_BARTERING, new LootPoolReference("main", lootEntries));

        if (ConfigHandler.COMMON.heroOfTheVillageClericDiamonds.get()) {
            existingLootPools.put(LootTables.CLERIC_GIFT, new LootPoolReference("main", Collections.singletonList(ItemLootEntry.lootTableItem(Items.DIAMOND).build())));
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