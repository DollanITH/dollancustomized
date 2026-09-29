package com.jhxiaochen.dollancustomized.registry.blocks;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.Reference;
import com.jhxiaochen.dollancustomized.common.block.*;
import com.jhxiaochen.dollancustomized.common.block.light.*;
import com.jhxiaochen.dollancustomized.common.block.metro.*;
import com.jhxiaochen.dollancustomized.common.block.rail.EmptySignBlock;
import com.jhxiaochen.dollancustomized.common.block.rail.RailwaySignPost;
import com.jhxiaochen.dollancustomized.common.block.rail.StationStopSign;
import com.jhxiaochen.dollancustomized.common.block.rail.StationStopWallSign;
import com.jhxiaochen.dollancustomized.common.block.rail.base.TwoBlocksSignBase;
import com.jhxiaochen.dollancustomized.common.block.road.*;
import com.jhxiaochen.dollancustomized.common.block.signpost.*;
import com.jhxiaochen.dollancustomized.common.item.*;
import com.jhxiaochen.dollancustomized.registry.DollanItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.*;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedList;
import java.util.List;

public class DollanBlocks {

    public static final List<Block> BLOCKS = new LinkedList<>();
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
    public static Block bus_lane_mark = new BusLaneMark();
    public static Block bus_lane_mark_slab = new BusLaneMarkSlab();
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
    public static Block railway_sign_post = new RailwaySignPost();
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
    public static Block arrow_left_right = new ArrowNew("arrow_left_right");
    public static Block arrow_left_right_straight = new ArrowNew("arrow_left_right_straight");
    public static Block arrow_left_straight = new ArrowNew("arrow_left_straight");
    public static Block arrow_right_straight = new ArrowNew("arrow_right_straight");
    public static Block arrow_left = new ArrowNew("arrow_left");
    public static Block arrow_right = new ArrowNew("arrow_right");
    public static Block arrow_straight = new ArrowNew("arrow_straight");
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
    public static Block power_method_change_sign = new TwoBlocksSignBase().setRegistryName("power_method_change_sign").setTranslationKey("PowerMethodChangeSign");
    public static Block station_stop_sign = new StationStopSign().setRegistryName("station_stop_sign").setTranslationKey("StationStopSign");
    public static Block station_stop_wall_sign = new StationStopWallSign().setRegistryName("station_stop_wall_sign").setTranslationKey("StationStopSign");
    public static Block thick_white_line_45 = new BlockAsphaltHorizontal("thick_white_line_45");
    public static Block thick_white_line_slab_45 = new BlockAsphaltSlabHorizontal("thick_white_line_slab_45");
    public static Block thick_yellow_line_45 = new BlockAsphaltHorizontal("thick_yellow_line_45");
    public static Block thick_yellow_line_slab_45 = new BlockAsphaltSlabHorizontal("thick_yellow_line_slab_45");
    public static Block platform_wall = new DollanBlock("platform_wall");
    public static Block marble_block = new DollanBlock("marble_block").setHardness(1.5F).setResistance(10.0F);

    public static Block beautiful_marble = new BlockMarble("beautiful_marble");
    public static Block calcite = new Block(Material.ROCK).setTranslationKey("dollancustomized.calcite").setRegistryName(Reference.MOD_ID, "calcite").setHardness(1.5F).setResistance(10.0F);
    public static Block cracked_deepslate = new BlockCrackedDeepSlate("cracked_deepslate");
    public static Block deepslate = new BlockDeepSlate("deepslate");
    public static Block elder_prismarine = new BlockElderPrismarine("elder_prismarine");
    public static Block floorblock = new DollanBlock3("floorblock").setHardness(1.0F);
    public static Block gray_porcelain_tile = new DollanBlock2("gray_porcelain_tile");
    public static Block gray_wall = new DollanBlock2("gray_wall");
    public static Block graymud = new DollanBlock2("graymud");
    public static Block graystoneblock = new DollanBlock2("graystoneblock");
    public static Block limestone = new BlockLimeStone();
    public static Block metal = new DollanBlock4("metal");
    public static Block mud_bricks = new DollanBlock2("mud_bricks");
    //public static Block myalite = new BlockMyalite("myalite");
    public static Block myalite = new Block(Material.ROCK).setRegistryName("myalite").setCreativeTab(DollanCustomized.Blocks).setTranslationKey(Reference.MOD_ID + ".myalite.normal").setHardness(1.0F).setResistance(10F);
    public static Block myalite_crystal = new BlockMyaliteCrystal();
    public static Block myalite_pavement = new Block(Material.ROCK).setRegistryName("myalite_pavement").setCreativeTab(DollanCustomized.Blocks).setTranslationKey(Reference.MOD_ID + ".myalite.pavement").setHardness(1.0F).setResistance(10F);
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

    public static Block signal_post = new SignalPost("signal_post");
    public static Block signal_none_one_light = new SignalLightLower("signal_none_one_light");
    public static Block signal_blue_one_light = new SignalLightLower("signal_blue_one_light");
    public static Block signal_red_one_light = new SignalLightLower("signal_red_one_light");
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
    public static Block deport_stop_sign = new TwoBlocksSignBase().setRegistryName("deport_stop_sign").setTranslationKey("DeportStopSign");
    public static Block empty_railway_sign = new EmptySignBlock().setRegistryName("empty_railway_sign").setTranslationKey("empty_railway_sign");

    public static Block road_signpost = new RoadSignpost();
    public static Block road_signpost_half = new RoadSignpostHalf();
    public static Block road_signpost_pole = new RoadSignpostPole();
    public static Block road_signpost_pole_double = new RoadSignpostPoleDouble();
    public static Block road_signpost_pole_horizontal = new RoadSignpostPoleHorizontal();
    public static Block road_signpost_pole_left = new RoadSignpostPoleLeft();
    public static Block road_signpost_pole_right = new RoadSignpostPoleRight();

