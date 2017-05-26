package com.zk.myweex.extend.module;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.zxing.client.android.CaptureActivity;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.loader.GlideImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.nanchen.compresshelper.CompressHelper;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.appfram.storage.DefaultWXStorage;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.zk.myweex.WXApplication;
import com.zk.myweex.activity.MainActivity;
import com.zk.myweex.utils.DataCleanManager;
import com.zk.myweex.utils.DensityUtil;
import com.zk.myweex.utils.HttpDownload;
import com.zk.myweex.utils.HttpUploadFile;
import com.zk.myweex.utils.Logger;
import com.zk.myweex.utils.ScreenManager;
import com.zk.myweex.utils.ScreenUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import cn.kiway.baoantong.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import uk.co.senab.photoview.sample.ViewPagerActivity;

import static com.zk.myweex.utils.HttpUploadFile.updateUserInfoUrl;
import static com.zk.myweex.utils.Utils.getCurrentVersion;


public class SJEventModule extends WXModule implements Callback {

    @JSMethod(uiThread = true)
    public void getVersion(JSCallback callback) {
        callback.invoke(getCurrentVersion(mWXSDKInstance.getContext()));
    }


    @JSMethod(uiThread = true)
    public void getCacheSize(JSCallback callback) {
        try {
            String cache = DataCleanManager.getCacheSize(mWXSDKInstance.getContext().getCacheDir());
            Log.d("test", "cache = " + cache);
            callback.invoke(cache);
        } catch (Exception e) {
            e.printStackTrace();
            callback.invoke("0M");
        }
    }

    @JSMethod(uiThread = true)
    public void clearCache(JSCallback callback) {
        DataCleanManager.cleanInternalCache(mWXSDKInstance.getContext());
        callback.invoke("0M");
    }

    private MediaPlayer mPlayer;

    OkHttpClient mOkHttpClient = new OkHttpClient();
    private String uploadBackUrl;

    @JSMethod(uiThread = true)
    public void loginSuccess(final String username) {
        Log.d("test", "loginSuccess username = " + username);
        mWXSDKInstance.getContext().getSharedPreferences("kiway", 0).edit().putBoolean("login", true).apply();
        mWXSDKInstance.getContext().startActivity(new Intent(mWXSDKInstance.getContext(), MainActivity.class));
        ScreenManager.getScreenManager().popAllActivityExceptOne(MainActivity.class);
    }

//    @JSMethod(uiThread = true)
//    public void logoutSuccess() {
//        Log.d("test", "logoutSuccess");
//        mWXSDKInstance.getContext().getSharedPreferences("kiway", 0).edit().putBoolean("login", false).apply();
//        mWXSDKInstance.getContext().startActivity(new Intent(mWXSDKInstance.getContext(), LoginActivity.class));
//        ScreenManager.getScreenManager().popAllActivityExceptOne(LoginActivity.class);
//    }

    @JSMethod(uiThread = true)
    public void loginElsewhere() {
        Log.d("test", "loginElsewhere");
        mWXSDKInstance.getContext().getSharedPreferences("kiway", 0).edit().putBoolean("login", false).apply();
        AlertDialog.Builder builder = new AlertDialog.Builder(mWXSDKInstance.getContext());
        builder.setMessage("您的帐号已经在别处登录，请您退出APP");
        builder.setTitle("提示");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }

