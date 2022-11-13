package com.ant.mcskyblock.fabric.loot;

import com.ant.mcskyblock.common.MCSkyBlock;

import com.ant.mcskyblock.common.config.SkyBlockConfigManager;
import com.ant.mcskyblock.common.loot.LootPoolReference;

import com.ant.mcskyblock.mixin.MixinLootPoolAccessor;
import com.ant.mcskyblock.mixin.MixinLootTableAccessor;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.feature.GeodeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
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

import java.util.*;

import static java.util.Map.entry;
// REQ FABRIC

public class LootTableUtils {
    private static Map<ResourceLocation, LootPool> newLootPools = new HashMap<>();
    private static Map<ResourceLocation, LootPoolReference> existingLootPools = new HashMap<>();

    static {

        if( !SkyBlockConfigManager.endCities() ) {
            if (SkyBlockConfigManager.phantomsDropElytra()) {
                newLootPools.put(EntityType.PHANTOM.getDefaultLootTable(),
                        LootPool.lootPool().setRolls(ConstantValue.exactly(1f))
                                .with(LootItem.lootTableItem(Items.ELYTRA).build())
                                .conditionally(LootItemKilledByPlayerCondition.killedByPlayer().build())
                                .conditionally(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.01f, 0.05f).build()).build());

                newLootPools.put(EntityType.PHANTOM.getDefaultLootTable(),
                        LootPool.lootPool().setRolls(ConstantValue.exactly(1f))
                                .with(LootItem.lootTableItem(Items.ELYTRA).build())
                                .conditionally(LootItemKilledByPlayerCondition.killedByPlayer().build())
                                .conditionally(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.01f, 0.05f).build()).build());
            }
        }

        if( !SkyBlockConfigManager.fortress() ) {
            if (SkyBlockConfigManager.witchDropsNetherWart()) {
                newLootPools.put(EntityType.WITCH.getDefaultLootTable(),
                        LootPool.lootPool().setRolls(ConstantValue.exactly(1f))
                                .with(LootItem.lootTableItem(Items.NETHER_WART).build())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0f, 1f)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0f, 1f)).build()).build());
            }
        }

        // husk always drop ?
        if (SkyBlockConfigManager.huskDropSand()) {
            newLootPools.put(EntityType.HUSK.getDefaultLootTable(),
                    LootPool.lootPool().setRolls(ConstantValue.exactly(1f))
                            .with(LootItem.lootTableItem( Items.SAND ).build())
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(0f, 1f)))
                            .apply(LootingEnchantFunction.lootingMultiplier( UniformGenerator.between(0f, 1f) ).build() ).build());
        }

        if (SkyBlockConfigManager.witherDropAncientDebris()) {
            newLootPools.put(EntityType.WITHER.getDefaultLootTable(),
                    LootPool.lootPool().setRolls(ConstantValue.exactly(1f))
                            .with(LootItem.lootTableItem(Items.ANCIENT_DEBRIS).build())
                            .conditionally(LootItemKilledByPlayerCondition.killedByPlayer().build())
                            .conditionally(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.05f, 0.05f).build())
                            .apply( SetItemCountFunction.setCount(ConstantValue.exactly(1f) ) ).build());
        }

        if (SkyBlockConfigManager.tropicalFishDropCoral()) {
            newLootPools.put(EntityType.TROPICAL_FISH.getDefaultLootTable(),
                    LootPool.lootPool().setRolls(ConstantValue.exactly(1f))
                            .with(LootItem.lootTableItem( Items.FIRE_CORAL_FAN   ).build())
                            .with(LootItem.lootTableItem( Items.TUBE_CORAL_FAN   ).build())
                            .with(LootItem.lootTableItem( Items.BRAIN_CORAL_FAN  ).build())
                            .with(LootItem.lootTableItem( Items.BUBBLE_CORAL_FAN ).build())
                            .with(LootItem.lootTableItem( Items.FIRE_CORAL_FAN   ).build())
                            .with(LootItem.lootTableItem( Items.HORN_CORAL_FAN   ).build())
                            .with(LootItem.lootTableItem( Items.TUBE_CORAL       ).build())
                            .with(LootItem.lootTableItem( Items.BRAIN_CORAL      ).build())
                            .with(LootItem.lootTableItem( Items.BUBBLE_CORAL     ).build())
                            .with(LootItem.lootTableItem( Items.FIRE_CORAL       ).build())
                            .with(LootItem.lootTableItem( Items.HORN_CORAL       ).build())
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(0f, 1f)))
                            .apply(LootingEnchantFunction.lootingMultiplier( UniformGenerator.between(0.7f, 1.0f) ).build() ).build());
        }

        if (SkyBlockConfigManager.enderDragonDropsHead()) {
            newLootPools.put(EntityType.ENDER_DRAGON.getDefaultLootTable(),
                    LootPool.lootPool().setRolls(ConstantValue.exactly(1f))
                            .with(LootItem.lootTableItem( Items.DRAGON_HEAD ).build())
                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1f))).build());
        }

        /*
        if (SkyBlockConfigManager.drownDropsGold()) {
            newLootPools.put(EntityType.DROWNED.getDefaultLootTable(),
                    LootPool.lootPool().setRolls(ConstantValue.exactly(1f))
                            .with(LootItem.lootTableItem(Items.GOLD_INGOT).build())
                            .conditionally(LootItemKilledByPlayerCondition.killedByPlayer().build())
                            .conditionally(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.05f, 0.05f).build())
                            .apply( SetItemCountFunction.setCount(ConstantValue.exactly(1f) ) ).build());
        }
        */

        List<LootPoolEntryContainer> piglinLootTable = new ArrayList<>();
        if (SkyBlockConfigManager.piglinNetherrack()) {
            piglinLootTable.add(LootItem.lootTableItem(Items.NETHERRACK)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(8f, 16f)))
                    .setWeight(40).build());
        }
        if (SkyBlockConfigManager.piglinNylium()) {
            piglinLootTable.add(LootItem.lootTableItem(Items.CRIMSON_NYLIUM)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(4f, 8f)))
                    .setWeight(20).build());

            piglinLootTable.add(LootItem.lootTableItem(Items.WARPED_NYLIUM)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(4f, 8f)))
                    .setWeight(20).build());
        }
        if (SkyBlockConfigManager.piglinAncientDebris() && !SkyBlockConfigManager.witherDropAncientDebris() ) {
            piglinLootTable.add(LootItem.lootTableItem(Items.ANCIENT_DEBRIS)
                    .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1f)))
                    .setWeight(1).build());
        }
        existingLootPools.put(BuiltInLootTables.PIGLIN_BARTERING, new LootPoolReference(0, piglinLootTable));

        List<LootPoolEntryContainer> clericHotVLootTable = new ArrayList<>();
        if (SkyBlockConfigManager.hotvClericDiamond() && !SkyBlockConfigManager.endCities() ) {
            clericHotVLootTable.add(LootItem.lootTableItem(Items.DIAMOND).build());
        }
        if ( SkyBlockConfigManager.hotvClericBuddingAmethyst() ) {
            clericHotVLootTable.add(LootItem.lootTableItem(Items.BUDDING_AMETHYST).build());
        }
        existingLootPools.put(BuiltInLootTables.CLERIC_GIFT, new LootPoolReference(0, clericHotVLootTable));
    }


    public static void register() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (newLootPools.containsKey(id)) {
                MCSkyBlock.LOGGER.info("Adding new loot pool for: " + id);
                tableBuilder.pool(newLootPools.get(id));
            }

            if (existingLootPools.containsKey(id)) {
                MCSkyBlock.LOGGER.info("Modifying existing loot pool [" + existingLootPools.get(id).getPoolIndex()  + "] for: " + id);

                LootPoolEntryContainer[] entries = ((MixinLootPoolAccessor)((MixinLootTableAccessor)lootManager.get(id)).getPools()[existingLootPools.get(id).getPoolIndex()]).getEntries();
                LootPoolEntryContainer[] newEntries = new LootPoolEntryContainer[entries.length + existingLootPools.get(id).getEntries().size()];

                System.arraycopy(entries, 0, newEntries, 0, entries.length);

                for (int i = 0; i < existingLootPools.get(id).getEntries().size(); i++) {
                    newEntries[entries.length + i] = existingLootPools.get(id).getEntries().get(i);
                }

                ((MixinLootPoolAccessor)((MixinLootTableAccessor)lootManager.get(id)).getPools()[existingLootPools.get(id).getPoolIndex()]).setEntries(newEntries);
            }
        });
    }
}
