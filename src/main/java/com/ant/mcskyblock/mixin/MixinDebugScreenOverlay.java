package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.common.world.level.structure.ClientStructureTracker;
import com.ant.mcskyblock.fabric.network.PacketHander;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
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
        PacketHander.sendToServer(PacketHander.STRUCTURECHECK_PACKET.getIdentifier(), PacketByteBufs.empty());
        if (ClientStructureTracker.structure != ClientStructureTracker.SupportedStructures.NONE) {
            list.add("Structure: " + ClientStructureTracker.structure.name().toLowerCase());
        }
        cir.setReturnValue(list);
    }
}
