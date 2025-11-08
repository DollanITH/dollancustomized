package com.jhxiaochen.dollancustomized.init;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.common.*;
import com.jhxiaochen.dollancustomized.common.metro.*;
import com.jhxiaochen.dollancustomized.common.rail.PowerMethodChangeSign;
import com.jhxiaochen.dollancustomized.common.rail.RailwaySignPost;
import com.jhxiaochen.dollancustomized.common.rail.StationStopSign;
import com.jhxiaochen.dollancustomized.common.road.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedList;
import java.util.List;

public class DollanBlocks {

public static Block crosswalk_diagonal = new BlockAsphaltHorizontal("crosswalk_diagonal");
public static Block crosswalk_diagonal_slab = new BlockAsphaltSlabHorizontal("crosswalk_diagonal_slab");
public static Block ms_lines = new RoadMergeOtherLine("ms_lines");
public static Block ms_lines_slab = new RoadMergeOtherLineSlab("ms_lines_slab");
public static Block road_sign_dont_stop = new BlockRoadSign("road_sign_dont_stop");
public static Block road_sign_left_warn = new BlockRoadSign("road_sign_left_warn");
public static Block road_sign_left_right_warn = new BlockRoadSign("road_sign_left_right_warn");
public static Block road_sign_right_warn = new BlockRoadSign("road_sign_right_warn");
public static Block road_sign_one_way_straight = new BlockRoadSign("road_sign_one_way_straight");
public static Block road_sign_dont_stop_1 = new BlockRoadSign("road_sign_dont_stop_1");
public static Block road_sign_highway_start = new BlockRoadSign("road_sign_highway_start");
public static Block road_sign_highway_end = new BlockRoadSign("road_sign_highway_end");
public static Block road_sign_park = new BlockRoadSign("road_sign_park");
public static Block road_sign_park_1 = new BlockRoadSign("road_sign_park_1");
public static Block single_white_90 = new BlockAsphaltHorizontal("single_white_90");
public static Block single_white_90_1 = new BlockAsphaltHorizontal("single_white_90_1");
public static Block single_yellow_90 = new BlockAsphaltHorizontal("single_yellow_90");
public static Block single_yellow_90_1 = new BlockAsphaltHorizontal("single_yellow_90_1");
public static Block single_white_90_slab = new BlockAsphaltSlabHorizontal("single_white_90_slab");
public static Block single_white_90_1_slab = new BlockAsphaltSlabHorizontal("single_white_90_1_slab");
public static Block single_yellow_90_slab = new BlockAsphaltSlabHorizontal("single_yellow_90_slab");
public static Block single_yellow_90_1_slab = new BlockAsphaltSlabHorizontal("single_yellow_90_1_slab");
public static Block single_white_all_line = new BlockAsphalt1("single_white_all_line");
public static Block single_yellow_all_line = new BlockAsphalt1("single_yellow_all_line");
public static Block single_white_all_line_slab = new BlockAsphaltSlab1("single_white_all_line_slab");
public static Block single_yellow_all_line_slab = new BlockAsphaltSlab1("single_yellow_all_line_slab");
public static Block road_sign_triangle_rang = new BlockRoadSign("road_sign_triangle_rang");
public static Block road_sign_triangle_slow = new BlockRoadSign("road_sign_triangle_slow");
public static Block road_sign_triangle_t1 = new BlockRoadSign("road_sign_triangle_t1");
public static Block road_sign_triangle_t = new BlockRoadSign("road_sign_triangle_t");
public static Block road_sign_triangle_t2 = new BlockRoadSign("road_sign_triangle_t2");
public static Block road_sign_triangle_y = new BlockRoadSign("road_sign_triangle_y");
public static Block road_sign_triangle_warn = new BlockRoadSign("road_sign_triangle_warn");
public static Block road_sign_triangle_shizi = new BlockRoadSign("road_sign_triangle_shizi");
public static Block bus_lane_mark = new BusLaneMark( );
public static Block bus_lane_mark_slab = new BusLaneMarkSlab( );
public static Block road_sign_do_not_enter = new BlockRoadSign("road_sign_do_not_enter");
public static Block road_sign_do_not_enter_1 = new BlockRoadSign("road_sign_do_not_enter_1");
public static Block road_sign_do_not_left = new BlockRoadSign("road_sign_do_not_left");
public static Block road_sign_do_not_right = new BlockRoadSign("road_sign_do_not_right");
public static Block road_sign_go_left = new BlockRoadSign("road_sign_go_left");
public static Block road_sign_go_right = new BlockRoadSign("road_sign_go_right");
public static Block road_sign_go_both = new BlockRoadSign("road_sign_go_both");
public static Block road_sign_do_not_diaotou = new BlockRoadSign("road_sign_do_not_diaotou");
public static Block road_sign_can_left = new BlockRoadSign("road_sign_can_left");
public static Block road_sign_can_right = new BlockRoadSign("road_sign_can_right");
public static Block road_sign_can_diaotou = new BlockRoadSign("road_sign_can_diaotou");
public static Block road_sign_post = new BlockRoadSignPost();
public static Block white_flat_lamp = new LightLamp("white_flat_lamp");
public static Block white_punctiform_lamp = new PunLamp("white_punctiform_lamp");
public static Block white_banding_lamp = new BandLamp("white_banding_lamp");
public static Block dollan_s2 = new BlockMetroBlock("dollan_s2").setCreativeTab(DollanCustomized.DOLLAN_TRAN_TAB);
public static Block dollan_s1 = new BlockMetroBlock("dollan_s1").setCreativeTab(DollanCustomized.DOLLAN_TRAN_TAB);
public static Block dollan_airport_line = new BlockMetroBlock("dollan_airport_line").setCreativeTab(DollanCustomized.DOLLAN_TRAN_TAB);
public static Block dollan_line_1 = new BlockMetroBlock("dollan_line_1");
public static Block dollan_line_2 = new BlockMetroBlock("dollan_line_2");
public static Block dollan_line_3 = new BlockMetroBlock("dollan_line_3");
public static Block dollan_line_4 = new BlockMetroBlock("dollan_line_4");
public static Block dollan_line_5 = new BlockMetroBlock("dollan_line_5");
public static Block dollan_line_6 = new BlockMetroBlock("dollan_line_6");
public static Block dollan_line_7 = new BlockMetroBlock("dollan_line_7");
public static Block dollan_line_8 = new BlockMetroBlock("dollan_line_8");
public static Block dollan_line_9 = new BlockMetroBlock("dollan_line_9");
public static Block dollan_line_10 = new BlockMetroBlock("dollan_line_10");
public static Block dollan_line_11 = new BlockMetroBlock("dollan_line_11");
public static Block dollan_line_12 = new BlockMetroBlock("dollan_line_12");
public static Block dollan_line_13 = new BlockMetroBlock("dollan_line_13");
public static Block dollan_line_14 = new BlockMetroBlock("dollan_line_14");
public static Block dollan_line_15 = new BlockMetroBlock("dollan_line_15");
public static Block dollan_line_16 = new BlockMetroBlock("dollan_line_16");
public static Block dollan_line_21 = new BlockMetroBlock("dollan_line_21");
public static Block dollan_line_22 = new BlockMetroBlock("dollan_line_22");
public static Block dollan_loop_line = new BlockMetroBlock("dollan_loop_line").setCreativeTab(DollanCustomized.DOLLAN_TRAN_TAB);
public static Block mingsha_S1 = new BlockMSMetroBlock("mingsha_S1");
public static Block mingsha_S2 = new BlockMSMetroBlock("mingsha_S2");
public static Block mingsha_S3 = new BlockMSMetroBlock("mingsha_S3");
public static Block mingsha_S4 = new BlockMSMetroBlock("mingsha_S4");
public static Block mingsha_S5 = new BlockMSMetroBlock("mingsha_S5");
public static Block mingsha_S6 = new BlockMSMetroBlock("mingsha_S6");
public static Block lamp_tran = new BlockTranLight("lamp_tran").setRegistryName("lamp_tran");
public static Block light = new BlockLight("light");
public static Block asphalt_slab = new BlockAsphaltSlab("asphalt_slab");
public static Block asphalt = new BlockAsphalt("asphalt");
public static Block bike_words = new ArrowNew("bike_words");
public static Block road_slow_down_mark = new ArrowNew("road_slow_down_mark");
public static Block road_give_way_mark = new ArrowNew("road_give_way_mark");
public static Block double_white_line = new BlockAsphaltHorizontal1("double_white_line");
public static Block double_yellow_line = new BlockAsphaltHorizontal1("double_yellow_line");
public static Block double_white_line_slab = new BlockAsphaltSlabHorizontal2("double_white_line_slab");
public static Block double_yellow_line_slab = new BlockAsphaltSlabHorizontal2("double_yellow_line_slab");
public static Block double_white_line_half = new BlockAsphaltHorizontal("double_white_line_half");
public static Block double_yellow_line_half = new BlockAsphaltHorizontal("double_yellow_line_half");
public static Block double_white_line_half_slab = new BlockAsphaltSlabHorizontal("double_white_line_half_slab");
public static Block double_yellow_line_half_slab = new BlockAsphaltSlabHorizontal("double_yellow_line_half_slab");
public static Block single_yellow_line = new BlockAsphaltHorizontal1("single_yellow_line");
public static Block single_yellow_line_slab = new BlockAsphaltSlabHorizontal2("single_yellow_line_slab");
public static Block single_white_line = new BlockAsphaltHorizontal1("single_white_line");
public static Block single_white_line_slab = new BlockAsphaltSlabHorizontal2("single_white_line_slab");
public static Block single_white_line_45 = new BlockAsphaltHorizontal("single_white_line_45");
public static Block single_white_line_slab_45 = new BlockAsphaltSlabHorizontal("single_white_line_slab_45");
public static Block single_yellow_line_45 = new BlockAsphaltHorizontal("single_yellow_line_45");
public static Block single_yellow_line_slab_45 = new BlockAsphaltSlabHorizontal("single_yellow_line_slab_45");
public static Block single_white_line_thick = new BlockAsphaltHorizontal1("single_white_line_thick");
public static Block single_white_line_thick_slab = new BlockAsphaltSlabHorizontal2("single_white_line_thick_slab");
public static Block single_yellow_line_thick = new BlockAsphaltHorizontal1("single_yellow_line_thick");
public static Block single_yellow_line_thick_slab = new BlockAsphaltSlabHorizontal2("single_yellow_line_thick_slab");
public static Block road_excl_zone_corner_in = new BlockAsphaltHorizontal("road_excl_zone_corner_in");
public static Block road_excl_zone_corner_in_slab = new BlockAsphaltSlabHorizontal("road_excl_zone_corner_in_slab");
public static Block road_excl_zone_corner_out = new BlockAsphaltHorizontal("road_excl_zone_corner_out");
public static Block road_excl_zone_corner_out_slab = new BlockAsphaltSlabHorizontal("road_excl_zone_corner_out_slab");
public static Block road_excl_zone_diagonal_in = new BlockAsphaltHorizontal("road_excl_zone_diagonal_in");
public static Block road_excl_zone_diagonal_in_slab = new BlockAsphaltSlabHorizontal("road_excl_zone_diagonal_in_slab");
public static Block road_excl_zone_diagonal_out = new BlockAsphaltHorizontal("road_excl_zone_diagonal_out");
public static Block road_excl_zone_diagonal_out_slab = new BlockAsphaltSlabHorizontal("road_excl_zone_diagonal_out_slab");
public static Block road_excl_zone_line = new BlockAsphaltHorizontal("road_excl_zone_line");
public static Block road_excl_zone_line_slab = new BlockAsphaltSlabHorizontal("road_excl_zone_line_slab");
public static Block road_excl_zone = new BlockAsphaltHorizontal("road_excl_zone");
public static Block road_excl_zone_slab = new BlockAsphaltSlabHorizontal("road_excl_zone_slab");
public static Block road_excl_zone_split_our_l = new BlockAsphaltHorizontal("road_excl_zone_split_our_l");
public static Block road_excl_zone_split_our_r_slab = new BlockAsphaltSlabHorizontal("road_excl_zone_split_our_r_slab");
public static Block road_excl_zone_split_our_r = new BlockAsphaltHorizontal("road_excl_zone_split_our_r");
public static Block road_excl_zone_split_our_l_slab = new BlockAsphaltSlabHorizontal("road_excl_zone_split_our_l_slab");
public static Block road_excl_zone_split_in_l = new BlockAsphaltHorizontal("road_excl_zone_split_in_l");
public static Block road_excl_zone_split_in_r_slab = new BlockAsphaltSlabHorizontal("road_excl_zone_split_in_r_slab");
public static Block road_excl_zone_split_in_r = new BlockAsphaltHorizontal("road_excl_zone_split_in_r");
public static Block road_excl_zone_split_in_l_slab = new BlockAsphaltSlabHorizontal("road_excl_zone_split_in_l_slab");
public static Block platform_fence = new BlockPlatFormFence("platform_fence");
public static Block crosswalk = new BlockAsphaltHorizontal1("crosswalk");
public static Block crosswalk_slab = new BlockAsphaltSlabHorizontal2("crosswalk_slab");
public static Block railway_sign_post = new RailwaySignPost( );
public static Block stop_line = new StopLineBlock();
public static Block stop_line_slab = new StopLineBlockSlab();
public static Block stop_line_left = new BlockAsphaltHorizontal("stop_line_left");
public static Block stop_line_left_slab = new BlockAsphaltSlabHorizontal("stop_line_left_slab");
public static Block stop_line_right = new BlockAsphaltHorizontal("stop_line_right");
public static Block stop_line_right_1 = new BlockAsphaltHorizontal("stop_line_right_1");
public static Block stop_line_right_slab = new BlockAsphaltSlabHorizontal("stop_line_right_slab");
public static Block stop_line_right_1_slab = new BlockAsphaltSlabHorizontal("stop_line_right_1_slab");
public static Block stop_line_cross = new DiagonalStopLineBlock();
public static Block stop_line_cross_slab = new DiagonalStopLineBlockSlab();
public static Block road_lamp_1_up = new RoadLamp1U();
public static Block road_lamp_1_bottom = new RoadLamp1B();
public static Block arrow_diaotou = new ArrowNew("arrow_diaotou");
public static Block arrow_left_diaotou = new ArrowNew("arrow_left_diaotou");
public static Block arrow_left_right          = new ArrowNew("arrow_left_right");
public static Block arrow_left_right_straight = new ArrowNew("arrow_left_right_straight");
public static Block arrow_left_straight  = new ArrowNew("arrow_left_straight");
public static Block arrow_right_straight = new ArrowNew("arrow_right_straight");
public static Block arrow_left           = new ArrowNew("arrow_left");
public static Block arrow_right            = new ArrowNew("arrow_right");
public static Block arrow_straight         = new ArrowNew("arrow_straight");
public static Block arrow_straight_diaotou = new ArrowNew("arrow_straight_diaotou");
public static Block arrow_confluence_left = new ArrowNew("arrow_confluence_left");
public static Block arrow_confluence_right = new ArrowNew("arrow_confluence_right");
public static Block asphalt_cyan_black_half_line_slab = new BlockAsphaltSlabHorizontal("asphalt_cyan_black_half_line_slab");
public static Block asphalt_cyan_black_half_line = new BlockAsphaltHorizontal("asphalt_cyan_black_half_line");
public static Block asphalt_cyan_black_half_slab = new BlockAsphaltSlabHorizontal("asphalt_cyan_black_half_slab");
public static Block asphalt_cyan_black_half_45_slab = new BlockAsphaltSlabHorizontal("asphalt_cyan_black_half_45_slab");
public static Block asphalt_cyan_black_half = new BlockAsphaltHorizontal("asphalt_cyan_black_half");
public static Block asphalt_cyan_black_half_45 = new BlockAsphaltHorizontal("asphalt_cyan_black_half_45");
public static Block double_yellow_line_diagonal_slab = new BlockAsphaltSlabHorizontal("double_yellow_line_diagonal_slab");
public static Block double_yellow_line_diagonal = new BlockAsphaltHorizontal("double_yellow_line_diagonal");
public static Block double_white_line_diagonal_slab = new BlockAsphaltSlabHorizontal("double_white_line_diagonal_slab");
public static Block double_white_line_diagonal = new BlockAsphaltHorizontal("double_white_line_diagonal");
public static Block asphalt_red_black_half = new BlockAsphaltHorizontal("asphalt_red_black_half");
public static Block asphalt_red_black_half_slab = new BlockAsphaltSlabHorizontal("asphalt_red_black_half_slab");
public static Block asphalt_red_black_half_45 = new BlockAsphaltHorizontal("asphalt_red_black_half_45");
public static Block asphalt_red_black_half_45_slab = new BlockAsphaltSlabHorizontal("asphalt_red_black_half_45_slab");
public static Block asphalt_red_black_half_line = new BlockAsphaltHorizontal("asphalt_red_black_half_line");
public static Block asphalt_red_black_half_line_slab = new BlockAsphaltSlabHorizontal("asphalt_red_black_half_line_slab");
public static Block asphalt_cyan_black_half_line_diagonal_slab = new BlockAsphaltSlabHorizontal("asphalt_cyan_black_half_line_diagonal_slab");
public static Block asphalt_cyan_black_half_line_diagonal = new BlockAsphaltHorizontal("asphalt_cyan_black_half_line_diagonal");
public static Block asphalt_red_black_half_line_diagonal = new BlockAsphaltHorizontal("asphalt_red_black_half_line_diagonal");
public static Block asphalt_red_black_half_line_diagonal_slab = new BlockAsphaltSlabHorizontal("asphalt_red_black_half_line_diagonal_slab");
public static Block asphalt_cyan_black_half_line_diagonal_1 = new BlockAsphaltHorizontal("asphalt_cyan_black_half_line_diagonal_1");
public static Block asphalt_red_black_half_line_diagonal_1 = new BlockAsphaltHorizontal("asphalt_red_black_half_line_diagonal_1");
public static Block asphalt_red_black_half_line_diagonal_1_slab = new BlockAsphaltSlabHorizontal("asphalt_red_black_half_line_diagonal_1_slab");
public static Block asphalt_cyan_black_half_line_diagonal_1_slab = new BlockAsphaltSlabHorizontal("asphalt_cyan_black_half_line_diagonal_1_slab");
public static Block road_sign_sl_15 = new BlockRoadSign("road_sign_sl_15");
public static Block road_sign_sl_20 = new BlockRoadSign("road_sign_sl_20");
public static Block road_sign_sl_30 = new BlockRoadSign("road_sign_sl_30");
public static Block road_sign_sl_40 = new BlockRoadSign("road_sign_sl_40");
public static Block road_sign_sl_50 = new BlockRoadSign("road_sign_sl_50");
public static Block road_sign_sl_60 = new BlockRoadSign("road_sign_sl_60");
public static Block road_sign_sl_70 = new BlockRoadSign("road_sign_sl_70");
public static Block road_sign_sl_80 = new BlockRoadSign("road_sign_sl_80");
public static Block road_sign_sl_90 = new BlockRoadSign("road_sign_sl_90");
public static Block road_sign_sl_100 = new BlockRoadSign("road_sign_sl_100");
public static Block road_sign_sl_110 = new BlockRoadSign("road_sign_sl_110");
public static Block road_sign_sl_120 = new BlockRoadSign("road_sign_sl_120");
public static Block metro_fence = new BlockMetroFence("metro_fence");
public static Block road_pedestrian_traffic_light = new BlockRoadSign("road_pedestrian_traffic_light");
public static Block road_traffic_light = new BlockRoadSign("road_traffic_light");
public static Block road_traffic_light_red = new BlockRoadSign("road_traffic_light_red");
public static Block road_traffic_light_green = new BlockRoadSign("road_traffic_light_green");
public static Block road_traffic_light_yellow = new BlockRoadSign("road_traffic_light_yellow");
public static Block power_method_change_sign = new PowerMethodChangeSign.Register().setRegistryName("power_method_change_sign").setUnlocalizedName("PowerMethodChangeSign");
public static Block station_stop_sign = new StationStopSign().setRegistryName("station_stop_sign").setUnlocalizedName("StationStopSign");
public static Block thick_white_line_45 = new BlockAsphaltHorizontal("thick_white_line_45");
public static Block thick_white_line_slab_45 = new BlockAsphaltSlabHorizontal("thick_white_line_slab_45");
public static Block thick_yellow_line_45 = new BlockAsphaltHorizontal("thick_yellow_line_45");
public static Block thick_yellow_line_slab_45 = new BlockAsphaltSlabHorizontal("thick_yellow_line_slab_45");
public static Block platform_wall = new DollanBlock("platform_wall");
public static Block marble_block = new DollanBlock("marble_block").setHardness(1.5F).setResistance(10.0F);

public static Block beautiful_marble = new BlockMarble("beautiful_marble");
public static Block dollan_line_20 = new BlockMetroBlock("dollan_line_20");
public static Block calcite = new DollanBlock("calcite").setHardness(1.5F).setResistance(10.0F);
public static Block cherry_planks = new DollanCherryPlanks("cherry_planks");
public static Block cracked_deepslate = new BlockCrackedDeepSlate("cracked_deepslate");
public static Block deepslate = new BlockDeepSlate("deepslate");
public static Block elder_prismarine = new BlockElderPrismarine("elder_prismarine");
public static Block floorblock = new DollanBlock3("floorblock").setHardness(1.0F);
public static Block gray_porcelain_tile = new DollanBlock2("gray_porcelain_tile");
public static Block gray_wall = new DollanBlock2("gray_wall");
public static Block graymud = new DollanBlock2("graymud");
public static Block graystoneblock = new DollanBlock2("graystoneblock");
public static Block limestone = new BlockLimeStone();
public static Block mangrove_planks = new DollanPlanks("mangrove_planks");
public static Block metal = new DollanBlock4("metal");
public static Block mud_bricks = new DollanBlock2("mud_bricks");
//public static Block myalite = new BlockMyalite("myalite");
public static Block myalite = new Block(Material.ROCK).setRegistryName("myalite").setCreativeTab(DollanCustomized.Blocks).setUnlocalizedName(Reference.MOD_ID + ".myalite.normal").setHardness(1.0F).setResistance(10F);
public static Block myalite_crystal = new BlockMyaliteCrystal();
public static Block myalite_pavement = new Block(Material.ROCK).setRegistryName("myalite_pavement").setCreativeTab(DollanCustomized.Blocks).setUnlocalizedName(Reference.MOD_ID + ".myalite.pavement").setHardness(1.0F).setResistance(10F);
public static Block packed_mud = new DollanBlock2("packed_mud");
public static Block polished_blackstone = new BlockPolishedBlackStone();
public static Block polished_deepslate = new DollanPolishedDeepSlate("polished_deepslate");
public static Block polished_limestone = new DollanPolished("polished_limestone");
public static Block polished_marble = new DollanPolished("polished_marble");
public static Block polished_myalite = new DollanPolished("polished_myalite");
public static Block quartz_bricks = new DollanBlock2("quartz_bricks");
public static Block rock_1 = new DollanBlock2("rock_1");
public static Block rock_2 = new DollanBlock2("rock_2");
public static Block sandy_bricks = new DollanBlock2("sandy_bricks");
public static Block teacherblock = new DollanBlock3("teacherblock");
public static Block white_color_purple_block = new DollanBlock2("white_color_purple_block");
public static Block elder_sea_lantern = new DollanBlock5("elder_sea_lantern").setLightLevel(1.0F);
public static Block modern_stone_tile = new DollanBlock2("modern_stone_tile");
public static Block froglight = new BlockFrogLight();
public static Block marble_tile = new BlockMarbleTile();
public static Block dollan_line_17 = new BlockMetroBlock2("dollan_line_17");
public static Block dollan_line_18 = new BlockMetroBlock2("dollan_line_18");
public static Block dollan_line_19 = new BlockMetroBlock2("dollan_line_19");

public static Block overhead_lines = new OverheadLines("overhead_lines");
public static Block overhead_lines_straighter = new OverheadLines("overhead_lines_straighter");
public static Block overhead_lines_diagonal = new OverheadLinesDiagonal("overhead_lines_diagonal");
public static Block overhead_lines_support = new OverheadLinesSupport("overhead_lines_support");
public static Block overhead_lines_support_a1 = new OverheadLinesSupport("overhead_lines_support_a1");
public static Block overhead_lines_support_a2 = new OverheadLinesSupport("overhead_lines_support_a2");
public static Block overhead_lines_support_a3 = new OverheadLinesDiagonalSupport("overhead_lines_support_a3");
public static Block overhead_lines_support_a4 = new OverheadLinesDiagonalSupport2("overhead_lines_support_a4");
public static Block overhead_lines_diagonal_straighter = new OverheadLinesDiagonal("overhead_lines_diagonal_straighter");
public static Block overhead_lines_slope = new OverheadLinesSlope("overhead_lines_slope");
public static Block overhead_lines_slope_1 = new OverheadLinesSlope1("overhead_lines_slope_1");
public static Block overhead_lines_slope_2 = new OverheadLinesSlope2("overhead_lines_slope_2");
public static Block overhead_lines_double_diagonal = new OverheadLinesDD("overhead_lines_double_diagonal");
public static Block overhead_lines_double_diagonal_1 = new OverheadLinesDD1("overhead_lines_double_diagonal_1");
public static Block overhead_lines_double_wye = new OverheadLinesWye("overhead_lines_double_wye");

public static Block overhead_steel = new OverheadSteel("overhead_steel");
public static Block overhead_steel_diagonal = new OverheadSteelDiagonal("overhead_steel_diagonal");
public static Block overhead_steel_support = new OverheadSteelSupport("overhead_steel_support");
public static Block overhead_steel_diagonal_support = new OverheadSteelDiagonalSupport("overhead_steel_diagonal_support");
public static Block overhead_steel_diagonal_support_1 = new OverheadSteelDiagonalSupport("overhead_steel_diagonal_support_1");
public static Block overhead_steel_support_1 = new OverheadSteelSupport1("overhead_steel_support_1");
public static Block steel_double = new OverheadSteelDouble();
public static Block steel_double_support = new OverheadSteelDoubleSupport();

public static Block overhead_steel_slope = new OverheadSteelSlope("overhead_steel_slope");
public static Block overhead_steel_double_diagonal = new OverheadSteelDD("overhead_steel_double_diagonal");
public static Block overhead_steel_double_diagonal_1 = new OverheadSteelDD1("overhead_steel_double_diagonal_1");
public static Block overhead_steel_double_wye = new OverheadSteelWye("overhead_steel_double_wye");
public static Block overhead_steel_double_wye_1 = new OverheadSteelWye("overhead_steel_double_wye_1");

public static Block overhead_post = new OverheadPost("overhead_post");
public static Block overhead_post_1 = new OverheadPost1("overhead_post_1");
public static Block overhead_steel_post_up = new OverheadPost2("overhead_steel_post_up");
public static Block overhead_web_corner = new BlockOverHeadWeb("overhead_web_corner");
public static Block overhead_web_pillar = new BlockOverHeadWebRotated("overhead_web_pillar");
public static Block overhead_web_pillar_post = new BlockOverHeadWebWithPost("overhead_web_pillar_post");
public static Block overhead_web_pillar_post_1 = new BlockOverHeadWebWithPost("overhead_web_pillar_post_1");
public static Block overhead_web_pillar_post_2 = new BlockOverHeadWebWithPost("overhead_web_pillar_post_2");

public static Block signal_post  = new SignalPost("signal_post");
public static Block signal_none_one_light  = new SignalLightLower("signal_none_one_light");
public static Block signal_blue_one_light  = new SignalLightLower("signal_blue_one_light");
public static Block signal_red_one_light   = new SignalLightLower("signal_red_one_light");
public static Block signal_green_one_light = new SignalLightLower("signal_green_one_light");
public static Block signal_white_one_light = new SignalLightLower("signal_white_one_light");

public static Block signal_none_two_lights = new SignalLight("signal_none_two_lights");
public static Block signal_blue_two_lights = new SignalLight("signal_blue_two_lights");
public static Block signal_yellow_two_lights = new SignalLight("signal_yellow_two_lights");
public static Block signal_red_two_lights = new SignalLight("signal_red_two_lights");
public static Block signal_green_two_lights = new SignalLight("signal_green_two_lights");
public static Block signal_white_two_lights = new SignalLight("signal_white_two_lights");

public static Block signal_none_three_lights = new SignalLightHigher("signal_none_three_lights");
public static Block signal_blue_three_lights = new SignalLightHigher("signal_blue_three_lights");
public static Block signal_yellow_three_lights = new SignalLightHigher("signal_yellow_three_lights");
public static Block signal_red_three_lights = new SignalLightHigher("signal_red_three_lights");
public static Block signal_green_three_lights = new SignalLightHigher("signal_green_three_lights");
public static Block signal_white_three_lights = new SignalLightHigher("signal_white_three_lights");

public static Block signal_blue_four_lights = new SignalLightHighest("signal_blue_four_lights");
public static Block signal_yellow_four_lights = new SignalLightHighest("signal_yellow_four_lights");
public static Block signal_red_four_lights = new SignalLightHighest("signal_red_four_lights");
public static Block signal_green_four_lights = new SignalLightHighest("signal_green_four_lights");

public static Block metro_tran0 = new OverheadLineTran0();
public static Block metro_tran1 = new OverheadLineTran1();
public static Block metro_tran2 = new OverheadLineTran2();
public static Block metro_tran3 = new OverheadLineTran3();
public static Block metro_tran4 = new OverheadLineTran4();
public static Block metro_tran5 = new OverheadLineTran5("overhead_translation_5");
public static Block metro_tran6 = new OverheadLineTran6("overhead_translation_6");
public static Block metro_tran7 = new OverheadLineTran7();

public static Block station_end_door = new BlockStationEndDoor("station_end_door");

