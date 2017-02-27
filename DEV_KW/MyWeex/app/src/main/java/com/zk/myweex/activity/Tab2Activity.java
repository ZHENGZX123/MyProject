package com.zk.myweex.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ViewGroup;

import com.taobao.weex.utils.WXFileUtils;
import com.zk.myweex.R;
import com.zk.myweex.WXApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Tab2Activity extends BaseActivity {

    private int lastProgress;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab2);
        setContainer((ViewGroup) findViewById(R.id.index_container));
        getSupportActionBar().hide();
        pd = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);

        renderPage(WXFileUtils.loadAsset("yjpt/tab2.js", this), "file://assets/");

        //在这里要做增量管理
        //1.读取我当前的模块，请求服务器，查询对应模块的信息，返回值包括
        // 模块id， 版本号 ， 文件大小 ， 下载地址
        //2.比较是否有新的版本，如果有，提示下载。(是单个更新还是一次性更新)
        //3.下载替换zip文件

        checkVersionUp();
    }

    private void checkVersionUp() {
        File root = new File(WXApplication.PATH);
        File[] files = root.listFiles();
        if (files == null) {
            return;
        }
        if (files.length == 0) {
            return;
        }
        for (File f : files) {
            String name = f.getName();
            Log.d("test", "file = " + f.getAbsolutePath());
            String version = getSharedPreferences("yjpt", 0).getString("version_" + name, "1.0.0");
            Log.d("test", "version = " + version);
        }
        //访问http，提交参数： zip名字, 版本号(支持数组)
        boolean hasNewVersion = hasNewVersion();
        if (hasNewVersion) {
            showUpdateConfirmDialog("http://120.24.84.206/yjpt/function1.zip", "function1.zip");
        }
    }

    protected void showUpdateConfirmDialog(final String url, final String zipName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
        AlertDialog dialog_download = builder.setMessage("发现新的版本，是否更新？").setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface d, int arg1) {
                d.dismiss();
                downloadNewVersion(url, zipName);
            }
        }).create();
        dialog_download.setCancelable(false);
        dialog_download.show();
    }

    protected void downloadNewVersion(final String urlString, final String zipName) {
        pd.setMessage("正在下载新版本，请稍等");
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.show();
        pd.setCancelable(false);
        pd.setProgress(0);

        new Thread() {
            @SuppressWarnings("resource")
            public void run() {
                try {
                    InputStream input = null;
                    HttpURLConnection urlConn = null;
                    URL url = new URL(urlString);
                    urlConn = (HttpURLConnection) url.openConnection();
                    urlConn.setRequestProperty("Accept-Encoding", "identity");
                    urlConn.setReadTimeout(10000);
                    urlConn.setConnectTimeout(10000);
                    input = urlConn.getInputStream();
                    int total = urlConn.getContentLength();
                    File file = null;
                    OutputStream output = null;
                    String savedFilePath = WXApplication.PATH + zipName;
                    file = new File(savedFilePath);
                    output = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int temp = 0;
                    int read = 0;
                    while ((temp = input.read(buffer)) != -1) {
                        output.write(buffer, 0, temp);
                        read += temp;
                        float progress = ((float) read) / total;
                        int progress_int = (int) (progress * 100);
                        if (lastProgress != progress_int) {
                            lastProgress = progress_int;
                            Message msg = new Message();
                            msg.what = 6;// downloading
                            msg.arg1 = progress_int;
                            mHandler.sendMessage(msg);
                        } else {
                            // do not send msg
                        }
                    }
                    output.flush();
                    output.close();
                    input.close();
                    Message msg = new Message();
                    msg.what = 4;
                    msg.obj = savedFilePath;
                    mHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(5);
                }
            }
        }.start();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 4) {
                pd.dismiss();
                toast("下载完成");
//                String savedFilePath = (String) msg.obj;
//                Intent intent = new Intent();
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.setAction(android.content.Intent.ACTION_VIEW);
//                intent.setDataAndType(Uri.fromFile(new File(savedFilePath)), "application/vnd.android.package-archive");
//                startActivity(intent);
//                finish();
            } else if (msg.what == 5) {
                pd.dismiss();
                toast("下载失败");
            } else if (msg.what == 6) {
                pd.setProgress(msg.arg1);
            }
        }
    };

    private boolean hasNewVersion() {
        return true;
    }

}
