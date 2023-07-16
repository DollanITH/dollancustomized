package com.jhxiaochen.dollancustomized.init;

import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.common.*;
import com.jhxiaochen.dollancustomized.common.road.BlockAsphalt;
import com.jhxiaochen.dollancustomized.common.road.BlockAsphaltSlab;
import com.jhxiaochen.dollancustomized.common.road.RoadMergeOtherLine;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.*;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedList;
import java.util.List;

public class DollanItems{
        public static Item leungcraft;
        public static Item dollancraft;
        public static final ItemBlock beautiful_marble = withRegistryName(new
            ItemMultiTexture(DollanBlocks.beautiful_marble, DollanBlocks.beautiful_marble,
            new ItemMultiTexture.Mapper()
            {
                public String apply(ItemStack p_apply_1_) {
                    return BlockMarble.MarbleType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
                }
            }));


    private static ItemBlock withRegistryName(ItemBlock item)
    {
        item.setRegistryName(item.getBlock().getRegistryName());
        return item;
    }

    public static void init() {
        leungcraft = new ItemLeungCraft(3,1.2F,false)
                .setAlwaysEdible()
                .setUnlocalizedName("leungcraft").setRegistryName("leungcraft");
        dollancraft = new ItemDollanCraft(3,1.2F,false)
                .setAlwaysEdible()
                .setUnlocalizedName("dollancraft").setRegistryName("dollancraft");
        }

