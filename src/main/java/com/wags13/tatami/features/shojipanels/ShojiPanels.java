package com.wags13.tatami.features.shojipanels;

import com.wags13.tatami.features.shojipanels.blocks.BlockPanelFloral;
import com.wags13.tatami.features.shojipanels.blocks.BlockPanelPlain;
import com.wags13.tatami.features.shojipanels.blocks.BlockPanelTiled;
import com.wags13.tatami.registries.GeneralRegistry;
import com.wags13.tatami.base.Feature;
import com.wags13.tatami.features.shojipanels.blocks.BlockPanelDoor;
import com.wags13.tatami.features.shojipanels.items.ItemPanelDoor;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class ShojiPanels extends Feature {

    public static Block PANEL_PLAIN;
    public static Block PANEL_TILED;
    public static Block PANEL_FLORAL;

    public static Block PANEL_DOOR;
    public static Item ITEM_PANEL_DOOR;

    public ShojiPanels() {
        super("shoji_panels");
    }

    @Override
    public void preInit() {
        PANEL_PLAIN = new BlockPanelPlain();
        PANEL_TILED = new BlockPanelTiled();
        PANEL_FLORAL = new BlockPanelFloral();

        PANEL_DOOR = new BlockPanelDoor();
        ITEM_PANEL_DOOR = new ItemPanelDoor(PANEL_DOOR);

        GeneralRegistry.registerBlockWithItemBlock(PANEL_PLAIN, "panel_plain");
        GeneralRegistry.registerBlockWithItemBlock(PANEL_TILED, "panel_tiled");
        GeneralRegistry.registerBlockWithItemBlock(PANEL_FLORAL, "panel_floral");

        GeneralRegistry.registerBlockWithCustomItem(PANEL_DOOR, ITEM_PANEL_DOOR, "panel_door");
    }
}
