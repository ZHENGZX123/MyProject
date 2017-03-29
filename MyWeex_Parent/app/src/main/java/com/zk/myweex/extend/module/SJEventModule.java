package com.zk.myweex.extend.module;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.loader.GlideImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.pay.util.Constants;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zk.myweex.activity.LoginActivity;
import com.zk.myweex.activity.MainActivity2;
import com.zk.myweex.utils.ScreenManager;
import com.zk.myweex.utils.UploadUtil;
import com.zk.myweex.utils.Utils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.kwim.mqttcilent.mqttclient.MqttInstance;
import yjpty.teaching.http.BaseHttpHandler;
import yjpty.teaching.http.BaseHttpRequest;
import yjpty.teaching.http.HttpHandler;
import yjpty.teaching.http.HttpResponseModel;
import yjpty.teaching.http.IUrContant;
import yjpty.teaching.util.IConstant;


public class SJEventModule extends WXModule implements HttpHandler {
    /**
     * 微信API
     */
    private IWXAPI msgApi;
    /**
     * 请求回调
     */
    protected BaseHttpHandler activityHandler = new BaseHttpHandler(this) {
    };
    /**
     * 微信支付请求
     */
    private PayReq req;
//    @JSMethod(uiThread = true)
//    public void loadFunction(final String zipName, final JSCallback callback) {
//        Toast.makeText(mWXSDKInstance.getContext(), " loadJSBundle js :" + zipName, Toast.LENGTH_SHORT).show();
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    load(zipName, callback);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }
//
//    private void load(final String zipName, final JSCallback callback) throws Exception {
//        Log.d("test", "load name = " + zipName);
//        String path = WXApplication.PATH + zipName;
//        if (new File(path).exists()) {
//            Log.d("test", "存在，直接加载");
//            ZipPackage zip = Realm.getDefaultInstance().where(ZipPackage.class).equalTo("name", zipName).findFirst();
//            loadJSBundle(zipName, zip.indexPath);
//        } else {
//            Log.d("test", "不存在，下载");
//            Service s = new Service().findOne(new KWQuery().equalTo("id", zipName.replace(".zip", "")));
//            Log.d("test", "s  = " + s.toString());
//            //返回最新的全量包
//            Package p = new Package().findOne(new KWQuery().equalTo("serviceId", s.getId()).equalTo("updateType", "all").equalTo("platform", "android").descending("version"));
//            Log.d("test", "p = " + p.toString());
//            String baseUrl = s.get("baseUrl").toString();
//            String downloadUrl = p.get("url").toString();
//            String version = p.get("version").toString();
//            downloadJSBundle(zipName, downloadUrl, version, baseUrl);
//        }
//    }
//
//    @JSMethod(uiThread = true)
//    public void deleteFunction(String zipName, JSCallback callback) {
//        String path = "file://" + WXApplication.PATH + zipName;
//        Log.d("test", "path = " + path);
//        if (new File(path).exists()) {
//            Log.d("test", "存在，删除");
//            new File(path).delete();
//            final RealmResults<ZipPackage> results = Realm.getDefaultInstance().where(ZipPackage.class).equalTo("name", zipName).findAll();
//            Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
//                @Override
//                public void execute(Realm realm) {
//                    results.clear();
//                }
//            });
//            callback.invoke("delete success");
//        } else {
//            Log.d("test", "不存在，不用管");
//        }
//    }
//
//    //首次下载
//    public void downloadJSBundle(final String zipName, final String downloadUrl, final String version, final String baseUrl) {
//        //1.访问接口，参数是zipName，返回是 name， 下载地址 ， 版本号
//        HttpDownload httpDownload = new HttpDownload();
//        int ret = httpDownload.downFile(downloadUrl, WXApplication.PATH, zipName);
//        Log.d("test", "下载返回值 ret = " + ret);
//        if (ret != 0) {
//            toast("下载失败，请稍后再试");
//            return;
//        }
//        Log.d("test", "下载成功，保存版本号");
//
//        Realm.getDefaultInstance().beginTransaction();
//        ZipPackage zip = Realm.getDefaultInstance().createObject(ZipPackage.class);
//        zip.name = zipName;
//        zip.indexPath = baseUrl;
//        zip.version = version;
//        Realm.getDefaultInstance().commitTransaction();
//        Log.d("test", "下载成功，加载本地sdcard");
//        loadJSBundle(zipName, baseUrl);
//    }
//
//    public void loadJSBundle(String zipName, String baseUrl) {
//        String path = WXApplication.PATH + zipName + "/" + baseUrl;
//        Log.d("test", "loadJSBundle path = " + path);
//        Intent intent = new Intent(mWXSDKInstance.getContext(), WXPageActivity.class);
//        intent.setData(Uri.parse(path));
//        mWXSDKInstance.getContext().startActivity(intent);
//    }
//
//    @JSMethod(uiThread = true)
//    public void sendEvent(JSCallback callback) {
//        Log.d("test", "module id = " + mWXSDKInstance.getInstanceId());
//        Map<String, Object> params = new HashMap<>();
//        params.put("test1", "test1");
//        params.put("test2", "test2");
//        //这个只能调用相同instance的事件。
////        mWXSDKInstance.fireGlobalEventCallback("tab1_event", params);
//        //这个只能callback
////        callback.invoke(params);
//        mWXSDKInstance.fireSuperGlobalEventCallback("tab1_event", params);
//    }


    @JSMethod(uiThread = true)
    public void showLog(String tag, String log) {
        Log.d(tag, log);
    }


    @JSMethod(uiThread = true)
    public void loginSuccess(String url) {
        Log.d("test", "loginSuccess url = " + url);

//        {"pageUrl":"http://assets/yjpts/weex_jzd/index.js","userPwd":"111111","userName":"13430893721","jsessionid":"6B1C6F0669567E562130F2618D6E603C","userType":"2"}

        mWXSDKInstance.getContext().getSharedPreferences("kiway", 0).edit().putBoolean("login", true).commit();
        try {
            String userName = new JSONObject(url).getString("userName");
            String userPwd = new JSONObject(url).getString("userPwd");
            String jsessionid = new JSONObject(url).getString("jsessionid");

            mWXSDKInstance.getContext().getSharedPreferences("kiway", 0).edit().putString("userName", userName).commit();
            mWXSDKInstance.getContext().getSharedPreferences("kiway", 0).edit().putString("userPwd", userPwd).commit();
            mWXSDKInstance.getContext().getSharedPreferences("kiway", 0).edit().putString("jsessionid", jsessionid).commit();


        } catch (JSONException e) {
            e.printStackTrace();
        }

        mWXSDKInstance.getContext().startActivity(new Intent(mWXSDKInstance.getContext(), MainActivity2.class));
        ((Activity) mWXSDKInstance.getContext()).finish();
    }

    @JSMethod(uiThread = true)
    public void logoutSuccess(String url) {
        Log.d("test", "logoutSuccess");
        try {
            //这里还要退出mqtt。
            MqttInstance.getInstance().getPushInterface().logout();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

            Bitmap b = Utils.createQRImage("hello", 400, 400);
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
    public void PostSigalImg(String dic, JSCallback callback) {
        Log.d("test", "PostSigalImg dic = " + dic);

        try {
            org.json.JSONObject obj = new org.json.JSONObject(dic);
            userId = obj.getString("userId");
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 888 && resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data == null) {
                return;
            }
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            Log.d("test", "images count = " + images.size());
            HashMap map = new HashMap();
            map.put("path", "file://" + images.get(0).path);
            pickerCallback.invoke(map);
            doUploadImage(images);
        } else if (requestCode == 8888) {
            //微信支付成功
            Log.d("test", "8888");
        }
    }

    private void doUploadImage(final ArrayList<ImageItem> images) {
        new Thread() {
            @Override
            public void run() {
                ImageItem ii = images.get(0);

                File file = new File(ii.path);
                String ret = UploadUtil.uploadFile(file, "http://192.168.8.114:8888/yjpt/course/file", "icon", "JSESSIONID=" + jsessionid);
                Log.d("test", "upload ret = " + ret);
            }
        }.start();
    }

    @JSMethod(uiThread = true)
    public void WeChatPay(String url) {

        BaseHttpRequest.JSESSIONID = mWXSDKInstance.getContext().getSharedPreferences("kiway", 0).getString("jsessionid", "");

        msgApi = WXAPIFactory.createWXAPI(mWXSDKInstance.getContext(), null);
        Log.d("test", "url = " + url);

        req = new PayReq();
        msgApi.registerApp(Constants.APP_ID);
        Activity a = ((Activity) mWXSDKInstance.getContext());
        if (!msgApi.isWXAppInstalled() || msgApi.isWXAppSupportAPI()) {
            Toast.makeText(a, "没有安装微信", Toast.LENGTH_SHORT).show();
            return;
        }
        if (msgApi.getWXAppSupportAPI() < Build.PAY_SUPPORTED_SDK_INT) {
            Toast.makeText(a, "微信版本过低，不支持支付", Toast.LENGTH_SHORT).show();
            return;
        }
        JSONObject data = null;
        try {
            data = new JSONObject(url);
            Map<String, String> map = new HashMap<String, String>();
            map.put("attach", data.getString("attach"));
            map.put("total", data.getString("total"));
            map.put("outTradeNo", data.getString("outTradeNo"));
            map.put("remark", data.getString("remark"));
            IConstant.HTTP_CONNECT_POOL.addRequest(IUrContant.GET_WEI_PRIDUCT_URL, map, activityHandler, true, 1);


            // 测试用
//{"total":0.06,"remark":"智慧课堂","attach":{"childId":"ffc7337009f211e7b048299dbf864a63","classId":"bc6f1550093611e7bc6713374ff06eb4","schoolId":"344da9f107cb11e7bbb321aab2798d9f","payUserId":"43f8d7f00d3c11e7a588051dfb74031a"},"outTradeNo":"20170328173719695","url":"file:///mnt/sdcard/kiway/weex/ParentTab0.zip/yjpt/weex_jzd/catalog-list.js"}

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void httpErr(HttpResponseModel message) throws Exception {

    }

    @Override
    public void httpSuccess(HttpResponseModel message) throws Exception {
        if (message.getUrl().equals(IUrContant.GET_WEI_PRIDUCT_URL)) {
            JSONObject da = new JSONObject(new String(message.getResponse()));
            req.appId = Constants.APP_ID;
            req.partnerId = Constants.MCH_ID;
            req.prepayId = da.optString("prepayId");
            req.packageValue = "Sign=WXPay";
            req.nonceStr = da.optString("nonceStr");
            req.timeStamp = da.optString("timeStamp");
            req.extData = "app data"; // optional
            List<NameValuePair> signParams = new LinkedList<NameValuePair>();
            signParams.add(new BasicNameValuePair("appid", req.appId));
            signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
            signParams.add(new BasicNameValuePair("package", req.packageValue));
            signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
            signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
            signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
            req.sign = da.optString("signSecond");
            msgApi.sendReq(req);
        }
    }

    @Override
    public void HttpError(HttpResponseModel message) throws Exception {

    }
}

