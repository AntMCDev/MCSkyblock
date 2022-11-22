package com.ant.mcskyblock.fabric.loot;

import com.ant.mcskyblock.common.SkyBlock;
import com.ant.mcskyblock.common.config.SkyBlockConfig;
import com.ant.mcskyblock.common.loot.LootPoolReference;
import com.ant.mcskyblock.mixin.MixinLootPoolAccessor;
import com.ant.mcskyblock.mixin.MixinLootTableAccessor;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// REQ FABRIC

public class LootTableUtils {
    private static Map<ResourceLocation, LootPool> newLootPools = new HashMap<>();
    private static Map<ResourceLocation, LootPoolReference> existingLootPools = new HashMap<>();

    static {
        if( !SkyBlockConfig.STRUCTURES.GEN_END_CITY) {
            if (SkyBlockConfig.DROPS.PHANTOM_ELYTRA) {
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

        if (SkyBlockConfig.DROPS.DROWNED_GOLD) {
            newLootPools.put(EntityType.DROWNED.getDefaultLootTable(),
                    LootPool.lootPool().setRolls(ConstantValue.exactly(1f))
                            .with(LootItem.lootTableItem(Items.GOLD_INGOT).build())
                            .conditionally(LootItemKilledByPlayerCondition.killedByPlayer().build())
                            .conditionally(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.05f, 0.05f).build())
                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1f))).build());
        }

        if (SkyBlockConfig.DROPS.HUSK_SAND) {
            newLootPools.put(EntityType.HUSK.getDefaultLootTable(),
                    LootPool.lootPool().setRolls(ConstantValue.exactly(1f))
                            .with(LootItem.lootTableItem(Items.SAND).build())
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(0f, 1f)))
                            .apply(LootingEnchantFunction.lootingMultiplier( UniformGenerator.between(0f, 1f) ).build() ).build());
        }

        if (SkyBlockConfig.DROPS.RAVENGER_SCULK_SHRIEKER) {
            newLootPools.put(EntityType.RAVAGER.getDefaultLootTable(),
                    LootPool.lootPool().setRolls(ConstantValue.exactly(1f))
                            .with(LootItem.lootTableItem( com.ant.mcskyblock.common.world.level.block.Blocks.ACTIVE_SCULK_SHRIEKER_BLOCK ).build())
                            .conditionally(LootItemKilledByPlayerCondition.killedByPlayer().build())
                            .conditionally(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.05f, 0.05f).build())
                            .apply( SetItemCountFunction.setCount(ConstantValue.exactly(1f) ) ).build());
        }

        if(SkyBlockConfig.DROPS.BRUTE_ANCIENT_DEBRIS ){
            newLootPools.put(EntityType.PIGLIN_BRUTE.getDefaultLootTable(),
                    LootPool.lootPool().setRolls(ConstantValue.exactly(1f))
                            .with(LootItem.lootTableItem(Items.ANCIENT_DEBRIS).build())
                            .conditionally(LootItemKilledByPlayerCondition.killedByPlayer().build())
                            .conditionally(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.05f, 0.05f).build())
                            .apply( SetItemCountFunction.setCount(ConstantValue.exactly(1f) ) ).build());

        }

        if (SkyBlockConfig.DROPS.TROPICAL_FISH_CORAL) {
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

        if (SkyBlockConfig.DROPS.ENDER_DRAGON_HEAD) {
            newLootPools.put(EntityType.ENDER_DRAGON.getDefaultLootTable(),
                    LootPool.lootPool().setRolls(ConstantValue.exactly(1f))
                            .with(LootItem.lootTableItem( Items.DRAGON_HEAD ).build())
                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1f))).build());
        }

        List<LootPoolEntryContainer> piglinLootTable = new ArrayList<>();
        if (SkyBlockConfig.TRADING.PIGLIN_WEEPING_VINES) {
            piglinLootTable.add(LootItem.lootTableItem(Items.WEEPING_VINES)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(8f, 16f)))
                    .setWeight(40).build());
        }
        if (SkyBlockConfig.TRADING.PIGLIN_NYLIUM) {
            piglinLootTable.add(LootItem.lootTableItem(Items.CRIMSON_NYLIUM)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(4f, 8f)))
                    .setWeight(20).build());

            piglinLootTable.add(LootItem.lootTableItem(Items.WARPED_NYLIUM)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(4f, 8f)))
                    .setWeight(20).build());
        }
        existingLootPools.put(BuiltInLootTables.PIGLIN_BARTERING, new LootPoolReference(0, piglinLootTable));

        List<LootPoolEntryContainer> clericHotVLootTable = new ArrayList<>();
        if (SkyBlockConfig.TRADING.HOTV_CLERIC_DIAMOND && !SkyBlockConfig.STRUCTURES.GEN_END_CITY ) {
            clericHotVLootTable.add(LootItem.lootTableItem(Items.DIAMOND).build());
        }
        if (SkyBlockConfig.TRADING.HOTV_CLERIC_BUDDING_AMETHYST ) {
            clericHotVLootTable.add(LootItem.lootTableItem(Items.BUDDING_AMETHYST).build());
        }
        existingLootPools.put(BuiltInLootTables.CLERIC_GIFT, new LootPoolReference(0, clericHotVLootTable));
    }

    public static void register() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (newLootPools.containsKey(id)) {
                SkyBlock.LOGGER.info("Adding new loot pool for: " + id);
                tableBuilder.pool(newLootPools.get(id));
            }

            if (existingLootPools.containsKey(id)) {
                SkyBlock.LOGGER.info("Modifying existing loot pool [" + existingLootPools.get(id).getPoolIndex()  + "] for: " + id);

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
