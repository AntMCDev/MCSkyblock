package com.ant.mcskyblock.utils;

import com.ant.mcskyblock.MCSkyblock;
import com.ant.mcskyblock.config.ConfigHandler;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.BasicTrade;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

@Mod.EventBusSubscriber(modid = MCSkyblock.MOD_ID)
public class TradingUtils {
    private static List<VillagerTrades.ITrade> wanderingTradingGenericTradeAdditions = new ArrayList<>();
    private static List<VillagerTrades.ITrade> wanderingTradingRareTradeAdditions = new ArrayList<>();
    private static Map<VillagerProfession, LevelledTrade[]> trades = new HashMap<>();

    static {

        if (ConfigHandler.COMMON.wanderingTraderSponge.get()) {
            wanderingTradingGenericTradeAdditions.add(new BasicTrade(3, new ItemStack(Items.SPONGE, 1), 8, 1));
        }

        if (ConfigHandler.COMMON.wanderingTraderLavaBucket.get()) {
            wanderingTradingRareTradeAdditions.add(new BasicTrade(5, new ItemStack(Items.LAVA_BUCKET, 1), 4, 1));
        }

        if (ConfigHandler.COMMON.wanderingTraderChorusPlant.get()) {
            wanderingTradingRareTradeAdditions.add(new BasicTrade(6, new ItemStack(Items.CHORUS_PLANT, 1), 6, 1));
        }

        if (ConfigHandler.COMMON.wanderingTraderHeartOfTheSea.get()) {
            wanderingTradingRareTradeAdditions.add(new BasicTrade(10, new ItemStack(Items.HEART_OF_THE_SEA, 1), 1, 1));
        }

        if (ConfigHandler.COMMON.wanderingTraderEndPortalFrame.get()) {
            wanderingTradingRareTradeAdditions.add(new BasicTrade(50, new ItemStack(Items.END_PORTAL_FRAME, 1), 1, 1));
        }

        if (ConfigHandler.COMMON.masonQuartzTrade.get()) {
            trades.put(VillagerProfession.MASON, new LevelledTrade[]{
                    new LevelledTrade(1, 1, new ItemStack(Items.QUARTZ, 2), 16, 1, 0.05f)
            });
        }

        if (ConfigHandler.COMMON.clericShulkerShellTrade.get()) {
            trades.put(VillagerProfession.CLERIC, new LevelledTrade[]{
                    new RandomPriceLevelledTrade(5, 20, new ItemStack(Items.SHULKER_SHELL, 2), 3, 1, 0.2f, 10)
            });
        }
    }

    @SubscribeEvent
    public static void onWandererTradesEvent(WandererTradesEvent event) {
        List<VillagerTrades.ITrade> genericTrades =  event.getGenericTrades();
        List<VillagerTrades.ITrade> rareTrades = event.getRareTrades();

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
