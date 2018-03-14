package cn.kiway.marketplace;

import android.app.Application;

import com.androidev.download.DefaultNotifier;
import com.androidev.download.DownloadManager;

/**
 * Created by Administrator on 2018/1/23.
 */

public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        DownloadManager.getInstance().initialize(this, 3);
        DownloadManager.getInstance().setDownloadNotifier(new DefaultNotifier(this));
    }
}
