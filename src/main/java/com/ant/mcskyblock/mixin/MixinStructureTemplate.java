package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.MCSkyBlock;
import com.ant.mcskyblock.config.ConfigHandler;
import com.ant.mcskyblock.skyblock.SkyblockChunkGenerator;
import com.google.common.collect.Lists;
import net.minecraft.block.Blocks;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(StructureTemplate.class)
public class MixinStructureTemplate {
    @Final
    @Shadow
    private List<StructureTemplate.PalettedBlockInfoList> blockInfoLists;

    @Inject(at = @At("HEAD"), method = "place", cancellable = true)
    public void place(ServerWorldAccess world, BlockPos pos, BlockPos pivot, StructurePlacementData placementData, Random random, int flags, CallbackInfoReturnable<Boolean> cir) {
        if (world.toServerWorld().getChunkManager().getChunkGenerator() instanceof SkyblockChunkGenerator) {
            if (!ConfigHandler.Common.GENERATE_BASTION || !isBastion()) {
                if (!ConfigHandler.Common.GENERATE_ANCIENT_CITY || !isAncientCity()) {
                    if (!ConfigHandler.Common.GENERATE_PILLAGER_OUTPOST || !isPillagerOutpost()) {
                        cir.setReturnValue(true);
                    }
                }
            }
        }
    }

    private boolean isBastion() {
        return blockInfoLists.stream().anyMatch(p -> p.getAllOf(Blocks.BLACKSTONE).size() > 0 && p.getAllOf(Blocks.POLISHED_BLACKSTONE_BRICKS).size() > 0);
    }

    private boolean isAncientCity() {
        return blockInfoLists.stream().anyMatch(p -> p.getAllOf(Blocks.CHISELED_DEEPSLATE).size() > 0);
    }

    private boolean isPillagerOutpost() {
        return blockInfoLists.stream().anyMatch(p -> p.getAllOf(Blocks.WHITE_WALL_BANNER).size() > 0);
    }
}
