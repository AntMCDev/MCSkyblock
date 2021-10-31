package com.ant.mcskyblock.mixin;

import com.google.common.collect.ImmutableList;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.collection.Weighted;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Pool.class)
public interface MixinPoolAccessor<E extends Weighted> {
    @Final
    @Mutable
    @Accessor("entries")
    void setEntries(ImmutableList<E> entries);
}
