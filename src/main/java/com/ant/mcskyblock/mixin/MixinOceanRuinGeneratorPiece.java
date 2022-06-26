package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.MCSkyBlock;
import com.ant.mcskyblock.world.SkyblockChunkGenerator;
import net.minecraft.structure.OceanRuinGenerator;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OceanRuinGenerator.Piece.class)
public class MixinOceanRuinGeneratorPiece {
    @Inject(at = @At("HEAD"), method = "handleMetadata", cancellable = true)
    protected void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess world, Random random, BlockBox boundingBox, CallbackInfo ci) {
        if (MCSkyBlock.isSkyblockWorld(world.toServerWorld().getChunkManager().getChunkGenerator())) {
            ci.cancel();
        }
    }
}
