package com.zk.myweex;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.common.WXException;
import com.zk.myweex.extend.adapter.PicassoImageAdapter;
import com.zk.myweex.extend.module.MyTab2;
import com.zk.myweex.utils.VersionManager;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Administrator on 2017/2/22.
 */

public class WXApplication extends Application {

    public static String PATH = "/mnt/sdcard/kiway/yjpt/";

    public static String PATH_TMP = "/mnt/sdcard/kiway/tmp/";

    public static String PATH_BACKUP = "/mnt/sdcard/kiway/backup/";

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);

        VersionManager manager = new VersionManager();
        manager.init(this);
        manager.getLocalVersion();
        manager.getRemoteVersion();

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
            WXSDKEngine.registerModule("my_tab2", MyTab2.class);
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