    @JSMethod(uiThread = true)
    public void PostAvatar(JSCallback callback) {
        try {
            pickerCallback = callback;

            ImagePicker imagePicker = ImagePicker.getInstance();
            imagePicker.setImageLoader(new GlideImageLoader());// 图片加载器
            imagePicker.setSelectLimit(1);// 设置可以选择几张
            imagePicker.setMultiMode(false);// 是否为多选
            imagePicker.setCrop(true);// 是否剪裁
            imagePicker.setFocusWidth(1000);// 需要剪裁的宽
            imagePicker.setFocusHeight(1000);// 需要剪裁的高
            imagePicker.setStyle(CropImageView.Style.RECTANGLE);// 方形
            imagePicker.setShowCamera(true);// 是否显示摄像

            Intent intent = new Intent(mWXSDKInstance.getContext(), ImageGridActivity.class);
            ((Activity) mWXSDKInstance.getContext()).startActivityForResult(intent, 8888);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private JSCallback pickerCallback;

    @JSMethod(uiThread = true)
    public void PostSigalImg(JSCallback callback) {
        try {
            pickerCallback = callback;

            ImagePicker imagePicker = ImagePicker.getInstance();
            imagePicker.setImageLoader(new GlideImageLoader());// 图片加载器
            imagePicker.setSelectLimit(8);// 设置可以选择几张
            imagePicker.setMultiMode(true);// 是否为多选
            imagePicker.setCrop(false);// 是否剪裁
            imagePicker.setFocusWidth(1000);// 需要剪裁的宽
            imagePicker.setFocusHeight(1000);// 需要剪裁的高
            imagePicker.setStyle(CropImageView.Style.RECTANGLE);// 方形
            imagePicker.setShowCamera(true);// 是否显示摄像

            Intent intent = new Intent(mWXSDKInstance.getContext(), ImageGridActivity.class);
            ((Activity) mWXSDKInstance.getContext()).startActivityForResult(intent, 888);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private MediaRecorder mediaRecorder;
    private File recordFile;
    private long start;

    @JSMethod(uiThread = true)
    public void PostSigalVoice(JSCallback callback) {
        pickerCallback = callback;

        final Dialog dialog = new Dialog(mWXSDKInstance.getContext(), R.style.popupDialog);
        dialog.setContentView(R.layout.dialog_voice);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        final Button voice = (Button) dialog.findViewById(R.id.voice);
        voice.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (voice.getText().toString().equals("开始")) {
                    startRecord();
                    voice.setText("结束");
                } else if (voice.getText().toString().equals("结束")) {
                    stopRecord();
                    dialog.dismiss();
                }
            }
        });
    }

    private void stopRecord() {
        if (recordFile == null) {
            return;
        }
        if (mediaRecorder == null) {
            return;
        }

        mediaRecorder.stop();

        final int duration = (int) (System.currentTimeMillis() - start) / 1000;
        if (duration < 1) {
            toast("太短了");
            return;
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    String token = new DefaultWXStorage(mWXSDKInstance.getContext()).performGetItem("token");
                    Call call = mOkHttpClient.newCall(HttpUploadFile.returnUploadImgRequser(recordFile, HttpUploadFile.FileType.Mp3, token));
                    Response response = call.execute();
                    String ret = response.body().string();
                    Log.d("test", "upload ret  = " + ret);
                    String after = new JSONObject(ret).getJSONObject("result").getString("url");
                    HashMap map = new HashMap();
                    map.put("duration", duration);
                    map.put("url", after);
                    pickerCallback.invoke(map);
                } catch (Exception e) {
                    e.printStackTrace();
                    toast("上传失败，请稍后再试");
                }
            }
        }.start();
//        Configuration config = new Configuration.Builder().connectTimeout(10).build();
//        UploadManager uploadManager = new UploadManager(config);
//        uploadManager.put(recordFile, key, getQiniuToken(),
//                new UpCompletionHandler() {
//                    @Override
//                    public void complete(String key, ResponseInfo info, JSONObject res) {
//                        if (info.isOK()) {
//                            Log.i("qiniu", "Upload Success");
//                            String url = "http://ooy49eq1n.bkt.clouddn.com/" + key;
//                            HashMap map = new HashMap();
//                            map.put("duration", duration);
//                            map.put("url", url);
//                            pickerCallback.invoke(map);
//                        } else {
//                            Log.i("qiniu", "Upload Fail");
//                            toast("上传失败，请稍后再试");
//                        }
//                    }
//                }, null);
    }

    private void startRecord() {
        try {
            //录音并上传
            // 判断，若当前文件已存在，则删除
            String path = "/mnt/sdcard/voice/";
            if (!new File(path).exists()) {
                new File(path).mkdirs();
            }
            recordFile = new File(path + System.currentTimeMillis() + ".mp3");
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setAudioChannels(2);
            mediaRecorder.setAudioSamplingRate(44100);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);//输出格式
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);//编码格式
            mediaRecorder.setAudioEncodingBitRate(16);
            mediaRecorder.setOutputFile(recordFile.getAbsolutePath());

            // 准备好开始录音
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (Exception e) {
            e.printStackTrace();
            toast("录制声音失败，请检查SDcard");
        }
        start = System.currentTimeMillis();
    }

    private ProgressDialog pd;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 888 && resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data == null) {
                return;
            }
            boolean isOrig = data.getBooleanExtra(ImagePreviewActivity.ISORIGIN, false);
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            Log.d("test", "images count = " + images.size());
            if (!isOrig) {
                for (ImageItem ii : images) {
                    File newFile = CompressHelper.getDefault(mWXSDKInstance.getContext()).compressToFile(new File(ii.path));
                    ii.path = newFile.getAbsolutePath();
                    ii.size = newFile.length();
                }
            }
            pd = new ProgressDialog(mWXSDKInstance.getContext());
            pd.setMessage("上传中请稍等");
            pd.show();

            doUploadImage(images);
        } else if (requestCode == 8888 && resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data == null) {
                return;
            }
            boolean isOrig = data.getBooleanExtra(ImagePreviewActivity.ISORIGIN, false);
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            Log.d("test", "images count = " + images.size());
            if (!isOrig) {
                File newFile = CompressHelper.getDefault(mWXSDKInstance.getContext()).compressToFile(new File(images.get(0).path));
                images.get(0).path = newFile.getAbsolutePath();
                images.get(0).size = newFile.length();
            }
            uploadUserPic(images.get(0).path, HttpUploadFile.FileType.Image);
        } else if (requestCode == 999) {
            //扫描二维码返回
            if (data == null) {
                return;
            }
            String result = data.getStringExtra("result");
            Log.d("test", "result = " + result);
            scanCallback.invoke(result);
        }
    }

    void uploadUserPic(String fileName, String fileType) {
        try {
            String token = new DefaultWXStorage(mWXSDKInstance.getContext()).performGetItem("token");
            mOkHttpClient.newCall(HttpUploadFile.returnUploadImgRequser(new File(fileName), fileType, token)).enqueue
                    (this);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void doUploadImage(final ArrayList<ImageItem> images) {
        new Thread() {
            @Override
            public void run() {
                int successCount = 0;
                int count = images.size();
                String url = "";
                String path = "";
                for (int i = 0; i < count; i++) {
                    ImageItem ii = images.get(i);
                    Log.d("test", "ii.size = " + ii.size);
                    String key = System.currentTimeMillis() + ".jpg";
                    File file = new File(ii.path);
//                    Configuration config = new Configuration.Builder().connectTimeout(10).build();
//                    UploadManager uploadManager = new UploadManager(config);
//                    ResponseInfo result = uploadManager.syncPut(file, key, getQiniuToken(), null);
//                    Log.d("test", "result = " + result.toString());
                    try {
                        String token = new DefaultWXStorage(mWXSDKInstance.getContext()).performGetItem("token");
                        Call call = mOkHttpClient.newCall(HttpUploadFile.returnUploadImgRequser(file, HttpUploadFile.FileType.Image, token));
                        Response response = call.execute();
                        String ret = response.body().string();
                        Log.d("test", "upload ret  = " + ret);
                        successCount++;
                        String after = new JSONObject(ret).getJSONObject("result").getString("url");
                        if (i == count - 1) {
                            url = url + after;
                            path = path + ii.path;
                        } else {
                            url = url + after + ",";
                            path = path + ii.path + ",";
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (successCount != images.size()) {
                    toast("上传失败，请稍后再试");
                    return;
                }
                Log.d("test", "url = " + url);
                HashMap map = new HashMap();
                map.put("url", url);
                map.put("path", path);
                pickerCallback.invoke(map);
                hidePD();
            }
        }.start();
    }

    private void hidePD() {
        ((Activity) mWXSDKInstance.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pd.dismiss();
            }
        });
    }

    @JSMethod(uiThread = true)
    public void backToMain() {
        Log.d("test", "backToMain is called");
        mWXSDKInstance.getContext().startActivity(new Intent(mWXSDKInstance.getContext(), MainActivity.class));
    }

    @JSMethod(uiThread = true)
    public void showPhoto(String param1, String param2) {
        try {
            Log.d("test", "showPhoto param1 = " + param1);
            Log.d("test", "showPhoto param2 = " + param2);
            ViewPagerActivity.sDrawables = param1.replace("[", "").replace("]", "").replace("\"", "").split(",");
            Intent intent = new Intent(mWXSDKInstance.getContext(), ViewPagerActivity.class);
            intent.putExtra("position", Integer.parseInt(param2));
            mWXSDKInstance.getContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @JSMethod(uiThread = true)
    public void PlayVoice(final String url, final JSCallback callback) {
        Log.d("test", "PlayVoice url = " + url);
        if (!new File("/mnt/sdcard/audio/").exists()) {
            new File("/mnt/sdcard/audio/").mkdirs();
        }
        new Thread() {
            @Override
            public void run() {
                String name = url.substring(url.lastIndexOf("/") + 1);
                File check = new File("/mnt/sdcard/audio/" + name);
                if (check.exists()) {
                    play("/mnt/sdcard/audio/" + name, callback);
                    return;
                }
                HttpDownload httpDownload = new HttpDownload();
                int ret = httpDownload.downFile(url, "/mnt/sdcard/audio/", name);
                if (ret != 0) {
                    return;
                }
                play("/mnt/sdcard/audio/" + name, callback);
            }
        }.start();
    }

    @JSMethod(uiThread = true)
    public void StopVoice() {
        if (mPlayer == null) {
            return;
        }
        if (mPlayer.isPlaying()) {
            mPlayer.stop();
        }
    }

    private void play(String url, final JSCallback callback) {
        try {
            if (mPlayer != null && mPlayer.isPlaying()) {
                mPlayer.stop();
                mPlayer.release();
            }

            mPlayer = new MediaPlayer();
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    callback.invoke("ok");
                }
            });

            mPlayer.setDataSource(url);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JSCallback scanCallback;

    @JSMethod(uiThread = true)
    public void QRScan(JSCallback callback) {
        Log.d("test", "QRScan");
        this.scanCallback = callback;
        ((Activity) mWXSDKInstance.getContext()).startActivityForResult(new Intent(mWXSDKInstance.getContext(), CaptureActivity.class), 999);
    }

    @JSMethod(uiThread = true)
    public void ShowTabbar() {
        try {
            MainActivity.main.bottom.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @JSMethod(uiThread = true)
    public void HideTabbar() {
        try {
            MainActivity.main.bottom.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @JSMethod(uiThread = true)
    public void finish() {
        long current = System.currentTimeMillis();
        if (WXApplication.time == 0) {
            WXApplication.time = current;
            toast("再按一次返回退出APP");
            return;
        }
        if ((current - WXApplication.time) < 2000) {
            WXApplication.time = 0;
            ((Activity) mWXSDKInstance.getContext()).finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        } else {
            toast("再按一次返回退出APP");
            WXApplication.time = current;
        }
    }

    @JSMethod(uiThread = true)
    public void Share(String text, String url) {
        Log.d("test", "Share text = " + text + " , url = " + url);
        //截取屏幕图片
        screenshot(text, url);
    }

    private void screenshot(String text, String jumpUrl) {
        // 获取屏幕
        View dView = ((Activity) mWXSDKInstance.getContext()).getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bmp = dView.getDrawingCache();
        String name = System.currentTimeMillis() + ".png";
        String filePath = "/mnt/sdcard/" + name;
        if (bmp != null) {
            try {
                // 图片文件路径
                File file = new File(filePath);
                FileOutputStream os = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
//                doShare(filePath, text, jumpUrl);
            }
        }
    }

    @JSMethod(uiThread = true)
    public void gongzhonghao() {
        Bitmap bmp = BitmapFactory.decodeResource(mWXSDKInstance.getContext().getResources(),
                R.drawable.erweima);
        // 首先保存图片
        File appDir = new File("/mnt/sdcard/erweima/");
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(mWXSDKInstance.getContext().getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        toast("二维码已保存至手机，请使用手机微信扫描二维码关注公众号");
        // 最后通知图库更新
        mWXSDKInstance.getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.parse("file://" + file.getAbsolutePath())));

        // /打开微信
        try {
            Intent intent = new Intent();
            ComponentName cmp = new ComponentName("com.tencent.mm",
                    "com.tencent.mm.ui.LauncherUI");
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            mWXSDKInstance.getContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @JSMethod(uiThread = true)
    public void showDropDown(String list, float x, float y, float width, final JSCallback callback) {
        Log.d("test", "list = " + list);
        int screenwidth = ScreenUtil.getDisplayWidth((AppCompatActivity) mWXSDKInstance.getContext());
        int screenheight = ScreenUtil.getDisplayHeight((AppCompatActivity) mWXSDKInstance.getContext());
        String[] splits = list.split(",");
        int showlenght = splits.length > 6 ? 6 : splits.length;
        int height = showlenght * DensityUtil.dip2px(mWXSDKInstance.getContext(), 30);
        View contentView = LayoutInflater.from(mWXSDKInstance.getContext()).inflate(
                R.layout.pop_dropdown, null);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                (int) (screenwidth * width), height, true);
        popupWindow.setFocusable(true);//这里必须设置为true才能点击区域外或者消失
        popupWindow.setTouchable(true);//这个控制PopupWindow内部控件的点击事件
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();

        Activity a = (Activity) mWXSDKInstance.getContext();
        View root = a.getWindow().getDecorView();

        popupWindow.showAtLocation(root, Gravity.TOP | Gravity.LEFT, (int) (x), (int)
                (y));

        ListView lv = (ListView) contentView.findViewById(R.id.lv);
        MyAdapter adapter = new MyAdapter();
        lv.setAdapter(adapter);

        items.clear();
        for (int i = 0; i < splits.length; i++) {
            items.add(splits[i]);
        }
        adapter.notifyDataSetChanged();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap map = new HashMap();
                map.put("position", i);
                map.put("value", items.get(i));
                callback.invoke(map);
                popupWindow.dismiss();
            }
        });
    }

    private ArrayList<String> items = new ArrayList<>();

    @Override
    public void onFailure(Call call, IOException e) {
        toast("上传失败");
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (call.request().url().toString().equals(HttpUploadFile.uploadUserPicUrl)) {
            try {
                String token = new DefaultWXStorage(mWXSDKInstance.getContext()).performGetItem("token");
                JSONObject data = new JSONObject(response.body().string());
                Logger.log("------------" + data);
                if (data.optInt("status") == 200) {
                    String username = new DefaultWXStorage(mWXSDKInstance.getContext()).performGetItem("username");
                    uploadBackUrl = data.optJSONObject("result").optString("url");
                    RequestBody requestBodyPost = new FormBody.Builder()
                            .add("loginAccount", username)
                            .add("avatar", uploadBackUrl)
                            .add("token", token)
                            .build();
                    Request request = new Request.Builder()
                            .url(updateUserInfoUrl)
                            .post(requestBodyPost)
                            .build();
                    mOkHttpClient.newCall(request).enqueue(this);
                    Logger.log("------call------");
                } else {
                    toast("上传失败");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (call.request().url().toString().equals(updateUserInfoUrl))
            try {
                String s = response.body().string();
                Logger.log("----lllll--------" + s);
                JSONObject data = new JSONObject(s);
                Logger.log("----lllll--------" + data);
                if (data.optInt("status") == 200) {
                    toast("更新成功");
                    HashMap map = new HashMap();
                    map.put("url", uploadBackUrl);
                    pickerCallback.invoke(map);
                } else {
                    toast("更新失败");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }

    private class MyAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public MyAdapter() {
            inflater = LayoutInflater.from(mWXSDKInstance.getContext());
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder holder;
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.item_dropdown, null);
                holder = new ViewHolder();
                holder.tv = (TextView) rowView.findViewById(R.id.tv);
                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }
            holder.tv.setText(items.get(position));
            return rowView;
        }

        public class ViewHolder {
            public TextView tv;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public String getItem(int arg0) {
            return items.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }
    }
}

