package com.jhxiaochen.dollancustomized.tileentity;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.common.JacarandaStandingSign;
import com.jhxiaochen.dollancustomized.common.JacarandaWallSign;
import com.jhxiaochen.dollancustomized.data.AxisPosition;
import com.jhxiaochen.dollancustomized.network.NetworkManager;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityJacarandaSign extends TileEntitySign {

    // 背面文本
    public final ITextComponent[] backText = new ITextComponent[] {
        new TextComponentString(""),
        new TextComponentString(""),
        new TextComponentString(""),
        new TextComponentString("")
    };

    // 涂蜡状态
    private boolean waxed_0 = false;
    private boolean waxed_1 = false;

    // 编辑状态
    public  boolean isUsing = false;
    private int editedSlot = 0;
    private int hasTextSides = -1;

    // For GUI rendering
    public int lineBeingEdited = -1;

    // 获取文本面数量
    public int getTextSides() {
        return this.hasTextSides;
    }

    // 获取当前编辑的槽位
    public int getEditedSlot() {
        return this.editedSlot;
    }

    // 设置编辑槽位
    public void setEditedSlot(int editedSlot) {
        this.editedSlot = editedSlot;
    }

    // 获取父方块
    public Block getParentBlock() {
        return this.getParentBlockState().getBlock();
    }

    public IBlockState getParentBlockState() {
        return this.world.getBlockState(this.pos);
    }

    @Override
    public boolean getIsEditable() {
        return true; // 强制设置为可编辑
    }

    // 通过面获取文本数组
    public ITextComponent[] getTextForSlot(int slot) {
        return slot == 0 ? this.signText : this.backText;
    }

    // 更新文本面状态
    public void upTextSides() {
        boolean has0 = this.hasText(0);
        boolean has1 = this.hasText(1);

        if (has0 && has1) {
            this.hasTextSides = 2; // 两面都有
        } else if (has0) {
            this.hasTextSides = 0; // 仅正面（0号槽位）有
        } else if (has1) {
            this.hasTextSides = 1; // 仅背面（1号槽位）有
        } else {
            this.hasTextSides = -1; // 都没有
        }
    }

    // 检查指定面是否有文本
    public boolean hasText(int slot) {
        if (this.isUsing) {
            return true; // 此方块正在编辑, 不允许修改
        }

        // 检查 slot 面 4 行文本是否非空
        ITextComponent[] targetText = (slot == 0) ? signText : backText;
        for (ITextComponent line : targetText) {
            if (line != null && !line.getUnformattedText().trim().isEmpty()) {
                return true;
            }
        }

        return false; // 所有行都为空
    }

    // 检查这一面是否有文本
    public boolean hasAny(int slot) {
        return this.hasText(slot);
    }

    // 编辑文本
    public boolean editText(int slot, EntityPlayer player) {
        if (slot != 0 && slot != 1) return false;

        if (this.isWaxed(slot)) return false; // 涂蜡面禁止编辑

        // 设置编辑面
        this.setEditedSlot(slot);

        // 锁定告示牌
        this.isUsing = true;

        if (world.isRemote) {
            // 客户端直接打开编辑界面
            player.openGui(DollanCustomized.instance,
                          com.jhxiaochen.dollancustomized.client.gui.GuiHandler.JACARANDA_SIGN_EDIT_GUI_ID,
                          world, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }

        // 服务器端发送初始文本数据到客户端
        NetworkManager.sendSignTextUpdate(
            (EntityPlayerMP) player,
            pos,
            slot,
            this.getTextForSlot(slot),
            true
        );

        // 发送消息给玩家
        player.sendMessage(new net.minecraft.util.text.TextComponentString(
            "正在编辑" + (slot == 0 ? "正面" : "背面") + "（按住Shift+右键切换面）"));

        return true;
    }

    // 更新指定面的文本
    public void updateText(int slot, ITextComponent[] text, boolean isLocking) {
        this.isUsing = isLocking;
        if (text == null) return;

        // 保存旧文本用于比较
        ITextComponent[] oldText = new ITextComponent[4];
        if (slot == 0) {
            System.arraycopy(this.signText, 0, oldText, 0, 4);
        } else {
            System.arraycopy(this.backText, 0, oldText, 0, 4);
        }

        // 更新文本（只更新当前编辑的面，不修改另一个面）
        if (slot == 0) {
            System.arraycopy(text, 0, this.signText, 0, text.length);
        } else if (slot == 1) {
            System.arraycopy(text, 0, this.backText, 0, text.length);
        } else {
            return;
        }

        // 标记为脏并更新文本面状态
        this.markDirty();
        this.upTextSides();

        // 清除渲染位置缓存
        this.removeTextRenderPos(slot);

        // 只有文本发生变化时才进行网络更新
        if (this.world != null && !this.textsEqual(oldText, text)) {
            if (this.world.isRemote) {
                // 客户端：发送更新到服务器
                NetworkManager.updateSignData(pos, createSignNBT());

                // 更新本地渲染
                world.markBlockRangeForRenderUpdate(pos, pos);

                // 添加文本变化特效
                this.addTextChangeEffect();
            } else {
                // 服务器端：通知更新和广播
                world.notifyBlockUpdate(pos, getBlockType().getDefaultState(), getBlockType().getDefaultState(), 3);
                world.markChunkDirty(pos, this);

                // 广播更新到所有客户端
                NetworkManager.sendSignTextUpdateToAll(
                    pos,
                    slot,
                    text,
                    isLocking,
                    world.provider.getDimension(),
                    pos.getX(),
                    pos.getY(),
                    pos.getZ(),
                    64
                );
            }
        }
    }

    // 比较两个文本数组是否相等
    private boolean textsEqual(ITextComponent[] text1, ITextComponent[] text2) {
        if (text1.length != text2.length) return false;
        for (int i = 0; i < text1.length; i++) {
            String str1 = text1[i] != null ? text1[i].getUnformattedText() : "";
            String str2 = text2[i] != null ? text2[i].getUnformattedText() : "";
            if (!str1.equals(str2)) {
                return false;
            }
        }
        return true;
    }

    // 添加文本变化特效（可扩展）
    private void addTextChangeEffect() {
        // 这里可以添加粒子效果或其他视觉效果
        // 例如：当文本发生变化时，播放一个轻微的粒子效果
    }

    // 解锁告示牌
    public void unlock() {
        this.isUsing = false;
        this.markDirty();
    }

    // 涂蜡方法
    public boolean tryWaxed(int slot, ItemStack heldItem) {
        if (slot != 0 && slot != 1) return false;
        if (this.isWaxed(slot)) return false; // 已经涂蜡
        if (heldItem == null || heldItem.isEmpty()) return false;

        // 没在使用 && 有文本才能涂蜡
        if (!this.isUsing && !this.hasText(slot)) return false;

        // 检查是否是蜡（使用 OreDictionary）
        int[] oreIDs = OreDictionary.getOreIDs(heldItem);
        for (int id : oreIDs) {
            String oreName = OreDictionary.getOreName(id);
            if ("slimeball".equals(oreName) || "honeycomb".equals(oreName) || "beeswax".equals(oreName)) {
                return this.setWaxed(slot);
            }
        }

        // 检查是否是蜡（通过注册名）
        String registryName = heldItem.getItem().getRegistryName().toString();
        if (registryName.contains("honeycomb") || registryName.contains("slimeball")) {
            return this.setWaxed(slot);
        }

        // 检查是否是蜡烛（额外支持）
        if (registryName.contains("candle") || registryName.contains("wax")) {
            return this.setWaxed(slot);
        }

        return false;
    }

    public boolean setWaxed(int slot) {
        if (slot == 0) {
            this.waxed_0 = true;
        } else {
            this.waxed_1 = true;
        }
        this.markDirty();

        if (!world.isRemote) {
            // 服务器端通知更新
            world.notifyBlockUpdate(pos, getBlockType().getDefaultState(), getBlockType().getDefaultState(), 3);

            // 发送更新到所有客户端
            NetworkManager.sendSignTextUpdateToAll(
                pos,
                slot,
                null,
                false,
                world.provider.getDimension(),
                pos.getX(),
                pos.getY(),
                pos.getZ(),
                64
            );
        }

        return true;
    }

    public boolean isWaxed(int slot) {
        if (slot == 0) return this.waxed_0;
        return this.waxed_1;
    }

    // 检查是否有任意一面被涂蜡
    public boolean hasWaxedSide() {
        return this.waxed_0 || this.waxed_1;
    }

    // 获取涂蜡面的数量
    public int getWaxedSideCount() {
        return (this.waxed_0 ? 1 : 0) + (this.waxed_1 ? 1 : 0);
    }

    // 处理来自原版包的文本更新
    public void processVanillaUpdate(ITextComponent[] text) {
        // 更新正面文本（原版包总是更新正面）
        System.arraycopy(text, 0, this.signText, 0, 4);
        this.markDirty();
        this.upTextSides();
    }

    // 获取当前文本的简单方法
    public ITextComponent[] getCurrentText() {
        return this.getTextForSlot(this.editedSlot);
    }

    // 创建NBT数据（用于客户端发送到服务器）
    public NBTTagCompound createSignNBT() {
        NBTTagCompound nbt = new NBTTagCompound();

        // 保存当前编辑的槽位
        nbt.setInteger("EditSlot", this.editedSlot);
        nbt.setBoolean("IsLocking", this.isUsing);

        // 保存文本数据
        ITextComponent[] currentText = this.getTextForSlot(this.editedSlot);
        for (int i = 0; i < 4; i++) {
            if (currentText[i] != null) {
                nbt.setString("Text" + (i + 1), ITextComponent.Serializer.componentToJson(currentText[i]));
            }
        }

        return nbt;
    }

    // 存储 & 读取
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        // 使用setter设置isEditable
        this.setEditable(false);

        ICommandSender icommandsender = new ICommandSender() {
            public String getName() {
                return "Sign";
            }
            public boolean canUseCommand(int permLevel, String commandName) {
                return permLevel <= 2;
            }
            public BlockPos getPosition() {
                return TileEntityJacarandaSign.this.pos;
            }
            public Vec3d getPositionVector() {
                return new Vec3d((double)TileEntityJacarandaSign.this.pos.getX() + 0.5D,
                               (double)TileEntityJacarandaSign.this.pos.getY() + 0.5D,
                               (double)TileEntityJacarandaSign.this.pos.getZ() + 0.5D);
            }
            public World getEntityWorld() {
                return TileEntityJacarandaSign.this.world;
            }
            public MinecraftServer getServer() {
                return TileEntityJacarandaSign.this.world.getMinecraftServer();
            }
        };

        for (int i = 0; i < 4; i++) {
            String s = compound.getString("Text" + (i + 1));
            ITextComponent itextcomponent = ITextComponent.Serializer.jsonToComponent(s);

            try {
                this.signText[i] = TextComponentUtils.processComponent(icommandsender, itextcomponent, null);
            } catch (CommandException var7) {
                this.signText[i] = itextcomponent;
            }
        }

        // 读取背面文本
        if (compound.hasKey("BackText")) {
            NBTTagCompound backTextTag = compound.getCompoundTag("BackText");
            for (int i = 0; i < 4; i++) {
                String s = backTextTag.getString("Line" + (i + 1));
                if (!s.isEmpty()) {
                    backText[i] = ITextComponent.Serializer.jsonToComponent(s);
                }
            }
        }

        // 读取涂蜡状态
        if (compound.hasKey("waxed_0")) {
            this.waxed_0 = compound.getBoolean("waxed_0");
        }
        if (compound.hasKey("waxed_1")) {
            this.waxed_1 = compound.getBoolean("waxed_1");
        }

        // 直接使用继承的stats字段（通过反射）
        try {
            java.lang.reflect.Field statsField = TileEntitySign.class.getDeclaredField("stats");
            statsField.setAccessible(true);
            CommandResultStats stats = (CommandResultStats) statsField.get(this);
            if (stats != null) {
                stats.readStatsFromNBT(compound);
            }
        } catch (Exception e) {
            // 如果反射失败，就跳过stats的读取
        }

        this.upTextSides();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);

        // 保存背面文本
        NBTTagCompound backTextTag = new NBTTagCompound();
        for (int i = 0; i < 4; i++) {
            String s = ITextComponent.Serializer.componentToJson(this.backText[i]);
            backTextTag.setString("Line" + (i + 1), s);
        }
        compound.setTag("BackText", backTextTag);

        // 保存涂蜡状态
        if (this.waxed_0) {
            compound.setBoolean("waxed_0", this.waxed_0);
        }
        if (this.waxed_1) {
            compound.setBoolean("waxed_1", this.waxed_1);
        }

        // 直接使用继承的stats字段（通过反射）
        try {
            java.lang.reflect.Field statsField = TileEntitySign.class.getDeclaredField("stats");
            statsField.setAccessible(true);
            CommandResultStats stats = (CommandResultStats) statsField.get(this);
            if (stats != null) {
                stats.writeStatsToNBT(compound);
            }
        } catch (Exception e) {
            // 如果反射失败，就跳过stats的写入
        }

        return compound;
    }

    // 更新包
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.getPos(), 1, this.getUpdateTag());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound tag = super.writeToNBT(new NBTTagCompound());
        writeToNBT(tag);
        return tag;
    }

    @Override
    public void onDataPacket(net.minecraft.network.NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        NBTTagCompound tag = pkt.getNbtCompound();
        this.readFromNBT(tag);
    }

    private AxisPosition textRenderPos_0;
    private AxisPosition textRenderPos_1;

    public AxisPosition getTextRenderPos(int slot) {
        if (slot == 0)
            return textRenderPos_0;
        else if (slot == 1)
            return textRenderPos_1;
        else
            return null;
    }

    public void setTextRenderPos(int slot, AxisPosition renderPos) {
        if (slot == 0)
            this.textRenderPos_0 = renderPos;
        else if (slot == 1)
            this.textRenderPos_1 = renderPos;
    }

    private void removeTextRenderPos(int slot) {
        if (slot == 0)
            this.textRenderPos_0 = null;
        else if (slot == 1)
            this.textRenderPos_1 = null;
    }

    public AxisPosition getTextRenderPosition(int slot) {
        AxisPosition renderPos = this.getTextRenderPos(slot);

        if (renderPos != null) return renderPos; // 有已存储的渲染位置直接返回, 避免重复获取

        IBlockState state = this.getWorld().getBlockState(this.getPos());
        Block block = state.getBlock();

        // 竖直告示牌
        if (block instanceof JacarandaStandingSign) {
            // 根据朝向获取渲染位置
            int meta = state.getValue(JacarandaStandingSign.ROTATION);
            if (slot == 1) {
                meta = (meta + 8) % 16; // 计算对称面meta值
            }
            renderPos = JACARANDA_SIGN_ROTATION_MAP.get(meta);
        }
        // 墙上告示牌
        else if (block instanceof JacarandaWallSign) {
            int meta = getBlockMetadata( );
            if (slot == 1) {
                renderPos = JACARANDA_SIGN_WALL_ROTATION_MAP_1.get(meta);
            } else renderPos = JACARANDA_SIGN_WALL_ROTATION_MAP.get(meta);
        }

        // 默认返回: 北方 0 槽
        if (renderPos == null) {
            renderPos = JACARANDA_SIGN_ROTATION_MAP.get(0);
        }

        // 存储渲染位置
        this.setTextRenderPos(slot, renderPos);
        return renderPos;
    }

    // 渲染位置映射
    private static final java.util.Map<Integer, AxisPosition> JACARANDA_SIGN_ROTATION_MAP = java.util.Collections.unmodifiableMap(
        new java.util.HashMap<Integer, AxisPosition>() {{
        put(0, new AxisPosition(0.5, 0.5408, 0f));
        put(1, new AxisPosition(0.4844, 0.5377, -22.5f));
        put(2, new AxisPosition(0.4711, 0.5289, -45f));
        put(3, new AxisPosition(0.4617, 0.5146, -67.5f));
        put(4, new AxisPosition(0.4592, 0.5, 270f));
        put(5, new AxisPosition(0.4617, 0.4854, 247.5f));
        put(6, new AxisPosition(0.4711, 0.4711, 225f));
        put(7, new AxisPosition(0.4844, 0.4623, 202.5f));
        put(8, new AxisPosition(0.5, 0.4592, 180f));
        put(9, new AxisPosition(0.5156, 0.4623, 157.5f));
        put(10, new AxisPosition(0.5289, 0.4711, 135f));
        put(11, new AxisPosition(0.5383, 0.4854, 112.5f));
        put(12, new AxisPosition(0.5408, 0.5, 90f));
        put(13, new AxisPosition(0.5383, 0.5146, 67.5f));
        put(14, new AxisPosition(0.5289, 0.5289, 45f));
        put(15, new AxisPosition(0.5156, 0.5377, 22.5f));
        }}
    );

    private static final java.util.Map<Integer, AxisPosition> JACARANDA_SIGN_WALL_ROTATION_MAP = java.util.Collections.unmodifiableMap(
        new java.util.HashMap<Integer, AxisPosition>() {{
            put(2, new AxisPosition(0.5, 0.8965, 180f));
            put(4, new AxisPosition(0.1035, 0.5, -90f));
            put(3, new AxisPosition(0.5, 0.1035, 0f));
            put(5, new AxisPosition(0.8965, 0.5, 90f));
        }}
    );

    private static final java.util.Map<Integer, AxisPosition> JACARANDA_SIGN_WALL_ROTATION_MAP_1 = java.util.Collections.unmodifiableMap(
        new java.util.HashMap<Integer, AxisPosition>() {{
            put(2, new AxisPosition(0.5, 0.1035, 0f));
            put(4, new AxisPosition(0.1035, 0.5, -90F));
            put(3, new AxisPosition(0.5, 0.1035, 180f));
            put(5, new AxisPosition(0.1035, 0.5, 90f));
        }}
    );

}