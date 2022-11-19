package com.ant.mcskyblock.fabric.command;

import com.ant.mcskyblock.common.world.level.levelgen.IslandGenerator;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceOrTagLocationArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.*;

/**
 * The LocateIsland Command is for in-game use. It is used to locate a island that has already been generated.
 */
public class LocateIslandCommand {

    /**
     * throw if there is no such biome.
     */
    private static final DynamicCommandExceptionType ERROR_BIOME_INVALID = new DynamicCommandExceptionType(object -> Component.translatable("commands.locate.biome.invalid", object));


    /**
     * Registers the commands for the skyblock mod.  At the moment there is only the locate-island biome-arg command.
     */
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(
                Commands.literal("locate-island")
                        .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                        .then(Commands.argument("biome", ResourceOrTagLocationArgument.resourceOrTag(Registry.BIOME_REGISTRY))
                        .executes(ctx -> {
                            BlockPos pos = IslandGenerator.nearest(new BlockPos(ctx.getSource().getPosition()), ResourceOrTagLocationArgument.getRegistryType(ctx, "biome", Registry.BIOME_REGISTRY, ERROR_BIOME_INVALID).unwrap().orThrow().location().getPath());
                            if (pos != null) {
                                ctx.getSource().sendSuccess(ComponentUtils.wrapInSquareBrackets(Component.translatable("chat.coordinates", pos.getX(), pos.getY(), pos.getZ())).withStyle(style -> style.withColor(ChatFormatting.GREEN).withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tp @s " + pos.getX() + " " + pos.getY() + " " + pos.getZ())).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.translatable("chat.coordinates.tooltip")))), false);
                            } else {
                                ctx.getSource().sendFailure(Component.literal("No island found in a reasonable distance"));
                            }
                            return 1;
                        }))
        ));
    }
}
