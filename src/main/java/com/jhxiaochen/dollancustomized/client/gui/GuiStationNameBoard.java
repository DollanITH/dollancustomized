package com.jhxiaochen.dollancustomized.client.gui;

import com.cleanroommc.client.IMEHandler;
import com.jhxiaochen.dollancustomized.common.tileentity.TileEntityStationNameBoard;
import com.jhxiaochen.dollancustomized.network.NetworkManager;
import com.jhxiaochen.dollancustomized.network.packet.PacketStationNameBoard;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;

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

    /** 待重挂的字段（跨帧执行 false→true 序列） */
    private GuiTextField pendingRefocus;
    /** 重挂序列的执行进度：0=未开始, 1=已执行setFocused(false), 2=完成 */
    private int refocusStage = 0;
    /** 记录当前逻辑焦点，用于检测焦点变化 */
    private GuiTextField lastFocusTarget;


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
        Keyboard.enableRepeatEvents(true);
        IMEHandler.setIME(true);

        // 强制释放鼠标焦点，确保指针显示
        if (this.mc != null && this.mc.inGameHasFocus) {
            this.mc.setIngameNotInFocus();
        }

        if (this.fontRenderer == null) return;

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
        // 文本输入字段
        this.chineseTextField = new GuiTextField(0, this.fontRenderer, this.width / 2 - 100, this.height / 2 - 80, 200, 20);
        this.chineseTextField.setMaxStringLength(15);
        this.chineseTextField.setText(this.tileEntity.chineseText != null ? this.tileEntity.chineseText : "");

        this.chineseTextField.setFocused(true);
        this.lastFocusTarget = this.chineseTextField;  // ← 加这行

    }

    private String getFontSizeButtonName() {
        String[] sizeNames = {"小号", "中号", "大号"};
        return "字体大小: " + sizeNames[currentFontSize - 1];
    }

    // GuiRoadText 有，GuiStationNameBoard 完全没有：
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        super.onGuiClosed();
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
            r = Math.max(0, Math.min(255, r));
            g = Math.max(0, Math.min(255, g));
            b = Math.max(0, Math.min(255, b));

            // 只在值真正变化时才回写 setText，
            // 避免在 IME 组合过程中打断输入、避免重置光标位置
            if (currentRed != r) {
                currentRed = r;
                this.redTextField.setText(String.valueOf(r));
            }
            if (currentGreen != g) {
                currentGreen = g;
                this.greenTextField.setText(String.valueOf(g));
            }
            if (currentBlue != b) {
                currentBlue = b;
                this.blueTextField.setText(String.valueOf(b));
            }
        } catch (NumberFormatException e) {
            // 解析失败（含空文本、负号、非数字等），恢复为当前有效值，
            // 同样只在变化时回写
            String rText = String.valueOf(currentRed);
            String gText = String.valueOf(currentGreen);
            String bText = String.valueOf(currentBlue);
            if (!rText.equals(this.redTextField.getText())) {
                this.redTextField.setText(rText);
            }
            if (!gText.equals(this.greenTextField.getText())) {
                this.greenTextField.setText(gText);
            }
            if (!bText.equals(this.blueTextField.getText())) {
                this.blueTextField.setText(bText);
            }
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

    private GuiTextField getFocusedTextField() {
        if (this.chineseTextField != null && this.chineseTextField.isFocused()) {
            return this.chineseTextField;
        }
        if (this.englishTextField != null && this.englishTextField.isFocused()) {
            return this.englishTextField;
        }
        if (this.redTextField != null && this.redTextField.isFocused()) {
            return this.redTextField;
        }
        if (this.greenTextField != null && this.greenTextField.isFocused()) {
            return this.greenTextField;
        }
        if (this.blueTextField != null && this.blueTextField.isFocused()) {
            return this.blueTextField;
        }
        return null;
    }

    private boolean saveAndCloseCheck() {
        if (!chineseTextField.getText().isEmpty() && !englishTextField.getText().isEmpty()) {
            saveAndClose();
            return true;
        }
        return false;
    }

    /** 判断点落在哪个字段内，无则返回 null */
    private GuiTextField fieldAt(int mouseX, int mouseY) {
        if (inBounds(mouseX, mouseY, chineseTextField)) return chineseTextField;
        if (inBounds(mouseX, mouseY, englishTextField)) return englishTextField;
        if (inBounds(mouseX, mouseY, redTextField))     return redTextField;
        if (inBounds(mouseX, mouseY, greenTextField))   return greenTextField;
        if (inBounds(mouseX, mouseY, blueTextField))    return blueTextField;
        return null;
    }

    private boolean inBounds(int mx, int my, GuiTextField f) {
        return f != null
                && mx >= f.x && mx < f.x + f.width
                && my >= f.y && my < f.y + f.height;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        GuiTextField target = fieldAt(mouseX, mouseY);
        if (target != null) {
            // ── 焦点迁移：0 次中间态，直接切到目标 ──
            if (chineseTextField != null) chineseTextField.setFocused(target == chineseTextField);
            if (englishTextField != null) englishTextField.setFocused(target == englishTextField);
            if (redTextField != null)     redTextField.setFocused(target == redTextField);
            if (greenTextField != null)   greenTextField.setFocused(target == greenTextField);
            if (blueTextField != null)    blueTextField.setFocused(target == blueTextField);

            if (target == redTextField || target == greenTextField || target == blueTextField) {
                updateRGBFromFields();
            }

            // ── 关键：目标字段若是"逻辑焦点变化"，登记跨帧重挂 ──
            if (target != lastFocusTarget) {
                lastFocusTarget = target;
                pendingRefocus = target;
                refocusStage = 0;  // 下一次 updateScreen 开始序列
            }
            return;
        }

        // 点击空白：0 次焦点变更，不登记重挂
        if (getFocusedTextField() == null && chineseTextField != null) {
            chineseTextField.setFocused(true);
            lastFocusTarget = chineseTextField;
            pendingRefocus = chineseTextField;
            refocusStage = 0;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        // 1. 先无条件转发给聚焦字段（GuiRoadText 同款：不判定焦点直接转，
        //    未聚焦字段的 textboxKeyTyped 自身会拒绝处理）
        GuiTextField focused = getFocusedTextField();

        if (focused == null) {
            focused = chineseTextField;
            if (focused != null) {
                focused.setFocused(true);
                lastFocusTarget = focused;   // ← 同步
                pendingRefocus = focused;    // ← 顺带补一次重挂
                refocusStage = 0;
            }
        }

        if (focused != null) {
            focused.textboxKeyTyped(typedChar, keyCode);
        }

        // 2. ESC（转发之后处理，与 GuiRoadText 一致）
        if (keyCode == Keyboard.KEY_ESCAPE) {
            this.mc.displayGuiScreen(null);
            return;
        }

        // 3. Enter 提交
        if (keyCode == Keyboard.KEY_RETURN || keyCode == Keyboard.KEY_NUMPADENTER) {
            updateRGBFromFields();
            saveAndClose();
            return;
        }

        // 4. 其余交父类（保持 Cleanroom 输入链路完整）
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void updateScreen() {
        // ── IME 重挂序列：setFocused(false) → 等一帧 → setFocused(true) ──
        // 这模拟了"GUI 重新打开"的完整挂载时序，让 Cleanroom 把 IME 上下文
        // 重新绑定到当前字段。拆成两帧是因为同一帧内 false→true 会被
        // 输入层当作"无变化"合并掉，这正是点击切焦点后 IME 丢失的根源。
        if (pendingRefocus != null) {
            if (refocusStage == 0) {
                pendingRefocus.setFocused(false);
                refocusStage = 1;
            } else if (refocusStage == 1) {
                pendingRefocus.setFocused(true);
                pendingRefocus = null;
                refocusStage = 0;
            }
        }

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
