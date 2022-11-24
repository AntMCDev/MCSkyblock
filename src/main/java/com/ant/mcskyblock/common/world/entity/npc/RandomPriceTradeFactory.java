package com.ant.mcskyblock.common.world.entity.npc;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import org.jetbrains.annotations.Nullable;

/**
 * [COMMON] HELPER CLASS - This class is used to define randomized trading information for villagers
 */
public class RandomPriceTradeFactory extends BasicTradeFactory {
    protected int maxAdditionalEmeralds;

    public RandomPriceTradeFactory(int price, ItemStack buy, int maxUses, int experience, float multiplier, int maxAdditionalEmeralds) {
        super(price, buy, maxUses, experience, multiplier);
        this.maxAdditionalEmeralds = maxAdditionalEmeralds;
    }

    @Nullable
    @Override
    public MerchantOffer getOffer(Entity entity, RandomSource randomSource) {
        int i = randomSource.nextInt( maxAdditionalEmeralds );
        return new MerchantOffer(new ItemStack(Items.EMERALD, this.price + i), this.buy, this.maxUses, this.experience, this.multiplier);
    }
}
