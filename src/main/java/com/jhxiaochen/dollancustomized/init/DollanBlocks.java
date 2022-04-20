package com.jhxiaochen.dollancustomized.init;

import com.jhxiaochen.dollancustomized.DollanCustomized;
import com.jhxiaochen.dollancustomized.common.*;
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

public class DollanBlocks {

    public static Block
            crosswalk_diagonal,
            crosswalk_diagonal_slab,
            road_sign_highway_start,
            road_sign_highway_end,
            road_sign_dont_stop,
            road_sign_dont_stop_1,
            road_sign_park,
            road_sign_park_1,
            road_sign_triangle_rang,
            road_sign_triangle_slow,
            road_sign_triangle_shizi,
            road_sign_triangle_t,
            road_sign_triangle_t1,
            road_sign_triangle_t2,
            road_sign_triangle_warn,
            road_sign_triangle_y,
            bus_lane_mark,
            road_sign_do_not_enter,
            road_sign_do_not_enter_1,
            road_sign_do_not_left,
            road_sign_do_not_right,
            road_sign_go_left,
            road_sign_go_right,
    road_sign_go_both,
            road_sign_can_diaotou,
            road_sign_can_left,
            road_sign_can_right,
            road_sign_do_not_diaotou,
            metro_fence,
    platform_fence,
    road_pedestrian_traffic_light,
    road_traffic_light,
            road_traffic_light_green,
            road_traffic_light_yellow,
            road_traffic_light_red,
            road_sign_sl_15,
            road_sign_sl_20,
            road_sign_sl_30,
    road_sign_sl_40,
            road_sign_sl_50,
            road_sign_sl_60,
            road_sign_sl_70,
            road_sign_sl_80,
            road_sign_sl_90,
            road_sign_sl_100,
            road_sign_sl_110,
            road_sign_sl_120,
            road_sign_post,
            arrow_diaotou,
    arrow_left_diaotou,
    arrow_left_right,
    arrow_left_right_staright,
    arrow_left_staright,
    arrow_right_staright,
    arrow_staright,
    arrow_left,
    arrow_right,
            blackflatlamp,
            asphalt,
            asphalt_slab,
            greenflatlamp,
            white_flat_lamp,
            yellowflatlamp,
            redflatlamp,
            double_yellow_line,
            double_white_line,
            double_yellow_line_slab,
    double_yellow_line_diagonal_slab,
            double_yellow_line_diagonal,
            double_white_line_slab,
            double_white_line_diagonal_slab,
            double_white_line_diagonal,
    single_yellow_line,
    single_yellow_line_slab,
    single_white_line_thick,
    single_white_line_thick_slab,
            single_yellow_line_thick,
            single_yellow_line_thick_slab,
    asphalt_cyan_black_half_slab,
            asphalt_cyan_black_half,

            asphalt_cyan_black_half_45,
            asphalt_cyan_black_half_45_slab,
            asphalt_red_black_half_45,
            asphalt_red_black_half_45_slab,

            asphalt_cyan_black_half_line_slab,
            asphalt_cyan_black_half_line,
            asphalt_cyan_black_half_line_diagonal,
            asphalt_cyan_black_half_line_diagonal_slab,
            asphalt_red_black_half_line_diagonal,
            asphalt_red_black_half_line_diagonal_slab,
    asphalt_red_black_half_line_diagonal_1,
    asphalt_red_black_half_line_diagonal_1_slab,
    asphalt_cyan_black_half_line_diagonal_1_slab,
    asphalt_cyan_black_half_line_diagonal_1,
    asphalt_red_black_half_line,
    asphalt_red_black_half_line_slab,
            asphalt_red_black_half,
            asphalt_red_black_half_slab,
    bike_words,
    crosswalk,
    crosswalk_slab,
    stop_line,
    stop_line_slab,
            stop_line_left,
            stop_line_left_slab,
            stop_line_right,
            stop_line_right_slab,
            single_white_line,
            single_white_line_slab,
            single_white_line_45,
            single_white_line_slab_45,
            single_yellow_line_45,
            single_yellow_line_slab_45,

