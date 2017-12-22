package cn.kiway.mytestcase;

/**
 * Created by Administrator on 2017/12/20.
 */

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiAutomatorTestCase;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;


@RunWith(AndroidJUnit4.class)
public class TestOne {
    public static final String WECHAT_PACKAGE_NAME = "com.tencent.mm";
    private Context mContext;
    private UiDevice mDevice;
    private static final int LONG_TIMEOUT = 10000;

    @Test
    public void demo() throws UiObjectNotFoundException {
        Log.d("test", "star------------");
        try {
            setUp("浪翻云");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("test", "end-------------");
    }


    public void setUp(String name) throws UiObjectNotFoundException, InterruptedException, IOException {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mContext = InstrumentationRegistry.getContext();
        mDevice.findObject(new UiSelector().text(name)).click();
        //testSetting(name);
    }

    public void testSetting(String name) throws UiObjectNotFoundException, InterruptedException, IOException {
        //先请求后台
        UiAutomatorTestCase u = new UiAutomatorTestCase();
        // 启动微信
        startWeChat(mContext);
        //UiObject x = mDevice.findObject(new UiSelector().text("微信"));
        //x.click();
        // 等待微信显示出来
        mDevice.wait(Until.hasObject(By.pkg(WECHAT_PACKAGE_NAME).depth(0)), LONG_TIMEOUT);
        mDevice.findObject(new UiSelector().text(name)).click();
        UiObject editText = new UiObject(new UiSelector().className("android.widget.EditText"));
        editText.setText("回声：content");


        u.sleep(100);
        mDevice.findObject(new UiSelector().text("发送")).click();
        mDevice.pressHome();
    }


    /**
     * 启动微信
     *
     * @param context
     */
    void startWeChat(Context context) {
        PackageManager pm = context.getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(WECHAT_PACKAGE_NAME);
        if (intent == null) {
            return;
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 检查微信是否安装
     *
     * @param context
     * @return
     */
    boolean isWeChatInstall(Context context) {
        PackageManager pm = context.getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(WECHAT_PACKAGE_NAME);
        if (intent == null) {
            return false;
        }
        return true;
    }
}
