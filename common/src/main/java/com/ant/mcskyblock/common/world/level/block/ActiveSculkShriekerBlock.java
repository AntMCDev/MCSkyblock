package com.ant.mcskyblock.common.world.level.block;

import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

/**
 * [COMMON] BLOCK - This is an active variation of the sculk shrieker - even after being placed, which can be used
 * to summon the Warden.
 */
public class ActiveSculkShriekerBlock extends SculkShriekerBlock {
    public ActiveSculkShriekerBlock() {
        super(Properties.of().mapColor(MapColor.COLOR_BLACK).strength(3.0F, 3.0F).sound(SoundType.SCULK_SHRIEKER));
        this.registerDefaultState((((this.stateDefinition.any()).setValue(SHRIEKING, false)).setValue(WATERLOGGED, false)).setValue(CAN_SUMMON, true));
    }
}
