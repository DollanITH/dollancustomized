package com.jhxiaochen.common.blocks.metroline;

import com.jhxiaochen.common.blocks.ModBlock;
import net.minecraft.block.Block;

public class XingShenMetroLineBlock extends Block {
    public XingShenMetroLineBlock(Settings settings) {
        super(settings);
    }

    public ModBlock.MetroLineBlockType getMetroLineBlockType() {
        return ModBlock.MetroLineBlockType.XINGSHEN;
    }
}