            single_white_all_line,//sb
    single_white_all_line_slab,
            single_white_90,
            single_white_90_slab,
            single_white_90_1,
            single_white_90_1_slab,
            single_yellow_all_line,
            single_yellow_all_line_slab,
            single_yellow_90,
            single_yellow_90_slab,
            single_yellow_90_1,
            single_yellow_90_1_slab,
            road_excl_zone,
            road_excl_zone_slab,
    road_excl_zone_diagonal_out,
    road_excl_zone_diagonal_out_slab,
            road_excl_zone_diagonal_in,
            road_excl_zone_diagonal_in_slab,
                    road_excl_zone_corner_out,
                    road_excl_zone_corner_out_slab,
                    road_excl_zone_corner_in,
                    road_excl_zone_corner_in_slab,
    road_excl_zone_line,
    road_excl_zone_line_slab,
    road_excl_zone_split_our_l,
            road_excl_zone_split_our_r,
            road_excl_zone_split_our_r_slab,
            road_excl_zone_split_our_l_slab,
            road_excl_zone_split_in_l,
            road_excl_zone_split_in_r,
            road_excl_zone_split_in_r_slab,
            road_excl_zone_split_in_l_slab,
            dollan_line_1,
            dollan_line_2,
            dollan_line_3,
            dollan_line_4,
            dollan_line_5,
            dollan_line_6,
            dollan_line_7,
            dollan_line_8,
            dollan_line_9,
            dollan_line_10,
            dollan_line_11,
            dollan_line_12,
            dollan_line_13,
            dollan_line_14,
    dollan_line_15,
                    dollan_line_16,
                    dollan_s1,
    dollan_s2,
    dollan_airport_line,
    mingsha_S1,
                    mingsha_S2,
                    mingsha_S3,
                    mingsha_S4,
                    mingsha_S5,
                    mingsha_S6,
            lamp_tran,
            light,
            redlight,
            white_punctiform_lamp,
            road_lamp_1_up,
            road_lamp_1_bottom,
    thick_white_line_45,
                    thick_yellow_line_45,thick_yellow_line_slab_45,
                    thick_white_line_slab_45,
    ms_lines,
    ms_lines_slab,
            white_banding_lamp;

