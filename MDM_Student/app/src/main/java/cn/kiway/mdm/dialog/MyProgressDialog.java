package cn.kiway.mdm.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import cn.kiway.mdm.KWApp;
import cn.kiway.mdm.R;
import cn.kiway.mdm.activity.FileListActivity;
import cn.kiway.mdm.hprose.jrf.client.JRFClient;
import cn.kiway.mdm.hprose.socket.Logger;
import cn.kiway.mdm.utils.HttpDownload;
import cn.kiway.mdm.utils.MyDBHelper;
import cn.kiway.mdm.utils.Utils;

import static cn.kiway.mdm.hprose.socket.KwHproseClient.client;

/**
 * Created by Administrator on 2017/11/15.
 */

public class MyProgressDialog extends Dialog implements JRFClient.DownLoadCallBack, View.OnClickListener {
    protected ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT);

    Activity context;
    ProgressBar progressBar;
    JSONObject data;
    String path = "/mnt/sdcard/kiwayFile";
    TextView textView;
    Button button;

    public MyProgressDialog(@NonNull Activity context, String data) {
        super(context, R.style.LoadingDialog);
        this.context = context;
        try {
            this.data = new JSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    String downUrl;
    String depositPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.ok);
        button = (Button) findViewById(R.id.sure);
        button.setOnClickListener(this);
        textView.setEnabled(false);
        fullWindowCenter();
        downUrl = data.optString("msg").replace("\\/", "/");

        downloadFile();
    }

    protected void fullWindowCenter() {
        layoutParams = getWindow().getAttributes();
        Rect rect = new Rect();
        View v = getWindow().getDecorView();
        v.getWindowVisibleDisplayFrame(rect);
    }


    @Override
    public void onClick(View view) {
        button.setVisibility(View.GONE);
        if (textView.getText().toString().contains("失败")) {
            downloadFile();
        } else {
            Utils.openFile(context, depositPath);
            dismiss();
        }
    }

    private void downloadFile() {
        if (!new File(path).exists())//如果文件夹没有创建
            new File(path).mkdir();
        if (progressBar.getVisibility() == View.GONE)
            progressBar.setVisibility(View.VISIBLE);
        textView.setEnabled(false);
        textView.setText(downUrl.split("/")[downUrl.split("/").length - 1] + "\n" + "正在接收老师的文件，请勿操作");
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (downUrl.startsWith("http://")) {//公网下载
                    final String filename = downUrl.split("/")[downUrl.split("/").length - 1] + "." + data.optString
                            ("fileType");//公网地址没有文件名，从JS数据中解析加上后缀名
                    final String folder = path + "/";//下载存放的文件夹
                    int ret = new HttpDownload().downFile(downUrl, folder, filename);//开始下载
                    Log.d("test", "download ret = " + ret);
                    if (ret == -1) {
                        error();
                    } else {
                        depositPath = folder + filename;
                        success();
                    }
                } else {//局域网下载
                    depositPath = path + "/" + data.optString("msg").split("/")[data
                            .optString("msg").split("/").length - 1];//下载的存放路径
                    if (!new File(depositPath).exists())//如果文件没有创建
                        try {
                            new File(depositPath).createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    if (client != null) {//开始下载
                        client.getFile(depositPath, downUrl, MyProgressDialog.this);
                    }
                }
            }
        }).start();
    }


    @Override
    public void success() {
        Logger.log("dialogSucess");
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                cn.kiway.mdm.entity.File a = new cn.kiway.mdm.entity.File();//保存记录到本地
                a.filename = depositPath.split("/")[depositPath.split("/").length - 1];
                a.time = System.currentTimeMillis() + "";
                a.filepath = depositPath;
                new MyDBHelper(context).addFile(a);
                textView.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                textView.setText(downUrl.split("/")[downUrl.split("/").length - 1] + "\n接收完成!");
                button.setVisibility(View.VISIBLE);
                button.setText("打开文件");
                if (KWApp.instance.currentActivity != null && KWApp.instance.currentActivity instanceof
                        FileListActivity) {
                    ((FileListActivity) KWApp.instance.currentActivity).refreshUI();
                }
            }
        });

    }

    @Override
    public void error() {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button.setVisibility(View.VISIBLE);
                button.setText("重新接收");
                textView.setEnabled(true);
                textView.setText(downUrl.split("/")[downUrl.split("/").length - 1] + "\n接收失败！！");
            }
        });
    }
}
