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
import com.zk.myweex.WXApplication;
import com.zk.myweex.activity.LoginActivity;
import com.zk.myweex.activity.MainActivity2;
import com.zk.myweex.utils.ScreenManager;
import com.zk.myweex.utils.UploadUtil;
import com.zk.myweex.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cn.kwim.mqttcilent.common.cache.dao.DaoType;
import cn.kwim.mqttcilent.common.cache.dao.MainListDao;
import cn.kwim.mqttcilent.common.cache.javabean.FriendList;
import cn.kwim.mqttcilent.common.cache.javabean.GroupContent;
import cn.kwim.mqttcilent.common.cache.javabean.GroupListMember;
import cn.kwim.mqttcilent.common.cache.javabean.MainList;
import cn.kwim.mqttcilent.common.cache.javabean.Message;
import cn.kwim.mqttcilent.mqttclient.MqttInstance;
import cn.kwim.mqttcilent.mqttclient.mq.PushInterface;
import io.realm.Realm;
import uk.co.senab.photoview.sample.ViewPagerActivity;
import yjpty.teaching.http.BaseHttpHandler;
import yjpty.teaching.http.BaseHttpRequest;
import yjpty.teaching.http.HttpHandler;
import yjpty.teaching.http.HttpResponseModel;
import yjpty.teaching.http.IUrContant;
import yjpty.teaching.util.IConstant;

import static cn.kwim.mqttcilent.common.cache.dao.Dao.getRealm;


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
        new Thread() {
            @Override
            public void run() {
                try {
                    MqttInstance.getInstance().getPushInterface().logout();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

        Realm realm = getRealm();
        realm.beginTransaction();
        realm.where(MainList.class).findAll().deleteAllFromRealm();
        realm.where(Message.class).findAll().deleteAllFromRealm();
        realm.where(GroupListMember.class).findAll().deleteAllFromRealm();
        realm.where(GroupContent.class).findAll().deleteAllFromRealm();
        realm.where(FriendList.class).findAll().deleteAllFromRealm();
        realm.commitTransaction();
        realm.close();

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
            String filepath = Utils.saveMyBitmap(b, new Random().nextInt() + "");
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

            doUploadImage(images);
        }
    }

    private void doUploadImage(final ArrayList<ImageItem> images) {
        new Thread() {
            @Override
            public void run() {
                ImageItem ii = images.get(0);

                File file = new File(ii.path);
                String ret = UploadUtil.uploadFile(file, url, "icon", "JSESSIONID=" + jsessionid);
                Log.d("test", "upload ret = " + ret);
                if (ret == null) {
                    return;
                }
                if (!ret.contains("200")) {
                    return;
                }
                HashMap map = new HashMap();
                try {
                    map.put("path", new JSONObject(ret).getJSONObject("data").getString("url"));//"file://" + images.get(0).path
                    pickerCallback.invoke(map);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @JSMethod(uiThread = true)
    public void WeChatPay(String param, JSCallback callback) {
        Log.d("test", "param = " + param);
        //{"total":0.06,"remark":"智慧课堂","attach":{"childId":"ffc7337009f211e7b048299dbf864a63","classId":"bc6f1550093611e7bc6713374ff06eb4","schoolId":"344da9f107cb11e7bbb321aab2798d9f","payUserId":"43f8d7f00d3c11e7a588051dfb74031a"},"outTradeNo":"20170328173719695","url":"file:///mnt/sdcard/kiway/weex/parenttab0.zip/yjpt/weex_jzd/catalog-list.js"}

        BaseHttpRequest.JSESSIONID = mWXSDKInstance.getContext().getSharedPreferences("kiway", 0).getString("jsessionid", "");
        msgApi = WXAPIFactory.createWXAPI(mWXSDKInstance.getContext(), null);
        req = new PayReq();
        msgApi.registerApp(Constants.APP_ID);
        Activity a = ((Activity) mWXSDKInstance.getContext());
        if (!msgApi.isWXAppInstalled()) {
            Toast.makeText(a, "没有安装微信", Toast.LENGTH_SHORT).show();
            return;
        }
        if (msgApi.getWXAppSupportAPI() < Build.PAY_SUPPORTED_SDK_INT) {
            Toast.makeText(a, "微信版本过低，不支持支付", Toast.LENGTH_SHORT).show();
            return;
        }
        JSONObject data = null;
        try {
            data = new JSONObject(param);
            Map<String, String> map = new HashMap<String, String>();
            map.put("attach", data.getString("attach"));
            map.put("total", data.getString("total"));
            map.put("outTradeNo", data.getString("outTradeNo"));
            map.put("remark", data.getString("remark"));
            IConstant.HTTP_CONNECT_POOL.addRequest(IUrContant.GET_WEI_PRIDUCT_URL, map, activityHandler, true, 1);

            WXApplication.callback = callback;
            // 测试用
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void httpErr(HttpResponseModel message) throws Exception {
        toast("请求失败，请稍后再试");
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
            req.sign = da.optString("signSecond");
            msgApi.sendReq(req);
        }
    }

    @Override
    public void HttpError(HttpResponseModel message) throws Exception {
        toast("请求失败，请稍后再试");
    }

    @JSMethod(uiThread = true)
    public void backToMain() {
        Log.d("test", "backToMain is called");
        mWXSDKInstance.getContext().startActivity(new Intent(mWXSDKInstance.getContext(), MainActivity2.class));
        ScreenManager.getScreenManager().popAllActivityExceptOne(MainActivity2.class);
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


    @JSMethod(uiThread = true)
    public void modifyClass() {
        Log.d("test", "modifyClass is called");
        PushInterface pushInterface = MqttInstance.getInstance().getPushInterface();
        if (pushInterface != null) {
            //2.grouplist
            getGroupInfo(pushInterface.getGroupList());
        }
    }

    private void getGroupInfo(String list) {
        Log.d("mqtt", "groupList = " + list);
        MainListDao.saveGroupList(list, DaoType.SESSTIONTYPE.GROUP);
    }
}

