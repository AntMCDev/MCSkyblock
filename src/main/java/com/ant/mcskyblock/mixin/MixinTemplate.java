package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.world.SkyblockChunkGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(Template.class)
public abstract class MixinTemplate {
    @Inject(at = @At("HEAD"), method = "placeInWorld(Lnet/minecraft/world/IServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/gen/feature/template/PlacementSettings;Ljava/util/Random;I)Z", cancellable = true)
    public void placeInWorld(IServerWorld p_237146_1_, BlockPos p_237146_2_, BlockPos p_237146_3_, PlacementSettings p_237146_4_, Random p_237146_5_, int p_237146_6_, CallbackInfoReturnable cir) {
        if (SkyblockChunkGenerator.isWorldSkyblock(p_237146_1_.getLevel()))
            cir.setReturnValue(true);
    }
}
