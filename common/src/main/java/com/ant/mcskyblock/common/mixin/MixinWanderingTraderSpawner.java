package com.ant.mcskyblock.common.mixin;

import com.ant.mcskyblock.common.config.Config;
import com.ant.mcskyblock.common.world.level.levelgen.SkyBlockChunkGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.npc.WanderingTraderSpawner;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.storage.ServerLevelData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(WanderingTraderSpawner.class)
public abstract class MixinWanderingTraderSpawner {
    @Shadow
    private int tickDelay;

    @Final
    @Shadow
    private ServerLevelData serverLevelData;

    @Shadow
    abstract void tryToSpawnLlamaFor(ServerLevel serverLevel, WanderingTrader wanderingTrader, int i);

    @Shadow
    abstract BlockPos findSpawnPositionNear(LevelReader levelReader, BlockPos blockPos, int i);

    @Shadow
    abstract boolean hasEnoughSpace(BlockGetter blockGetter, BlockPos blockPos);

    @Inject(at = @At("HEAD"), method = "tick", cancellable = true)
    public void tick(ServerLevel serverLevel, boolean bl, boolean bl2, CallbackInfoReturnable<Integer> cir) {
        if (serverLevel.getChunkSource().getGenerator() instanceof SkyBlockChunkGenerator && Config.INSTANCE.trading.SCHEDULED_WANDERING_TRADER && serverLevel.getGameRules().getBoolean(GameRules.RULE_DO_TRADER_SPAWNING) && serverLevel.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)) {
            if ((serverLevel.getDayTime() / 24000L % 2147483647L) % 14 == 0 && serverLevel.getDayTime() % 24000L == 6000L) {
                if (this.spawn(serverLevel)) {
                    cir.setReturnValue(1);
                    return;
                }
            }
            cir.setReturnValue(0);
        }
    }

    private boolean spawn(ServerLevel serverLevel) {
        Player player = serverLevel.getRandomPlayer();
        if (player == null) {
            return true;
        } else {
            BlockPos blockPos = player.blockPosition();
            PoiManager poiManager = serverLevel.getPoiManager();
            Optional<BlockPos> optional = poiManager.find((holder) -> holder.is(PoiTypes.MEETING), (blockPosx) -> true, blockPos, 48, PoiManager.Occupancy.ANY);
            BlockPos blockPos2 = optional.orElse(blockPos);
            BlockPos blockPos3 = this.findSpawnPositionNear(serverLevel, blockPos2, 48);
            if (blockPos3 != null && this.hasEnoughSpace(serverLevel, blockPos3)) {
                if (serverLevel.getBiome(blockPos3).is(BiomeTags.WITHOUT_WANDERING_TRADER_SPAWNS)) {
                    return false;
                }

                WanderingTrader wanderingTrader = EntityType.WANDERING_TRADER.spawn(serverLevel, blockPos3, MobSpawnType.EVENT);
                if (wanderingTrader != null) {
                    for(int j = 0; j < 2; ++j) {
                        this.tryToSpawnLlamaFor(serverLevel, wanderingTrader, 4);
                    }

                    this.serverLevelData.setWanderingTraderId(wanderingTrader.getUUID());
                    wanderingTrader.setDespawnDelay(48000);
                    wanderingTrader.setWanderTarget(blockPos2);
                    wanderingTrader.restrictTo(blockPos2, 16);
                    return true;
                }
            }

            return false;
        }
    }
}
