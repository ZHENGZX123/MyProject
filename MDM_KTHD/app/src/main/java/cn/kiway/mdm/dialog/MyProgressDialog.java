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

import org.json.JSONObject;

import java.io.File;

import cn.kiway.mdm.utils.HttpDownload;
import cn.kiway.mdm.utils.Logger;
import cn.kiway.mdm.utils.Utils;
import studentsession.kiway.cn.mdm_studentsession.R;

/**
 * Created by Administrator on 2017/11/15.
 */

public class MyProgressDialog extends Dialog implements View.OnClickListener {
    protected ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT);

    Activity context;
    ProgressBar progressBar;
    JSONObject data;
    String downPath = "/mnt/sdcard/kiwayFile/";//下载文件目录
    TextView textView;
    Button button;
    String downUrl;//下载地址
    String fileName;//下载的文件名

    public MyProgressDialog(@NonNull Activity context, JSONObject data) {
        super(context, R.style.LoadingDialog);
        this.context = context;
        this.data = data;
    }


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
        downUrl = data.optString("url").replace("\\/", "/");
        fileName=data.optString("fileName");
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
            Utils.openFile(context, downPath+fileName);
            dismiss();
        }
    }

    private void downloadFile() {
        if (!new File(downPath).exists())//如果文件夹没有创建
            new File(downPath).mkdir();
        if (progressBar.getVisibility() == View.GONE)
            progressBar.setVisibility(View.VISIBLE);
        textView.setEnabled(false);
        textView.setText(fileName + "\n" + "正在接收老师的文件，请勿操作");
        new Thread(new Runnable() {
            @Override
            public void run() {
                    int ret = new HttpDownload().downFile(downUrl, downPath, fileName);//开始下载
                    Log.d("test", "download ret = " + ret);
                    if (ret == -1) {
                        error();
                    } else {
                        success();
                    }
                }
        }).start();
    }

    public void success() {
        Logger.log("dialogSucess");
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                FileModel a = new FileModel();//保存记录到本地
//                a.filename = fileName;
//                a.time = System.currentTimeMillis() + "";
//                a.filepath = downPath + fileName;
//                new MyDBHelper(context).addFile(a);
//                textView.setEnabled(true);
//                progressBar.setVisibility(View.GONE);
//                textView.setText(fileName+ "\n接收完成!");
//                button.setVisibility(View.VISIBLE);
//                button.setText("打开文件");
//                if (App.instance.currentActivity != null && App.instance.currentActivity instanceof
//                        FileListActivity) {
//                    ((FileListActivity) App.instance.currentActivity).refreshUI();
//                }
            }
        });

    }

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
