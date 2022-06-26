package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.MCSkyBlock;
import com.ant.mcskyblock.world.SkyblockWorldType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.MoreOptionsDialog;
import net.minecraft.client.world.GeneratorOptionsHolder;
import net.minecraft.tag.TagKey;
import net.minecraft.tag.WorldPresetTags;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.WorldPreset;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mixin(MoreOptionsDialog.class)
public class MixinMoreOptionsDialog {
    @Shadow
    private GeneratorOptionsHolder generatorOptionsHolder;

    @Inject(at = @At("RETURN"), method = "collectPresets", cancellable = true)
    private static void collectPresets(Registry<WorldPreset> presetRegistry, TagKey<WorldPreset> tag, CallbackInfoReturnable<Optional<List<RegistryEntry<WorldPreset>>>> cir) {
        if (tag == WorldPresetTags.NORMAL && cir.getReturnValue().isPresent()) {
            List<RegistryEntry<WorldPreset>> mutable = new ArrayList<>(cir.getReturnValue().get());
            mutable.add(presetRegistry.entryOf(SkyblockWorldType.MC_SKYBLOCK_PRESET));
            cir.setReturnValue(Optional.of(mutable.stream().collect(Collectors.toUnmodifiableList())));
        }
    }
}
