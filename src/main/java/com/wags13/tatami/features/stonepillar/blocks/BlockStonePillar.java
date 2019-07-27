package com.wags13.tatami.features.stonepillar.blocks;

import com.wags13.tatami.Tatami;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockStonePillar extends Block {

    protected static final double cube = 0.0625f;
    protected static final AxisAlignedBB PILLAR_AABB =
            new AxisAlignedBB(2.0D * cube, 0.0D, 2.0D * cube, 14.0D * cube, 1.0D, 14.0D * cube);

    public BlockStonePillar() {
        super(Material.ROCK);
        this.setCreativeTab(Tatami.tabTatami);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return PILLAR_AABB;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
