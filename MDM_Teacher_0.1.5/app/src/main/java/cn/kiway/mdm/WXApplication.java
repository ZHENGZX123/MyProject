package cn.kiway.mdm;

import android.app.Application;

import com.tencent.smtt.sdk.QbSdk;

import org.xutils.x;

/**
 * Created by Administrator on 2017/7/5.
 */

public class WXApplication extends Application {

//    public static String url = "http://202.104.136.9:8084";
//    public static String uploadUrl = "http://202.104.136.9:8083";

    public static String url = "http://www.yuertong.com:8084";
    public static String uploadUrl = "http://www.yuertong.com:8083";

    public static String ROOT = "/mnt/sdcard/kiway_mdm_teacher/";
    public static String HTML = "mdm_teacher/dist/index.html";
    public static String ZIP = "mdm_teacher.zip";

    @Override
    public void onCreate() {
        super.onCreate();
        //xutils
        x.Ext.init(this);
        //x5
        initTBS();
    }

    /**
     * 初始化TBS浏览服务X5内核
     */
    private void initTBS() {
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), null);
    }
}
