package com.jhxiaochen.dollancustomized.common.rail;

import com.jhxiaochen.dollancustomized.common.item.ItemRailwaySign;
import com.jhxiaochen.dollancustomized.common.rail.base.TwoBlocksSignBase;
import com.jhxiaochen.dollancustomized.model.Registry;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Registry.ModElement.Tag
public class StationEdgeSign extends Registry.ModElement {
    @GameRegistry.ObjectHolder("dollancustomized:station_edge_sign")
    public static Block block = null;

    public StationEdgeSign(Registry registry) {
        super(registry);
    }

    @Override
    public void initElements() {
        registry.blocks.add(() -> new StationEdgeSign.Register().setRegistryName("station_edge_sign").setUnlocalizedName("StationEdgeSign"));
        registry.items.add(() -> new ItemRailwaySign(block).setRegistryName(block.getRegistryName()));
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockItemModel(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation("dollancustomized:station_edge_sign", "inventory"));
    }

    public static class Register extends TwoBlocksSignBase {
        @Override
        public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
            return state.getValue(HALF) == EnumSignHalf.LOWER ? getBoxXZ(state, 2.0 / 16.0, 1,  14.0 / 16.0) : getBoxXZ(state, 2.0 / 16.0, 6.0 / 16.0, 14.0/16.0);
        }
    }




}
