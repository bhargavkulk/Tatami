package com.wags13.tatami.features.tatamimats.blocks;

import com.wags13.tatami.Tatami;
import com.wags13.tatami.features.tatamimats.TatamiMats;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockTatamiFull extends BlockHorizontal {

    public static final PropertyEnum<BlockTatamiFull.EnumPartType> PART = PropertyEnum.<BlockTatamiFull.EnumPartType>create(
            "part", BlockTatamiFull.EnumPartType.class);
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockTatamiFull() {
        super(Material.WOOD);
        this.setHardness(2.0F).setResistance(5.0F);
        this.setSoundType(SoundType.WOOD);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{FACING, PART});
    }

    public int getMetaFromState(IBlockState state) {
        int i = 0;
        i = i | ((EnumFacing) state.getValue(FACING)).getHorizontalIndex();

        if (state.getValue(PART) == EnumPartType.HEAD) {
            i |= 8;
        }

        return i;
    }

    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.getHorizontal(meta);
        return (meta & 8) > 0 ? this.getDefaultState().withProperty(PART, EnumPartType.HEAD).withProperty(FACING, enumfacing)
                : this.getDefaultState().withProperty(PART, EnumPartType.FOOT).withProperty(FACING, enumfacing);
    }

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);

        if (state.getValue(PART) == EnumPartType.FOOT) {
            if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock() != this) {
                worldIn.setBlockToAir(pos);
            }
        } else if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock() != this) {
            if (!worldIn.isRemote) {
                this.dropBlockAsItem(worldIn, pos, state, 0);
            }

            worldIn.setBlockToAir(pos);
        }
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return state.getValue(PART) == EnumPartType.FOOT ? Items.AIR : TatamiMats.itemTatamiFull;
    }

    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
        if (state.getValue(PART) == EnumPartType.HEAD) {
            spawnAsEntity(worldIn, pos, new ItemStack(TatamiMats.itemTatamiFull, 1));
        }
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        BlockPos blockpos = pos;

        if (state.getValue(PART) == EnumPartType.FOOT) {
            blockpos = pos.offset((EnumFacing) state.getValue(FACING));
        }

        return new ItemStack(TatamiMats.itemTatamiFull, 1);
    }

    /**
     * Called before the Block is set to air in the world. Called regardless of if the player's tool can actually
     * collect this block
     */
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        if (player.capabilities.isCreativeMode && state.getValue(PART) == EnumPartType.FOOT) {
            BlockPos blockpos = pos.offset((EnumFacing) state.getValue(FACING));

            if (worldIn.getBlockState(blockpos).getBlock() == this) {
                worldIn.setBlockToAir(blockpos);
            }
        }
    }

    /**
     * Spawns the block's drops in the world. By the time this is called the Block has possibly been set to air via
     * Block.removedByPlayer
     */
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
        if (state.getValue(PART) == EnumPartType.HEAD) {
            spawnAsEntity(worldIn, pos, new ItemStack(TatamiMats.itemTatamiFull, 1));
        } else {
            super.harvestBlock(worldIn, player, pos, state, (TileEntity) null, stack);
        }
    }

    public enum EnumPartType implements IStringSerializable {
        HEAD("head"), FOOT("foot");

        private final String name;

        EnumPartType(String name) {
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
