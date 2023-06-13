package com.ant.mcskyblock.common.mixin;

import com.ant.mcskyblock.common.config.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.EnumSet;

@Mixin(Endermite.class)
public class MixinEndermite extends Monster {
    protected MixinEndermite(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(at = @At("HEAD"), method = "registerGoals")
    protected void registerGoals(CallbackInfo ci) {
        if (Config.INSTANCE.spawning.ENDERMITES_SPAWN_ENDSTONE) {
            this.goalSelector.addGoal(5, new EndermiteMergeWithStoneGoal((Endermite) ((Object) this)));
        }
    }

    static class EndermiteMergeWithStoneGoal
            extends RandomStrollGoal {
        @Nullable
        private Direction selectedDirection;
        private boolean doMerge;

        public EndermiteMergeWithStoneGoal(Endermite endermite) {
            super(endermite, 1.0, 10);
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (this.mob.getTarget() != null) {
                return false;
            }
            if (!this.mob.getNavigation().isDone()) {
                return false;
            }
            RandomSource randomSource = this.mob.getRandom();
            if (this.mob.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && randomSource.nextInt(EndermiteMergeWithStoneGoal.reducedTickDelay(10)) == 0) {
                this.selectedDirection = Direction.getRandom(randomSource);
                BlockPos blockPos = BlockPos.containing(this.mob.getX(), this.mob.getY() + 0.5, this.mob.getZ()).relative(this.selectedDirection);
                BlockState blockState = this.mob.level().getBlockState(blockPos);
                if (InfestedBlock.isCompatibleHostBlock(blockState)) {
                    this.doMerge = true;
                    return true;
                }
            }
            this.doMerge = false;
            return super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            if (this.doMerge) {
                return false;
            }
            return super.canContinueToUse();
        }

        @Override
        public void start() {
            if (!this.doMerge) {
                super.start();
                return;
            }
            Level levelAccessor = this.mob.level();
            BlockPos blockPos = BlockPos.containing(this.mob.getX(), this.mob.getY() + 0.5, this.mob.getZ()).relative(this.selectedDirection);
            BlockState blockState = levelAccessor.getBlockState(blockPos);
            if (InfestedBlock.isCompatibleHostBlock(blockState)) {
                levelAccessor.setBlock(blockPos, Blocks.END_STONE.defaultBlockState(), 3);
                this.mob.spawnAnim();
                this.mob.discard();
            }
        }
    }
}
