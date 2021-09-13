package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.network.PacketHander;
import com.ant.mcskyblock.world.StructureInfo;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(DebugHud.class)
public class MixinDebugHud {
    @Inject(at = @At("RETURN"), method = "getRightText", cancellable = true)
    protected void getRightText(CallbackInfoReturnable<List<String>> cir) {
        List<String> list = (List<String>)cir.getReturnValue();
        PacketHander.sendToServer(PacketHander.STRUCTURECHECK_PACKET.getIdentifier(), PacketByteBufs.empty());
        if (StructureInfo.structure != StructureInfo.SupportedStructures.NONE) {
            list.add("Structure: " + StructureInfo.structure.name().toLowerCase());
        }
        cir.setReturnValue(list);
    }
}
