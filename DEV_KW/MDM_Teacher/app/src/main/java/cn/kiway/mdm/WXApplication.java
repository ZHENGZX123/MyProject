package cn.kiway.mdm;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import com.tencent.smtt.sdk.QbSdk;

import org.xutils.x;

import cn.kiway.mdm.service.RecordService;

/**
 * Created by Administrator on 2017/7/5.
 */

public class WXApplication extends Application {

    public static String serverUrl = "http://192.168.8.161:8085";
    public static String clientUrl = "http://192.168.8.161:8083";

    public static String ROOT = "/mnt/sdcard/kiway_mdm_teacher/";
    public static String HTML = "mdm_teacher/dist/index.html";
    public static String ZIP = "mdm_teacher.zip";

    public static Activity currentActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        //xutils
        x.Ext.init(this);
        //x5
        initTBS();
        //录屏
        startService(new Intent(this, RecordService.class));
    }

    /**
     * 初始化TBS浏览服务X5内核
     */
    private void initTBS() {
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), null);
    }
}
