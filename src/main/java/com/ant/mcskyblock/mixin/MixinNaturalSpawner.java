package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.common.config.SkyBlockConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NaturalSpawner.class)
public class MixinNaturalSpawner {
    @Inject(at = @At("RETURN"), method = "isSpawnPositionOk", cancellable = true)
    private static void canSpawn(SpawnPlacements.Type location, LevelReader world, BlockPos pos, @Nullable EntityType<?> entityType, CallbackInfoReturnable<Boolean> cir) {
        if ( SkyBlockConfig.WorldGen.STOP_BAT_SPAWNS && entityType == EntityType.BAT) {
            cir.setReturnValue(false);
        }
    }
}
