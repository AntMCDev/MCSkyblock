package com.ant.mcskyblock.utils.trade;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import org.jetbrains.annotations.Nullable;

public class RandomPriceTradeFactory extends BasicTradeFactory {
    protected int maxAdditionalEmeralds;

    public RandomPriceTradeFactory(int price, ItemStack buy, int maxUses, int experience, float multiplier, int maxAdditionalEmeralds) {
        super(price, buy, maxUses, experience, multiplier);
        this.maxAdditionalEmeralds = maxAdditionalEmeralds;
    }

    @Nullable
    @Override
    public TradeOffer create(Entity entity, Random random) {
        int i = random.nextInt(maxAdditionalEmeralds);
        return new TradeOffer(new ItemStack(Items.EMERALD, this.price + i), this.buy, this.maxUses, this.experience, this.multiplier);
    }
}
