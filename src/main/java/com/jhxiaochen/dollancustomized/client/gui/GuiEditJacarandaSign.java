package com.jhxiaochen.dollancustomized.client.gui;

import com.jhxiaochen.dollancustomized.common.JacarandaHangingSign;
import com.jhxiaochen.dollancustomized.common.JacarandaHangingSignAttached;
import com.jhxiaochen.dollancustomized.common.JacarandaStandingSign;
import com.jhxiaochen.dollancustomized.common.JacarandaWallSign;
import com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class GuiEditJacarandaSign extends GuiScreen {
    private              int                     updateCounter;
    private              int                     editingSlot;
    private              String                  titleText;
    private final        TileEntityJacarandaSign tile;
    private              int                     editLine = 0;
    private              Deque<String[]>         undoStack = new ArrayDeque<>();
    private              long                    lastCheckTime = 0;
    private static final double                  MAX_DISTANCE_SQ = 5.0 * 5.0;
    private boolean needSave;
    private boolean isClosing = false;

    // 按钮ID
    private static final int BTN_CLEAR = 0;
    private static final int BTN_UNDO = 1;
    private static final int BTN_DONE = 2;
    private static final int BTN_CANCEL = 3;

    public GuiEditJacarandaSign(TileEntityJacarandaSign tile) {
        this.tile = tile;
        this.needSave = true;
        this.editingSlot = tile.getEditedSlot();

        String slotDescription = this.editingSlot == 0 ?
            I18n.format("sign.edit") :
            I18n.format("sign.edit") + " - " + I18n.format("sign.back");
        this.titleText = I18n.format("sign.edit") + " - " + slotDescription;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();

        // 添加4个按钮
        int buttonY = this.height - 50;
        int halfScreen = this.width / 2;

        this.buttonList.add(new GuiButton(BTN_CLEAR, halfScreen - 100, buttonY, 98, 20, "×"));
        this.buttonList.add(new GuiButton(BTN_UNDO, halfScreen + 2, buttonY, 98, 20, "↺"));
        this.buttonList.add(new GuiButton(BTN_DONE, halfScreen - 100, buttonY + 24, 98, 20, I18n.format("gui.done")));
        this.buttonList.add(new GuiButton(BTN_CANCEL, halfScreen + 2, buttonY + 24, 98, 20, I18n.format("gui.cancel")));

        this.backupText();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        // 绘制标题
        drawCenteredString(fontRenderer, this.titleText, this.width / 2, 40, 0xFFFFFF);

        // 绘制3D告示牌（只渲染一次，避免重复）
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)(this.width / 2), 0.0F, 50.0F);
        float f = 93.75F;
        GlStateManager.scale(-f, -f, -f);
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);

        Block block = tile.getBlockType();

        if (block instanceof JacarandaStandingSign || block instanceof JacarandaHangingSignAttached) {
            float f1 = (float)(tile.getBlockMetadata() * 360) / 16.0F;
            GlStateManager.rotate(f1, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.0F, -1.0625F, 0.0F);
        } else if (block instanceof JacarandaWallSign) {
            int i = tile.getBlockMetadata();
            float f2 = 0.0F;

            if (i == 2) {
                f2 = 180.0F;
            } else if (i == 4) {
                f2 = 90.0F;
            } else if (i == 5) {
                f2 = -90.0F;
            }

            GlStateManager.rotate(f2, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.0F, -1.0625F, 0.0F);
        } else if (block instanceof JacarandaHangingSign) {
            int meta = tile.getBlockMetadata();
            float rotation = 180.0F;

            if (meta == 2) {
                rotation = 0.0F;
            } else if (meta == 4) {
                rotation = 90.0F;
            } else if (meta == 5) {
                rotation = -90.0F;
            }

            GlStateManager.rotate(-rotation, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.0F, -1.0625F, 0.0F);
        }

        if (this.updateCounter / 6 % 2 == 0) {
            tile.lineBeingEdited = editLine;
        }

        if (editingSlot == 1) {
            GlStateManager.rotate(180.0F,0.0F, 1.0F, 0.0F);
        }

        TileEntityRendererDispatcher.instance.render(tile, -0.5D, -0.75D, -0.5D, 0.0F);
        tile.lineBeingEdited = -1;
        GlStateManager.popMatrix();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        // ESC键关闭GUI
        if (keyCode == Keyboard.KEY_ESCAPE) {
            this.needSave = false;
            this.isClosing = true;
            this.mc.displayGuiScreen(null);
            return;
        }

        // 处理特殊按键 - 参考原版GuiEditSign
        if (keyCode == 200) {  // UP
            this.editLine = this.editLine - 1 & 3;
            return;
        }

        if (keyCode == 208 || keyCode == 28 || keyCode == 156) {  // DOWN, RETURN, TAB
            this.editLine = this.editLine + 1 & 3;
            return;
        }

        // 处理删除键
        ITextComponent[] textArray = this.tile.getTextForSlot(editingSlot);
        if (textArray == null) {
            textArray = new ITextComponent[4];
        }

        String currentText = textArray[editLine].getUnformattedText( );

        if (keyCode == 14 && !currentText.isEmpty( )) {  // BACKSPACE
            this.backupText( );
            currentText = currentText.substring(0, currentText.length( ) - 1);
            textArray[editLine] = new TextComponentString(currentText);
            this.tile.updateText(editingSlot, textArray, false);
            return;
        }

        // 处理输入字符 - 移除 ASCII 限制，支持中文输入
        if (ChatAllowedCharacters.isAllowedCharacter(typedChar) && this.fontRenderer.getStringWidth(currentText + typedChar) <= 90)
        {
            if (currentText.length( ) < 15) {
                this.backupText( );
                currentText = currentText + typedChar;
                textArray[editLine] = new TextComponentString(currentText);
                this.tile.updateText(editingSlot, textArray, false);
            }
        }
        // 返回后，让父类处理键盘重复事件和输入法编辑器（IME）
    }

    private void backupText() {
        if (this.undoStack.size() >= 500) {
            this.undoStack.removeLast();
        }

        ITextComponent[] text = this.tile.getTextForSlot(editingSlot);
        this.undoStack.push(Arrays.stream(text)
            .map(t -> t != null ? t.getUnformattedText() : "")
            .toArray(String[]::new));
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        ++this.updateCounter;

        if (System.currentTimeMillis() - lastCheckTime > 1000) {
            EntityPlayer player = this.mc.player;
            double distSq = player.getDistanceSq(
                tile.getPos().getX() + 0.5,
                tile.getPos().getY() + 0.5,
                tile.getPos().getZ() + 0.5
            );

            if (distSq > MAX_DISTANCE_SQ) {
                this.mc.displayGuiScreen(null);
            }

            lastCheckTime = System.currentTimeMillis();
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (!button.enabled) {
            return;
        }

        if (button.id == BTN_CLEAR) {
            this.backupText();
            ITextComponent[] text = this.tile.getTextForSlot(editingSlot);
            for (int i = 0; i < 4; i++) {
                text[i] = new TextComponentString("");
            }
            this.tile.updateText(editingSlot, text, false);
        } else if (button.id == BTN_UNDO && !this.undoStack.isEmpty()) {
            String[] prevState = this.undoStack.pop();
            ITextComponent[] text = this.tile.getTextForSlot(editingSlot);
            for (int i = 0; i < 4 && i < prevState.length; i++) {
                text[i] = new TextComponentString(prevState[i]);
            }
            this.tile.updateText(editingSlot, text, false);
        } else if (button.id == BTN_DONE) {
            this.needSave = true;
            this.isClosing = true;
            this.mc.displayGuiScreen(null);
        } else if (button.id == BTN_CANCEL) {
            this.needSave = false;
            this.isClosing = true;
            this.mc.displayGuiScreen(null);
        }
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);

        // 保存当前编辑的文本
        ITextComponent[] newText = this.tile.getTextForSlot(editingSlot);

        if (this.isClosing) {
            if (this.needSave) {
                NBTTagCompound nbt = tile.createSignNBT(editingSlot);
                com.jhxiaochen.dollancustomized.network.NetworkManager.sendUpdateSignToServer(tile.getPos(), nbt);
                tile.updateText(editingSlot, newText, false);
            } else {
                tile.unlock();
            }
            tile.setEditable(true);
        }

        this.isClosing = false;
        this.needSave = true;
    }
}
