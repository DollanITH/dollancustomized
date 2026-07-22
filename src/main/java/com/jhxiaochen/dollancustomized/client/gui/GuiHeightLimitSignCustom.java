package com.jhxiaochen.dollancustomized.client.gui;

import com.jhxiaochen.dollancustomized.network.NetworkManager;
import com.jhxiaochen.dollancustomized.network.packet.PacketHeightLimitSign;
import com.jhxiaochen.dollancustomized.tileentity.TileEntityHeightLimitSignCustom;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiHeightLimitSignCustom extends GuiScreen {
    private GuiTextField integerField;   // 整数部分输入 (1-99)
    private GuiTextField decimalField;   // 小数部分输入 (0-9)
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

        int centerX = this.width / 2;
        int centerY = this.height / 2;

        // 整数部分输入框（限制为个位数）
        this.integerField = new GuiTextField(0, this.fontRenderer,
            centerX - 60, centerY - 15, 40, 20);
        this.integerField.setMaxStringLength(1);
        this.integerField.setText(String.valueOf(tileEntity.getIntegerPart()));
        this.integerField.setFocused(true);

        // 小数部分输入框
        this.decimalField = new GuiTextField(1, this.fontRenderer,
            centerX + 20, centerY - 15, 30, 20);
        this.decimalField.setMaxStringLength(1);
        this.decimalField.setText(String.valueOf(tileEntity.getDecimalPart()));

        // 按钮
        this.doneButton = this.addButton(new GuiButton(1, centerX - 4 - 100, centerY + 20, 100, 20, I18n.format("gui.done")));
        this.cancelButton = this.addButton(new GuiButton(2, centerX + 4, centerY + 20, 100, 20, I18n.format("gui.cancel")));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 1) {
            saveAndClose();
        } else if (button.id == 2) {
            this.mc.displayGuiScreen(null);
        }
    }

    private void saveAndClose() {
        try {
            int integer = Integer.parseInt(this.integerField.getText());
            int decimal = Integer.parseInt(this.decimalField.getText());

            // 验证输入范围（整数部分限制为0-9）
            integer = Math.max(2, Math.min(6, integer));
            decimal = Math.max(0, Math.min(9, decimal));

            // 创建并发送包到服务器
            PacketHeightLimitSign packet = new PacketHeightLimitSign(this.tileEntity.getPos(),
                String.format("%d.%d", integer, decimal));
            NetworkManager.INSTANCE.sendToServer(packet);
        } catch (NumberFormatException e) {
            // 输入无效，恢复默认值4.8
            this.integerField.setText("4");
            this.decimalField.setText("8");
        }

        this.mc.displayGuiScreen(null);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        // 处理整数部分
        this.integerField.textboxKeyTyped(typedChar, keyCode);

        // 处理小数部分
        this.decimalField.textboxKeyTyped(typedChar, keyCode);

        // Tab键切换焦点
        if (keyCode == Keyboard.KEY_TAB) {
            if (this.integerField.isFocused()) {
                this.integerField.setFocused(false);
                this.decimalField.setFocused(true);
            } else {
                this.decimalField.setFocused(false);
                // 在 Minecraft 1.12.2 中，我们不手动调用按钮的 mouseClicked
                // 只需要设置焦点，按钮会自动处理点击
            }
            return;
        }

        // 拦截ESC键 (keyCode == 1)
        if (keyCode == 1) {
            this.mc.displayGuiScreen(null);
            return;
        }

        // 拦截ENTER键 (keyCode == 28)
        if (keyCode == 28) {
            saveAndClose();
            return;
        }

        // 其他按键交给父类处理
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        // 点击文本框（GuiTextField 需要传入 mouseButton 参数）
        this.integerField.mouseClicked(mouseX, mouseY, mouseButton);
        this.decimalField.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void updateScreen() {
        this.integerField.updateCursorCounter();
        this.decimalField.updateCursorCounter();
        super.updateScreen();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        // 绘制标签（使用粗体和黄色以增强可见性）
        String integerLabel = TextFormatting.YELLOW + TextFormatting.BOLD.toString() + "整数部分(2-6):";
        String decimalLabel = TextFormatting.YELLOW + TextFormatting.BOLD.toString() + "小数部分(0-9):";
        this.fontRenderer.drawString(integerLabel, this.width / 2 - 75, this.height / 2 - 30, 0xFFFFFF);
        this.fontRenderer.drawString(decimalLabel, this.width / 2 + 5, this.height / 2 - 30, 0xFFFFFF);

        this.integerField.drawTextBox();
        this.decimalField.drawTextBox();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
