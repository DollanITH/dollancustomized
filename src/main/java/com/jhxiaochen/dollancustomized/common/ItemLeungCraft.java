package com.jhxiaochen.dollancustomized.common;

import com.jhxiaochen.dollancustomized.init.DollanDamageSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemLeungCraft extends ItemFood
{

    public ItemLeungCraft(int amount, float saturation, boolean isWolfFood)
    {
        super(amount, saturation, isWolfFood);
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return super.hasEffect(stack) || stack.getMetadata() > 0;
    }

    public EnumRarity getRarity(ItemStack stack)
    {
        return stack.getMetadata() == 0 ? EnumRarity.RARE : EnumRarity.EPIC;
    }

    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        player.addPotionEffect(new PotionEffect(MobEffects.POISON, 3, 1));
        player.attackEntityFrom(DollanDamageSource.VALLID, 20.5F);
    }
}
