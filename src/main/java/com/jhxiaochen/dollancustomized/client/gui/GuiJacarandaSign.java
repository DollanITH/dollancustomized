package com.jhxiaochen.dollancustomized.client.gui;

import com.jhxiaochen.dollancustomized.network.NetworkManager;
import com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.io.IOException;

public class GuiJacarandaSign extends GuiScreen {
    private final TileEntityJacarandaSign sign;
    private final BlockPos pos;
    private final World world;

    // 编辑状态
    private int currentLine = 0;
    private boolean isEditable = false;
    private String[] tempLines = new String[4];

    public GuiJacarandaSign(TileEntityJacarandaSign sign, World world, BlockPos pos) {
        this.sign = sign;
        this.world = world;
        this.pos = pos;
        loadCurrentText();
    }

    private void loadCurrentText() {
        // 从NBT加载当前文本
        NBTTagCompound nbt = new NBTTagCompound();
        sign.writeToNBT(nbt);

        // 加载所有行文本
        for (int i = 0; i < 4; i++) {
            tempLines[i] = nbt.getString("Line" + i);
        }

        // 加载当前编辑行
        currentLine = nbt.getInteger("EditingLine");
    }

    @Override
    public void initGui() {
        this.buttonList.clear();

        // 添加"完成"按钮
        this.buttonList.add(new GuiButtonSign(0, this.width / 2 - 100, this.height - 52, I18n.format("gui.done")));

        // 设置编辑状态
        isEditable = true;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        // 绘制告示牌编辑界面
        this.drawSignEditScreen();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawSignEditScreen() {
        int centerX = this.width / 2;
        int centerY = this.height / 2 - 50;

        // 绘制标题
        drawString(this.fontRenderer, I18n.format("tile.jacaranda_sign.name"), centerX - 50, centerY - 60, 0xFFFFFF);

        // 绘制编辑背景框
        drawRect(centerX - 101, centerY - 21, centerX + 101, centerY + 81, 0xE0000000);

        // 绘制边框
        drawRect(centerX - 100, centerY - 20, centerX + 100, centerY - 19, 0xFF808080);
        drawRect(centerX - 100, centerY + 80, centerX + 100, centerY + 81, 0xFF808080);
        drawRect(centerX - 100, centerY - 20, centerX - 99, centerY + 80, 0xFF808080);
        drawRect(centerX + 99, centerY - 20, centerX + 100, centerY + 80, 0xFF808080);

        // 绘制4行文本编辑框
        for (int i = 0; i < 4; i++) {
            int y = centerY + i * 20;

            // 高亮当前编辑行
            if (i == currentLine) {
                drawRect(centerX - 99, y - 1, centerX + 99, y + 18, 0x40808080);
            }

            // 绘制文本
            String text = tempLines[i] != null ? tempLines[i] : "";
            String displayText = text.length() > 15 ? text.substring(0, 15) : text;

            // 如果是当前编辑行，添加光标
            if (i == currentLine) {
                long time = System.currentTimeMillis();
                if (time % 2000 < 1000) {
                    displayText += "_";
                }
            }

            drawString(this.fontRenderer, displayText, centerX - 95, y, 0xFFFFFF);

            // 绘制行号
            drawString(this.fontRenderer, (i + 1) + ":", centerX - 110, y, 0x808080);
        }

        // 绘制提示信息
        drawString(this.fontRenderer, I18n.format("gui.sign.click_to_edit"), centerX - 70, centerY + 95, 0x808080);
        drawString(this.fontRenderer, I18n.format("gui.sign.shift_click_for_colors"), centerX - 100, centerY + 110, 0x808080);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1 || Minecraft.getMinecraft().gameSettings.keyBindInventory.isActiveAndMatches(keyCode)) {
            // 按ESC键或inventory键，关闭GUI
            isEditable = false;
            currentLine = -1;
            this.mc.displayGuiScreen(null);
        } else if (keyCode >= 2 && keyCode <= 11) {
            // 数字键2-11选择编辑行（0-9对应行1-10）
            int line = keyCode - 2;
            if (line < 4) {
                currentLine = line;
            }
        } else if (keyCode == 200) {
            // 上箭头，切换到上一行
            if (currentLine > 0) {
                currentLine--;
            }
        } else if (keyCode == 208) {
            // 下箭头，切换到下一行
            if (currentLine < 3) {
                currentLine++;
            }
        } else {
            // 处理文本输入
            handleTextInput(typedChar, keyCode);
        }
    }

