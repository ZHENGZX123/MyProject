package com.zk.dynamicloader.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.zk.dynamicloader.R;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import static android.os.Build.BRAND;
import static android.os.Build.MODEL;
import static com.zk.dynamicloader.utils.Constant.MDMJAR;
import static com.zk.dynamicloader.utils.Constant.URL_JAR_UPGRADE;

/**
 * Created by Administrator on 2018/11/19.
 */

public class MainActivity2 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        //根据平板型号，下载对应的最新的jar
        checkJarUpgrade();
    }

    private void checkJarUpgrade() {
        Log.d("test", "BRAND = " + BRAND);
        Log.d("test", "MODEL = " + MODEL);


        String url = URL_JAR_UPGRADE;
        Log.d("test", "url = " + url);
        RequestParams param1 = new RequestParams(url);
        param1.addParameter("modelName", MODEL);
        param1.addParameter("businessMen", BRAND);
        param1.setMaxRetryCount(5);
        Log.d("test", "url = "+ param1);
        x.http().get(param1, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("test", "onSuccess result = " + result);

                doDownloadJar();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d("test", "onError ex = " + ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.d("test", "onCancelled");
            }

            @Override
            public void onFinished() {
                Log.d("test", "onFinished");
            }
        });
    }

    private void doDownloadJar() {
        String downloadUrl = "xxx";
        Log.d("test", "doDownloadFile url = " + downloadUrl);
        final String savedFilePath = MDMJAR + ".tmp";
        RequestParams param2 = new RequestParams(downloadUrl);
        param2.setSaveFilePath(savedFilePath);
        param2.setMaxRetryCount(5);
        param2.setAutoRename(false);
        param2.setAutoResume(true);
        x.http().get(param2, new org.xutils.common.Callback.CommonCallback<File>() {

            @Override
            public void onSuccess(File result) {
                result.renameTo(new File(result.getAbsolutePath().replace(".tmp", "")));
                Log.d("test", "onSuccess");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d("test", "onError ex = " + ex.toString());
                new File(savedFilePath).delete();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.d("test", "onCancelled");
                new File(savedFilePath).delete();
            }

            @Override
            public void onFinished() {
                Log.d("test", "onFinished");
            }
        });
    }
}
