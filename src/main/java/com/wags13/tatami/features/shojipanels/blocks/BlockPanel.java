package com.wags13.tatami.features.shojipanels.blocks;

import com.wags13.tatami.Tatami;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockPanel extends BlockPane {

    public static PropertyEnum<EnumBlockType> TYPE = PropertyEnum.create("type", EnumBlockType.class);

    public BlockPanel() {
        super(Material.CLOTH, true);
        this.setCreativeTab(Tatami.tabTatami);
        this.setSoundType(SoundType.CLOTH);
        this.setHardness(0.8f);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{TYPE, NORTH, SOUTH, EAST, WEST});
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {

        Block blockNorth = worldIn.getBlockState(pos.offset(EnumFacing.NORTH)).getBlock();
        Block blockSouth = worldIn.getBlockState(pos.offset(EnumFacing.SOUTH)).getBlock();
        Block blockEast = worldIn.getBlockState(pos.offset(EnumFacing.EAST)).getBlock();
        Block blockWest = worldIn.getBlockState(pos.offset(EnumFacing.WEST)).getBlock();


        boolean canConnectToNorth = canPaneConnectTo(worldIn, pos, EnumFacing.NORTH) || blockNorth instanceof BlockPanelDoor;
        boolean canConnectToSouth = canPaneConnectTo(worldIn, pos, EnumFacing.SOUTH) || blockSouth instanceof BlockPanelDoor;
        boolean canConnectToEast = canPaneConnectTo(worldIn, pos, EnumFacing.EAST) || blockEast instanceof BlockPanelDoor;
        boolean canConnectToWest = canPaneConnectTo(worldIn, pos, EnumFacing.WEST) || blockWest instanceof BlockPanelDoor;

        boolean post = !canConnectToEast && !canConnectToNorth && !canConnectToSouth && !canConnectToWest;

        state = state.withProperty(NORTH, canConnectToNorth)
                .withProperty(SOUTH, canConnectToSouth)
                .withProperty(WEST, canConnectToWest)
                .withProperty(EAST, canConnectToEast);

        IBlockState upState = worldIn.getBlockState(pos.up());
        IBlockState downState = worldIn.getBlockState(pos.down());

        boolean top = false;
        boolean down = false;

        if (upState.getBlock() instanceof BlockPanel) {
            top = true;
        }

        if ((downState.getBlock() instanceof BlockPanel || downState.getBlock() instanceof BlockPanelDoor)
                && !downState.getBlock().isAir(downState, worldIn, pos.down())) {
            down = true;
        }

        if(top && down) {
            return state.withProperty(TYPE, EnumBlockType.MIDDLE);
        } else if(down) {
            return state.withProperty(TYPE, EnumBlockType.TOP);
        } else {
            return state.withProperty(TYPE, EnumBlockType.BOTTOM);
        }

    }

    @Override
    public boolean canPaneConnectTo(IBlockAccess world, BlockPos pos, EnumFacing dir) {
        return super.canPaneConnectTo(world, pos, dir);
    }

    @Override
    protected boolean canSilkHarvest() {
        return false;
    }

    enum EnumBlockType implements IStringSerializable {
        BOTTOM("bottom"),
        MIDDLE("middle"),
        TOP("top"),
        POST("post");

        private final String name;

        EnumBlockType(String name) {
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
