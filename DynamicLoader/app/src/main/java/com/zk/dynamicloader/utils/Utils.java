package com.zk.dynamicloader.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2018/10/9.
 */

public class Utils {

    public static void copyDBToSD(Context context, String path, String filename) {//获取资产管理者
        AssetManager am = context.getAssets();
        try {//打开资产目录下的文件
            InputStream inputStream = am.open(filename);File file=new File(path,filename);
            FileOutputStream fos = new FileOutputStream(file);
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = inputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.close();
        } catch (IOException e) {
            e
                    .printStackTrace();
        }
    }


}