        public static void register()
        {
            registerItem(leungcraft);
            registerItem(dollancraft);
            registerItemBlock(DollanBlocks.white_flat_lamp);
            registerItemBlock(DollanBlocks.white_punctiform_lamp);
            registerItemBlock(DollanBlocks.white_banding_lamp);
            registerItemBlock(DollanBlocks.lamp_tran, new ItemMultiTexture(DollanBlocks.lamp_tran, DollanBlocks.lamp_tran, new ItemMultiTexture.Mapper() {
                        public String apply(ItemStack p_apply_1_) {
                            return EnumDyeColor.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
                        }
                    })
            );
            registerItemBlock(DollanBlocks.light, new ItemMultiTexture(DollanBlocks.light, DollanBlocks.light, new ItemMultiTexture.Mapper() {
                        public String apply(ItemStack p_apply_1_) {
                            return EnumDyeColor.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
                        }
                    })
            );
            registerItemBlock(DollanBlocks.road_lamp_1_bottom);
            registerItemBlock(DollanBlocks.road_lamp_1_up);

            registerItemBlock(DollanBlocks.platform_fence);
            registerItemBlock(DollanBlocks.metro_fence, new ItemMultiTexture(DollanBlocks.metro_fence,DollanBlocks.metro_fence, new ItemMultiTexture.Mapper() {
                        public String apply(ItemStack p_apply_1_) {
                            return BlockMetroFence.FenceType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
                        }
                    })
            );

            registerItemBlock(DollanBlocks.arrow_diaotou);
            registerItemBlock(DollanBlocks.arrow_left_diaotou);
            registerItemBlock(DollanBlocks.arrow_left_right);
            registerItemBlock(DollanBlocks.arrow_left_right_staright);
            registerItemBlock(DollanBlocks.arrow_left_staright);
            registerItemBlock(DollanBlocks.arrow_right_staright);
            registerItemBlock(DollanBlocks.arrow_left);
            registerItemBlock(DollanBlocks.arrow_right);
            registerItemBlock(DollanBlocks.arrow_staright);
            registerItemBlock(DollanBlocks.bike_words);
            registerItemBlock(DollanBlocks.bus_lane_mark);
            registerItemBlock(DollanBlocks.road_traffic_light);
            registerItemBlock(DollanBlocks.road_traffic_light_red);
            registerItemBlock(DollanBlocks.road_traffic_light_green);
            registerItemBlock(DollanBlocks.road_traffic_light_yellow);
            registerItemBlock(DollanBlocks.road_pedestrian_traffic_light);
            registerItemBlock(DollanBlocks.road_sign_post);

            registerItemBlock(DollanBlocks.double_yellow_line_diagonal_slab);
            registerItemBlock(DollanBlocks.double_yellow_line_diagonal);
            registerItemBlock(DollanBlocks.double_white_line_diagonal_slab);
            registerItemBlock(DollanBlocks.double_white_line_diagonal);
            registerItemBlock(DollanBlocks.asphalt, new ItemMultiTexture(DollanBlocks.asphalt,DollanBlocks.asphalt, new ItemMultiTexture.Mapper() {
                        public String apply(ItemStack p_apply_1_) {
                            return BlockAsphalt.AsphaltType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
                        }
                    })
            );
            registerItemBlock(DollanBlocks.asphalt_slab, new ItemMultiTexture(DollanBlocks.asphalt_slab,DollanBlocks.asphalt_slab, new ItemMultiTexture.Mapper() {
                        public String apply(ItemStack p_apply_1_) {
                            return BlockAsphaltSlab.AsphaltType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
                        }
                    })
            );

            registerItemBlock(DollanBlocks.single_yellow_line);
            registerItemBlock(DollanBlocks.single_yellow_line_slab);
            registerItemBlock(DollanBlocks.single_white_line);
            registerItemBlock(DollanBlocks.single_white_line_slab);
            registerItemBlock(DollanBlocks.single_white_line_45
            );
            registerItemBlock(DollanBlocks.single_white_line_slab_45);
            registerItemBlock(DollanBlocks.single_yellow_line_45);
            registerItemBlock(DollanBlocks.single_yellow_line_slab_45);
            registerItemBlock(DollanBlocks.single_white_line_thick);
            registerItemBlock(DollanBlocks.single_white_line_thick_slab);
            registerItemBlock(DollanBlocks.single_yellow_line_thick);
            registerItemBlock(DollanBlocks.single_yellow_line_thick_slab);

            registerItemBlock(DollanBlocks.double_white_line);
            registerItemBlock(DollanBlocks.double_white_line_slab);
            registerItemBlock(DollanBlocks.double_yellow_line);
            registerItemBlock(DollanBlocks.double_yellow_line_slab);

            registerItemBlock(DollanBlocks.double_white_line_half);
            registerItemBlock(DollanBlocks.double_white_line_half_slab);
            registerItemBlock(DollanBlocks.double_yellow_line_half);
            registerItemBlock(DollanBlocks.double_yellow_line_half_slab);

            registerItemBlock(DollanBlocks.crosswalk_slab);
            registerItemBlock(DollanBlocks.crosswalk);
            registerItemBlock(DollanBlocks.stop_line_slab);
            registerItemBlock(DollanBlocks.stop_line);
            registerItemBlock(DollanBlocks.stop_line_left_slab);
            registerItemBlock(DollanBlocks.stop_line_left);
            registerItemBlock(DollanBlocks.stop_line_right_slab);
            registerItemBlock(DollanBlocks.stop_line_right_1_slab);
            registerItemBlock(DollanBlocks.stop_line_right);
            registerItemBlock(DollanBlocks.stop_line_right_1);

            registerItemBlock(DollanBlocks.asphalt_cyan_black_half_line);
            registerItemBlock(DollanBlocks.asphalt_cyan_black_half_line_slab);
            registerItemBlock(DollanBlocks.asphalt_cyan_black_half);
            registerItemBlock(DollanBlocks.asphalt_cyan_black_half_slab);
            registerItemBlock(DollanBlocks.asphalt_red_black_half_slab);
            registerItemBlock(DollanBlocks.asphalt_red_black_half);
            registerItemBlock(DollanBlocks.asphalt_red_black_half_line);
            registerItemBlock(DollanBlocks.asphalt_red_black_half_line_slab);
            registerItemBlock(DollanBlocks.asphalt_cyan_black_half_line_diagonal);
            registerItemBlock(DollanBlocks.asphalt_cyan_black_half_line_diagonal_slab);
            registerItemBlock(DollanBlocks.asphalt_red_black_half_line_diagonal_slab);
            registerItemBlock(DollanBlocks.asphalt_red_black_half_line_diagonal);
            registerItemBlock(DollanBlocks.asphalt_cyan_black_half_line_diagonal_1);
            registerItemBlock(DollanBlocks.asphalt_cyan_black_half_line_diagonal_1_slab);
            registerItemBlock(DollanBlocks.asphalt_red_black_half_line_diagonal_1_slab);
            registerItemBlock(DollanBlocks.asphalt_red_black_half_line_diagonal_1);

            registerItemBlock(DollanBlocks.road_excl_zone_corner_out);
            registerItemBlock(DollanBlocks.road_excl_zone_corner_out_slab);
            registerItemBlock(DollanBlocks.road_excl_zone_corner_in);
            registerItemBlock(DollanBlocks.road_excl_zone_corner_in_slab);

            registerItemBlock(DollanBlocks.road_excl_zone_diagonal_out);
            registerItemBlock(DollanBlocks.road_excl_zone_diagonal_out_slab);
            registerItemBlock(DollanBlocks.road_excl_zone_diagonal_in);
            registerItemBlock(DollanBlocks.road_excl_zone_diagonal_in_slab);
            registerItemBlock(DollanBlocks.road_excl_zone);
            registerItemBlock(DollanBlocks.road_excl_zone_slab);
            registerItemBlock(DollanBlocks.road_excl_zone_line);
            registerItemBlock(DollanBlocks.road_excl_zone_line_slab);
            registerItemBlock(DollanBlocks.road_excl_zone_split_our_l);
            registerItemBlock(DollanBlocks.road_excl_zone_split_our_l_slab);
            registerItemBlock(DollanBlocks.road_excl_zone_split_our_r);
            registerItemBlock(DollanBlocks.road_excl_zone_split_our_r_slab);
            registerItemBlock(DollanBlocks.road_excl_zone_split_in_l);
            registerItemBlock(DollanBlocks.road_excl_zone_split_in_l_slab);
            registerItemBlock(DollanBlocks.road_excl_zone_split_in_r);
            registerItemBlock(DollanBlocks.road_excl_zone_split_in_r_slab);

            registerItemBlock(DollanBlocks.dollan_airport_line);
            registerItemBlock(DollanBlocks.dollan_s1);
            registerItemBlock(DollanBlocks.dollan_s2);

            registerItemBlock(DollanBlocks.dollan_line_1);
            registerItemBlock(DollanBlocks.dollan_line_2);
            registerItemBlock(DollanBlocks.dollan_line_3);
            registerItemBlock(DollanBlocks.dollan_line_4);
            registerItemBlock(DollanBlocks.dollan_line_5);
            registerItemBlock(DollanBlocks.dollan_line_6);
            registerItemBlock(DollanBlocks.dollan_line_7);
            registerItemBlock(DollanBlocks.dollan_line_8);
            registerItemBlock(DollanBlocks.dollan_line_9);
            registerItemBlock(DollanBlocks.dollan_line_10);
            registerItemBlock(DollanBlocks.dollan_line_11);
            registerItemBlock(DollanBlocks.dollan_line_12);
            registerItemBlock(DollanBlocks.dollan_line_13);
            registerItemBlock(DollanBlocks.dollan_line_14);
            registerItemBlock(DollanBlocks.dollan_line_15);
            registerItemBlock(DollanBlocks.dollan_line_16);

            registerItemBlock(DollanBlocks.mingsha_S1);
            registerItemBlock(DollanBlocks.mingsha_S2);
            registerItemBlock(DollanBlocks.mingsha_S3);
            registerItemBlock(DollanBlocks.mingsha_S4);
            registerItemBlock(DollanBlocks.mingsha_S5);
            registerItemBlock(DollanBlocks.mingsha_S6);

            registerItemBlock(DollanBlocks.road_sign_go_right);
            registerItemBlock(DollanBlocks.road_sign_go_left);
            registerItemBlock(DollanBlocks.road_sign_go_both);
            registerItemBlock(DollanBlocks.road_sign_can_left);
            registerItemBlock(DollanBlocks.road_sign_can_right);
            registerItemBlock(DollanBlocks.road_sign_can_diaotou);
            registerItemBlock(DollanBlocks.road_sign_do_not_enter);
            registerItemBlock(DollanBlocks.road_sign_do_not_enter_1);
            registerItemBlock(DollanBlocks.road_sign_do_not_left);
            registerItemBlock(DollanBlocks.road_sign_do_not_right);
            registerItemBlock(DollanBlocks.road_sign_do_not_diaotou);
            registerItemBlock(DollanBlocks.road_sign_sl_15);
            registerItemBlock(DollanBlocks.road_sign_sl_20);
            registerItemBlock(DollanBlocks.road_sign_sl_30);
            registerItemBlock(DollanBlocks.road_sign_sl_40);
            registerItemBlock(DollanBlocks.road_sign_sl_50);
            registerItemBlock(DollanBlocks.road_sign_sl_60);
            registerItemBlock(DollanBlocks.road_sign_sl_70);
            registerItemBlock(DollanBlocks.road_sign_sl_80);
            registerItemBlock(DollanBlocks.road_sign_sl_90);
            registerItemBlock(DollanBlocks.road_sign_sl_100);
            registerItemBlock(DollanBlocks.road_sign_sl_110);
            registerItemBlock(DollanBlocks.road_sign_sl_120);

            registerItemBlock(DollanBlocks.road_sign_triangle_rang);
            registerItemBlock(DollanBlocks.road_sign_triangle_slow);
            registerItemBlock(DollanBlocks.road_sign_triangle_warn);
            registerItemBlock(DollanBlocks.road_sign_triangle_shizi);
            registerItemBlock(DollanBlocks.road_sign_triangle_t);
            registerItemBlock(DollanBlocks.road_sign_triangle_t1);
            registerItemBlock(DollanBlocks.road_sign_triangle_t2);
            registerItemBlock(DollanBlocks.road_sign_triangle_y);
            //2.1
            registerItemBlock(DollanBlocks.road_sign_dont_stop);
            registerItemBlock(DollanBlocks.road_sign_dont_stop_1);
            registerItemBlock(DollanBlocks.road_sign_highway_start);
            registerItemBlock(DollanBlocks.road_sign_highway_end);
            registerItemBlock(DollanBlocks.road_sign_park);
            registerItemBlock(DollanBlocks.road_sign_park_1);


            //2.1
            registerItemBlock(DollanBlocks.asphalt_red_black_half_45);
            registerItemBlock(DollanBlocks.asphalt_red_black_half_45_slab);
            registerItemBlock(DollanBlocks.asphalt_cyan_black_half_45);
            registerItemBlock(DollanBlocks.asphalt_cyan_black_half_45_slab);

            registerItemBlock(DollanBlocks.single_white_90);
            registerItemBlock(DollanBlocks.single_white_90_1);
            registerItemBlock(DollanBlocks.single_white_all_line);
            registerItemBlock(DollanBlocks.single_white_90_slab);
            registerItemBlock(DollanBlocks.single_white_90_1_slab);
            registerItemBlock(DollanBlocks.single_white_all_line_slab);
            registerItemBlock(DollanBlocks.single_yellow_90);
            registerItemBlock(DollanBlocks.single_yellow_90_1);
            registerItemBlock(DollanBlocks.single_yellow_all_line);
            registerItemBlock(DollanBlocks.single_yellow_90_slab);
            registerItemBlock(DollanBlocks.single_yellow_90_1_slab);
            registerItemBlock(DollanBlocks.single_yellow_all_line_slab);

            registerItemBlock(DollanBlocks.thick_white_line_45
            );
            registerItemBlock(DollanBlocks.thick_white_line_slab_45);
            registerItemBlock(DollanBlocks.thick_yellow_line_45);
            registerItemBlock(DollanBlocks.thick_yellow_line_slab_45);

            registerItemBlock(DollanBlocks.ms_lines, new ItemMultiTexture(DollanBlocks.ms_lines,DollanBlocks.ms_lines, new ItemMultiTexture.Mapper() {
                        public String apply(ItemStack p_apply_1_) {
                            return RoadMergeOtherLine.EnumVariant.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
                        }
                    })
            );

            registerItemBlock(DollanBlocks.ms_lines_slab, new ItemMultiTexture(DollanBlocks.ms_lines_slab,DollanBlocks.ms_lines_slab, new ItemMultiTexture.Mapper() {
                        public String apply(ItemStack p_apply_1_) {
                            return RoadMergeOtherLine.EnumVariant.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
                        }
                    })
            );
            registerItemBlock(DollanBlocks.crosswalk_diagonal);
            registerItemBlock(DollanBlocks.crosswalk_diagonal_slab);

            registerItemBlock(DollanBlocks.arrow_staright_diaotou);
            registerItemBlock(DollanBlocks.platform_wall);
            registerItemBlock(DollanBlocks.marble_block);

            registerItemBlock(DollanBlocks.road_sign_one_way_straight);
            registerItemBlock(DollanBlocks.road_sign_left_warn);
            registerItemBlock(DollanBlocks.road_sign_right_warn);
            registerItemBlock(DollanBlocks.road_sign_left_right_warn);

            registerItemBlock(DollanBlocks.arrow_confluence_left);
            registerItemBlock(DollanBlocks.arrow_confluence_right);
            registerItemBlock(DollanBlocks.arrow_diaotou_sl);
            registerItemBlock(DollanBlocks.arrow_left_diaotou_sl);
            registerItemBlock(DollanBlocks.arrow_left_right_sl);
            registerItemBlock(DollanBlocks.arrow_left_right_staright_sl);
            registerItemBlock(DollanBlocks.arrow_left_staright_sl);
            registerItemBlock(DollanBlocks.arrow_right_staright_sl);
            registerItemBlock(DollanBlocks.arrow_left_sl);
            registerItemBlock(DollanBlocks.arrow_right_sl);
            registerItemBlock(DollanBlocks.arrow_staright_sl);
            registerItemBlock(DollanBlocks.arrow_staright_diaotou_sl);
            registerItemBlock(DollanBlocks.arrow_confluence_left_sl);
            registerItemBlock(DollanBlocks.arrow_confluence_right_sl);
           /* registerItemBlock(DollanBlocks.beautiful_marble,
                new ItemMultiTexture(DollanBlocks.beautiful_marble, DollanBlocks.beautiful_marble, new ItemMultiTexture.Mapper() {
                    public String apply(ItemStack p_apply_1_) {
                        return BlockMarble.MarbleType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
                    }
                })
        );*/
            registerItem(beautiful_marble);
            registerItemBlock(DollanBlocks.cherry_planks);
            registerItemBlock(DollanBlocks.calcite);
            registerItemBlock(DollanBlocks.cracked_deepslate, new ItemMultiTexture(DollanBlocks.cracked_deepslate,DollanBlocks.cracked_deepslate, new ItemMultiTexture.Mapper() {
                        public String apply(ItemStack p_apply_1_) {
                            return BlockCrackedDeepSlate.DeepslateType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
                        }
                    })
            );
            registerItemBlock(DollanBlocks.deepslate, new ItemMultiTexture(DollanBlocks.deepslate,DollanBlocks.deepslate, new ItemMultiTexture.Mapper() {
                        public String apply(ItemStack p_apply_1_) {
                            return BlockDeepSlate.DeepslateType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
                        }
                    })
            );
            registerItemBlock(DollanBlocks.elder_prismarine, new ItemMultiTexture(DollanBlocks.elder_prismarine,DollanBlocks.elder_prismarine, new ItemMultiTexture.Mapper() {
                        public String apply(ItemStack p_apply_1_) {
                            return BlockElderPrismarine.PrismarineType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
                        }
                    })
            );
            registerItemBlock(DollanBlocks.floorblock);
            registerItemBlock(DollanBlocks.gray_porcelain_tile);
            registerItemBlock(DollanBlocks.gray_wall);
            registerItemBlock(DollanBlocks.graymud);
            registerItemBlock(DollanBlocks.graystoneblock);
            registerItemBlock(DollanBlocks.limestone, new ItemMultiTexture(DollanBlocks.limestone,DollanBlocks.limestone, new ItemMultiTexture.Mapper() {
                        public String apply(ItemStack p_apply_1_) {
                            return BlockLimeStone.LimeStoneType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
                        }
                    })
            );
            registerItemBlock(DollanBlocks.mangrove_planks);
            registerItemBlock(DollanBlocks.metal);
            registerItemBlock(DollanBlocks.mud_bricks);
            registerItemBlock(DollanBlocks.packed_mud);
            registerItemBlock(DollanBlocks.myalite, new ItemMultiTexture(DollanBlocks.myalite,DollanBlocks.myalite, new ItemMultiTexture.Mapper() {
                        public String apply(ItemStack p_apply_1_) {
                            return BlockMyalite.MyaliteType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
                        }
                    })
            );

        }