    public static void register() {
        registerBlock(road_slow_down_mark);
        registerBlock(road_give_way_mark);
        registerBlock(station_end_door);
        registerBlock(white_flat_lamp);
        registerBlock(white_punctiform_lamp);
        registerBlock(white_banding_lamp);
        registerBlock(lamp_tran);
        registerBlock(light);
        registerBlock(road_lamp_1_bottom);
        registerBlock(road_lamp_1_up);

        registerBlock(platform_fence);
        registerBlock(metro_fence);
        registerBlock(railway_sign_post);
        registerBlock(station_stop_sign);
        registerBlock(power_method_change_sign);


        registerBlock(arrow_diaotou);
        registerBlock(arrow_left_diaotou);
        registerBlock(arrow_left_right);
        registerBlock(arrow_left_right_straight);
        registerBlock(arrow_left_straight);
        registerBlock(arrow_right_straight);
        registerBlock(arrow_left);
        registerBlock(arrow_right);
        registerBlock(arrow_straight);
        registerBlock(bike_words);
        registerBlock(bus_lane_mark);
        registerBlock(bus_lane_mark_slab);
        registerBlock(road_traffic_light);
        registerBlock(road_traffic_light_red);
        registerBlock(road_traffic_light_green);
        registerBlock(road_traffic_light_yellow);
        registerBlock(road_pedestrian_traffic_light);
        registerBlock(road_sign_post);

        registerBlock(double_yellow_line_diagonal_slab);
        registerBlock(double_yellow_line_diagonal);
        registerBlock(double_white_line_diagonal_slab);
        registerBlock(double_white_line_diagonal);
        registerBlock(asphalt);
        registerBlock(asphalt_slab);

        registerBlock(single_yellow_line);
        registerBlock(single_yellow_line_slab);
        registerBlock(single_white_line);
        registerBlock(single_white_line_slab);
        registerBlock(single_white_line_45);

        registerBlock(single_white_line_slab_45);
        registerBlock(single_yellow_line_45);
        registerBlock(single_yellow_line_slab_45);
        registerBlock(single_white_line_thick);
        registerBlock(single_white_line_thick_slab);
        registerBlock(single_yellow_line_thick);
        registerBlock(single_yellow_line_thick_slab);

        registerBlock(double_white_line);
        registerBlock(double_white_line_slab);
        registerBlock(double_yellow_line);
        registerBlock(double_yellow_line_slab);

        registerBlock(double_white_line_half);
        registerBlock(double_white_line_half_slab);
        registerBlock(double_yellow_line_half);
        registerBlock(double_yellow_line_half_slab);

        registerBlock(crosswalk_slab);
        registerBlock(crosswalk);
        registerBlock(stop_line_slab);
        registerBlock(stop_line);
        registerBlock(stop_line_cross_slab);
        registerBlock(stop_line_cross);
        registerBlock(stop_line_left_slab);
        registerBlock(stop_line_left);
        registerBlock(stop_line_right_slab);
        registerBlock(stop_line_right_1_slab);
        registerBlock(stop_line_right);
        registerBlock(stop_line_right_1);

        registerBlock(asphalt_cyan_black_half_line);
        registerBlock(asphalt_cyan_black_half_line_slab);
        registerBlock(asphalt_cyan_black_half);
        registerBlock(asphalt_cyan_black_half_slab);
        registerBlock(asphalt_red_black_half_slab);
        registerBlock(asphalt_red_black_half);
        registerBlock(asphalt_red_black_half_line);
        registerBlock(asphalt_red_black_half_line_slab);
        registerBlock(asphalt_cyan_black_half_line_diagonal);
        registerBlock(asphalt_cyan_black_half_line_diagonal_slab);
        registerBlock(asphalt_red_black_half_line_diagonal_slab);
        registerBlock(asphalt_red_black_half_line_diagonal);
        registerBlock(asphalt_cyan_black_half_line_diagonal_1);
        registerBlock(asphalt_cyan_black_half_line_diagonal_1_slab);
        registerBlock(asphalt_red_black_half_line_diagonal_1_slab);
        registerBlock(asphalt_red_black_half_line_diagonal_1);

        registerBlock(road_excl_zone_corner_out);
        registerBlock(road_excl_zone_corner_out_slab);
        registerBlock(road_excl_zone_corner_in);
        registerBlock(road_excl_zone_corner_in_slab);

        registerBlock(road_excl_zone_diagonal_out);
        registerBlock(road_excl_zone_diagonal_out_slab);
        registerBlock(road_excl_zone_diagonal_in);
        registerBlock(road_excl_zone_diagonal_in_slab);
        registerBlock(road_excl_zone);
        registerBlock(road_excl_zone_slab);
        registerBlock(road_excl_zone_line);
        registerBlock(road_excl_zone_line_slab);
        registerBlock(road_excl_zone_split_our_l);
        registerBlock(road_excl_zone_split_our_l_slab);
        registerBlock(road_excl_zone_split_our_r);
        registerBlock(road_excl_zone_split_our_r_slab);
        registerBlock(road_excl_zone_split_in_l);
        registerBlock(road_excl_zone_split_in_l_slab);
        registerBlock(road_excl_zone_split_in_r);
        registerBlock(road_excl_zone_split_in_r_slab);

        registerBlock(dollan_airport_line);
        registerBlock(dollan_s1);
        registerBlock(dollan_s2);

        registerBlock(dollan_line_1);
        registerBlock(dollan_line_2);
        registerBlock(dollan_line_3);
        registerBlock(dollan_line_4);
        registerBlock(dollan_line_5);
        registerBlock(dollan_line_6);
        registerBlock(dollan_line_7);
        registerBlock(dollan_line_8);
        registerBlock(dollan_line_9);
        registerBlock(dollan_line_10);
        registerBlock(dollan_line_11);
        registerBlock(dollan_line_12);
        registerBlock(dollan_line_13);
        registerBlock(dollan_line_14);
        registerBlock(dollan_line_15);
        registerBlock(dollan_line_16);



        registerBlock(mingsha_S1);
        registerBlock(mingsha_S2);
        registerBlock(mingsha_S3);
        registerBlock(mingsha_S4);
        registerBlock(mingsha_S5);
        registerBlock(mingsha_S6);

        registerBlock(road_sign_go_right);
        registerBlock(road_sign_go_left);
        registerBlock(road_sign_go_both);
        registerBlock(road_sign_can_left);
        registerBlock(road_sign_can_right);
        registerBlock(road_sign_can_diaotou);
        registerBlock(road_sign_do_not_enter);
        registerBlock(road_sign_do_not_enter_1);
        registerBlock(road_sign_do_not_left);
        registerBlock(road_sign_do_not_right);
        registerBlock(road_sign_do_not_diaotou);
        registerBlock(road_sign_sl_15);
        registerBlock(road_sign_sl_20);
        registerBlock(road_sign_sl_30);
        registerBlock(road_sign_sl_40);
        registerBlock(road_sign_sl_50);
        registerBlock(road_sign_sl_60);
        registerBlock(road_sign_sl_70);
        registerBlock(road_sign_sl_80);
        registerBlock(road_sign_sl_90);
        registerBlock(road_sign_sl_100);
        registerBlock(road_sign_sl_110);
        registerBlock(road_sign_sl_120);

        registerBlock(road_sign_triangle_rang);
        registerBlock(road_sign_triangle_slow);
        registerBlock(road_sign_triangle_warn);
        registerBlock(road_sign_triangle_shizi);
        registerBlock(road_sign_triangle_t);
        registerBlock(road_sign_triangle_t1);
        registerBlock(road_sign_triangle_t2);
        registerBlock(road_sign_triangle_y);
        //2.1
        registerBlock(road_sign_dont_stop);
        registerBlock(road_sign_dont_stop_1);
        registerBlock(road_sign_highway_start);
        registerBlock(road_sign_highway_end);
        registerBlock(road_sign_park);
        registerBlock(road_sign_park_1);


        //2.1
        registerBlock(asphalt_red_black_half_45);
        registerBlock(asphalt_red_black_half_45_slab);
        registerBlock(asphalt_cyan_black_half_45);
        registerBlock(asphalt_cyan_black_half_45_slab);

        registerBlock(single_white_90);
        registerBlock(single_white_90_1);
        registerBlock(single_white_all_line);
        registerBlock(single_white_90_slab);
        registerBlock(single_white_90_1_slab);
        registerBlock(single_white_all_line_slab);
        registerBlock(single_yellow_90);
        registerBlock(single_yellow_90_1);
        registerBlock(single_yellow_all_line);
        registerBlock(single_yellow_90_slab);
        registerBlock(single_yellow_90_1_slab);
        registerBlock(single_yellow_all_line_slab);

        registerBlock(thick_white_line_45);
        registerBlock(thick_white_line_slab_45);
        registerBlock(thick_yellow_line_45);
        registerBlock(thick_yellow_line_slab_45);

        registerBlock(ms_lines);
        registerBlock(ms_lines_slab);
        registerBlock(crosswalk_diagonal);
        registerBlock(crosswalk_diagonal_slab);

        registerBlock(arrow_straight_diaotou);
        registerBlock(platform_wall);
        registerBlock(marble_block);

        registerBlock(road_sign_one_way_straight);
        registerBlock(road_sign_left_warn);
        registerBlock(road_sign_right_warn);
        registerBlock(road_sign_left_right_warn);

        registerBlock(arrow_confluence_left);
        registerBlock(arrow_confluence_right);
        //registerBlock(arrow_diaotou_sl);
        //registerBlock(arrow_left_diaotou_sl);
        //registerBlock(arrow_left_right_sl);
        //registerBlock(arrow_left_right_staright_sl);
        //registerBlock(arrow_left_staright_sl);
        //registerBlock(arrow_right_staright_sl);
        //registerBlock(arrow_left_sl);
        //registerBlock(arrow_right_sl);
        //registerBlock(arrow_staright_sl);
        //registerBlock(arrow_staright_diaotou_sl);
        //registerBlock(arrow_confluence_left_sl);
        //registerBlock(arrow_confluence_right_sl);
        //other
        registerBlock(beautiful_marble);
        //registerBlock(cherry_planks);
        registerBlock(calcite);
        registerBlock(dollan_line_18);
        registerBlock(cracked_deepslate);
        registerBlock(deepslate);
        registerBlock(elder_prismarine);
        registerBlock(floorblock);
        registerBlock(gray_porcelain_tile);
        registerBlock(gray_wall);
        registerBlock(graymud);
        registerBlock(graystoneblock);
        registerBlock(dollan_line_17);
        registerBlock(dollan_line_19);
        registerBlock(limestone);
        registerBlock(mangrove_planks);
        registerBlock(metal);
        registerBlock(modern_stone_tile);
        registerBlock(mud_bricks);
        registerBlock(myalite);
        registerBlock(myalite_pavement);
        registerBlock(myalite_crystal);
        registerBlock(packed_mud);
        registerBlock(polished_blackstone);
        registerBlock(polished_limestone);
        registerBlock(polished_deepslate);
        registerBlock(polished_marble);
        registerBlock(polished_myalite);
        registerBlock(quartz_bricks);
        registerBlock(rock_1);
        registerBlock(rock_2);
        registerBlock(dollan_line_22);
        registerBlock(sandy_bricks);
        registerBlock(teacherblock);
        registerBlock(white_color_purple_block);
        registerBlock(elder_sea_lantern);
        registerBlock(froglight);
        registerBlock(marble_tile);
        registerBlock(dollan_line_21);
        registerBlock(dollan_loop_line);
        registerBlock(dollan_line_20);

        registerBlock(overhead_lines);
        registerBlock(overhead_lines_slope);
        registerBlock(overhead_lines_slope_1);
        registerBlock(overhead_lines_slope_2);
        registerBlock(overhead_lines_diagonal);
        registerBlock(overhead_lines_straighter);
        registerBlock(overhead_lines_diagonal_straighter);
        registerBlock(overhead_lines_support);
        registerBlock(overhead_lines_support_a1);
        registerBlock(overhead_lines_support_a2);
        registerBlock(overhead_lines_support_a3);
        registerBlock(overhead_lines_support_a4);
        registerBlock(overhead_lines_double_diagonal);
        registerBlock(overhead_lines_double_diagonal_1);
        registerBlock(overhead_lines_double_wye);

        registerBlock(metro_tran0);
        registerBlock(metro_tran1);
        registerBlock(metro_tran2);
        registerBlock(metro_tran3);
        registerBlock(metro_tran4);
        registerBlock(metro_tran5);
        registerBlock(metro_tran6);
        registerBlock(metro_tran7);

        registerBlock(overhead_steel);
        registerBlock(overhead_steel_slope);
        registerBlock(overhead_steel_diagonal);
        registerBlock(overhead_steel_support);
        registerBlock(overhead_steel_diagonal_support);
        registerBlock(overhead_steel_diagonal_support_1);
        registerBlock(overhead_steel_support_1);
        registerBlock(overhead_steel_double_diagonal);
        registerBlock(overhead_steel_double_diagonal_1);
        registerBlock(overhead_steel_double_wye);
        registerBlock(overhead_steel_double_wye_1);
        registerBlock(steel_double);
        registerBlock(steel_double_support);

        registerBlock(overhead_post);
        registerBlock(overhead_post_1);
        registerBlock(overhead_steel_post_up);
        registerBlock(overhead_web_corner);
        registerBlock(overhead_web_pillar);
        registerBlock(overhead_web_pillar_post);
        registerBlock(overhead_web_pillar_post_1);
        registerBlock(overhead_web_pillar_post_2);

        registerBlock(signal_post);
        registerBlock(signal_none_one_light);
        registerBlock(signal_red_one_light);
        registerBlock(signal_blue_one_light);  
        registerBlock(signal_green_one_light);
        registerBlock(signal_white_one_light);
        
        registerBlock(signal_none_two_lights);
        registerBlock(signal_red_two_lights);
        registerBlock(signal_blue_two_lights);
        registerBlock(signal_green_two_lights);
        registerBlock(signal_white_two_lights);
        registerBlock(signal_yellow_two_lights);
        
        registerBlock(signal_none_three_lights);
        registerBlock(signal_red_three_lights);
        registerBlock(signal_blue_three_lights);
        registerBlock(signal_green_three_lights);
        registerBlock(signal_white_three_lights);
        registerBlock(signal_yellow_three_lights);
        
        registerBlock(signal_red_four_lights);
        registerBlock(signal_blue_four_lights);
        registerBlock(signal_green_four_lights);
        registerBlock(signal_yellow_four_lights);

    }

    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        registerModel(road_give_way_mark);
        registerModel(road_slow_down_mark);
        registerModel(station_end_door);
        registerModel(railway_sign_post);
        registerModel(station_stop_sign);
        registerModel(station_stop_sign,1,"station_stop_sign_wall");
        registerModel(station_stop_sign,2,"station_stop_sign_wall_single");
        registerModel(arrow_confluence_left);
        registerModel(arrow_confluence_right);

