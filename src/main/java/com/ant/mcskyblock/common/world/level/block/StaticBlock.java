package com.ant.mcskyblock.common.world.level.block;

import com.ant.mcskyblock.common.MCSkyBlock;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

// Just a container class for non-falling variants of vanilla blocks
public class StaticBlock {
    // Block objects
    public static final Block SAND = register("static_sand", new Block(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.SAND).strength(0.5f).sound(SoundType.SAND)));
    public static final Block RED_SAND = register("static_red_sand", new Block(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.COLOR_ORANGE).strength(0.5f).sound(SoundType.SAND)));
    public static final Block GRAVEL = register("static_gravel", new Block(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.STONE).strength(0.6f).sound(SoundType.GRAVEL)));
    public static final Block WHITE_CONCRETE_POWDER = register("static_white_concrete_powder", new StaticConcretePowderBlock(Blocks.WHITE_CONCRETE, BlockBehaviour.Properties.of(Material.SAND, DyeColor.WHITE).strength(0.5f).sound(SoundType.SAND)));
    public static final Block ORANGE_CONCRETE_POWDER = register("static_orange_concrete_powder", new StaticConcretePowderBlock(Blocks.ORANGE_CONCRETE, BlockBehaviour.Properties.of(Material.SAND, DyeColor.WHITE).strength(0.5f).sound(SoundType.SAND)));
    public static final Block MAGENTA_CONCRETE_POWDER = register("static_magenta_concrete_powder", new StaticConcretePowderBlock(Blocks.MAGENTA_CONCRETE, BlockBehaviour.Properties.of(Material.SAND, DyeColor.WHITE).strength(0.5f).sound(SoundType.SAND)));
    public static final Block LIGHT_BLUE_CONCRETE_POWDER = register("static_light_blue_concrete_powder", new StaticConcretePowderBlock(Blocks.LIGHT_BLUE_CONCRETE, BlockBehaviour.Properties.of(Material.SAND, DyeColor.WHITE).strength(0.5f).sound(SoundType.SAND)));
    public static final Block YELLOW_CONCRETE_POWDER = register("static_yellow_concrete_powder", new StaticConcretePowderBlock(Blocks.YELLOW_CONCRETE, BlockBehaviour.Properties.of(Material.SAND, DyeColor.WHITE).strength(0.5f).sound(SoundType.SAND)));
    public static final Block LIME_CONCRETE_POWDER = register("static_lime_concrete_powder", new StaticConcretePowderBlock(Blocks.LIME_CONCRETE, BlockBehaviour.Properties.of(Material.SAND, DyeColor.WHITE).strength(0.5f).sound(SoundType.SAND)));
    public static final Block PINK_CONCRETE_POWDER = register("static_pink_concrete_powder", new StaticConcretePowderBlock(Blocks.PINK_CONCRETE, BlockBehaviour.Properties.of(Material.SAND, DyeColor.WHITE).strength(0.5f).sound(SoundType.SAND)));
    public static final Block GRAY_CONCRETE_POWDER = register("static_gray_concrete_powder", new StaticConcretePowderBlock(Blocks.GRAY_CONCRETE, BlockBehaviour.Properties.of(Material.SAND, DyeColor.WHITE).strength(0.5f).sound(SoundType.SAND)));
    public static final Block LIGHT_GRAY_CONCRETE_POWDER = register("static_light_gray_concrete_powder", new StaticConcretePowderBlock(Blocks.LIGHT_GRAY_CONCRETE, BlockBehaviour.Properties.of(Material.SAND, DyeColor.WHITE).strength(0.5f).sound(SoundType.SAND)));
    public static final Block CYAN_CONCRETE_POWDER = register("static_cyan_concrete_powder", new StaticConcretePowderBlock(Blocks.CYAN_CONCRETE, BlockBehaviour.Properties.of(Material.SAND, DyeColor.WHITE).strength(0.5f).sound(SoundType.SAND)));
    public static final Block PURPLE_CONCRETE_POWDER = register("static_purple_concrete_powder", new StaticConcretePowderBlock(Blocks.PURPLE_CONCRETE, BlockBehaviour.Properties.of(Material.SAND, DyeColor.WHITE).strength(0.5f).sound(SoundType.SAND)));
    public static final Block BLUE_CONCRETE_POWDER = register("static_blue_concrete_powder", new StaticConcretePowderBlock(Blocks.BLUE_CONCRETE, BlockBehaviour.Properties.of(Material.SAND, DyeColor.WHITE).strength(0.5f).sound(SoundType.SAND)));
    public static final Block BROWN_CONCRETE_POWDER = register("static_brown_concrete_powder", new StaticConcretePowderBlock(Blocks.BROWN_CONCRETE, BlockBehaviour.Properties.of(Material.SAND, DyeColor.WHITE).strength(0.5f).sound(SoundType.SAND)));
    public static final Block GREEN_CONCRETE_POWDER = register("static_green_concrete_powder", new StaticConcretePowderBlock(Blocks.GREEN_CONCRETE, BlockBehaviour.Properties.of(Material.SAND, DyeColor.WHITE).strength(0.5f).sound(SoundType.SAND)));
    public static final Block RED_CONCRETE_POWDER = register("static_red_concrete_powder", new StaticConcretePowderBlock(Blocks.RED_CONCRETE, BlockBehaviour.Properties.of(Material.SAND, DyeColor.WHITE).strength(0.5f).sound(SoundType.SAND)));
    public static final Block BLACK_CONCRETE_POWDER = register("static_black_concrete_powder", new StaticConcretePowderBlock(Blocks.BLACK_CONCRETE, BlockBehaviour.Properties.of(Material.SAND, DyeColor.WHITE).strength(0.5f).sound(SoundType.SAND)));

    /**
     *
      * @param key
     * @param block
     * @return
     */
    private static Block register(String key, Block block) {
        Block result = Registry.register(Registry.BLOCK, new ResourceLocation(MCSkyBlock.MOD_NAME, key), block);
        Registry.register(Registry.ITEM, new ResourceLocation(MCSkyBlock.MOD_NAME, key), new BlockItem(result, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
        return result;
    }
}
