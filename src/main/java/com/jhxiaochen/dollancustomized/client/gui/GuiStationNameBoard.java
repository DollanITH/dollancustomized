package com.jhxiaochen.dollancustomized.client.gui;

import com.jhxiaochen.dollancustomized.common.tileentity.TileEntityStationNameBoard;
import com.jhxiaochen.dollancustomized.network.NetworkManager;
import com.jhxiaochen.dollancustomized.network.packet.PacketStationNameBoard;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;

import java.io.IOException;

public class GuiStationNameBoard extends GuiScreen {
    private       GuiTextField               chineseTextField;
    private       GuiTextField               englishTextField;
    private final TileEntityStationNameBoard tileEntity;
    private       GuiButton                  doneButton;
    private GuiButton cancelButton;

    // RGB输入字段
    private GuiTextField redTextField, greenTextField, blueTextField;
    private int currentRed, currentGreen, currentBlue;

    // 字体大小按钮
    private GuiButton fontSizeButton;
    private int currentFontSize;

    // 字体族按钮
    private GuiButton fontFamilyButton;
    private int currentFontFamilyIndex;
    private final String[] fontFamilyOptions = {"default", "cn_1", "cn_2"};
    private final String[] fontFamilyNames = {"默认字体", "字体1", "字体2"};

    public GuiStationNameBoard(TileEntityStationNameBoard te) {
        this.tileEntity = te;
        this.currentRed = te.colorRed;
        this.currentGreen = te.colorGreen;
        this.currentBlue = te.colorBlue;
        this.currentFontSize = te.fontSize;
        this.currentFontFamilyIndex = 0;
        for (int i = 0; i < fontFamilyOptions.length; i++) {
            if (fontFamilyOptions[i].equals(te.fontFamilyChinese)) {
                currentFontFamilyIndex = i;
                break;
            }
        }
    }

    @Override
    public void initGui() {
        super.initGui();

        // 强制释放鼠标焦点，确保指针显示
        if (this.mc != null && this.mc.inGameHasFocus) {
            this.mc.setIngameNotInFocus();
        }

        if (this.fontRenderer == null) return;

        // 文本输入字段
        this.chineseTextField = new GuiTextField(0, this.fontRenderer, this.width / 2 - 100, this.height / 2 - 80, 200, 20);
        this.chineseTextField.setMaxStringLength(15);
        this.chineseTextField.setText(this.tileEntity.chineseText != null ? this.tileEntity.chineseText : "");

        this.englishTextField = new GuiTextField(1, this.fontRenderer, this.width / 2 - 100, this.height / 2 - 40, 200, 20);
        this.englishTextField.setMaxStringLength(40);
        this.englishTextField.setText(this.tileEntity.englishText != null ? this.tileEntity.englishText : "");

        // RGB输入字段
        this.redTextField = new GuiTextField(2, this.fontRenderer, this.width / 2 - 100, this.height / 2, 60, 20);
        this.redTextField.setMaxStringLength(3);
        this.redTextField.setText(String.valueOf(currentRed));

        this.greenTextField = new GuiTextField(3, this.fontRenderer, this.width / 2 - 30, this.height / 2, 60, 20);
        this.greenTextField.setMaxStringLength(3);
        this.greenTextField.setText(String.valueOf(currentGreen));

        this.blueTextField = new GuiTextField(4, this.fontRenderer, this.width / 2 + 40, this.height / 2, 60, 20);
        this.blueTextField.setMaxStringLength(3);
        this.blueTextField.setText(String.valueOf(currentBlue));

        // 字体大小按钮
        this.fontSizeButton = this.addButton(new GuiButton(5, this.width / 2 - 100, this.height / 2 + 25, 200, 20, getFontSizeButtonName()));

        // 字体族按钮
        this.fontFamilyButton = this.addButton(new GuiButton(6, this.width / 2 - 100, this.height / 2 + 50, 200, 20, getFontFamilyButtonName()));

        // 确认和取消按钮
        this.doneButton = this.addButton(new GuiButton(7, this.width / 2 - 90, this.height / 2 + 85, 90, 20, I18n.format("gui.done")));
        this.cancelButton = this.addButton(new GuiButton(8, this.width / 2 + 4, this.height / 2 + 85, 90, 20, I18n.format("gui.cancel")));
    }

    private String getFontSizeButtonName() {
        String[] sizeNames = {"小号", "中号", "大号"};
        return "字体大小: " + sizeNames[currentFontSize - 1];
    }

