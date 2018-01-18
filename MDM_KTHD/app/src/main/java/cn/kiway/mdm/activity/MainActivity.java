package cn.kiway.mdm.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import cn.kiway.mdm.utils.Logger;
import cn.kiway.mdm.utils.NetworkUtil;
import cn.kiway.mdm.utils.Utils;
import io.agora.openlive.model.ConstantApp;
import io.agora.openlive.ui.LiveRoomActivity;
import io.agora.rtc.Constants;
import studentsession.kiway.cn.mdm_studentsession.R;

import static cn.kiway.mdm.utils.IContants.CHECK_VERSION_URL;
import static cn.kiway.mdm.utils.Utils.getCurrentVersion;

public class MainActivity extends BaseActivity {

    public static MainActivity instantce;
    private Dialog dialog_download;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instantce = this;
        setContentView(R.layout.activity_main);
        initView();
        getAppData();
    }

    @Override
    public void initView() {
        super.initView();
        titleName.setText("开维互动学生端");
    }

    public void onInfo(View view) {//个人信息
//        startPlayer();
        startActivity(new Intent(this, UserInfoActivity.class));
//        startActivity(new Intent(this, AnswerQuestionsAcitivity.class));
    }

    public void onCourse(View view) {
        startActivity(new Intent(this, Course2Activity.class));
    }

    public void onFile(View view) {//查看文件
        startActivity(new Intent(this, FileListActivity.class));
    }

    public void logout(View view) {

    }

    public void onMsg(View view) {//查看消息
        startActivity(new Intent(this, NotifyMsgActivity.class));
    }

    public void onDis(View view) {
        findViewById(R.id.connect).setVisibility(View.GONE);
    }

    public void onUdp(View view) {
    }

    public void fenxi(View view) {
        startActivity(new Intent(this, FenxiActivity.class));
    }

    private void getAppData() {//判断是手动打开还是推送打开
        Intent intent = getIntent();
        if (intent != null) {
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
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 2) {
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
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(savedFilePath)), "application/vnd.android.package-archive");
                startActivity(intent);
                finish();
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (app.isAttenClass) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //--------------------------------------------------2.0新增--------------------------

    public void startPlayer() {
        //1.开始播放教师推屏
        Intent i = new Intent(MainActivity.this, LiveRoomActivity.class);
        i.putExtra(ConstantApp.ACTION_KEY_CROLE, Constants.CLIENT_ROLE_AUDIENCE);
        i.putExtra(ConstantApp.ACTION_KEY_ROOM_NAME, "kiway");
        startActivity(i);
    }

    public void clickSnapshot(View view) {
        //保存截图到本地
        getWindow().getDecorView().setDrawingCacheEnabled(true);
        Bitmap bitmap = getWindow().getDecorView().getDrawingCache();
        Utils.saveBitmap(bitmap, System.currentTimeMillis() + ".png");
        toast("请在我的文件里查看");
    }

    public void clickAsk(View view) {
        final Dialog dialog = new Dialog(this, R.style.popupDialog);
        dialog.setContentView(R.layout.dialog_ask);
        dialog.show();
    }


}
