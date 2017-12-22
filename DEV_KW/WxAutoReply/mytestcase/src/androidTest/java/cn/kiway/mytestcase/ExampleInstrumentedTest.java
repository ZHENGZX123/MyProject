package cn.kiway.mytestcase;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Environment;
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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Administrator on 2017/12/18.
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    public static final String WECHAT_PACKAGE_NAME = "com.tencent.mm";
    Context mContext;
    UiDevice mDevice;
    private static final int LONG_TIMEOUT = 10000;

    public ExampleInstrumentedTest() {
        super();
    }

    @Before
    public void setUp(String name) throws UiObjectNotFoundException, InterruptedException, IOException {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mContext = InstrumentationRegistry.getContext();
        testSetting(name);
    }

    @Test
    public void testSetting(String name) throws UiObjectNotFoundException, InterruptedException, IOException {
        if (isWeChatInstall(mContext)) {
            // 启动微信
            startWeChat(mContext);
            Log.e("-----", System.currentTimeMillis() + "");
            // 等待微信显示出来
            mDevice.wait(Until.hasObject(By.pkg(WECHAT_PACKAGE_NAME).depth(0)), LONG_TIMEOUT);
            UiAutomatorTestCase u = new UiAutomatorTestCase();
            //  Home键
            //mDevice.pressHome();
            // 找到设置并点击
            mDevice.findObject(new UiSelector().text("微信")).click();
            // //u.sleep(2000);
//            getTextView();
//            //u.sleep(2000);
//            mDevice.findObject(new UiSelector().text("通讯录")).click();
            //  //u.sleep(2000);
            mDevice.findObject(new UiSelector().text(name)).click();
            // //u.sleep(2000);
            // mDevice.findObject(new UiSelector().text("发消息")).click();

            // //u.sleep(2000);
            //自动发送3个长度随机的文字消息
            autoSendMessage(mDevice, "data/local/input/test.txt", 1);
            //  //u.sleep(2000);
            //自动发送3个长度随机的语音消息
//        autoSendVoice(mDevice,1);
//        //u.sleep(2000);
            //随机发三张照片
            // autoSendImage(mDevice, 3);
            ////u.sleep(2000);

            //找到返回键并返回
            // UiSelector uiSelector = new UiSelector().resourceId("com.tencent.mm:id/h4");
            // UiObject switcher = new UiObject(uiSelector);
            // 判断该控件是否存在
            // if (switcher.exists()) {
            //点击该控件
            // switcher.click();
            //   }
            //  autoSendWordsToFriendsCircle(mDevice, "data/local/input/test.txt", 1);
            //u.sleep(2000);

            //autoSendPictureToFriendsCircle(mDevice, 2);
            //u.sleep(2000);
            // mDevice.pressHome();
            Log.e("-----", System.currentTimeMillis() + "");
        }
    }

    public void getTextView() throws UiObjectNotFoundException {
        UiObject editText = new UiObject(new UiSelector().className("android.view.View"));
        Log.e("--------------", editText.getText().toString());
    }

    //从手机中的文件filePath中随机取一段不定长度的字符串发送
    public void autoSendMessage(UiDevice mDevice, String filePath, int messageNum) throws FileNotFoundException,
            IOException, UiObjectNotFoundException, InterruptedException {
        UiAutomatorTestCase u = new UiAutomatorTestCase();
        UiObject editText = new UiObject(new UiSelector().className("android.widget.EditText"));
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard, filePath);
//        BufferedReader br = new BufferedReader(new FileReader(file));
        //  String resStr = br.readLine();
        while (messageNum != 0) {
            int[] begEnd = randomCommon(0, "我是测试的".length(), 2);
            String sendStr = null;
            if (begEnd[0] > begEnd[1])
                sendStr = "我是测试的".substring(begEnd[1], begEnd[0]);
            else
                sendStr = "我是测试的".substring(begEnd[0], begEnd[1]);
            editText.setText(sendStr);
            // u.sleep(3000);
//            mDevice.findObject(new UiSelector().text("发送")).click();
            UiSelector uiSelector = new UiSelector().text("发送");
            UiObject switcher = new UiObject(uiSelector);
            if (!switcher.exists())
                continue;
            //点击该控件
            switcher.click();
            //u.sleep(2000);
            messageNum--;
        }
        //br.close();
    }

    public int[] randomCommon(int min, int max, int n) {
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while (count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if (num == result[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                result[count] = num;
                count++;
            }
        }
        return result;
    }

    public void autoSendVoice(UiDevice mDevice, int messageNum) throws UiObjectNotFoundException, InterruptedException {
        UiAutomatorTestCase u = new UiAutomatorTestCase();
        //点击文字切换语音的按钮
        UiSelector uiSelector = new UiSelector().resourceId("com.tencent.mm:id/a_z");
        UiObject switcher2voice = new UiObject(uiSelector);
        switcher2voice.click();
        //u.sleep(2000);
        while (messageNum != 0) {
            UiObject voiceButton = new UiObject(new UiSelector().resourceId("com.tencent.mm:id/a3c"));
            if (!voiceButton.exists())
                continue;
            Rect voiceButton_rect = voiceButton.getBounds();
            int[] randTime = randomCommon(100, 1500, 1);
            mDevice.swipe(voiceButton_rect.centerX(), voiceButton_rect.centerY(), voiceButton_rect.centerX(),
                    voiceButton_rect.centerY(), randTime[0]);
            //u.sleep(2000);
            messageNum--;
        }
        switcher2voice.click();
    }

    public void autoSendImage(UiDevice mDevice, int Num) throws UiObjectNotFoundException, InterruptedException {
        int messageNum = Num;
        while (messageNum != 0) {
            UiAutomatorTestCase u = new UiAutomatorTestCase();
            //点击右下角的“+”按钮
            UiSelector plusSelector = new UiSelector().resourceId("com.tencent.mm:id/aa6");
            UiObject switcher2image = new UiObject(plusSelector);
            switcher2image.click();
            //u.sleep(2000);
            //点击“相册”
            UiSelector photoSelector = new UiSelector().resourceId("com.tencent.mm:id/o3");
            UiObject switcher2photo = new UiObject(photoSelector);
            switcher2photo.click();
            //u.sleep(2000);
            //选择照片
            int time = Num - messageNum;
            while (time != 0) {
                //从坐标[482,871][719,1108]滑到[482,148][719,385]
                mDevice.swipe((719 - 482) / 2 + 482, (1108 - 871) / 2 + 871, (719 - 482) / 2 + 482, (385 - 148) / 2 +
                        148, 10);
                //u.sleep(1000);
                time--;
            }
            UiObject gridview = new UiObject(new UiSelector().resourceId("com.tencent.mm:id/csk"));
            int[] randId2photo = randomCommon(0, gridview.getChildCount(), 1);
            UiObject relativeLayout = gridview.getChild(new UiSelector().className("android.widget.RelativeLayout")
                    .index(randId2photo[0]));
            UiObject checkBox = relativeLayout.getChild(new UiSelector().className("android.widget.CheckBox"));
            if (!checkBox.exists())
                continue;
            checkBox.click();
            //u.sleep(2000);
            //点击“发送”按钮
            UiObject sendPhotoBut = new UiObject(new UiSelector().resourceId("com.tencent.mm:id/h1"));
            sendPhotoBut.click();
            //u.sleep(2000);

            messageNum--;
        }
    }

    public void autoSendWordsToFriendsCircle(UiDevice mDevice, String filePath, int messageNum) throws IOException,
            UiObjectNotFoundException, InterruptedException {
        UiAutomatorTestCase u = new UiAutomatorTestCase();
        mDevice.findObject(new UiSelector().text("发现")).click();
        //u.sleep(2000);
        while (messageNum != 0) {
            //点击朋友圈
            UiObject friendsCirle = new UiObject(new UiSelector().resourceId("com.tencent.mm:id/a95"));
            friendsCirle.click();
            //u.sleep(2000);
            //长按“相机”图标，可以发送纯文字的朋友圈
            UiObject button = new UiObject(new UiSelector().resourceId("com.tencent.mm:id/fx"));
            Rect button_rect = button.getBounds();
            mDevice.swipe(button_rect.centerX(), button_rect.centerY(), button_rect.centerX(), button_rect.centerY(),
                    200);
            //u.sleep(2000);
            //输入文字
            UiObject editText = new UiObject(new UiSelector().resourceId("com.tencent.mm:id/d5v"));
            File sdcard = Environment.getExternalStorageDirectory();
            File file = new File(sdcard, filePath);
            //  BufferedReader br = new BufferedReader(new FileReader(file));
            String resStr = "测试法朋友圈";
            int[] begEnd = randomCommon(0, resStr.length(), 2);
            String sendStr = null;
            if (begEnd[0] > begEnd[1])
                sendStr = resStr.substring(begEnd[1], begEnd[0]);
            else
                sendStr = resStr.substring(begEnd[0], begEnd[1]);
            editText.setText(sendStr);
            //u.sleep(2000);
            //点击发送
            UiObject sendButton = new UiObject(new UiSelector().resourceId("com.tencent.mm:id/h1"));
            sendButton.click();
            //u.sleep(2000);
            //点击返回
            UiObject backButton = new UiObject(new UiSelector().resourceId("com.tencent.mm:id/hj"));
            backButton.click();
            //u.sleep(2000);
            messageNum--;
            //  br.close();
        }
    }

    public void autoSendPictureToFriendsCircle(UiDevice mDevice, int messageNum) throws IOException,
            UiObjectNotFoundException, InterruptedException {
        UiAutomatorTestCase u = new UiAutomatorTestCase();
        mDevice.findObject(new UiSelector().text("发现")).click();
        //u.sleep(2000);
        while (messageNum != 0) {
            //点击朋友圈
            UiObject friendsCirle = new UiObject(new UiSelector().resourceId("com.tencent.mm:id/a95"));
            friendsCirle.click();
            //u.sleep(2000);
            //短“相机”图标，可以发送图片的朋友圈
            UiObject button = new UiObject(new UiSelector().resourceId("com.tencent.mm:id/fx"));
            button.click();
            //u.sleep(2000);
            //点击“从相册选择”
            UiObject tempObj = new UiObject(new UiSelector().text("从相册选择"));
            tempObj.click();
            //u.sleep(2000);
            //选择照片,先从1到9随机选要给整数作为照片的数量
            int[] picNum = randomCommon(1, 9, 1);
            int curPicNum = picNum[0];
            while (curPicNum != 0) {
//                int time = picNum[0] - curPicNum;
//                while(time!=0)
//                {
                //从坐标[482,871][719,1108]滑到[482,148][719,385]
                mDevice.swipe((719 - 482) / 2 + 482, (1108 - 871) / 2 + 871, (719 - 482) / 2 + 482, (385 - 148) / 2 +
                        148, 10);
                u.sleep(1000);
//                    time--;
//                }
                UiObject gridview = new UiObject(new UiSelector().resourceId("com.tencent.mm:id/csk"));
                int[] randId2photo = randomCommon(0, gridview.getChildCount(), 1);
                UiObject relativeLayout = gridview.getChild(new UiSelector().className("android.widget" +
                        ".RelativeLayout").index(randId2photo[0]));
                UiObject checkBox = relativeLayout.getChild(new UiSelector().className("android.widget.CheckBox"));
                if (!checkBox.exists())
                    continue;
                checkBox.click();
                u.sleep(1000);
                curPicNum--;
            }
            //点击“完成”按钮
            UiObject finishPhotoBut = new UiObject(new UiSelector().resourceId("com.tencent.mm:id/h1"));
            finishPhotoBut.click();
            //u.sleep(2000);
            //点击“发送”按钮
            UiObject sendPhotoBut = new UiObject(new UiSelector().resourceId("com.tencent.mm:id/h1"));
            sendPhotoBut.click();
            //u.sleep(2000);
            messageNum--;
            //点击返回
            UiObject backButton = new UiObject(new UiSelector().resourceId("com.tencent.mm:id/hj"));
            backButton.click();
            //u.sleep(2000);
        }
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
