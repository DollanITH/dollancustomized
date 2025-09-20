package com.jhxiaochen.dollancustomized.common.rail;

import com.jhxiaochen.dollancustomized.common.rail.base.TwoBlocksSignBase;
import com.jhxiaochen.dollancustomized.init.DollanBlocks;
import com.jhxiaochen.dollancustomized.model.Registry;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Registry.ModElement.Tag
public class PowerMethodChangeSign extends Registry.ModElement {
    public PowerMethodChangeSign(Registry registry) {
        super(registry);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockItemModel(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(DollanBlocks.power_method_change_sign), 0, new ModelResourceLocation("dollancustomized:power_method_change_sign", "inventory"));
    }

    public static class Register extends TwoBlocksSignBase {

    }

}
