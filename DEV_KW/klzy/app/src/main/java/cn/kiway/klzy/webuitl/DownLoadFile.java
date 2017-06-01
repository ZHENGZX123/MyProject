package cn.kiway.klzy.webuitl;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.kiway.klzy.App;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/5/27.
 */

public class DownLoadFile {

    public static void downoalFile(final String fileUrl, final App app, final Handler mHandler, final String
            fileNamePath) {
        Request request = new Request.Builder().url(fileUrl).build();
        app.mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Message msg = mHandler.obtainMessage();
                msg.what = HanderMessageWhat.messageWhat2;
                msg.obj = "文件下载失败";
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file;
                    file = new File(FileUtils.createDocFloder(), fileNamePath);
                    file.delete();
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        Log.d("h_bl", "progress=" + progress);
                        Message msg = mHandler.obtainMessage();
                        msg.what = HanderMessageWhat.messageWhat3;
                        msg.obj = "正在下载,请稍后\n" + progress + "%";
                        mHandler.sendMessage(msg);
                    }
                    fos.flush();
                    Log.d("h_bl", "文件下载成功");
                    Message msg = mHandler.obtainMessage();
                    msg.what = HanderMessageWhat.messageWhat2;
                    msg.obj = "文件下载成功";
                    mHandler.sendMessage(msg);
                    //下载成功后做什么
                    try {
                        FileUtils.openFile(app.getApplicationContext(), file.getAbsolutePath());
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(app.getApplicationContext(), "打开失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.d("h_bl", "文件下载失败");
                    Message msg = mHandler.obtainMessage();
                    msg.what = HanderMessageWhat.messageWhat2;
                    msg.obj = "文件下载失败";
                    mHandler.sendMessage(msg);
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        });
    }
}