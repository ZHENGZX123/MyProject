package com.zk.myweex.extend.module;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaRecorder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.loader.GlideImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.qiniu.android.common.Zone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.zk.myweex.activity.LoginActivity;
import com.zk.myweex.activity.MainActivity2;
import com.zk.myweex.regionselector.RegionSelectActivity;
import com.zk.myweex.regionselector.util.DBCopyUtil;
import com.zk.myweex.utils.ScreenManager;
import com.zk.myweex.utils.Utils;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import cn.kiway.yiqiyuedu.R;
import uk.co.senab.photoview.sample.ViewPagerActivity;

import static com.zk.myweex.utils.Utils.getQiniuToken;


public class SJEventModule extends WXModule {

    @JSMethod(uiThread = true)
    public void showLog(String tag, String log) {
        Log.d(tag, log);
    }

    @JSMethod(uiThread = true)
    public void loginSuccess(String username) {
        Log.d("test", "loginSuccess username = " + username);
        mWXSDKInstance.getContext().getSharedPreferences("kiway", 0).edit().putBoolean("login", true).commit();
        mWXSDKInstance.getContext().getSharedPreferences("kiway", 0).edit().putString("userName", username).commit();
        mWXSDKInstance.getContext().startActivity(new Intent(mWXSDKInstance.getContext(), MainActivity2.class));
        ScreenManager.getScreenManager().popAllActivityExceptOne(MainActivity2.class);
    }

    @JSMethod(uiThread = true)
    public void logoutSuccess(String url) {
        doLogout();
    }

    @JSMethod(uiThread = true)
    public void logoutSuccess() {
        doLogout();
    }

    private void doLogout() {
        Log.d("test", "logoutSuccess");
        mWXSDKInstance.getContext().getSharedPreferences("kiway", 0).edit().putBoolean("login", false).commit();
        mWXSDKInstance.getContext().startActivity(new Intent(mWXSDKInstance.getContext(), LoginActivity.class));
        ScreenManager.getScreenManager().popAllActivityExceptOne(LoginActivity.class);
    }


    @JSMethod(uiThread = true)
    public void makeQRCode(String dic, JSCallback callback) {
        Log.d("test", "makeQRCode = " + dic);
        //生成一个二维码，保存在本地，把路径callback回去。
        try {
            org.json.JSONObject obj = new org.json.JSONObject(dic);
            String classId = obj.getString("classId");
            String className = obj.getString("className");
            String schoolId = obj.getString("schoolId");
            String code = "http://www.yuertong.com/yjpts/?&ref=class&classid=" + classId + "&schoolId=" + schoolId + "&classname=" + className;
            Bitmap b = Utils.createQRImage(code, 400, 400);
            String filepath = Utils.saveMyBitmap(b, "myweex");
            callback.invoke("file://" + filepath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JSCallback pickerCallback;
    String userId;
    String url;
    String jsessionid;

    @JSMethod(uiThread = true)
    public void PostSigalImg(JSCallback callback) {
//        Log.d("test", "PostSigalImg dic = " + dic);
        try {
//            org.json.JSONObject obj = new org.json.JSONObject(dic);
//            userId = obj.getString("userId");
//            url = obj.getString("url");
//            jsessionid = obj.getString("jsessionid");

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
        if (recordFile != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
        }

        final int duration = (int) (System.currentTimeMillis() - start) / 1000;
        if (duration < 1) {
            toast("太短了");
            return;
        }
        String key = recordFile.getName();
        Configuration config = new Configuration.Builder().zone(Zone.zone2).connectTimeout(10).build();
        UploadManager uploadManager = new UploadManager(config);
        uploadManager.put(recordFile, key, getQiniuToken(),
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        if (info.isOK()) {
                            Log.i("qiniu", "Upload Success");
                            String url = "http://ooy49eq1n.bkt.clouddn.com/" + key;
                            HashMap map = new HashMap();
                            map.put("duration", duration);
                            map.put("url", url);
                            pickerCallback.invoke(map);
                        } else {
                            Log.i("qiniu", "Upload Fail");
                            toast("上传失败，请稍后再试");
                        }
                    }
                }, null);
    }

    private void startRecord() {
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
        try {
            // 准备好开始录音
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        start = System.currentTimeMillis();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 888 && resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data == null) {
                return;
            }
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            Log.d("test", "images count = " + images.size());

            doUploadImage(images);
        } else if (requestCode == 100 && resultCode == 200) {
            String province = data.getStringExtra(RegionSelectActivity.REGION_PROVINCE);
            String city = data.getStringExtra(RegionSelectActivity.REGION_CITY);
            String area = data.getStringExtra(RegionSelectActivity.REGION_AREA);
            selectCallback.invoke(province + city + area);
        }
    }

    private void doUploadImage(final ArrayList<ImageItem> images) {
//        new Thread() {
//            @Override
//            public void run() {
//                ImageItem ii = images.get(0);
//
//                File file = new File(ii.path);
//                String ret = UploadUtil.uploadFile(file, url, "icon", "JSESSIONID=" + jsessionid);
//                Log.d("test", "upload ret = " + ret);
//                if (ret == null) {
//                    return;
//                }
//                if (!ret.contains("200")) {
//                    return;
//                }
//                HashMap map = new HashMap();
//                try {
//                    map.put("path", new JSONObject(ret).getJSONObject("data").getString("url"));//"file://" + images.get(0).path
//                    pickerCallback.invoke(map);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();

        ImageItem ii = images.get(0);
        String key = System.currentTimeMillis() + ".jpg";
        File file = new File(ii.path);
        Configuration config = new Configuration.Builder().zone(Zone.zone2).connectTimeout(10).build();
        UploadManager uploadManager = new UploadManager(config);
        uploadManager.put(file, key, getQiniuToken(),
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        if (info.isOK()) {
                            Log.i("qiniu", "Upload Success");
                            String url = "http://ooy49eq1n.bkt.clouddn.com/" + key;
                            HashMap map = new HashMap();
                            map.put("url", url);
                            pickerCallback.invoke(map);
                        } else {
                            Log.i("qiniu", "Upload Fail");
                            toast("上传失败，请稍后再试");
                        }
                    }
                }, null);
    }

    @JSMethod(uiThread = true)
    public void backToMain() {
        Log.d("test", "backToMain is called");
        mWXSDKInstance.getContext().startActivity(new Intent(mWXSDKInstance.getContext(), MainActivity2.class));
    }

    @JSMethod(uiThread = true)
    public void showPhoto(String param1, String param2) {
        try {
            Log.d("test", "showPhoto param1 = " + param1);
            Log.d("test", "showPhoto param2 = " + param2);
            ViewPagerActivity.sDrawables = param1.replace("[", "").replace("]", "").replace("\"", "").split(",");
            Intent intent = new Intent(mWXSDKInstance.getContext(), ViewPagerActivity.class);
            intent.putExtra("position", Integer.parseInt(param2));//0 , 1
            mWXSDKInstance.getContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private JSCallback selectCallback;

    @JSMethod(uiThread = true)
    public void selectRegion(JSCallback callback) {
        this.selectCallback = callback;
        DBCopyUtil.copyDataBaseFromAssets(mWXSDKInstance.getContext(), "region.db");
        ((Activity) mWXSDKInstance.getContext()).startActivityForResult(new Intent(mWXSDKInstance.getContext(), RegionSelectActivity.class), 100);
    }

}

