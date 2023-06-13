package com.ant.mcskyblock.common.spawning;

import com.ant.mcskyblock.common.config.ChestLootConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RespawnAnchorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

import java.util.Optional;

public class SpawnUtils {
    public static boolean hasBedOrAnchor(ServerLevel serverLevel, BlockPos blockPos) {
        BlockState blockState = serverLevel.getBlockState(blockPos);
        Block block = blockState.getBlock();
        return (block instanceof RespawnAnchorBlock && blockState.getValue(RespawnAnchorBlock.CHARGE) > 0 && RespawnAnchorBlock.canSetSpawn(serverLevel)) || (block instanceof BedBlock && BedBlock.canSetSpawn(serverLevel));
    }

    public static Optional<BlockPos> findValidBoundingBox(ServerLevel serverLevel, BoundingBox boundingBox) {
        for (int y = boundingBox.minY(), ym = boundingBox.maxY(); y <= ym; y++) {
            for (int x = boundingBox.minX(), xm = boundingBox.maxX(); x <= xm; x++) {
                    for (int z = boundingBox.minZ(), zm = boundingBox.maxZ(); z <= zm; z++) {
                        if (
                                serverLevel.getBlockState(new BlockPos(x, y+2, z)).getBlock().isPossibleToRespawnInThis(serverLevel.getBlockState(new BlockPos(x, y+2, z))) &&
                                serverLevel.getBlockState(new BlockPos(x, y+1, z)).getBlock().isPossibleToRespawnInThis(serverLevel.getBlockState(new BlockPos(x, y+1, z))) &&
                                !serverLevel.getBlockState(new BlockPos(x, y, z)).is(Blocks.AIR)
                        ) {
                            return Optional.of(new BlockPos(x, y, z));
                        }
                    }
                }
        }
        return Optional.empty();
    }

    public static void spawnChest(ServerLevel serverLevel, BlockPos blockPos) {
        serverLevel.setBlock(blockPos, Blocks.CHEST.defaultBlockState(), 2);
        BlockEntity blockEntity = serverLevel.getBlockEntity(blockPos);
        if (blockEntity instanceof ChestBlockEntity entity) {
            ChestLootConfig.SETTINGS.keySet().forEach(i -> {
                ChestLootConfig.ChestItem chestLoot = ChestLootConfig.SETTINGS.get(i);
                entity.setItem(i, new ItemStack(BuiltInRegistries.ITEM.get(chestLoot.resourceLocation), chestLoot.count));
            });
        }
    }
}
