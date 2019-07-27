package com.wags13.tatami.features.stonelantern;

import com.wags13.tatami.base.Feature;
import com.wags13.tatami.features.stonelantern.blocks.BlockStoneLantern;
import com.wags13.tatami.registries.GeneralRegistry;
import net.minecraft.block.Block;

public class StoneLantern extends Feature {

    public static Block STONE_LANTERN;

    public StoneLantern() {
        super("stone_lantern");
    }

    @Override
    public void preInit() {
        STONE_LANTERN = new BlockStoneLantern();

        GeneralRegistry.registerBlockWithItemBlock(STONE_LANTERN, "stone_lantern");
    }
}
