package com.jhxiaochen.dollancustomized.tileentity;

import com.jhxiaochen.dollancustomized.common.JacarandaHangingSign;
import com.jhxiaochen.dollancustomized.common.JacarandaHangingSignAttached;
import com.jhxiaochen.dollancustomized.data.AxisPosition;
import com.jhxiaochen.dollancustomized.init.DollanSounds;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 悬挂式蓝花楹木告示牌 TileEntity
 * 支持双面文本显示（正面和背面）
 */
public class TileEntityJacarandaHangingSign extends TileEntitySign {

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
    public boolean isUsing = false;
    private int editedSlot = 0;
    private int hasTextSides = -1;

    // 反射字段访问
    private java.lang.reflect.Field isEditableField;
    private java.lang.reflect.Field statsField;
    private boolean reflectionInit = false;

    // 物品存储
    private int hasDisplayedSides = -1;
    private static boolean lastPickedSide = true;
    private final ItemStackHandler itemHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            TileEntityJacarandaHangingSign.this.markDirty();
        }
    };

    // 初始化反射字段
    private void initReflection() {
        if (reflectionInit) return;
        try {
            java.lang.Class<?> signClass = TileEntitySign.class;
            isEditableField = signClass.getDeclaredField("isEditable");
            isEditableField.setAccessible(true);
            statsField = signClass.getDeclaredField("stats");
            statsField.setAccessible(true);
            reflectionInit = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    public int getDisplayedSides() {
        return this.hasDisplayedSides;
    }

    public static int getAndToggleLastPickedSide() {
        lastPickedSide = !lastPickedSide;
        return lastPickedSide ? 0 : 1;
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    public ItemStack getDisplayItem(int slot) {
        if (slot == 0 || slot == 1) {
            return this.itemHandler.getStackInSlot(slot);
        }
        return ItemStack.EMPTY;
    }

    // 获取父方块
    public Block getParentBlock() {
        return this.getParentBlockState().getBlock();
    }

    public IBlockState getParentBlockState() {
        return this.world.getBlockState(this.pos);
    }

    // 获取方块类型
    public Block getBlockType() {
        return this.getParentBlockState().getBlock();
    }

    @Override
    public boolean getIsEditable() {
        try {
            initReflection();
            if (isEditableField != null) {
                return isEditableField.getBoolean(this);
            }
        } catch (Exception e) {
            // 忽略错误
        }
        return true;
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
            this.hasTextSides = 2;
        } else if (has0) {
            this.hasTextSides = 0;
        } else if (has1) {
            this.hasTextSides = 1;
        } else {
            this.hasTextSides = -1;
        }

        this.removeTextRenderPos(has0 ? -1 : 0);
        this.removeTextRenderPos(has1 ? -1 : 1);
    }

    // 检查指定面是否有文本
    public boolean hasText(int slot) {
        if (this.isUsing) {
            return true;
        }

        ITextComponent[] targetText = (slot == 0) ? signText : backText;
        for (ITextComponent line : targetText) {
            if (line != null && !line.getUnformattedText().trim().isEmpty()) {
                return true;
            }
        }

        return false;
    }
    // 更新面
    public void upDisplayedSides() {
        boolean has0 = this.hasDisplayItems(0);
        boolean has1 = this.hasDisplayItems(1);

        if (has0 && has1) {
            this.hasDisplayedSides = 2; // 两面都有物品
        } else if (has0) {
            this.hasDisplayedSides = 0; // 仅正面（0号槽位）有物品
        } else if (has1) {
            this.hasDisplayedSides = 1; // 仅背面（1号槽位）有物品
        } else {
            this.hasDisplayedSides = -1; // 都没有
        }

        this.removeItemRenderPos(has0 ? -1 : 0);
        this.removeItemRenderPos(has1 ? -1 : 1);
    }

    // 检查是否有物品
    public boolean hasDisplayItems(int slot) {
        if (slot == 0 || slot == 1) {
            return !this.itemHandler.getStackInSlot(slot).isEmpty();
        }
        return false;
    }

    public boolean hasAny(int slot) {
        return this.hasText(slot) || this.hasDisplayItems(slot);
    }

    // 移除物品渲染位置
    public void removeItemRenderPos(int slot) {
        if (slot == 0) {
            this.textRenderPos_0 = null;
        } else if (slot == 1) {
            this.textRenderPos_1 = null;
        }
    }

    // 展示物品
    public boolean displayItems(World world, BlockPos clickPos, IBlockState state, EntityPlayer player, ItemStack heldItem, int slot) {
        if (slot == -1) return false;

        // 获取物品
        ItemStack copyItem = heldItem.copy();
        copyItem.setCount(1); // 展示一个
        // 修改物品
        this.modifyDisplayedItem(world, clickPos, slot, copyItem);

        // 减少物品栏物品
        if (!player.capabilities.isCreativeMode) {
            heldItem.shrink(1);
        }

        // 播放音效
        world.playSound(null, clickPos, DollanSounds.BLOCK_CHERRY_WOOD_PLACE,
                        SoundCategory.BLOCKS, 1.0F, 1.0F);

        return true;
    }

    // 移除展示的物品
    public boolean removeDisplayItems(World world, BlockPos clickPos, IBlockState state, EntityPlayer player, int slot) {
        if (slot == -1) return false;

        // 获取展示的物品
        ItemStack stackInSlot = this.itemHandler.getStackInSlot(slot);
        if (!stackInSlot.isEmpty()) {
            // 修改物品
            this.modifyDisplayedItem(world, clickPos, slot, ItemStack.EMPTY);

            // 给玩家物品
            player.addItemStackToInventory(stackInSlot.copy());

            return true;
        }
        return false;
    }

    // 修改物品
    public void modifyDisplayedItem(World world, BlockPos clickPos, int slot, ItemStack newStack) {
        ItemStack copy = newStack.copy();
        this.itemHandler.setStackInSlot(slot, copy);
        this.markDirty();
        this.upDisplayedSides();
        if (!world.isRemote) {
            // 服务器同步物品
            com.jhxiaochen.dollancustomized.network.NetworkManager.sendSignItemUpdateToAll(
                clickPos, slot, copy,
                world.provider.getDimension(),
                clickPos.getX(),
                clickPos.getY(),
                clickPos.getZ(),
                64
            );
        }
    }

    // 编辑文本
    public boolean editText(int slot, net.minecraft.entity.player.EntityPlayer player) {
        if (slot != 0 && slot != 1) return false;

        if (this.isWaxed(slot)) return false;

        this.setEditedSlot(slot);
        this.isUsing = true;

        if (world.isRemote) {
            player.openGui(
                com.jhxiaochen.dollancustomized.DollanCustomized.instance,
                com.jhxiaochen.dollancustomized.client.gui.GuiHandler.JACARANDA_SIGN_EDIT_GUI_ID,
                world, pos.getX(), pos.getY(), pos.getZ()
            );
            return true;
        }

        // 服务器端发送初始文本数据到客户端
        sendTextToClient((net.minecraft.entity.player.EntityPlayerMP) player, slot, this.getTextForSlot(slot), true);

        player.sendMessage(new net.minecraft.util.text.TextComponentString(
            "正在编辑" + (slot == 0 ? "正面" : "背面") + "（按住Shift+右键切换面）"));

        return true;
    }

    // 更新指定面的文本
    public void updateText(int slot, ITextComponent[] text, boolean isLocking) {
        this.isUsing = isLocking;
        if (text == null) return;

        ITextComponent[] oldText = new ITextComponent[4];
        if (slot == 0) {
            System.arraycopy(this.signText, 0, oldText, 0, 4);
        } else {
            System.arraycopy(this.backText, 0, oldText, 0, 4);
        }

        if (slot == 0) {
            System.arraycopy(text, 0, this.signText, 0, text.length);
        } else if (slot == 1) {
            System.arraycopy(text, 0, this.backText, 0, text.length);
        } else {
            return;
        }

        this.markDirty();
        this.upTextSides();
        this.removeTextRenderPos(slot);

        if (this.world != null && !this.textsEqual(oldText, text)) {
            if (this.world.isRemote) {
                this.world.markBlockRangeForRenderUpdate(pos, pos);
            } else {
                world.notifyBlockUpdate(pos, getBlockType().getDefaultState(), getBlockType().getDefaultState(), 3);
                world.markChunkDirty(pos, this);

                // 广播更新到所有客户端
                sendTextUpdateToAll(
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

    // 解锁告示牌
    public void unlock() {
        this.isUsing = false;
        this.markDirty();
    }

    // 涂蜡方法
    public boolean tryWaxed(int slot, net.minecraft.item.ItemStack heldItem) {
        if (slot != 0 && slot != 1) return false;
        if (this.isWaxed(slot)) return false;
        if (heldItem == null || heldItem.isEmpty()) return false;

        if (!this.isUsing && !this.hasText(slot)) return false;

        // 检查是否是蜡
        String registryName = heldItem.getItem().getRegistryName().toString();
        if (registryName.contains("honeycomb") || registryName.contains("slimeball") ||
            registryName.contains("beeswax") || registryName.contains("candle")) {
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
            world.notifyBlockUpdate(pos, getBlockType().getDefaultState(), getBlockType().getDefaultState(), 3);
            sendTextUpdateToAll(
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

    public boolean hasWaxedSide() {
        return this.waxed_0 || this.waxed_1;
    }

    public int getWaxedSideCount() {
        return (this.waxed_0 ? 1 : 0) + (this.waxed_1 ? 1 : 0);
    }

    // 发送文本到客户端
    private void sendTextToClient(net.minecraft.entity.player.EntityPlayerMP player, int slot, ITextComponent[] text, boolean isLocking) {
        NBTTagCompound updateTag = this.getUpdateTag();
        net.minecraft.network.play.server.SPacketUpdateTileEntity packet =
            new net.minecraft.network.play.server.SPacketUpdateTileEntity(this.pos, 1, updateTag);
        player.connection.sendPacket(packet);
    }

    // 发送文本更新到所有客户端
    private void sendTextUpdateToAll(BlockPos pos, int slot, ITextComponent[] text, boolean isLocking, int dimension, double x, double y, double z, double range) {
        com.jhxiaochen.dollancustomized.network.NetworkManager.sendSignTextUpdateToAll(
            pos, slot, text, isLocking, dimension, x, y, z, range);
    }

    // 更新包
    @Override
    public net.minecraft.network.play.server.SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound updateTag = this.getUpdateTag();
        return new net.minecraft.network.play.server.SPacketUpdateTileEntity(this.getPos(), 1, updateTag);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound tag = new NBTTagCompound();
        return writeToNBT(tag);
    }
    public NBTTagCompound createSignNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("EditSlot", this.editedSlot);
        nbt.setBoolean("IsLocking", this.isUsing);

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
        // 读取 signText 字段
        for (int i = 0; i < 4; i++) {
            if (compound.hasKey("Text" + (i + 1))) {
                this.signText[i] = ITextComponent.Serializer.jsonToComponent(compound.getString("Text" + (i + 1)));
            } else {
                this.signText[i] = new TextComponentString("");
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

        // 读取物品存储
        if (compound.hasKey("Items")) {
            this.itemHandler.deserializeNBT(compound.getCompoundTag("Items"));
        }

        this.upTextSides();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        // 保存 signText 字段
        for (int i = 0; i < 4; i++) {
            String s = ITextComponent.Serializer.componentToJson(this.signText[i]);
            compound.setString("Text" + (i + 1), s);
        }

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

        // 保存物品存储
        compound.setTag("Items", this.itemHandler.serializeNBT());

        // 保存 isEditable 和 stats 字段（使用反射）
        try {
            initReflection();
            if (isEditableField != null) {
                isEditableField.setBoolean(this, this.getIsEditable());
            }
        } catch (Exception e) {
            // 忽略错误
        }

        return compound;
    }

    @Override
    public void onDataPacket(net.minecraft.network.NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        NBTTagCompound tag = pkt.getNbtCompound();
        this.readFromNBT(tag);
    }

    // 渲染位置
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

    // 获取渲染位置
    public AxisPosition getTextRenderPosition(int slot) {
        AxisPosition renderPos = this.getTextRenderPos(slot);

        if (renderPos != null) return renderPos;

        IBlockState state = this.getWorld().getBlockState(this.getPos());
        Block block = state.getBlock();

        // 悬挂式告示牌
        if (block instanceof JacarandaHangingSign || block instanceof JacarandaHangingSignAttached) {
            // 正方向悬挂告示牌
            boolean isNorthSouth = (block instanceof JacarandaHangingSign)
                ? isNorthSouth(state.getValue(JacarandaHangingSign.FACING).getIndex())
                : isNorthSouth(state.getValue(JacarandaHangingSignAttached.ROTATION));

            if (isNorthSouth) {
                if (slot == 0) {
                    this.setTextRenderPos(slot, JACARANDA_HANGING_SIGN_ROTATION_MAP.get(0));
                } else {
                    this.setTextRenderPos(slot, JACARANDA_HANGING_SIGN_ROTATION_MAP.get(8));
                }
            } else {
                if (slot == 0) {
                    this.setTextRenderPos(slot, JACARANDA_HANGING_SIGN_ROTATION_MAP.get(12));
                } else {
                    this.setTextRenderPos(slot, JACARANDA_HANGING_SIGN_ROTATION_MAP.get(4));
                }
            }

            return this.getTextRenderPos(slot);
        }

        // 默认返回: 北方 0 槽
        if (renderPos == null) {
            renderPos = JACARANDA_HANGING_SIGN_ROTATION_MAP.get(0);
        }

        this.setTextRenderPos(slot, renderPos);
        return renderPos;
    }

    // 判断是否南北朝向
    private boolean isNorthSouth(int facing) {
        return facing == 0 || facing == 8; // 北或南
    }

    // 渲染位置映射
    private static final Map<Integer, AxisPosition> JACARANDA_HANGING_SIGN_ROTATION_MAP = Collections.unmodifiableMap(
        new HashMap<Integer, AxisPosition>() {{
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

    @Override
    public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> capability, net.minecraft.util.EnumFacing facing) {
        return capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing) {
        if (capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.itemHandler);
        }
        return super.getCapability(capability, facing);
    }
}