        registerModel(road_sign_left_warn);
        registerModel(road_sign_right_warn);
        registerModel(road_sign_left_right_warn);

        registerModel(road_sign_one_way_straight);

        registerModel(double_white_line_half);
        registerModel(double_white_line_half_slab);
        registerModel(double_yellow_line_half);
        registerModel(double_yellow_line_half_slab);
        registerModel(crosswalk_diagonal);
        registerModel(crosswalk_diagonal_slab);
        registerModel(dollan_airport_line);
        registerModel(dollan_s1);
        registerModel(dollan_s2);
        registerModel(road_excl_zone_corner_out);
        registerModel(road_excl_zone_corner_out_slab);
        registerModel(road_excl_zone_corner_in);
        registerModel(road_excl_zone_corner_in_slab);
        registerModel(platform_fence);
        registerModel(mingsha_S1);
        registerModel(mingsha_S2);
        registerModel(mingsha_S3);
        registerModel(mingsha_S4);
        registerModel(mingsha_S5);
        registerModel(mingsha_S6);
        registerModel(asphalt_red_black_half_45);
        registerModel(asphalt_red_black_half_45_slab);
        registerModel(asphalt_cyan_black_half_45);
        registerModel(asphalt_cyan_black_half_45_slab);
        registerModel(road_sign_dont_stop);
        registerModel(road_sign_dont_stop_1);
        registerModel(road_sign_highway_start);
        registerModel(road_sign_highway_end);
        registerModel(road_sign_park);
        registerModel(road_sign_park_1);
        registerModel(single_white_90);
        registerModel(single_white_90_1);
        registerModel(single_white_all_line);
        registerModel(single_white_90_slab);
        registerModel(single_white_90_1_slab);
        registerModel(single_white_all_line_slab);
        registerModel(single_yellow_90);
        registerModel(single_yellow_90_1);
        registerModel(single_yellow_all_line);
        registerModel(single_yellow_90_slab);
        registerModel(single_yellow_90_1_slab);
        registerModel(single_yellow_all_line_slab);
        registerModel(road_sign_triangle_warn);
        registerModel(road_sign_triangle_t);
        registerModel(road_sign_triangle_t1);
        registerModel(road_sign_triangle_t2);
        registerModel(road_sign_triangle_shizi);
        registerModel(road_sign_triangle_y);
        registerModel(road_sign_triangle_rang);
        registerModel(road_sign_triangle_slow);
        registerModel(road_sign_go_right);
        registerModel(road_sign_go_left);
        registerModel(road_sign_go_both);
        for (BusLaneMark.EnumType subtype : BusLaneMark.EnumType.values()) {
            registerModel(bus_lane_mark, subtype.getMetadata(), "bus_lane_" + subtype.getName());
        }
        registerModel(road_sign_do_not_enter);
        registerModel(road_traffic_light);
        registerModel(road_traffic_light_red);
        registerModel(road_traffic_light_green);
        registerModel(road_traffic_light_yellow);
        registerModel(road_pedestrian_traffic_light);
        registerModel(metro_fence,BlockMetroFence.FenceType.METRO.getMetadata(), "metro_fence");
        registerModel(metro_fence,BlockMetroFence.FenceType.ROAD_CENTER.getMetadata(), "road_center_fence");
        registerModel(metro_fence,BlockMetroFence.FenceType.ROAD.getMetadata(), "road_fence");
        registerModel(road_sign_post);
        registerModel(double_yellow_line_diagonal);
        registerModel(double_yellow_line_diagonal_slab);
        registerModel(double_white_line_diagonal);
        registerModel(double_white_line_diagonal_slab);
        registerModel(bike_words);
        registerModel(double_white_line);
        registerModel(double_white_line_slab);
        registerModel(double_yellow_line);
        registerModel(double_yellow_line_slab);
        registerModel(single_yellow_line);
        registerModel(single_yellow_line_slab);
        registerModel(single_white_line);
        registerModel(single_white_line_slab);
        registerModel(single_white_line_45);
        registerModel(single_white_line_slab_45);
        registerModel(single_yellow_line_45);
        registerModel(single_yellow_line_slab_45);
        registerModel(thick_white_line_45);
        registerModel(thick_white_line_slab_45);
        registerModel(thick_yellow_line_45);
        registerModel(thick_yellow_line_slab_45);
        registerModel(crosswalk);
        registerModel(crosswalk_slab);
        registerModel(stop_line,0, "stop_line");
        registerModel(stop_line,2, "road_stop_diagonal");
        registerModel(stop_line,1, "road_stop_diagonal_1");
        registerModel(stop_line_slab,0, "stop_line_slab");
        registerModel(stop_line_slab,2, "road_stop_diagonal_slab");
        registerModel(stop_line_slab,1, "road_stop_diagonal_1_slab");
        registerModel(stop_line_cross,0, "road_stop_diagonal_white_line");
        registerModel(stop_line_cross,1, "road_stop_diagonal_white_line_1");
        registerModel(stop_line_cross,2, "road_stop_diagonal_yellow_line");
        registerModel(stop_line_cross,3, "road_stop_diagonal_yellow_line_1");
        registerModel(stop_line_cross_slab,0, "road_stop_diagonal_white_line_slab");
        registerModel(stop_line_cross_slab,1, "road_stop_diagonal_white_line_1_slab");
        registerModel(stop_line_cross_slab,2, "road_stop_diagonal_yellow_line_slab");
        registerModel(stop_line_cross_slab,3, "road_stop_diagonal_yellow_line_1_slab");
        registerModel(stop_line);
        registerModel(stop_line_left_slab);
        registerModel(stop_line_left);
        registerModel(stop_line_right_slab);
        registerModel(stop_line_right_1_slab);
        registerModel(stop_line_right);
        registerModel(stop_line_right_1);
        registerModel(single_white_line_thick);
        registerModel(single_white_line_thick_slab);
        registerModel(single_yellow_line_thick);
        registerModel(single_yellow_line_thick_slab);
        registerModel(road_excl_zone_diagonal_in);
        registerModel(road_excl_zone_diagonal_in_slab);
        registerModel(road_excl_zone_diagonal_out);
        registerModel(road_excl_zone_diagonal_out_slab);
        registerModel(road_excl_zone);
        registerModel(road_excl_zone_slab);
        registerModel(road_excl_zone_line);
        registerModel(road_excl_zone_line_slab);
        registerModel(road_excl_zone_split_our_l);
        registerModel(road_excl_zone_split_our_l_slab);
        registerModel(road_excl_zone_split_our_r);
        registerModel(road_excl_zone_split_our_r_slab);
        registerModel(road_excl_zone_split_in_l);
        registerModel(road_excl_zone_split_in_l_slab);
        registerModel(road_excl_zone_split_in_r);
        registerModel(road_excl_zone_split_in_r_slab);
        registerModel(road_lamp_1_bottom);
        registerModel(road_lamp_1_up);
        registerModel(white_flat_lamp);
        registerModel(white_punctiform_lamp);
        registerModel(white_banding_lamp);
        registerModel(asphalt,BlockAsphalt.AsphaltType.BLACK.getMetadata(), "black_asphalt");
        registerModel(asphalt,BlockAsphalt.AsphaltType.CYAN.getMetadata(), "cyan_asphalt");
        registerModel(asphalt,BlockAsphalt.AsphaltType.RED.getMetadata(), "red_asphalt");
        registerModel(asphalt,BlockAsphalt.AsphaltType.WHITE.getMetadata(), "white_asphalt");
        registerModel(asphalt,BlockAsphalt.AsphaltType.YELLOW.getMetadata(), "yellow_asphalt");
        registerModel(asphalt_slab,BlockAsphalt.AsphaltType.BLACK.getMetadata(), "black_asphalt_slab");
        registerModel(asphalt_slab,BlockAsphalt.AsphaltType.CYAN.getMetadata(), "cyan_asphalt_slab");
        registerModel(asphalt_slab,BlockAsphalt.AsphaltType.RED.getMetadata(), "red_asphalt_slab");
        registerModel(dollan_line_1);
        registerModel(dollan_line_2);
        registerModel(dollan_line_3);
        registerModel(dollan_line_4);
        registerModel(dollan_line_5);
        registerModel(dollan_line_6);
        registerModel(dollan_line_7);
        registerModel(dollan_line_8);
        registerModel(dollan_line_9);
        registerModel(dollan_line_10);
        registerModel(dollan_line_11);
        registerModel(dollan_line_12);
        registerModel(dollan_line_13);
        registerModel(dollan_line_14);
        registerModel(dollan_line_15);
        registerModel(dollan_line_16);
        registerModel(dollan_line_21);
        registerModel(dollan_line_22);

