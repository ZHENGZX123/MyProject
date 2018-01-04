package cn.kiway.mdm.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RadioGroup;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.loader.GlideImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.nanchen.compresshelper.CompressHelper;

import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import cn.kiway.mdm.utils.MyWebViewClient;
import cn.kiway.mdm.utils.Utils;
import studentsession.kiway.cn.mdm_studentsession.R;

/**
 * Created by Administrator on 2018/1/3.
 */

public class AnswerQuestionsAcitivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    JSONObject data = new JSONObject();
    RadioGroup choiceQuestion, TofF;

    private WebView wv;
    private static final int SELECT_PHOTO = 8888;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
//        try {
//            data = new JSONObject(getIntent().getStringExtra("data"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        initView();
        initData();
        load();
    }

    void initView() {
        choiceQuestion = (RadioGroup) findViewById(R.id.choiceQuestion);
        TofF = (RadioGroup) findViewById(R.id.TofF);
        wv = (WebView) findViewById(R.id.wv);
        choiceQuestion.setOnCheckedChangeListener(this);
        TofF.setOnCheckedChangeListener(this);
    }

    void initData() {
        // int qusetionType = data.optJSONObject("content").optInt("type");
        int qusetionType = 5;
        switch (qusetionType) {
            case 1://选择
                findViewById(R.id.choiceQuestion).setVisibility(View.VISIBLE);
                break;
            case 2://问答
                break;
            case 3://填空
                findViewById(R.id.blankAnswers).setVisibility(View.VISIBLE);
                break;
            case 4://判断
                findViewById(R.id.TofF1).setVisibility(View.VISIBLE);
                break;
            case 5://主观题
                findViewById(R.id.wv).setVisibility(View.VISIBLE);
                findViewById(R.id.ok).setVisibility(View.GONE);
                break;
        }


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
        //settings.setDomStorageEnabled(true); 画板不能用这个属性
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setLoadWithOverviewMode(true);
        wv.setWebViewClient(new MyWebViewClient());
        wv.setVerticalScrollBarEnabled(false);
        wv.setWebChromeClient(new WebChromeClient());
        wv.addJavascriptInterface(new JsAndroidInterface2(), "wx");
    }

    private void load() {
        wv.clearCache(true);
        String url = "file:///android_asset/whiteboard/index.html";
        Log.d("test", "url = " + url);
        wv.loadUrl(url);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch (radioGroup.getId()) {
            case R.id.TofF:
                break;
            case R.id.choiceQuestion:
                break;
        }
    }

    public class JsAndroidInterface2 {
        public JsAndroidInterface2() {
        }

        @JavascriptInterface
        public String setBackgroundImage() {
            Log.d("test", "setBackgroundImage is called");
            return "";///mnt/sdcard/test.jpg
        }

        @JavascriptInterface
        public void saveBase64(String param) {
            Log.d("test", "saveBase64 is called , param = " + param);
            Bitmap bitmap = null;
            try {
                byte[] bitmapArray = Base64.decode(param.split(",")[1], Base64.DEFAULT);
                bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
                Utils.saveBitmap(bitmap, System.currentTimeMillis() + ".png");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @JavascriptInterface
        public void selectPhoto() {
            Log.d("test", "selectPhoto is called");
            ImagePicker imagePicker = ImagePicker.getInstance();
            imagePicker.setImageLoader(new GlideImageLoader());// 图片加载器
            imagePicker.setSelectLimit(1);// 设置可以选择几张
            imagePicker.setMultiMode(false);// 是否为多选
            imagePicker.setCrop(true);// 是否剪裁
            imagePicker.setFocusWidth(1000);// 需要剪裁的宽
            imagePicker.setFocusHeight(1000);// 需要剪裁的高
            imagePicker.setStyle(CropImageView.Style.RECTANGLE);// 方形
            imagePicker.setShowCamera(true);// 是否显示摄像
            Intent intent = new Intent(AnswerQuestionsAcitivity.this, ImageGridActivity.class);
            startActivityForResult(intent, SELECT_PHOTO);
        }

        @JavascriptInterface
        public void submitBase64(String param) {
            Log.d("test", "submitBase64 param = " + param);
            //1.保存图片到本地
            //2.提交给服务器
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO && resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data == null) {
                return;
            }
            boolean isOrig = data.getBooleanExtra(ImagePreviewActivity.ISORIGIN, false);
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (!isOrig) {
                Log.d("test", "压缩前大小" + new File(images.get(0).path).length());
                File newFile = CompressHelper.getDefault(this).compressToFile(new File(images.get(0).path));
                images.get(0).path = newFile.getAbsolutePath();
                images.get(0).size = newFile.length();
                Log.d("test", "压缩后大小" + images.get(0).size);
            }
            String path = images.get(0).path;

            wv.loadUrl("javascript:selectPhotoCallback('file://" + path + "')");
        }
    }
}
