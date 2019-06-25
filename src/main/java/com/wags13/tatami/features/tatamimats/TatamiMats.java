package com.wags13.tatami.features.tatamimats;

import com.wags13.tatami.Registry;
import com.wags13.tatami.base.Feature;
import com.wags13.tatami.features.tatamimats.blocks.*;
import com.wags13.tatami.features.tatamimats.items.*;

import net.minecraft.block.Block;
import net.minecraft.item.Item;


public class TatamiMats extends Feature {
	
	public static Block TATAMI_HALF;
	public static Block TATAMI_FULL;
	public static Item ITEM_TATAMI_FULL;
	
	public TatamiMats() {
		super("tatami_mats");
	}

	@Override
	public void preInit() {
		TATAMI_HALF = new BlockTatamiHalf();
		TATAMI_FULL = new BlockTatamiFull();
		ITEM_TATAMI_FULL = new ItemTatamiFull();
		
		Registry.registerBlockWithItemBlock(TATAMI_HALF, "tatami_half");
		Registry.registerBlockWithCustomItem(TATAMI_FULL, ITEM_TATAMI_FULL, "tatami_full");

	}

}
