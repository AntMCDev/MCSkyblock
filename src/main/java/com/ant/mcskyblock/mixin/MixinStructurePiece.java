package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.world.SkyblockChunkGenerator;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(StructurePiece.class)
public abstract class MixinStructurePiece {
    @Shadow
    protected MutableBoundingBox boundingBox;

    @Inject(at = @At("HEAD"), method = "placeBlock", cancellable = true)
    protected void placeBlock(ISeedReader p_175811_1_, BlockState p_175811_2_, int p_175811_3_, int p_175811_4_, int p_175811_5_, MutableBoundingBox p_175811_6_, CallbackInfo ci) {
        if (SkyblockChunkGenerator.isWorldSkyblock(p_175811_1_.getLevel()))
            ci.cancel();
    }

    @Inject(at = @At("HEAD"), method = "fillColumnDown", cancellable = true)
    protected void fillColumnDown(ISeedReader p_175808_1_, BlockState p_175808_2_, int p_175808_3_, int p_175808_4_, int p_175808_5_, MutableBoundingBox p_175808_6_, CallbackInfo ci) {
        if (SkyblockChunkGenerator.isWorldSkyblock(p_175808_1_.getLevel()))
            ci.cancel();
    }

    @Inject(at = @At("HEAD"), method = "createChest(Lnet/minecraft/world/ISeedReader;Lnet/minecraft/util/math/MutableBoundingBox;Ljava/util/Random;IIILnet/minecraft/util/ResourceLocation;)Z", cancellable = true)
    protected void createChest(ISeedReader p_186167_1_, MutableBoundingBox p_186167_2_, Random p_186167_3_, int p_186167_4_, int p_186167_5_, int p_186167_6_, ResourceLocation p_186167_7, CallbackInfoReturnable cir) {
        if (SkyblockChunkGenerator.isWorldSkyblock(p_186167_1_.getLevel()))
            cir.setReturnValue(true);
    }

    @Inject(at = @At("HEAD"), method = "createChest(Lnet/minecraft/world/IServerWorld;Lnet/minecraft/util/math/MutableBoundingBox;Ljava/util/Random;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/ResourceLocation;Lnet/minecraft/block/BlockState;)Z", cancellable = true)
    protected void createChest(IServerWorld p_191080_1_, MutableBoundingBox p_191080_2_, Random p_191080_3_, BlockPos p_191080_4_, ResourceLocation p_191080_5_, BlockState p_191080_6_, CallbackInfoReturnable cir) {
        if (SkyblockChunkGenerator.isWorldSkyblock(p_191080_1_.getLevel()))
            cir.setReturnValue(true);
    }
}
