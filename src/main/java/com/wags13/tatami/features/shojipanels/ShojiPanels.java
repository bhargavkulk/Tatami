package com.wags13.tatami.features.shojipanels;

import com.wags13.tatami.Registry;
import com.wags13.tatami.base.Feature;
import com.wags13.tatami.features.shojipanels.blocks.BlockPanel;
import net.minecraft.block.Block;

public class ShojiPanels extends Feature {

    public static Block PANEL_PLAIN;
    public static Block PANEL_TILED;
    public static Block PANEL_FLORAL;

    public ShojiPanels() {
        super("shoji_panels");
    }

    @Override
    public void preInit() {
        PANEL_PLAIN = new BlockPanel();
        PANEL_TILED = new BlockPanel();
        PANEL_FLORAL = new BlockPanel();

        Registry.registerBlockWithItemBlock(PANEL_PLAIN, "panel_plain");
        Registry.registerBlockWithItemBlock(PANEL_TILED, "panel_tiled");
        Registry.registerBlockWithItemBlock(PANEL_FLORAL, "panel_floral");
    }
}
