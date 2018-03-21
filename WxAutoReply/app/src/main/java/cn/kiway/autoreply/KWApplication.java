package cn.kiway.autoreply;

import android.app.Application;
import android.util.Log;

/**
 * Created by Administrator on 2018/3/21.
 */

public class KWApplication extends Application {


    public static String ROOT = "/mnt/sdcard/kiway_autoreply/";


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("test", "APP onCreate");
    }
}
