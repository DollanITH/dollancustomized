package com.jhxiaochen.dollancustomized.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class Command114514 extends CommandBase
{
    public String getName()
    {
        return "114514";
    }

    public int getRequiredPermissionLevel()
    {
        return 0;
    }

    public String getUsage(ICommandSender sender)
    {
        return "commands.114514.usage";
    }

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length == 0)
        {
            EntityPlayer entityplayer = getCommandSenderAsPlayer(sender);
            entityplayer.addPotionEffect(new PotionEffect(MobEffects.POISON, 100, 1));
            entityplayer.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 50, 1));
            notifyCommandListener(sender, this, "commands.114514.successful", new Object[] {entityplayer.getDisplayName()});
        }
        else
        {
            EntityPlayer entityplayer = getCommandSenderAsPlayer(sender);
            entityplayer.onKillCommand();
            Entity entity = getEntity(server, sender, args[0]);
            notifyCommandListener(sender, this, "commands.114514.fail", new Object[] {entity.getDisplayName()});
        }
    }

    public boolean isUsernameIndex(String[] args, int index)
    {
        return index == 0;
    }

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.emptyList();
    }
}
