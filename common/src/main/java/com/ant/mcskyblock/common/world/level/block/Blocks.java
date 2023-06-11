package com.ant.mcskyblock.common.world.level.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class Blocks {
    public static final Map<String, Block> BLOCKS = new HashMap<>();
    static {
        BLOCKS.put("static_sand", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sound(SoundType.SAND)));
        BLOCKS.put("static_red_sand", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sound(SoundType.SAND)));
        BLOCKS.put("static_gravel", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.SNARE).strength(0.6F).sound(SoundType.GRAVEL)));
        BLOCKS.put("static_white_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.WHITE_CONCRETE, DyeColor.WHITE));
        BLOCKS.put("static_orange_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.ORANGE_CONCRETE, DyeColor.ORANGE));
        BLOCKS.put("static_magenta_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.MAGENTA_CONCRETE, DyeColor.MAGENTA));
        BLOCKS.put("static_light_blue_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.LIGHT_BLUE_CONCRETE, DyeColor.LIGHT_BLUE));
        BLOCKS.put("static_yellow_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.YELLOW_CONCRETE, DyeColor.YELLOW));
        BLOCKS.put("static_lime_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.LIME_CONCRETE, DyeColor.LIME));
        BLOCKS.put("static_pink_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.PINK_CONCRETE, DyeColor.PINK));
        BLOCKS.put("static_gray_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.GRAY_CONCRETE, DyeColor.GRAY));
        BLOCKS.put("static_light_gray_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.LIGHT_GRAY_CONCRETE, DyeColor.LIGHT_GRAY));
        BLOCKS.put("static_cyan_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.CYAN_CONCRETE, DyeColor.CYAN));
        BLOCKS.put("static_purple_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.PURPLE_CONCRETE, DyeColor.PURPLE));
        BLOCKS.put("static_blue_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.BLUE_CONCRETE, DyeColor.BLUE));
        BLOCKS.put("static_brown_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.BROWN_CONCRETE, DyeColor.BROWN));
        BLOCKS.put("static_green_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.GREEN_CONCRETE, DyeColor.GREEN));
        BLOCKS.put("static_red_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.RED_CONCRETE, DyeColor.RED));
        BLOCKS.put("static_black_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.BLACK_CONCRETE, DyeColor.BLACK));
        BLOCKS.put("active_sculk_shrieker", new ActiveSculkShriekerBlock());
        BLOCKS.put("spawn_block", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL).noLootTable()));
    }

    public static Block staticSand() { return BLOCKS.get("static_sand"); }
    public static Block staticRedSand() { return BLOCKS.get("static_red_sand"); }
    public static Block staticGravel() { return BLOCKS.get("static_gravel"); }
    public static Block activeSculkShrieker() { return BLOCKS.get("active_sculk_shrieker"); }
    public static Block spawnBlock() {
        return BLOCKS.get("spawn_block");
    }

    public static void registerBlocks(BiConsumer<String, Block> biConsumer) {
        for (String s : BLOCKS.keySet()) {
            biConsumer.accept(s, BLOCKS.get(s));
        }
    }

    public static void registerItems(BiConsumer<String, Item> biConsumer) {
        for (String s : BLOCKS.keySet()) {
            biConsumer.accept(s, new BlockItem(BLOCKS.get(s), new Item.Properties()));
        }
    }
}
