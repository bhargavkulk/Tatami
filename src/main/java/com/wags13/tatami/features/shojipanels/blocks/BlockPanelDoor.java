package com.wags13.tatami.features.shojipanels.blocks;

import com.wags13.tatami.features.shojipanels.ShojiPanels;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockPanelDoor extends BlockDoor {

    private static final double cube = 0.0625f;

    protected static final AxisAlignedBB SOUTH_CLOSED_AABB =
            new AxisAlignedBB(0.0D, 0.0D, 6.0D * cube, 1.0D, 1.0D, 1.0D - (8.0D * cube));
    protected static final AxisAlignedBB NORTH_CLOSED_AABB =
            new AxisAlignedBB(0.0D, 0.0D, 8.0D * cube, 1.0D, 1.0D, 1.0D - (6.0D * cube));
    protected static final AxisAlignedBB WEST_CLOSED_AABB =
            new AxisAlignedBB(8.0D * cube, 0.0D, 0.0D, 1.0D - (6.0D * cube), 1.0D, 1.0D);
    protected static final AxisAlignedBB EAST_CLOSED_AABB =
            new AxisAlignedBB(6.0D * cube, 0.0D, 0.0D, 1.0D - (8.0D * cube), 1.0D, 1.0D);

    protected static final AxisAlignedBB SOUTH_RH_AABB =
            new AxisAlignedBB(0.0D, 0.0D, 5.0D * cube, 3.0D * cube, 1.0D, 1.0D - (9.0D * cube));
    protected static final AxisAlignedBB WEST_RH_AABB =
            new AxisAlignedBB(9.0D * cube, 0.0D, 0.0D, 1.0D - (5.0D * cube), 1.0D, 3.0D * cube);
    protected static final AxisAlignedBB NORTH_RH_AABB =
            new AxisAlignedBB(13.0D * cube, 0.0D, 9.0D * cube, 1.0D, 1.0D, 1.0D - (5.0D * cube));
    protected static final AxisAlignedBB EAST_RH_AABB =
            new AxisAlignedBB(5.0D * cube, 0.0D, 13.0D * cube, 1.0D - (9.0D * cube), 1.0D, 1.0D);

    protected static final AxisAlignedBB SOUTH_LH_AABB =
            new AxisAlignedBB(13.0D * cube, 0.0D, 5.0D * cube, 1.0D, 1.0D, 1.0D - (9.0D * cube));
    protected static final AxisAlignedBB WEST_LH_AABB =
            new AxisAlignedBB(9.0D * cube, 0.0D, 13.0D * cube, 1.0D - (5.0D * cube), 1.0D, 1.0D);
    protected static final AxisAlignedBB NORTH_LH_AABB =
            new AxisAlignedBB(0.0D * cube, 0.0D, 9.0D * cube, 3.0D * cube, 1.0D, 1.0D - (5.0D * cube));
    protected static final AxisAlignedBB EAST_LH_AABB =
            new AxisAlignedBB(5.0D * cube, 0.0D, 0.0D, 1.0D - (9.0D * cube), 1.0D, 3.0D * cube);


    public BlockPanelDoor() {
        super(Material.CLOTH);
        this.setSoundType(SoundType.CLOTH);
        this.setHardness(0.8f);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        state = state.getActualState(source, pos);
        EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);
        boolean isClosed = !((Boolean) state.getValue(OPEN)).booleanValue();
        boolean isRightHinged = state.getValue(HINGE) == BlockDoor.EnumHingePosition.RIGHT;

        switch (enumfacing) {
            case EAST:
            default:
                return isClosed ? EAST_CLOSED_AABB : (isRightHinged ? EAST_RH_AABB : EAST_LH_AABB);
            case SOUTH:
                return isClosed ? SOUTH_CLOSED_AABB : (isRightHinged ? SOUTH_RH_AABB : SOUTH_LH_AABB);
            case WEST:
                return isClosed ? WEST_CLOSED_AABB : (isRightHinged ? WEST_RH_AABB : WEST_LH_AABB);
            case NORTH:
                return isClosed ? NORTH_CLOSED_AABB : (isRightHinged ? NORTH_RH_AABB : NORTH_LH_AABB);
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ShojiPanels.ITEM_PANEL_DOOR;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(ShojiPanels.ITEM_PANEL_DOOR);
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    private int getCloseSound() {
        return this.blockMaterial == Material.IRON ? 1011 : 1012;
    }

    private int getOpenSound() {
        return this.blockMaterial == Material.IRON ? 1005 : 1006;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER) {
            BlockPos blockpos = pos.down();
            IBlockState iblockstate = worldIn.getBlockState(blockpos);

            if (iblockstate.getBlock() != this) {
                worldIn.setBlockToAir(pos);
            } else if (blockIn != this) {
                iblockstate.neighborChanged(worldIn, blockpos, blockIn, fromPos);
            }
        } else {
            boolean flag1 = false;
            BlockPos blockpos1 = pos.up();
            IBlockState iblockstate1 = worldIn.getBlockState(blockpos1);

            if (iblockstate1.getBlock() != this) {
                worldIn.setBlockToAir(pos);
                flag1 = true;
            }

            if (!worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP)) {
                worldIn.setBlockToAir(pos);
                flag1 = true;

                if (iblockstate1.getBlock() == this) {
                    worldIn.setBlockToAir(blockpos1);
                }
            }

            if (flag1) {
                if (!worldIn.isRemote) {
                    this.dropBlockAsItem(worldIn, pos, state, 0);
                }
            } else {
                boolean flag = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(blockpos1);

                if (blockIn != this && (flag || blockIn.getDefaultState().canProvidePower()) && flag != ((Boolean) iblockstate1.getValue(POWERED)).booleanValue()) {
                    worldIn.setBlockState(blockpos1, iblockstate1.withProperty(POWERED, Boolean.valueOf(flag)), 2);

                    if (flag != ((Boolean) state.getValue(OPEN)).booleanValue()) {
                        worldIn.setBlockState(pos, state.withProperty(OPEN, Boolean.valueOf(flag)), 2);
                        worldIn.markBlockRangeForRenderUpdate(pos, pos);
                        worldIn.playEvent((EntityPlayer) null, flag ? this.getOpenSound() : this.getCloseSound(), pos, 0);
                    }
                }
            }
        }
    }
}
