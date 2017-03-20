package com.zk.myweex.extend.module;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.client.android.CaptureActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.loader.GlideImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.zk.myweex.utils.UploadUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import yjpty.teaching.acitivity.HeizInfoActivity;
import yjpty.teaching.http.BaseHttpRequest;


public class WXEventModule extends WXModule {

    private static final String WEEX_CATEGORY = "com.kiway.android.intent.category.WEEX";
    private static final String WEEX_ACTION = "com.kiway.android.intent.action.WEEX";


    @JSMethod(uiThread = true)
    public void openURL(String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        String scheme = Uri.parse(url).getScheme();
        StringBuilder builder = new StringBuilder();
        if (TextUtils.equals("http", scheme) || TextUtils.equals("https", scheme) || TextUtils.equals("file", scheme)) {
            builder.append(url);
        } else {
            builder.append("http:");
            builder.append(url);
        }

        Uri uri = Uri.parse(builder.toString());
        Intent intent = new Intent(WEEX_ACTION, uri);
        intent.addCategory(WEEX_CATEGORY);
        mWXSDKInstance.getContext().startActivity(intent);

        if (mWXSDKInstance.checkModuleEventRegistered("event", this)) {
            HashMap<String, Object> params = new HashMap<>();
            params.put("param1", "param1");
            params.put("param2", "param2");
            params.put("param3", "param3");
            mWXSDKInstance.fireModuleEvent("event", this, params);
        }
    }

    private JSCallback pickerCallback;

    String url;
    String jsessionid;

    @JSMethod(uiThread = true)
    public void PostSigalImg(String dic, JSCallback callback) {
        Log.d("test", "dic = " + dic);

        try {
            org.json.JSONObject obj = new org.json.JSONObject(dic);
            url = obj.getString("url");
            jsessionid = obj.getString("jsessionid");

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

    @JSMethod(uiThread = true)
    public void teachClass(String classId, JSCallback callback) {
        Log.d("test", "teachClass classid = " + classId);

        //判断当前SSID，相等就返回1。不等就设置wifi
    }


    private JSCallback scanCallback;

    //我要上课，扫描二维码
    @JSMethod(uiThread = true)
    public void QRScan(String classId, JSCallback callback) {
        Log.d("test", "QRScan classid = " + classId);
        this.scanCallback = callback;

        ((Activity) mWXSDKInstance.getContext()).startActivityForResult(new Intent(mWXSDKInstance.getContext(), CaptureActivity.class), 999);
    }

    @JSMethod(uiThread = true)
    public void ControllBox(String dic) {
        Log.d("test", "ControllBox dic = " + dic);

        //跳到另一个控制页面
    }


    @JSMethod(uiThread = true)
    public void CallPhone(String phone) {
        Log.d("test", "CallPhone phone = " + phone);
        try {
            JSONObject obj = new JSONObject(phone);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + obj.getString("phone")));
            mWXSDKInstance.getContext().startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @JSMethod()
    public void Publish(String str, JSCallback callback) {
        //这个是做什么的,调了一个http去做上传。。。
        Log.d("test", "publish str = " + str);

        try {
            JSONObject obj = new JSONObject(str);
            String url = obj.getString("url");
            String jsessionid = obj.getString("jsessionid");
            String content = obj.getString("content");
            org.json.JSONArray img_url = obj.getJSONArray("img_url");
            String classes = obj.getString("classes");


            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(10000);
            client.addHeader("Cookie", "JSESSIONID=" + jsessionid);

            RequestParams params = new RequestParams();
            params.put("classes", classes);
            params.put("content", content);
            params.put("img_url", img_url);

            client.post(mWXSDKInstance.getContext(), url, params, new TextHttpResponseHandler() {

                public void onFailure(int statusCode, org.apache.http.Header[] headers, String responseString, Throwable throwable) {
                    Log.d("test", "onFailure = " + responseString);
                }

                public void onSuccess(int statusCode, org.apache.http.Header[] headers, final String responseString) {
                    Log.d("test", "onSuccess = " + responseString);
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @JSMethod()
    public void AddClass(String str, JSCallback callback) {
        //这个是做什么的？

        //跳到一个页面去了，QRCode_ViewController
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

        } else if (requestCode == 999) {
            //扫描二维码返回
            if (data == null) {
                return;
            }
            String result = data.getStringExtra("result");
            Toast.makeText(mWXSDKInstance.getContext(), "扫描到的是" + result, Toast.LENGTH_SHORT).show();

            //扫描二维码，扫描后的数据返回给js
            //callback(@{@"result":result,@"hCode":Hcode,@"SSID":ssid});
        }
    }


    @JSMethod(uiThread = true)
    public void teaching(String url) {

        BaseHttpRequest.JSESSIONID = url;
        Log.d("test", "teaching url = " + url);
        //我要上课，要什么参数。
        Intent i = new Intent(mWXSDKInstance.getContext(), HeizInfoActivity.class);

        mWXSDKInstance.getContext().startActivity(i);
    }


    private void doUploadImage(final ArrayList<ImageItem> images) {
        new Thread() {
            @Override
            public void run() {
                ImageItem ii = images.get(0);
                File file = new File(ii.path);
                String ret = UploadUtil.uploadFile(file, "http://192.168.8.114:8888/yjpt/course/file", "qzq", "JSESSIONID=" + jsessionid);
                Log.d("test", "upload ret = " + ret);

                if (!ret.contains("200")) {
                    return;
                }

                try {
                    String imgUrl = new JSONObject(ret).getJSONObject("data").getString("url");

//                  callback(@{@"path":path,@"imgUrl":imgUrl});

                    HashMap map = new HashMap();
                    map.put("path", "file://" + ii.path);
                    map.put("imgUrl", imgUrl);

                    pickerCallback.invoke(map);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
