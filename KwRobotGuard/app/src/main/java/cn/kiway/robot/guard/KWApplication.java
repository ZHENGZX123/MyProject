package cn.kiway.robot.guard;

import android.app.Application;
import android.util.Log;

import org.xutils.x;


/**
 * Created by Administrator on 2018/3/21.
 */

public class KWApplication extends Application {

    public static String ROOT = "/mnt/sdcard/kiway_robot_launcher/";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("test", "APP onCreate");
        x.Ext.init(this);
    }


}
