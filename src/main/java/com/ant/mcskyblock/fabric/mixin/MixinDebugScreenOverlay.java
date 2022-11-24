package com.ant.mcskyblock.fabric.mixin;

import com.ant.mcskyblock.common.network.PacketHandler;
import com.ant.mcskyblock.common.world.level.structure.ClientStructureTracker;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(DebugScreenOverlay.class)
public class MixinDebugScreenOverlay {
    @Inject(at = @At("RETURN"), method = "getSystemInformation", cancellable = true)
    protected void getSystemInformation(CallbackInfoReturnable<List<String>> cir) {
        List<String> list = (List<String>)cir.getReturnValue();
        PacketHandler.INSTANCE.sendToServer(PacketHandler.STRUCTURE_CHECK_PACKET.getIdentifier(), new byte[0]);
        if (ClientStructureTracker.structure != ClientStructureTracker.SupportedStructures.NONE) {
            list.add("Structure: " + ClientStructureTracker.structure.name().toLowerCase());
        }
        cir.setReturnValue(list);
    }
}
