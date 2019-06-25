package com.wags13.tatami.features.shojipanels.blocks;

import com.wags13.tatami.Tatami;
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
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{TYPE, NORTH, SOUTH, EAST, WEST});
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        boolean canConnectToNorth = canPaneConnectTo(worldIn, pos, EnumFacing.NORTH);
        boolean canConnectToSouth = canPaneConnectTo(worldIn, pos, EnumFacing.SOUTH);
        boolean canConnectToEast = canPaneConnectTo(worldIn, pos, EnumFacing.EAST);
        boolean canConnectToWest = canPaneConnectTo(worldIn, pos, EnumFacing.WEST);
        state = state.withProperty(NORTH, canConnectToNorth)
                .withProperty(SOUTH, canConnectToSouth)
                .withProperty(WEST, canConnectToWest)
                .withProperty(EAST, canConnectToEast);

        IBlockState stte = worldIn.getBlockState(pos.up());
        boolean top = false;

        if (stte.getBlock() instanceof BlockPanel) {
            top = true;
        }

        stte = worldIn.getBlockState(pos.down());
        boolean down = false;

        if (stte.getBlock() instanceof BlockPanel) {
            down = true;
        }

        if (!canConnectToEast && !canConnectToNorth && !canConnectToSouth && !canConnectToWest) {
            return state.withProperty(TYPE, EnumBlockType.POST);
        } else if (top && down) {
            return state.withProperty(TYPE, EnumBlockType.MIDDLE);
        } else if (down) {
            return state.withProperty(TYPE, EnumBlockType.TOP);
        } else {
            return state.withProperty(TYPE, EnumBlockType.BOTTOM);
        }
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
