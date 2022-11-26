package com.ant.mcskyblock.common.loot;

import com.ant.mcskyblock.common.SkyBlock;
import com.ant.mcskyblock.common.config.Config;
import com.ant.mcskyblock.fabric.mixin.MixinLootPoolAccessor;
import com.ant.mcskyblock.fabric.mixin.MixinLootTableAccessor;
import net.minecraft.data.loot.FishingLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
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

public class LootTableUtils {
    public static Map<ResourceLocation, LootPool> newLootPools = new HashMap<>();
    public static Map<ResourceLocation, LootPoolReference> existingLootPools = new HashMap<>();

    static {
        if( !Config.INSTANCE.structures.GEN_END_CITY) {
            if (Config.INSTANCE.drops.PHANTOM_ELYTRA) {
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

        if (Config.INSTANCE.drops.DROWNED_GOLD) {
            newLootPools.put(EntityType.DROWNED.getDefaultLootTable(),
                    LootPool.lootPool().setRolls(ConstantValue.exactly(1f))
                            .with(LootItem.lootTableItem(Items.GOLD_INGOT).build())
                            .conditionally(LootItemKilledByPlayerCondition.killedByPlayer().build())
                            .conditionally(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.05f, 0.05f).build())
                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1f))).build());
        }

        if (Config.INSTANCE.drops.HUSK_SAND) {
            newLootPools.put(EntityType.HUSK.getDefaultLootTable(),
                    LootPool.lootPool().setRolls(ConstantValue.exactly(1f))
                            .with(LootItem.lootTableItem(Items.SAND).build())
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(0f, 1f)))
                            .apply(LootingEnchantFunction.lootingMultiplier( UniformGenerator.between(0f, 1f) ).build() ).build());
        }

        if (Config.INSTANCE.drops.RAVENGER_SCULK_SHRIEKER) {
            newLootPools.put(EntityType.RAVAGER.getDefaultLootTable(),
                    LootPool.lootPool().setRolls(ConstantValue.exactly(1f))
                            .with(LootItem.lootTableItem( com.ant.mcskyblock.common.world.level.block.Blocks.ACTIVE_SCULK_SHRIEKER_BLOCK ).build())
                            .conditionally(LootItemKilledByPlayerCondition.killedByPlayer().build())
                            .conditionally(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.05f, 0.05f).build())
                            .apply( SetItemCountFunction.setCount(ConstantValue.exactly(1f) ) ).build());
        }

        if(Config.INSTANCE.drops.BRUTE_ANCIENT_DEBRIS ){
            newLootPools.put(EntityType.PIGLIN_BRUTE.getDefaultLootTable(),
                    LootPool.lootPool().setRolls(ConstantValue.exactly(1f))
                            .with(LootItem.lootTableItem(Items.ANCIENT_DEBRIS).build())
                            .conditionally(LootItemKilledByPlayerCondition.killedByPlayer().build())
                            .conditionally(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.05f, 0.05f).build())
                            .apply( SetItemCountFunction.setCount(ConstantValue.exactly(1f) ) ).build());

        }

        if (Config.INSTANCE.drops.TROPICAL_FISH_CORAL) {
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

        if (Config.INSTANCE.drops.ENDER_DRAGON_HEAD) {
            newLootPools.put(EntityType.ENDER_DRAGON.getDefaultLootTable(),
                    LootPool.lootPool().setRolls(ConstantValue.exactly(1f))
                            .with(LootItem.lootTableItem( Items.DRAGON_HEAD ).build())
                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1f))).build());
        }

        List<LootPoolEntryContainer> piglinLootTable = new ArrayList<>();
        if (Config.INSTANCE.trading.PIGLIN_WEEPING_VINES) {
            piglinLootTable.add(LootItem.lootTableItem(Items.WEEPING_VINES)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(8f, 16f)))
                    .setWeight(40).build());
        }
        if (Config.INSTANCE.trading.PIGLIN_NYLIUM) {
            piglinLootTable.add(LootItem.lootTableItem(Items.CRIMSON_NYLIUM)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(4f, 8f)))
                    .setWeight(20).build());

            piglinLootTable.add(LootItem.lootTableItem(Items.WARPED_NYLIUM)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(4f, 8f)))
                    .setWeight(20).build());
        }
        existingLootPools.put(BuiltInLootTables.PIGLIN_BARTERING, new LootPoolReference(0, piglinLootTable));


        List<LootPoolEntryContainer> fishingLootTable = new ArrayList<>();
        if (Config.INSTANCE.drops.FISHING_COCO_BEANS) {
            fishingLootTable.add(LootItem.lootTableItem(Items.COCOA_BEANS)
                    .when(FishingLoot.IN_JUNGLE.or(FishingLoot.IN_BAMBOO_JUNGLE.or(FishingLoot.IN_SPARSE_JUNGLE)))
                    .setWeight(10).build()
            );
        }
        existingLootPools.put(BuiltInLootTables.FISHING_JUNK, new LootPoolReference(0,fishingLootTable));



        List<LootPoolEntryContainer> clericHotVLootTable = new ArrayList<>();
        if (Config.INSTANCE.trading.HOTV_CLERIC_DIAMOND && !Config.INSTANCE.structures.GEN_END_CITY ) {
            clericHotVLootTable.add(LootItem.lootTableItem(Items.DIAMOND).build());
        }
        if (Config.INSTANCE.trading.HOTV_CLERIC_BUDDING_AMETHYST && !Config.INSTANCE.structures.GEN_GEODES) {
            clericHotVLootTable.add(LootItem.lootTableItem(Items.BUDDING_AMETHYST).build());
        }
        existingLootPools.put(BuiltInLootTables.CLERIC_GIFT, new LootPoolReference(0, clericHotVLootTable));
    }

    public static void register(LootTables lootManager, ResourceLocation id, LootTable.Builder tableBuilder) {
        if (LootTableUtils.newLootPools.containsKey(id)) {
            tableBuilder.pool(LootTableUtils.newLootPools.get(id));
        }

        if (LootTableUtils.existingLootPools.containsKey(id)) {
            LootPoolEntryContainer[] entries = ((MixinLootPoolAccessor)((MixinLootTableAccessor)lootManager.get(id)).getPools()[LootTableUtils.existingLootPools.get(id).getPoolIndex()]).getEntries();
            LootPoolEntryContainer[] newEntries = new LootPoolEntryContainer[entries.length + LootTableUtils.existingLootPools.get(id).getEntries().size()];

            System.arraycopy(entries, 0, newEntries, 0, entries.length);

            for (int i = 0; i < LootTableUtils.existingLootPools.get(id).getEntries().size(); i++) {
                newEntries[entries.length + i] = LootTableUtils.existingLootPools.get(id).getEntries().get(i);
            }

            ((MixinLootPoolAccessor)((MixinLootTableAccessor)lootManager.get(id)).getPools()[LootTableUtils.existingLootPools.get(id).getPoolIndex()]).setEntries(newEntries);
        }
    }
}
