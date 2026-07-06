package com.jhxiaochen.dollancustomized.command;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;

import javax.annotation.Nullable;
import java.util.*;

public class CommandExtendedFill extends CommandBase implements ICommand {

    // 撤销数据结构
    public static class UndoData {
        public final BlockPos pos;
        public final Block block;
        public final int meta;
        public final NBTTagCompound nbt;

        public UndoData(BlockPos pos, Block block, int meta, NBTTagCompound nbt) {
            this.pos = pos;
            this.block = block;
            this.meta = meta;
            this.nbt = nbt;
        }

        public NBTTagCompound writeToNBT() {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setLong("Pos", this.pos.toLong());
            tag.setString("Block", this.block.getRegistryName().toString());
            tag.setInteger("Meta", this.meta);
            if (this.nbt != null) {
                tag.setTag("NBT", this.nbt);
            }
            return tag;
        }

        public static UndoData readFromNBT(NBTTagCompound tag) {
            BlockPos pos = BlockPos.fromLong(tag.getLong("Pos"));
            Block block = Block.getBlockFromName(tag.getString("Block"));
            if (block == null) block = Blocks.AIR; // 防止模组被卸载导致方块不存在而崩溃
            int meta = tag.getInteger("Meta");
            NBTTagCompound nbt = tag.hasKey("NBT") ? tag.getCompoundTag("NBT") : null;
            return new UndoData(pos, block, meta, nbt);
        }
    }

    // 持久化的撤销栈存储
    public static class UndoStorage extends WorldSavedData {
        private static final String DATA_NAME = "extfill_undo_data";
        private final Map<UUID, Deque<List<UndoData>>> playerUndoMap = new HashMap<>();

        public UndoStorage(String name) {
            super(name);
        }

        public static UndoStorage get(MinecraftServer server) {
            // 修复：显式传入 Class 类型，使得泛型能正确推导并返回 UndoStorage
            UndoStorage data = (UndoStorage) server.getEntityWorld().getMapStorage().getOrLoadData(UndoStorage.class, DATA_NAME);
            if (data == null) {
                data = new UndoStorage(DATA_NAME);
                server.getEntityWorld().getMapStorage().setData(DATA_NAME, data);
            }
            return data;
        }

        public Deque<List<UndoData>> getStack(UUID uuid) {
            return playerUndoMap.computeIfAbsent(uuid, k -> new ArrayDeque<>());
        }

        @Override
        public NBTTagCompound writeToNBT(NBTTagCompound compound) {
            NBTTagList playerList = new NBTTagList();
            for (Map.Entry<UUID, Deque<List<UndoData>>> entry : playerUndoMap.entrySet()) {
                NBTTagCompound playerTag = new NBTTagCompound();
                playerTag.setUniqueId("UUID", entry.getKey());

                NBTTagList historyList = new NBTTagList();
                for (List<UndoData> history : entry.getValue()) {
                    NBTTagList actionList = new NBTTagList();
                    for (UndoData data : history) {
                        actionList.appendTag(data.writeToNBT());
                    }
                    NBTTagCompound actionTag = new NBTTagCompound();
                    actionTag.setTag("Data", actionList);
                    historyList.appendTag(actionTag);
                }
                playerTag.setTag("History", historyList);
                playerList.appendTag(playerTag);
            }
            compound.setTag("Players", playerList);
            return compound;
        }

        @Override
        public void readFromNBT(NBTTagCompound nbt) {
            playerUndoMap.clear();
            NBTTagList playerList = nbt.getTagList("Players", 10);
            for (int i = 0; i < playerList.tagCount(); i++) {
                NBTTagCompound playerTag = playerList.getCompoundTagAt(i);
                UUID uuid = playerTag.getUniqueId("UUID");
                NBTTagList historyList = playerTag.getTagList("History", 10);
                Deque<List<UndoData>> stack = new ArrayDeque<>();
                for (int j = 0; j < historyList.tagCount(); j++) {
                    NBTTagList actionList = historyList.getCompoundTagAt(j).getTagList("Data", 10);
                    List<UndoData> history = new ArrayList<>();
                    for (int k = 0; k < actionList.tagCount(); k++) {
                        history.add(UndoData.readFromNBT(actionList.getCompoundTagAt(k)));
                    }
                    stack.push(history); // 注意反序列化顺序
                }
                playerUndoMap.put(uuid, stack);
            }
        }
    }

    /**
     * Gets the name of the command
     */
    public String getName() {
        return "extfill";
    }

    public int getRequiredPermissionLevel() {
        return 4;
    }

