package com.ileja.autoreply;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    Button runBtn;
    public static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runBtn = (Button) findViewById(R.id.runBtn);

        instance = this;
    }

    /**
     * 点击按钮对应的方法
     *
     * @param v
     */
    public void runMyUiautomator(View v) {
        Log.i(TAG, "runMyUiautomator: ");
        new UiautomatorThread().start();
    }

    /**
     * 运行uiautomator是个费时的操作，不应该放在主线程，因此另起一个线程运行
     */
    class UiautomatorThread extends Thread {
        @Override
        public void run() {
            super.run();
            String command = generateCommand("cn.kiway.mytestcase", "TestOne", "demo");
            CMDUtils.CMD_Result rs = CMDUtils.runCMD(command, true, true);
            Log.e(TAG, "run: " + rs.error + "-------" + rs.success);
        }

        /**
         * 生成命令
         *
         * @param pkgName 包名
         * @param clsName 类名
         * @param mtdName 方法名
         * @return
         */
        public String generateCommand(String pkgName, String clsName, String mtdName) {
            String command = "am instrument  --user 0 -w -r   -e debug false -e class "
                    + pkgName + "." + clsName + "#" + mtdName + " "
                    + pkgName + ".test/android.support.test.runner.AndroidJUnitRunner";
            Log.e("test1: ", command);
            return command;
        }
    }
}