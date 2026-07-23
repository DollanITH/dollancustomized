package com.jhxiaochen.dollancustomized.init;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;


public class DollanDamageSource extends DamageSource {

    public static final DollanDamageSource VALLID = new DollanDamageSource("vallid");
    public static final DollanDamageSource HENG   = new DollanDamageSource("heng");

    static {
        VALLID.setDamageAllowedInCreativeMode();
        HENG.setDamageAllowedInCreativeMode();
    }

    public DollanDamageSource(String damageTypeIn)
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
