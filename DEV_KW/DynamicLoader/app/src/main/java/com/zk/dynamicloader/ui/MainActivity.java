package com.zk.dynamicloader.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.zk.dynamicloader.R;
import com.zk.dynamicloader.utils.Constant;
import com.zk.dynamicloader.utils.HttpDownload;
import com.zk.dynamicloader.utils.InvokeUtil;
import com.zk.dynamicloader.utils.Utils;

import java.io.File;

//mdm jar测试例子
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        degrade(null);
    }

    public void test1(View view) {
        //test
//        InvokeUtil util = new InvokeUtil(this, "/mnt/sdcard/test.jar", "cn.kiway.dynamic.MDMUtil", "isStatusBarExpandPanelDisabled");
//        util.getMethod();
//        String ret = util.invokeMethod();
//        toast(ret);

        //lenovo
//        InvokeUtil util = new InvokeUtil(this, "/mnt/sdcard/test.jar", "android.app.mia.MiaMdmPolicyManager", "shutDown");
//        util.getMethod();
//        String ret = util.invokeMethod(this);
//        toast(ret);

        //jichengshixun
        InvokeUtil util = new InvokeUtil(this, "/mnt/sdcard/test.jar", "com.android.mdm.MdmPolicyManager", "reboot");//shutDown
        util.getMethod();
        String ret = util.invokeMethod(this);
        toast(ret);
    }

    public void test2(View view) {
        //test
        InvokeUtil util = new InvokeUtil(this, "/mnt/sdcard/test.jar", "cn.kiway.dynamic.MDMUtil", "setStatusBarExpandPanelDisabled");
        util.getMethod(boolean.class);
        String ret = util.invokeMethod(true);
        toast(ret);


    }

    public void upgrade(View view) {
        if (new File("/mnt/sdcard/test.jar").exists()) {
            new File("/mnt/sdcard/test.jar").delete();
        }
        new Thread() {
            @Override
            public void run() {
                super.run();
                int i = new HttpDownload().downFile(Constant.downloadUrl, "/mnt/sdcard/", "test.jar");
                if (i == 0) {
                    toast("升级成功");
                } else {
                    toast("升级失败");
                }
            }
        }.start();
    }

    public void degrade(View view) {
        if (new File("/mnt/sdcard/test.jar").exists()) {
            new File("/mnt/sdcard/test.jar").delete();
        }
        Utils.copyDBToSD(this, "/mnt/sdcard/", "test.jar");
        toast("降级成功");
    }

    private void toast(final String txt) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, txt, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
