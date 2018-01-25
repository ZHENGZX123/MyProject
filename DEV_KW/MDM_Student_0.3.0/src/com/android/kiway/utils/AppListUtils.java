package com.android.kiway.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

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
        preinstallAPP.add("cn.kiway.homework.student");
        preinstallAPP.add("com.kk.poem");
        preinstallAPP.add("com.liulishuo.engzo");
        preinstallAPP.add("com.netease.vopen");
        preinstallAPP.add("com.zhtiantian.ChallengerTX");
        preinstallAPP.add("com.kamitu.drawsth.standalone.free.android");
        preinstallAPP.add("com.android.settings");
        preinstallAPP.add("cn.kiway.marketplace");
    }

    public static String getAppListData() {
        return preinstallAPP.toString();
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