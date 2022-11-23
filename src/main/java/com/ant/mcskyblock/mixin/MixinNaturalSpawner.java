package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.common.SkyBlock;
import com.ant.mcskyblock.common.config.SkyBlockConfig;
import com.ant.mcskyblock.common.world.level.levelgen.SkyBlockChunkGenerator;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NaturalSpawner.class)
public class MixinNaturalSpawner {
    @Shadow
    static final int MAGIC_NUMBER = (int)Math.pow(17.0, 2.0);


    private static final WeightedRandomList<MobSpawnSettings.SpawnerData> BASTION_SPAWNS = WeightedRandomList.create(
            new MobSpawnSettings.SpawnerData(EntityType.PIGLIN_BRUTE, 1, 4, 4),
            new MobSpawnSettings.SpawnerData(EntityType.PIGLIN, 20, 20 , 30),
            new MobSpawnSettings.SpawnerData(EntityType.ZOMBIFIED_PIGLIN, 10, 20 , 20)
    );

    @Inject(at = @At("RETURN"), method = "isSpawnPositionOk", cancellable = true)
    private static void isSpawnPositionOk(SpawnPlacements.Type location, LevelReader world, BlockPos pos, @Nullable EntityType<?> entityType, CallbackInfoReturnable<Boolean> cir) {
        if (SkyBlockConfig.SPAWNING.STOP_BAT_SPAWNS && entityType == EntityType.BAT) {
            cir.setReturnValue(false);
        }
    }

    @Inject(at = @At("RETURN"), method = "mobsAt", cancellable = true)
    private static void mobsAt(ServerLevel serverLevel, StructureManager structureManager, ChunkGenerator chunkGenerator, MobCategory mobCategory, BlockPos blockPos, @Nullable Holder<Biome> holder, CallbackInfoReturnable<WeightedRandomList<MobSpawnSettings.SpawnerData>> cir) {
        if (chunkGenerator instanceof SkyBlockChunkGenerator) {
            if (SkyBlockConfig.SPAWNING.SPAWN_PIGLIN_BRUTES) {
                NaturalSpawner.SpawnState lspawn = serverLevel.getChunkSource().getLastSpawnState();
                Object2IntMap<MobCategory> mobCount = lspawn.getMobCategoryCounts();
                if (mobCount != null) {
                    int cur = mobCount.getOrDefault(MobCategory.MONSTER, -1);
                    int max = MobCategory.MONSTER.getMaxInstancesPerChunk() * lspawn.getSpawnableChunkCount() / MAGIC_NUMBER;
                    SkyBlock.LOGGER.log(Level.INFO,  "cur "+ cur +  " max " + max);
                    // we should really think about adding brutes to there own mobcap(MobCategory)
                    // FIXME Broken on server ?
                    // int real_max = max * serverLevel.players().size()
                    if ( cur < max ) {
                        Structure structure = structureManager.registryAccess().registryOrThrow(Registry.STRUCTURE_REGISTRY).get(BuiltinStructures.BASTION_REMNANT);
                        if (structure != null && structureManager.getStructureAt(blockPos, structure).isValid()) {
                            cir.setReturnValue(BASTION_SPAWNS);
                        }
                    }
                }
            }
        }
    }
}
