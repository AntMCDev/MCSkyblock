package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.MCSkyBlock;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.structure.ShiftableStructurePiece;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShiftableStructurePiece.class)
public class MixinShiftableStructurePiece extends MixinStructurePiece {
    @Shadow
    protected int hPos;

    @Inject(at = @At("HEAD"), method = "adjustToAverageHeight", cancellable = true)
    protected void adjustToAverageHeight(WorldAccess world, BlockBox boundingBox, int deltaY, CallbackInfoReturnable<Boolean> cir) {
        if (MCSkyBlock.isSkyblockWorld(((ServerChunkManager)world.getChunkManager()).getChunkGenerator())) {
            if (this.hPos >= 0) {
                cir.setReturnValue(true);
            } else {
                int i = 0;
                int j = 0;
                BlockPos.Mutable mutable = new BlockPos.Mutable();

                for(int k = this.boundingBox.getMinZ(); k <= this.boundingBox.getMaxZ(); ++k) {
                    for(int l = this.boundingBox.getMinX(); l <= this.boundingBox.getMaxX(); ++l) {
                        mutable.set(l, 64, k);
                        if (boundingBox.contains(mutable)) {
                            i += world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, mutable).getY();
                            ++j;
                        }
                    }
                }

                deltaY = 64 - ((ServerChunkManager) world.getChunkManager()).getChunkGenerator().getMinimumY();

                if (j == 0) {
                    cir.setReturnValue(false);
                } else {
                    this.hPos = i / j;
                    this.boundingBox.move(0, this.hPos - this.boundingBox.getMinY() + deltaY, 0);
                    cir.setReturnValue(true);
                }
            }
        }
    }
}
