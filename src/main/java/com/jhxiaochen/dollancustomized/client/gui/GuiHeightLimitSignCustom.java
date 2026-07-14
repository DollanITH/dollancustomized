package com.jhxiaochen.dollancustomized.client.gui;

import com.jhxiaochen.dollancustomized.network.MessageHeightLimitSignCustom;
import com.jhxiaochen.dollancustomized.network.NetworkHandler;
import com.jhxiaochen.dollancustomized.tileentity.TileEntityHeightLimitSignCustom;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;

import java.io.IOException;

public class GuiHeightLimitSignCustom extends GuiScreen {
    private GuiTextField textField;
    private final TileEntityHeightLimitSignCustom tileEntity;
    private GuiButton doneButton;
    private GuiButton cancelButton;

    public GuiHeightLimitSignCustom(TileEntityHeightLimitSignCustom te) {
        this.tileEntity = te;
    }

    @Override
    public void initGui() {
        super.initGui();

        // 强制释放鼠标焦点，确保指针显示
        if (this.mc != null && this.mc.inGameHasFocus) {
            this.mc.setIngameNotInFocus();
        }

        if (this.fontRenderer == null) return;

        this.textField = new GuiTextField(0, this.fontRenderer, this.width / 2 - 100, this.height / 2 - 10, 200, 20);
        this.textField.setMaxStringLength(10);
        this.textField.setText(this.tileEntity.heightValue != null ? this.tileEntity.heightValue : "3.0");

        this.doneButton = this.addButton(new GuiButton(1, this.width / 2 - 4 - 100, this.height / 2 + 20, 100, 20, I18n.format("gui.done")));
        this.cancelButton = this.addButton(new GuiButton(2, this.width / 2 + 4, this.height / 2 + 20, 100, 20, I18n.format("gui.cancel")));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 1) {
            saveAndClose();
        } else if (button.id == 2) {
            this.mc.displayGuiScreen(null);
        }
    }

    // 【新增】提取保存并关闭的逻辑为独立方法，方便键盘事件调用
    private void saveAndClose() {
        String input = this.textField.getText();
        // 验证输入：只允许数字和小数点
        if (input == null || input.isEmpty() || !input.matches("^[0-9]+(\\.[0-9]*)?$")) {
            // 如果输入无效，使用默认值
            input = "3.0";
        }
        NetworkHandler.DOLLAN_NETWORK.sendToServer(new MessageHeightLimitSignCustom(this.tileEntity.getPos(), input));
        this.mc.displayGuiScreen(null);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        // 1. 将键盘输入转发给文本框
        this.textField.textboxKeyTyped(typedChar, keyCode);

        // 2. 拦截 ESC 键 (keyCode == 1)
        if (keyCode == 1) {
            this.mc.displayGuiScreen(null);
            return; // 直接退出，不调用 super，防止重复触发
        }

        // 3. 拦截 ENTER 键 (keyCode == 28)
        if (keyCode == 28) {
            saveAndClose();
            return;
        }

        // 4. 其他按键交给父类处理
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.textField.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void updateScreen() {
        this.textField.updateCursorCounter();
        super.updateScreen();
    }

    @Override
    public void drawDefaultBackground() {
        if (this.mc != null) {
            super.drawDefaultBackground();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (this.textField != null) {
            this.textField.drawTextBox();
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}