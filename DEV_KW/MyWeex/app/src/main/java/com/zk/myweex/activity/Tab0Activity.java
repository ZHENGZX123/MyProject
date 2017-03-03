package com.zk.myweex.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import com.zk.myweex.R;
import com.zk.myweex.WXApplication;
import com.zk.myweex.entity.ZipPackage;

import java.io.File;

import cn.kiway.baas.sdk.KWQuery;
import cn.kiway.baas.sdk.model.service.Package;
import cn.kiway.baas.sdk.model.service.Service;
import io.realm.Realm;

public class Tab0Activity extends WXBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab0);

        setContainer((ViewGroup) findViewById(R.id.index_container));
        getSupportActionBar().hide();

//        renderPage(WXFileUtils.loadAsset("yjpt/tab0.js", this), "file://assets/");

        new Thread() {
            @Override
            public void run() {
                try {
                    String zipName = "tab0.zip";
                    String path = WXApplication.PATH + "tab0.zip";
                    ZipPackage zip = Realm.getDefaultInstance().where(ZipPackage.class).equalTo("name", zipName).findFirst();
                    if (new File(path).exists() && zip != null) {
                        Log.d("test", "存在，直接加载");
                        loadJSBundle(zipName, zip.indexPath);
                    } else {
                        Log.d("test", "不存在，下载");
                        Service s = new Service().findOne(new KWQuery().equalTo("name", "tab0"));
                        Log.d("test", "s  = " + s.toString());
                        Package p = new Package().findOne(new KWQuery().equalTo("serviceId", s.getId()).descending("version"));
                        Log.d("test", "p = " + p.toString());
                        String baseUrl = s.get("baseUrl").toString();
                        String downloadUrl = p.get("url").toString();
                        String version = p.get("version").toString();
                        downloadJSBundle(zipName, downloadUrl, version, baseUrl);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
