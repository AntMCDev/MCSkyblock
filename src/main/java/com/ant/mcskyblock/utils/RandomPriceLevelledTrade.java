package com.ant.mcskyblock.utils;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;

import javax.annotation.Nullable;
import java.util.Random;

public class RandomPriceLevelledTrade extends LevelledTrade {
    private int maxAdditionalEmeralds;

    public RandomPriceLevelledTrade(int level, int emeralds, ItemStack forSale, int maxTrades, int xp, float multi, int maxAdditionalEmeralds) {
        super(level, emeralds, forSale, maxTrades, xp, multi);
        this.maxAdditionalEmeralds = maxAdditionalEmeralds;
    }

    @Nullable
    @Override
    public MerchantOffer getOffer(Entity merchant, Random rand) {
        int i = rand.nextInt(maxAdditionalEmeralds);
        return new MerchantOffer(new ItemStack(Items.EMERALD, price.getCount() + i), price2, forSale, maxTrades, xp, priceMult);
    }
}
