package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.MCSkyblock;
import com.ant.mcskyblock.world.SkyblockChunkGenerator;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.end.DragonFightManager;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.EndPodiumFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(DragonFightManager.class)
public abstract class MixinDragonFightManager {
    @Shadow
    private BlockPos portalLocation;

    @Shadow
    @Final
    private ServerWorld level;

    @Inject(at = @At("HEAD"), method = "spawnExitPortal", cancellable = true)
    private void spawnExitPortal(boolean p_186094_1_, CallbackInfo ci) {
        if (this.level.getChunkSource().generator instanceof SkyblockChunkGenerator) {
            EndPodiumFeature endpodiumfeature = new EndPodiumFeature(p_186094_1_);
            if (this.portalLocation == null) {
                for (this.portalLocation = this.level.getHeightmapPos(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.END_PODIUM_LOCATION).below(); this.level.getBlockState(this.portalLocation).is(Blocks.BEDROCK) && this.portalLocation.getY() > this.level.getSeaLevel(); this.portalLocation = this.portalLocation.below()) {
                }
            }

            BlockPos.Mutable pos = this.portalLocation.mutable();
            pos.setY(Math.max(this.portalLocation.getY(), 2));
            this.portalLocation = pos.immutable();

            endpodiumfeature.configured(IFeatureConfig.NONE).place(this.level, this.level.getChunkSource().getGenerator(), new Random(), this.portalLocation);

            ci.cancel();
        }
    }
}
