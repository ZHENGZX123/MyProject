package cn.kiway.mdm.utils;

import java.util.ArrayList;

import cn.kiway.mdm.entity.App;


/**
 * Created by Administrator on 2017/6/9.
 */

public class Constant {

    //只能打开以下app
    public static String[] apps = new String[]{
            "cn.kiway.mdm",//开维教育桌面 cn.kiway.mdm
            "com.kiway.yjpt.Teacher",//幼教园丁
            "cn.kiway.Yjptj",
            "cn.kiway.baoantong",    //宝安通
            "cn.kiway.baoantong_vue",
            "cn.kiway.yqyd",         //一起阅读
            "cn.kiway.yiqiyuedu",
            "cn.kiway.klzy",        //快乐作业
            "android"               //launcher选择框
    };

    public static ArrayList<App> otherApps = new ArrayList<>();

    public final static String kiwayQiTa="kiwayqita";
}