    public String getUsage(ICommandSender sender) {
        return "commands.extfill.usage";
    }

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length > 0 && "undo".equalsIgnoreCase(args[0])) {
            if (!(sender instanceof EntityPlayer)) {
                throw new CommandException("commands.extfill.undo.playerOnly", new Object[0]);
            }
            EntityPlayer player = (EntityPlayer) sender;

            // 确保撤销操作在主线程执行，避免多线程区块加载冲突
            server.addScheduledTask(() -> {
                UndoStorage storage = UndoStorage.get(server);
                Deque<List<UndoData>> stack = storage.getStack(player.getUniqueID());

                if (stack == null || stack.isEmpty()) {
                    try {
                        throw new CommandException("commands.extfill.undo.nothing", new Object[0]);
                    } catch (CommandException e) {
                        notifyCommandListener(sender, this, e.getMessage(), e.getErrorObjects());
                    }
                    return;
                }

                List<UndoData> data = stack.pop();
                storage.markDirty(); // 标记数据已更改以保存到磁盘

                World world = player.getEntityWorld();
                List<BlockPos> updatedPositions = new ArrayList<>();

                // 第一步：快速恢复方块状态，使用 flags=18 (不更新邻居，不触发渲染光照立刻更新，防止卡顿)
                for (UndoData d : data) {
                    // 数据冲突校验：如果当前方块状态不是我们记录的中间状态，说明被其他玩家改过，跳过避免覆盖
                    IBlockState currentState = world.getBlockState(d.pos);
                    if (currentState.getBlock() != d.block || currentState.getBlock().getMetaFromState(currentState) != d.meta) {
                        // 如果已经被其他人改动，跳过此方块的安全回滚
                        continue;
                    }

                    IBlockState restoreState = d.block.getStateFromMeta(d.meta);
                    world.setBlockState(d.pos, restoreState, 18);
                    updatedPositions.add(d.pos);

                    // 恢复 NBT 数据
                    if (d.nbt != null) {
                        TileEntity te = world.getTileEntity(d.pos);
                        if (te != null) {
                            te.readFromNBT(d.nbt);
                        }
                    }
                }

                // 第二步：统一对周围环境进行邻居更新，大幅提高性能
                for (BlockPos pos : updatedPositions) {
                    world.notifyNeighborsOfStateChange(pos, world.getBlockState(pos).getBlock(), true);
                }

                notifyCommandListener(sender, this, "commands.extfill.undo.success", new Object[] {updatedPositions.size()});
            });
            return;
        }

        if (args.length < 7) {
            throw new WrongUsageException("commands.extfill.usage", new Object[0]);
        } else {
            sender.setCommandStat(net.minecraft.command.CommandResultStats.Type.AFFECTED_BLOCKS, 0);
            BlockPos blockpos = parseBlockPos(sender, args, 0, false);
            BlockPos blockpos1 = parseBlockPos(sender, args, 3, false);
            Block block = CommandBase.getBlockByText(sender, args[6]);
            IBlockState iblockstate;

            if (args.length >= 8) {
                iblockstate = convertArgToBlockState(block, args[7]);
            } else {
                iblockstate = block.getDefaultState();
            }

            BlockPos blockpos2 = new BlockPos(Math.min(blockpos.getX(), blockpos1.getX()), Math.min(blockpos.getY(), blockpos1.getY()), Math.min(blockpos.getZ(), blockpos1.getZ()));
            BlockPos blockpos3 = new BlockPos(Math.max(blockpos.getX(), blockpos1.getX()), Math.max(blockpos.getY(), blockpos1.getY()), Math.max(blockpos.getZ(), blockpos1.getZ()));
            int i = (blockpos3.getX() - blockpos2.getX() + 1) * (blockpos3.getY() - blockpos2.getY() + 1) * (blockpos3.getZ() - blockpos2.getZ() + 1);

            if (i > 252625114) {
                throw new CommandException("commands.fill.tooManyBlocks", new Object[] {i, Integer.valueOf(252625114)});
            }
            World world = sender.getEntityWorld();

            if ((!world.isOutsideBuildHeight(blockpos2)) && (!world.isOutsideBuildHeight(blockpos3))) {
                for (int j = blockpos2.getZ(); j <= blockpos3.getZ(); j += 16) {
                    for (int k = blockpos2.getX(); k <= blockpos3.getX(); k += 16) {
                        if (!world.isBlockLoaded(new BlockPos(k, blockpos3.getY() - blockpos2.getY(), j))) {
                            throw new CommandException("commands.fill.outOfWorld", new Object[0]);
                        }
                    }
                }

                NBTTagCompound nbttagcompound = new NBTTagCompound();
                boolean flag = false;
                if (args.length >= 10 && block.hasTileEntity(iblockstate)) {
                    String s = buildString(args, 9);
                    try {
                        nbttagcompound = JsonToNBT.getTagFromJson(s);
                        flag = true;
                    } catch (NBTException nbtexception) {
                        throw new CommandException("commands.fill.tagError", new Object[] {nbtexception.getMessage()});
                    }
                }

                List<BlockPos> list = Lists.<BlockPos>newArrayList();
                List<UndoData> undoList = new ArrayList<>();

                i = 0;
                for (int l = blockpos2.getZ(); l <= blockpos3.getZ(); ++l) {
                    for (int i1 = blockpos2.getY(); i1 <= blockpos3.getY(); ++i1) {
                        for (int j1 = blockpos2.getX(); j1 <= blockpos3.getX(); ++j1) {
                            BlockPos blockpos4 = new BlockPos(j1, i1, l);
                            if (args.length >= 9) {
                                if (!"outline".equals(args[8]) && !"hollow".equals(args[8])) {
                                    if ("destroy".equals(args[8])) {
                                        if (sender instanceof EntityPlayer) recordUndoData(world, blockpos4, undoList);
                                        world.destroyBlock(blockpos4, true);
                                    } else if ("keep".equals(args[8])) {
                                        if (!world.isAirBlock(blockpos4)) {
                                            continue;
                                        }
                                    } else if ("replace".equals(args[8]) && !block.hasTileEntity(iblockstate) && args.length > 9) {
                                        Block block1 = CommandBase.getBlockByText(sender, args[9]);
                                        if (world.getBlockState(blockpos4).getBlock() != block1 || args.length > 10 && !"-1".equals(args[10]) && !"*".equals(args[10]) && !CommandBase.convertArgToBlockStatePredicate(block1, args[10]).apply(world.getBlockState(blockpos4))) {
                                            continue;
                                        }
                                    }
                                } else if (j1 != blockpos2.getX() && j1 != blockpos3.getX() && i1 != blockpos2.getY() && i1 != blockpos3.getY() && l != blockpos2.getZ() && l != blockpos3.getZ()) {
                                    if ("hollow".equals(args[8])) {
                                        if (sender instanceof EntityPlayer) recordUndoData(world, blockpos4, undoList);
                                        world.setBlockState(blockpos4, Blocks.AIR.getDefaultState(), 2);
                                        list.add(blockpos4);
                                    }
                                    continue;
                                }
                            }
                            TileEntity tileentity1 = world.getTileEntity(blockpos4);
                            if (tileentity1 != null && tileentity1 instanceof IInventory) {
                                ((IInventory)tileentity1).clear();
                            }

                            if (sender instanceof EntityPlayer) {
                                recordUndoData(world, blockpos4, undoList);
                            }

                            if (world.setBlockState(blockpos4, iblockstate, 2)) {
                                list.add(blockpos4);
                                ++i;
                                if (flag) {
                                    TileEntity tileentity = world.getTileEntity(blockpos4);
                                    if (tileentity != null) {
                                        nbttagcompound.setInteger("x", blockpos4.getX());
                                        nbttagcompound.setInteger("y", blockpos4.getY());
                                        nbttagcompound.setInteger("z", blockpos4.getZ());
                                        tileentity.readFromNBT(nbttagcompound);
                                    }
                                }
                            }
                        }
                    }
                }

                if (sender instanceof EntityPlayer && !undoList.isEmpty()) {
                    UndoStorage storage = UndoStorage.get(server);
                    storage.getStack(((EntityPlayer) sender).getUniqueID()).push(undoList);
                    storage.markDirty();
                }

                for (BlockPos blockpos5 : list) {
                    Block block2 = world.getBlockState(blockpos5).getBlock();
                    world.notifyNeighborsRespectDebug(blockpos5, block2, false);
                }

                if (i <= 0) {
                    throw new CommandException("commands.fill.failed", new Object[0]);
                } else {
                    sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, i);
                    notifyCommandListener(sender, this, "commands.fill.success", new Object[] {i});
                }
            } else {
                throw new CommandException("commands.fill.outOfWorld", new Object[0]);
            }
        }
    }

    private void recordUndoData(World world, BlockPos pos, List<UndoData> undoList) {
        IBlockState oldState = world.getBlockState(pos);
        Block oldBlock = oldState.getBlock();
        int meta = oldBlock.getMetaFromState(oldState);
        NBTTagCompound oldNbt = null;
        TileEntity te = world.getTileEntity(pos);
        if (te != null) {
            oldNbt = te.writeToNBT(new NBTTagCompound());
        }
        undoList.add(new UndoData(pos.toImmutable(), oldBlock, meta, oldNbt));
    }

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, new String[] {"undo"});
        } else if (args.length > 1 && args.length <= 4) {
            return getTabCompletionCoordinate(args, 1, targetPos);
        } else if (args.length > 4 && args.length <= 7) {
            return getTabCompletionCoordinate(args, 4, targetPos);
        } else if (args.length == 8) {
            return getListOfStringsMatchingLastWord(args, Block.REGISTRY.getKeys());
        } else if (args.length == 10) {
            return getListOfStringsMatchingLastWord(args, new String[] {"replace", "destroy", "keep", "hollow", "outline"});
        } else {
            return args.length == 11 && "replace".equals(args[9]) ? getListOfStringsMatchingLastWord(args, Block.REGISTRY.getKeys()) : Collections.emptyList();
        }
    }
}
