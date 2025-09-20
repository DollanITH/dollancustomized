package com.jhxiaochen.dollancustomized.common.rail;

import com.jhxiaochen.dollancustomized.common.rail.base.RailwaySignBase;
import com.jhxiaochen.dollancustomized.model.Registry;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Registry.ModElement.Tag
public class EmptySignBlock extends Registry.ModElement  {
    @GameRegistry.ObjectHolder("dollancustomized:empty_railway_sign")
    public static Block block = null;

    public EmptySignBlock(Registry registry) {
        super(registry);
    }

    @Override
    public void initElements() {
        registry.blocks.add(() -> new EmptySignBlock.Register().setRegistryName("empty_railway_sign").setUnlocalizedName("empty_railway_sign"));
        registry.items.add(() -> new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockItemModel(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation("dollancustomized:sign_none", "inventory"));
    }


    public static class Register extends RailwaySignBase {
        @Override
        public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
            return getBoxXZ(state, 2.0 / 16.0, 10.0 / 16.0, 3.0 / 16.0);
        }
    }

}