    public static void init() {
        crosswalk_diagonal = new BlockAsphaltHorizontal("crosswalk_diagonal");
        crosswalk_diagonal_slab = new BlockAsphaltHorizontal("crosswalk_diagonal_slab");
        ms_lines = new MergeOtherLine("ms_lines");
        ms_lines_slab = new MergeOtherLineSlab("ms_lines_slab");
        road_sign_dont_stop = new BlockRoadSign("road_sign_dont_stop");
        road_sign_dont_stop_1 = new BlockRoadSign("road_sign_dont_stop_1");
        road_sign_highway_start = new BlockRoadSign("road_sign_highway_start");
        road_sign_highway_end = new BlockRoadSign("road_sign_highway_end");
        road_sign_park = new BlockRoadSign("road_sign_park");
        road_sign_park_1 = new BlockRoadSign("road_sign_park_1");
        single_white_90 = new BlockAsphaltHorizontal("single_white_90");
        single_white_90_1 = new BlockAsphaltHorizontal("single_white_90_1");
        single_yellow_90 = new BlockAsphaltHorizontal("single_yellow_90");
        single_yellow_90_1 = new BlockAsphaltHorizontal("single_yellow_90_1");
        single_white_90_slab = new BlockAsphaltSlabHorizontal("single_white_90_slab");
        single_white_90_1_slab = new BlockAsphaltSlabHorizontal("single_white_90_1_slab");
        single_yellow_90_slab = new BlockAsphaltSlabHorizontal("single_yellow_90_slab");
        single_yellow_90_1_slab = new BlockAsphaltSlabHorizontal("single_yellow_90_1_slab");
        single_white_all_line = new BlockAsphalt1("single_white_all_line");
        single_yellow_all_line = new BlockAsphalt1("single_yellow_all_line");
        single_white_all_line_slab = new BlockAsphaltSlab1("single_white_all_line_slab");
        single_yellow_all_line_slab = new BlockAsphaltSlab1("single_yellow_all_line_slab");
        road_sign_triangle_rang = new BlockRoadSign("road_sign_triangle_rang");
        road_sign_triangle_slow = new BlockRoadSign("road_sign_triangle_slow");
        road_sign_triangle_t1 = new BlockRoadSign("road_sign_triangle_t1");
        road_sign_triangle_t = new BlockRoadSign("road_sign_triangle_t");
        road_sign_triangle_t2 = new BlockRoadSign("road_sign_triangle_t2");
        road_sign_triangle_y = new BlockRoadSign("road_sign_triangle_y");
        road_sign_triangle_warn = new BlockRoadSign("road_sign_triangle_warn");
        road_sign_triangle_shizi = new BlockRoadSign("road_sign_triangle_shizi");
        bus_lane_mark = new IronHorizontal("bus_lane_mark");
        road_sign_do_not_enter = new BlockRoadSign("road_sign_do_not_enter");
        road_sign_do_not_enter_1 = new BlockRoadSign("road_sign_do_not_enter_1");
        road_sign_do_not_left = new BlockRoadSign("road_sign_do_not_left");
        road_sign_do_not_right = new BlockRoadSign("road_sign_do_not_right");
        road_sign_go_left = new BlockRoadSign("road_sign_go_left");
        road_sign_go_right = new BlockRoadSign("road_sign_go_right");
        road_sign_go_both = new BlockRoadSign("road_sign_go_both");
        road_sign_do_not_diaotou = new BlockRoadSign("road_sign_do_not_diaotou");
        road_sign_can_left = new BlockRoadSign("road_sign_can_left");
        road_sign_can_right = new BlockRoadSign("road_sign_can_right");
        road_sign_can_diaotou = new BlockRoadSign("road_sign_can_diaotou");
        road_sign_post = new BlockRoadSignPost();
        blackflatlamp = new LightLamp("blackflatlamp");
        greenflatlamp = new LightLamp("greenflatlamp");
        redflatlamp = new LightLamp("redflatlamp");
        white_flat_lamp = new LightLamp("white_flat_lamp");
        yellowflatlamp = new LightLamp("yellowflatlamp");
        white_punctiform_lamp = new PunLamp("white_punctiform_lamp");
        white_banding_lamp = new BandLamp("white_banding_lamp");
        redlight = new BlockLight("redlight");
        dollan_s2 = new BlockMetroBlock("dollan_s2").setCreativeTab(DollanCustomized.DOLLAN_TRAN_TAB);
        dollan_s1 = new BlockMetroBlock("dollan_s1").setCreativeTab(DollanCustomized.DOLLAN_TRAN_TAB);
        dollan_airport_line = new BlockMetroBlock("dollan_airport_line").setCreativeTab(DollanCustomized.DOLLAN_TRAN_TAB);
        dollan_line_1 = new BlockMetroBlock("dollan_line_1");
        dollan_line_2 = new BlockMetroBlock("dollan_line_2");
        dollan_line_3 = new BlockMetroBlock("dollan_line_3");
        dollan_line_4 = new BlockMetroBlock("dollan_line_4");
        dollan_line_5 = new BlockMetroBlock("dollan_line_5");
        dollan_line_6 = new BlockMetroBlock("dollan_line_6");
        dollan_line_7 = new BlockMetroBlock("dollan_line_7");
        dollan_line_8 = new BlockMetroBlock("dollan_line_8");
        dollan_line_9 = new BlockMetroBlock("dollan_line_9");
        dollan_line_10 = new BlockMetroBlock("dollan_line_10");
        dollan_line_11 = new BlockMetroBlock("dollan_line_11");
        dollan_line_12 = new BlockMetroBlock("dollan_line_12");
        dollan_line_13 = new BlockMetroBlock("dollan_line_13");
        dollan_line_14 = new BlockMetroBlock("dollan_line_14");
        dollan_line_15 = new BlockMetroBlock("dollan_line_15");
        dollan_line_16 = new BlockMetroBlock("dollan_line_16");
        mingsha_S1 = new BlockMSMetroBlock("mingsha_S1");
        mingsha_S2 = new BlockMSMetroBlock("mingsha_S2");
        mingsha_S3 = new BlockMSMetroBlock("mingsha_S3");
        mingsha_S4 = new BlockMSMetroBlock("mingsha_S4");
        mingsha_S5 = new BlockMSMetroBlock("mingsha_S5");
        mingsha_S6 = new BlockMSMetroBlock("mingsha_S6");
        lamp_tran = new BlockTranLight("lamp_tran").setRegistryName("lamp_tran");
        light = new BlockLight("light");
        asphalt_slab = new BlockAsphaltSlab("asphalt_slab");
        asphalt = new BlockAsphalt("asphalt");
        bike_words = new IronHorizontal("bike_words");
        double_white_line = new BlockAsphaltHorizontal1("double_white_line");
        double_yellow_line = new BlockAsphaltHorizontal1("double_yellow_line");
        double_white_line_slab = new BlockAsphaltSlabHorizontal2("double_white_line_slab");
        double_yellow_line_slab = new BlockAsphaltSlabHorizontal2("double_yellow_line_slab");
        single_yellow_line = new BlockAsphaltHorizontal1("single_yellow_line");
        single_yellow_line_slab = new BlockAsphaltSlabHorizontal2("single_yellow_line_slab");
        single_white_line = new BlockAsphaltHorizontal1("single_white_line");
        single_white_line_slab = new BlockAsphaltSlabHorizontal2("single_white_line_slab");
        single_white_line_45 = new BlockAsphaltHorizontal("single_white_line_45");
        single_white_line_slab_45 = new BlockAsphaltSlabHorizontal("single_white_line_slab_45");
        single_yellow_line_45 = new BlockAsphaltHorizontal("single_yellow_line_45");
        single_yellow_line_slab_45 = new BlockAsphaltSlabHorizontal("single_yellow_line_slab_45");
        single_white_line_thick = new BlockAsphaltHorizontal1("single_white_line_thick");
        single_white_line_thick_slab = new BlockAsphaltSlabHorizontal2("single_white_line_thick_slab");
        single_yellow_line_thick = new BlockAsphaltHorizontal1("single_yellow_line_thick");
        single_yellow_line_thick_slab = new BlockAsphaltSlabHorizontal2("single_yellow_line_thick_slab");
        road_excl_zone_corner_in = new BlockAsphaltHorizontal("road_excl_zone_corner_in");
        road_excl_zone_corner_in_slab = new BlockAsphaltSlabHorizontal("road_excl_zone_corner_in_slab");
        road_excl_zone_corner_out = new BlockAsphaltHorizontal("road_excl_zone_corner_out");
        road_excl_zone_corner_out_slab = new BlockAsphaltSlabHorizontal("road_excl_zone_corner_out_slab");
        road_excl_zone_diagonal_in = new BlockAsphaltHorizontal("road_excl_zone_diagonal_in");
        road_excl_zone_diagonal_in_slab = new BlockAsphaltSlabHorizontal("road_excl_zone_diagonal_in_slab");
        road_excl_zone_diagonal_out = new BlockAsphaltHorizontal("road_excl_zone_diagonal_out");
        road_excl_zone_diagonal_out_slab = new BlockAsphaltSlabHorizontal("road_excl_zone_diagonal_out_slab");
        road_excl_zone_line = new BlockAsphaltHorizontal("road_excl_zone_line");
        road_excl_zone_line_slab = new BlockAsphaltSlabHorizontal("road_excl_zone_line_slab");
        road_excl_zone = new BlockAsphaltHorizontal("road_excl_zone");
        road_excl_zone_slab = new BlockAsphaltSlabHorizontal("road_excl_zone_slab");
        road_excl_zone_split_our_l = new BlockAsphaltHorizontal("road_excl_zone_split_our_l");
        road_excl_zone_split_our_r_slab = new BlockAsphaltSlabHorizontal("road_excl_zone_split_our_r_slab");
        road_excl_zone_split_our_r = new BlockAsphaltHorizontal("road_excl_zone_split_our_r");
        road_excl_zone_split_our_l_slab = new BlockAsphaltSlabHorizontal("road_excl_zone_split_our_l_slab");
        road_excl_zone_split_in_l = new BlockAsphaltHorizontal("road_excl_zone_split_in_l");
        road_excl_zone_split_in_r_slab = new BlockAsphaltSlabHorizontal("road_excl_zone_split_in_r_slab");
        road_excl_zone_split_in_r = new BlockAsphaltHorizontal("road_excl_zone_split_in_r");
        road_excl_zone_split_in_l_slab = new BlockAsphaltSlabHorizontal("road_excl_zone_split_in_l_slab");
        platform_fence = new BlockPlatFormFence("platform_fence");
        crosswalk = new BlockAsphaltHorizontal1("crosswalk");
        crosswalk_slab = new BlockAsphaltSlabHorizontal2("crosswalk_slab");
        stop_line = new BlockAsphaltHorizontal("stop_line");
        stop_line_slab = new BlockAsphaltSlabHorizontal("stop_line_slab");
        stop_line_left = new BlockAsphaltHorizontal("stop_line_left");
        stop_line_left_slab = new BlockAsphaltSlabHorizontal("stop_line_left_slab");
        stop_line_right = new BlockAsphaltHorizontal("stop_line_right");
        stop_line_right_slab = new BlockAsphaltSlabHorizontal("stop_line_right_slab");
        road_lamp_1_up = new RoadLamp1U();
        road_lamp_1_bottom = new RoadLamp1B();
        arrow_diaotou = new Arrow("arrow_diaotou");
        arrow_left_diaotou = new Arrow("arrow_left_diaotou");
        arrow_left_right = new Arrow("arrow_left_right");
        arrow_left_right_staright = new Arrow("arrow_left_right_staright");
        arrow_left_staright = new Arrow("arrow_left_staright");
        arrow_right_staright = new Arrow("arrow_right_staright");
        arrow_left = new Arrow("arrow_left");
        arrow_right = new Arrow("arrow_right");
        arrow_staright = new Arrow("arrow_staright");
        asphalt_cyan_black_half_line_slab = new BlockAsphaltSlabHorizontal("asphalt_cyan_black_half_line_slab");
        asphalt_cyan_black_half_line = new BlockAsphaltHorizontal("asphalt_cyan_black_half_line");
        asphalt_cyan_black_half_slab = new BlockAsphaltSlabHorizontal("asphalt_cyan_black_half_slab");
        asphalt_cyan_black_half_45_slab = new BlockAsphaltSlabHorizontal("asphalt_cyan_black_half_45_slab");
        asphalt_cyan_black_half = new BlockAsphaltHorizontal("asphalt_cyan_black_half");
        asphalt_cyan_black_half_45 = new BlockAsphaltHorizontal("asphalt_cyan_black_half_45");
        double_yellow_line_diagonal_slab = new BlockAsphaltSlabHorizontal("double_yellow_line_diagonal_slab");
        double_yellow_line_diagonal = new BlockAsphaltHorizontal("double_yellow_line_diagonal");
        double_white_line_diagonal_slab = new BlockAsphaltSlabHorizontal("double_white_line_diagonal_slab");
        double_white_line_diagonal = new BlockAsphaltHorizontal("double_white_line_diagonal");
        asphalt_red_black_half = new BlockAsphaltHorizontal("asphalt_red_black_half");
        asphalt_red_black_half_slab = new BlockAsphaltSlabHorizontal("asphalt_red_black_half_slab");
        asphalt_red_black_half_45 = new BlockAsphaltHorizontal("asphalt_red_black_half_45");
        asphalt_red_black_half_45_slab = new BlockAsphaltSlabHorizontal("asphalt_red_black_half_45_slab");
        asphalt_red_black_half_line = new BlockAsphaltHorizontal("asphalt_red_black_half_line");
        asphalt_red_black_half_line_slab = new BlockAsphaltSlabHorizontal("asphalt_red_black_half_line_slab");
        asphalt_cyan_black_half_line_diagonal_slab = new BlockAsphaltSlabHorizontal("asphalt_cyan_black_half_line_diagonal_slab");
        asphalt_cyan_black_half_line_diagonal = new BlockAsphaltHorizontal("asphalt_cyan_black_half_line_diagonal");
        asphalt_red_black_half_line_diagonal = new BlockAsphaltHorizontal("asphalt_red_black_half_line_diagonal");
        asphalt_red_black_half_line_diagonal_slab = new BlockAsphaltSlabHorizontal("asphalt_red_black_half_line_diagonal_slab");
    asphalt_cyan_black_half_line_diagonal_1 = new BlockAsphaltHorizontal("asphalt_cyan_black_half_line_diagonal_1");
        asphalt_red_black_half_line_diagonal_1 = new BlockAsphaltHorizontal("asphalt_red_black_half_line_diagonal_1");
        asphalt_red_black_half_line_diagonal_1_slab = new BlockAsphaltSlabHorizontal("asphalt_red_black_half_line_diagonal_1_slab");
        asphalt_cyan_black_half_line_diagonal_1_slab = new BlockAsphaltSlabHorizontal("asphalt_cyan_black_half_line_diagonal_1_slab");
        road_sign_sl_15 = new BlockRoadSign("road_sign_sl_15");
        road_sign_sl_20 = new BlockRoadSign("road_sign_sl_20");
        road_sign_sl_30 = new BlockRoadSign("road_sign_sl_30");
        road_sign_sl_40 = new BlockRoadSign("road_sign_sl_40");
        road_sign_sl_50 = new BlockRoadSign("road_sign_sl_50");
        road_sign_sl_60 = new BlockRoadSign("road_sign_sl_60");
        road_sign_sl_70 = new BlockRoadSign("road_sign_sl_70");
        road_sign_sl_80 = new BlockRoadSign("road_sign_sl_80");
        road_sign_sl_90 = new BlockRoadSign("road_sign_sl_90");
        road_sign_sl_100 = new BlockRoadSign("road_sign_sl_100");
        road_sign_sl_110 = new BlockRoadSign("road_sign_sl_110");
        road_sign_sl_120 = new BlockRoadSign("road_sign_sl_120");
        metro_fence = new BlockMetroFence("metro_fence");
        road_pedestrian_traffic_light = new BlockRoadSign("road_pedestrian_traffic_light");
        road_traffic_light = new BlockRoadSign("road_traffic_light");
        road_traffic_light_red = new BlockRoadSign("road_traffic_light_red");
        road_traffic_light_green = new BlockRoadSign("road_traffic_light_green");
        road_traffic_light_yellow = new BlockRoadSign("road_traffic_light_yellow");

        thick_white_line_45 = new BlockAsphaltHorizontal("thick_white_line_45");
        thick_white_line_slab_45 = new BlockAsphaltSlabHorizontal("thick_white_line_slab_45");
        thick_yellow_line_45 = new BlockAsphaltHorizontal("thick_yellow_line_45");
        thick_yellow_line_slab_45 = new BlockAsphaltSlabHorizontal("thick_yellow_line_slab_45");
    }
    public static void register() {
        registerBlock(white_flat_lamp);
        registerBlock(white_punctiform_lamp);
        registerBlock(white_banding_lamp);
        registerBlock(lamp_tran, new ItemMultiTexture(DollanBlocks.lamp_tran, DollanBlocks.lamp_tran, new ItemMultiTexture.Mapper() {
                    public String apply(ItemStack p_apply_1_) {
                        return EnumDyeColor.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
                    }
                })
        );
        registerBlock(light, new ItemMultiTexture(DollanBlocks.light, DollanBlocks.light, new ItemMultiTexture.Mapper() {
                    public String apply(ItemStack p_apply_1_) {
                        return EnumDyeColor.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
                    }
                })
        );
        registerBlock(road_lamp_1_bottom);
        registerBlock(road_lamp_1_up);

        registerBlock(platform_fence);
        registerBlock(metro_fence, new ItemMultiTexture(metro_fence,metro_fence, new ItemMultiTexture.Mapper() {
                    public String apply(ItemStack p_apply_1_) {
                        return BlockMetroFence.FenceType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
                    }
                })
        );

        registerBlock(arrow_diaotou);
        registerBlock(arrow_left_diaotou);
        registerBlock(arrow_left_right);
        registerBlock(arrow_left_right_staright);
        registerBlock(arrow_left_staright);
        registerBlock(arrow_right_staright);
        registerBlock(arrow_left);
        registerBlock(arrow_right);
        registerBlock(arrow_staright);
        registerBlock(bike_words);
        registerBlock(bus_lane_mark);
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
        registerBlock(asphalt, new ItemMultiTexture(asphalt,asphalt, new ItemMultiTexture.Mapper() {
                    public String apply(ItemStack p_apply_1_) {
                        return BlockAsphalt.AsphaltType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
                    }
                })
        );
        registerBlock(asphalt_slab, new ItemMultiTexture(asphalt_slab,asphalt_slab, new ItemMultiTexture.Mapper() {
                    public String apply(ItemStack p_apply_1_) {
                        return BlockAsphaltSlab.AsphaltType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
                    }
                })
        );

        registerBlock(single_yellow_line);
        registerBlock(single_yellow_line_slab);
        registerBlock(single_white_line);
        registerBlock(single_white_line_slab);
        registerBlock(single_white_line_45
        );
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

        registerBlock(crosswalk_slab);
        registerBlock(crosswalk);
        registerBlock(stop_line_slab);
        registerBlock(stop_line);
        registerBlock(stop_line_left_slab);
        registerBlock(stop_line_left);
        registerBlock(stop_line_right_slab);
        registerBlock(stop_line_right);

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

        registerBlock(thick_white_line_45
        );
        registerBlock(thick_white_line_slab_45);
        registerBlock(thick_yellow_line_45);
        registerBlock(thick_yellow_line_slab_45);

        registerBlock(ms_lines, new ItemMultiTexture(ms_lines,ms_lines, new ItemMultiTexture.Mapper() {
                    public String apply(ItemStack p_apply_1_) {
                        return MergeOtherLine.EnumVariant.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
                    }
                })
        );

        registerBlock(ms_lines_slab, new ItemMultiTexture(ms_lines_slab,ms_lines_slab, new ItemMultiTexture.Mapper() {
                    public String apply(ItemStack p_apply_1_) {
                        return MergeOtherLine.EnumVariant.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
                    }
                })
        );
        registerBlock(crosswalk_diagonal);
        registerBlock(crosswalk_diagonal_slab);

    }