    public static Block station_end_door = new BlockStationEndDoor("station_end_door");
    // Initialize metro line blocks
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
    public static Block dollan_line_17 = new BlockMetroBlock("dollan_line_17");
    public static Block dollan_line_18 = new BlockMetroBlock("dollan_line_18");
    public static Block dollan_line_19 = new BlockMetroBlock("dollan_line_19");
    public static Block dollan_line_20 = new BlockMetroBlock("dollan_line_20");
    public static Block dollan_line_21 = new BlockMetroBlock("dollan_line_21");
    public static Block dollan_line_22 = new BlockMetroBlock("dollan_line_22");
    public static Block road_text_mark = new BlockRoadText().setRegistryName("road_text_mark").setTranslationKey("RoadTextMark");
    public static Block road_sign_height_limit_custom = new BlockHeightLimitSignCustom();
    public static Block station_name_board = new BlockStationNameBoard().setRegistryName("station_name_board").setTranslationKey("StationNameBoard");
    public static Block arrow_no_diaotou = new DisableArrowYellow("arrow_no_diaotou");
    public static Block height_limit_3 = new HeightLimitSign("road_sign_height_limit_3");
    public static Block height_limit_4 = new HeightLimitSign("road_sign_height_limit_4");
    public static Block height_limit_5 = new HeightLimitSign("road_sign_height_limit_5");

