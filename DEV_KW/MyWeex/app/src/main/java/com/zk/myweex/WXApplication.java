package com.zk.myweex;

import android.app.Activity;
import android.app.Application;
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
import com.zk.myweex.entity.HttpCache;
import com.zk.myweex.extend.adapter.UniversalImageAdapter;
import com.zk.myweex.extend.module.LoginModule;
import com.zk.myweex.extend.module.MyHttpCache;
import com.zk.myweex.extend.module.MyModule;
import com.zk.myweex.extend.module.WXEventModule;
import com.zk.myweex.mqttclient.MqttInstance;
import com.zk.myweex.mqttclient.mq.Conf;
import com.zk.myweex.utils.FileUtils;

import java.io.File;

import cn.kiway.baas.sdk.Configure;
import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmResults;
import io.realm.RealmSchema;

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

        //xizhou sdk init
        Configure.getInstance().setHost("192.168.8.215");
        Configure.getInstance().setPort(4000);
        Configure.getInstance().setRoot("admin");

        //初始化mqtt
        Conf.getInstance().init(this);
        //mqttSdk 初始化
        MqttInstance.init(this);

        //realm初始化
        //.migration(migration)
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().schemaVersion(0).deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        //清掉时间差大于1天的数据
        long current = System.currentTimeMillis();
        long current_before_1day = current - 24 * 60 * 60 * 1000;
        final RealmResults<HttpCache> caches = Realm.getDefaultInstance().where(HttpCache.class).lessThan("requestTime", current_before_1day).findAll();
        Log.d("test", "temp.count = " + caches.size());
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                caches.deleteAllFromRealm();
            }
        });

        //universal-image-loader初始化
        initImageCache();

        //初始化weex
        WXSDKEngine.initialize(this, new InitConfig.Builder()
                .setImgAdapter(new UniversalImageAdapter()).build());

        //注册自定义组件
        try {
            WXSDKEngine.registerModule("my_module", MyModule.class);
            WXSDKEngine.registerModule("my_httpcache", MyHttpCache.class);
            WXSDKEngine.registerModule("login_module", LoginModule.class);
            WXSDKEngine.registerModule("event", WXEventModule.class);
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

    //realm数据库migration
    RealmMigration migration = new RealmMigration() {
        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
            Log.d("test", "db old = " + oldVersion + " , new = " + newVersion);
            RealmSchema schema = realm.getSchema();
            if (oldVersion == 0) {
//                schema.get("HttpCache").addField("add1", String.class);
//                oldVersion++;
            }
        }

        @Override
        public boolean equals(Object other) {
            if (other == null) {
                return false;
            }
            return getClass() == other.getClass();
        }

        @Override
        public int hashCode() {
            return getClass().hashCode();
        }
    };

    private void initImageCache() {
        DisplayMetrics displayMetrics = getApplicationContext().getResources()
                .getDisplayMetrics();

        // // 设置默认显示情况
        DisplayImageOptions.Builder displayImageOptionsBuilder = new DisplayImageOptions.Builder();
        // displayImageOptionsBuilder.showImageForEmptyUri(R.drawable.loading);
        // // 空uri的情况
        displayImageOptionsBuilder.cacheInMemory(false); // 缓存在内存
        displayImageOptionsBuilder.cacheOnDisc(true); // 缓存在磁盘
        displayImageOptionsBuilder
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2);

        File cacheDir = StorageUtils.getOwnCacheDirectory(this,
                "imageloader/weex");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this)
                .memoryCacheExtraOptions(displayMetrics.widthPixels,
                        displayMetrics.heightPixels)
                // maxwidth, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(5)
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
}
