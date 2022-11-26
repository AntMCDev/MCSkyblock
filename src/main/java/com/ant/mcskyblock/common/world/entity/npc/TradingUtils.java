package com.ant.mcskyblock.common.world.entity.npc;

import com.ant.mcskyblock.common.config.Config;
import com.ant.mcskyblock.common.world.entity.npc.BasicTradeFactory;
import com.ant.mcskyblock.common.world.entity.npc.RandomPriceTradeFactory;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;
import java.util.function.Consumer;

public class TradingUtils {
    private static final int WANDERING_TRADER_COMMON_OFFER = 1;
    private static final int WANDERING_TRADER_RARE_OFFER = 2;

    private record LevelledTradeOffer(int level, VillagerTrades.ItemListing factory) { }

    private static List<LevelledTradeOffer> wanderingTrades = new ArrayList<>();

    private static Map<VillagerProfession, List<LevelledTradeOffer>> trades = new HashMap<>();

    static {
        if (Config.INSTANCE.trading.WANDERING_TRADER_SPONGE) {
            wanderingTrades.add(new LevelledTradeOffer(WANDERING_TRADER_COMMON_OFFER, new BasicTradeFactory(3, new ItemStack(Items.SPONGE, 1), 8, 1)));
        }
        if (Config.INSTANCE.trading.WANDERING_TRADER_GLOW_LICHEN) {
            wanderingTrades.add(new LevelledTradeOffer(WANDERING_TRADER_COMMON_OFFER, new BasicTradeFactory(3, new ItemStack(Items.GLOW_LICHEN, 4), 8, 1)));
        }
        if (Config.INSTANCE.trading.WANDERING_TRADER_GLOW_BERRIES) {
            wanderingTrades.add(new LevelledTradeOffer(WANDERING_TRADER_COMMON_OFFER, new BasicTradeFactory(3, new ItemStack(Items.GLOW_BERRIES, 1), 8, 1)));
        }
        if (Config.INSTANCE.trading.WANDERING_TRADER_LAVA_BUCKET) {
            wanderingTrades.add(new LevelledTradeOffer(WANDERING_TRADER_RARE_OFFER, new BasicTradeFactory(5, new ItemStack(Items.LAVA_BUCKET, 1), 4, 1)));
        }
        if (Config.INSTANCE.trading.WANDERING_TRADER_SCULK_CATALYST) {
            wanderingTrades.add(new LevelledTradeOffer(WANDERING_TRADER_RARE_OFFER, new BasicTradeFactory(25, new ItemStack(Items.SCULK_CATALYST, 1), 4, 1)));
        }
        if (Config.INSTANCE.trading.WANDERING_TRADER_CHORUS_PLANT) {
            wanderingTrades.add(new LevelledTradeOffer(WANDERING_TRADER_RARE_OFFER, new BasicTradeFactory(6, new ItemStack(Items.CHORUS_FLOWER, 1), 6, 1)));
        }
        if (Config.INSTANCE.trading.WANDERING_TRADER_DISC_OTHERSIDE) {
            wanderingTrades.add(new LevelledTradeOffer(WANDERING_TRADER_RARE_OFFER, new BasicTradeFactory(5, new ItemStack(Items.MUSIC_DISC_OTHERSIDE, 1), 1, 1)));
        }
        if (Config.INSTANCE.trading.WANDERING_TRADER_DISC_5) {
            wanderingTrades.add(new LevelledTradeOffer(WANDERING_TRADER_RARE_OFFER, new BasicTradeFactory(5, new ItemStack(Items.MUSIC_DISC_5, 1), 1, 1)));
        }

        if (Config.INSTANCE.trading.WANDERING_TRADER_TWO_TALL_FLOWERS) {
            wanderingTrades.add(new LevelledTradeOffer(WANDERING_TRADER_RARE_OFFER, new BasicTradeFactory(5, new ItemStack(Items.LILAC, 1), 1, 1)));
            wanderingTrades.add(new LevelledTradeOffer(WANDERING_TRADER_RARE_OFFER, new BasicTradeFactory(5, new ItemStack(Items.ROSE_BUSH, 1), 1, 1)));
            wanderingTrades.add(new LevelledTradeOffer(WANDERING_TRADER_RARE_OFFER, new BasicTradeFactory(5, new ItemStack(Items.PEONY, 1), 1, 1)));
            wanderingTrades.add(new LevelledTradeOffer(WANDERING_TRADER_RARE_OFFER, new BasicTradeFactory(5, new ItemStack(Items.SUNFLOWER, 1), 1, 1)));
        }

        List<LevelledTradeOffer> clericTrades = new ArrayList<>();
        if (Config.INSTANCE.trading.CLERIC_SHULKER_SHELL) {
            clericTrades.add(new LevelledTradeOffer(5, new RandomPriceTradeFactory(20, new ItemStack(Items.SHULKER_SHELL, 2), 3, 1, 0.2f, 10)));
        }
        if (Config.INSTANCE.trading.CLERIC_ECHO_SHARD) {
            clericTrades.add(new LevelledTradeOffer(5, new RandomPriceTradeFactory(2, new ItemStack(Items.ECHO_SHARD, 1), 3, 1, 0.2f, 10)));
        }
        if (clericTrades.size() > 0) {
            trades.put(VillagerProfession.CLERIC, clericTrades);
        }

        List<LevelledTradeOffer> masonTrades = new ArrayList<>();
        if (Config.INSTANCE.trading.MASON_CALCITE) {
            masonTrades.add(new LevelledTradeOffer(3, new BasicTradeFactory(1, new ItemStack(Items.CALCITE, 16), 16, 20, 0.05f ) ) );
        }
        if (Config.INSTANCE.trading.MASON_DEEPSLATE) {
            masonTrades.add(new LevelledTradeOffer(3, new BasicTradeFactory(1, new ItemStack(Items.DEEPSLATE, 16), 16, 20, 0.05f)));
        }
        if (Config.INSTANCE.trading.MASON_TUFF) {
            masonTrades.add(new LevelledTradeOffer(3, new BasicTradeFactory(1, new ItemStack(Items.TUFF, 16), 16, 20, 0.05f)));
        }
        if (masonTrades.size() > 0) {
            trades.put(VillagerProfession.MASON, masonTrades);
        }
    }

    public static void register() {
        for (LevelledTradeOffer wanderingTrade : wanderingTrades) {
            registerOffers(VillagerTrades.WANDERING_TRADER_TRADES, wanderingTrade.level, factories -> factories.add(wanderingTrade.factory));
        }

        for (VillagerProfession villagerProfession : trades.keySet()) {
            for (LevelledTradeOffer trade : trades.get(villagerProfession)) {
                registerOffers(VillagerTrades.TRADES.computeIfAbsent(villagerProfession, key -> new Int2ObjectOpenHashMap<>()), trade.level, factories -> factories.add(trade.factory));
            }
        }
    }

    private static void registerOffers(Int2ObjectMap<VillagerTrades.ItemListing[]> leveledTradeMap, int level, Consumer<List<VillagerTrades.ItemListing>> factory) {
        final List<VillagerTrades.ItemListing> list = new ArrayList<>();
        factory.accept(list);

        final VillagerTrades.ItemListing[] originalEntries = leveledTradeMap.computeIfAbsent(level, key -> new VillagerTrades.ItemListing[0]);
        final VillagerTrades.ItemListing[] addedEntries = list.toArray(new VillagerTrades.ItemListing[0]);

        final VillagerTrades.ItemListing[] allEntries = ArrayUtils.addAll(originalEntries, addedEntries);
        leveledTradeMap.put(level, allEntries);
    }
}
