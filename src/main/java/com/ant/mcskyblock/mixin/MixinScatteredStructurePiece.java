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
    protected int heightPosition = -1;

    @Inject(at = @At("HEAD"), method = "updateAverageGroundHeight", cancellable = true)
    protected void updateAverageGroundHeight(IWorld p_202580_1_, MutableBoundingBox p_202580_2_, int p_202580_3_, CallbackInfoReturnable cir) {
        if (SkyblockChunkGenerator.isWorldSkyblock(((WorldGenRegion)p_202580_1_).getLevel()) && !ConfigHandler.COMMON.witchHutYZero.get()) {
            if (this.heightPosition >= 0) {
                cir.setReturnValue(true);
            } else {
                int i = 0;
                int j = 0;
                BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

                for (int k = this.boundingBox.z0; k <= this.boundingBox.z1; ++k) {
                    for (int l = this.boundingBox.x0; l <= this.boundingBox.x1; ++l) {
                        blockpos$mutable.set(l, 64, k);
                        if (p_202580_2_.isInside(blockpos$mutable)) {
                            i += p_202580_1_.getHeightmapPos(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, blockpos$mutable).getY();
                            ++j;
                        }
                    }
                }

                p_202580_3_ = 64;

                if (j == 0) {
                    cir.setReturnValue(false);
                } else {
                    this.heightPosition = i / j;
                    this.boundingBox.move(0, this.heightPosition - this.boundingBox.y0 + p_202580_3_, 0);
                    cir.setReturnValue(true);
                }
            }
        }
    }
}
