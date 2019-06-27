package com.wags13.tatami.features.shojipanels.blocks;

import com.wags13.tatami.features.shojipanels.ShojiPanels;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockPanelFloral extends BlockPanel{

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        Block blockNorth = worldIn.getBlockState(pos.offset(EnumFacing.NORTH)).getBlock();
        Block blockSouth = worldIn.getBlockState(pos.offset(EnumFacing.SOUTH)).getBlock();
        Block blockEast = worldIn.getBlockState(pos.offset(EnumFacing.EAST)).getBlock();
        Block blockWest = worldIn.getBlockState(pos.offset(EnumFacing.WEST)).getBlock();

        boolean canConnectToNorth = canPaneConnectTo(worldIn, pos, EnumFacing.NORTH) || blockNorth == ShojiPanels.PANEL_DOOR;
        boolean canConnectToSouth = canPaneConnectTo(worldIn, pos, EnumFacing.SOUTH) || blockSouth == ShojiPanels.PANEL_DOOR;
        boolean canConnectToEast = canPaneConnectTo(worldIn, pos, EnumFacing.EAST) || blockEast == ShojiPanels.PANEL_DOOR;
        boolean canConnectToWest = canPaneConnectTo(worldIn, pos, EnumFacing.WEST) || blockWest == ShojiPanels.PANEL_DOOR;

        boolean post = !canConnectToEast && !canConnectToNorth && !canConnectToSouth && !canConnectToWest;

        state = state.withProperty(NORTH, canConnectToNorth)
                .withProperty(SOUTH, canConnectToSouth)
                .withProperty(WEST, canConnectToWest)
                .withProperty(EAST, canConnectToEast);

        IBlockState upState = worldIn.getBlockState(pos.up());
        IBlockState downState = worldIn.getBlockState(pos.down());

        Block upBlock = upState.getBlock();
        Block downBlock = downState.getBlock();

        boolean up = upBlock instanceof  BlockPanelFloral;
        boolean down = downBlock instanceof BlockPanelFloral || downBlock instanceof BlockPanelDoor;

        if (post) {
            return state.withProperty(TYPE, EnumBlockType.POST);
        } else if (up && down) {
            return state.withProperty(TYPE, EnumBlockType.MIDDLE);
        } else if (up && !down) {
            if (downBlock.getClass().getSuperclass().equals(BlockPanel.class))
                return state.withProperty(TYPE, BlockPanel.EnumBlockType.BOTTOM);
            return state.withProperty(TYPE, EnumBlockType.BASE);
        } else if (!up && down) {
            return state.withProperty(TYPE, EnumBlockType.TOP);
        } else {
            return state.withProperty(TYPE, EnumBlockType.SQUARE);
        }
    }
}