        registerModel(lamp_tran, EnumDyeColor.WHITE.getMetadata(),"lamp_tran_0");
        registerModel(lamp_tran, EnumDyeColor.BLACK.getMetadata(),"lamp_tran_1");
        registerModel(lamp_tran, EnumDyeColor.YELLOW.getMetadata(),"lamp_tran_2");
        registerModel(lamp_tran, EnumDyeColor.RED.getMetadata(),"lamp_tran_3");
        registerModel(lamp_tran, EnumDyeColor.GREEN.getMetadata(),"lamp_tran_4");
        registerModel(lamp_tran, EnumDyeColor.BLUE.getMetadata(),"lamp_tran_blue");
        registerModel(lamp_tran, EnumDyeColor.BROWN.getMetadata(),"lamp_tran_brown");
        registerModel(lamp_tran, EnumDyeColor.GRAY.getMetadata(),"lamp_tran_gray");
        registerModel(lamp_tran, EnumDyeColor.LIGHT_BLUE.getMetadata(),"lamp_tran_light_blue");
        registerModel(lamp_tran, EnumDyeColor.LIME.getMetadata(),"lamp_tran_lime");
        registerModel(lamp_tran, EnumDyeColor.SILVER.getMetadata(),"lamp_tran_silver");
        registerModel(lamp_tran, EnumDyeColor.MAGENTA.getMetadata(),"lamp_tran_magenta");
        registerModel(lamp_tran, EnumDyeColor.PURPLE.getMetadata(),"lamp_tran_purple");
        registerModel(lamp_tran, EnumDyeColor.ORANGE.getMetadata(),"lamp_tran_orange");
        registerModel(lamp_tran, EnumDyeColor.PINK.getMetadata(),"lamp_tran_pink");
        registerModel(lamp_tran, EnumDyeColor.CYAN.getMetadata(),"lamp_tran_cyan");
        registerModel(light, EnumDyeColor.WHITE.getMetadata(),"lamp_white");
        registerModel(light, EnumDyeColor.BLACK.getMetadata(),"lamp_black");
        registerModel(light, EnumDyeColor.YELLOW.getMetadata(),"lamp_yellow");
        registerModel(light, EnumDyeColor.RED.getMetadata(),"lamp_red");
        registerModel(light, EnumDyeColor.GREEN.getMetadata(),"lamp_green");
        registerModel(light, EnumDyeColor.BLUE.getMetadata(),"lamp_blue");
        registerModel(light, EnumDyeColor.BROWN.getMetadata(),"lamp_brown");
        registerModel(light, EnumDyeColor.GRAY.getMetadata(),"lamp_gray");
        registerModel(light, EnumDyeColor.LIGHT_BLUE.getMetadata(),"lamp_light_blue");
        registerModel(light, EnumDyeColor.LIME.getMetadata(),"lamp_lime");
        registerModel(light, EnumDyeColor.SILVER.getMetadata(),"lamp_silver");
        registerModel(light, EnumDyeColor.MAGENTA.getMetadata(),"lamp_magenta");
        registerModel(light, EnumDyeColor.PURPLE.getMetadata(),"lamp_purple");
        registerModel(light, EnumDyeColor.ORANGE.getMetadata(),"lamp_orange");
        registerModel(light, EnumDyeColor.PINK.getMetadata(),"lamp_pink");
        registerModel(light, EnumDyeColor.CYAN.getMetadata(),"lamp_cyan");
        registerModel(asphalt_cyan_black_half_line);
        registerModel(asphalt_cyan_black_half_line_slab);
        registerModel(asphalt_cyan_black_half);
        registerModel(asphalt_cyan_black_half_slab);
        registerModel(asphalt_red_black_half_slab);
        registerModel(asphalt_red_black_half);
        registerModel(asphalt_red_black_half_line);
        registerModel(asphalt_red_black_half_line_slab);
        registerModel(asphalt_cyan_black_half_line_diagonal);
        registerModel(asphalt_cyan_black_half_line_diagonal_slab);
        registerModel(asphalt_red_black_half_line_diagonal_slab);
        registerModel(asphalt_red_black_half_line_diagonal);
        registerModel(asphalt_cyan_black_half_line_diagonal_1);
        registerModel(asphalt_cyan_black_half_line_diagonal_1_slab);
        registerModel(asphalt_red_black_half_line_diagonal_1_slab);
        registerModel(asphalt_red_black_half_line_diagonal_1);
        registerModel(arrow_diaotou);
        registerModel(arrow_left_diaotou);
        registerModel(arrow_left_right);
        registerModel(arrow_left_right_straight);
        registerModel(arrow_left_straight);
        registerModel(arrow_right_straight);
        registerModel(arrow_left);
        registerModel(arrow_right);
        registerModel(arrow_straight);
        registerModel(road_sign_sl_15);
        registerModel(road_sign_sl_20);
        registerModel(road_sign_sl_30);
        registerModel(road_sign_sl_40);
        registerModel(road_sign_sl_50);
        registerModel(road_sign_sl_60);
        registerModel(road_sign_sl_70);
        registerModel(road_sign_sl_80);
        registerModel(road_sign_sl_90);
        registerModel(road_sign_sl_100);
        registerModel(road_sign_sl_110);
        registerModel(road_sign_sl_120);
        registerModel(road_sign_do_not_enter);
        registerModel(road_sign_do_not_enter_1);
        registerModel(road_sign_do_not_left);
        registerModel(road_sign_do_not_right);
        registerModel(road_sign_do_not_diaotou);
        registerModel(road_sign_can_left);
        registerModel(road_sign_can_right);
        registerModel(road_sign_can_diaotou);

