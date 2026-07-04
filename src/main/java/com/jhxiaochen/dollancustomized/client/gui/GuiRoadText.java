package com.jhxiaochen.dollancustomized.client.gui;

import com.jhxiaochen.dollancustomized.network.MessageRoadText;
import com.jhxiaochen.dollancustomized.network.NetworkHandler;
import com.jhxiaochen.dollancustomized.tileentity.TileEntityRoadText;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;

import java.io.IOException;

public class GuiRoadText extends GuiScreen {

    private GuiTextField textField;
    private final TileEntityRoadText tileEntity;
    private GuiButton doneButton;
    private GuiButton cancelButton;

    public GuiRoadText(TileEntityRoadText te) {
        this.tileEntity = te;
    }

    @Override
    public void initGui() {
        super.initGui();

        // 保护：如果字体渲染器为空，直接退出防止崩溃
        if (this.fontRenderer == null) {
            return;
        }

        // 初始化文本框 (居中)
        this.textField = new GuiTextField(0, this.fontRenderer, this.width / 2 - 100, this.height / 2 - 10, 200, 20);
        this.textField.setMaxStringLength(50); // 最大字符数
        this.textField.setText(this.tileEntity.text != null ? this.tileEntity.text : ""); // 防止 text 为 null

        // 初始化按钮
        this.doneButton = this.addButton(new GuiButton(1, this.width / 2 - 4 - 100, this.height / 2 + 20, 100, 20, I18n.format("gui.done")));
        this.cancelButton = this.addButton(new GuiButton(2, this.width / 2 + 4, this.height / 2 + 20, 100, 20, I18n.format("gui.cancel")));
    }


    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 1) {
            // 点击完成：发送网络包到服务端保存数据
            NetworkHandler.INSTANCE.sendToServer(new MessageRoadText(this.tileEntity.getPos(), this.textField.getText()));
            this.mc.displayGuiScreen(null); // 关闭 GUI
        } else if (button.id == 2) {
            // 点击取消：直接关闭
            this.mc.displayGuiScreen(null);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        // 将键盘输入转发给文本框
        this.textField.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        // 将鼠标点击转发给文本框
        this.textField.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void updateScreen() {
        this.textField.updateCursorCounter();
        super.updateScreen();
    }

    // 重写 drawDefaultBackground，添加保护措施
    @Override
    public void drawDefaultBackground() {
        // 只有在 mc 存在时才绘制默认背景，防止空指针
        if (this.mc != null) {
            super.drawDefaultBackground();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // 调用带有保护的背景绘制
        this.drawDefaultBackground();

        // 调用父类的 drawScreen (主要用于绘制按钮)
        super.drawScreen(mouseX, mouseY, partialTicks);

        // 确保 textField 存在再绘制，防止初始化失败导致崩溃
        if (this.textField != null) {
            this.textField.drawTextBox();
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false; // 不暂停游戏
    }
}
