package com.zk.dynamicloader.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.zk.dynamicloader.R;

import org.json.JSONObject;
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

//mdm jar升级例子
public class MainActivity2 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    private void checkJarUpgrade() {
        Log.d("test", "BRAND = " + BRAND);
        Log.d("test", "MODEL = " + MODEL);

        RequestParams param1 = new RequestParams(URL_JAR_UPGRADE);
        param1.addParameter("modelName", MODEL);
        param1.addParameter("businessMen", BRAND);
        param1.setMaxRetryCount(5);
        Log.d("test", "url = " + param1);
        x.http().get(param1, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("test", "onSuccess result = " + result);
                try {
                    JSONObject o = new JSONObject(result);
                    JSONObject data = o.optJSONObject("data");
                    String jarUrl = data.optString("jarUrl");
                    String version = data.optString("version");

                    String currentJarVersion = getSharedPreferences("kiway", 0).getString("jarVersion", "0.0.0");
                    if (currentJarVersion.compareTo(version) < 0 || !new File(MDMJAR).exists()) {
                        doDownloadJar(jarUrl, version);
                    } else {
                        Log.d("test", "jar已经是最新版本");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
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

    private void doDownloadJar(String jarUrl, final String version) {
        String downloadUrl = jarUrl;
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
                getSharedPreferences("kiway", 0).edit().putString("jarVersion", version).commit();
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

    public void upgrade(View view) {
        //根据平板型号，下载对应的最新的jar
        checkJarUpgrade();
    }
}
