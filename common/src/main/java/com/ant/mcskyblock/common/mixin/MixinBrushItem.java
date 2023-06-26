package com.ant.mcskyblock.common.mixin;

import com.ant.mcskyblock.common.SkyBlock;
import com.ant.mcskyblock.common.utils.LocationUtils;
import com.ant.mcskyblock.common.world.level.levelgen.SkyBlockChunkGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BrushItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mixin(BrushItem.class)
public abstract class MixinBrushItem {
    @Shadow
    abstract HitResult calculateHitResult(LivingEntity livingEntity);

    @Shadow
    abstract int getUseDuration(ItemStack itemStack);

    @Shadow
    abstract void spawnDustParticles(Level level, BlockHitResult blockHitResult, BlockState blockState, Vec3 vec3, HumanoidArm humanoidArm);

    @Inject(at = @At("HEAD"), method = "onUseTick", cancellable = true)
    public void onUseTick(Level level, LivingEntity livingEntity2, ItemStack itemStack, int i, CallbackInfo ci) {
        boolean bl;
        BlockHitResult blockHitResult;
        Player player;
        block9: {
            block8: {
                if (i < 0 || !(livingEntity2 instanceof Player)) {
                    livingEntity2.releaseUsingItem();
                    return;
                }
                player = (Player)livingEntity2;
                HitResult hitResult = this.calculateHitResult(livingEntity2);
                if (!(hitResult instanceof BlockHitResult)) break block8;
                blockHitResult = (BlockHitResult)hitResult;
                if (hitResult.getType() == HitResult.Type.BLOCK) break block9;
            }
            livingEntity2.releaseUsingItem();
            return;
        }
        int j = this.getUseDuration(itemStack) - i + 1;
        boolean bl2 = bl = j % 10 == 5;
        if (bl) {
            BrushableBlockEntity brushableBlockEntity;
            boolean bl22;
            SoundEvent soundEvent;
            BlockPos blockPos = blockHitResult.getBlockPos();
            BlockState blockState = level.getBlockState(blockPos);
            HumanoidArm humanoidArm = livingEntity2.getUsedItemHand() == InteractionHand.MAIN_HAND ? player.getMainArm() : player.getMainArm().getOpposite();
            this.spawnDustParticles(level, blockHitResult, blockState, livingEntity2.getViewVector(0.0f), humanoidArm);
            Object object = blockState.getBlock();
            if (object instanceof BrushableBlock brushableBlock) {
                soundEvent = brushableBlock.getBrushSound();
            } else {
                soundEvent = SoundEvents.BRUSH_GENERIC;
            }
            level.playSound(player, blockPos, soundEvent, SoundSource.BLOCKS);
            if (!level.isClientSide() && (object = level.getBlockEntity(blockPos)) instanceof BrushableBlockEntity && (bl22 = (brushableBlockEntity = (BrushableBlockEntity)object).brush(level.getGameTime(), player, blockHitResult.getDirection()))) {
                EquipmentSlot equipmentSlot = itemStack.equals(player.getItemBySlot(EquipmentSlot.OFFHAND)) ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
                itemStack.hurtAndBreak(1, livingEntity2, livingEntity -> livingEntity.broadcastBreakEvent(equipmentSlot));
            }

            if (
                    level instanceof ServerLevel &&
                    ((ServerLevel)level).getChunkSource().getGenerator() instanceof SkyBlockChunkGenerator
            ) {
                BlockState bs = level.getBlockState(blockPos);
                if (LocationUtils.hasStructuresAtPos((ServerLevel)level, blockPos, Stream.of(
                        BuiltinStructures.DESERT_PYRAMID,
                        BuiltinStructures.OCEAN_RUIN_WARM
                ).collect(Collectors.toList()))) {
                    if (bs.is(Blocks.SAND) && !bs.is(Blocks.SUSPICIOUS_SAND)) {
                        level.setBlockAndUpdate(blockPos, Blocks.SUSPICIOUS_SAND.defaultBlockState());
                    }
                } else if (LocationUtils.hasStructuresAtPos((ServerLevel)level, blockPos, Stream.of(
                        BuiltinStructures.TRAIL_RUINS,
                        BuiltinStructures.OCEAN_RUIN_COLD
                ).collect(Collectors.toList()))) {
                    if (bs.is(Blocks.GRAVEL) && !bs.is(Blocks.SUSPICIOUS_GRAVEL)) {
                        level.setBlockAndUpdate(blockPos, Blocks.SUSPICIOUS_GRAVEL.defaultBlockState());
                    }
                }
            }

        }
        ci.cancel();
    }
}
