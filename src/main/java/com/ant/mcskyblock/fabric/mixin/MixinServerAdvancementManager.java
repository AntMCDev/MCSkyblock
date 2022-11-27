package com.ant.mcskyblock.fabric.mixin;

import com.ant.mcskyblock.common.advancements.AdvancementHelper;
import com.ant.mcskyblock.common.advancements.IAdvancement;
import com.ant.mcskyblock.common.advancements.skyblock.Skyblock;
import com.ant.mcskyblock.common.advancements.skyblock.CobblestoneGeneratorAdvancement;
import com.ant.mcskyblock.common.advancements.skyblock.PortalAdvancement;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(ServerAdvancementManager.class)
public class MixinServerAdvancementManager {
    private static final List<IAdvancement> ADVANCEMENTS = List.of(
            new Skyblock(),
            new Skyblock(),
            new CobblestoneGeneratorAdvancement(),
            new PortalAdvancement()
    );

    @Inject(at = @At("HEAD"), method = "apply")
    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profilerFiller, CallbackInfo ci) {
        ADVANCEMENTS.stream().filter(IAdvancement::enabled).forEach(r -> map.put(r.getResourceLocation(), AdvancementHelper.toJSON(r)));
    }
}
