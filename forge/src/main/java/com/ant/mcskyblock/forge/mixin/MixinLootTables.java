package com.ant.mcskyblock.forge.mixin;

import com.ant.mcskyblock.common.loot.LootHelper;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.storage.loot.LootDataManager;
import net.minecraft.world.level.storage.loot.LootDataType;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(LootDataManager.class)
public class MixinLootTables {
    @Inject(at = @At("RETURN"), method = "apply")
    protected void apply(Map<LootDataType<?>, Map<ResourceLocation, ?>> map2, CallbackInfo ci) {
        map2.forEach((res, jsonElement) -> {
            jsonElement.forEach((k, v) -> {
                LootHelper.register((LootDataManager)((Object)this), k, LootTable.lootTable());
            });
        });
    }
}
