package com.ant.mcskyblock.utils.trade;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import org.jetbrains.annotations.Nullable;

public class BasicTradeFactory implements TradeOffers.Factory {
    protected final int price;
    protected final ItemStack buy;
    protected final int maxUses;
    protected final int experience;
    protected final float multiplier;

    public BasicTradeFactory(int price, ItemStack buy, int maxUses, int experience) {
        this.price = price;
        this.buy = buy;
        this.maxUses = maxUses;
        this.experience = experience;
        this.multiplier = 1f;
    }

    public BasicTradeFactory(int price, ItemStack buy, int maxUses, int experience, float multiplier) {
        this.price = price;
        this.buy = buy;
        this.maxUses = maxUses;
        this.experience = experience;
        this.multiplier = multiplier;
    }

    @Nullable
    @Override
    public TradeOffer create(Entity entity, Random random) {
        return new TradeOffer(new ItemStack(Items.EMERALD, this.price), this.buy, this.maxUses, this.experience, this.multiplier);
    }
}
