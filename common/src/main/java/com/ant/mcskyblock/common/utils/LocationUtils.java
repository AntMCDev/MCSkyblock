package com.ant.mcskyblock.common.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.structure.Structure;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class LocationUtils {
    public static boolean hasStructureAtPos(ServerLevel level, BlockPos pos, ResourceKey<Structure> structureResourceKey) {
        return hasStructuresAtPos(level, pos, Collections.singletonList(structureResourceKey));
    }
    public static boolean hasStructuresAtPos(ServerLevel level, BlockPos pos, Collection<ResourceKey<Structure>> structureResourceKeys) {
        boolean result = false;
        HolderLookup.RegistryLookup<Structure> structureRegistryLookup = level.registryAccess().lookupOrThrow(Registries.STRUCTURE);
        for (ResourceKey<Structure> rks : structureResourceKeys) {
            Optional<Holder.Reference<Structure>> optStructureRef = structureRegistryLookup.get(rks);
            if (optStructureRef.isPresent()) {
                result = level.structureManager().getAllStructuresAt(pos).containsKey(optStructureRef.get().value());
                if (result) { break; }
            }
        }
        return result;
    }
}
