package cn.kiway.mdm.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

import cn.kiway.mdm.R;
import cn.kiway.mdm.entity.App;

import static cn.kiway.mdm.utils.Constant.kiwayQiTa;

/**
 * Created by Administrator on 2017/10/13.
 */

public class AppListUtils {
    public static ArrayList<App> getAppListData(Context context) {
        ArrayList<App> apps = new ArrayList<>();

        App a1 = new App();
        a1.name = "幼教家长";
        a1.packageName = "cn.kiway.Yjptj";
        a1.icon = Utils.getIconByPackageName(context.getPackageManager(), a1);
        apps.add(a1);

        App a15 = new App();
        a15.name = "幼教老师";
        a15.packageName = "cn.kiway.Yjpty";
        a15.icon = Utils.getIconByPackageName(context.getPackageManager(), a15);
        apps.add(a15);

        App a2 = new App();
        a2.name = "一起阅读";
        a2.packageName = "cn.kiway.yiqiyuedu";
        a2.icon = Utils.getIconByPackageName(context.getPackageManager(), a2);
        apps.add(a2);
        App a3 = new App();
        a3.name = "快乐作业";
        a3.packageName = "cn.kiway.klzy";
        a3.icon = Utils.getIconByPackageName(context.getPackageManager(), a3);
        apps.add(a3);

        App a4 = new App();
        a4.name = "宝安通";
        a4.packageName = "cn.kiway.baoantong_vue";
        a4.icon = Utils.getIconByPackageName(context.getPackageManager(), a4);
        apps.add(a4);

        App a5 = new App();
        a5.name = "家校通";
        a5.packageName = "";
        a5.icon = Utils.getIconByPackageName(context.getPackageManager(), a5);
        apps.add(a5);

        App a6 = new App();
        a6.name = "协同课堂";
        a6.packageName = "cn.kiway.txkt";
        a6.icon = Utils.getIconByPackageName(context.getPackageManager(), a6);
        apps.add(a6);

        App a7 = new App();
        a7.name = "童趣玩";
        a7.packageName = "";
        a7.icon = Utils.getIconByPackageName(context.getPackageManager(), a7);
        apps.add(a7);

        App a8 = new App();
        a8.name = "阿帆题";
        a8.packageName = "com.lejent.zuoyeshenqi.afanti";
        a8.icon = Utils.getIconByPackageName(context.getPackageManager(), a8);
        apps.add(a8);

        App a9 = new App();
        a9.name = "金山词霸";
        a9.packageName = "com.kingsoft";
        a9.icon = Utils.getIconByPackageName(context.getPackageManager(), a9);
        apps.add(a9);

        App a10 = new App();
        a10.name = "古诗词典";
        a10.packageName = "com.kk.poem";
        a10.icon = Utils.getIconByPackageName(context.getPackageManager(), a10);
        apps.add(a10);


        App a11 = new App();
        a11.name = "知米背单词";
        a11.packageName = "cn.edu.zjicm.wordsnet_d";
        a11.icon = Utils.getIconByPackageName(context.getPackageManager(), a11);
        apps.add(a11);


        App a12 = new App();
        a12.name = "英语流利说";
        a12.packageName = "com.liulishuo.engzo";
        a12.icon = Utils.getIconByPackageName(context.getPackageManager(), a12);
        apps.add(a12);


        App a13 = new App();
        a13.name = "网易公开课";
        a13.packageName = "com.netease.vopen";
        a13.icon = Utils.getIconByPackageName(context.getPackageManager(), a13);
        apps.add(a13);

        App a14 = new App();
        a14.name = "阿帆题-X";
        a14.packageName = "com.lejent.zuoyeshenqi.afantix";
        a14.icon = Utils.getIconByPackageName(context.getPackageManager(), a14);
        apps.add(a14);

        App a16 = new App();
        a16.name ="作业帮";
        a16.packageName = "com.baidu.homework";
        a16.icon = Utils.getIconByPackageName(context.getPackageManager(), a16);
        apps.add(a16);

        App a17 = new App();
        a17.name ="答题王";
        a17.packageName = "com.zhtiantian.ChallengerTX";
        a17.icon = Utils.getIconByPackageName(context.getPackageManager(), a17);
        apps.add(a17);

        App a18 = new App();
        a18.name ="疯狂猜成语";
        a18.packageName = "com.kamitu.drawsth.standalone.free.android";
        a18.icon = Utils.getIconByPackageName(context.getPackageManager(), a18);
        apps.add(a18);

        //其他应用的数据，
        App a100 = new App();
        a100.name = "其他应用";
        a100.packageName = kiwayQiTa;
        a100.icon = context.getResources().getDrawable(R.drawable.ic_more);
        apps.add(a100);

        return apps;
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<String>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);
    }

    public static boolean isCheckAppTime(Context context, String packageName) {
        if (!context.getSharedPreferences("kiway", 0).getString(packageName, "").equals("")) {//是否设置了使用时间
            String time = context.getSharedPreferences("kiway", 0).getString(packageName, "");
            if (!Utils.rangeInDefined(Integer.parseInt(Utils.getDateField(System.currentTimeMillis(), 11))
                    , Integer.parseInt(time.split("-")[0]), Integer.parseInt(time.split
                            ("-")[1]))) {//判断是否处于使用时间
                return true;
            }
        }
        return false;
    }
}
