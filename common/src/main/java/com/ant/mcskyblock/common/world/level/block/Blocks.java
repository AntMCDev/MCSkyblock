package com.ant.mcskyblock.common.world.level.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class Blocks {
    public static final Map<String, Block> BLOCKS = new HashMap<>();
    static {
        BLOCKS.put("static_sand", new Block(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.SAND).strength(0.5f).sound(SoundType.SAND)));
        BLOCKS.put("static_red_sand", new Block(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.COLOR_ORANGE).strength(0.5f).sound(SoundType.SAND)));
        BLOCKS.put("static_gravel", new Block(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.STONE).strength(0.6f).sound(SoundType.GRAVEL)));
        BLOCKS.put("static_white_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.WHITE_CONCRETE));
        BLOCKS.put("static_orange_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.ORANGE_CONCRETE));
        BLOCKS.put("static_magenta_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.MAGENTA_CONCRETE));
        BLOCKS.put("static_light_blue_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.LIGHT_BLUE_CONCRETE));
        BLOCKS.put("static_yellow_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.YELLOW_CONCRETE));
        BLOCKS.put("static_lime_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.LIME_CONCRETE));
        BLOCKS.put("static_pink_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.PINK_CONCRETE));
        BLOCKS.put("static_gray_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.GRAY_CONCRETE));
        BLOCKS.put("static_light_gray_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.LIGHT_GRAY_CONCRETE));
        BLOCKS.put("static_cyan_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.CYAN_CONCRETE));
        BLOCKS.put("static_purple_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.PURPLE_CONCRETE));
        BLOCKS.put("static_blue_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.BLUE_CONCRETE));
        BLOCKS.put("static_brown_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.BROWN_CONCRETE));
        BLOCKS.put("static_green_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.GREEN_CONCRETE));
        BLOCKS.put("static_red_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.RED_CONCRETE));
        BLOCKS.put("static_black_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.BLACK_CONCRETE));
        BLOCKS.put("active_sculk_shrieker", new ActiveSculkShriekerBlock());
    }

    public static Block staticSand() { return BLOCKS.get("static_sand"); }
    public static Block staticRedSand() { return BLOCKS.get("static_red_sand"); }
    public static Block staticGravel() { return BLOCKS.get("static_gravel"); }
    public static Block activeSculkShrieker() { return BLOCKS.get("active_sculk_shrieker"); }

    public static void registerBlocks(BiConsumer<String, Block> biConsumer) {
        for (String s : BLOCKS.keySet()) {
            biConsumer.accept(s, BLOCKS.get(s));
        }
    }

    public static void registerItems(BiConsumer<String, Item> biConsumer) {
        for (String s : BLOCKS.keySet()) {
            biConsumer.accept(s, new BlockItem(BLOCKS.get(s), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
        }
    }
}
