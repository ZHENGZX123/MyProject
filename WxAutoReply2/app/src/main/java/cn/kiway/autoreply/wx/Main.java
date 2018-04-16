package cn.kiway.autoreply.wx;

import android.content.ContentValues;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import cn.kiway.autoreply.util.PreferencesUtils;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import static cn.kiway.autoreply.wx.VersionParam.WECHAT_PACKAGE_NAME;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class Main implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
        Log.e("zzx---", lpparam.packageName);
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
        }
    }

    private void handleMsg(ContentValues contentValues, LoadPackageParam lpparam) throws
            XmlPullParserException, IOException, JSONException {
        int status = contentValues.getAsInteger("status");//不知道是啥
        String talker = contentValues.getAsString("talker");//xxx@chatroom 群聊
        int isSend = contentValues.getAsInteger("isSend");//1是否为自己发送
        String content = contentValues.getAsString("content");//消息内容
        int type = contentValues.getAsInteger("type");//1文字 2图片 3语音 4视频
        String item1 = "";//个人消息转发到后台
        String item2 = "";//某群内做转发
        String item3 = "";//在item2群内的消息(来自item3发的)转到指定人下（消息格式：目标人:消息内容）
        String item4 = "";//在item4群的消息转发到朋友圈
        if (!PreferencesUtils.getPeopleMsgToService().toString().equals("{}")) {
            item1 = PreferencesUtils.getPeopleMsgToService().getString("wxid");
            Log.e("PreferencesUtils", PreferencesUtils.getPeopleMsgToService().toString());
        }
        if (!PreferencesUtils.getZFRoom().toString().equals("{}")) {
            item2 = PreferencesUtils.getZFRoom().getString("wxid");
            Log.e("PreferencesUtils", PreferencesUtils.getZFRoom().toString());
        }
        if (!PreferencesUtils.getZFRoomPeople().toString().equals("{}")) {
            item3 = PreferencesUtils.getZFRoomPeople().getString("wxid");
            Log.e("PreferencesUtils", PreferencesUtils.getZFRoomPeople().toString());
        }
        if (!PreferencesUtils.getZFFriendsPeople().toString().equals("{}")) {
            item4 = PreferencesUtils.getZFFriendsPeople().getString("wxid");
            Log.e("PreferencesUtils", PreferencesUtils.getZFFriendsPeople().toString());
        }
        if (status == 4)
            return;
        if (talker.endsWith("@chatroom")) {
            Log.e("zzx", "这是群消息");
            if (talker.equals(item4)) {//转发到朋友圈
                //TODO: 2018/4/2
                Log.e("zzx", "这是要转发到朋友圈的群");
            } else if (talker.equals(item2)) {//群内消息做转发
                String roomTalker = content.split(":")[0];//群内发消息的人的wxid
                if (roomTalker.equals(item3)) { //从后台来的消息才做转发
                    // TODO: 2018/4/2
                    Log.e("zzx", "目标群：转发到目标人" + content);//content格式：接收人：消息内容
                } else {
                    //TODO: 2018/4/2
                    Log.e("zzx", "目标群：未做转发");
                }
            } else {
                // TODO: 2018/4/2
                Log.e("zzx", "既不是转发群也不是转发朋友圈群消息的其他处理");
            }
        } else {
            // TODO: 2018/4/2
            Log.e("zzx", "这是个人消息我要转发到后台");
        }
    }
}
