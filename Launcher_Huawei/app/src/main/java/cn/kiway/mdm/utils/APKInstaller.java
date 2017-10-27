package cn.kiway.mdm.utils;

import android.util.Log;

import java.util.ArrayList;

import cn.kiway.mdm.activity.MainActivity;
import cn.kiway.mdm.mdm.MDMHelper;

/**
 * Created by Administrator on 2017/10/26.
 */

public class APKInstaller {

    private static ArrayList<String> packages = new ArrayList<>();

    public static void addPackage(final MainActivity m, final String packageName, final String url, final String name, final String version) {
        if (packages.contains(packageName)) {
            return;
        }
        packages.add(packageName);

        new Thread() {
            @Override
            public void run() {
                final String savePath = "/mnt/sdcard/mdm/apk/";
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
