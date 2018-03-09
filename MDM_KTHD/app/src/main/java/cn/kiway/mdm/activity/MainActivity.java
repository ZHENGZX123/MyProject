package cn.kiway.mdm.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import cn.kiway.mdm.App;
import cn.kiway.mdm.utils.FileUtils;
import cn.kiway.mdm.utils.Logger;
import cn.kiway.mdm.utils.NetworkUtil;
import cn.kiway.mdm.utils.Utils;
import cn.kiway.mdm.view.RoundedImageView;
import studentsession.kiway.cn.mdm_studentsession.R;

import static cn.kiway.mdm.utils.IContants.CHECK_VERSION_URL;
import static cn.kiway.mdm.utils.Utils.getCurrentVersion;

public class MainActivity extends ScreenSharingActivity {

    public static MainActivity instantce;
    private Dialog dialog_download;
    TextView className, studentName;
    RoundedImageView userPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("test", "KTHD main oncreate");
        instantce = this;
        setContentView(R.layout.activity_main);
        getAppData();
        initView();
        mHandler.sendEmptyMessage(5);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("KTHD main", "onStart()");
        App.instance.connectService(App.instance.mClientCallback);
    }

    @Override
    public void initView() {
        super.initView();
        className = (TextView) findViewById(R.id.className);
        studentName = (TextView) findViewById(R.id.studentName);
        userPic = (RoundedImageView) findViewById(R.id.userPic);
        if (!getSharedPreferences("kiway", 0).getString("userUrl", "").equals(""))
            ImageLoader.getInstance().displayImage(getSharedPreferences("kiway", 0).getString("userUrl", ""),
                    userPic, App.getLoaderOptions());
        studentName.setText(getSharedPreferences("kiwaykthd", 0).getString("studentName", ""));
        className.setText(getSharedPreferences("kiwaykthd", 0).getString("className", ""));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!getSharedPreferences("kiway", 0).getString("userUrl", "").equals(""))
            ImageLoader.getInstance().displayImage(getSharedPreferences("kiway", 0).getString("userUrl", ""),
                    userPic, App.getLoaderOptions());
    }

    public void onInfo(View view) {//个人信息
        startActivity(new Intent(this, UserInfoActivity.class));
    }

    public void onFile(View view) {//查看文件
        startActivity(new Intent(this, FileListActivity.class));
    }

    public void logout(View view) {
        toast("测试用");
        finish();
    }

    public void onMsg(View view) {//查看消息
        startActivity(new Intent(this, YiShangKeActivity.class));
    }

    public void onKT(View view) {
        //课堂分析
        startActivity(new Intent(this, FenxiActivity.class));
    }


    private void getAppData() {//判断是手动打开还是推送打开
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getStringExtra("studentName") != null)//学生名字
                getSharedPreferences("kiwaykthd", 0).edit().putString("studentName", intent.getStringExtra
                        ("studentName")).commit();

            if (intent.getStringExtra("className") != null)//班级名字
                getSharedPreferences("kiwaykthd", 0).edit().putString("className", intent.getStringExtra
                        ("className")).commit();
            if (intent.getStringExtra("studentNumber") != null)//学号
                getSharedPreferences("kiwaykthd", 0).edit().putString("studentNumber", intent.getStringExtra
                        ("studentNumber")).commit();

            if (intent.getStringExtra("classId") != null)//班级Id
                getSharedPreferences("kiwaykthd", 0).edit().putString("classId", intent.getStringExtra
                        ("classId")).commit();

            if (intent.getStringExtra("schoolId") != null)//学校Id
                getSharedPreferences("kiwaykthd", 0).edit().putString("schoolId", intent.getStringExtra
                        ("schoolId")).commit();

            if (intent.getStringExtra("huaweiToken") != null)//华为token
                getSharedPreferences("kiwaykthd", 0).edit().putString("huaweiToken", intent.getStringExtra
                        ("huaweiToken")).commit();
            String shangke = intent.getStringExtra("shangke");
            Logger.log("shangke::::::" + shangke);
            if (shangke != null && !shangke.equals("")) {
            } else {
                checkNewVersion();
            }
        } else {
            checkNewVersion();
        }
    }

    //下面是版本更新相关
    public void checkNewVersion() {
        new Thread() {
            @Override
            public void run() {
                try {
                    HttpGet httpRequest = new HttpGet(CHECK_VERSION_URL);
                    DefaultHttpClient client = new DefaultHttpClient();
                    HttpResponse response = client.execute(httpRequest);
                    String ret = EntityUtils.toString(response.getEntity());
                    Log.d("test", "new version = " + ret);
                    Message msg = new Message();
                    msg.what = 2;
                    msg.obj = ret;
                    mHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(3);
                }
            }
        }.start();
    }

    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                try {
                    JSONObject data = new JSONObject(msg.obj.toString());
                    String url = data.optString("url");
                    String name = data.optString("name");
                    App.instance.uploadUserFile(url, 1, name, FileUtils.GetFileSize(new File(App.ROOT
                            + name)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else if (msg.what == 2) {
                String ret = (String) msg.obj;
                try {
                    //1.apk更新
                    Log.d("test", "新版本返回值" + ret);
                    String apkVersion = new JSONObject(ret).getString("apkCode");
                    String apkUrl = new JSONObject(ret).getString("apkUrl");
                    if (getCurrentVersion(getApplicationContext()).compareTo(apkVersion) < 0) {
                        downloadSilently(apkUrl, apkVersion);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (msg.what == 4) {
                // 下载完成后安装
                String savedFilePath = (String) msg.obj;
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(savedFilePath)), "application/vnd.android.package-archive");
                startActivity(intent);
                finish();
            } else if (msg.what == 5) {
                Utils.login(MainActivity.this, new Utils.ReLogin() {
                    @Override
                    public void onSuccess() {
                        Logger.log("onSuccesss");
                    }

                    @Override
                    public void onFailure() {
                        Logger.log("onSuccesssFailuer");
                    }
                });
                mHandler.sendEmptyMessageDelayed(5, 10 * 60 * 1000);
            }
        }
    };

    private void downloadSilently(String apkUrl, String version) {
        boolean isWifi = NetworkUtil.isWifi(this);
        if (!isWifi) {
            Log.d("test", "不是wifi...");
            return;
        }
        final String savedFilePath = "/mnt/sdcard/cache/mdm_zhkt_" + version + ".apk";
        if (new File(savedFilePath).exists()) {
            Log.d("test", "该文件已经下载好了");
            askforInstall(savedFilePath);
            return;
        }
        RequestParams params = new RequestParams(apkUrl);
        params.setSaveFilePath(savedFilePath);
        params.setAutoRename(false);
        params.setAutoResume(true);
        x.http().get(params, new org.xutils.common.Callback.CommonCallback<File>() {
            @Override
            public void onSuccess(File result) {
                //成功后弹出对话框询问，是否安装
                askforInstall(savedFilePath);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

    private void askforInstall(final String savedFilePath) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, AlertDialog.THEME_HOLO_LIGHT);
        dialog_download = builder.setMessage(getString(R.string.new_version)).setNegativeButton(android.R.string.ok, new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        dialog_download.dismiss();
                        Message msg = new Message();
                        msg.what = 4;
                        msg.obj = savedFilePath;
                        mHandler.sendMessage(msg);
                    }
                }).setPositiveButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).create();
        dialog_download.show();
    }

    ////////////////以上是版本更新
    @Override
    public void onBackPressed() {
        if (app.isAttenClass) {
            toast("目前正在上课中，无法退出");
            return;
        }
        super.onBackPressed();
    }

    public void startTuiping() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initModules();
                startCapture();
                mRtcEngine.joinChannel(null, Utils.getIMEI(getApplicationContext()), "", 0);
            }
        });
    }

    public void endTuiping() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mRtcEngine == null) {
                    return;
                }
                mRtcEngine.leaveChannel();
                stopCapture();
                deInitModules();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
