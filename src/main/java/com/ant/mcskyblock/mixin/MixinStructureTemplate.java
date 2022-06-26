package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.MCSkyBlock;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.gen.structure.Structure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StructureTemplate.class)
public class MixinStructureTemplate {
    @Inject(at = @At("HEAD"), method = "place", cancellable = true)
    public void place(ServerWorldAccess world, BlockPos pos, BlockPos pivot, StructurePlacementData placementData, Random random, int flags, CallbackInfoReturnable<Boolean> cir) {
        if (MCSkyBlock.isSkyblockWorld(world.toServerWorld().getChunkManager().getChunkGenerator())) {
            cir.setReturnValue(true);
        }
    }
}
