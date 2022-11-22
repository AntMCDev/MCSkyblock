package com.ant.mcskyblock.common.world.level.block;

import com.ant.mcskyblock.common.SkyBlock;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class Blocks {
    // Block objects
    public static final Block STATIC_SAND = register("static_sand", new Block(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.SAND).strength(0.5f).sound(SoundType.SAND)));
    public static final Block STATIC_RED_SAND = register("static_red_sand", new Block(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.COLOR_ORANGE).strength(0.5f).sound(SoundType.SAND)));
    public static final Block STATIC_GRAVEL = register("static_gravel", new Block(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.STONE).strength(0.6f).sound(SoundType.GRAVEL)));
    public static final Block STATIC_WHITE_CONCRETE_POWDER = register("static_white_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.WHITE_CONCRETE));
    public static final Block STATIC_ORANGE_CONCRETE_POWDER = register("static_orange_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.ORANGE_CONCRETE));
    public static final Block STATIC_MAGENTA_CONCRETE_POWDER = register("static_magenta_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.MAGENTA_CONCRETE));
    public static final Block STATIC_LIGHT_BLUE_CONCRETE_POWDER = register("static_light_blue_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.LIGHT_BLUE_CONCRETE));
    public static final Block STATIC_YELLOW_CONCRETE_POWDER = register("static_yellow_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.YELLOW_CONCRETE));
    public static final Block STATIC_LIME_CONCRETE_POWDER = register("static_lime_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.LIME_CONCRETE));
    public static final Block STATIC_PINK_CONCRETE_POWDER = register("static_pink_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.PINK_CONCRETE));
    public static final Block STATIC_GRAY_CONCRETE_POWDER = register("static_gray_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.GRAY_CONCRETE));
    public static final Block STATIC_LIGHT_GRAY_CONCRETE_POWDER = register("static_light_gray_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.LIGHT_GRAY_CONCRETE));
    public static final Block STATIC_CYAN_CONCRETE_POWDER = register("static_cyan_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.CYAN_CONCRETE));
    public static final Block STATIC_PURPLE_CONCRETE_POWDER = register("static_purple_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.PURPLE_CONCRETE));
    public static final Block STATIC_BLUE_CONCRETE_POWDER = register("static_blue_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.BLUE_CONCRETE));
    public static final Block STATIC_BROWN_CONCRETE_POWDER = register("static_brown_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.BROWN_CONCRETE));
    public static final Block STATIC_GREEN_CONCRETE_POWDER = register("static_green_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.GREEN_CONCRETE));
    public static final Block STATIC_RED_CONCRETE_POWDER = register("static_red_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.RED_CONCRETE));
    public static final Block STATIC_BLACK_CONCRETE_POWDER = register("static_black_concrete_powder", new StaticConcretePowderBlock(net.minecraft.world.level.block.Blocks.BLACK_CONCRETE));
    public static final Block ACTIVE_SCULK_SHRIEKER_BLOCK = register("active_sculk_shrieker", new ActiveSculkShriekerBlock());

    /**
     *
     * @param key
     * @param block
     * @return
     */
    private static Block register(String key, Block block) {
        Block result = Registry.register(Registry.BLOCK, new ResourceLocation(SkyBlock.MOD_NAME, key), block);
        Registry.register(Registry.ITEM, new ResourceLocation(SkyBlock.MOD_NAME, key), new BlockItem(result, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
        return result;
    }

    public static void init() {}
}
