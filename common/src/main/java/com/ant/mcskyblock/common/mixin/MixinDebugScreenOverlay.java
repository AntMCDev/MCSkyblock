package com.ant.mcskyblock.common.mixin;

import com.ant.mcskyblock.common.config.Config;
import com.ant.mcskyblock.common.network.PacketHandler;
import com.ant.mcskyblock.common.world.level.structure.ClientStructureTracker;
import net.minecraft.client.Minecraft;
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
        List<String> list = cir.getReturnValue();
        PacketHandler.INSTANCE.sendToServer(PacketHandler.STRUCTURE_CHECK_PACKET.getIdentifier(), new byte[0]);
        if (ClientStructureTracker.structures != null) {
            list.add("Structure: " + ClientStructureTracker.structures);
        }

        if (Config.INSTANCE.trading.SCHEDULED_WANDERING_TRADER) {
            long t = Minecraft.getInstance().level.getDayTime() + 330000L;
            long hours = 24 - (t % 24000L / 1000L) % 24;
            hours = hours == 24 ? 0 : hours;
            long days = 14 - (t / 24000L % 2147483647L) % 14;
            days = hours == 0 ? days : days - 1;
            list.add("Trader spawns in " + days + " days, " + hours + " hours");
        }

        cir.setReturnValue(list);

    }
}
