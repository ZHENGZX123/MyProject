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

    public static ArrayList<String> preinstallAPP = new ArrayList<>();

    static {
        preinstallAPP.clear();
        preinstallAPP.add("cn.kiway.homework.student");
        preinstallAPP.add("com.kk.poem");
        preinstallAPP.add("com.liulishuo.engzo");
        preinstallAPP.add("com.netease.vopen");
        preinstallAPP.add("com.zhtiantian.ChallengerTX");
        preinstallAPP.add("com.kamitu.drawsth.standalone.free.android");
    }

    public static List<List<App>> getAppListData(Context context) {

        List<List<App>> data = new ArrayList<>();

//        App a1 = new App();
//        ArrayList<App> apps1 = new ArrayList<>();
//        a1.name = "协同作业";
//        a1.packageName = "cn.kiway.homework.student";
//        apps1.add(a1);
//        data.add(apps1);
//
//        ArrayList<App> apps10 = new ArrayList<>();
//        App a10 = new App();
//        a10.name = "古诗词典";
//        a10.packageName = "com.kk.poem";
//        apps10.add(a10);
//        data.add(apps10);
//
//
//        ArrayList<App> apps12 = new ArrayList<>();
//        App a12 = new App();
//        a12.name = "英语流利说";
//        a12.packageName = "com.liulishuo.engzo";
//        apps12.add(a12);
//        data.add(apps12);
//
//        ArrayList<App> apps13 = new ArrayList<>();
//        App a13 = new App();
//        a13.name = "网易公开课";
//        a13.packageName = "com.netease.vopen";
//        apps13.add(a13);
//        data.add(apps13);
//
//
//        ArrayList<App> apps17 = new ArrayList<>();
//        App a17 = new App();
//        a17.name = "答题王";
//        a17.packageName = "com.zhtiantian.ChallengerTX";
//        apps17.add(a17);
//        data.add(apps17);
//
//        ArrayList<App> apps18 = new ArrayList<>();
//        App a18 = new App();
//        a18.name = "疯狂猜成语";
//        a18.packageName = "com.kamitu.drawsth.standalone.free.android";
//        apps18.add(a18);
//        data.add(apps18);

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

}
