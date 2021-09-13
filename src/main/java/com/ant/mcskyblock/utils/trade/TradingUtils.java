package com.ant.mcskyblock.utils.trade;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;

import java.util.*;

public class TradingUtils {
    private static final int WANDERING_TRADER_COMMON_OFFER = 1;
    private static final int WANDERING_TRADER_RARE_OFFER = 2;

    private static class LevelledTradeOffer {
        private int level;
        private final TradeOffers.Factory factory;

        public LevelledTradeOffer(int level, TradeOffers.Factory factory) {
            this.level = level;
            this.factory = factory;
        }
    }

    private static List<LevelledTradeOffer> wanderingTrades = new ArrayList<>();
    private static Map<VillagerProfession, List<LevelledTradeOffer>> trades = new HashMap<>();

    static {
        wanderingTrades.add(new LevelledTradeOffer(WANDERING_TRADER_COMMON_OFFER, new BasicTradeFactory(3, new ItemStack(Items.SPONGE, 1), 8, 1)));
        wanderingTrades.add(new LevelledTradeOffer(WANDERING_TRADER_COMMON_OFFER, new BasicTradeFactory(3, new ItemStack(Items.GLOW_LICHEN, 4), 8, 1)));
        wanderingTrades.add(new LevelledTradeOffer(WANDERING_TRADER_COMMON_OFFER, new BasicTradeFactory(3, new ItemStack(Items.GLOW_BERRIES, 1), 8, 1)));
        wanderingTrades.add(new LevelledTradeOffer(WANDERING_TRADER_RARE_OFFER, new BasicTradeFactory(5, new ItemStack(Items.LAVA_BUCKET, 1), 4, 1)));
        wanderingTrades.add(new LevelledTradeOffer(WANDERING_TRADER_RARE_OFFER, new BasicTradeFactory(5, new ItemStack(Items.SCULK_SENSOR, 1), 4, 1)));
        wanderingTrades.add(new LevelledTradeOffer(WANDERING_TRADER_RARE_OFFER, new BasicTradeFactory(6, new ItemStack(Items.CHORUS_FLOWER, 1), 6, 1)));
        wanderingTrades.add(new LevelledTradeOffer(WANDERING_TRADER_RARE_OFFER, new BasicTradeFactory(10, new ItemStack(Items.HEART_OF_THE_SEA, 1), 1, 1)));
        wanderingTrades.add(new LevelledTradeOffer(WANDERING_TRADER_RARE_OFFER, new BasicTradeFactory(50, new ItemStack(Items.END_PORTAL_FRAME, 1), 1, 1)));
        trades.put(VillagerProfession.CLERIC, Collections.singletonList(new LevelledTradeOffer(5, new RandomPriceTradeFactory(20, new ItemStack(Items.SHULKER_SHELL, 2), 3, 1, 0.2f, 10))));
        List<LevelledTradeOffer> masonTrades = new ArrayList<>();
        masonTrades.add(new LevelledTradeOffer(3, new BasicTradeFactory(1, new ItemStack(Items.CALCITE, 16), 16, 20, 0.05f)));
        masonTrades.add(new LevelledTradeOffer(3, new BasicTradeFactory(1, new ItemStack(Items.DEEPSLATE, 16), 16, 20, 0.05f)));
        masonTrades.add(new LevelledTradeOffer(3, new BasicTradeFactory(1, new ItemStack(Items.TUFF, 16), 16, 20, 0.05f)));
        trades.put(VillagerProfession.MASON, masonTrades);
    }

    public static void register() {
        for (LevelledTradeOffer wanderingTrade : wanderingTrades) {
            TradeOfferHelper.registerWanderingTraderOffers(wanderingTrade.level, factories -> {
                factories.add(wanderingTrade.factory);
            });
        }

        for (VillagerProfession villagerProfession : trades.keySet()) {
            for (LevelledTradeOffer trade : trades.get(villagerProfession)) {
                TradeOfferHelper.registerVillagerOffers(villagerProfession, trade.level, factories -> {
                    factories.add(trade.factory);
                });
            }
        }
    }
}
