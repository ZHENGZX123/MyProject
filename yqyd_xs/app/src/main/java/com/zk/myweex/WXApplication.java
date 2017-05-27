package com.zk.myweex;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.common.WXException;
import com.tencent.bugly.crashreport.CrashReport;
import com.zk.myweex.extend.adapter.UniversalImageAdapter;
import com.zk.myweex.extend.component.MyEditText;
import com.zk.myweex.extend.component.MyListView;
import com.zk.myweex.extend.component.MyWebView2;
import com.zk.myweex.extend.module.SJEventModule;

import java.io.File;

import cn.kiway.baas.sdk.Configure;

/**
 * Created by Administrator on 2017/2/22.
 */

public class WXApplication extends Application {

    public static String ROOT = "/mnt/sdcard/kiway/yqyd_xs/";

    public static String PATH = ROOT + "weex/";

    public static String PATH_TMP = ROOT + "tmp/";

    public static String PATH_BACKUP = ROOT + "backup/";

    public static String PATH_PATCH = ROOT + "patch/";

    public static String PATH_APK = ROOT + "apk/";


    public Activity currentActivity;

    public static long time;

    @Override
    public void onCreate() {
        super.onCreate();

        //xizhou sdk init
        Configure.getInstance().setHost("zip.kiway.cn");
        Configure.getInstance().setPort(3000);
        Configure.getInstance().setRoot("api");
        Configure.init("5b4e6117143c2abd97a51a04303987bc", "3a13696a633e49d20ee6bf4bee9f5874c64aa57b", "");

        //universal-image-loader初始化
        initImageCache();

        //初始化weex
        WXSDKEngine.initialize(this, new InitConfig.Builder()
                .setImgAdapter(new UniversalImageAdapter(this)).build());

        //注册自定义组件
        try {
            WXSDKEngine.registerComponent("mylistview", MyListView.class);
            WXSDKEngine.registerComponent("mywebview2", MyWebView2.class);
            WXSDKEngine.registerComponent("myedittext", MyEditText.class);
            WXSDKEngine.registerModule("SJevent", SJEventModule.class);
        } catch (WXException e) {
            e.printStackTrace();
        }

        //bugly
        CrashReport.initCrashReport(getApplicationContext(), "528f1459f9", false);

        registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                Log.d("lifecycle", "onActivityCreated = " + activity.getLocalClassName());
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.d("lifecycle", "onActivityStarted = " + activity.getLocalClassName());
            }

            @Override
            public void onActivityResumed(Activity activity) {
                currentActivity = activity;
                Log.d("lifecycle", "onActivityResumed = " + activity.getLocalClassName());
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.d("lifecycle", "onActivityPaused = " + activity.getLocalClassName());
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Log.d("lifecycle", "onActivityStopped = " + activity.getLocalClassName());
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                Log.d("lifecycle", "onActivitySaveInstanceState = " + activity.getLocalClassName());
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.d("lifecycle", "onActivityDestroyed = " + activity.getLocalClassName());
                // The demo code of calling 'notifyTrimMemory()'
                if (false) {
                    // We assume that the application is on an idle time.
                    WXSDKManager.getInstance().notifyTrimMemory();
                }
            }
        });
    }

    private void initImageCache() {
        DisplayMetrics displayMetrics = getApplicationContext().getResources()
                .getDisplayMetrics();

        // // 设置默认显示情况
        DisplayImageOptions.Builder displayImageOptionsBuilder = new DisplayImageOptions.Builder();
        // displayImageOptionsBuilder.showImageForEmptyUri(R.drawable.loading);
        // // 空uri的情况
        displayImageOptionsBuilder.cacheInMemory(false); // 缓存在内存
        displayImageOptionsBuilder.cacheOnDisc(true); // 缓存在磁盘
        displayImageOptionsBuilder.bitmapConfig(Bitmap.Config.ARGB_8888);
        displayImageOptionsBuilder
                .imageScaleType(ImageScaleType.NONE);

        File cacheDir = StorageUtils.getOwnCacheDirectory(this,
                "imageloader/weex");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this)
                .memoryCacheExtraOptions(displayMetrics.widthPixels,
                        displayMetrics.heightPixels)
                // maxwidth, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(10)
                // 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 1)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCacheExtraOptions(200, 200)
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                // You can pass your own memory cache
                // implementation/你可以通过自己的内存缓存实现
                // .memoryCacheSize(2 * 1024 * 1024)
                // .discCacheSize(50 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                // 将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(100)
                // 缓存的文件数量
//                .discCache(new UnlimitedDiscCache(cacheDir))
                // 自定义缓存路径
                .denyCacheImageMultipleSizesInMemory()
                // .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .defaultDisplayImageOptions(displayImageOptionsBuilder.build())
                .imageDownloader(
                        new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // connectTimeout
                /* .writeDebugLogs() */.build(); // Remove for release app
        ImageLoader.getInstance().init(config);
    }

//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(this);
//    }
}
