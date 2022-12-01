package com.ant.mcskyblock.forge.mixin;

import com.ant.mcskyblock.common.loot.LootHelper;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(LootTables.class)
public class MixinLootTables {
    @Inject(at = @At("RETURN"), method = "apply")
    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager arg, ProfilerFiller arg2, CallbackInfo ci) {
        map.forEach((res, jsonElement) -> {
            LootHelper.register((LootTables)((Object)this), res, LootTable.lootTable());
        });
    }
}
