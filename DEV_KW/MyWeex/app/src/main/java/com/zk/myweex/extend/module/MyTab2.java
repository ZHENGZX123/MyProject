package com.zk.myweex.extend.module;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

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


public class MyTab2 extends WXModule {

    private ProgressDialog pd;

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

    private void hidePD() {
        ((Activity) mWXSDKInstance.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pd.dismiss();
            }
        });

    }

    private void loadJSBundle(String zipName) {
        //TODO 假设路径是function1.zip/function1/index.js , 这个路径要求web传过来。
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