    private void handleTextInput(char typedChar, int keyCode) {
        String currentText = tempLines[currentLine] != null ? tempLines[currentLine] : "";

        if (keyCode == 14) {
            // Backspace键
            if (!currentText.isEmpty()) {
                String newText = currentText.substring(0, currentText.length() - 1);
                tempLines[currentLine] = newText;
            }
        } else if (keyCode == 28 || keyCode == 156) {
            // Enter键或Numpad Enter
            // 切换到下一行
            if (currentLine < 3) {
                currentLine++;
            }
        } else if (typedChar >= 32 && typedChar <= 126) {
            // 可打印字符
            String newText = currentText + typedChar;
            tempLines[currentLine] = newText;
        }

        // 限制每行字符数
        if (tempLines[currentLine] != null && tempLines[currentLine].length() > 15) {
            tempLines[currentLine] = tempLines[currentLine].substring(0, 15);
        }
    }

    @Override
    public void onGuiClosed() {
        // GUI关闭时保存文本并更新服务器
        saveCurrentText();
        sendUpdateToServer();
    }

    private void saveCurrentText() {
        // 保存当前编辑的文本到TileEntity
        NBTTagCompound nbt = new NBTTagCompound();
        sign.writeToNBT(nbt);

        // 保存所有行文本
        for (int i = 0; i < 4; i++) {
            nbt.setString("Line" + i, tempLines[i] != null ? tempLines[i] : "");
        }

        // 保存当前编辑行
        nbt.setInteger("EditingLine", currentLine);

        // 应用更新
        sign.readFromNBT(nbt);
    }

    private void sendUpdateToServer() {
        // 如果不是远程世界，直接更新
        if (world != null && !world.isRemote) {
            sign.markDirty();
            world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        } else {
            // 如果是远程世界，发送更新包到服务器
            com.jhxiaochen.dollancustomized.network.packet.PacketEditState packet =
                new com.jhxiaochen.dollancustomized.network.packet.PacketEditState(pos, currentLine, tempLines);
            NetworkManager.INSTANCE.sendToServer(packet);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    /**
     * 自定义按钮类
     */
    class GuiButtonSign extends net.minecraft.client.gui.GuiButton {
        public GuiButtonSign(int buttonId, int x, int y, String buttonText) {
            super(buttonId, x, y, 200, 20, buttonText);
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
        {
            this.enabled = true;
            this.visible = true;

            if (this.visible) {
                mc.getTextureManager().bindTexture(GuiButton.BUTTON_TEXTURES);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
                int i = this.getHoverState(this.hovered);
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                GlStateManager.blendFunc(770, 771);
                this.drawTexturedModalRect(this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
                this.drawTexturedModalRect(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
                this.mouseDragged(mc, mouseX, mouseY);
                int j = 14737632;

                if (packedFGColour != 0) {
                    j = packedFGColour;
                } else if (!this.enabled) {
                    j = 10526880;
                } else if (this.hovered) {
                    j = 16777120;
                }

                this.drawCenteredString(mc.fontRenderer, this.displayString, this.x + this.width / 2, this.y + (this.height - 8) / 2, j);
            }
        }

        @Override
        public void mouseReleased(int mouseX, int mouseY) {
            if (this.enabled && this.visible && mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height) {
                // 点击"完成"按钮
                isEditable = false;
                currentLine = -1;
                mc.displayGuiScreen(null);
            }
        }
    }
}