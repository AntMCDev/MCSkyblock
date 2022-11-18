package com.ant.mcskyblock.mixin;

import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LootPool.class)
public interface MixinLootPoolAccessor {
    @Final
    @Accessor
    LootPoolEntryContainer[] getEntries();

    @Mutable
    @Accessor("entries")
    void setEntries(LootPoolEntryContainer[] entries);
}
