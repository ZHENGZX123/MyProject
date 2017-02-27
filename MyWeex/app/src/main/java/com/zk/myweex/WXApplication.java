package com.zk.myweex;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.common.WXException;
import com.zk.myweex.adapter.DefaultWebSocketAdapterFactory;
import com.zk.myweex.adapter.JSExceptionAdapter;
import com.zk.myweex.adapter.PicassoImageAdapter;
import com.zk.myweex.extend.module.MyTab2;

/**
 * Created by Administrator on 2017/2/22.
 */

public class WXApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * Set up for fresco usage.
         * Set<RequestListener> requestListeners = new HashSet<>();
         * requestListeners.add(new RequestLoggingListener());
         * ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
         *     .setRequestListeners(requestListeners)
         *     .build();
         * Fresco.initialize(this,config);
         **/
//    initDebugEnvironment(true, false, "DEBUG_SERVER_HOST");
        WXSDKEngine.addCustomOptions("appName", "WXSample");
        WXSDKEngine.addCustomOptions("appGroup", "WXApp");
        WXSDKEngine.initialize(this,
                new InitConfig.Builder()
                        //.setImgAdapter(new FrescoImageAdapter())// use fresco adapter
                        .setImgAdapter(new PicassoImageAdapter())
//                        .setDebugAdapter(new PlayDebugAdapter())
                        .setWebSocketAdapterFactory(new DefaultWebSocketAdapterFactory())
                        .setJSExceptionAdapter(new JSExceptionAdapter())
                        .build()
        );

        Fresco.initialize(this);

        //注册组件
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
