package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.common.world.level.levelgen.SkyBlockChunkGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndDragonFight.class)
public class MixinEndDragonFight {
    @Shadow
    private BlockPos portalLocation;

    @Final
    @Shadow
    private ServerLevel level;

    @Inject(at = @At("HEAD"), method = "spawnExitPortal", cancellable = true)
    private void spawnExitPortal(boolean bl, CallbackInfo ci) {
        if (level.getChunkSource().getGenerator() instanceof SkyBlockChunkGenerator) {
            EndPodiumFeature endPortalFeature = new EndPodiumFeature(bl);
            if (this.portalLocation == null) {
                for (this.portalLocation = this.level.getHeightmapPos( Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,  EndPodiumFeature.END_PODIUM_LOCATION ).below();
                     this.level.getBlockState(this.portalLocation).is(Blocks.BEDROCK) && this.portalLocation.getY() > this.level.getSeaLevel();
                     this.portalLocation = this.portalLocation.below() ) {

                }
            }

            BlockPos.MutableBlockPos pos = this.portalLocation.mutable();
            pos.setY(Math.max(this.portalLocation.getY(), 2));
            this.portalLocation = pos.immutable();

            endPortalFeature.place(FeatureConfiguration.NONE, this.level, this.level.getChunkSource().getGenerator(),
                    RandomSource.create(), this.portalLocation);

            ci.cancel();
        }
    }
}