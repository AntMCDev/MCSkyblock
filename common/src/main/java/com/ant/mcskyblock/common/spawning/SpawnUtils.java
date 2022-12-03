package com.ant.mcskyblock.common.spawning;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RespawnAnchorBlock;
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
        for (int x = boundingBox.minX(), xm = boundingBox.maxX(); x <= xm; x++) {
            for (int y = boundingBox.maxY(), ym = boundingBox.minY(); y >= ym; y--) {
                for (int z = boundingBox.minZ(), zm = boundingBox.maxZ(); z <= zm; z++) {
                    if (
                            serverLevel.getBlockState(new BlockPos(x, y, z)).getBlock().isPossibleToRespawnInThis() &&
                            serverLevel.getBlockState(new BlockPos(x, y+1, z)).getBlock().isPossibleToRespawnInThis() &&
                            !serverLevel.getBlockState(new BlockPos(x, y-1, z)).is(Blocks.AIR)
                    ) {
                        return Optional.of(new BlockPos(x, y, z));
                    }
                }
            }
        }
        return Optional.empty();
    }
}
