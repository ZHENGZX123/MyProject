package cn.kiway.yqyd.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.loader.GlideImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import cn.bingoogolapple.qrcode.zxingdemo.ScanActivity;
import cn.kiway.yqyd.App;
import cn.kiway.yqyd.R;
import cn.kiway.yqyd.dialog.LoginDialog;
import cn.kiway.yqyd.utils.HanderMessageWhat;
import cn.kiway.yqyd.utils.HttpUploadFile;
import cn.kiway.yqyd.utils.Logger;
import cn.kiway.yqyd.utils.SharedPreferencesUtil;
import cn.kiway.yqyd.utils.sound.AudioRecoderUtils;
import cn.kiway.yqyd.utils.sound.PopupWindowFactory;
import cn.kiway.yqyd.utils.sound.TimeUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static cn.kiway.yqyd.utils.HanderMessageWhat.ResultMessage888;
import static cn.kiway.yqyd.utils.HanderMessageWhat.ResultMessage999;
import static cn.kiway.yqyd.utils.HanderMessageWhat.messageWhat1;
import static cn.kiway.yqyd.utils.HttpUploadFile.updateUserInfoUrl;

public class WebViewActivity2 extends Activity implements Callback {

    private WebView wv;
    private String uploadBackUrl = "";
    private LoginDialog loginDialog;
    App app;
    private boolean isImg;
    /**
     * 录音
     */
    private ImageView mImageView;
    private TextView mTextView;
    private AudioRecoderUtils mAudioRecoderUtils;
    private Button SoundRecord;
    private PopupWindowFactory mPop;
    private RelativeLayout rl;

