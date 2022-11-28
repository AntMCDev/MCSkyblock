package com.ant.mcskyblock.fabric.mixin;

import com.ant.mcskyblock.common.SkyBlock;
import com.ant.mcskyblock.common.config.Config;
import com.google.common.collect.Lists;
import net.minecraft.Util;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.SwiftSneakEnchantment;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(EnchantmentMenu.class)
public class MixinEnchantmentMenu {
    @Final
    @Shadow
    private ContainerLevelAccess access;

    @Final
    @Shadow
    private RandomSource random;

    @Final
    @Shadow
    private DataSlot enchantmentSeed;

    @Inject(at = @At("HEAD"), method = "getEnchantmentList", cancellable = true)
    private void getEnchantmentList(ItemStack itemStack, int i, int j, CallbackInfoReturnable<List<EnchantmentInstance>> cir) {
        if (Config.INSTANCE.crafting.ANCIENT_CITY_SWIFT_SNEAK) {
            this.random.setSeed(this.enchantmentSeed.get() + i);
            List<EnchantmentInstance> list = selectEnchantment(this.random, itemStack, j, false);
            if (itemStack.is(Items.BOOK) && list.size() > 1) {
                list.remove(this.random.nextInt(list.size()));
            }
            cir.setReturnValue(list);
        }
    }

    private List<EnchantmentInstance> selectEnchantment(RandomSource randomSource, ItemStack itemStack, int i, boolean bl) {
        ArrayList<EnchantmentInstance> list = Lists.newArrayList();
        Item item = itemStack.getItem();
        int j = item.getEnchantmentValue();
        if (j <= 0) {
            return list;
        }
        i += 1 + randomSource.nextInt(j / 4 + 1) + randomSource.nextInt(j / 4 + 1);
        float f = (randomSource.nextFloat() + randomSource.nextFloat() - 1.0f) * 0.15f;
        List<EnchantmentInstance> list2 = getAvailableEnchantmentResults(i = Mth.clamp(Math.round((float)i + (float)i * f), 1, Integer.MAX_VALUE), itemStack, bl);
        if (!list2.isEmpty()) {
            WeightedRandom.getRandomItem(randomSource, list2).ifPresent(list::add);
            while (randomSource.nextInt(50) <= i) {
                if (!list.isEmpty()) {
                    EnchantmentHelper.filterCompatibleEnchantments(list2, Util.lastOf(list));
                }
                if (list2.isEmpty()) break;
                WeightedRandom.getRandomItem(randomSource, list2).ifPresent(list::add);
                i /= 2;
            }
        }
        return list;
    }

    private List<EnchantmentInstance> getAvailableEnchantmentResults(int i, ItemStack itemStack, boolean bl) {
        boolean inAncientCity = access.evaluate((level, blockPos) -> LocationPredicate.inStructure(BuiltinStructures.ANCIENT_CITY).matches((ServerLevel)level, blockPos.getX(), blockPos.getY(), blockPos.getZ())).orElse(false);
        ArrayList<EnchantmentInstance> list = Lists.newArrayList();
        Item item = itemStack.getItem();
        boolean bl2 = itemStack.is(Items.BOOK);
        block0: for (Enchantment enchantment : Registry.ENCHANTMENT) {
            if ((enchantment.isTreasureOnly() && !bl || !enchantment.isDiscoverable() || !enchantment.category.canEnchant(item) && !bl2) && !(enchantment instanceof SwiftSneakEnchantment && inAncientCity)) continue;
            for (int j = enchantment.getMaxLevel(); j > enchantment.getMinLevel() - 1; --j) {
                if (i < enchantment.getMinCost(j) || i > enchantment.getMaxCost(j)) continue;
                list.add(new EnchantmentInstance(enchantment, j));
                continue block0;
            }
        }
        return list;
    }
}
