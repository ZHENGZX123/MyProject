package cn.kiway.marketplace;

import android.app.Application;

import com.androidev.download.DefaultNotifier;
import com.androidev.download.DownloadManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by 4ndroidev on 17/4/20.
 */

public class MarketPlaceApplication extends Application {
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new LoggerInterceptor("MarketPlace::::"))
            .connectTimeout(10000L, TimeUnit.MILLISECONDS)
            .readTimeout(10000L, TimeUnit.MILLISECONDS)
            //其他配置
            .build();

    @Override
    public void onCreate() {
        super.onCreate();
        DownloadManager.getInstance().initialize(this, 3);
        DownloadManager.getInstance().setDownloadNotifier(new DefaultNotifier(this));
        OkHttpUtils.initClient(okHttpClient);
    }
}
