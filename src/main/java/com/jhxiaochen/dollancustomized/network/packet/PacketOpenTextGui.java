package com.jhxiaochen.dollancustomized.network.packet;

import com.jhxiaochen.dollancustomized.client.gui.GuiHeightLimitSignCustom;
import com.jhxiaochen.dollancustomized.client.gui.GuiRoadText;
import com.jhxiaochen.dollancustomized.client.gui.GuiStationNameBoard;
import com.jhxiaochen.dollancustomized.common.tileentity.TileEntityHeightLimitSignCustom;
import com.jhxiaochen.dollancustomized.common.tileentity.TileEntityRoadText;
import com.jhxiaochen.dollancustomized.common.tileentity.TileEntityStationNameBoard;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 服务端 → 客户端：打开自定义文字方块编辑 GUI。
 *
 * <p>在 1.12.2 Forge 中，{@code EntityPlayer#openGui} 只有在 {@code getServerGuiElement}
 * 返回非空 {@link net.minecraft.inventory.Container} 时才会向客户端发送打开 GUI 的包；
 * 纯客户端 GUI（无容器）在服务端调用 {@code openGui} 会静默无操作，导致文字方块右键无法编辑。
 * 因此这里改为像 {@link PacketJacarandaSignGui} 一样，由服务端发送一个自定义包，
 * 客户端 Handler（{@code @SideOnly(CLIENT)}）收到后直接创建并显示对应 GUI。
 *
 * <p>外层类只引用公共类，客户端 GUI 类只在内部 Handler 中引用，避免服务端加载本类时
 * 触发 {@code NoClassDefFoundError: GuiScreen}。
 */
public class PacketOpenTextGui extends BasePacket {

    public static final int GUI_ROAD_TEXT = 1;
    public static final int GUI_HEIGHT_LIMIT_SIGN = 2;
    public static final int GUI_STATION_NAME_BOARD = 4;

    private int guiId;

    public PacketOpenTextGui() {
    }

    public PacketOpenTextGui(BlockPos pos, int guiId) {
        super(pos);
        this.guiId = guiId;
    }

    public int getGuiId() {
        return guiId;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = readBlockPos(buf);
        guiId = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        writeBlockPos(buf, pos);
        buf.writeInt(guiId);
    }

    public static class Handler implements IMessageHandler<PacketOpenTextGui, IMessage> {

        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketOpenTextGui message, MessageContext ctx) {
            Minecraft minecraft = Minecraft.getMinecraft();
            minecraft.addScheduledTask(() -> {
                World world = minecraft.world;
                if (world == null || message.pos == null) {
                    return;
                }
                TileEntity te = world.getTileEntity(message.pos);
                switch (message.guiId) {
                    case GUI_ROAD_TEXT:
                        if (te instanceof TileEntityRoadText) {
                            minecraft.displayGuiScreen(new GuiRoadText((TileEntityRoadText) te));
                        }
                        break;
                    case GUI_HEIGHT_LIMIT_SIGN:
                        if (te instanceof TileEntityHeightLimitSignCustom) {
                            minecraft.displayGuiScreen(new GuiHeightLimitSignCustom((TileEntityHeightLimitSignCustom) te));
                        }
                        break;
                    case GUI_STATION_NAME_BOARD:
                        if (te instanceof TileEntityStationNameBoard) {
                            minecraft.displayGuiScreen(new GuiStationNameBoard((TileEntityStationNameBoard) te));
                        }
                        break;
                    default:
                        break;
                }
            });
            return null;
        }
    }
}
