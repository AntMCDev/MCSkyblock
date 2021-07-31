package com.ant.mcskyblock.utils;

import com.ant.mcskyblock.MCSkyblock;
import com.ant.mcskyblock.config.ConfigHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.lang.reflect.Field;
import java.util.*;

@Mod.EventBusSubscriber(modid = MCSkyblock.MOD_ID)
public class LootTableUtils {
    private static Map<ResourceLocation, LootPool> newLootPools = new HashMap<>();
    private static Map<ResourceLocation, LootPoolReference> existingLootPools = new HashMap<>();

    static {
        if (ConfigHandler.COMMON.phantomElytra.get()) {
            newLootPools.put(new ResourceLocation("minecraft", "entities/phantom"), LootPool.lootPool().name("phantom_elytra").setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Items.ELYTRA)).when(LootItemKilledByPlayerCondition.killedByPlayer()).when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.01F, 0.05F)).build());
        }

        if (ConfigHandler.COMMON.witchNetherWart.get()) {
            newLootPools.put(new ResourceLocation("minecraft", "entities/witch"), LootPool.lootPool().name("witch_netherwart").setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Items.NETHER_WART).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))).build());
        }

        if (ConfigHandler.COMMON.enderDragonHead.get()) {
            newLootPools.put(new ResourceLocation("minecraft", "entities/ender_dragon"), LootPool.lootPool().name("enderdragon_dragonhead").setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Items.DRAGON_HEAD).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))).when(LootItemKilledByPlayerCondition.killedByPlayer()).build());
        }

        List<LootPoolEntryContainer> lootEntries = new ArrayList<>();
        if (ConfigHandler.COMMON.piglinNetherrack.get()) {
            lootEntries.add(LootItem.lootTableItem(Items.NETHERRACK).apply(SetItemCountFunction.setCount(UniformGenerator.between(8.0F, 16.0F))).setWeight(40).build());
        }
        if (ConfigHandler.COMMON.piglinSoulSoil.get()) {
            lootEntries.add(LootItem.lootTableItem(Items.SOUL_SOIL).apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 8.0F))).setWeight(20).build());
        }
        if (ConfigHandler.COMMON.piglinNylium.get()) {
            lootEntries.add(LootItem.lootTableItem(Items.CRIMSON_NYLIUM).apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 8.0F))).setWeight(20).build());
            lootEntries.add(LootItem.lootTableItem(Items.WARPED_NYLIUM).apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 8.0F))).setWeight(20).build());
        }
        if (ConfigHandler.COMMON.piglinFungus.get()) {
            lootEntries.add(LootItem.lootTableItem(Items.CRIMSON_FUNGUS).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).setWeight(10).build());
            lootEntries.add(LootItem.lootTableItem(Items.WARPED_FUNGUS).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).setWeight(10).build());
        }
        if (ConfigHandler.COMMON.piglinAncientDebris.get()) {
            lootEntries.add(LootItem.lootTableItem(Items.ANCIENT_DEBRIS).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))).setWeight(1).build());
        }
        existingLootPools.put(BuiltInLootTables.PIGLIN_BARTERING, new LootPoolReference("main", lootEntries));

        if (ConfigHandler.COMMON.heroOfTheVillageClericDiamonds.get()) {
            existingLootPools.put(BuiltInLootTables.CLERIC_GIFT, new LootPoolReference("main", Collections.singletonList(LootItem.lootTableItem(Items.DIAMOND).build())));
        }
    }

    @SubscribeEvent
    public static void onLootTableLoadEvent(LootTableLoadEvent event) {
        if (newLootPools.containsKey(event.getName())) {
            event.getTable().addPool(newLootPools.get(event.getName()));
        }

        if (existingLootPools.containsKey(event.getName())) {
            String poolName = existingLootPools.get(event.getName()).getPoolName();
            for (LootPoolEntryContainer l : existingLootPools.get(event.getName()).getEntries()) {
                ((List<LootPoolEntryContainer>) ObfuscationReflectionHelper.getPrivateValue(LootPool.class, event.getTable().getPool(poolName), "entries")).add(l);
            }
        }
    }
}