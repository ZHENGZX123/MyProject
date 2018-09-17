package cn.kiway.robot;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;

import com.mob.MobSDK;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import org.xutils.x;

import java.io.File;

import cn.kiway.robot.util.CrashHandler;
import cn.kiway.robot.util.Utils;

import static cn.kiway.robot.util.Utils.saveDefaultFile;


/**
 * Created by Administrator on 2018/3/21.
 */

public class KWApplication extends Application {

    public static String ROOT = "/mnt/sdcard/kiway_robot/";
    public static String defaultVideoIcon = ROOT + "/downloads/video.png";
    public static String defaultFileIcon = ROOT + "/downloads/file.png";
    public static String defaultPPTIcon = ROOT + "/downloads/ppt.png";
    public static String defaultPDFIcon = ROOT + "/downloads/pdf.png";
    public static String defaultWordIcon = ROOT + "/downloads/word.png";
    public static String defaultXlsIcon = ROOT + "/downloads/xls.png";
    public static String defaultZIPIcon = ROOT + "/downloads/zip.png";

    public static String defaultWechat = ROOT + "/downloads/wechat.apk";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("test", "APP onCreate");
        x.Ext.init(this);
        initImageCache();
        MobSDK.init(this);
        CrashHandler.getInstance().init(this);

        saveDefaultFile(this, "file.png", R.mipmap.file);
        saveDefaultFile(this, "video.png", R.mipmap.video);
        saveDefaultFile(this, "ppt.png", R.mipmap.ppt);
        saveDefaultFile(this, "pdf.png", R.mipmap.pdf);
        saveDefaultFile(this, "word.png", R.mipmap.word);
        saveDefaultFile(this, "xls.png", R.mipmap.xls);
        saveDefaultFile(this, "zip.png", R.mipmap.zip);
        saveDefaultFile(this, "wechat.apk", "http://robot.kiway.cn/static/download/version/wechat.apk");

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                Log.d("test", "addShutdownHook");
                Utils.closeMQ();
            }
        });

        deleteAllCachedFiles(ROOT);
        deleteAllCachedFiles("/sdcard/DCIM/Camera/");
        ImageLoader.getInstance().clearDiskCache();
    }

    private void deleteAllCachedFiles(String folder) {
        File[] files = new File(folder).listFiles();
        if (files == null || files.length == 0) {
            return;
        }
        for (File f : files) {
            Log.d("test", "f = " + f.getAbsolutePath());
            if (!f.isDirectory() && (f.getName().contains("db") || f.getName().endsWith("jpg"))) {
                Log.d("test", "delete file = " + f.getName());
                f.delete();
            }
        }
    }

    private void initImageCache() {
        DisplayMetrics displayMetrics = getApplicationContext().getResources()
                .getDisplayMetrics();

        //设置默认显示情况
        DisplayImageOptions.Builder displayImageOptionsBuilder = new DisplayImageOptions.Builder();
        // displayImageOptionsBuilder.showImageForEmptyUri(R.drawable.loading);
        // // 空uri的情况
        displayImageOptionsBuilder.cacheInMemory(false); // 缓存在内存
        displayImageOptionsBuilder.cacheOnDisc(true); // 缓存在磁盘
        displayImageOptionsBuilder.bitmapConfig(Bitmap.Config.ARGB_8888);
        displayImageOptionsBuilder
                .imageScaleType(ImageScaleType.NONE);
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
                // .discCache(new UnlimitedDiscCache(cacheDir))
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
//         RoundedBitmapDisplayer displayer = new RoundedBitmapDisplayer(10);
//         displayImageOptionsBuilder.displayer(displayer);
        displayImageOptionsBuilder.displayer(new SimpleBitmapDisplayer());
        DisplayImageOptions defaultDisplayImageOptions = displayImageOptionsBuilder
                .build();
        return defaultDisplayImageOptions;
    }
}
