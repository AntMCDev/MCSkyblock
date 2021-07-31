package com.ant.mcskyblock.utils;

import com.ant.mcskyblock.MCSkyblock;
import com.ant.mcskyblock.config.ConfigHandler;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.BasicTrade;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

@Mod.EventBusSubscriber(modid = MCSkyblock.MOD_ID)
public class TradingUtils {
    private static List<VillagerTrades.ItemListing> wanderingTradingGenericTradeAdditions = new ArrayList<>();
    private static List<VillagerTrades.ItemListing> wanderingTradingRareTradeAdditions = new ArrayList<>();
    private static Map<VillagerProfession, LevelledTrade[]> trades = new HashMap<>();

    static {

        if (ConfigHandler.COMMON.wanderingTraderSponge.get()) {
            wanderingTradingGenericTradeAdditions.add(new BasicTrade(3, new ItemStack(Items.SPONGE, 1), 8, 1));
        }

        if (ConfigHandler.COMMON.wanderingTraderLavaBucket.get()) {
            wanderingTradingRareTradeAdditions.add(new BasicTrade(5, new ItemStack(Items.LAVA_BUCKET, 1), 4, 1));
        }

        if (ConfigHandler.COMMON.wanderingTraderChorusFlower.get()) {
            wanderingTradingRareTradeAdditions.add(new BasicTrade(6, new ItemStack(Items.CHORUS_FLOWER, 1), 6, 1));
        }

        if (ConfigHandler.COMMON.wanderingTraderHeartOfTheSea.get()) {
            wanderingTradingRareTradeAdditions.add(new BasicTrade(10, new ItemStack(Items.HEART_OF_THE_SEA, 1), 1, 1));
        }

        if (ConfigHandler.COMMON.wanderingTraderEndPortalFrame.get()) {
            wanderingTradingRareTradeAdditions.add(new BasicTrade(50, new ItemStack(Items.END_PORTAL_FRAME, 1), 1, 1));
        }

        if (ConfigHandler.COMMON.clericShulkerShellTrade.get()) {
            trades.put(VillagerProfession.CLERIC, new LevelledTrade[]{
                    new RandomPriceLevelledTrade(5, 20, new ItemStack(Items.SHULKER_SHELL, 2), 3, 1, 0.2f, 10)
            });
        }
    }

    @SubscribeEvent
    public static void onWandererTradesEvent(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> genericTrades =  event.getGenericTrades();
        List<VillagerTrades.ItemListing> rareTrades = event.getRareTrades();

        genericTrades.addAll(wanderingTradingGenericTradeAdditions);
        rareTrades.addAll(wanderingTradingRareTradeAdditions);
    }

    @SubscribeEvent
    public static void onVillagerTradesEvent(VillagerTradesEvent event) {
        if (trades.containsKey(event.getType())) {
            for (LevelledTrade l : trades.get(event.getType())) {
                event.getTrades().get(l.level).add(l);
            }
        }
    }
}
