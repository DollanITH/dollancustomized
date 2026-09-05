package com.jhxiaochen.dollancustomized.mixin;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntitySnowman.class)
public class MixinEntitySnowman extends EntityCreature implements IAnimals {
    public MixinEntitySnowman(World worldIn) {
        super(worldIn);
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return DollanCustomized.createEgg("snowman");
    }
}
