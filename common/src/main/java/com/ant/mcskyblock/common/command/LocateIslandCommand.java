package com.ant.mcskyblock.common.command;

import com.ant.mcskyblock.common.world.level.levelgen.IslandGenerator;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceOrTagArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.*;

/**
 * [COMMON] COMMAND - This is used to locate a generated island
 */
public class LocateIslandCommand {

    /**
     * throw if there is no such biome.
     */
    private static final DynamicCommandExceptionType ERROR_BIOME_INVALID = new DynamicCommandExceptionType(object -> Component.translatable("commands.locate.biome.invalid", object));


    /**
     * Registers the commands for the skyblock mod.  At the moment there is only the locate-island biome-arg command.
     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext) {
        dispatcher.register(
                Commands.literal("locate-island")
                        .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                        .then(
                                Commands.argument("biome", ResourceOrTagArgument.resourceOrTag(commandBuildContext, Registries.BIOME))
                                .executes(ctx -> {
                                    BlockPos pos = IslandGenerator.nearest(BlockPos.containing(ctx.getSource().getPosition()), ResourceOrTagArgument.getResourceOrTag(ctx, "biome", Registries.BIOME).asPrintable());
                                    if (pos != null) {
                                        ctx.getSource().sendSuccess(() -> ComponentUtils.wrapInSquareBrackets(Component.translatable("chat.coordinates", pos.getX(), pos.getY(), pos.getZ())).withStyle(style -> style.withColor(ChatFormatting.GREEN).withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tp @s " + pos.getX() + " " + pos.getY() + " " + pos.getZ())).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.translatable("chat.coordinates.tooltip")))), false);
                                    } else {
                                        ctx.getSource().sendFailure(Component.literal("No island found in a reasonable distance"));
                                    }
                                    return 1;
                                })
                        )
        );
    }
}
