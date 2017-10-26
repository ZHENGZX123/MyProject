package cn.kiway.mdm.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

import cn.kiway.mdm.entity.App;

/**
 * Created by Administrator on 2017/10/13.
 */

public class AppListUtils {
    public static List<List<App>> getAppListData(Context context) {

        List<List<App>> data = new ArrayList<>();


        App a1 = new App();
        ArrayList<App> apps1 = new ArrayList<>();
        a1.name = "幼教家长";
        a1.packageName = "cn.kiway.Yjptj";
        //a1.icon = Utils.getIconByPackageName(context.getPackageManager(), a1);
        apps1.add(a1);
        data.add(apps1);

        ArrayList<App> apps15 = new ArrayList<>();
        App a15 = new App();
        a15.name = "幼教老师";
        a15.packageName = "cn.kiway.Yjpty";
       // a15.icon = Utils.getIconByPackageName(context.getPackageManager(), a15);
        apps15.add(a15);
        data.add(apps15);

        ArrayList<App> apps2 = new ArrayList<>();
        App a2 = new App();
        a2.name = "一起阅读";
        a2.packageName = "cn.kiway.yiqiyuedu";
      //  a2.icon = Utils.getIconByPackageName(context.getPackageManager(), a2);
        apps2.add(a2);
        data.add(apps2);

        ArrayList<App> apps3 = new ArrayList<>();
        App a3 = new App();
        a3.name = "快乐作业";
        a3.packageName = "cn.kiway.klzy";
       // a3.icon = Utils.getIconByPackageName(context.getPackageManager(), a3);
        apps3.add(a3);
        data.add(apps3);

        ArrayList<App> apps4 = new ArrayList<>();
        App a4 = new App();
        a4.name = "宝安通";
        a4.packageName = "cn.kiway.baoantong_vue";
       /// a4.icon = Utils.getIconByPackageName(context.getPackageManager(), a4);
        apps4.add(a4);
        data.add(apps4);

        ArrayList<App> apps5 = new ArrayList<>();
        App a5 = new App();
        a5.name = "家校通";
        a5.packageName = "";
       // a5.icon = Utils.getIconByPackageName(context.getPackageManager(), a5);
        apps5.add(a5);
        data.add(apps5);

        ArrayList<App> apps6 = new ArrayList<>();
        App a6 = new App();
        a6.name = "协同课堂";
        a6.packageName = "cn.kiway.txkt";
       // a6.icon = Utils.getIconByPackageName(context.getPackageManager(), a6);
        apps6.add(a6);
        data.add(apps6);

        ArrayList<App> apps7 = new ArrayList<>();
        App a7 = new App();
        a7.name = "童趣玩";
        a7.packageName = "";
       // a7.icon = Utils.getIconByPackageName(context.getPackageManager(), a7);
        apps7.add(a7);
        data.add(apps7);

        ArrayList<App> apps8 = new ArrayList<>();
        App a8 = new App();
        a8.name = "阿帆题";
        a8.packageName = "com.lejent.zuoyeshenqi.afanti";
       // a8.icon = Utils.getIconByPackageName(context.getPackageManager(), a8);
        apps8.add(a8);
        data.add(apps8);

        ArrayList<App> apps9 = new ArrayList<>();
        App a9 = new App();
        a9.name = "金山词霸";
        a9.packageName = "com.kingsoft";
       // a9.icon = Utils.getIconByPackageName(context.getPackageManager(), a9);
        apps9.add(a9);
        data.add(apps9);

        ArrayList<App> apps10 = new ArrayList<>();
        App a10 = new App();
        a10.name = "古诗词典";
        a10.packageName = "com.kk.poem";
       // a10.icon = Utils.getIconByPackageName(context.getPackageManager(), a10);
        apps10.add(a10);
        data.add(apps10);

        ArrayList<App> apps11 = new ArrayList<>();
        App a11 = new App();
        a11.name = "知米背单词";
        a11.packageName = "cn.edu.zjicm.wordsnet_d";
       // a11.icon = Utils.getIconByPackageName(context.getPackageManager(), a11);
        apps11.add(a11);
        data.add(apps11);


        ArrayList<App> apps12 = new ArrayList<>();
        App a12 = new App();
        a12.name = "英语流利说";
        a12.packageName = "com.liulishuo.engzo";
       // a12.icon = Utils.getIconByPackageName(context.getPackageManager(), a12);
        apps12.add(a12);
        data.add(apps12);

        ArrayList<App> apps13 = new ArrayList<>();
        App a13 = new App();
        a13.name = "网易公开课";
        a13.packageName = "com.netease.vopen";
       // a13.icon = Utils.getIconByPackageName(context.getPackageManager(), a13);
        apps13.add(a13);
        data.add(apps13);

        ArrayList<App> apps14 = new ArrayList<>();
        App a14 = new App();
        a14.name = "阿帆题-X";
        a14.packageName = "com.lejent.zuoyeshenqi.afantix";
       // a14.icon = Utils.getIconByPackageName(context.getPackageManager(), a14);
        apps14.add(a14);
        data.add(apps14);

        ArrayList<App> apps16 = new ArrayList<>();
        App a16 = new App();
        a16.name = "作业帮";
        a16.packageName = "com.baidu.homework";
       //a16.icon = Utils.getIconByPackageName(context.getPackageManager(), a16);
        apps16.add(a16);
        data.add(apps16);

        ArrayList<App> apps17 = new ArrayList<>();
        App a17 = new App();
        a17.name = "答题王";
        a17.packageName = "com.zhtiantian.ChallengerTX";
       // a17.icon = Utils.getIconByPackageName(context.getPackageManager(), a17);
        apps17.add(a17);
        data.add(apps17);

        ArrayList<App> apps18 = new ArrayList<>();
        App a18 = new App();
        a18.name = "疯狂猜成语";
        a18.packageName = "com.kamitu.drawsth.standalone.free.android";
       // a18.icon = Utils.getIconByPackageName(context.getPackageManager(), a18);
        apps18.add(a18);
        data.add(apps18);

        //其他应用的数据。暂时屏蔽，改为由后台管控
//        App a100 = new App();
//        a100.name = "其他应用";
//        a100.packageName = kiwayQiTa;
//        a100.icon = context.getResources().getDrawable(R.drawable.ic_more);
//        apps.add(a100);

        return data;
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