    private static void registerBlock(Block block) {
        registerBlock(block, new ItemBlock(block));
    }

    private static void registerBlock(Block block, ItemBlock item) {
        RegistrationHandler.BLOCKS.add(block);
        item.setRegistryName(block.getRegistryName());
        DollanItems.RegistrationHandler.ITEMS.add(item);
    }

    public static void registerModels() {
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
        registerModel(bus_lane_mark);
        registerModel(road_sign_do_not_enter);
        registerModel(road_traffic_light);
        registerModel(road_traffic_light_red);
        registerModel(road_traffic_light_green);
        registerModel(road_traffic_light_yellow);
        registerModel(road_pedestrian_traffic_light);
        registerModel(metro_fence,BlockMetroFence.FenceType.METRO.getMetadata(), "dollancustomized:metro_fence");
        registerModel(metro_fence,BlockMetroFence.FenceType.ROAD_CENTER.getMetadata(), "dollancustomized:road_center_fence");
        registerModel(metro_fence,BlockMetroFence.FenceType.ROAD.getMetadata(), "dollancustomized:road_fence");
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
        registerModel(stop_line_slab);
        registerModel(stop_line);
        registerModel(stop_line_slab);
        registerModel(stop_line);
        registerModel(stop_line_left_slab);
        registerModel(stop_line_left);
        registerModel(stop_line_right_slab);
        registerModel(stop_line_right);
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
        registerModel(asphalt,BlockAsphalt.AsphaltType.BLACK.getMetadata(), "dollancustomized:black_asphalt");
        registerModel(asphalt,BlockAsphalt.AsphaltType.CYAN.getMetadata(), "dollancustomized:cyan_asphalt");
        registerModel(asphalt,BlockAsphalt.AsphaltType.RED.getMetadata(), "dollancustomized:red_asphalt");
        registerModel(asphalt,BlockAsphalt.AsphaltType.WHITE.getMetadata(), "dollancustomized:white_asphalt");
        registerModel(asphalt,BlockAsphalt.AsphaltType.YELLOW.getMetadata(), "dollancustomized:yellow_asphalt");
        registerModel(asphalt_slab,BlockAsphalt.AsphaltType.BLACK.getMetadata(), "dollancustomized:black_asphalt_slab");
        registerModel(asphalt_slab,BlockAsphalt.AsphaltType.CYAN.getMetadata(), "dollancustomized:cyan_asphalt_slab");
        registerModel(asphalt_slab,BlockAsphalt.AsphaltType.RED.getMetadata(), "dollancustomized:red_asphalt_slab");
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
        registerModel(lamp_tran, EnumDyeColor.WHITE.getMetadata(),"dollancustomized:lamp_tran_0");
        registerModel(lamp_tran, EnumDyeColor.BLACK.getMetadata(),"dollancustomized:lamp_tran_1");
        registerModel(lamp_tran, EnumDyeColor.YELLOW.getMetadata(),"dollancustomized:lamp_tran_2");
        registerModel(lamp_tran, EnumDyeColor.RED.getMetadata(),"dollancustomized:lamp_tran_3");
        registerModel(lamp_tran, EnumDyeColor.GREEN.getMetadata(),"dollancustomized:lamp_tran_4");
        registerModel(lamp_tran, EnumDyeColor.BLUE.getMetadata(),"dollancustomized:lamp_tran_blue");
        registerModel(lamp_tran, EnumDyeColor.BROWN.getMetadata(),"dollancustomized:lamp_tran_brown");
        registerModel(lamp_tran, EnumDyeColor.GRAY.getMetadata(),"dollancustomized:lamp_tran_gray");
        registerModel(lamp_tran, EnumDyeColor.LIGHT_BLUE.getMetadata(),"dollancustomized:lamp_tran_light_blue");
        registerModel(lamp_tran, EnumDyeColor.LIME.getMetadata(),"dollancustomized:lamp_tran_lime");
        registerModel(lamp_tran, EnumDyeColor.SILVER.getMetadata(),"dollancustomized:lamp_tran_silver");
        registerModel(lamp_tran, EnumDyeColor.MAGENTA.getMetadata(),"dollancustomized:lamp_tran_magenta");
        registerModel(lamp_tran, EnumDyeColor.PURPLE.getMetadata(),"dollancustomized:lamp_tran_purple");
        registerModel(lamp_tran, EnumDyeColor.ORANGE.getMetadata(),"dollancustomized:lamp_tran_orange");
        registerModel(lamp_tran, EnumDyeColor.PINK.getMetadata(),"dollancustomized:lamp_tran_pink");
        registerModel(lamp_tran, EnumDyeColor.CYAN.getMetadata(),"dollancustomized:lamp_tran_cyan");
        registerModel(light, EnumDyeColor.WHITE.getMetadata(),"dollancustomized:lamp_white");
        registerModel(light, EnumDyeColor.BLACK.getMetadata(),"dollancustomized:lamp_black");
        registerModel(light, EnumDyeColor.YELLOW.getMetadata(),"dollancustomized:lamp_yellow");
        registerModel(light, EnumDyeColor.RED.getMetadata(),"dollancustomized:lamp_red");
        registerModel(light, EnumDyeColor.GREEN.getMetadata(),"dollancustomized:lamp_green");
        registerModel(light, EnumDyeColor.BLUE.getMetadata(),"dollancustomized:lamp_blue");
        registerModel(light, EnumDyeColor.BROWN.getMetadata(),"dollancustomized:lamp_brown");
        registerModel(light, EnumDyeColor.GRAY.getMetadata(),"dollancustomized:lamp_gray");
        registerModel(light, EnumDyeColor.LIGHT_BLUE.getMetadata(),"dollancustomized:lamp_light_blue");
        registerModel(light, EnumDyeColor.LIME.getMetadata(),"dollancustomized:lamp_lime");
        registerModel(light, EnumDyeColor.SILVER.getMetadata(),"dollancustomized:lamp_silver");
        registerModel(light, EnumDyeColor.MAGENTA.getMetadata(),"dollancustomized:lamp_magenta");
        registerModel(light, EnumDyeColor.PURPLE.getMetadata(),"dollancustomized:lamp_purple");
        registerModel(light, EnumDyeColor.ORANGE.getMetadata(),"dollancustomized:lamp_orange");
        registerModel(light, EnumDyeColor.PINK.getMetadata(),"dollancustomized:lamp_pink");
        registerModel(light, EnumDyeColor.CYAN.getMetadata(),"dollancustomized:lamp_cyan");
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
        registerModel(arrow_left_right_staright);
        registerModel(arrow_left_staright);
        registerModel(arrow_right_staright);
        registerModel(arrow_left);
        registerModel(arrow_right);
        registerModel(arrow_staright);
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

        registerModel(ms_lines,MergeOtherLine.EnumVariant.MERGE_WHITE.getMetadata(),"dollancustomized:merge_line");
        registerModel(ms_lines,MergeOtherLine.EnumVariant.MERGE_YELLOW.getMetadata(),"dollancustomized:merge_line_yellow");
        registerModel(ms_lines,MergeOtherLine.EnumVariant.SEPARATE_WHITE.getMetadata(),"dollancustomized:separate_line");
        registerModel(ms_lines,MergeOtherLine.EnumVariant.SEPARATE_YELLOW.getMetadata(),"dollancustomized:separate_line_yellow");

        registerModel(ms_lines_slab,MergeOtherLineSlab.EnumVariant.MERGE_WHITE.getMetadata(),"dollancustomized:merge_line_slab");
        registerModel(ms_lines_slab,MergeOtherLineSlab.EnumVariant.MERGE_YELLOW.getMetadata(),"dollancustomized:merge_line_yellow_slab");
        registerModel(ms_lines_slab,MergeOtherLineSlab.EnumVariant.SEPARATE_WHITE.getMetadata(),"dollancustomized:separate_line_slab");
        registerModel(ms_lines_slab,MergeOtherLineSlab.EnumVariant.SEPARATE_YELLOW.getMetadata(),"dollancustomized:separate_line_yellow_slab");

    }

    @SideOnly(Side.CLIENT)
    private static void registerModel(Block block,int metadata, String name) {
        ModelResourceLocation model = new ModelResourceLocation(name, "inventory");
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), metadata, model);
    }

    @SideOnly(Side.CLIENT)
    private static void registerModel(Block block) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));

    }

    @Mod.EventBusSubscriber()
    public static class RegistrationHandler {
        public static final List<Block> BLOCKS = new LinkedList<>();

        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Block> event) {
            DollanBlocks.init();
            DollanBlocks.register();
            BLOCKS.stream().forEach(block -> event.getRegistry().register(block));
        }
    }
}