        registerModel(ms_lines, RoadMergeOtherLine.EnumVariant.MERGE_WHITE.getMetadata(),"merge_line");
        registerModel(ms_lines, RoadMergeOtherLine.EnumVariant.MERGE_YELLOW.getMetadata(),"merge_line_yellow");
        registerModel(ms_lines, RoadMergeOtherLine.EnumVariant.SEPARATE_WHITE.getMetadata(),"separate_line");
        registerModel(ms_lines, RoadMergeOtherLine.EnumVariant.SEPARATE_YELLOW.getMetadata(),"separate_line_yellow");

        registerModel(ms_lines_slab, RoadMergeOtherLineSlab.EnumVariant.MERGE_WHITE.getMetadata(),"merge_line_slab");
        registerModel(ms_lines_slab, RoadMergeOtherLineSlab.EnumVariant.MERGE_YELLOW.getMetadata(),"merge_line_yellow_slab");
        registerModel(ms_lines_slab, RoadMergeOtherLineSlab.EnumVariant.SEPARATE_WHITE.getMetadata(),"separate_line_slab");
        registerModel(ms_lines_slab, RoadMergeOtherLineSlab.EnumVariant.SEPARATE_YELLOW.getMetadata(),"separate_line_yellow_slab");

        registerModel(arrow_straight_diaotou);
        registerModel(platform_wall);
        registerModel(marble_block);

