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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.Random;

@Mixin(StructurePiece.class)
public abstract class MixinStructurePiece {
    @Inject(at = @At("HEAD"), method = "setBlockState", cancellable = true)
    protected void setBlockState(ISeedReader worldIn, BlockState blockstateIn, int x, int y, int z, MutableBoundingBox boundingboxIn, CallbackInfo ci) {
        if (SkyblockChunkGenerator.isWorldSkyblock(worldIn.getWorld()))
            ci.cancel();
    }

    @Inject(at = @At("HEAD"), method = "replaceAirAndLiquidDownwards", cancellable = true)
    protected void replaceAirAndLiquidDownwards(ISeedReader worldIn, BlockState blockstateIn, int x, int y, int z, MutableBoundingBox boundingboxIn, CallbackInfo ci) {
        if (SkyblockChunkGenerator.isWorldSkyblock(worldIn.getWorld()))
            ci.cancel();
    }

    @Inject(at = @At("HEAD"), method = "(Lnet/minecraft/world/ISeedReader;Lnet/minecraft/util/math/MutableBoundingBox;Ljava/util/Random;IIILnet/minecraft/util/ResourceLocation;)Z", cancellable = true)
    protected void generateChest(ISeedReader worldIn, MutableBoundingBox structurebb, Random randomIn, int x, int y, int z, ResourceLocation loot, CallbackInfoReturnable cir) {
        if (SkyblockChunkGenerator.isWorldSkyblock(worldIn.getWorld()))
            cir.setReturnValue(true);
    }

    @Inject(at = @At("HEAD"), method = "generateChest(Lnet/minecraft/world/IServerWorld;Lnet/minecraft/util/math/MutableBoundingBox;Ljava/util/Random;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/ResourceLocation;Lnet/minecraft/block/BlockState;)Z", cancellable = true)
    protected void generateChest(IServerWorld worldIn, MutableBoundingBox boundsIn, Random rand, BlockPos posIn, ResourceLocation resourceLocationIn, BlockState p_191080_6_, CallbackInfoReturnable cir) {
        if (SkyblockChunkGenerator.isWorldSkyblock(worldIn.getWorld()))
            cir.setReturnValue(true);
    }
}
