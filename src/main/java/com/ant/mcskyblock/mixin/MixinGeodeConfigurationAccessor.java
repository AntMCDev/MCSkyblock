package com.ant.mcskyblock.mixin;

import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GeodeConfiguration.class)
public interface MixinGeodeConfigurationAccessor {
    @Mutable
    @Accessor("invalidBlocksThreshold")
    void setInvalidBlocksThreshold(int invalidBlocksThreshold);
}
