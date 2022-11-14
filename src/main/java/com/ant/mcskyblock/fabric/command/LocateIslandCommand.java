package com.ant.mcskyblock.fabric.command;

import com.ant.mcskyblock.common.MCSkyBlock;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.*;

public class LocateIslandCommand {
    public static void register() {
        // TODO: Add biome-specific argument
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(
                Commands.literal("locate-island")
                        .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                        .executes(ctx -> {
                            BlockPos pos = MCSkyBlock.ISLAND_MANAGER.findNearest(new BlockPos(ctx.getSource().getPosition()));
                            if (pos != null) {
                                ctx.getSource().sendSuccess(ComponentUtils.wrapInSquareBrackets(Component.translatable("chat.coordinates", pos.getX(), pos.getY(), pos.getZ())).withStyle(style -> style.withColor(ChatFormatting.GREEN).withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tp @s " + pos.getX() + " " + pos.getY() + " " + pos.getZ())).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.translatable("chat.coordinates.tooltip")))), false);
                            } else {
                                ctx.getSource().sendFailure(Component.literal("No island found in a reasonable distance"));
                            }
                            return 1;
                        })
        ));
    }
}