    public static void registerBlockAndModels() {
        registerSimpleBlock(road_signpost);
        registerSimpleBlock(road_signpost_half);
        registerSimpleBlock(road_signpost_pole);
        registerSimpleBlock(road_signpost_pole_double);
        registerSimpleBlock(road_signpost_pole_horizontal);
        registerSimpleBlock(road_signpost_pole_left);
        registerSimpleBlock(road_signpost_pole_right);
        registerSimpleBlock(road_text_mark);
        registerSimpleBlock(station_name_board);
        registerSimpleBlock(road_give_way_mark);
        registerSimpleBlock(road_slow_down_mark);
        registerSimpleBlock(lamp_tran, new ItemMultiTexture(lamp_tran, lamp_tran, p_apply_1_ -> EnumDyeColor.byMetadata(p_apply_1_.getMetadata()).getTranslationKey()));
        registerSimpleBlock(light, new ItemMultiTexture(light, light, p_apply_1_ -> EnumDyeColor.byMetadata(p_apply_1_.getMetadata()).getTranslationKey()));
        registerSimpleBlock(metro_fence, new ItemMultiTexture(metro_fence, metro_fence, p_apply_1_ -> BlockMetroFence.FenceType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName()));
        registerSimpleBlock(station_end_door, null);
        registerSimpleBlock(railway_sign_post);
        registerSimpleBlock(power_method_change_sign, new ItemRailwaySign(power_method_change_sign));
        registerSimpleBlock(deport_stop_sign, new ItemRailwaySign(deport_stop_sign));
        registerSimpleBlock(empty_railway_sign, new ItemMultiTexture(empty_railway_sign, empty_railway_sign , stack -> EmptySignBlock.SignNone.byIndex(stack.getMetadata()).getName()));
        registerSimpleBlock(station_stop_sign);
        registerSimpleBlock(arrow_confluence_left, new ItemRoadMarkArrow(arrow_confluence_left));
        registerSimpleBlock(arrow_confluence_right, new ItemRoadMarkArrow(arrow_confluence_right));
        registerSimpleBlock(station_stop_wall_sign, new ItemStationStopWallSign());
        registerSimpleBlock(bus_lane_mark, new ItemBusLaneMark());
        registerSimpleBlock(bus_lane_mark_slab, null);

        registerSimpleBlock(road_sign_left_warn);
        registerSimpleBlock(road_sign_right_warn);
        registerSimpleBlock(road_sign_left_right_warn);
        registerSimpleBlock(road_sign_height_limit_custom);
        registerSimpleBlock(ms_lines, new ItemMultiTexture(ms_lines, ms_lines, p_apply_1_ -> RoadMergeOtherLine.EnumVariant.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName()));
        registerSimpleBlock(ms_lines_slab, new ItemMultiTexture(ms_lines_slab, ms_lines_slab, p_apply_1_ -> RoadMergeOtherLine.EnumVariant.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName()));

        registerSimpleBlock(road_sign_one_way_straight);

        registerSimpleBlock(double_white_line_half);
        registerSimpleBlock(double_white_line_half_slab);
        registerSimpleBlock(double_yellow_line_half);
        registerSimpleBlock(double_yellow_line_half_slab);
        registerSimpleBlock(crosswalk_diagonal);
        registerSimpleBlock(crosswalk_diagonal_slab);
        registerSimpleBlock(dollan_airport_line);
        registerSimpleBlock(dollan_s1);
        registerSimpleBlock(dollan_s2);
        registerSimpleBlock(road_excl_zone_corner_out);
        registerSimpleBlock(road_excl_zone_corner_out_slab);
        registerSimpleBlock(road_excl_zone_corner_in);
        registerSimpleBlock(road_excl_zone_corner_in_slab);
        registerSimpleBlock(platform_fence);
        registerSimpleBlock(dollan_line_1);
        registerSimpleBlock(dollan_line_2);
        registerSimpleBlock(dollan_line_3);
        registerSimpleBlock(dollan_line_4);
        registerSimpleBlock(dollan_line_5);
        registerSimpleBlock(dollan_line_6);
        registerSimpleBlock(dollan_line_7);
        registerSimpleBlock(dollan_line_8);
        registerSimpleBlock(dollan_line_9);
        registerSimpleBlock(dollan_line_10);
        registerSimpleBlock(dollan_line_11);
        registerSimpleBlock(dollan_line_12);
        registerSimpleBlock(dollan_line_13);
        registerSimpleBlock(dollan_line_14);
        registerSimpleBlock(dollan_line_15);
        registerSimpleBlock(dollan_line_16);
        registerSimpleBlock(dollan_line_17);
        registerSimpleBlock(dollan_line_18);
        registerSimpleBlock(dollan_line_19);
        registerSimpleBlock(dollan_line_20);
        registerSimpleBlock(dollan_line_21);
        registerSimpleBlock(dollan_line_22);
        registerSimpleBlock(mingsha_S1);
        registerSimpleBlock(mingsha_S2);
        registerSimpleBlock(mingsha_S3);
        registerSimpleBlock(mingsha_S4);
        registerSimpleBlock(mingsha_S5);
        registerSimpleBlock(mingsha_S6);
        registerSimpleBlock(asphalt_red_black_half_45);
        registerSimpleBlock(asphalt_red_black_half_45_slab);
        registerSimpleBlock(asphalt_cyan_black_half_45);
        registerSimpleBlock(asphalt_cyan_black_half_45_slab);
        registerSimpleBlock(asphalt, new ItemMultiTexture(asphalt, asphalt, p_apply_1_ -> BlockAsphalt.AsphaltType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName()));
        registerSimpleBlock(asphalt_slab, new ItemMultiTexture(asphalt_slab, asphalt_slab, p_apply_1_ -> BlockAsphalt.AsphaltType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName()));
        registerSimpleBlock(road_sign_dont_stop);
        registerSimpleBlock(road_sign_dont_stop_1);
        registerSimpleBlock(road_sign_highway_start);
        registerSimpleBlock(road_sign_highway_end);
        registerSimpleBlock(road_sign_park);
        registerSimpleBlock(road_sign_park_1);
        registerSimpleBlock(single_white_90);
        registerSimpleBlock(single_white_90_1);
        registerSimpleBlock(single_white_all_line);
        registerSimpleBlock(single_white_90_slab);
        registerSimpleBlock(single_white_90_1_slab);
        registerSimpleBlock(single_white_all_line_slab);
        registerSimpleBlock(single_yellow_90);
        registerSimpleBlock(single_yellow_90_1);
        registerSimpleBlock(single_yellow_all_line);
        registerSimpleBlock(single_yellow_90_slab);
        registerSimpleBlock(single_yellow_90_1_slab);
        registerSimpleBlock(single_yellow_all_line_slab);
        registerSimpleBlock(road_sign_triangle_warn);
        registerSimpleBlock(road_sign_triangle_t);
        registerSimpleBlock(road_sign_triangle_t1);
        registerSimpleBlock(road_sign_triangle_t2);
        registerSimpleBlock(road_sign_triangle_shizi);
        registerSimpleBlock(road_sign_triangle_y);
        registerSimpleBlock(road_sign_triangle_rang);
        registerSimpleBlock(road_sign_triangle_slow);
        registerSimpleBlock(road_sign_go_right);
        registerSimpleBlock(road_sign_go_left);
        registerSimpleBlock(road_sign_go_both);
        registerSimpleBlock(road_sign_do_not_enter);
        registerSimpleBlock(road_traffic_light);
        registerSimpleBlock(road_traffic_light_red);
        registerSimpleBlock(road_traffic_light_green);
        registerSimpleBlock(road_traffic_light_yellow);
        registerSimpleBlock(road_pedestrian_traffic_light);
        registerSimpleBlock(road_sign_post);
        registerSimpleBlock(double_yellow_line_diagonal);
        registerSimpleBlock(double_yellow_line_diagonal_slab);
        registerSimpleBlock(double_white_line_diagonal);
        registerSimpleBlock(double_white_line_diagonal_slab);
        registerSimpleBlock(bike_words, new ItemRoadMarkArrow(bike_words));
        registerSimpleBlock(double_white_line);
        registerSimpleBlock(double_white_line_slab);
        registerSimpleBlock(double_yellow_line);
        registerSimpleBlock(double_yellow_line_slab);
        registerSimpleBlock(single_yellow_line);
        registerSimpleBlock(single_yellow_line_slab);
        registerSimpleBlock(single_white_line);
        registerSimpleBlock(single_white_line_slab);
        registerSimpleBlock(single_white_line_45);
        registerSimpleBlock(single_white_line_slab_45);
        registerSimpleBlock(single_yellow_line_45);
        registerSimpleBlock(single_yellow_line_slab_45);
        registerSimpleBlock(thick_white_line_45);
        registerSimpleBlock(thick_white_line_slab_45);
        registerSimpleBlock(thick_yellow_line_45);
        registerSimpleBlock(thick_yellow_line_slab_45);
        registerSimpleBlock(crosswalk);
        registerSimpleBlock(crosswalk_slab);
        registerSimpleBlock(stop_line, new ItemMultiTexture(stop_line, stop_line, p_apply_1_ -> StopLineBlock.EnumVarient.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName()));
        registerSimpleBlock(stop_line_slab, new ItemMultiTexture(stop_line_slab, stop_line_slab, p_apply_1_ -> StopLineBlockSlab.EnumVarient.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName()));
        registerSimpleBlock(stop_line_cross, new ItemMultiTexture(stop_line_cross, stop_line_cross, p_apply_1_ -> DiagonalStopLineBlock.EnumVarient.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName()));
        registerSimpleBlock(stop_line_cross_slab, new ItemMultiTexture(stop_line_cross_slab, stop_line_cross_slab, p_apply_1_ -> DiagonalStopLineBlockSlab.EnumVarient.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName()));
        registerSimpleBlock(stop_line_left);
        registerSimpleBlock(stop_line_left_slab);
        registerSimpleBlock(stop_line_right_slab);
        registerSimpleBlock(stop_line_right_1_slab);
        registerSimpleBlock(stop_line_right);
        registerSimpleBlock(stop_line_right_1);
        registerSimpleBlock(single_white_line_thick);
        registerSimpleBlock(single_white_line_thick_slab);
        registerSimpleBlock(single_yellow_line_thick);
        registerSimpleBlock(single_yellow_line_thick_slab);
        registerSimpleBlock(road_excl_zone_diagonal_in);
        registerSimpleBlock(road_excl_zone_diagonal_in_slab);
        registerSimpleBlock(road_excl_zone_diagonal_out);
        registerSimpleBlock(road_excl_zone_diagonal_out_slab);
        registerSimpleBlock(road_excl_zone);
        registerSimpleBlock(road_excl_zone_slab);
        registerSimpleBlock(road_excl_zone_line);
        registerSimpleBlock(road_excl_zone_line_slab);
        registerSimpleBlock(road_excl_zone_split_our_l);
        registerSimpleBlock(road_excl_zone_split_our_l_slab);
        registerSimpleBlock(road_excl_zone_split_our_r);
        registerSimpleBlock(road_excl_zone_split_our_r_slab);
        registerSimpleBlock(road_excl_zone_split_in_l);
        registerSimpleBlock(road_excl_zone_split_in_l_slab);
        registerSimpleBlock(road_excl_zone_split_in_r);
        registerSimpleBlock(road_excl_zone_split_in_r_slab);
        registerSimpleBlock(road_lamp_1_bottom);
        registerSimpleBlock(road_lamp_1_up);
        registerSimpleBlock(white_flat_lamp);
        registerSimpleBlock(white_punctiform_lamp);
        registerSimpleBlock(white_banding_lamp);

        registerSimpleBlock(asphalt_cyan_black_half_line);
        registerSimpleBlock(asphalt_cyan_black_half_line_slab);
        registerSimpleBlock(asphalt_cyan_black_half);
        registerSimpleBlock(asphalt_cyan_black_half_slab);
        registerSimpleBlock(asphalt_red_black_half_slab);
        registerSimpleBlock(asphalt_red_black_half);
        registerSimpleBlock(asphalt_red_black_half_line);
        registerSimpleBlock(asphalt_red_black_half_line_slab);
        registerSimpleBlock(asphalt_cyan_black_half_line_diagonal);
        registerSimpleBlock(asphalt_cyan_black_half_line_diagonal_slab);
        registerSimpleBlock(asphalt_red_black_half_line_diagonal_slab);
        registerSimpleBlock(asphalt_red_black_half_line_diagonal);
        registerSimpleBlock(asphalt_cyan_black_half_line_diagonal_1);
        registerSimpleBlock(asphalt_cyan_black_half_line_diagonal_1_slab);
        registerSimpleBlock(asphalt_red_black_half_line_diagonal_1_slab);
        registerSimpleBlock(asphalt_red_black_half_line_diagonal_1);
        registerSimpleBlock(arrow_no_diaotou);
        registerSimpleBlock(arrow_diaotou, new ItemRoadMarkArrow(arrow_diaotou));
        registerSimpleBlock(arrow_left_diaotou, new ItemRoadMarkArrow(arrow_left_diaotou));
        registerSimpleBlock(arrow_left_right, new ItemRoadMarkArrow(arrow_left_right));
        registerSimpleBlock(arrow_left_right_straight, new ItemRoadMarkArrow(arrow_left_right_straight));
        registerSimpleBlock(arrow_left_straight, new ItemRoadMarkArrow(arrow_left_straight));
        registerSimpleBlock(arrow_right_straight, new ItemRoadMarkArrow(arrow_right_straight));
        registerSimpleBlock(arrow_left, new ItemRoadMarkArrow(arrow_left));
        registerSimpleBlock(arrow_right, new ItemRoadMarkArrow(arrow_right));
        registerSimpleBlock(arrow_straight, new ItemRoadMarkArrow(arrow_straight));
        registerSimpleBlock(road_sign_sl_15);
        registerSimpleBlock(road_sign_sl_20);
        registerSimpleBlock(road_sign_sl_30);
        registerSimpleBlock(road_sign_sl_40);
        registerSimpleBlock(road_sign_sl_50);
        registerSimpleBlock(road_sign_sl_60);
        registerSimpleBlock(road_sign_sl_70);
        registerSimpleBlock(road_sign_sl_80);
        registerSimpleBlock(road_sign_sl_90);
        registerSimpleBlock(road_sign_sl_100);
        registerSimpleBlock(road_sign_sl_110);
        registerSimpleBlock(road_sign_sl_120);
        registerSimpleBlock(road_sign_do_not_enter_1);
        registerSimpleBlock(road_sign_do_not_left);
        registerSimpleBlock(road_sign_do_not_right);
        registerSimpleBlock(road_sign_do_not_diaotou);
        registerSimpleBlock(road_sign_can_left);
        registerSimpleBlock(road_sign_can_right);
        registerSimpleBlock(road_sign_can_diaotou);


        registerSimpleBlock(arrow_straight_diaotou, new ItemRoadMarkArrow(arrow_straight_diaotou));
        registerSimpleBlock(platform_wall);
        registerSimpleBlock(marble_block);

        registerSimpleBlock(dollan_loop_line);

        if (!Loader.isModLoaded("depthsupdate")) {
            registerSimpleBlock(calcite);
            registerSimpleBlock(polished_deepslate);
            registerSimpleBlock(graystoneblock);
        }
        registerSimpleBlock(floorblock);
        registerSimpleBlock(gray_porcelain_tile);
        registerSimpleBlock(gray_wall);
        registerSimpleBlock(graymud);
        registerSimpleBlock(metal);
        registerSimpleBlock(mud_bricks);
        registerSimpleBlock(myalite);
        registerSimpleBlock(myalite_crystal);
        registerSimpleBlock(myalite_pavement);
        registerSimpleBlock(packed_mud);
        registerSimpleBlock(polished_limestone);
        registerSimpleBlock(polished_marble);
        registerSimpleBlock(polished_myalite);
        registerSimpleBlock(quartz_bricks);
        registerSimpleBlock(rock_1);
        registerSimpleBlock(rock_2);
        registerSimpleBlock(sandy_bricks);
        registerSimpleBlock(teacherblock);
        registerSimpleBlock(white_color_purple_block);
        registerSimpleBlock(elder_sea_lantern);
        registerSimpleBlock(modern_stone_tile);

        registerSimpleBlock(overhead_lines);
        registerSimpleBlock(overhead_lines_slope);
        registerSimpleBlock(overhead_lines_slope_1);
        registerSimpleBlock(overhead_lines_slope_2);
        registerSimpleBlock(overhead_lines_diagonal);
        registerSimpleBlock(overhead_lines_straighter);
        registerSimpleBlock(overhead_lines_diagonal_straighter);
        registerSimpleBlock(overhead_lines_support);
        registerSimpleBlock(overhead_lines_support_a1);
        registerSimpleBlock(overhead_lines_support_a2);
        registerSimpleBlock(overhead_lines_support_a3);
        registerSimpleBlock(overhead_lines_support_a4);
        registerSimpleBlock(overhead_lines_double_diagonal);
        registerSimpleBlock(overhead_lines_double_diagonal_1);
        registerSimpleBlock(overhead_lines_double_wye);

        registerSimpleBlock(metro_tran0);
        registerSimpleBlock(metro_tran1);
        registerSimpleBlock(metro_tran2);
        registerSimpleBlock(metro_tran3);
        registerSimpleBlock(metro_tran4);
        registerSimpleBlock(metro_tran5);
        registerSimpleBlock(metro_tran6);
        registerSimpleBlock(metro_tran7);

        registerSimpleBlock(overhead_steel);
        registerSimpleBlock(overhead_steel_slope);
        registerSimpleBlock(overhead_steel_support);
        registerSimpleBlock(overhead_steel_diagonal, new ItemOverheadSteelDiagonal(overhead_steel_diagonal));
        registerSimpleBlock(overhead_steel_diagonal_support, new ItemOverheadSteelDiagonalSupport(overhead_steel_diagonal_support));
        registerSimpleBlock(overhead_steel_diagonal_support_1, new ItemOverheadSteelDiagonalSupport(overhead_steel_diagonal_support_1));
        registerSimpleBlock(overhead_steel_support_1);
        registerSimpleBlock(overhead_steel_double_diagonal);
        registerSimpleBlock(overhead_steel_double_diagonal_1);
        registerSimpleBlock(overhead_steel_double_wye);
        registerSimpleBlock(overhead_steel_double_wye_1);
        registerSimpleBlock(steel_double);
        registerSimpleBlock(steel_double_support);


        registerSimpleBlock(overhead_post);
        registerSimpleBlock(overhead_post_1);
        registerSimpleBlock(overhead_steel_post_up);
        registerSimpleBlock(overhead_web_corner);
        registerSimpleBlock(overhead_web_pillar);
        registerSimpleBlock(overhead_web_pillar_post);
        registerSimpleBlock(overhead_web_pillar_post_1);
        registerSimpleBlock(overhead_web_pillar_post_2);

        registerSimpleBlock(signal_post);
        registerSimpleBlock(signal_none_one_light);
        registerSimpleBlock(signal_red_one_light);
        registerSimpleBlock(signal_blue_one_light);
        registerSimpleBlock(signal_green_one_light);
        registerSimpleBlock(signal_white_one_light);

        registerSimpleBlock(signal_none_two_lights);
        registerSimpleBlock(signal_red_two_lights);
        registerSimpleBlock(signal_blue_two_lights);
        registerSimpleBlock(signal_green_two_lights);
        registerSimpleBlock(signal_white_two_lights);
        registerSimpleBlock(signal_yellow_two_lights);

        registerSimpleBlock(signal_none_three_lights);
        registerSimpleBlock(signal_red_three_lights);
        registerSimpleBlock(signal_blue_three_lights);
        registerSimpleBlock(signal_green_three_lights);
        registerSimpleBlock(signal_white_three_lights);
        registerSimpleBlock(signal_yellow_three_lights);

        registerSimpleBlock(signal_red_four_lights);
        registerSimpleBlock(signal_blue_four_lights);
        registerSimpleBlock(signal_green_four_lights);
        registerSimpleBlock(signal_yellow_four_lights);
        registerSimpleBlock(beautiful_marble, new ItemMultiTexture(beautiful_marble, beautiful_marble, p_apply_1_ -> BlockMarble.MarbleType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName()));
        if (!Loader.isModLoaded("depthsupdate")) {
            registerSimpleBlock(cracked_deepslate, new ItemMultiTexture(cracked_deepslate, cracked_deepslate, p_apply_1_ -> BlockCrackedDeepSlate.DeepslateType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName()));
            registerSimpleBlock(deepslate, new ItemMultiTexture(deepslate, deepslate, p_apply_1_ -> BlockDeepSlate.DeepslateType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName()));
        }
        registerSimpleBlock(elder_prismarine, new ItemMultiTexture(elder_prismarine, elder_prismarine, new ItemMultiTexture.Mapper() {
            public String apply(ItemStack p_apply_1_) {
                return BlockElderPrismarine.PrismarineType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
            }
        }));
        registerSimpleBlock(limestone, new ItemMultiTexture(limestone, limestone, new ItemMultiTexture.Mapper() {
            public String apply(ItemStack p_apply_1_) {
                return BlockLimeStone.LimeStoneType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
            }
        }));
        registerSimpleBlock(polished_blackstone, new ItemMultiTexture(polished_blackstone, polished_blackstone, new ItemMultiTexture.Mapper() {
            public String apply(ItemStack p_apply_1_) {
                return BlockPolishedBlackStone.BlackStoneType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
            }
        }));
        registerSimpleBlock(froglight, new ItemMultiTexture(froglight, froglight, new ItemMultiTexture.Mapper() {
            public String apply(ItemStack p_apply_1_) {
                return BlockFrogLight.FrogLightType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
            }
        }));
        registerSimpleBlock(marble_tile, new ItemMultiTexture(marble_tile, marble_tile, new ItemMultiTexture.Mapper() {
            public String apply(ItemStack p_apply_1_) {
                return EnumDyeColor.byMetadata(p_apply_1_.getMetadata()).getTranslationKey();
            }
        }));

        registerSimpleBlock(height_limit_3, new ItemMultiTexture(height_limit_3, height_limit_3, stack -> {
            HeightLimitSign.EnumHeight type = HeightLimitSign.EnumHeight.byMetadata(stack.getMetadata());
            return type.getUnlocalizedName();
        }));
        registerSimpleBlock(height_limit_4, new ItemMultiTexture(height_limit_4, height_limit_4, stack -> {
            HeightLimitSign.EnumHeight type = HeightLimitSign.EnumHeight.byMetadata(stack.getMetadata());
            return type.getUnlocalizedName();
        }));
        registerSimpleBlock(height_limit_5, new ItemMultiTexture(height_limit_5, height_limit_5, stack -> {
            HeightLimitSign.EnumHeight type = HeightLimitSign.EnumHeight.byMetadata(stack.getMetadata());
            return type.getUnlocalizedName();
        }));
    }

