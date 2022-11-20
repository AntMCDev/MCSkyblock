package com.ant.mcskyblock.common.world.level.block;

import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class ActiveSculkShriekerBlock extends SculkShriekerBlock {
    public ActiveSculkShriekerBlock() {
        super(BlockBehaviour.Properties.of(Material.SCULK, MaterialColor.COLOR_BLACK).strength(3.0f, 3.0f).sound(SoundType.SCULK_SHRIEKER));
        this.registerDefaultState((((this.stateDefinition.any()).setValue(SHRIEKING, false)).setValue(WATERLOGGED, false)).setValue(CAN_SUMMON, true));
    }
}
