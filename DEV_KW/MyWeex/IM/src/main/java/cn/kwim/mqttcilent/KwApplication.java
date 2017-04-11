package cn.kwim.mqttcilent;


import android.graphics.Bitmap;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.io.File;

import cn.kiway.App;
import cn.kiway.IConstant;
import cn.kwim.mqttcilent.common.cache.KwMigration;
import cn.kwim.mqttcilent.mqttclient.mq.Conf;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Administrator on 2017/1/5.
 */

public class KwApplication extends App {
    //分配的可用内存
    private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();

    //使用的缓存数量
    private static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 4;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Realm数据库
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("yjpty.realm")
                .schemaVersion(1)
                .migration(new KwMigration())
                .build();
        Realm.setDefaultConfiguration(realmConfig);

        //初始化mqtt
        Conf.getInstance().init(this);

        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(this).setMaxCacheSize(2 * 1024 * 2014)
                .setBaseDirectoryName(IConstant.DOWNLOAD_PHOTO_FLODER)
                .setBaseDirectoryPathSupplier(new Supplier<File>() {
                    @Override
                    public File get() {
                        return getCacheDir();
                    }
                }).build();
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)
                .setMainDiskCacheConfig(diskCacheConfig)
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .build();
        Fresco.initialize(this, config);
    }
}
