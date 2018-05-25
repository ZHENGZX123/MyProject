package cn.kiway.robot.hook;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static android.content.ContentValues.TAG;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;

/**
 * Created by su on 2017/12/29.
 */

public class Main implements IXposedHookLoadPackage {

    private final String WECHAT_PACKAGE = "com.tencent.mm";
    Context context;

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (lpparam.appInfo == null || (lpparam.appInfo.flags & (ApplicationInfo.FLAG_SYSTEM |
                ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)) != 0) {
            return;
        }

        final String packageName = lpparam.packageName;
        final String processName = lpparam.processName;

        if (WECHAT_PACKAGE.equals(packageName)) {

            try {
                Class<?> ContextClass = findClass("android.content.ContextWrapper", lpparam.classLoader);
                findAndHookMethod(ContextClass, "getApplicationContext", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        Context applicationContext = (Context) param.getResult();
                        Main.this.context=applicationContext;
                        XposedBridge.log("得到上下文");
                    }
                });
            } catch (Throwable t) {
                XposedBridge.log("获取上下文出错"+t);
            }


            findAndHookMethod("com.tencent.wcdb.database.SQLiteDatabase", lpparam.classLoader, "insert", String
                    .class, String.class, ContentValues.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    ContentValues contentValues = (ContentValues) param.args[2];
                    String tableName = (String) param.args[0];
                    Log.e(TAG, contentValues.toString());
                    Log.e(TAG, tableName);
                    if (TextUtils.isEmpty(tableName) || !tableName.equals("message")) {
                        return;
                    }
                    int isSend = contentValues.getAsInteger("isSend");
                    Log.e(TAG, isSend+"");
                    if (isSend==1)
                        return;
                    int status = contentValues.getAsInteger("status");
                    Log.e(TAG, status+"");
                    if (status == 4) {
                        return;
                    }
                    Integer type = contentValues.getAsInteger("type");
                    Log.e(TAG, type+"");
                    if (null == type) {
                        return;
                    }
                    String content = contentValues.getAsString("content");
                    long createTime=contentValues.getAsLong("createTime");
                    String talker=contentValues.getAsString("talker");
                    int talkerType=0;
                    if (talker.endsWith("@chatroom")){
                        talkerType=1;
                    }else {
                        talkerType=0;
                    }
                    Log.e(TAG,content);
                    Log.e(TAG,talker);
                    Log.e(TAG,createTime+"");
                    Log.e(TAG,talkerType+"");
                    Log.e(TAG,isSend+"");
                    Log.e(TAG,context+"");
                    Intent intent=new Intent("cn.kiway.robot.receiver");
                    intent.putExtra("content",content);
                    intent.putExtra("talker",talker);
                    intent.putExtra("createTime",createTime);
                    intent.putExtra("talkerType",talkerType);
                    intent.putExtra("isSend",isSend);
                    context.sendBroadcast(intent);

//                    new MyDBHelper(context).addMessage(content,talker,createTime,talkerType,isSend);
//                    new MyDBHelper(context).getMessages();
                }
            });

            if (WECHAT_PACKAGE.equals(processName)) {
                // 只HOOK UI进程
                try {
                    findAndHookMethod(ContextWrapper.class,
                            "attachBaseContext",
                            Context.class, new XC_MethodHook() {
                                @Override
                                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                    super.afterHookedMethod(param);
                                    Context context = (Context) param.args[0];

                                    ClassLoader appClassLoader = context.getClassLoader();
                                    handleHook(appClassLoader,
                                            getVersionName(context, WECHAT_PACKAGE));
                                }
                            });
                } catch (Throwable e) {
                    XposedBridge.log(e);
                }
            }
        }
    }

    private void handleHook(ClassLoader classLoader, String versionName) {
        new TencentLocationManagerHook(versionName).hook(classLoader);
    }

    private String getVersionName(Context context, String pkgName) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(pkgName, 0);
            return packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
