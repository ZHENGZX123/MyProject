package cn.kiway.klzy.webuitl;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.DownloadListener;
import android.widget.Toast;

import java.io.File;

import cn.kiway.klzy.App;

/**
 * Created by Administrator on 2017/5/26.
 * 支持webview下载
 */

public class MyWebViewDownLoadListener implements DownloadListener {
    Context context;
    Handler handler;
    App app;

    public MyWebViewDownLoadListener(Context context, Handler handler, App app) {
        this.context = context;
        this.handler = handler;
        this.app = app;
    }

    @Override
    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                long contentLength) {
        Log.i("*****************", "url=" + url);
        Log.i("*****************", "userAgent=" + userAgent);
        Log.i("*****************", "contentDisposition=" + contentDisposition);
        Log.i("*****************", "mimetype=" + mimetype);
        Log.i("*****************", "contentLength=" + contentLength);
//        Uri uri = Uri.parse(url);
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        context.startActivity(intent);
        String fileName = url.split("/")[url.split("/").length - 1];

        File file = new File(FileUtils.createDocFloder(), fileName);
        if (!file.exists()) {
            Message msg = handler.obtainMessage();
            msg.what = HanderMessageWhat.messageWhat4;
            msg.obj = "文件正在下载\n0%";
            handler.sendMessage(msg);
            DownLoadFile.downoalFile(url, app, handler, fileName);
        } else {
            try {
                FileUtils.openFile(app.getApplicationContext(), file.getAbsolutePath());
            } catch (Exception e) {
                Toast.makeText(app.getApplicationContext(), "打开失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
