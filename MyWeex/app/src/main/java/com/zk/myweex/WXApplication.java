package com.zk.myweex;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.common.WXException;
import com.zk.myweex.extend.adapter.PicassoImageAdapter;
import com.zk.myweex.extend.module.MyHttp;
import com.zk.myweex.extend.module.MyModule;
import com.zk.myweex.file.FileUtils;
import com.zk.myweex.mqttclient.MqttInstance;
import com.zk.myweex.mqttclient.mq.Conf;

import java.io.File;

import cn.kiway.baas.sdk.Configure;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Administrator on 2017/2/22.
 */

public class WXApplication extends Application {

    public static String ROOT = "/mnt/sdcard/kiway/";

    public static String PATH = "/mnt/sdcard/kiway/weex/";

    public static String PATH_TMP = "/mnt/sdcard/kiway/tmp/";

    public static String PATH_BACKUP = "/mnt/sdcard/kiway/backup/";

    public static String PATH_PATCH = "/mnt/sdcard/kiway/patch/";



    @Override
    public void onCreate() {
        super.onCreate();

        if (getSharedPreferences("kiway", 0).getBoolean("isFirst", true)) {
            if (new File(WXApplication.ROOT).exists()) {
                FileUtils.delFolder(WXApplication.ROOT);
            }
            getSharedPreferences("kiway", 0).edit().putBoolean("isFirst", false).commit();
        }

        //sdk init
        Configure.getInstance().setHost("192.168.8.28");
        Configure.getInstance().setPort(4000);
        Configure.getInstance().setRoot("admin");

        //初始化mqtt
        Conf.getInstance().init(this);
        //mqttSdk 初始化
        MqttInstance.init(this);

        //realm初始化
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);


//    initDebugEnvironment(true, false, "DEBUG_SERVER_HOST");
        WXSDKEngine.addCustomOptions("appName", "WXSample");
        WXSDKEngine.addCustomOptions("appGroup", "WXApp");
        WXSDKEngine.initialize(this,
                new InitConfig.Builder()
                        .setImgAdapter(new PicassoImageAdapter())
                        .build()
        );

        //注册自定义组件
        try {
            WXSDKEngine.registerModule("my_module", MyModule.class);
            WXSDKEngine.registerModule("my_http", MyHttp.class);
        } catch (WXException e) {
            e.printStackTrace();
        }

        registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                // The demo code of calling 'notifyTrimMemory()'
                if (false) {
                    // We assume that the application is on an idle time.
                    WXSDKManager.getInstance().notifyTrimMemory();
                }
            }
        });

    }
}
