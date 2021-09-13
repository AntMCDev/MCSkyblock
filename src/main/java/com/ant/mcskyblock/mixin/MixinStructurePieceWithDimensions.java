package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.MCSkyBlock;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.structure.StructurePieceWithDimensions;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StructurePieceWithDimensions.class)
public class MixinStructurePieceWithDimensions extends MixinStructurePiece {
    @Shadow
    protected int hPos;

    @Inject(at = @At("HEAD"), method = "method_14839", cancellable = true)
    protected void method_14839(WorldAccess world, BlockBox boundingBox, int i, CallbackInfoReturnable<Boolean> cir) {
        if (MCSkyBlock.isSkyblockWorld(((ServerChunkManager)world.getChunkManager()).getChunkGenerator())) {
            if (this.hPos >= 0) {
                cir.setReturnValue(true);
            } else {
                int j = 0;
                int k = 0;
                BlockPos.Mutable mutable = new BlockPos.Mutable();

                for(int l = this.boundingBox.getMinZ(); l <= this.boundingBox.getMaxZ(); ++l) {
                    for(int m = this.boundingBox.getMinX(); m <= this.boundingBox.getMaxX(); ++m) {
                        mutable.set(m, 64, l);
                        if (boundingBox.contains(mutable)) {
                            j += world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, mutable).getY();
                            ++k;
                        }
                    }
                }

                i = 64;

                if (k == 0) {
                    cir.setReturnValue(false);
                } else {
                    this.hPos = j / k;
                    this.boundingBox.move(0, this.hPos - this.boundingBox.getMinY() + i, 0);
                    cir.setReturnValue(true);
                }
            }
        }
    }
}
