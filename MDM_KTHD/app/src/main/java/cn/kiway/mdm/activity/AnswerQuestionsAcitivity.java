package cn.kiway.mdm.activity;

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

import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.kiway.mdm.utils.MyWebViewClient;
import studentsession.kiway.cn.mdm_studentsession.R;

/**
 * Created by Administrator on 2018/1/3.
 */

public class AnswerQuestionsAcitivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    JSONObject data = new JSONObject();
    RadioGroup choiceQuestion, TofF;

    private WebView wv;

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
            return "/mnt/sdcard/test.jpg";
        }

        @JavascriptInterface
        public void saveBase64(String param) {
            Log.d("test", "saveBase64 is called , param = " + param);
            Bitmap bitmap = null;
            try {
                byte[] bitmapArray = Base64.decode(param.split(",")[1], Base64.DEFAULT);
                bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
                //Utils.saveBitmap(bitmap, System.currentTimeMillis() + ".png");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
