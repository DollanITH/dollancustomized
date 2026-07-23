package com.jhxiaochen.dollancustomized.tileentity;

import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.world.World;

public class TileEntityJacarandaSign extends TileEntitySign {
    // 告示牌文本字段继承自父类，无需重复定义

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        for (int i = 0; i < 4; ++i) {
            String s = ITextComponent.Serializer.componentToJson(this.signText[i]);
            compound.setString("Text" + (i + 1), s);
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

        for (int i = 0; i < 4; ++i) {
            String s = compound.getString("Text" + (i + 1));
            ITextComponent itextcomponent = ITextComponent.Serializer.jsonToComponent(s);

            try {
                this.signText[i] = TextComponentUtils.processComponent(icommandsender, itextcomponent, null);
            } catch (CommandException var7) {
                this.signText[i] = itextcomponent;
            }
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
    }
}