    private String getFontFamilyButtonName() {
        return "中文字体样式: " + fontFamilyNames[currentFontFamilyIndex];
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 7) {
            saveAndClose();
        } else if (button.id == 8) {
            this.mc.displayGuiScreen(null);
        } else if (button.id == 5) {
            // 循环切换字体大小
            this.currentFontSize = (this.currentFontSize % 3) + 1;
            this.fontSizeButton.displayString = getFontSizeButtonName();
        } else if (button.id == 6) {
            // 循环切换字体族
            this.currentFontFamilyIndex = (this.currentFontFamilyIndex + 1) % fontFamilyOptions.length;
            this.fontFamilyButton.displayString = getFontFamilyButtonName();
        }
    }

    private void updateRGBFromFields() {
        try {
            int r = Integer.parseInt(this.redTextField.getText());
            int g = Integer.parseInt(this.greenTextField.getText());
            int b = Integer.parseInt(this.blueTextField.getText());

            // 限制范围
            currentRed = Math.max(0, Math.min(255, r));
            currentGreen = Math.max(0, Math.min(255, g));
            currentBlue = Math.max(0, Math.min(255, b));

            // 更新字段显示
            this.redTextField.setText(String.valueOf(currentRed));
            this.greenTextField.setText(String.valueOf(currentGreen));
            this.blueTextField.setText(String.valueOf(currentBlue));
        } catch (NumberFormatException e) {
            // 如果解析失败，恢复当前值
            this.redTextField.setText(String.valueOf(currentRed));
            this.greenTextField.setText(String.valueOf(currentGreen));
            this.blueTextField.setText(String.valueOf(currentBlue));
        }
    }

    private void saveAndClose() {
        // 确保RGB值有效
        updateRGBFromFields();

        // 创建并发送包到服务器
        PacketStationNameBoard packet = new PacketStationNameBoard(this.tileEntity.getPos(),
                                                                 this.chineseTextField.getText(),
                                                                 this.englishTextField.getText(),
                                                                 this.currentFontSize,
                                                                 this.currentRed, this.currentGreen, this.currentBlue,
                                                                 fontFamilyOptions[currentFontFamilyIndex],
                                                                 fontFamilyOptions[currentFontFamilyIndex]);
        NetworkManager.INSTANCE.sendToServer(packet);
        this.mc.displayGuiScreen(null);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        // 1. 将键盘输入转发给文本框
        if (this.chineseTextField.isFocused()) {
            this.chineseTextField.textboxKeyTyped(typedChar, keyCode);
        } else if (this.englishTextField.isFocused()) {
            this.englishTextField.textboxKeyTyped(typedChar, keyCode);
        } else if (this.redTextField.isFocused()) {
            if (keyCode == 28 || keyCode == 156) { // Enter or Numpad Enter
                updateRGBFromFields();
                if (!saveAndCloseCheck()) {
                    super.keyTyped(typedChar, keyCode);
                }
            } else {
                this.redTextField.textboxKeyTyped(typedChar, keyCode);
            }
        } else if (this.greenTextField.isFocused()) {
            if (keyCode == 28 || keyCode == 156) { // Enter or Numpad Enter
                updateRGBFromFields();
                if (!saveAndCloseCheck()) {
                    super.keyTyped(typedChar, keyCode);
                }
            } else {
                this.greenTextField.textboxKeyTyped(typedChar, keyCode);
            }
        } else if (this.blueTextField.isFocused()) {
            if (keyCode == 28 || keyCode == 156) { // Enter or Numpad Enter
                updateRGBFromFields();
                if (!saveAndCloseCheck()) {
                    super.keyTyped(typedChar, keyCode);
                }
            } else {
                this.blueTextField.textboxKeyTyped(typedChar, keyCode);
            }
        }

        // 2. 拦截 ESC 键 (keyCode == 1)
        if (keyCode == 1) {
            this.mc.displayGuiScreen(null);
            return;
        }

        // 3. 拦截 ENTER 键 (keyCode == 28)
        if (keyCode == 28 && !chineseTextField.isFocused() && !englishTextField.isFocused() &&
            !redTextField.isFocused() && !greenTextField.isFocused() && !blueTextField.isFocused()) {
            updateRGBFromFields();
            saveAndClose();
            return;
        }

        // 4. 其他按键交给父类处理
        super.keyTyped(typedChar, keyCode);
    }

    private boolean saveAndCloseCheck() {
        if (!chineseTextField.getText().isEmpty() && !englishTextField.getText().isEmpty()) {
            saveAndClose();
            return true;
        }
        return false;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.chineseTextField.mouseClicked(mouseX, mouseY, mouseButton);
        this.englishTextField.mouseClicked(mouseX, mouseY, mouseButton);
        this.redTextField.mouseClicked(mouseX, mouseY, mouseButton);
        this.greenTextField.mouseClicked(mouseX, mouseY, mouseButton);
        this.blueTextField.mouseClicked(mouseX, mouseY, mouseButton);

        // 当点击RGB字段时自动更新值
        if (redTextField.isFocused() || greenTextField.isFocused() || blueTextField.isFocused()) {
            updateRGBFromFields();
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void updateScreen() {
        this.chineseTextField.updateCursorCounter();
        this.englishTextField.updateCursorCounter();
        this.redTextField.updateCursorCounter();
        this.greenTextField.updateCursorCounter();
        this.blueTextField.updateCursorCounter();
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

        // 绘制标签和文本字段
        if (this.fontRenderer != null) {
            // 标题
            this.fontRenderer.drawString("车站大字壁设置", this.width / 2 - 35, this.height / 2 - 110, 0xFFFFFFFF);

            // 文本标签
            this.fontRenderer.drawString("中文文本:", this.width / 2 - 100, this.height / 2 - 95, 0xFFFFFFFF);
            this.fontRenderer.drawString("英文文本:", this.width / 2 - 100, this.height / 2 - 55, 0xFFFFFFFF);

            // RGB标签
            this.fontRenderer.drawString("RGB颜色 (0-255):", this.width / 2 - 100, this.height / 2 - 15, 0xFFFFFFFF);

            // 颜色预览
            this.drawRect(this.width / 2 + 140, this.height / 2 - 35, this.width / 2 + 170, this.height / 2 - 15,
                          (currentRed << 16) | (currentGreen << 8) | currentBlue);
        }

        // 绘制所有文本字段
        if (this.chineseTextField != null) {
            this.chineseTextField.drawTextBox();
        }
        if (this.englishTextField != null) {
            this.englishTextField.drawTextBox();
        }
        if (this.redTextField != null) {
            this.redTextField.drawTextBox();
        }
        if (this.greenTextField != null) {
            this.greenTextField.drawTextBox();
        }
        if (this.blueTextField != null) {
            this.blueTextField.drawTextBox();
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}