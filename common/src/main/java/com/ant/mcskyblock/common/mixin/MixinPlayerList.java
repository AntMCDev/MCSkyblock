package com.ant.mcskyblock.common.mixin;

import com.ant.mcskyblock.common.spawning.SpawnUtils;
import com.ant.mcskyblock.common.world.level.block.Blocks;
import com.ant.mcskyblock.common.world.level.levelgen.IslandGenerator;
import com.ant.mcskyblock.common.world.level.levelgen.SkyBlockChunkGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RespawnAnchorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;

@Mixin(PlayerList.class)
public class MixinPlayerList {
    @Final
    @Shadow
    private MinecraftServer server;

    @Inject(method = "respawn", at = @At("HEAD"))
    public void respawn(ServerPlayer serverPlayer, boolean bl, CallbackInfoReturnable<ServerPlayer> cir) {
        ServerLevel serverLevel = this.server.getLevel(serverPlayer.getRespawnDimension());
        if (serverLevel != null && serverLevel.getChunkSource().getGenerator() instanceof SkyBlockChunkGenerator) {
            BlockPos blockPos = serverPlayer.getRespawnPosition();
            if (blockPos == null || !SpawnUtils.hasBedOrAnchor(serverLevel, blockPos)) {
                Optional<IslandGenerator.Island> island = IslandGenerator.playerIsland(serverPlayer.getStringUUID());
                if (island.isPresent()) {
                    Optional<BlockPos> blockPos2 = SpawnUtils.findValidBoundingBox(serverLevel, ((IslandGenerator.PlayerIsland) island.get()).boundingBox());
                    blockPos2.ifPresent(pos -> serverPlayer.setRespawnPosition(this.server.overworld().dimension(), pos, 0, true, false));

                    if (blockPos2.isEmpty()) {
                        // Player island not generated, or completely mined - try to spawn on another player island
                        List<IslandGenerator.Island> islands = IslandGenerator.otherPlayerIslands(serverPlayer.getStringUUID());
                        boolean otherIslandFound = false;
                        for (IslandGenerator.Island i : islands) {
                            Optional<BlockPos> blockPos3 = SpawnUtils.findValidBoundingBox(serverLevel, ((IslandGenerator.PlayerIsland)i).boundingBox());
                            if (blockPos3.isPresent()) {
                                otherIslandFound = true;
                                serverPlayer.setRespawnPosition(serverLevel.dimension(), blockPos3.get(), 0, true, false);
                                break;
                            }
                        }
                        if (!otherIslandFound) {
                            // If in here, ALL starter islands are mined, so generate a non-lootable spawn block at the world origin
                            serverLevel.setBlockAndUpdate(new BlockPos(0, 64, 0), Blocks.spawnBlock().defaultBlockState());
                            serverPlayer.setRespawnPosition(serverLevel.dimension(), BlockPos.containing(0.5, 65.6, 0.5), 0, true, false);
                        }
                    }
                }
            }
        }
    }
}
