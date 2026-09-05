package com.jhxiaochen.dollancustomized.mixin;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityIronGolem.class)
public class MixinEntityIronGolem extends EntityCreature implements IAnimals {
    public MixinEntityIronGolem(World worldIn) {
        super(worldIn);
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return DollanCustomized.createEgg("villager_golem");
    }
}
