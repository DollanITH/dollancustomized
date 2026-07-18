package com.jhxiaochen.dollancustomized.network;

import com.jhxiaochen.dollancustomized.client.gui.GuiJacarandaSign;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientPacketHandler {

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        // 这里可以添加快捷键处理逻辑
    }

    /**
     * 更新客户端的告示牌状态
     */
    public static void updateClientSign(BlockPos pos, int lineBeingEdited, String[] textLines) {
        World world = Minecraft.getMinecraft().world;
        if (world != null) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign) {
                com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign sign =
                    (com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign) tileEntity;

                // 更新编辑状态
                sign.setLineBeingEdited(lineBeingEdited);

                // 更新文本内容
                for (int i = 0; i < 4; i++) {
                    sign.setLineText(i, textLines[i].isEmpty()
                        ? new net.minecraft.util.text.TextComponentString("")
                        : new net.minecraft.util.text.TextComponentString(textLines[i]));
                }
            }
        }
    }

    /**
     * 打开告示牌GUI
     */
    public static void openSignGui(BlockPos pos) {
        World world = Minecraft.getMinecraft().world;
        if (world != null) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign) {
                com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign sign =
                    (com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign) tileEntity;

                // 打开告示牌编辑GUI
                Minecraft.getMinecraft().displayGuiScreen(new GuiJacarandaSign(sign, world, pos));
            }
        }
    }
}