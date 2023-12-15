package com.jhxiaochen.common.command;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandException;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.KillCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Command114514{
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder) CommandManager.literal("114514").requires(source -> source.hasPermissionLevel(4))).executes(context -> Command114514.execute((ServerCommandSource)context.getSource(), ImmutableList.of(((ServerCommandSource)context.getSource()).getEntityOrThrow())))).then(CommandManager.argument("targets", EntityArgumentType.entities()).executes(context -> Command114514.execute((ServerCommandSource)context.getSource(), EntityArgumentType.getEntities(context, "targets")))));
    }
    private static int execute(ServerCommandSource source, Collection<? extends Entity> targets) {
        if (targets.size() == 1) {
            source.sendFeedback(() -> Text.translatable("commands.114514.successful", ((Entity)targets.iterator().next()).getDisplayName()), false);
        } else {
            for (Entity entity : targets) {
                entity.kill();
            }
            source.sendFeedback(() -> Text.translatable("commands.114514.fail", targets.size()), false);
        }
        return targets.size();
    }

}
