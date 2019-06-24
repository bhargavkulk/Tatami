package com.wags13.tatami.features.tatamimats.items;

import com.wags13.tatami.Tatami;
import com.wags13.tatami.features.tatamimats.TatamiMats;
import com.wags13.tatami.features.tatamimats.blocks.BlockTatamiFull;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/*else if (facing != EnumFacing.UP) {
            return EnumActionResult.FAIL;
        } */

public class ItemTatamiFull extends Item {

    public ItemTatamiFull() {
        this.setCreativeTab(Tatami.tabTatami);
    }

    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos footPos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (worldIn.isRemote) {
            return EnumActionResult.SUCCESS;
        } else {
            IBlockState foot = worldIn.getBlockState(footPos);
            Block footBlock = foot.getBlock();
            boolean isReplaceable = footBlock.isReplaceable(worldIn, footPos);

            if (!isReplaceable) {
                footPos = footPos.offset(facing);
            }

            EnumFacing enumfacing = player.getHorizontalFacing();
            BlockPos headPos = footPos.offset(enumfacing);
            ItemStack itemstack = player.getHeldItem(hand);

            if (player.canPlayerEdit(footPos, facing, itemstack) && player.canPlayerEdit(headPos, facing, itemstack)) {
                IBlockState head = worldIn.getBlockState(headPos);

                /// checks if the block where the foot is to be placed is replaceable
                boolean isFootPlaceable = isReplaceable || worldIn.isAirBlock(footPos);
                boolean isHeadPlaceable = head.getBlock().isReplaceable(worldIn, headPos) || worldIn.isAirBlock(headPos);

                if (isFootPlaceable && isHeadPlaceable) {
                    IBlockState footState = TatamiMats.tatamiFull.getDefaultState()
                            .withProperty(BlockTatamiFull.FACING, enumfacing)
                            .withProperty(BlockTatamiFull.PART, BlockTatamiFull.EnumPartType.FOOT);
                    IBlockState headState = footState.withProperty(BlockTatamiFull.PART, BlockTatamiFull.EnumPartType.HEAD)
                            .withProperty(BlockTatamiFull.FACING, enumfacing.getOpposite());

                    worldIn.setBlockState(footPos, footState, 10);
                    worldIn.setBlockState(headPos, headState, 10);
                    SoundType soundtype = footState.getBlock().getSoundType(footState, worldIn, footPos, player);
                    worldIn.playSound((EntityPlayer) null, footPos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);

                    worldIn.notifyNeighborsRespectDebug(footPos, footBlock, false);
                    worldIn.notifyNeighborsRespectDebug(headPos, head.getBlock(), false);

                    itemstack.shrink(1);
                    return EnumActionResult.SUCCESS;
                } else {
                    return EnumActionResult.FAIL;
                }
            } else {
                return EnumActionResult.FAIL;
            }
        }
    }

}
