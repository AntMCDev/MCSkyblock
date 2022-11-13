package com.ant.mcskyblock.common.block;

import com.ant.mcskyblock.common.MCSkyBlock;
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

// Just a container class for non-falling variants of vanilla blocks
public class StaticBlock {
    // Block objects
    public static final Block SAND = Registry.register(Registry.BLOCK, new ResourceLocation(MCSkyBlock.MOD_NAME, "static_sand"), new Block(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.SAND).strength(0.5f).sound(SoundType.SAND)));
    public static final Block RED_SAND = Registry.register(Registry.BLOCK, new ResourceLocation(MCSkyBlock.MOD_NAME, "static_red_sand"), new Block(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.COLOR_ORANGE).strength(0.5f).sound(SoundType.SAND)));

    // Item objects (not in loot tables - but accessible via /give etc)
    public static final Item SAND_ITEM = Registry.register(Registry.ITEM, new ResourceLocation(MCSkyBlock.MOD_NAME, "static_sand"), new BlockItem(SAND, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final Item RED_SAND_ITEM = Registry.register(Registry.ITEM, new ResourceLocation(MCSkyBlock.MOD_NAME, "static_red_sand"), new BlockItem(RED_SAND, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
}
