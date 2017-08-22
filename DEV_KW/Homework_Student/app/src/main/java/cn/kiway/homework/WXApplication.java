package cn.kiway.homework;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Process;
import android.util.DisplayMetrics;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.io.File;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import ly.count.android.api.Countly;

/**
 * Created by Administrator on 2017/7/5.
 */

public class WXApplication extends Application {

    public static String url;
    public static final String zhengshiUrl = "http://zy.xtclass.com:8389";//正式地址
    public static final String ceshiUrl = "http://202.104.136.9:8389";    //测试地址8390

    public static boolean isTest = true;//测试true  正式false

    static {
        if (isTest) {
            url = ceshiUrl;
        } else {
            url = zhengshiUrl;
        }
    }

    public static String ROOT = "/mnt/sdcard/kiway_student/";
    public static String HTML = "xtzy_student/dist/index.html";
    public static String ZIP = "xtzy_student.zip";

    public static String BOOKS = "/mnt/sdcard/books/";

    //小米推送用
    // user your appid the key.
    private static final String APP_ID = "2882303761517595245";
    // user your appid the key.
    private static final String APP_KEY = "5181759540245";


    @Override
    public void onCreate() {
        super.onCreate();

        initImageCache();

        //小米推送
        // 注册push服务，注册成功后会向DemoMessageReceiver发送广播
        // 可以从DemoMessageReceiver的onCommandResult方法中MiPushCommandMessage对象参数中获取注册信息
        if (shouldInit()) {
            MiPushClient.registerPush(this, APP_ID, APP_KEY);
        }

        //countly
        Countly.sharedInstance().init(this, "http://192.168.8.61:80", "482dd339784e8488a4680897ffb117f1e7d90baf");

        //jpush
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
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

    public static DisplayImageOptions getLoaderOptions() {
        DisplayImageOptions.Builder displayImageOptionsBuilder = new DisplayImageOptions.Builder();
        // displayImageOptionsBuilder.showImageForEmptyUri(R.drawable.loading);
        displayImageOptionsBuilder.cacheInMemory(false);
        displayImageOptionsBuilder.cacheOnDisc(true);
        // RoundedBitmapDisplayer displayer = new RoundedBitmapDisplayer(10);
        // displayImageOptionsBuilder.displayer(displayer);
        DisplayImageOptions defaultDisplayImageOptions = displayImageOptionsBuilder
                .build();
        return defaultDisplayImageOptions;
    }
}
