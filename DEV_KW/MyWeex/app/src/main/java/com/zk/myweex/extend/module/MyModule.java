package com.zk.myweex.extend.module;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.zk.myweex.WXApplication;
import com.zk.myweex.activity.WXPageActivity;
import com.zk.myweex.entity.ZipPackage;
import com.zk.myweex.https.HttpDownload;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmResults;


public class MyModule extends WXModule {

    private ProgressDialog pd;

    @JSMethod(uiThread = true)
    public void loadFunction(String zipName, JSCallback callback) {
        Toast.makeText(mWXSDKInstance.getContext(), " loadJSBundle js :" + zipName, Toast.LENGTH_SHORT).show();
        String path = WXApplication.PATH + zipName;
        Log.d("test", "path = " + path);
        //这里还要判断realm里是否有版本号，如果没有，就说明程序被卸载过。。。
        ZipPackage zip = Realm.getDefaultInstance().where(ZipPackage.class).equalTo("name", zipName).findFirst();
        if (new File(path).exists() && zip != null) {
            Log.d("test", "存在，直接加载");
            loadJSBundle(zipName);
        } else {
            Log.d("test", "不存在，下载");
            pd = new ProgressDialog(mWXSDKInstance.getContext());
            pd.setMessage("首次加载，请稍等");
            pd.show();
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
            final RealmResults<ZipPackage> results = Realm.getDefaultInstance().where(ZipPackage.class).equalTo("name", zipName).findAll();
            Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    results.clear();
                }
            });
            callback.invoke("delete success");
        } else {
            Log.d("test", "不存在，不用管");
        }
    }

    //首次下载
    private void downloadJSBundle(final String zipName) {
        //1.访问接口，参数是zipName，返回是 name， 下载地址 ， 版本号
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpDownload httpDownload = new HttpDownload();
                int ret = httpDownload.downFile("http://120.24.84.206/yjpt/" + zipName, WXApplication.PATH, zipName);
                Log.d("test", "下载返回值 ret = " + ret);
                hidePD();
                if (ret != 0) {
                    toast("下载失败，请稍后再试");
                    return;
                }
                Log.d("test", "下载成功，保存版本号");

                Realm.getDefaultInstance().beginTransaction();
                ZipPackage zip = Realm.getDefaultInstance().createObject(ZipPackage.class);
                zip.name = zipName;
                zip.indexPath = "要填这个哦";
                zip.version = "1.0.0";
                Realm.getDefaultInstance().commitTransaction();
                Log.d("test", "下载成功，加载本地sdcard");
                loadJSBundle(zipName);
            }
        }).start();
    }


    private void loadJSBundle(String zipName) {
        //TODO 假设路径是function1.zip/function1/index.js , 这个路径要求web传过来。
        String fileName = zipName.replace(".zip", "");
        String path = "file://" + WXApplication.PATH + zipName + "/" + fileName + "/index.js";
        Intent intent = new Intent(mWXSDKInstance.getContext(), WXPageActivity.class);
        intent.setData(Uri.parse(path));
        mWXSDKInstance.getContext().startActivity(intent);
    }

    private void hidePD() {
        ((Activity) mWXSDKInstance.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pd.dismiss();
            }
        });
    }

    private void toast(String txt) {
        ((Activity) mWXSDKInstance.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mWXSDKInstance.getContext(), "txt", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @JSMethod(uiThread = true)
    public void getGrid1(JSCallback callback) {
        JSONArray a = new JSONArray();
        JSONObject o1 = new JSONObject();
        o1.put("title", "function1");
        o1.put("src", "https://img.alicdn.com/tps/i2/TB1CpD7IXXXXXbSXXXXUAkPJpXX-87-87.png");
        o1.put("path", "function1/index.js");
        JSONObject o2 = new JSONObject();
        o2.put("title", "function2");
        o2.put("src", "https://img.alicdn.com/tps/i2/TB1CpD7IXXXXXbSXXXXUAkPJpXX-87-87.png");
        o2.put("path", "function2/index.js");
        JSONObject o3 = new JSONObject();
        o3.put("title", "function3");
        o3.put("src", "https://img.alicdn.com/tps/i2/TB1CpD7IXXXXXbSXXXXUAkPJpXX-87-87.png");
        o3.put("path", "function3/index.js");
        a.add(o1);
        a.add(o2);
        a.add(o3);
        Log.d("test", "a.toString() = " + a.toString());
        callback.invoke(a.toString());
    }

    @JSMethod(uiThread = true)
    public void getGrid2(String zipName, JSCallback callback) {
        JSONArray a = new JSONArray();
        JSONObject o1 = new JSONObject();
        o1.put("title", "function4");
        o1.put("src", "https://img.alicdn.com/tps/i2/TB1CpD7IXXXXXbSXXXXUAkPJpXX-87-87.png");
        o1.put("path", "function4/index.js");
        JSONObject o2 = new JSONObject();
        o2.put("title", "function5");
        o2.put("src", "https://img.alicdn.com/tps/i2/TB1CpD7IXXXXXbSXXXXUAkPJpXX-87-87.png");
        o2.put("path", "function5/index.js");
        JSONObject o3 = new JSONObject();
        o3.put("title", "function6");
        o3.put("src", "https://img.alicdn.com/tps/i2/TB1CpD7IXXXXXbSXXXXUAkPJpXX-87-87.png");
        o3.put("path", "function6/index.js");
        a.add(o1);
        a.add(o2);
        a.add(o3);
        Log.d("test", "a.toString() = " + a.toString());
        callback.invoke(a.toString());
    }

}

