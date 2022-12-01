package com.ant.mcskyblock.common.mixin;

import com.ant.mcskyblock.common.world.level.levelgen.IslandGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(PlayerList.class)
public class MixinPlayerList {
    @Final
    @Shadow
    private MinecraftServer server;

    @Inject(method = "respawn", at = @At("HEAD"))
    public void respawn(ServerPlayer serverPlayer, boolean bl, CallbackInfoReturnable<ServerPlayer> cir) {
        BlockPos blockPos = serverPlayer.getRespawnPosition();
        float f = serverPlayer.getRespawnAngle();
        boolean bl2 = serverPlayer.isRespawnForced();
        ServerLevel serverLevel = this.server.getLevel(serverPlayer.getRespawnDimension());
        Optional<Vec3> optional = serverLevel != null && blockPos != null ? Player.findRespawnPositionAndUseSpawnBlock(serverLevel, blockPos, f, bl2, bl) : Optional.empty();
        if (optional.isEmpty() && blockPos != null) {
            BlockPos blockPos2 = IslandGenerator.islandPosition(serverPlayer.getStringUUID());
            if (blockPos2 != null) {
                serverPlayer.setRespawnPosition(this.server.overworld().dimension(), blockPos2, 0, true, false);
            }
        }
    }
}
