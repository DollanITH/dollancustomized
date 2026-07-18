package com.jhxiaochen.dollancustomized.tileentity;

import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityJacarandaSign extends TileEntity {
    public final ITextComponent[] signText = new ITextComponent[] {new TextComponentString(""), new TextComponentString(""), new TextComponentString(""), new TextComponentString("")};
    public int lineBeingEdited = -1;
    private boolean isEditable = true;
    private EntityPlayer player;
    private final CommandResultStats stats = new CommandResultStats();

    // 【核心修复1】解除 64 格距离限制
    @Override
    public double getMaxRenderDistanceSquared() {
        return 102400D;
    }

    // 【核心修复2】不要返回 INFINITE_EXTENT_AABB！
    // 返回一个紧贴方块的 1x1x1 包围盒，引擎会认为它永远在视野内，不会裁剪边缘导致闪烁
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(pos, pos.add(1, 1, 1));
    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return pass == 0; // 确保只在主通道渲染，防止透明度叠加闪烁
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        for (int i = 0; i < 4; ++i) {
            String s = ITextComponent.Serializer.componentToJson(this.signText[i]);
            compound.setString("Text" + (i + 1), s);
        }

        this.stats.writeStatsToNBT(compound);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        this.isEditable = false;
        super.readFromNBT(compound);
        ICommandSender icommandsender = new ICommandSender() {
            public String getName()
            {
                return "Sign";
            }
            public boolean canUseCommand(int permLevel, String commandName)
            {
                return permLevel <= 2; //Forge: Fixes  MC-75630 - Exploit with signs and command blocks
            }
            public BlockPos getPosition()
            {
                return TileEntityJacarandaSign.this.pos;
            }
            public Vec3d getPositionVector()
            {
                return new Vec3d((double)TileEntityJacarandaSign.this.pos.getX() + 0.5D, (double)TileEntityJacarandaSign.this.pos.getY() + 0.5D, (double)TileEntityJacarandaSign.this.pos.getZ() + 0.5D);
            }
            public World getEntityWorld()
            {
                return TileEntityJacarandaSign.this.world;
            }
            public MinecraftServer getServer()
            {
                return TileEntityJacarandaSign.this.world.getMinecraftServer();
            }
        };

        for (int i = 0; i < 4; ++i) {
            String s = compound.getString("Text" + (i + 1));
            ITextComponent itextcomponent = ITextComponent.Serializer.jsonToComponent(s);

            try
            {
                this.signText[i] = TextComponentUtils.processComponent(icommandsender, itextcomponent, (EntityPlayer)null);
            }
            catch (CommandException var7)
            {
                this.signText[i] = itextcomponent;
            }
        }

        this.stats.readStatsFromNBT(compound);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        this.readFromNBT(tag);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, 0, this.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
    }

    /**
     * 设置可编辑状态
     */
    @SideOnly(Side.CLIENT)
    public void setEditable(boolean isEditableIn)
    {
        this.isEditable = isEditableIn;

        if (!isEditableIn)
        {
            this.player = null;
        }
    }

    /**
     * 设置玩家
     */
    public void setPlayer(EntityPlayer playerIn)
    {
        this.player = playerIn;
    }

    /**
     * 获取玩家
     */
    public EntityPlayer getPlayer()
    {
        return this.player;
    }

    /**
     * 执行命令（与原版告示牌相同的命令执行逻辑）
     */
    public boolean executeCommand(EntityPlayer playerIn)
    {
        ICommandSender icommandsender = new ICommandSender()
        {
            public String getName()
            {
                return playerIn.getName();
            }
            public ITextComponent getDisplayName()
            {
                return playerIn.getDisplayName();
            }
            public void sendMessage(ITextComponent component)
            {
            }
            public boolean canUseCommand(int permLevel, String commandName)
            {
                return permLevel <= 2;
            }
            public BlockPos getPosition()
            {
                return TileEntityJacarandaSign.this.pos;
            }
            public Vec3d getPositionVector()
            {
                return new Vec3d((double)TileEntityJacarandaSign.this.pos.getX() + 0.5D, (double)TileEntityJacarandaSign.this.pos.getY() + 0.5D, (double)TileEntityJacarandaSign.this.pos.getZ() + 0.5D);
            }
            public World getEntityWorld()
            {
                return playerIn.getEntityWorld();
            }
            public EntityPlayer getCommandSenderEntity()
            {
                return playerIn;
            }
            public boolean sendCommandFeedback()
            {
                return false;
            }
            public void setCommandStat(CommandResultStats.Type type, int amount)
            {
                if (TileEntityJacarandaSign.this.world != null && !TileEntityJacarandaSign.this.world.isRemote)
                {
                    TileEntityJacarandaSign.this.stats.setCommandStatForSender(TileEntityJacarandaSign.this.world.getMinecraftServer(), this, type, amount);
                }
            }
            public MinecraftServer getServer()
            {
                return playerIn.getServer();
            }
        };

        for (ITextComponent itextcomponent : this.signText)
        {
            Style style = itextcomponent == null ? null : itextcomponent.getStyle();

            if (style != null && style.getClickEvent() != null)
            {
                ClickEvent clickevent = style.getClickEvent();

                if (clickevent.getAction() == ClickEvent.Action.RUN_COMMAND)
                {
                    playerIn.getServer().getCommandManager().executeCommand(icommandsender, clickevent.getValue());
                }
            }
        }

        return true;
    }

    /**
     * 获取命令统计信息
     */
    public CommandResultStats getStats()
    {
        return this.stats;
    }
}