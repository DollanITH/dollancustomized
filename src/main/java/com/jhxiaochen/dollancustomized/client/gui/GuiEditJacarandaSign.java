package com.jhxiaochen.dollancustomized.client.gui;

import com.jhxiaochen.dollancustomized.client.renderer.tileentity.TileEntityJacarandaSignRenderer;
import com.jhxiaochen.dollancustomized.common.JacarandaStandingSign;
import com.jhxiaochen.dollancustomized.tileentity.TileEntityJacarandaSign;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.play.client.CPacketUpdateSign;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class GuiEditJacarandaSign extends GuiScreen {
    private final TileEntityJacarandaSign tileSign;
    private int updateCounter;
    private int editLine;
    private GuiButton doneBtn;

    public GuiEditJacarandaSign(TileEntityJacarandaSign teSign) {
        // 确保告示牌是可编辑的
        teSign.setEditable(true);
        this.tileSign = teSign;
    }

    @Override
    public void initGui() {
        super.initGui();

        // 强制释放鼠标焦点，确保指针显示
        if (this.mc != null && this.mc.inGameHasFocus) {
            this.mc.setIngameNotInFocus();
        }

        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        this.doneBtn = this.addButton(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120, I18n.format("gui.done")));
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);

        if (this.mc.getConnection() != null) {
            this.mc.getConnection().sendPacket(new CPacketUpdateSign(this.tileSign.getPos(), this.tileSign.signText));
        }
    }

    @Override
    public void updateScreen() {
        ++this.updateCounter;
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.enabled) {
            if (button.id == 0) {
                this.tileSign.markDirty();
                this.mc.displayGuiScreen((GuiScreen)null);
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 200) {
            this.editLine = this.editLine - 1 & 3;
        }

        if (keyCode == 208 || keyCode == 28 || keyCode == 156) {
            this.editLine = this.editLine + 1 & 3;
        }

        String s = this.tileSign.signText[this.editLine].getUnformattedText();

        if (keyCode == 14 && !s.isEmpty()) {
            s = s.substring(0, s.length() - 1);
        }

        if (ChatAllowedCharacters.isAllowedCharacter(typedChar) && this.fontRenderer.getStringWidth(s + typedChar) <= 90) {
            s = s + typedChar;
        }

        this.tileSign.signText[this.editLine] = new TextComponentString(s);

        if (keyCode == 1) {
            this.actionPerformed(this.doneBtn);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, I18n.format("sign.edit"), this.width / 2, 40, 16777215);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)(this.width / 2), 0.0F, 50.0F);
        float f = 93.75F;
        GlStateManager.scale(-f, -f, -f);
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);

        // 处理我们的告示牌旋转
        if (this.tileSign.getWorld() != null) {
            IBlockState state;
            state = this.tileSign.getWorld().getBlockState(this.tileSign.getPos());
            Block block = state.getBlock();

            // 检查是否是站立告示牌
            if (block == JacarandaStandingSign.jacaranda_standing_sign) {
                int meta = state.getValue(JacarandaStandingSign.ROTATION);
                float rotation = (float)(meta * 360) / 16.0F;
                GlStateManager.rotate(rotation, 0.0F, 1.0F, 0.0F);
                GlStateManager.translate(0.0F, -1.0625F, 0.0F);
            }
            else
            {
                int i = this.tileSign.getBlockMetadata();
                float f2 = 0.0F;

                if (i == 2)
                {
                    f2 = 180.0F;
                }

                if (i == 4)
                {
                    f2 = 90.0F;
                }

                if (i == 5)
                {
                    f2 = -90.0F;
                }

                GlStateManager.rotate(f2, 0.0F, 1.0F, 0.0F);
                GlStateManager.translate(0.0F, -1.0625F, 0.0F);
            }
        }

        if (this.updateCounter / 6 % 2 == 0) {
            this.tileSign.lineBeingEdited = this.editLine;
        }

        // 使用自定义渲染器渲染蓝花楹木告示牌
        TileEntityJacarandaSignRenderer.instance.render(this.tileSign, -0.5D, -0.75D, -0.5D, 0.0F, -1, 1.0F);
        this.tileSign.lineBeingEdited = -1;
        GlStateManager.popMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}