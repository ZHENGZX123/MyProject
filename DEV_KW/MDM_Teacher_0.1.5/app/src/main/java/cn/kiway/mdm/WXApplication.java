package cn.kiway.mdm;

import android.app.Application;
import android.util.Log;

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
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.setDownloadWithoutWifi(true);//非wifi条件下允许下载X5内核
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("test", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }
}
