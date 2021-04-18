package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.MCSkyblock;
import com.ant.mcskyblock.config.ConfigHandler;
import com.ant.mcskyblock.world.SkyblockChunkGenerator;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.feature.structure.ScatteredStructurePiece;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ScatteredStructurePiece.class)
public abstract class MixinScatteredStructurePiece extends MixinStructurePiece {
    @Shadow
    protected int hPos = -1;

    @Inject(at = @At("HEAD"), method = "isInsideBounds", cancellable = true)
    protected void isInsideBounds(IWorld worldIn, MutableBoundingBox boundsIn, int heightIn, CallbackInfoReturnable cir) {
        if (SkyblockChunkGenerator.isWorldSkyblock(((WorldGenRegion)worldIn).getWorld()) && !ConfigHandler.COMMON.witchHutYZero.get()) {
            if (this.hPos >= 0) {
                cir.setReturnValue(true);
            } else {
                int i = 0;
                int j = 0;
                BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

                for (int k = this.boundingBox.minZ; k <= this.boundingBox.maxZ; ++k) {
                    for (int l = this.boundingBox.minX; l <= this.boundingBox.maxX; ++l) {
                        blockpos$mutable.setPos(l, 64, k);
                        if (boundsIn.isVecInside(blockpos$mutable)) {
                            i += worldIn.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, blockpos$mutable).getY();
                            ++j;
                        }
                    }
                }

                heightIn = 64;

                if (j == 0) {
                    cir.setReturnValue(false);
                } else {
                    this.hPos = i / j;
                    this.boundingBox.offset(0, this.hPos - this.boundingBox.minY + heightIn, 0);
                    cir.setReturnValue(true);
                }
            }
        }
    }
}
