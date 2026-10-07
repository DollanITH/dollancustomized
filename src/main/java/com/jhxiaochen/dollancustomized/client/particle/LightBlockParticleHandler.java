package com.jhxiaochen.dollancustomized.client.particle;

import com.jhxiaochen.dollancustomized.registry.blocks.DollanBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 手持光源方块时，持续扫描玩家周围已放置的光源方块并生成显形粒子
 * （LightBlockParticle → ParticleBlockConditional，按坐标去重）。
 * 判断条件与选中箱（ClientProxy.getLightSelectionBox）保持一致：主手或副手任一手持。
 */
@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT)
public class LightBlockParticleHandler {

    /** 扫描半径（格）：只显示玩家周围这个范围内的光源方块 */
    private static final int RANGE = 24;
    /** 单批粒子上限，防止光源方块过多时粒子数量爆炸 */
    private static final int MAX_PARTICLES = 96;
    /** 扫描间隔（tick） */
    private static final int SCAN_INTERVAL = 2;

    private static World lastWorld;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getMinecraft();
        World world = mc.world;
        EntityPlayer player = mc.player;
        if (world == null || player == null) return;

        // 切换世界/维度时清空旧世界的条件粒子
        if (world != lastWorld) {
            ParticleBlockConditional.clear();
            lastWorld = world;
        }

        // 仅手持光源方块时显示（主手或副手）
        Item lightItem = Item.getItemFromBlock(DollanBlocks.light_block);
        boolean holding = player.getHeldItemMainhand().getItem() == lightItem
                || player.getHeldItemOffhand().getItem() == lightItem;
        if (!holding) return;

        if (world.getTotalWorldTime() % SCAN_INTERVAL != 0) return;

        BlockPos center = player.getPosition();
        BlockPos min = center.add(-RANGE, -RANGE, -RANGE);
        BlockPos max = center.add(RANGE, RANGE, RANGE);

        int spawned = 0;
        for (BlockPos.MutableBlockPos pos : BlockPos.getAllInBoxMutable(min, max)) {
            IBlockState state = world.getBlockState(pos);
            if (state.getBlock() == DollanBlocks.light_block) {
                // 去重生成：同坐标粒子已存在时仅刷新寿命
                ParticleBlockConditional.spawnParticle(
                        new LightBlockParticle(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D));
                if (++spawned >= MAX_PARTICLES) break;
            }
        }
    }
}
