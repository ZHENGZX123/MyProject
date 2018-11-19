package com.zk.dynamicloader;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.zk.dynamicloader.utils.HttpDownload;
import com.zk.dynamicloader.utils.IContant;
import com.zk.dynamicloader.utils.Utils;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void test1(View view) {
        String ret = callMethod(this, "/mnt/sdcard/test.jar", "cn.kiway.dynamic.MDMUtil", "isStatusBarExpandPanelDisabled", null);
        toast(ret);
    }

    public void test2(View view) {
        String ret = callMethod(this, "/mnt/sdcard/test.jar", "cn.kiway.dynamic.MDMUtil", "setStatusBarExpandPanelDisabled", new String[]{"true"});
        toast(ret);
    }

    String callMethod(Context c, String jarFilePath, String className, String methodName, String[] params) {
        String ret = null;
        if (!new File(jarFilePath).exists()) {
            return "jar不存在";
        }
        try {
            String tmpPath = c.getApplicationContext().getDir("Jar", 0).getAbsolutePath();
            DexClassLoader cl = new DexClassLoader(jarFilePath, tmpPath
                    , null, c.getClass().getClassLoader());
            Class<?> classToCall = cl.loadClass(className);
            if (params == null) {
                Method methodToExecute = classToCall.getDeclaredMethod(methodName, null);
                ret = (String) methodToExecute.invoke(classToCall.newInstance());
            } else {
                Method methodToExecute = classToCall.getDeclaredMethod(methodName, String[].class);
                ret = (String) methodToExecute.invoke(classToCall.newInstance(), new Object[]{params});
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "jar异常" + e.toString();
        }
        return ret;
    }

    public void upgrade(View view) {
        if (new File("/mnt/sdcard/test.jar").exists()) {
            new File("/mnt/sdcard/test.jar").delete();
        }
        new Thread() {
            @Override
            public void run() {
                super.run();
                int i = new HttpDownload().downFile(IContant.downloadUrl, "/mnt/sdcard/", "test.jar");
                if (i == 0) {
                    toast("升级成功");
                } else {
                    toast("升级失败");
                }
            }
        }.start();
    }

    public void degrade(View view) {
        if (new File("/mnt/sdcard/test.jar").exists()) {
            new File("/mnt/sdcard/test.jar").delete();
        }
        Utils.copyDBToSD(this, "/mnt/sdcard/", "test.jar");
        toast("降级成功");
    }

    private void toast(final String txt) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, txt, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
