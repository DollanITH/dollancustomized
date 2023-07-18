package com.jhxiaochen.dollancustomized.init;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;


public class DollanCraftDamageSource extends DamageSource {

    public static final DollanCraftDamageSource VALLID = new DollanCraftDamageSource("vallid");
    public static final DollanCraftDamageSource HENG = new DollanCraftDamageSource("heng");

    static {
        VALLID.setDamageBypassesArmor();
        HENG.setDamageAllowedInCreativeMode();
    }

    public DollanCraftDamageSource(String damageTypeIn)
    {
        super(damageTypeIn);
        this.damageType = damageTypeIn;
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn)
    {
        EntityLivingBase entitylivingbase = entityLivingBaseIn.getAttackingEntity();
        String s = "death.dollancraft." + this.damageType;
        String s1 = s + ".player";
        return entitylivingbase != null && I18n.canTranslate(s1) ? new TextComponentTranslation(s1, new Object[] {entityLivingBaseIn.getDisplayName(), entitylivingbase.getDisplayName()}) : new TextComponentTranslation(s, new Object[] {entityLivingBaseIn.getDisplayName()});
    }

}
