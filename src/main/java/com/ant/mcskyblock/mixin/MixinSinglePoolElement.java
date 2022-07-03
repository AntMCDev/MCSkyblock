package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.config.ConfigHandler;
import com.ant.mcskyblock.skyblock.SkyblockChunkGenerator;
import com.mojang.datafixers.util.Either;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SinglePoolElement.class)
public class MixinSinglePoolElement {
    @Final
    @Shadow
    protected Either<Identifier, StructureTemplate> location;

    @ModifyVariable(at = @At("HEAD"), method = "generate", ordinal = 0, argsOnly = true)
    public BlockPos injectPos(BlockPos pos) {
        return pos.mutableCopy().add(0, ConfigHandler.Common.ADJUST_STRUCTURE_Y, 0).toImmutable();
    }

    @Inject(at = @At("HEAD"), method = "generate", cancellable = true)
    public void generate(StructureTemplateManager structureTemplateManager, StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, BlockPos pos, BlockPos pivot, BlockRotation rotation, BlockBox box, Random random, boolean keepJigsaws, CallbackInfoReturnable<Boolean> cir) {
        if (world.toServerWorld().getChunkManager().getChunkGenerator() instanceof SkyblockChunkGenerator) {
            if (!isSpawnable()) {
                cir.setReturnValue(true);
            }
        }
    }

    private boolean isSpawnable() {
        if (!ConfigHandler.Common.GENERATE_ANCIENT_CITY || !isAncientCity()) {
            if (!ConfigHandler.Common.GENERATE_BASTION || !isBastion()) {
                if (!ConfigHandler.Common.GENERATE_PILLAGER_OUTPOST || !isPillagerOutpost()) {
                    if (!ConfigHandler.Common.GENERATE_VILLAGE || !isVillage()) {
                        if (!ConfigHandler.Common.GENERATE_MISC_STRUCTURES || !isMisc()) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean isAncientCity() {
        return is("ancient_city/");
    }

    private boolean isBastion() {
        return is("bastion/");
    }

    private boolean isPillagerOutpost() {
        return is("pillager_outpost/");
    }

    private boolean isVillage() {
        return is("village/");
    }

    private boolean isMisc() {
        return !isAncientCity() && !isBastion() && !isPillagerOutpost() && !isVillage();
    }

    private boolean is(String pattern) {
        return location.left().isPresent() && location.left().get().getPath().contains(pattern);
    }
}
