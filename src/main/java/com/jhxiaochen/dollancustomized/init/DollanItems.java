package com.jhxiaochen.dollancustomized.init;

import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.common.*;
import com.jhxiaochen.dollancustomized.common.item.*;
import com.jhxiaochen.dollancustomized.common.item.ItemArrow;
import com.jhxiaochen.dollancustomized.common.road.*;
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
        public static Item dollan;

    public static void init() {
        leungcraft = new ItemLeungCraft(5,1.2F,false)
                .setAlwaysEdible()
                .setUnlocalizedName("leungcraft").setRegistryName("leungcraft");
        dollan = new ItemDollanCraft(5, 1.2F, false)
                .setAlwaysEdible()
                .setUnlocalizedName("dollancraft").setRegistryName("dollan");
        }

        public static void register()
        {
            registerItemBlock(DollanBlocks.station_end_door, new ItemStationEndDoor(DollanBlocks.station_end_door));
            registerItemBlock(DollanBlocks.dollan_line_18);
            registerItem(leungcraft);
            registerItem(dollan);
            registerItemBlock(DollanBlocks.power_method_change_sign, new ItemRailwaySign(DollanBlocks.power_method_change_sign));
            registerItemBlock(DollanBlocks.white_flat_lamp);
            registerItemBlock(DollanBlocks.white_punctiform_lamp);
            registerItemBlock(DollanBlocks.white_banding_lamp);
            registerItemBlock(DollanBlocks.lamp_tran, new ItemMultiTexture(DollanBlocks.lamp_tran, DollanBlocks.lamp_tran, p_apply_1_ -> EnumDyeColor.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName())
            );
            registerItemBlock(DollanBlocks.light, new ItemMultiTexture(DollanBlocks.light, DollanBlocks.light, p_apply_1_ -> EnumDyeColor.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName())
            );
            registerItemBlock(DollanBlocks.road_lamp_1_bottom);
            registerItemBlock(DollanBlocks.road_lamp_1_up);

            registerItemBlock(DollanBlocks.platform_fence);
            registerItemBlock(DollanBlocks.metro_fence, new ItemMultiTexture(DollanBlocks.metro_fence, DollanBlocks.metro_fence, p_apply_1_ -> BlockMetroFence.FenceType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName())
            );

            registerItemBlock(DollanBlocks.road_give_way_mark, new ItemArrow(DollanBlocks.road_give_way_mark));
            registerItemBlock(DollanBlocks.road_slow_down_mark, new ItemArrow(DollanBlocks.road_slow_down_mark));
            registerItemBlock(DollanBlocks.arrow_diaotou, new ItemArrow(DollanBlocks.arrow_diaotou));
            registerItemBlock(DollanBlocks.arrow_left_diaotou, new ItemArrow(DollanBlocks.arrow_left_diaotou));
            registerItemBlock(DollanBlocks.arrow_left_right, new ItemArrow(DollanBlocks.arrow_left_right));
            registerItemBlock(DollanBlocks.arrow_left_right_straight, new ItemArrow(DollanBlocks.arrow_left_right_straight));
            registerItemBlock(DollanBlocks.arrow_left_straight, new ItemArrow(DollanBlocks.arrow_left_straight));
            registerItemBlock(DollanBlocks.arrow_right_straight, new ItemArrow(DollanBlocks.arrow_right_straight));
            registerItemBlock(DollanBlocks.arrow_left, new ItemArrow(DollanBlocks.arrow_left));
            registerItemBlock(DollanBlocks.arrow_right, new ItemArrow(DollanBlocks.arrow_right));
            registerItemBlock(DollanBlocks.arrow_straight, new ItemArrow(DollanBlocks.arrow_straight));
            registerItemBlock(DollanBlocks.bike_words, new ItemArrow(DollanBlocks.bike_words));
            registerItemBlock(DollanBlocks.bus_lane_mark, new ItemBusLaneMark());
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
            registerItemBlock(DollanBlocks.asphalt, new ItemMultiTexture(DollanBlocks.asphalt, DollanBlocks.asphalt, p_apply_1_ -> BlockAsphalt.AsphaltType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName())
            );
            registerItemBlock(DollanBlocks.asphalt_slab, new ItemMultiTexture(DollanBlocks.asphalt_slab, DollanBlocks.asphalt_slab, p_apply_1_ -> BlockAsphaltSlab.AsphaltType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName())
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
            registerItemBlock(DollanBlocks.stop_line_slab, new ItemMultiTexture(DollanBlocks.stop_line_slab, DollanBlocks.stop_line_slab, p_apply_1_ -> StopLineBlockSlab.EnumVarient.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName())
            );
            registerItemBlock(DollanBlocks.stop_line, new ItemMultiTexture(DollanBlocks.stop_line, DollanBlocks.stop_line, p_apply_1_ -> StopLineBlock.EnumVarient.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName())
            );
            registerItemBlock(DollanBlocks.stop_line_cross, new ItemMultiTexture(DollanBlocks.stop_line_cross, DollanBlocks.stop_line_cross, p_apply_1_ -> DiagonalStopLineBlock.EnumVarient.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName())
            );
            registerItemBlock(DollanBlocks.stop_line_cross_slab, new ItemMultiTexture(DollanBlocks.stop_line_cross_slab, DollanBlocks.stop_line_cross_slab, p_apply_1_ -> DiagonalStopLineBlockSlab.EnumVarient.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName())
            );
            registerItemBlock(DollanBlocks.stop_line_left_slab);
            registerItemBlock(DollanBlocks.stop_line_left);
            registerItemBlock(DollanBlocks.stop_line_right_slab);
            registerItemBlock(DollanBlocks.stop_line_right_1_slab);
            registerItemBlock(DollanBlocks.stop_line_right);
            registerItemBlock(DollanBlocks.stop_line_right_1);
            registerItemBlock(DollanBlocks.railway_sign_post);
            registerItemBlock(DollanBlocks.station_stop_sign, new ItemStationStopSign());

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

            registerItemBlock(DollanBlocks.ms_lines, new ItemMultiTexture(DollanBlocks.ms_lines, DollanBlocks.ms_lines, p_apply_1_ -> RoadMergeOtherLine.EnumVariant.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName())
            );

            registerItemBlock(DollanBlocks.ms_lines_slab, new ItemMultiTexture(DollanBlocks.ms_lines_slab, DollanBlocks.ms_lines_slab, p_apply_1_ -> RoadMergeOtherLine.EnumVariant.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName())
            );
            registerItemBlock(DollanBlocks.crosswalk_diagonal);
            registerItemBlock(DollanBlocks.crosswalk_diagonal_slab);

            registerItemBlock(DollanBlocks.arrow_straight_diaotou, new ItemArrow(DollanBlocks.arrow_straight_diaotou));
            registerItemBlock(DollanBlocks.platform_wall);
            registerItemBlock(DollanBlocks.marble_block);

            registerItemBlock(DollanBlocks.road_sign_one_way_straight);
            registerItemBlock(DollanBlocks.road_sign_left_warn);
            registerItemBlock(DollanBlocks.road_sign_right_warn);
            registerItemBlock(DollanBlocks.road_sign_left_right_warn);

            registerItemBlock(DollanBlocks.arrow_confluence_left, new ItemArrow(DollanBlocks.arrow_confluence_left));
            registerItemBlock(DollanBlocks.arrow_confluence_right, new ItemArrow(DollanBlocks.arrow_confluence_right));
            //registerItemBlock(DollanBlocks.arrow_diaotou_sl);
            //registerItemBlock(DollanBlocks.arrow_left_diaotou_sl);
            //registerItemBlock(DollanBlocks.arrow_left_right_sl);
            //registerItemBlock(DollanBlocks.arrow_left_right_staright_sl);
            //registerItemBlock(DollanBlocks.arrow_left_staright_sl);
            //registerItemBlock(DollanBlocks.arrow_right_staright_sl);
            //registerItemBlock(DollanBlocks.arrow_left_sl);
            //registerItemBlock(DollanBlocks.arrow_right_sl);
            //registerItemBlock(DollanBlocks.arrow_staright_sl);
            //registerItemBlock(DollanBlocks.arrow_staright_diaotou_sl);
            //registerItemBlock(DollanBlocks.arrow_confluence_left_sl);
            //registerItemBlock(DollanBlocks.arrow_confluence_right_sl);
            registerItemBlock(DollanBlocks.dollan_loop_line);
            registerItemBlock(DollanBlocks.dollan_line_17);
            registerItemBlock(DollanBlocks.dollan_line_19);
            registerItemBlock(DollanBlocks.dollan_line_22);
            registerItemBlock(DollanBlocks.beautiful_marble,new
                    ItemMultiTexture(DollanBlocks.beautiful_marble, DollanBlocks.beautiful_marble,
                                     p_apply_1_ -> BlockMarble.MarbleType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName()));
            //registerItemBlock(DollanBlocks.cherry_planks);
            registerItemBlock(DollanBlocks.dollan_line_21);
            registerItemBlock(DollanBlocks.calcite);
            registerItemBlock(DollanBlocks.cracked_deepslate, new ItemMultiTexture(DollanBlocks.cracked_deepslate, DollanBlocks.cracked_deepslate, p_apply_1_ -> BlockCrackedDeepSlate.DeepslateType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName())
            );
            registerItemBlock(DollanBlocks.deepslate, new ItemMultiTexture(DollanBlocks.deepslate, DollanBlocks.deepslate, p_apply_1_ -> BlockDeepSlate.DeepslateType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName())
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
            //registerItemBlock(DollanBlocks.myalite, new ItemMultiTexture(DollanBlocks.myalite, DollanBlocks.myalite, p_apply_1_ -> BlockMyalite.MyaliteType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName()));
            registerItemBlock(DollanBlocks.myalite);
            registerItemBlock(DollanBlocks.myalite_crystal);
            registerItemBlock(DollanBlocks.myalite_pavement);
            registerItemBlock(DollanBlocks.polished_blackstone,
                    new ItemMultiTexture(DollanBlocks.polished_blackstone,DollanBlocks.polished_blackstone, new ItemMultiTexture.Mapper() {
                        public String apply(ItemStack p_apply_1_) {
                            return BlockPolishedBlackStone.BlackStoneType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
                        }
                    })
            );
            registerItemBlock(DollanBlocks.polished_limestone);
            registerItemBlock(DollanBlocks.polished_marble);
            registerItemBlock(DollanBlocks.polished_deepslate);
            registerItemBlock(DollanBlocks.polished_myalite);
            registerItemBlock(DollanBlocks.quartz_bricks);
            registerItemBlock(DollanBlocks.rock_1);
            registerItemBlock(DollanBlocks.rock_2);
            registerItemBlock(DollanBlocks.sandy_bricks);
            registerItemBlock(DollanBlocks.teacherblock);
            registerItemBlock(DollanBlocks.white_color_purple_block);
            registerItemBlock(DollanBlocks.elder_sea_lantern);
            registerItemBlock(DollanBlocks.modern_stone_tile);
            registerItemBlock(DollanBlocks.dollan_line_20);
            registerItemBlock(DollanBlocks.froglight,
                    new ItemMultiTexture(DollanBlocks.froglight,DollanBlocks.froglight, new ItemMultiTexture.Mapper() {
                        public String apply(ItemStack p_apply_1_) {
                            return BlockFrogLight.FrogLightType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
                        }
                    })
            );
            registerItemBlock(DollanBlocks.marble_tile,
                    new ItemMultiTexture(DollanBlocks.marble_tile,DollanBlocks.marble_tile, new ItemMultiTexture.Mapper() {
                        public String apply(ItemStack p_apply_1_) {
                            return EnumDyeColor.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
                        }
                    })
            );

            registerItemBlock(DollanBlocks.overhead_lines);
            registerItemBlock(DollanBlocks.overhead_lines_slope);
            registerItemBlock(DollanBlocks.overhead_lines_slope_1);
            registerItemBlock(DollanBlocks.overhead_lines_slope_2);
            registerItemBlock(DollanBlocks.overhead_lines_diagonal);
            registerItemBlock(DollanBlocks.overhead_lines_straighter);
            registerItemBlock(DollanBlocks.overhead_lines_diagonal_straighter);
            registerItemBlock(DollanBlocks.overhead_lines_support);
            registerItemBlock(DollanBlocks.overhead_lines_support_a1);
            registerItemBlock(DollanBlocks.overhead_lines_support_a2);
            registerItemBlock(DollanBlocks.overhead_lines_support_a3);
            registerItemBlock(DollanBlocks.overhead_lines_support_a4);
            registerItemBlock(DollanBlocks.overhead_lines_double_diagonal);
            registerItemBlock(DollanBlocks.overhead_lines_double_diagonal_1);
            registerItemBlock(DollanBlocks.overhead_lines_double_wye);

            registerItemBlock(DollanBlocks.metro_tran0);
            registerItemBlock(DollanBlocks.metro_tran1);
            registerItemBlock(DollanBlocks.metro_tran2);
            registerItemBlock(DollanBlocks.metro_tran3);
            registerItemBlock(DollanBlocks.metro_tran4);
            registerItemBlock(DollanBlocks.metro_tran5);
            registerItemBlock(DollanBlocks.metro_tran6);
            registerItemBlock(DollanBlocks.metro_tran7);

            registerItemBlock(DollanBlocks.overhead_steel);
            registerItemBlock(DollanBlocks.overhead_steel_slope);
            registerItemBlock(DollanBlocks.overhead_steel_diagonal);
            registerItemBlock(DollanBlocks.overhead_steel_support);
            registerItemBlock(DollanBlocks.overhead_steel_diagonal_support, new ItemOverheadSteelDiagonalSupport(DollanBlocks.overhead_steel_diagonal_support));
            registerItemBlock(DollanBlocks.overhead_steel_diagonal_support_1, new ItemOverheadSteelDiagonalSupport(DollanBlocks.overhead_steel_diagonal_support_1));
            registerItemBlock(DollanBlocks.overhead_steel_support_1);
            registerItemBlock(DollanBlocks.overhead_steel_double_diagonal);
            registerItemBlock(DollanBlocks.overhead_steel_double_diagonal_1);
            registerItemBlock(DollanBlocks.overhead_steel_double_wye);
            registerItemBlock(DollanBlocks.overhead_steel_double_wye_1);
            registerItemBlock(DollanBlocks.steel_double);
            registerItemBlock(DollanBlocks.steel_double_support);

            registerItemBlock(DollanBlocks.overhead_post);
            registerItemBlock(DollanBlocks.overhead_post_1);
            registerItemBlock(DollanBlocks.overhead_steel_post_up);
            registerItemBlock(DollanBlocks.overhead_web_corner);
            registerItemBlock(DollanBlocks.overhead_web_pillar);
            registerItemBlock(DollanBlocks.overhead_web_pillar_post);
            registerItemBlock(DollanBlocks.overhead_web_pillar_post_1);
            registerItemBlock(DollanBlocks.overhead_web_pillar_post_2);

            registerItemBlock(DollanBlocks.signal_post);
            registerItemBlock(DollanBlocks.signal_none_one_light);
            registerItemBlock(DollanBlocks.signal_red_one_light);
            registerItemBlock(DollanBlocks.signal_blue_one_light);
            registerItemBlock(DollanBlocks.signal_green_one_light);
            registerItemBlock(DollanBlocks.signal_white_one_light);

            registerItemBlock(DollanBlocks.signal_none_two_lights);
            registerItemBlock(DollanBlocks.signal_red_two_lights);
            registerItemBlock(DollanBlocks.signal_blue_two_lights);
            registerItemBlock(DollanBlocks.signal_green_two_lights);
            registerItemBlock(DollanBlocks.signal_white_two_lights);
            registerItemBlock(DollanBlocks.signal_yellow_two_lights);

            registerItemBlock(DollanBlocks.signal_none_three_lights);
            registerItemBlock(DollanBlocks.signal_red_three_lights);
            registerItemBlock(DollanBlocks.signal_blue_three_lights);
            registerItemBlock(DollanBlocks.signal_green_three_lights);
            registerItemBlock(DollanBlocks.signal_white_three_lights);
            registerItemBlock(DollanBlocks.signal_yellow_three_lights);

            registerItemBlock(DollanBlocks.signal_red_four_lights);
            registerItemBlock(DollanBlocks.signal_blue_four_lights);
            registerItemBlock(DollanBlocks.signal_green_four_lights);
            registerItemBlock(DollanBlocks.signal_yellow_four_lights);

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
            registerModel(dollan, 0, "dollancraft");
            registerModel(dollan, 1, "dollancraft");

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