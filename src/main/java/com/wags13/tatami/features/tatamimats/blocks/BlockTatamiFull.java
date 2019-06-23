package com.wags13.tatami.features.tatamimats.blocks;

import com.wags13.tatami.Tatami;

import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.IStringSerializable;

public class BlockTatamiFull extends BlockHorizontal {

	public static final PropertyEnum<BlockTatamiFull.EnumPartType> PART = PropertyEnum.<BlockTatamiFull.EnumPartType>create(
			"part", BlockTatamiFull.EnumPartType.class);

	public BlockTatamiFull() {
		super(Material.WOOD);
		this.setSoundType(SoundType.WOOD);
	}
	
	

	public static enum EnumPartType implements IStringSerializable {
		HEAD("head"), FOOT("foot");

		private final String name;

		private EnumPartType(String name) {
			this.name = name;
		}

		public String toString() {
			return this.name;
		}

		public String getName() {
			return this.name;
		}
	}
}