    float x1, y1, x2, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview2);
        wv = (WebView) findViewById(R.id.wv);
        loginDialog = new LoginDialog(this);
        app = (App) getApplicationContext();
        initData();
        load();
        initSoundRecord();
    }


    private void initData() {
        //跨域问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            wv.getSettings().setAllowUniversalAccessFromFileURLs(true);
        } else {
            try {
                Class<?> clazz = wv.getSettings().getClass();
                Method method = clazz.getMethod("setAllowUniversalAccessFromFileURLs", boolean.class);
                if (method != null) {
                    method.invoke(wv.getSettings(), true);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setDomStorageEnabled(true);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setLoadWithOverviewMode(true);
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        wv.setVerticalScrollBarEnabled(false);
        wv.setWebChromeClient(new WebChromeClient());
        wv.addJavascriptInterface(new JsAndroidIntetface(), "js");
    }

    private void load() {
        String token = getIntent().getStringExtra("token");
        wv.loadUrl("file:///android_asset/yqyd(teacher)/yqyd/yqyd/index/index.html?token=" + token);
    }


    long time = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.d("test", "click back = " + wv.getUrl());
            String url = wv.getUrl();
            String page = url.substring(url.lastIndexOf("/") + 1);
            //Toast.makeText(this, page, Toast.LENGTH_SHORT).show();
            if (page.contains("file:///android_asset/yqyd(teacher)/yqyd/yqyd/index/index.html?token=") || page
                    .endsWith("sc") || page.endsWith("main") || page.endsWith("/class/myclass")) {
                long t = System.currentTimeMillis();
                if (t - time >= 2000) {
                    time = t;
                    Toast.makeText(this, "再按一下退出", Toast.LENGTH_SHORT).show();
                } else
                    finish();
                return true;
            } else {
                if (wv.canGoBack()) {
                    wv.goBack();//返回上一页面
                    return true;
                } else {
                    long t = System.currentTimeMillis();
                    if (t - time >= 2000) {
                        time = t;
                        Toast.makeText(this, "再按一下退出", Toast.LENGTH_SHORT).show();
                    } else
                        finish();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == ResultMessage888) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker
                        .EXTRA_RESULT_ITEMS);
                uploadUserPic(images.get(0).path, HttpUploadFile.FileType
                        .Image, true);
            } else {
                Toast.makeText(this, "更新失败", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == ResultMessage999 && data != null) { //扫描二维码返回
            String result = data.getStringExtra("result");
            Toast.makeText(WebViewActivity2.this, result, Toast.LENGTH_SHORT).show();
        }
    }

    void uploadUserPic(String fileName, String fileType, boolean isImg) {
        if (loginDialog != null)
            loginDialog.show();
        this.isImg = isImg;
        try {
            app.mOkHttpClient.newCall(HttpUploadFile.returnUploadImgRequser(new File(fileName), fileType)).enqueue
                    (this);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {
        Message message = new Message();
        message.obj = "上传失败";
        message.what = HanderMessageWhat.messageWhat2;
        if (call.request().url().toString().equals(HttpUploadFile.uploadUserPicUrl)) {
            handler.sendMessage(message);
        } else if (call.request().url().toString().equals(updateUserInfoUrl + uploadBackUrl)) {
            handler.sendMessage(message);
        }
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (call.request().url().toString().equals(HttpUploadFile.uploadUserPicUrl)) {
            try {
                JSONObject data = new JSONObject(response.body().string());
                Logger.log("------------" + data);
                if (data.optInt("status") == 200) {
                    if (isImg) {
                        uploadBackUrl = data.optJSONObject("result").optString("url");
                        RequestBody requestBodyPost = new FormBody.Builder()
                                .add("loginAccount", SharedPreferencesUtil.getString(this, "userName"))
                                .add("avatar", uploadBackUrl)
                                .build();
                        Request request = new Request.Builder()
                                .url(updateUserInfoUrl)
                                .post(requestBodyPost)
                                .build();
                        app.mOkHttpClient.newCall(request).enqueue(this);
                    } else {
                        Logger.log("---------------------语音");
                    }
                } else {
                    Message message = new Message();
                    message.obj = "上传失败";
                    message.what = 2;
                    handler.sendMessage(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (call.request().url().toString().equals(updateUserInfoUrl))
            try {
                JSONObject data = new JSONObject(response.body().string());
                Message message = new Message();
                if (data.optInt("status") == 200) {
                    message.obj = "javascript:changeUserImg('" + uploadBackUrl + "')";
                    message.what = messageWhat1;
                } else {
                    message.obj = "更新失败";
                    message.what = HanderMessageWhat.messageWhat2;
                }
                handler.sendMessage(message);
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HanderMessageWhat.messageWhat1:
                    if (loginDialog != null)
                        loginDialog.close();
                    wv.loadUrl((String) msg.obj);
                    break;
                case HanderMessageWhat.messageWhat2:
                    if (loginDialog != null)
                        loginDialog.close();
                    Toast.makeText(WebViewActivity2.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public class JsAndroidIntetface {
        public JsAndroidIntetface() {
        }

        @JavascriptInterface
        public void logout() {
            //退出
            SharedPreferencesUtil.save(WebViewActivity2.this, "userName", "");
            SharedPreferencesUtil.save(WebViewActivity2.this, "passWord", "");
            startActivity(new Intent(WebViewActivity2.this, LoginActivity.class));
        }

        @JavascriptInterface
        public void scan() {
            //扫码的功能
            startActivityForResult(new Intent(WebViewActivity2.this, ScanActivity.class), ResultMessage999);
        }

        @JavascriptInterface
        public String getSessionObj() {//js获取用户名
            JSONObject da = new JSONObject();
            try {
                da.put("userName", SharedPreferencesUtil.getString(WebViewActivity2.this, "userName"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return da.toString();
        }

        @JavascriptInterface
        public void updateUserInfo() {
            //imagepicker
            ImagePicker imagePicker = ImagePicker.getInstance();
            imagePicker.setImageLoader(new GlideImageLoader());// 图片加载器
            imagePicker.setSelectLimit(1);// 设置可以选择几张
            imagePicker.setMultiMode(false);// 是否为多选
            imagePicker.setCrop(true);// 是否剪裁
            imagePicker.setFocusWidth(800);// 需要剪裁的宽
            imagePicker.setFocusHeight(800);// 需要剪裁的高
            imagePicker.setStyle(CropImageView.Style.RECTANGLE);// 方形
            imagePicker.setShowCamera(true);// 是否显示摄像
            Intent intent = new Intent(WebViewActivity2.this, ImageGridActivity.class);
            WebViewActivity2.this.startActivityForResult(intent, ResultMessage888);
        }

        @JavascriptInterface
        public void SoundRecording() {
            //录音上传
        }
    }

    /**
     * 录音
     */
    void initSoundRecord() {
        rl = (RelativeLayout) findViewById(R.id.rl);
        //PopupWindow的布局文件
        final View view = View.inflate(this, R.layout.layout_microphone, null);
        mPop = new PopupWindowFactory(this, view);
        //PopupWindow布局文件里面的控件
        mImageView = (ImageView) view.findViewById(R.id.iv_recording_icon);
        mTextView = (TextView) view.findViewById(R.id.tv_recording_time);
        SoundRecord = (Button) findViewById(R.id.SoundRecord);
        mAudioRecoderUtils = new AudioRecoderUtils();
        //录音回调
        mAudioRecoderUtils.setOnAudioStatusUpdateListener(new AudioRecoderUtils.OnAudioStatusUpdateListener() {
            //录音中....db为声音分贝，time为录音时长
            @Override
            public void onUpdate(double db, long time) {
                mImageView.getDrawable().setLevel((int) (3000 + 6000 * db / 100));
                if (TimeUtils.long2String(time).equals("01:00")) {
                    mPop.dismiss();
                    SoundRecord.setText("按住说话");
                    mAudioRecoderUtils.stopRecord();        //结束录音（保存录音文件）
                } else {
                    mTextView.setText(TimeUtils.long2String(time));
                }
            }

            //录音结束，filePath为保存路径
            @Override
            public void onStop(String filePath) {
                mTextView.setText(TimeUtils.long2String(0));
                uploadUserPic(filePath, HttpUploadFile.FileType
                        .Mp3, false);
            }
        });
        //Button的touch监听
        SoundRecord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //当手指按下的时候
                        x1 = event.getX();
                        y1 = event.getY();
                        mPop.showAtLocation(rl, Gravity.CENTER, 0, 0);
                        SoundRecord.setText("松开保存,上滑取消");
                        mAudioRecoderUtils.startRecord();
                        break;

                    case MotionEvent.ACTION_UP:
                        //当手指离开的时候
                        x2 = event.getX();
                        y2 = event.getY();
                        if (y1 - y2 > 50) {
                            mAudioRecoderUtils.cancelRecord();    //取消录音（不保存录音文件）
                        } else {
                            mAudioRecoderUtils.stopRecord();        //结束录音（保存录音文件）
                        }
                        mPop.dismiss();
                        SoundRecord.setText("按住说话");
                        break;
                }
                return true;
            }
        });
    }
}