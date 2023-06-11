package com.ant.mcskyblock.common.loot;

import com.ant.mcskyblock.common.config.Config;
import com.ant.mcskyblock.common.world.level.block.Blocks;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.data.loot.packs.VanillaFishingLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

public class LootHelper {
    public static Map<ResourceLocation, LootPool> newLootPools = new HashMap<>();
    public static Map<ResourceLocation, LootPoolReference> existingLootPools = new HashMap<>();

    public static final LootItemCondition.Builder IN_NETHER = LocationCheck.checkLocation(LocationPredicate.Builder.location().setDimension(Level.NETHER));
    public static final LootItemCondition.Builder IN_STRONGHOLD = LocationCheck.checkLocation(LocationPredicate.Builder.location().setStructure(BuiltinStructures.STRONGHOLD));
    public static final LootItemCondition.Builder IN_ANCIENT_CITY = LocationCheck.checkLocation(LocationPredicate.Builder.location().setStructure(BuiltinStructures.ANCIENT_CITY));

    public static void resync(ResourceLocation id) {
        if (Config.INSTANCE.drops.PHANTOM_ELYTRA && id.equals(EntityType.PHANTOM.getDefaultLootTable())) {
            LootHelper.newLootPools.put(EntityType.PHANTOM.getDefaultLootTable(),
                    new LootHelper.LootPoolBuilder().setRolls(ConstantValue.exactly(1f))
                            .with(LootItem.lootTableItem(Items.ELYTRA).build())
                            .conditionally(LootItemKilledByPlayerCondition.killedByPlayer().build())
                            .conditionally(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.01f, 0.05f).build()).build());
        } else {
            LootHelper.newLootPools.remove(EntityType.PHANTOM.getDefaultLootTable());
        }

