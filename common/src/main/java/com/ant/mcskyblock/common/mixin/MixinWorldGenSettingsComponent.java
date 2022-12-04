package com.ant.mcskyblock.common.mixin;

import com.ant.mcskyblock.common.SkyBlock;
import net.minecraft.client.gui.screens.worldselection.WorldGenSettingsComponent;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(WorldGenSettingsComponent.class)
public class MixinWorldGenSettingsComponent {
    @Inject(at = @At("RETURN"), method = "getNonEmptyList", cancellable = true)
    private static void getNonEmptyList(Registry<WorldPreset> registry, TagKey<WorldPreset> tagKey, CallbackInfoReturnable<Optional<List<Holder<WorldPreset>>>> cir) {
        if (tagKey.location().toString().equals("minecraft:normal")) {
            List<Holder<WorldPreset>> list = cir.getReturnValue() != null && cir.getReturnValue().isPresent() ? new ArrayList<>(cir.getReturnValue().get()) : new ArrayList<>();
            list.add(SkyBlock.SB_PRESET_HOLDER);
            Optional<List<Holder<WorldPreset>>> result = Optional.of(list.stream().toList());
            result.get().forEach(r -> r.unwrapKey().ifPresent(s -> System.out.println(s.location().toString())));
            cir.setReturnValue(result);
        }
    }
}
