package com.wags13.tatami.features.stonepillar;

import com.wags13.tatami.base.Feature;
import com.wags13.tatami.features.stonepillar.blocks.BlockStonePillar;
import com.wags13.tatami.registries.GeneralRegistry;
import net.minecraft.block.Block;

public class StonePillar extends Feature {

    public static Block STONE_PILLAR;

    public StonePillar() {
        super("stone_pillar");
    }

    @Override
    public void preInit() {
        STONE_PILLAR = new BlockStonePillar();

        GeneralRegistry.registerBlockWithItemBlock(STONE_PILLAR, "stone_pillar");
    }
}
