package com.zk.myweex.extend.module;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.loader.GlideImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.zk.myweex.activity.LoginActivity;
import com.zk.myweex.activity.MainActivity2;
import com.zk.myweex.utils.ScreenManager;
import com.zk.myweex.utils.UploadUtil;
import com.zk.myweex.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URLEncoder;
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

import static cn.kwim.mqttcilent.common.cache.dao.Dao.getRealm;


public class SJEventModule extends WXModule {

    @JSMethod(uiThread = true)
    public void sendEvent(JSCallback callback) {
        Log.d("test", "module id = " + mWXSDKInstance.getInstanceId());
        Map<String, Object> params = new HashMap<>();
        params.put("test1", "test1");
        params.put("test2", "test2");
        //这个只能调用相同instance的事件。
//        mWXSDKInstance.fireGlobalEventCallback("tab1_event", params);
        //这个只能callback
//        callback.invoke(params);
        mWXSDKInstance.fireSuperGlobalEventCallback("tab1_event", params);
    }

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
            String code = "http://www.yuertong.com/yjpts/?classid=" + classId + "&ref=class&schoolId=" + schoolId + "&classname=" + className;

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
            intent.putExtra("position", Integer.parseInt(param2));
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

