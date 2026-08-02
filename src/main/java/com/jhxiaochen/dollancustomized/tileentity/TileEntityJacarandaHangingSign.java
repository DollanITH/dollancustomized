package com.jhxiaochen.dollancustomized.tileentity;

import com.jhxiaochen.dollancustomized.init.DollanSounds;
import com.jhxiaochen.dollancustomized.network.NetworkManager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

/**
 * 悬挂式蓝花楹木告示牌 TileEntity
 * 支持双面文本显示（正面和背面）
 */
public class TileEntityJacarandaHangingSign extends TileEntityJacarandaSign {

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

    public boolean hasWaxedSide() {
        return this.waxed_0 || this.waxed_1;
    }

    public int getWaxedSideCount() {
        return (this.waxed_0 ? 1 : 0) + (this.waxed_1 ? 1 : 0);
    }

    // 创建NBT数据（用于客户端发送到服务器）
    // slot: 要保存的槽位（0=正面，1=背面），-1 表示使用 editedSlot
    public NBTTagCompound createSignNBT(int slot) {
        NBTTagCompound nbt = new NBTTagCompound();

        // 保存当前编辑的槽位（如果没有指定，使用 editedSlot）
        int effectiveSlot = (slot != -1) ? slot : this.editedSlot;
        nbt.setInteger("EditSlot", effectiveSlot);
        nbt.setBoolean("IsLocking", this.isUsing);

        // 保存编辑槽位的文本
        ITextComponent[] currentText = this.getTextForSlot(effectiveSlot);
        for (int i = 0; i < 4; i++) {
            if (currentText[i] != null) {
                nbt.setString("Text" + (i + 1), ITextComponent.Serializer.componentToJson(currentText[i]));
            }
        }

        // 同时保存另一面的文本（防止刷新后消失）
        int otherSlot = (effectiveSlot == 0) ? 1 : 0;
        ITextComponent[] otherText = this.getTextForSlot(otherSlot);
        NBTTagCompound backTextTag = new NBTTagCompound();
        for (int i = 0; i < 4; i++) {
            if (otherText[i] != null) {
                backTextTag.setString("Line" + (i + 1), ITextComponent.Serializer.componentToJson(otherText[i]));
            }
        }
        nbt.setTag("BackText", backTextTag);

        return nbt;
    }

    // 重载方法：默认使用 editedSlot
    public NBTTagCompound createSignNBT() {
        return createSignNBT(-1);
    }

    // 存储 & 读取
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        // 读取物品存储
        if (compound.hasKey("Items")) {
            this.itemHandler.deserializeNBT(compound.getCompoundTag("Items"));
        }
        this.upDisplayedSides();  // 修复：添加调用以更新显示的物品面
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        // 保存物品存储
        compound.setTag("Items", this.itemHandler.serializeNBT());

        return compound;
    }

}
