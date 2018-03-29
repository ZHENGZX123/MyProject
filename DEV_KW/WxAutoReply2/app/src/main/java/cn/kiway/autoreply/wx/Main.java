package cn.kiway.autoreply.wx;

import android.content.ContentValues;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import cn.kiway.autoreply.activity.MainActivity;
import cn.kiway.autoreply.entity.Action;
import cn.kiway.autoreply.service.AutoReplyService;
import cn.kiway.autoreply.util.WxUtils;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import static android.R.attr.action;
import static android.R.attr.id;
import static cn.kiway.autoreply.entity.Action.TYPE_TXT;
import static cn.kiway.autoreply.wx.VersionParam.WECHAT_PACKAGE_NAME;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class Main implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
        Log.e("zzx---",lpparam.packageName);
        if (lpparam.packageName.equals(WECHAT_PACKAGE_NAME)) {
            findAndHookMethod("com.tencent.wcdb.database.SQLiteDatabase", lpparam.classLoader, "insert", String
                    .class, String.class, ContentValues.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    ContentValues contentValues = (ContentValues) param.args[2];
                    String tableName = (String) param.args[0];
                    Log.e("zzx", contentValues.toString());
                    if (TextUtils.isEmpty(tableName) || !tableName.equals("message")) {
                        return;
                    }
                    handleMsg(contentValues, lpparam);
                }
            });
            new HideModule().hide(lpparam);
        } else if (lpparam.packageName.equals("cn.kiway.autoreply")) {
            findAndHookMethod("cn.kiway.autoreply.service.AutoReplyService", lpparam.classLoader, "onCreate", new
                    XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            object = param.thisObject;
                            if (param.thisObject != null) {
                                XposedHelpers.callMethod(param.thisObject, "sendWxMsg", WxUtils.wxRoomId1, "测试发送：：", 1);
                                XposedHelpers.callMethod(param.thisObject, "sendMsgToServer", id, action);
                            }
                        }
                    });
        }
    }

    Object object;


    private void handleMsg(ContentValues contentValues, LoadPackageParam lpparam) throws
            XmlPullParserException, IOException, JSONException {
        int status = contentValues.getAsInteger("status");//不知道是啥
        String talker = contentValues.getAsString("talker");//xxx@chatroom 群聊
        int isSend = contentValues.getAsInteger("isSend");//1是否为自己发送
        if (status == 4 || talker.endsWith("@chatroom") || isSend == 1) {//如果是群聊，自己发送的则过滤
            return;
        }
        int type = contentValues.getAsInteger("type");
        if (type == 1) {//文字
            Log.e("zzx", "文字");
            String content = contentValues.getAsString("content");
            long id = System.currentTimeMillis();
            Action action = new Action();
            action.sender = WxUtils.getWxNickName(talker);
            action.content = content;
            action.talker = talker;
            action.receiveType = TYPE_TXT;
            if (MainActivity.instance != null) {
                int recvCount = MainActivity.instance.getSharedPreferences("kiway", 0).getInt("recvCount", 0) + 1;
                MainActivity.instance.getSharedPreferences("kiway", 0).edit().putInt("recvCount", recvCount).apply();
                MainActivity.instance.updateServiceCount();
            }
            if (object != null) {
                XposedHelpers.callMethod(object, "sendWxMsg", WxUtils.wxRoomId1, content, 1);
                XposedHelpers.callMethod(object, "sendMsgToServer", id, action);
            }
        } else if (type == 2) {//图片
            //做转发
            String msgId = contentValues.getAsString("msgId");
            AutoReplyService.instance.ForwardMx(2, msgId);
        }
    }
}
