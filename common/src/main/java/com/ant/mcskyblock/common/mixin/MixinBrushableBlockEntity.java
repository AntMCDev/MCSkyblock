package com.ant.mcskyblock.common.mixin;

import com.ant.mcskyblock.common.utils.LocationUtils;
import com.ant.mcskyblock.common.world.level.levelgen.SkyBlockChunkGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(BrushableBlockEntity.class)
public abstract class MixinBrushableBlockEntity extends BlockEntity {
    @Shadow
    private ResourceLocation lootTable;

    @Shadow
    private ItemStack item;

    @Shadow
    abstract void setLootTable(ResourceLocation resourceLocation, long l);

    public MixinBrushableBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @Inject(at = @At("HEAD"), method = "unpackLootTable", cancellable = true)
    public void unpackLootTable(Player player, CallbackInfo ci) {
        if (
                player instanceof ServerPlayer serverPlayer &&
                serverPlayer.serverLevel().getChunkSource().getGenerator() instanceof SkyBlockChunkGenerator &&
                lootTable == null &&
                item.isEmpty() &&
                level instanceof ServerLevel &&
                ((ServerLevel)level).getChunkSource().getGenerator() instanceof SkyBlockChunkGenerator
        ) {
            if (LocationUtils.hasStructureAtPos((ServerLevel)level, getBlockPos(), BuiltinStructures.DESERT_PYRAMID)) {
                if (RandomSource.create().nextIntBetweenInclusive(1, 2) == 1) {
                    setLootTable(BuiltInLootTables.DESERT_PYRAMID_ARCHAEOLOGY, getBlockPos().asLong());
                } else {
                    setLootTable(BuiltInLootTables.DESERT_WELL_ARCHAEOLOGY, getBlockPos().asLong());
                }
            } else if (LocationUtils.hasStructureAtPos((ServerLevel)level, getBlockPos(), BuiltinStructures.OCEAN_RUIN_COLD)) {
                setLootTable(BuiltInLootTables.OCEAN_RUIN_COLD_ARCHAEOLOGY, getBlockPos().asLong());
            } else if (LocationUtils.hasStructureAtPos((ServerLevel)level, getBlockPos(), BuiltinStructures.OCEAN_RUIN_WARM)) {
                setLootTable(BuiltInLootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY, getBlockPos().asLong());
            } else if (LocationUtils.hasStructureAtPos((ServerLevel)level, getBlockPos(), BuiltinStructures.TRAIL_RUINS)) {
                if (RandomSource.create().nextIntBetweenInclusive(1, 10) == 1) {
                    setLootTable(BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_RARE, getBlockPos().asLong());
                } else {
                    setLootTable(BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_COMMON, getBlockPos().asLong());
                }
            }
        }
    }

    @Inject(at = @At("RETURN"), method = "brushingCompleted", cancellable = true)
    private void brushingCompleted(Player player, CallbackInfo ci) {
        if (player instanceof ServerPlayer serverPlayer && serverPlayer.serverLevel().getChunkSource().getGenerator() instanceof SkyBlockChunkGenerator && this.level != null) {
            this.level.setBlockAndUpdate(this.worldPosition, Blocks.AIR.defaultBlockState());
        }
    }
}