        registerModel(dollan_line_17);
        registerModel(dollan_line_19);
        registerModel(dollan_line_18);
        registerModel(dollan_line_20);

        registerModel(dollan_loop_line);


        registerModel(beautiful_marble,0,"normal_marble");
        registerModel(beautiful_marble,1,"bricks_marble");
        registerModel(beautiful_marble,2,"cut_marble");
        registerModel(beautiful_marble,3,"litcut_marble");
        registerModel(beautiful_marble,4,"smooth_marble");
        registerModel(cherry_planks);
        registerModel(cracked_deepslate);
        registerModel(cracked_deepslate,0,"bricks_cracked_deepslate");
        registerModel(cracked_deepslate,1,"tiles_cracked_deepslate");
        registerModel(calcite);
        registerModel(deepslate);
        registerModel(deepslate,1,"deepslate_bricks");
        registerModel(deepslate,2,"deepslate_tiles");
        registerModel(elder_prismarine,0,BlockElderPrismarine.PrismarineType.NORMAL.getName());
        registerModel(elder_prismarine,1,BlockElderPrismarine.PrismarineType.BRICKS.getName());
        registerModel(elder_prismarine,2,BlockElderPrismarine.PrismarineType.SMOOTH.getName());
        registerModel(floorblock);

        registerModel(gray_porcelain_tile);
        registerModel(gray_wall);
        registerModel(graymud);
        registerModel(graystoneblock);
        registerModel(limestone,0,BlockLimeStone.LimeStoneType.NORMAL.getName());
        registerModel(limestone,1,BlockLimeStone.LimeStoneType.BRICKS.getName());
        registerModel(limestone,2,BlockLimeStone.LimeStoneType.PAVEMENT.getName());
        registerModel(mangrove_planks);
        registerModel(metal);
        registerModel(mud_bricks);
        registerModel(myalite,0,BlockMyalite.MyaliteType.NORMAL.getName());
        registerModel(myalite,1,BlockMyalite.MyaliteType.CRYSTAL.getName());
        registerModel(myalite,2,BlockMyalite.MyaliteType.PAVEMENT.getName());
        registerModel(myalite_crystal,0,BlockMyalite.MyaliteType.CRYSTAL.getName());
        registerModel(myalite_pavement,0,BlockMyalite.MyaliteType.PAVEMENT.getName());
        registerModel(packed_mud);
        registerModel(polished_blackstone,0,BlockPolishedBlackStone.BlackStoneType.NORMAL.getName());
        registerModel(polished_blackstone,1,BlockPolishedBlackStone.BlackStoneType.BRICKS.getName());
        registerModel(polished_limestone);
        registerModel(polished_deepslate);
        registerModel(polished_marble);
        registerModel(polished_myalite);
        registerModel(quartz_bricks);
        registerModel(rock_1);
        registerModel(rock_2);
        registerModel(sandy_bricks);
        registerModel(teacherblock);
        registerModel(white_color_purple_block);
        registerModel(elder_sea_lantern);
        registerModel(modern_stone_tile);
        registerModel(froglight,0,BlockFrogLight.FrogLightType.PURPLE.getName() + "_froglight");
        registerModel(froglight,1,BlockFrogLight.FrogLightType.ORANGE.getName() + "_froglight");
        registerModel(froglight,2,BlockFrogLight.FrogLightType.GREEN.getName() + "_froglight");
        registerModel(marble_tile, EnumDyeColor.WHITE.getMetadata(),"white_marble_tile");
        registerModel(marble_tile, EnumDyeColor.BLACK.getMetadata(),"black_marble_tile");
        registerModel(marble_tile, EnumDyeColor.YELLOW.getMetadata(),"yellow_marble_tile");
        registerModel(marble_tile, EnumDyeColor.RED.getMetadata(),"red_marble_tile");
        registerModel(marble_tile, EnumDyeColor.GREEN.getMetadata(),"green_marble_tile");
        registerModel(marble_tile, EnumDyeColor.BLUE.getMetadata(),"blue_marble_tile");
        registerModel(marble_tile, EnumDyeColor.BROWN.getMetadata(),"brown_marble_tile");
        registerModel(marble_tile, EnumDyeColor.GRAY.getMetadata(),"gray_marble_tile");
        registerModel(marble_tile, EnumDyeColor.LIGHT_BLUE.getMetadata(),"light_blue_marble_tile");
        registerModel(marble_tile, EnumDyeColor.LIME.getMetadata(),"lime_marble_tile");
        registerModel(marble_tile, EnumDyeColor.SILVER.getMetadata(),"silver_marble_tile");
        registerModel(marble_tile, EnumDyeColor.MAGENTA.getMetadata(),"magenta_marble_tile");
        registerModel(marble_tile, EnumDyeColor.PURPLE.getMetadata(),"purple_marble_tile");
        registerModel(marble_tile, EnumDyeColor.ORANGE.getMetadata(),"orange_marble_tile");
        registerModel(marble_tile, EnumDyeColor.PINK.getMetadata(),"pink_marble_tile");
        registerModel(marble_tile, EnumDyeColor.CYAN.getMetadata(),"cyan_marble_tile");
        registerModel(overhead_lines);
        registerModel(overhead_lines_slope);
        registerModel(overhead_lines_slope_1);
        registerModel(overhead_lines_slope_2);
        registerModel(overhead_lines_diagonal);
        registerModel(overhead_lines_straighter);
        registerModel(overhead_lines_diagonal_straighter);
        registerModel(overhead_lines_support);
        registerModel(overhead_lines_support_a1);
        registerModel(overhead_lines_support_a2);
        registerModel(overhead_lines_support_a3);
        registerModel(overhead_lines_support_a4);
        registerModel(overhead_lines_double_diagonal);
        registerModel(overhead_lines_double_diagonal_1);
        registerModel(overhead_lines_double_wye);