    public static void registerSimpleBlock(Block block, Item itemBlock) {
        boolean flag = itemBlock instanceof ItemBlock && !(itemBlock instanceof ItemMultiTexture);
        BLOCKS.add(block);
        if (itemBlock != null) {
            if (itemBlock.getRegistryName() == null && block.getRegistryName() != null) {
                itemBlock.setRegistryName(block.getRegistryName());
            }
        DollanItems.ITEMS.add(itemBlock);
        }
        if (flag || itemBlock instanceof ItemRoadMarkArrow) {
            registerModel(block);
        }

    }

    /**
     * 注册所有自定义模型
     * 将所有 registerCustomModel 调用整合到这个方法中
     */
    @SideOnly(Side.CLIENT)
    public static void registerAllCustomModels() {

        registerCustomModel(asphalt, BlockAsphalt.AsphaltType.BLACK.getMetadata(), "black_asphalt");
        registerCustomModel(asphalt, BlockAsphalt.AsphaltType.CYAN.getMetadata(), "cyan_asphalt");
        registerCustomModel(asphalt, BlockAsphalt.AsphaltType.RED.getMetadata(), "red_asphalt");
        registerCustomModel(asphalt, BlockAsphalt.AsphaltType.WHITE.getMetadata(), "white_asphalt");
        registerCustomModel(asphalt, BlockAsphalt.AsphaltType.YELLOW.getMetadata(), "yellow_asphalt");
        registerCustomModel(asphalt_slab, BlockAsphalt.AsphaltType.BLACK.getMetadata(), "black_asphalt_slab");
        registerCustomModel(asphalt_slab, BlockAsphalt.AsphaltType.CYAN.getMetadata(), "cyan_asphalt_slab");
        registerCustomModel(asphalt_slab, BlockAsphalt.AsphaltType.RED.getMetadata(), "red_asphalt_slab");

        // 道路标志和标记的模型注册
        registerCustomModel(station_stop_wall_sign, 0, "station_stop_sign_wall_single");
        registerCustomModel(station_stop_wall_sign, 1, "station_stop_sign_wall");
        registerCustomModel(station_stop_wall_sign, 2, "station_stop_sign_wall_single_1");
        registerCustomModel(station_stop_wall_sign, 3, "station_stop_sign_wall_1");

        // 公交车道标记的模型注册
        for (BusLaneMark.EnumType subtype : BusLaneMark.EnumType.values()) {
            registerCustomModel(bus_lane_mark, subtype.getMetadata(), "bus_lane_" + subtype.getName());
        }

        // 地铁围栏的模型注册
        registerCustomModel(metro_fence, BlockMetroFence.FenceType.METRO.getMetadata(), "metro_fence");
        registerCustomModel(metro_fence, BlockMetroFence.FenceType.ROAD_CENTER.getMetadata(), "road_center_fence");
        registerCustomModel(metro_fence, BlockMetroFence.FenceType.ROAD.getMetadata(), "road_fence");

        // 沥青路面和路板的模型注册
        
        // 交通灯的模型注册
        registerCustomModel(lamp_tran, EnumDyeColor.WHITE.getMetadata(), "lamp_tran_0");
        registerCustomModel(lamp_tran, EnumDyeColor.BLACK.getMetadata(), "lamp_tran_1");
        registerCustomModel(lamp_tran, EnumDyeColor.YELLOW.getMetadata(), "lamp_tran_2");
        registerCustomModel(lamp_tran, EnumDyeColor.RED.getMetadata(), "lamp_tran_3");
        registerCustomModel(lamp_tran, EnumDyeColor.GREEN.getMetadata(), "lamp_tran_4");
        registerCustomModel(lamp_tran, EnumDyeColor.BLUE.getMetadata(), "lamp_tran_blue");
        registerCustomModel(lamp_tran, EnumDyeColor.BROWN.getMetadata(), "lamp_tran_brown");
        registerCustomModel(lamp_tran, EnumDyeColor.GRAY.getMetadata(), "lamp_tran_gray");
        registerCustomModel(lamp_tran, EnumDyeColor.LIGHT_BLUE.getMetadata(), "lamp_tran_light_blue");
        registerCustomModel(lamp_tran, EnumDyeColor.LIME.getMetadata(), "lamp_tran_lime");
        registerCustomModel(lamp_tran, EnumDyeColor.SILVER.getMetadata(), "lamp_tran_silver");
        registerCustomModel(lamp_tran, EnumDyeColor.MAGENTA.getMetadata(), "lamp_tran_magenta");
        registerCustomModel(lamp_tran, EnumDyeColor.PURPLE.getMetadata(), "lamp_tran_purple");
        registerCustomModel(lamp_tran, EnumDyeColor.ORANGE.getMetadata(), "lamp_tran_orange");
        registerCustomModel(lamp_tran, EnumDyeColor.PINK.getMetadata(), "lamp_tran_pink");
        registerCustomModel(lamp_tran, EnumDyeColor.CYAN.getMetadata(), "lamp_tran_cyan");

        // 普通路灯的模型注册
        registerCustomModel(light, EnumDyeColor.WHITE.getMetadata(), "lamp_white");
        registerCustomModel(light, EnumDyeColor.BLACK.getMetadata(), "lamp_black");
        registerCustomModel(light, EnumDyeColor.YELLOW.getMetadata(), "lamp_yellow");
        registerCustomModel(light, EnumDyeColor.RED.getMetadata(), "lamp_red");
        registerCustomModel(light, EnumDyeColor.GREEN.getMetadata(), "lamp_green");
        registerCustomModel(light, EnumDyeColor.BLUE.getMetadata(), "lamp_blue");
        registerCustomModel(light, EnumDyeColor.BROWN.getMetadata(), "lamp_brown");
        registerCustomModel(light, EnumDyeColor.GRAY.getMetadata(), "lamp_gray");
        registerCustomModel(light, EnumDyeColor.LIGHT_BLUE.getMetadata(), "lamp_light_blue");
        registerCustomModel(light, EnumDyeColor.LIME.getMetadata(), "lamp_lime");
        registerCustomModel(light, EnumDyeColor.SILVER.getMetadata(), "lamp_silver");
        registerCustomModel(light, EnumDyeColor.MAGENTA.getMetadata(), "lamp_magenta");
        registerCustomModel(light, EnumDyeColor.PURPLE.getMetadata(), "lamp_purple");
        registerCustomModel(light, EnumDyeColor.ORANGE.getMetadata(), "lamp_orange");
        registerCustomModel(light, EnumDyeColor.PINK.getMetadata(), "lamp_pink");
        registerCustomModel(light, EnumDyeColor.CYAN.getMetadata(), "lamp_cyan");

        // 地铁线路和路板合并/分离线的模型注册
        registerCustomModel(ms_lines, RoadMergeOtherLine.EnumVariant.MERGE_WHITE.getMetadata(), "merge_line");
        registerCustomModel(ms_lines, RoadMergeOtherLine.EnumVariant.MERGE_YELLOW.getMetadata(), "merge_line_yellow");
        registerCustomModel(ms_lines, RoadMergeOtherLine.EnumVariant.SEPARATE_WHITE.getMetadata(), "separate_line");
        registerCustomModel(ms_lines, RoadMergeOtherLine.EnumVariant.SEPARATE_YELLOW.getMetadata(), "separate_line_yellow");
        registerCustomModel(ms_lines_slab, RoadMergeOtherLineSlab.EnumVariant.MERGE_WHITE.getMetadata(), "merge_line_slab");
        registerCustomModel(ms_lines_slab, RoadMergeOtherLineSlab.EnumVariant.MERGE_YELLOW.getMetadata(), "merge_line_yellow_slab");
        registerCustomModel(ms_lines_slab, RoadMergeOtherLineSlab.EnumVariant.SEPARATE_WHITE.getMetadata(), "separate_line_slab");
        registerCustomModel(ms_lines_slab, RoadMergeOtherLineSlab.EnumVariant.SEPARATE_YELLOW.getMetadata(), "separate_line_yellow_slab");

        // 大理石的模型注册
        registerCustomModel(beautiful_marble, 0, "normal_marble");
        registerCustomModel(beautiful_marble, 1, "bricks_marble");
        registerCustomModel(beautiful_marble, 2, "cut_marble");
        registerCustomModel(beautiful_marble, 3, "litcut_marble");
        registerCustomModel(beautiful_marble, 4, "smooth_marble");

        // 深板岩的模型注册（条件）
        if (!Loader.isModLoaded("depthsupdate")) {
            registerCustomModel(cracked_deepslate, 0, "bricks_cracked_deepslate");
            registerCustomModel(cracked_deepslate, 1, "tiles_cracked_deepslate");
            registerCustomModel(deepslate, 0, "deepslate");
            registerCustomModel(deepslate, 1, "deepslate_bricks");
            registerCustomModel(deepslate, 2, "deepslate_tiles");
        }

        // 老海晶石的模型注册
        registerCustomModel(elder_prismarine, 0, BlockElderPrismarine.PrismarineType.NORMAL.getName());
        registerCustomModel(elder_prismarine, 1, BlockElderPrismarine.PrismarineType.BRICKS.getName());
        registerCustomModel(elder_prismarine, 2, BlockElderPrismarine.PrismarineType.SMOOTH.getName());

        // 石灰石的模型注册
        registerCustomModel(limestone, 0, BlockLimeStone.LimeStoneType.NORMAL.getName());
        registerCustomModel(limestone, 1, BlockLimeStone.LimeStoneType.BRICKS.getName());
        registerCustomModel(limestone, 2, BlockLimeStone.LimeStoneType.PAVEMENT.getName());

        // 黑曜石的模型注册
        registerCustomModel(polished_blackstone, 0, BlockPolishedBlackStone.BlackStoneType.NORMAL.getName());
        registerCustomModel(polished_blackstone, 1, BlockPolishedBlackStone.BlackStoneType.BRICKS.getName());

        // 矮光石的模型注册
        registerCustomModel(froglight, 0, BlockFrogLight.FrogLightType.PURPLE.getName() + "_froglight");
        registerCustomModel(froglight, 1, BlockFrogLight.FrogLightType.ORANGE.getName() + "_froglight");
        registerCustomModel(froglight, 2, BlockFrogLight.FrogLightType.GREEN.getName() + "_froglight");

        // 大理地砖的模型注册
        registerCustomModel(marble_tile, EnumDyeColor.WHITE.getMetadata(), "white_marble_tile");
        registerCustomModel(marble_tile, EnumDyeColor.BLACK.getMetadata(), "black_marble_tile");
        registerCustomModel(marble_tile, EnumDyeColor.YELLOW.getMetadata(), "yellow_marble_tile");
        registerCustomModel(marble_tile, EnumDyeColor.RED.getMetadata(), "red_marble_tile");
        registerCustomModel(marble_tile, EnumDyeColor.GREEN.getMetadata(), "green_marble_tile");
        registerCustomModel(marble_tile, EnumDyeColor.BLUE.getMetadata(), "blue_marble_tile");
        registerCustomModel(marble_tile, EnumDyeColor.BROWN.getMetadata(), "brown_marble_tile");
        registerCustomModel(marble_tile, EnumDyeColor.GRAY.getMetadata(), "gray_marble_tile");
        registerCustomModel(marble_tile, EnumDyeColor.LIGHT_BLUE.getMetadata(), "light_blue_marble_tile");
        registerCustomModel(marble_tile, EnumDyeColor.LIME.getMetadata(), "lime_marble_tile");
        registerCustomModel(marble_tile, EnumDyeColor.SILVER.getMetadata(), "silver_marble_tile");
        registerCustomModel(marble_tile, EnumDyeColor.MAGENTA.getMetadata(), "magenta_marble_tile");
        registerCustomModel(marble_tile, EnumDyeColor.PURPLE.getMetadata(), "purple_marble_tile");
        registerCustomModel(marble_tile, EnumDyeColor.ORANGE.getMetadata(), "orange_marble_tile");
        registerCustomModel(marble_tile, EnumDyeColor.PINK.getMetadata(), "pink_marble_tile");
        registerCustomModel(marble_tile, EnumDyeColor.CYAN.getMetadata(), "cyan_marble_tile");

        // 架空线路支柱的模型注册
        registerCustomModel(overhead_steel_diagonal, 1, "overhead_steel_diagonal");
        registerCustomModel(overhead_steel_diagonal, 0, "overhead_steel_diagonal_1");
        registerCustomModel(overhead_steel_diagonal, 2, "overhead_steel_diagonal_2");
        registerCustomModel(overhead_steel_diagonal_support, 0, "overhead_steel_diagonal_support");
        registerCustomModel(overhead_steel_diagonal_support, 1, "overhead_steel_diagonal_support_2");
        registerCustomModel(overhead_steel_diagonal_support, 2, "overhead_steel_diagonal_support_4");
        registerCustomModel(overhead_steel_diagonal_support_1, 0, "overhead_steel_diagonal_support_1");
        registerCustomModel(overhead_steel_diagonal_support_1, 1, "overhead_steel_diagonal_support_3");
        registerCustomModel(overhead_steel_diagonal_support_1, 2, "overhead_steel_diagonal_support_5");

        // 停车线地砖的模型注册
        registerCustomModel(stop_line, 0, "stop_line");
        registerCustomModel(stop_line, 1, "road_stop_diagonal_1");
        registerCustomModel(stop_line, 2, "road_stop_diagonal");
        registerCustomModel(stop_line_slab, 0, "stop_line_slab");
        registerCustomModel(stop_line_slab, 2, "road_stop_diagonal_slab");
        registerCustomModel(stop_line_slab, 1, "road_stop_diagonal_1_slab");
        registerCustomModel(stop_line_cross, 0, "road_stop_diagonal_white_line");
        registerCustomModel(stop_line_cross, 1, "road_stop_diagonal_white_line_1");
        registerCustomModel(stop_line_cross, 2, "road_stop_diagonal_yellow_line");
        registerCustomModel(stop_line_cross, 3, "road_stop_diagonal_yellow_line_1");
        registerCustomModel(stop_line_cross_slab, 0, "road_stop_diagonal_white_line_slab");
        registerCustomModel(stop_line_cross_slab, 1, "road_stop_diagonal_white_line_1_slab");
        registerCustomModel(stop_line_cross_slab, 2, "road_stop_diagonal_yellow_line_slab");
        registerCustomModel(stop_line_cross_slab, 3, "road_stop_diagonal_yellow_line_1_slab");

        // 限高标志的模型注册
        registerCustomModel(height_limit_3, 0, "road_sign_height_limit_3_subtype_1");
        registerCustomModel(height_limit_3, 1, "road_sign_height_limit_3_subtype_2");
        registerCustomModel(height_limit_4, 0, "road_sign_height_limit_4_subtype_1");
        registerCustomModel(height_limit_4, 1, "road_sign_height_limit_4_subtype_2");
        registerCustomModel(height_limit_5, 0, "road_sign_height_limit_5_subtype_1");
        registerCustomModel(height_limit_5, 1, "road_sign_height_limit_5_subtype_2");

        registerCustomModel(empty_railway_sign, 0, "sign_none");
        registerCustomModel(empty_railway_sign, 4, "sign_none_1");

    }

    public static void registerSimpleBlock(Block block) {
        registerSimpleBlock(block, new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }

    @SideOnly(Side.CLIENT)
    private static void registerCustomModel(net.minecraft.block.Block block, int metadata, String name) {
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            ModelResourceLocation model = new ModelResourceLocation(Reference.MOD_ID + ":" + name, "inventory");
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), metadata, model);
        }
    }

    private static void registerModel(net.minecraft.block.Block block) {
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            registerCustomModel(block, 0, block.getRegistryName().getPath());
        }
    }

}