        if (Config.INSTANCE.drops.DROWNED_GOLD && id.equals(EntityType.DROWNED.getDefaultLootTable())) {
            LootHelper.newLootPools.put(EntityType.DROWNED.getDefaultLootTable(),
                    new LootHelper.LootPoolBuilder().setRolls(ConstantValue.exactly(1f))
                            .with(LootItem.lootTableItem(Items.GOLD_INGOT).build())
                            .conditionally(LootItemKilledByPlayerCondition.killedByPlayer().build())
                            .conditionally(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.05f, 0.05f).build())
                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1f)).build()).build());
        } else {
            LootHelper.newLootPools.remove(EntityType.DROWNED.getDefaultLootTable());
        }

        if (Config.INSTANCE.drops.HUSK_SAND && id.equals(EntityType.HUSK.getDefaultLootTable())) {
            LootHelper.newLootPools.put(EntityType.HUSK.getDefaultLootTable(),
                    new LootHelper.LootPoolBuilder().setRolls(ConstantValue.exactly(1f))
                            .with(LootItem.lootTableItem(Items.SAND).build())
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(0f, 1f)).build())
                            .apply(LootingEnchantFunction.lootingMultiplier( UniformGenerator.between(0f, 1f) ).build() ).build());
        } else {
            LootHelper.newLootPools.remove(EntityType.HUSK.getDefaultLootTable());
        }

        if (Config.INSTANCE.drops.RAVAGER_SCULK_SHRIEKER && id.equals(EntityType.RAVAGER.getDefaultLootTable())) {
            LootHelper.newLootPools.put(EntityType.RAVAGER.getDefaultLootTable(),
                    new LootHelper.LootPoolBuilder().setRolls(ConstantValue.exactly(1f))
                            .with(LootItem.lootTableItem( Blocks.activeSculkShrieker()).build())
                            .conditionally(LootItemKilledByPlayerCondition.killedByPlayer().build())
                            .conditionally(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.05f, 0.05f).build())
                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1f)).build() ).build());
        } else {
            LootHelper.newLootPools.remove(EntityType.RAVAGER.getDefaultLootTable());
        }

        if(Config.INSTANCE.drops.BRUTE_ANCIENT_DEBRIS && id.equals(EntityType.PIGLIN_BRUTE.getDefaultLootTable())){
            LootHelper.newLootPools.put(EntityType.PIGLIN_BRUTE.getDefaultLootTable(),
                    new LootHelper.LootPoolBuilder().setRolls(ConstantValue.exactly(1f))
                            .with(LootItem.lootTableItem(Items.ANCIENT_DEBRIS).build())
                            .conditionally(LootItemKilledByPlayerCondition.killedByPlayer().build())
                            .conditionally(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.05f, 0.05f).build())
                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1f) ).build()).build());

        } else {
            LootHelper.newLootPools.remove(EntityType.PIGLIN_BRUTE.getDefaultLootTable());
        }

        if (Config.INSTANCE.drops.WITCH_NETHER_WART && id.equals(EntityType.WITCH.getDefaultLootTable())) {
            LootHelper.newLootPools.put(EntityType.WITCH.getDefaultLootTable(),
                    new LootHelper.LootPoolBuilder().setRolls(ConstantValue.exactly(1f))
                            .with(LootItem.lootTableItem(Items.NETHER_WART).build())
                            .conditionally(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.5f, 0.5f).build())
                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1f)).build()).build());
        } else {
            LootHelper.newLootPools.remove(EntityType.WITCH.getDefaultLootTable());
        }

        if (Config.INSTANCE.drops.TROPICAL_FISH_CORAL && id.equals(EntityType.TROPICAL_FISH.getDefaultLootTable())) {
            LootHelper.newLootPools.put(EntityType.TROPICAL_FISH.getDefaultLootTable(),
                    new LootHelper.LootPoolBuilder().setRolls(ConstantValue.exactly(1f))
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
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(0f, 1f)).build())
                            .apply(LootingEnchantFunction.lootingMultiplier( UniformGenerator.between(0.7f, 1.0f) ).build() ).build());
        } else {
            LootHelper.newLootPools.remove(EntityType.TROPICAL_FISH.getDefaultLootTable());
        }

        if (Config.INSTANCE.drops.ENDER_DRAGON_HEAD && id.equals(EntityType.ENDER_DRAGON.getDefaultLootTable())) {
            LootHelper.newLootPools.put(EntityType.ENDER_DRAGON.getDefaultLootTable(),
                    new LootHelper.LootPoolBuilder().setRolls(ConstantValue.exactly(1f))
                            .with(LootItem.lootTableItem( Items.DRAGON_HEAD ).build())
                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1f)).build()).build());
        } else {
            LootHelper.newLootPools.remove(EntityType.ENDER_DRAGON.getDefaultLootTable());
        }

        List<LootPoolEntryContainer> piglinLootTable = new ArrayList<>();
        if (Config.INSTANCE.trading.PIGLIN_WEEPING_VINES && id.equals(BuiltInLootTables.PIGLIN_BARTERING)) {
            piglinLootTable.add(LootItem.lootTableItem(Items.WEEPING_VINES)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(8f, 16f)))
                    .setWeight(40).build());
        }
        if (Config.INSTANCE.trading.PIGLIN_NYLIUM && id.equals(BuiltInLootTables.PIGLIN_BARTERING)) {
            piglinLootTable.add(LootItem.lootTableItem(Items.CRIMSON_NYLIUM)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(4f, 8f)))
                    .setWeight(20).build());

            piglinLootTable.add(LootItem.lootTableItem(Items.WARPED_NYLIUM)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(4f, 8f)))
                    .setWeight(20).build());
        }
        if (piglinLootTable.size() > 0) {
            LootHelper.existingLootPools.put(BuiltInLootTables.PIGLIN_BARTERING, new LootPoolReference(0, piglinLootTable));
        } else {
            LootHelper.newLootPools.remove(BuiltInLootTables.PIGLIN_BARTERING);
        }

        List<LootPoolEntryContainer> fishingLootTable = new ArrayList<>();
        if (Config.INSTANCE.drops.FISHING_COCOA_BEANS && id.equals(BuiltInLootTables.FISHING_JUNK)) {
            fishingLootTable.add(LootItem.lootTableItem(Items.COCOA_BEANS)
                    .when(VanillaFishingLoot.IN_JUNGLE.or(VanillaFishingLoot.IN_BAMBOO_JUNGLE.or(VanillaFishingLoot.IN_SPARSE_JUNGLE)))
                    .setWeight(10).build()
            );

            LootHelper.existingLootPools.put(BuiltInLootTables.FISHING_JUNK, new LootPoolReference(0,fishingLootTable));
        } else {
            LootHelper.newLootPools.remove(BuiltInLootTables.FISHING_JUNK);
        }

        List<LootPoolEntryContainer> clericHotVLootTable = new ArrayList<>();
        if (Config.INSTANCE.trading.HOTV_CLERIC_DIAMOND && id.equals(BuiltInLootTables.CLERIC_GIFT)) {
            clericHotVLootTable.add(LootItem.lootTableItem(Items.DIAMOND).build());
        }
        if (Config.INSTANCE.trading.HOTV_CLERIC_BUDDING_AMETHYST && id.equals(BuiltInLootTables.CLERIC_GIFT)) {
            clericHotVLootTable.add(LootItem.lootTableItem(Items.BUDDING_AMETHYST).build());
        }
        if (clericHotVLootTable.size() > 0) {
            LootHelper.existingLootPools.put(BuiltInLootTables.CLERIC_GIFT, new LootPoolReference(0, clericHotVLootTable));
        } else {
            LootHelper.newLootPools.remove(BuiltInLootTables.CLERIC_GIFT);
        }

        List<LootPoolEntryContainer> creeperDiscLootTable = new ArrayList<>();
        if (Config.INSTANCE.drops.CONDITIONAL_CREEPER_DISCS && id.equals(EntityType.CREEPER.getDefaultLootTable())) {
            creeperDiscLootTable.add(LootItem.lootTableItem(Items.MUSIC_DISC_PIGSTEP).when(LootHelper.IN_NETHER).build());
            creeperDiscLootTable.add(LootItem.lootTableItem(Items.MUSIC_DISC_OTHERSIDE).when(LootHelper.IN_STRONGHOLD).build());
            creeperDiscLootTable.add(LootItem.lootTableItem(Items.DISC_FRAGMENT_5).when(LootHelper.IN_ANCIENT_CITY).build());

            LootHelper.existingLootPools.put(EntityType.CREEPER.getDefaultLootTable(), new LootPoolReference(1, creeperDiscLootTable));
        } else {
            LootHelper.newLootPools.remove(EntityType.CREEPER.getDefaultLootTable());
        }
    }

    public static class LootPoolBuilder {
        private final LootPool.Builder builder;

        public LootPoolBuilder() {
            this.builder = LootPool.lootPool();
        }

        public LootPoolBuilder setRolls(NumberProvider numberProvider) {
            this.builder.setRolls(numberProvider);
            return this;
        }

        @SuppressWarnings("unchecked")
        public LootPoolBuilder with(LootPoolEntryContainer entry) {
            Optional<Field> entries = Arrays.stream(LootPool.Builder.class.getDeclaredFields()).peek(AccessibleObject::trySetAccessible).filter(f -> f.getType().isAssignableFrom(List.class) && ((ParameterizedType)f.getGenericType()).getActualTypeArguments()[0].getTypeName().equals(LootPoolEntryContainer.class.getTypeName())).findFirst();
            if (entries.isPresent()) {
                try {
                    ((List<LootPoolEntryContainer>)entries.get().get(builder)).add(entry);
                } catch (IllegalAccessException ignore) {}
            }
            return this;
        }

        @SuppressWarnings("unchecked")
        public LootPoolBuilder conditionally(LootItemCondition condition) {
            Optional<Field> conditions = Arrays.stream(LootPool.Builder.class.getDeclaredFields()).peek(AccessibleObject::trySetAccessible).filter(f -> f.getType().isAssignableFrom(List.class) && ((ParameterizedType)f.getGenericType()).getActualTypeArguments()[0].getTypeName().equals(LootItemCondition.class.getTypeName())).findFirst();
            if (conditions.isPresent()) {
                try {
                    ((List<LootItemCondition>)conditions.get().get(builder)).add(condition);
                } catch (IllegalAccessException ignore) {}
            }
            return this;
        }

        @SuppressWarnings("unchecked")
        public LootPoolBuilder apply(LootItemFunction function) {
            Optional<Field> functions = Arrays.stream(LootPool.Builder.class.getDeclaredFields()).peek(AccessibleObject::trySetAccessible).filter(f -> f.getType().isAssignableFrom(List.class) && ((ParameterizedType)f.getGenericType()).getActualTypeArguments()[0].getTypeName().equals(LootItemFunction.class.getTypeName())).findFirst();
            if (functions.isPresent()) {
                try {
                    ((List<LootItemFunction>)functions.get().get(builder)).add(function);
                } catch (IllegalAccessException ignore) {}
            }
            return this;
        }

        public LootPool build() {
            return builder.build();
        }
    }

    public static class LootTableBuilderHelper {
        @SuppressWarnings("unchecked")
        public static void pool(LootTable.Builder builder, LootPool pool) {
            Optional<Field> entries = Arrays.stream(LootTable.Builder.class.getDeclaredFields()).peek(AccessibleObject::trySetAccessible).filter(f -> f.getType().isAssignableFrom(List.class) && ((ParameterizedType)f.getGenericType()).getActualTypeArguments()[0].getTypeName().equals(LootPool.class.getTypeName())).findFirst();
            if (entries.isPresent()) {
                try {
                    ((List<LootPool>)entries.get().get(builder)).add(pool);
                } catch (IllegalAccessException ignore) {}
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static void register(LootTables lootManager, ResourceLocation id, LootTable.Builder tableBuilder) {
        resync(id);

        if (LootHelper.newLootPools.containsKey(id)) {
            LootTableBuilderHelper.pool(tableBuilder, LootHelper.newLootPools.get(id));
        }

        if (LootHelper.existingLootPools.containsKey(id)) {
            Optional<Field> tablesField = Arrays.stream(LootTables.class.getDeclaredFields()).peek(AccessibleObject::trySetAccessible).filter(f -> f.getType().isAssignableFrom(Map.class)).findFirst();
            Optional<Field> poolsField = Arrays.stream(LootTable.class.getDeclaredFields()).peek(AccessibleObject::trySetAccessible).filter(f -> f.getType().isArray() && f.getType().getComponentType().isAssignableFrom(LootPool.class)).findFirst();
            boolean poolsList = false;
            if (poolsField.isEmpty()) {
                poolsList = true;
                poolsField = Arrays.stream(LootTable.class.getDeclaredFields()).peek(AccessibleObject::trySetAccessible).filter(f -> f.getType().isAssignableFrom(List.class) && ((ParameterizedType)f.getGenericType()).getActualTypeArguments()[0].getTypeName().equals(LootPool.class.getTypeName())).findFirst();
            }
            Optional<Field> entriesField = Arrays.stream(LootPool.class.getDeclaredFields()).peek(AccessibleObject::trySetAccessible).filter(f -> f.getType().isArray() && f.getType().getComponentType().isAssignableFrom(LootPoolEntryContainer.class)).findFirst();

            if (tablesField.isPresent() && poolsField.isPresent() && entriesField.isPresent()) {
                try {
                    Map<ResourceLocation, LootTable> tables = (Map<ResourceLocation, LootTable>)tablesField.get().get(lootManager);
                    LootPool pool = poolsList ? ((List<LootPool>)poolsField.get().get(tables.get(id))).get(LootHelper.existingLootPools.get(id).getPoolIndex()) : ((LootPool[])poolsField.get().get(tables.get(id)))[LootHelper.existingLootPools.get(id).getPoolIndex()];
                    LootPoolEntryContainer[] entries = (LootPoolEntryContainer[])entriesField.get().get(pool);
                    LootPoolEntryContainer[] newEntries = new LootPoolEntryContainer[entries.length + LootHelper.existingLootPools.get(id).getEntries().size()];
                    System.arraycopy(entries, 0, newEntries, 0, entries.length);
                    for (int i = 0; i < LootHelper.existingLootPools.get(id).getEntries().size(); i++) {
                        newEntries[entries.length + i] = LootHelper.existingLootPools.get(id).getEntries().get(i);
                    }
                    entriesField.get().set(poolsList ?
                            ((List<LootPool>)poolsField.get().get(((Map<ResourceLocation, LootTable>)tablesField.get().get(lootManager)).get(id))).get(LootHelper.existingLootPools.get(id).getPoolIndex()) :
                            ((LootPool[]) poolsField.get().get(((Map<ResourceLocation, LootTable>) tablesField.get().get(lootManager)).get(id)))[LootHelper.existingLootPools.get(id).getPoolIndex()], newEntries
                    );
                } catch (IllegalAccessException ignore) {}
            }
        }
    }
}
