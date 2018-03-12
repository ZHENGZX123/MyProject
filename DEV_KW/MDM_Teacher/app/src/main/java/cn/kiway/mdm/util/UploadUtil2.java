package cn.kiway.mdm.util;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;
import org.xutils.common.util.KeyValue;
import org.xutils.http.body.MultipartBody;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.kiway.mdm.entity.UploadTask;

/**
 * Created by Administrator on 2018/3/12.
 */

public class UploadUtil2 {

    public static void addTask(Context c, String filepath, String courseID) {
        new MyDBHelper(c).addTask(new UploadTask(filepath, courseID, UploadTask.STATUS_START, ""));
    }

    public static void startTask(Context c) {
        //1.开始之前先重置一遍 status 1=>0
        ArrayList<UploadTask> tasks = new MyDBHelper(c).getTasksByStatus(UploadTask.STATUS_UPLOADING);
        for (UploadTask ut : tasks) {
            new MyDBHelper(c).setTaskStatus(ut, UploadTask.STATUS_START);
        }
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    Log.d("test", "check upload task ...");
                    //2.查找未完成的task，然后上传
                    ArrayList<UploadTask> tasks = new MyDBHelper(c).getTasksByStatus(UploadTask.STATUS_START);
                    Log.d("test", "未完成的tasks size = " + tasks.size());
                    for (UploadTask ut : tasks) {
                        Log.d("test", "ut = " + ut);
                        doUpload(c, ut);
                    }
                    try {
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private static void doUpload(Context c, UploadTask ut) {
        String accessToken = c.getSharedPreferences("kiway", 0).getString("x-auth-token", "");
        String url = "http://mdm.kiway.cn:8085/common/file?x-auth-token=" + accessToken;
        org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(url);
        //params.addBodyParameter("upload", new File("/mnt/sdcard/15204167950531.jpg") );
        //params.addBodyParameter("filename", "15204167950531.jpg");

        File file = new File(ut.filepath);
        List<KeyValue> list = new ArrayList<>();
        list.add(new KeyValue("file", file));
        MultipartBody body = new MultipartBody(list, "UTF-8");
        params.setRequestBody(body);
        params.setMultipart(true);

        x.http().post(params, new org.xutils.common.Callback.ProgressCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("test", "onSuccess result = " + result);
                try {
                    JSONObject obj = new JSONObject(result);
                    String url = obj.optJSONObject("data").optString("url");
                    //缓存本地-服务器对应关系
                    c.getSharedPreferences("kiway", 0).edit().putString(url, ut.filepath).commit();
                    //添加记录
                    Utils.addVideoRecord(c, ut, ut.courseID, url, "mp4");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d("test", "onError " + ex.toString());
                new MyDBHelper(c).setTaskStatus(ut, UploadTask.STATUS_START);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.d("test", "onCancelled");
            }

            @Override
            public void onFinished() {
                Log.d("test", "onFinished");
            }

            @Override
            public void onWaiting() {
                Log.d("test", "onWaiting");
                new MyDBHelper(c).setTaskStatus(ut, UploadTask.STATUS_UPLOADING);
            }

            @Override
            public void onStarted() {
                Log.d("test", "onStarted");
                new MyDBHelper(c).setTaskStatus(ut, UploadTask.STATUS_UPLOADING);
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                Log.d("test", "onLoading total = " + total + " current = " + current + " isDownloading = " + isDownloading);
                new MyDBHelper(c).setTaskStatus(ut, UploadTask.STATUS_UPLOADING);
            }
        });
    }
}
