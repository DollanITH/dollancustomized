package com.jhxiaochen.dollancustomized.common;

import com.jhxiaochen.dollancustomized.init.DollanCraftDamageSource;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDollanCraft extends ItemFood
{

    public ItemDollanCraft(int amount, float saturation, boolean isWolfFood)
    {
        super(amount, saturation, isWolfFood);
        this.setHasSubtypes(true);
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return super.hasEffect(stack) || stack.getMetadata() > 0;
    }

    public EnumRarity getRarity(ItemStack stack)
    {
        return stack.getMetadata() == 0 ? EnumRarity.EPIC : EnumRarity.RARE;
    }

    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        if (!worldIn.isRemote) {
            if (stack.getMetadata() > 0) {
                player.attackEntityFrom(DollanCraftDamageSource.VALLID, 28.5F);
                player.addPotionEffect(new PotionEffect(MobEffects.UNLUCK, 114514, 1));
            }
        }
        else
        {
            player.addPotionEffect(new PotionEffect(MobEffects.POISON, 3, 1));
            player.addPotionEffect(new PotionEffect(MobEffects.LUCK, 200, 114));
        }
    }

    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
            items.add(new ItemStack(this));
            items.add(new ItemStack(this, 1, 1));
        }
    }
}
