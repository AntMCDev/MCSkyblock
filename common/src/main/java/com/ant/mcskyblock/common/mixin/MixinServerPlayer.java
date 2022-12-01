package com.ant.mcskyblock.common.mixin;

import com.ant.mcskyblock.common.world.level.levelgen.IslandGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class MixinServerPlayer {
    @Inject(method = "die", at = @At("HEAD"))
    public void init(CallbackInfo ci) {
        ServerPlayer player = (ServerPlayer)((Object)this);
        if (player.getRespawnPosition() == null) {
            BlockPos b = IslandGenerator.islandPosition(player.getStringUUID());
            if (b != null) { player.setRespawnPosition(player.getServer().overworld().dimension(), b, 0, true, false); }
        }
    }
}