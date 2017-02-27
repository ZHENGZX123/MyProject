package com.zk.myweex.extend.module;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.zk.myweex.WXApplication;
import com.zk.myweex.activity.WXPageActivity;
import com.zk.myweex.https.HttpDownload;

import java.io.File;


public class MyTab2 extends WXModule {


    @JSMethod(uiThread = true)
    public void loadFunction(String zipName, JSCallback callback) {
        Toast.makeText(mWXSDKInstance.getContext(), " loadJSBundle js :" + zipName, Toast.LENGTH_SHORT).show();
        String path = "file://" + WXApplication.PATH + zipName;
        Log.d("test", "path = " + path);
        if (new File(path).exists()) {
            Log.d("test", "存在，直接加载");
            loadJSBundle(zipName);
        } else {
            Log.d("test", "不存在，下载");
            downloadJSBundle(zipName);
        }
    }

    @JSMethod(uiThread = true)
    public void deleteFunction(String zipName, JSCallback callback) {
        String path = "file://" + WXApplication.PATH + zipName;
        Log.d("test", "path = " + path);
        if (new File(path).exists()) {
            Log.d("test", "存在，删除");
            new File(path).delete();
            mWXSDKInstance.getContext().getSharedPreferences("yjpt", 0).edit().remove("version_" + zipName).commit();
            callback.invoke("delete success");
        } else {
            Log.d("test", "不存在，不用管");
        }
    }

    private void downloadJSBundle(final String zipName) {
        //1.访问接口，参数是zipName，返回是 name， 下载地址 ， 版本号
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpDownload httpDownload = new HttpDownload();
                int ret = httpDownload.downFile("http://120.24.84.206/yjpt/" + zipName, "/mnt/sdcard/yjpt/", zipName);
                Log.d("test", "下载返回值 ret = " + ret);
                if (ret != 0) {
                    toast("下载失败，请稍后再试");
                    return;
                }
                Log.d("test", "下载成功，保存版本号");
                mWXSDKInstance.getContext().getSharedPreferences("yjpt", 0).edit().putString("version_" + zipName, "1.0.0").commit();
                Log.d("test", "下载成功，加载本地sdcard");
                loadJSBundle(zipName);
            }
        }).start();
    }

    private void loadJSBundle(String zipName) {
        //假设路径是function1.zip/function1/index.js
        String fileName = zipName.replace(".zip", "");
        String path = "file://" + WXApplication.PATH + zipName + "/" + fileName + "/index.js";
        Intent intent = new Intent(mWXSDKInstance.getContext(), WXPageActivity.class);
        intent.setData(Uri.parse(path));
        mWXSDKInstance.getContext().startActivity(intent);
    }

    private void toast(String txt) {
        ((Activity) mWXSDKInstance.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mWXSDKInstance.getContext(), "txt", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

