package com.ant.mcskyblock.utils.loot;

import com.ant.mcskyblock.MCSkyBlock;
import com.ant.mcskyblock.mixin.MixinLootPoolAccessor;
import com.ant.mcskyblock.mixin.MixinLootTableAccessor;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.KilledByPlayerLootCondition;
import net.minecraft.loot.condition.RandomChanceWithLootingLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.LootingEnchantLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

import java.util.*;

public class LootTableUtils {
    private static Map<Identifier, LootPool> newLootPools = new HashMap<>();
    private static Map<Identifier, LootPoolReference> existingLootPools = new HashMap<>();

    static {
        newLootPools.put(EntityType.PHANTOM.getLootTableId(), FabricLootPoolBuilder.builder().rolls(ConstantLootNumberProvider.create(1f)).with(ItemEntry.builder(Items.ELYTRA)).withCondition(KilledByPlayerLootCondition.builder().build()).withCondition(RandomChanceWithLootingLootCondition.builder(0.01f, 0.05f).build()).build());
        newLootPools.put(EntityType.WITCH.getLootTableId(), FabricLootPoolBuilder.builder().rolls(ConstantLootNumberProvider.create(1f)).with(ItemEntry.builder(Items.NETHER_WART)).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0f, 1f))).withFunction(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0f, 1f)).build()).build());
        newLootPools.put(EntityType.ENDER_DRAGON.getLootTableId(), FabricLootPoolBuilder.builder().rolls(ConstantLootNumberProvider.create(1f)).with(ItemEntry.builder(Items.DRAGON_HEAD)).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1f))).build());

        List<LootPoolEntry> piglinLootTable = new ArrayList<>();
        piglinLootTable.add(ItemEntry.builder(Items.NETHERRACK).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(8f, 16f))).weight(40).build());
        piglinLootTable.add(ItemEntry.builder(Items.SOUL_SOIL).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4f, 8f))).weight(20).build());
        piglinLootTable.add(ItemEntry.builder(Items.CRIMSON_NYLIUM).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4f, 8f))).weight(20).build());
        piglinLootTable.add(ItemEntry.builder(Items.WARPED_NYLIUM).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4f, 8f))).weight(20).build());
        piglinLootTable.add(ItemEntry.builder(Items.CRIMSON_FUNGUS).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 2f))).weight(10).build());
        piglinLootTable.add(ItemEntry.builder(Items.WARPED_FUNGUS).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 2f))).weight(10).build());
        piglinLootTable.add(ItemEntry.builder(Items.ANCIENT_DEBRIS).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1f))).weight(1).build());
        existingLootPools.put(LootTables.PIGLIN_BARTERING_GAMEPLAY, new LootPoolReference(0, piglinLootTable));

        List<LootPoolEntry> clericHotVLootTable = new ArrayList<>();
        clericHotVLootTable.add(ItemEntry.builder(Items.DIAMOND).build());
        clericHotVLootTable.add(ItemEntry.builder(Items.BUDDING_AMETHYST).build());
        existingLootPools.put(LootTables.HERO_OF_THE_VILLAGE_CLERIC_GIFT_GAMEPLAY, new LootPoolReference(0, clericHotVLootTable));
    }

    public static void register() {
        LootTableLoadingCallback.EVENT.register((resourceManager, manager, id, supplier, setter) -> {
            MCSkyBlock.LOGGER.info(id.toString());
            if (newLootPools.containsKey(id)) {
                MCSkyBlock.LOGGER.info("Adding new loot pool for: " + id);
                supplier.withPool(newLootPools.get(id));
            }

            if (existingLootPools.containsKey(id)) {
                MCSkyBlock.LOGGER.info("Modifying existing loot pool [" + existingLootPools.get(id).getPoolIndex()  + "] for: " + id);

                LootPoolEntry[] entries = ((MixinLootPoolAccessor)((MixinLootTableAccessor)manager.getTable(id)).getPools()[existingLootPools.get(id).getPoolIndex()]).getEntries();
                LootPoolEntry[] newEntries = new LootPoolEntry[entries.length + existingLootPools.get(id).getEntries().size()];

                System.arraycopy(entries, 0, newEntries, 0, entries.length);

                for (int i = 0; i < existingLootPools.get(id).getEntries().size(); i++) {
                    newEntries[entries.length + i] = existingLootPools.get(id).getEntries().get(i);
                }

                ((MixinLootPoolAccessor)((MixinLootTableAccessor)manager.getTable(id)).getPools()[existingLootPools.get(id).getPoolIndex()]).setEntries(newEntries);
            }
        });
    }
}
