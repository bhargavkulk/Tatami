package com.wags13.tatami.features.shojipanels;

import com.wags13.tatami.Registry;
import com.wags13.tatami.base.Feature;
import com.wags13.tatami.features.shojipanels.blocks.BlockPanel;
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
        PANEL_PLAIN = new BlockPanel();
        PANEL_TILED = new BlockPanel();
        PANEL_FLORAL = new BlockPanel();

        PANEL_DOOR = new BlockPanelDoor();
        ITEM_PANEL_DOOR = new ItemPanelDoor(PANEL_DOOR);

        Registry.registerBlockWithItemBlock(PANEL_PLAIN, "panel_plain");
        Registry.registerBlockWithItemBlock(PANEL_TILED, "panel_tiled");
        Registry.registerBlockWithItemBlock(PANEL_FLORAL, "panel_floral");

        Registry.registerBlockWithCustomItem(PANEL_DOOR, ITEM_PANEL_DOOR, "panel_door");
    }
}
