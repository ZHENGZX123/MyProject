package cn.kiway.mdm.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import cn.kiway.mdm.R;
import cn.kiway.mdm.hprose.jrf.client.JRFClient;
import cn.kiway.mdm.hprose.socket.Logger;
import cn.kiway.mdm.utils.FileUtils;
import cn.kiway.mdm.utils.MyDBHelper;

import static cn.kiway.mdm.hprose.socket.KwHproseClient.client;

/**
 * Created by Administrator on 2017/11/15.
 */

public class ProgressDialog extends Dialog implements JRFClient.DownLoadCallBack, View.OnClickListener {
    protected ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT);

    Activity context;
    ProgressBar progressBar;
    JSONObject data;
    String path = Environment.getExternalStorageDirectory().getPath() + "/kiwayFile";
    TextView textView;

    public ProgressDialog(@NonNull Activity context, String data) {
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
        textView.setOnClickListener(this);
        textView.setEnabled(false);
        fullWindowCenter();
        downUrl = data.optString("msg");
        depositPath = path + "/" + data.optString("msg").split("/")[data
                .optString("msg").split("/").length - 1];
        Logger.log(":::::下载地址:::::" + downUrl);
        Logger.log("::::::存放地址::::" + depositPath);
        if (!new File(path).exists())//如果文件夹没有创建
            new File(path).mkdir();
        if (!new File(depositPath).exists())//如果文件没有创建
            try {
                new File(depositPath).createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        if (client != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    client.getFile(depositPath, downUrl, ProgressDialog.this);
                }
            }).start();
        }
        textView.setText(downUrl.split("/")[downUrl.split("/").length - 1] + "\n" + "正在接收老师的文件，请勿操作");
    }

    protected void fullWindowCenter() {
        layoutParams = getWindow().getAttributes();
        Rect rect = new Rect();
        View v = getWindow().getDecorView();
        v.getWindowVisibleDisplayFrame(rect);
    }

    @Override
    public void success() {
        Logger.log("dialogSucess");
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                cn.kiway.mdm.entity.File a = new cn.kiway.mdm.entity.File();//保存记录到本地
                a.filename = depositPath.split("/")[depositPath.split("/").length-1];
                a.time = System.currentTimeMillis() + "";
                a.filepath = depositPath;
                new MyDBHelper(context).addFile(a);
                textView.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                textView.setText(downUrl.split("/")[downUrl.split("/").length - 1] + "\n接收完成,打开文件");
            }
        });

    }

    @Override
    public void error() {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setEnabled(true);
                textView.setText(downUrl.split("/")[downUrl.split("/").length - 1] + "接收失败,重新接收");
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (textView.getText().toString().equals("失败")) {
            progressBar.setVisibility(View.VISIBLE);
            textView.setEnabled(false);
            if (client != null) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        client.getFile(depositPath, downUrl, ProgressDialog.this);
                    }
                }).start();
            }
        } else {
            FileUtils.openFile(context, depositPath);
            dismiss();
        }
    }
}
