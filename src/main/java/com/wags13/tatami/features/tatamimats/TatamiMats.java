package com.wags13.tatami.features.tatamimats;

import com.wags13.tatami.Registry;
import com.wags13.tatami.base.Feature;
import com.wags13.tatami.features.tatamimats.blocks.*;
import com.wags13.tatami.features.tatamimats.items.*;

import net.minecraft.block.Block;
import net.minecraft.item.Item;


public class TatamiMats extends Feature {
	
	public static Block tatamiHalf;
	public static Block tatamiFull;
	public static Item itemTatamiFull;
	
	public TatamiMats() {
		super("tatami_mats");
	}

	@Override
	public void preInit() {
		tatamiHalf = new BlockTatamiHalf();
		tatamiFull = new BlockTatamiFull();
		itemTatamiFull = new ItemTatamiFull();
		
		Registry.registerBlockWithItemBlock(tatamiHalf, "tatami_half");
		Registry.registerBlockWithCustomItem(tatamiFull, itemTatamiFull, "tatami_full");

	}

}
