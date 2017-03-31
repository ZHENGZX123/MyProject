package com.crazy.wang.photoscan.application;

import android.app.Application;
import android.content.Context;

import com.crazy.wang.photoscan.util.DisplayImageOptionsUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * @author Cloudsoar(wangyb)
 * @datetime 2015-11-19 19:57 GMT+8
 * @email 395044952@qq.com
 */
public class CSApplication extends Application {
    private static CSApplication mApplication;
    public final ImageLoader imageLoader = ImageLoader.getInstance();

    public static synchronized CSApplication getInstance() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        initImageLoader(getApplicationContext());
    }

    /**
     * 初始化ImageLoader
     *
     * @param context
     */
    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(
                context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);// 线程池中线程的个数
        config.denyCacheImageMultipleSizesInMemory();// 拒绝缓存多个图片
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());// 将保存的时候的URI名称用MD5
        // 加密
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);// 设置图片下载和显示的工作队列排序
        // config.writeDebugLogs(); // 打开调试日志,删除不显示日志
        config.defaultDisplayImageOptions(DisplayImageOptionsUtil.defaultOptions);// 显示图片的参数
        // config.diskCache(new UnlimitedDiskCache(cacheDir));//自定义缓存路径

        ImageLoader.getInstance().init(config.build());
    }
}
