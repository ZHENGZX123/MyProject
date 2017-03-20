package com.zk.myweex.extend.module;

import android.app.Activity;
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
import com.zk.myweex.utils.HttpDownload;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.kiway.baas.sdk.KWQuery;
import cn.kiway.baas.sdk.model.service.Package;
import cn.kiway.baas.sdk.model.service.Service;
import io.realm.Realm;
import io.realm.RealmResults;


public class MyModule extends WXModule {

    @JSMethod(uiThread = true)
    public void loadFunction(final String zipName, final JSCallback callback) {
        Toast.makeText(mWXSDKInstance.getContext(), " loadJSBundle js :" + zipName, Toast.LENGTH_SHORT).show();
        new Thread() {
            @Override
            public void run() {
                try {
                    load(zipName, callback);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void load(final String zipName, final JSCallback callback) throws Exception {
        Log.d("test", "load name = " + zipName);
        String path = WXApplication.PATH + zipName;
        if (new File(path).exists()) {
            Log.d("test", "存在，直接加载");
            ZipPackage zip = Realm.getDefaultInstance().where(ZipPackage.class).equalTo("name", zipName).findFirst();
            loadJSBundle(zipName, zip.indexPath);
        } else {
            Log.d("test", "不存在，下载");
            Service s = new Service().findOne(new KWQuery().equalTo("id", zipName.replace(".zip", "")));
            Log.d("test", "s  = " + s.toString());
            //返回最新的全量包
            Package p = new Package().findOne(new KWQuery().equalTo("serviceId", s.getId()).equalTo("updateType", "all").equalTo("platform", "android").descending("version"));
            Log.d("test", "p = " + p.toString());
            String baseUrl = s.get("baseUrl").toString();
            String downloadUrl = p.get("url").toString();
            String version = p.get("version").toString();
            downloadJSBundle(zipName, downloadUrl, version, baseUrl);
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
    public void downloadJSBundle(final String zipName, final String downloadUrl, final String version, final String baseUrl) {
        //1.访问接口，参数是zipName，返回是 name， 下载地址 ， 版本号
        HttpDownload httpDownload = new HttpDownload();
        int ret = httpDownload.downFile(downloadUrl, WXApplication.PATH, zipName);
        Log.d("test", "下载返回值 ret = " + ret);
        if (ret != 0) {
            toast("下载失败，请稍后再试");
            return;
        }
        Log.d("test", "下载成功，保存版本号");

        Realm.getDefaultInstance().beginTransaction();
        ZipPackage zip = Realm.getDefaultInstance().createObject(ZipPackage.class);
        zip.name = zipName;
        zip.indexPath = baseUrl;
        zip.version = version;
        Realm.getDefaultInstance().commitTransaction();
        Log.d("test", "下载成功，加载本地sdcard");
        loadJSBundle(zipName, baseUrl);
    }

    public void loadJSBundle(String zipName, String baseUrl) {
        String path = WXApplication.PATH + zipName + "/" + baseUrl;
        Log.d("test", "loadJSBundle path = " + path);
        Intent intent = new Intent(mWXSDKInstance.getContext(), WXPageActivity.class);
        intent.setData(Uri.parse(path));
        mWXSDKInstance.getContext().startActivity(intent);
    }

    private void toast(final String txt) {
        ((Activity) mWXSDKInstance.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mWXSDKInstance.getContext(), txt, Toast.LENGTH_SHORT).show();
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

        //根据tab名字，查找本机已经下载的function包
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


    @JSMethod(uiThread = true)
    public void sendEvent(JSCallback callback) {
        Log.d("test", "module id = " + mWXSDKInstance.getInstanceId());
        Map<String, Object> params = new HashMap<>();
        params.put("test1", "test1");
        params.put("test2", "test2");
        //这个只能调用相同instance的事件。
//        mWXSDKInstance.fireGlobalEventCallback("tab1_event", params);
        //这个只能callback
//        callback.invoke(params);
        mWXSDKInstance.fireSuperGlobalEventCallback("tab1_event", params);
    }


    @JSMethod(uiThread = true)
    public void showLog(String tag, String log) {
        Log.d(tag, log);
    }

}

