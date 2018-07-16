package cn.kiway.robot.moment;

import android.content.Context;
import android.util.Log;

import java.io.File;

import dalvik.system.DexClassLoader;

import static cn.kiway.robot.KWApplication.defaultWechat;

/**
 * Created by chiontang on 2/17/16.
 */
public class Task {

    protected Context context = null;
    public SnsReader snsReader = null;

    public Task(Context context) {
        this.context = context;
        this.makeExtDir();
    }

    public void makeExtDir() {
        File extDir = new File(Config.EXT_DIR);
        if (!extDir.exists()) {
            extDir.mkdirs();
        }
    }

    public boolean initSnsReader() {
        File outputAPKFile = new File(defaultWechat);
        if (!outputAPKFile.exists()) {
            Log.d("test", "APK没有下载好");
            return false;
        }
        try {
            Config.initWeChatVersion();
            DexClassLoader cl = new DexClassLoader(
                    outputAPKFile.getAbsolutePath(),
                    context.getDir("outdex", 0).getAbsolutePath(),
                    null,
                    ClassLoader.getSystemClassLoader());
            Class SnsDetail = cl.loadClass(Config.PROTOCAL_SNS_DETAIL_CLASS);
            Class SnsDetailParser = cl.loadClass(Config.SNS_XML_GENERATOR_CLASS);
            Class SnsObject = cl.loadClass(Config.PROTOCAL_SNS_OBJECT_CLASS);
            snsReader = new SnsReader(SnsDetail, SnsDetailParser, SnsObject);

            return true;
        } catch (Throwable e) {
            Log.e("wechatmomentstat", "exception", e);
        }
        return false;
    }
}
