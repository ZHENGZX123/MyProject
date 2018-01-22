package com.android.kiway.utils;

import android.util.Log;

import com.android.kiway.activity.MainActivity;

import java.util.ArrayList;

import cn.kiway.mdmsdk.MDMHelper;

/**
 * Created by Administrator on 2017/10/26.
 */

public class APKInstaller {

    private static ArrayList<String> packages = new ArrayList<>();

    public static void install(final MainActivity m, final String packageName, final String url, final String name, final String version) {
        if (m == null) {
            return;
        }
        if (packages.contains(packageName)) {
            return;
        }
        packages.add(packageName);

        new Thread() {
            @Override
            public void run() {
                final String savePath = "/mnt/sdcard/kiway_mdm_student/apk/";
                final String saveName = name + "_" + version + ".apk";
                int ret = new HttpDownload().downFile(url, savePath, saveName);
                Log.d("test", "下载" + name + "，ret = " + ret);
                if (ret != 0) {
                    packages.remove(packageName);//下载失败要移除包名，下次继续
                    return;
                }
                m.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("test", "开始安装");
                        MDMHelper.getAdapter().installPackage(savePath + saveName);
                        Log.d("test", "安装结束");
                        packages.remove(packageName);//不管安装成功失败，都要移除包名
                    }
                });
            }
        }.start();
    }
}
