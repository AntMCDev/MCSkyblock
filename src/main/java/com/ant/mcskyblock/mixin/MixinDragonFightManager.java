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
    private BlockPos exitPortalLocation;

    @Shadow
    @Final
    private ServerWorld world;

    @Inject(at = @At("HEAD"), method = "generatePortal", cancellable = true)
    private void generatePortal(boolean active, CallbackInfo ci) {
        if (this.world.getChunkProvider().getChunkGenerator() instanceof SkyblockChunkGenerator) {
            EndPodiumFeature endpodiumfeature = new EndPodiumFeature(active);
            if (this.exitPortalLocation == null) {
                for (this.exitPortalLocation = this.world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.END_PODIUM_LOCATION).down(); this.world.getBlockState(this.exitPortalLocation).isIn(Blocks.BEDROCK) && this.exitPortalLocation.getY() > this.world.getSeaLevel(); this.exitPortalLocation = this.exitPortalLocation.down()) {
                }
            }

            BlockPos.Mutable pos = this.exitPortalLocation.toMutable();
            pos.setY(Math.max(this.exitPortalLocation.getY(), 2));
            this.exitPortalLocation = pos.toImmutable();

            endpodiumfeature.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).generate(this.world, this.world.getChunkProvider().getChunkGenerator(), new Random(), this.exitPortalLocation);

            ci.cancel();
        }
    }
}