        registerModel(metro_tran0);
        registerModel(metro_tran1);
        registerModel(metro_tran2);
        registerModel(metro_tran3);
        registerModel(metro_tran4);
        registerModel(metro_tran5);
        registerModel(metro_tran6);
        registerModel(metro_tran7);

        registerModel(overhead_steel);
        registerModel(overhead_steel_slope);
        registerModel(overhead_steel_diagonal);
        registerModel(overhead_steel_support);
        registerModel(overhead_steel_diagonal_support);
        registerModel(overhead_steel_diagonal_support_1);
        registerModel(overhead_steel_diagonal_support,1,"overhead_steel_diagonal_support_2");
        registerModel(overhead_steel_diagonal_support_1,1,"overhead_steel_diagonal_support_3");
        registerModel(overhead_steel_support_1);
        registerModel(overhead_steel_double_diagonal);
        registerModel(overhead_steel_double_diagonal_1);
        registerModel(overhead_steel_double_wye);
        registerModel(overhead_steel_double_wye_1);
        registerModel(steel_double);
        registerModel(steel_double_support);

        registerModel(overhead_post);
        registerModel(overhead_post_1);
        registerModel(overhead_steel_post_up);
        registerModel(overhead_web_corner);
        registerModel(overhead_web_pillar);
        registerModel(overhead_web_pillar_post);
        registerModel(overhead_web_pillar_post_1);
        registerModel(overhead_web_pillar_post_2);

