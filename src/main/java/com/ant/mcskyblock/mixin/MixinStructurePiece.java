package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.skyblock.SkyblockChunkGenerator;
import net.minecraft.block.BlockState;
import net.minecraft.structure.StructurePiece;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StructurePiece.class)
public class MixinStructurePiece {
    @Shadow
    protected BlockBox boundingBox;

    @Inject(at = @At("HEAD"), method = "addBlock", cancellable = true)
    protected void addBlock(StructureWorldAccess world, BlockState block, int x, int y, int z, BlockBox box, CallbackInfo ci) {
        if (world.toServerWorld().getChunkManager().getChunkGenerator() instanceof SkyblockChunkGenerator) {
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "fillDownwards", cancellable = true)
    protected void fillDownwards(StructureWorldAccess world, BlockState state, int x, int y, int z, BlockBox box, CallbackInfo ci) {
        if (world.toServerWorld().getChunkManager().getChunkGenerator() instanceof SkyblockChunkGenerator) {
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "addChest(Lnet/minecraft/world/StructureWorldAccess;Lnet/minecraft/util/math/BlockBox;Lnet/minecraft/util/math/random/Random;IIILnet/minecraft/util/Identifier;)Z", cancellable = true)
    protected void addChest(StructureWorldAccess world, BlockBox boundingBox, Random random, int x, int y, int z, Identifier lootTableId, CallbackInfoReturnable<Boolean> cir) {
        if (world.toServerWorld().getChunkManager().getChunkGenerator() instanceof SkyblockChunkGenerator) {
            cir.setReturnValue(true);
        }
    }

    @Inject(at = @At("HEAD"), method = "addChest(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/util/math/BlockBox;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/Identifier;Lnet/minecraft/block/BlockState;)Z", cancellable = true)
    protected void addChest(ServerWorldAccess world, BlockBox boundingBox, Random random, BlockPos pos, Identifier lootTableId, BlockState block, CallbackInfoReturnable<Boolean> cir) {
        if (world.toServerWorld().getChunkManager().getChunkGenerator() instanceof SkyblockChunkGenerator) {
            cir.setReturnValue(true);
        }
    }
}
