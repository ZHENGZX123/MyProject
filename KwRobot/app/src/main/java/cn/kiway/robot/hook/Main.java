package cn.kiway.robot.hook;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import cn.kiway.robot.BuildConfig;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by su on 2017/12/29.
 */

public class Main implements IXposedHookLoadPackage {

    private final String WECHAT_PACKAGE = "com.tencent.mm";

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        if (lpparam.appInfo == null || (lpparam.appInfo.flags & (ApplicationInfo.FLAG_SYSTEM |
                ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)) != 0) {
            return;
        }

        final String packageName = lpparam.packageName;
        final String processName = lpparam.processName;

        if (BuildConfig.APPLICATION_ID.equals(packageName)) {
            XposedHelpers.findAndHookMethod("com.wuxiaosu.wechathelper.activity.MainActivity", lpparam.classLoader,
                    "showModuleActiveInfo", boolean.class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            param.args[0] = true;
                            super.beforeHookedMethod(param);
                        }
                    });
        }

        if (WECHAT_PACKAGE.equals(packageName)) {
            try {
                XposedHelpers.findAndHookMethod(Application.class,
                        "attach",
                        Context.class, new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                Context context = (Context) param.args[0];
                                ClassLoader appClassLoader = context.getClassLoader();
                            }
                        });
            } catch (Throwable e) {
                XposedBridge.log(e);
            }
            if (WECHAT_PACKAGE.equals(processName)) {
                // 只HOOK UI进程
                try {
                    // 参见 ：https://github.com/Gh0u1L5/WechatMagician/blob/master/src/main/java/com/gh0u1l5/wechatmagician/backend/WechatHook.kt
                    XposedHelpers.findAndHookMethod(ContextWrapper.class,
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