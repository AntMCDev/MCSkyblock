package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.config.ConfigHandler;
import com.ant.mcskyblock.skyblock.SkyblockChunkGenerator;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import org.spongepowered.asm.mixin.Final;
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

    @Final
    @Shadow
    private StructurePieceType type;

    @Inject(at = @At("HEAD"), method = "addBlock", cancellable = true)
    protected void addBlock(StructureWorldAccess world, BlockState block, int x, int y, int z, BlockBox box, CallbackInfo ci) {
        if (world.toServerWorld().getChunkManager().getChunkGenerator() instanceof SkyblockChunkGenerator) {
            if (!ConfigHandler.Common.GENERATE_FORTRESS || !isNetherFortress()) {
                ci.cancel();
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "fillDownwards", cancellable = true)
    protected void fillDownwards(StructureWorldAccess world, BlockState state, int x, int y, int z, BlockBox box, CallbackInfo ci) {
        if (world.toServerWorld().getChunkManager().getChunkGenerator() instanceof SkyblockChunkGenerator) {
            if (!ConfigHandler.Common.GENERATE_FORTRESS || !isNetherFortress()) {
                ci.cancel();
            }
        }
    }

    private boolean isNetherFortress() {
        return type == StructurePieceType.NETHER_FORTRESS_BRIDGE ||
                type == StructurePieceType.NETHER_FORTRESS_BRIDGE_CROSSING ||
                type == StructurePieceType.NETHER_FORTRESS_BRIDGE_END ||
                type == StructurePieceType.NETHER_FORTRESS_BRIDGE_PLATFORM ||
                type == StructurePieceType.NETHER_FORTRESS_BRIDGE_STAIRS ||
                type == StructurePieceType.NETHER_FORTRESS_BRIDGE_SMALL_CROSSING ||
                type == StructurePieceType.NETHER_FORTRESS_CORRIDOR_BALCONY ||
                type == StructurePieceType.NETHER_FORTRESS_CORRIDOR_CROSSING ||
                type == StructurePieceType.NETHER_FORTRESS_CORRIDOR_EXIT ||
                type == StructurePieceType.NETHER_FORTRESS_CORRIDOR_LEFT_TURN ||
                type == StructurePieceType.NETHER_FORTRESS_CORRIDOR_NETHER_WARTS_ROOM ||
                type == StructurePieceType.NETHER_FORTRESS_CORRIDOR_RIGHT_TURN ||
                type == StructurePieceType.NETHER_FORTRESS_CORRIDOR_STAIRS ||
                type == StructurePieceType.NETHER_FORTRESS_SMALL_CORRIDOR ||
                type == StructurePieceType.NETHER_FORTRESS_START;
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
