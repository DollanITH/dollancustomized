package com.jhxiaochen.dollancustomized.client.gui;

import com.jhxiaochen.dollancustomized.common.tileentity.TileEntityRoadText;
import com.jhxiaochen.dollancustomized.network.NetworkManager;
import com.jhxiaochen.dollancustomized.network.packet.PacketRoadText;
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

    // 颜色切换按钮
    private GuiButton colorButton;
    // 本地缓存的颜色状态（0=白，1=黄）
    private int currentColorMode;

    public GuiRoadText(TileEntityRoadText te) {
        this.tileEntity = te;
        this.currentColorMode = te.colorMode; // 初始化为方块当前的颜色
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
        this.textField.setMaxStringLength(50);
        this.textField.setText(this.tileEntity.text != null ? this.tileEntity.text : "");

        this.doneButton = this.addButton(new GuiButton(1, this.width / 2 - 4 - 100, this.height / 2 + 20, 100, 20, I18n.format("gui.done")));
        this.cancelButton = this.addButton(new GuiButton(2, this.width / 2 + 4, this.height / 2 + 20, 100, 20, I18n.format("gui.cancel")));

        this.colorButton = this.addButton(new GuiButton(3, this.width / 2 - 100, this.height / 2 - 40, 200, 20, getColorButtonName()));
    }

    private String getColorButtonName() {
        return "字体颜色: " + (currentColorMode == 0 ? "白色" : "黄色");
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 1) {
            saveAndClose();
        } else if (button.id == 2) {
            this.mc.displayGuiScreen(null);
        } else if (button.id == 3) {
            this.currentColorMode = (this.currentColorMode == 0) ? 1 : 0;
            this.colorButton.displayString = getColorButtonName();
        }
    }

    // 【新增】提取保存并关闭的逻辑为独立方法，方便键盘事件调用
    private void saveAndClose() {
        // 创建并发送包到服务器
        PacketRoadText packet = new PacketRoadText(this.tileEntity.getPos(), this.textField.getText(), this.currentColorMode);
        NetworkManager.INSTANCE.sendToServer(packet);
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
        // 保护：如果文本框正在被编辑（聚焦），则不拦截回车，让玩家可以输入换行（如果支持）或避免误触
        // 如果你的输入框不支持换行，其实可以直接保存。这里为了安全加上 !textField.isFocused()
        if (keyCode == 28 && !textField.isFocused()) {
            saveAndClose();
            return;
        }

        // 4. 如果点击了输入框使其聚焦，按回车直接保存（可选逻辑，提升体验）
        if (keyCode == 28 && textField.isFocused()) {
            saveAndClose();
            return;
        }

        // 5. 其他按键交给父类处理 (例如 Tab 切换焦点等)
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
