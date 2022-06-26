package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.MCSkyBlock;
import com.ant.mcskyblock.skyblock.SkyblockWorldPreset;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.WorldPreset;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.world.gen.WorldPresets$Registrar")
public class MixinWorldPresetsRegistrar {
    @Inject(at = @At("RETURN"), method = "initAndGetDefault")
    public void initAndGetDefault(CallbackInfoReturnable<RegistryEntry<WorldPreset>> cir) {
        MCSkyBlock.LOGGER.info("Registering skyblock world preset");
        SkyblockWorldPreset.register();
    }
}
