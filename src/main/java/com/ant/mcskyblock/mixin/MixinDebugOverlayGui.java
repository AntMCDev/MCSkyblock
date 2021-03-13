package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.network.PacketHandler;
import com.ant.mcskyblock.network.PacketStructureCheck;
import com.ant.mcskyblock.world.StructureInfo;
import net.minecraft.client.gui.overlay.DebugOverlayGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(DebugOverlayGui.class)
public abstract class MixinDebugOverlayGui {
    @Inject(at = @At("RETURN"), method = "getDebugInfoRight", cancellable = true)
    protected void getDebugInfoRight(CallbackInfoReturnable cir) {
        List<String> list = (List<String>)cir.getReturnValue();
        PacketHandler.sendToServer(new PacketStructureCheck());
        if (StructureInfo.structure != StructureInfo.SupportedStructures.NONE) {
            list.add("Structure: " + StructureInfo.structure.name().toLowerCase());
        }
        cir.setReturnValue(list);
    }
}