        public static void registerItem(Item item)
        {
            RegistrationHandler.ITEMS.add(item);
        }

    private static void registerItemBlock(Block blockIn)
    {
        registerItemBlock(blockIn, new ItemBlock(blockIn));
    }

    private static void registerItemBlock(net.minecraft.block.Block block, ItemBlock item) {
        item.setRegistryName(block.getRegistryName());
        DollanItems.RegistrationHandler.ITEMS.add(item);
    }

        public static void registerModels() {
            registerModel(leungcraft);
            registerModel(dollancraft,0,"dollancraft");
            registerModel(dollancraft,1,"dollancraft");
            registerModel(beautiful_marble,0,"normal_marble");
            registerModel(beautiful_marble,1,"bricks_marble");
            registerModel(beautiful_marble,2,"cut_marble");
            registerModel(beautiful_marble,3,"litcut_marble");
            registerModel(beautiful_marble,4,"smooth_marble");


        }


    @SideOnly(Side.CLIENT)
    private static void registerModel(Item item)
    {
        ModelResourceLocation modelResourceLocation = new ModelResourceLocation(item.getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(item, 0, modelResourceLocation);
    }

    @SideOnly(Side.CLIENT)
    private static void registerModel(Item item, int metadata, String name) {
            ModelResourceLocation model = new ModelResourceLocation(Reference.MOD_ID + ":" + name, "inventory");
            ModelLoader.setCustomModelResourceLocation(item, metadata, model);
        }


@Mod.EventBusSubscriber()
public static class RegistrationHandler {
    public static final List<Item> ITEMS = new LinkedList<>();

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        DollanItems.init();
        DollanItems.register();
        ITEMS.stream().forEach(item -> event.getRegistry().register(item));
    }
}
}