package com.zk.myweex.utils;

import android.content.Context;
import android.util.Log;

import com.zk.myweex.WXApplication;
import com.zk.myweex.https.HttpDownload;

import java.io.File;

/**
 * Created by Administrator on 2017/2/28.
 */

public class VersionManager {

    private Context context;

    public void init(Context c) {
        this.context = c;

        if (!new File(WXApplication.PATH).exists()) {
            new File(WXApplication.PATH).mkdirs();
        }
        if (!new File(WXApplication.PATH_BACKUP).exists()) {
            new File(WXApplication.PATH_BACKUP).mkdirs();
        }
        if (!new File(WXApplication.PATH_TMP).exists()) {
            new File(WXApplication.PATH_TMP).mkdirs();
        }
    }

    //在这里要做增量管理
    //1.读取我当前的模块，请求服务器，查询对应模块的信息，返回值包括
    // 模块id， 版本号 ， 文件大小 ， 下载地址
    //2.比较是否有新的版本，如果有，提示下载。(是单个更新还是一次性更新)
    //3.下载替换zip文件

    public void getRemoteVersion() {
        File root = new File(WXApplication.PATH);
        File[] files = root.listFiles();
        if (files == null) {
            return;
        }
        if (files.length == 0) {
            return;
        }
        for (File f : files) {
            String name = f.getName();
            Log.d("test", "file = " + f.getAbsolutePath());
            String version = this.context.getSharedPreferences("yjpt", 0).getString("version_" + name, "1.0.0");
            Log.d("test", "version = " + version);
        }
        //访问http，提交参数： zip名字, 版本号(支持数组)
        boolean hasNewVersion = hasNewVersion();
        if (hasNewVersion) {
//        改为静默更新
//            showUpdateConfirmDialog("http://120.24.84.206/yjpt/function1.zip", "function1.zip");
            downloadNewVersion("http://120.24.84.206/yjpt/function1.zip", "function1.zip");
        }
    }

//    protected void showUpdateConfirmDialog(final String url, final String zipName) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this.context, AlertDialog.THEME_HOLO_LIGHT);
//        AlertDialog dialog_download = builder.setMessage("发现新的版本，是否更新？").setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface d, int arg1) {
//                d.dismiss();
//                downloadNewVersion(url, zipName);
//            }
//        }).create();
//        dialog_download.setCancelable(false);
//        dialog_download.show();
//    }

    protected void downloadNewVersion(final String urlString, final String zipName) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpDownload httpDownload = new HttpDownload();
                int ret = httpDownload.downFile(urlString, WXApplication.PATH_TMP, zipName);
                Log.d("test", "下载返回值 ret = " + ret);
                if (ret != 0) {
                    return;
                }
                Log.d("test", "下载成功");

                //判断tmp下有完整的zip包，如果有的话，提示用户更新。
                //1.先保存旧包
                File currentFile = new File(WXApplication.PATH + zipName);
                boolean move1 = currentFile.renameTo(new File(WXApplication.PATH_BACKUP + zipName));
                //2.替换旧包
                File newFile = new File(WXApplication.PATH_TMP + zipName);
                boolean move2 = newFile.renameTo(new File(WXApplication.PATH + zipName));
                //3.保存版本号
                context.getSharedPreferences("yjpt", 0).edit().putString("version_" + zipName, "1.0.1").commit();
            }
        }).start();

        if (true) {
            return;
        }
    }

    private boolean hasNewVersion() {
        return true;
    }

    public void getLocalVersion() {
        //检查tmp目录下有zip包，如果有，提示用户更新
        File root = new File(WXApplication.PATH_TMP);
        File[] files = root.listFiles();
        if (files == null) {
            return;
        }
        if (files.length == 0) {
            return;
        }

        //1.如果是完整的f.zip，直接替换

        //2.如果是增量的p.zip，增量替换
    }
}
