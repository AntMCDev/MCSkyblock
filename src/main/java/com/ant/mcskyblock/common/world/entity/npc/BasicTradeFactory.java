package com.ant.mcskyblock.common.world.entity.npc;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import org.jetbrains.annotations.Nullable;


// NON FABRIC
public class BasicTradeFactory implements VillagerTrades.ItemListing {
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
    public MerchantOffer getOffer(Entity entity, RandomSource randomSource) {
        return new MerchantOffer(new ItemStack( Items.EMERALD, this.price), this.buy, this.maxUses, this.experience, this.multiplier);
    }
}