        registerModel(signal_post);
        registerModel(signal_none_one_light);
        registerModel(signal_red_one_light);
        registerModel(signal_blue_one_light);
        registerModel(signal_green_one_light);
        registerModel(signal_white_one_light);

        registerModel(signal_none_two_lights);
        registerModel(signal_red_two_lights);
        registerModel(signal_blue_two_lights);
        registerModel(signal_green_two_lights);
        registerModel(signal_white_two_lights);
        registerModel(signal_yellow_two_lights);

        registerModel(signal_none_three_lights);
        registerModel(signal_red_three_lights);
        registerModel(signal_blue_three_lights);
        registerModel(signal_green_three_lights);
        registerModel(signal_white_three_lights);
        registerModel(signal_yellow_three_lights);

        registerModel(signal_red_four_lights);
        registerModel(signal_blue_four_lights);
        registerModel(signal_green_four_lights);
        registerModel(signal_yellow_four_lights);
        
        registerStateMapper(DollanBlocks.beautiful_marble,
                new StateMap.Builder().withSuffix("_marble").withName(BlockMarble.VARIANT).build());
        registerStateMapper(DollanBlocks.cracked_deepslate,
                new StateMap.Builder().withSuffix("_cracked_deepslate").withName(BlockCrackedDeepSlate.VARIANT).build());
        registerStateMapper(DollanBlocks.deepslate,
                new StateMap.Builder().withName(BlockDeepSlate.VARIANT).build());
        registerStateMapper(DollanBlocks.elder_prismarine,
                new StateMap.Builder().withName(BlockElderPrismarine.VARIANT).build());
        registerStateMapper(DollanBlocks.limestone,
                new StateMap.Builder().withName(BlockLimeStone.VARIANT).build());
        //registerStateMapper(DollanBlocks.myalite, new StateMap.Builder().withName(BlockMyalite.VARIANT).build());
        registerStateMapper(DollanBlocks.polished_blackstone,
                new StateMap.Builder().withName(BlockPolishedBlackStone.VARIANT).build());
        registerStateMapper(DollanBlocks.froglight,
                new StateMap.Builder().withName(BlockFrogLight.COLOR).withSuffix("_froglight").build());
        registerStateMapper(DollanBlocks.marble_tile,
                new StateMap.Builder().withName(BlockColored.COLOR).withSuffix("_marble_tile").build());
        registerStateMapper(DollanBlocks.stop_line,
                new StateMap.Builder().withName(StopLineBlock.VARIENT).build());
        registerStateMapper(DollanBlocks.stop_line_slab,
                new StateMap.Builder().withName(StopLineBlockSlab.VARIENT).withSuffix("_slab").build());
        registerStateMapper(DollanBlocks.stop_line_cross,
                new StateMap.Builder().withName(DiagonalStopLineBlock.VARIENT).build());
        registerStateMapper(DollanBlocks.stop_line_cross_slab,
                new StateMap.Builder().withName(DiagonalStopLineBlockSlab.VARIENT).withSuffix("_slab").build());
        registerStateMapper(DollanBlocks.overhead_steel_diagonal_support,
                new StateMap.Builder()
                        .withName(OverheadSteelDiagonalSupport.TYPE)
                        .withSuffix("_overhead_steel_diagonal_support").build()
        );        registerStateMapper(DollanBlocks.overhead_steel_diagonal_support_1,
                new StateMap.Builder()
                        .withName(OverheadSteelDiagonalSupport.TYPE)
                        .withSuffix("_overhead_steel_diagonal_support_1").build()
        );
        registerStateMapper(DollanBlocks.station_end_door, new StateMap.Builder().withName(BlockStationEndDoor.HALF).withSuffix("_station_end_door").ignore(BlockStationEndDoor.POWERED).build());

    }

    private static void registerBlock(net.minecraft.block.Block block) {
        registerBlock(block, new ItemBlock(block));
    }

    private static void registerBlock(Block block, ItemBlock item) {
        RegistrationHandler.BLOCKS.add(block);
        item.setRegistryName(block.getRegistryName());
    }

    @SideOnly(Side.CLIENT)
    private static void registerStateMapper(Block block, IStateMapper mapper)
    {
        ModelLoader.setCustomStateMapper(block, mapper);
    }


    @SideOnly(Side.CLIENT)
    private static void registerModel(net.minecraft.block.Block block, int metadata, String name) {
        ModelResourceLocation model = new ModelResourceLocation(Reference.MOD_ID + ":" + name, "inventory");
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), metadata, model);
    }
    @SideOnly(Side.CLIENT)
    private static void registerModel(net.minecraft.block.Block block, int metadata) {
        ModelResourceLocation model = new ModelResourceLocation(block.getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), metadata, model);
    }

    @SideOnly(Side.CLIENT)
    private static void registerModel(net.minecraft.block.Block block) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }

    @Mod.EventBusSubscriber()
    public static class RegistrationHandler {
        public static final List<net.minecraft.block.Block> BLOCKS = new LinkedList<>();

        @SubscribeEvent
        public static void registerBlock(final RegistryEvent.Register<net.minecraft.block.Block> event) {
            DollanBlocks.register();
            BLOCKS.stream().forEach(block -> event.getRegistry().register(block));
        }
    }

}
