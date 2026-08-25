package com.jhxiaochen.dollancustomized.fixes;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class ItemDataFixable implements IFixableData {
    @Override
    public int getFixVersion() {
        return 1340;
    }

    @Override
    public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
        String oldName = compound.getString("id").replace("minecraft:", "");
        String remappedName = Remaper.getRemappedTileName(oldName);
            if (remappedName != null) {
                compound.setString("id", remappedName);
                }
        return compound;
    }
}
