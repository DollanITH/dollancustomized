package com.jhxiaochen.dollancustomized.common.item;

import com.jhxiaochen.dollancustomized.common.block.BlockLightBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jspecify.annotations.Nullable;

import java.util.List;

/**
 * 光源方块物品 —— 对齐 1.17+ 原版光源方块的右键交互。
 *
 * 1.12.2 服务端右键流程（PlayerInteractionManager.processRightClickBlock）中，
 * 潜行时会跳过方块的 onBlockActivated，而 Forge 的 onItemUseFirst 无论是否潜行
 * 都会最先执行，因此在这里拦截：
 *
 *  - 点击已放置的光源方块，且玩家为创造模式 + OP2（1.12.2 canUseCommandBlock，
 *    对应原版 canUseGameMasterBlocks）：
 *      普通右键 → 等级 +1（15 归零循环）；
 *      潜行右键 → 直接设为 15；
 *  - 其余情况返回 PASS，走默认放置逻辑（与原版一致：可把光源方块放到任意方块表面，
 *    生存模式同样可以放置，只是不能调整等级）。
 */
public class ItemLightBlock extends ItemMultiTexture {

    public ItemLightBlock(Block block) {
        super(block, block, ignored -> BlockLightBlock.LEVEL.getName());
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof BlockLightBlock) {
            if (!player.canUseCommandBlock()) {
                return EnumActionResult.PASS;
            }
            if (world.isRemote) {
                return EnumActionResult.SUCCESS;
            }
            if (player.isSneaking()) {
                world.setBlockState(pos, state.withProperty(BlockLightBlock.LEVEL, 15), 3);
            } else {
                world.setBlockState(pos, state.cycleProperty(BlockLightBlock.LEVEL), 3);
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!player.canUseCommandBlock()) {
            return EnumActionResult.PASS;
        }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("item.dollancustomized.light_level", stack.getMetadata()));
    }

}
