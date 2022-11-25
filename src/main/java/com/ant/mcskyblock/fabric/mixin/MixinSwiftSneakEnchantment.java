package com.ant.mcskyblock.fabric.mixin;

import com.ant.mcskyblock.common.config.Config;
import net.minecraft.world.item.enchantment.SwiftSneakEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SwiftSneakEnchantment.class)
public class MixinSwiftSneakEnchantment {
    @Inject(at = @At("HEAD"), method = "isTradeable", cancellable = true)
    public void isTradeable(CallbackInfoReturnable<Boolean> cir) {
        if (Config.INSTANCE.trading.LIBRARIAN_SWIFT_SNEAK) {
            cir.setReturnValue(true);
        }
    }
}
