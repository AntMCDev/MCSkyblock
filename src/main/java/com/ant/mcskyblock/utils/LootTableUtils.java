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

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = MCSkyblock.MOD_ID)
public class LootTableUtils {
    private static Map<ResourceLocation, LootPool> lootPools = new HashMap<>();

    static {
        if (ConfigHandler.COMMON.phantomElytra.get()) {
            lootPools.put(new ResourceLocation("minecraft", "entities/phantom"), LootPool.builder().name("phantom_elytra").rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.ELYTRA)).acceptCondition(KilledByPlayer.builder()).acceptCondition(RandomChanceWithLooting.builder(0.01F, 0.05F)).build());
        }

        if (ConfigHandler.COMMON.witchNetherWart.get()) {
            lootPools.put(new ResourceLocation("minecraft", "entities/witch"), LootPool.builder().name("witch_netherwart").rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.NETHER_WART).acceptFunction(SetCount.builder(RandomValueRange.of(0.0F, 1.0F))).acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F)))).build());
        }

        if (ConfigHandler.COMMON.enderDragonHead.get()) {
            lootPools.put(new ResourceLocation("minecraft", "entities/ender_dragon"), LootPool.builder().name("enderdragon_dragonhead").rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.DRAGON_HEAD).acceptFunction(SetCount.builder(ConstantRange.of(1)))).acceptCondition(KilledByPlayer.builder()).build());
        }
    }

    @SubscribeEvent
    public static void onLootTableLoadEvent(LootTableLoadEvent event) {
        if (lootPools.containsKey(event.getName())) {
            event.getTable().addPool(lootPools.get(event.getName()));
        }
    }
}
