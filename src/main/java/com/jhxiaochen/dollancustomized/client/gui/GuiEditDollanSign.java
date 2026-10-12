package com.jhxiaochen.dollancustomized.client.gui;

import com.cleanroommc.client.IMEHandler;
import com.jhxiaochen.dollancustomized.common.block.wood.DollanHangingSign;
import com.jhxiaochen.dollancustomized.common.block.wood.DollanHangingSignAttached;
import com.jhxiaochen.dollancustomized.common.block.wood.DollanStandingSign;
import com.jhxiaochen.dollancustomized.common.block.wood.DollanWallSign;
import com.jhxiaochen.dollancustomized.common.tileentity.TileEntityDollanSign;
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

public class GuiEditDollanSign extends GuiScreen {
    private int updateCounter;
    private int editingSlot;
    private String titleText;
    private final TileEntityDollanSign tile;
    private int editLine = 0;
    private Deque<String[]> undoStack = new ArrayDeque<>();
    private long lastCheckTime = 0;
    private static final double MAX_DISTANCE_SQ = 5.0 * 5.0;
    private boolean needSave;
    private boolean isClosing = false;

    // 按钮ID
    private static final int BTN_CLEAR = 0;
    private static final int BTN_UNDO = 1;
    private static final int BTN_DONE = 2;
    private static final int BTN_CANCEL = 3;

    public GuiEditDollanSign(TileEntityDollanSign tile) {
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
        Keyboard.enableRepeatEvents(true);
        IMEHandler.setIME(true);

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
        GlStateManager.translate((float) (this.width / 2), 0.0F, 50.0F);
        float f = 93.75F;
        GlStateManager.scale(-f, -f, -f);
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);

        Block block = tile.getBlockType();

        if (block instanceof DollanStandingSign || block instanceof DollanHangingSignAttached) {
            float f1 = (float) (tile.getBlockMetadata() * 360) / 16.0F;
            GlStateManager.rotate(f1, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.0F, -1.0625F, 0.0F);
        } else if (block instanceof DollanWallSign) {
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
        } else if (block instanceof DollanHangingSign) {
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
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        }

        TileEntityRendererDispatcher.instance.render(tile, -0.5D, -0.75D, -0.5D, 0.0F);
        tile.lineBeingEdited = -1;
        GlStateManager.popMatrix();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        // 输入法上屏的字符 keyCode == 0，必须放在 ESC 判断之前不受影响
        // 原版结构：先处理换行/退格，再处理字符，最后统一写回

        if (keyCode == Keyboard.KEY_ESCAPE)          // ESC
        {
            this.needSave = false;
            this.isClosing = true;
            this.mc.displayGuiScreen(null);
            return;
        }

        if (keyCode == 200)                          // UP
        {
            this.editLine = this.editLine - 1 & 3;
            return;
        }

        if (keyCode == 208 || keyCode == 28 || keyCode == 156)  // DOWN / ENTER / NUMPAD_ENTER
        {
            this.editLine = this.editLine + 1 & 3;
            return;
        }

        // 每次都重新获取，避免引用过期
        ITextComponent[] textArray = this.tile.getTextForSlot(editingSlot);
        if (textArray == null)
        {
            textArray = new ITextComponent[4];
            for (int i = 0; i < 4; i++) textArray[i] = new TextComponentString("");
        }
        // 关键：防止 NPE
        if (textArray[editLine] == null)
        {
            textArray[editLine] = new TextComponentString("");
        }

        String s = textArray[editLine].getUnformattedText();

        if (keyCode == 14 && !s.isEmpty())           // BACKSPACE
        {
            this.backupText();
            s = s.substring(0, s.length() - 1);
            textArray[editLine] = new TextComponentString(s);
            this.tile.updateText(editingSlot, textArray, false);
            return;
        }

        // 字符输入：含 IME 上屏（keyCode == 0）
        if (ChatAllowedCharacters.isAllowedCharacter(typedChar)
                && this.fontRenderer.getStringWidth(s + typedChar) <= 90)
        {
            this.backupText();
            s = s + typedChar;
            textArray[editLine] = new TextComponentString(s);
            this.tile.updateText(editingSlot, textArray, false);
        }
        // 不命中时不写回，与原版"统一写回"不同，避免空更新触发不必要的同步
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
            // 用反射调用：部分服务端/客户端运行时没有 func_145913_a 这个符号，
            // 直接写 tile.setEditable(true) 会抛 NoSuchMethodError
            com.jhxiaochen.dollancustomized.common.interaction.ExtendedPlayer.setSignEditable(tile, true);
        }

        this.isClosing = false;
        this.needSave = true;
    }
}
