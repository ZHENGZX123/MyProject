package com.zk.dynamicloader.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.zk.dynamicloader.R;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import static android.os.Build.BRAND;
import static android.os.Build.MODEL;
import static com.zk.dynamicloader.utils.Constant.MDMJAR;

/**
 * Created by Administrator on 2018/11/19.
 */

public class MainActivity2 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Log.d("test", "BRAND = " + BRAND);
        Log.d("test", "MODEL = " + MODEL);


        //根据平板型号，下载对应的最新的jar

        String downloadUrl = "xxx";
        Log.d("test", "doDownloadFile url = " + downloadUrl);
        final String savedFilePath = MDMJAR + ".tmp";
        RequestParams params = new RequestParams(downloadUrl);
        params.setSaveFilePath(savedFilePath);
        params.setMaxRetryCount(5);
        params.setAutoRename(false);
        params.setAutoResume(true);
        x.http().get(params, new org.xutils.common.Callback.CommonCallback<File>() {

            @Override
            public void onSuccess(File result) {
                result.renameTo(new File(result.getAbsolutePath().replace(".tmp", "")));
                Log.d("test", "onSuccess");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d("test", "onError");
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
