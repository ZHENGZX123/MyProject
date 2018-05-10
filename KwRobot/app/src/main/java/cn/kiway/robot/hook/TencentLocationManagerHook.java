package cn.kiway.robot.hook;


import android.util.Log;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/**
 * Created by su on 2017/8/30.
 * 腾讯定位 hook
 */

public class TencentLocationManagerHook {
    private XSharedPreferences xsp;

    private boolean fakeLocation;
    private String latitude;
    private String longitude;

    private String methodName;

    public static final String DEFAULT_PREFERENCES_NAME = "location";
    public static final String XSPlatitude = "latitude";
    public static final String XSPlongitude = "longitude";

    public TencentLocationManagerHook(String versionName) {

        //6.6.1 6.6.0 通过
        if (versionName.startsWith("6.6")) {        // 6.6.x
            methodName = "a";
        } else if (versionName.startsWith("6.5")) { // 6.5.x
            methodName = "b";
        }
    }

    public void hook(ClassLoader classLoader) {
        try {
            Class managerClazz = XposedHelpers.findClass("com.tencent.map.geolocation.TencentLocationManager",
                    classLoader);
            XposedBridge.hookAllMethods(managerClazz, "requestLocationUpdates", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    Object tencentLocationListener = param.args[1];
                    XposedBridge.hookAllMethods(tencentLocationListener.getClass(), methodName, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            reload();
                            param.args[1] = Double.valueOf(latitude);
                            param.args[2] = Double.valueOf(longitude);
                            super.beforeHookedMethod(param);
                        }
                    });
                    super.beforeHookedMethod(param);
                }
            });
        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }


    private void reload() {

        xsp = new XSharedPreferences("cn.kiway.robot");
       // xsp.reload();
        //fakeLocation = xsp.getBoolean("fake_location", true);
        latitude = xsp.getString(XSPlatitude, "39.908860");
        longitude = xsp.getString(XSPlongitude, "116.397390");

        Log.e("----------",latitude);
        Log.e("----------",longitude);
    }
}
