package cn.kiway.mdm.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Administrator on 2017/10/27.
 */

public class AppReceiverIn extends BroadcastReceiver {
    public static final String TAG = "cehsi";

    public static final String INSTALL_SUCCESS = "INSTALL_SUCCESS";//安装成功
    public static final String REMOVE_SUCCESS = "REMOVE_SUCCESS";//移除成功
    public static final String REPLACE_SUCCESS = "REPLACE_SUCCESS";//替换成功
    public static final String PACKAGENAME = "PACKAGENAME";//包名

    @Override
    public void onReceive(Context context, Intent intent) {
        String packageName = intent.getData().getSchemeSpecificPart();
        Intent intent1 = new Intent();
        intent1.putExtra(PACKAGENAME, packageName);
        if (TextUtils.equals(intent.getAction(), Intent.ACTION_PACKAGE_ADDED)) {//监听应用安装成功
            intent1.setAction(INSTALL_SUCCESS);
            Log.e(TAG, "--------安装成功" + packageName);
        } else if (TextUtils.equals(intent.getAction(), Intent.ACTION_PACKAGE_REPLACED)) {
            intent1.setAction(REPLACE_SUCCESS);
            Log.e(TAG, "--------替换成功" + packageName);
        } else if (TextUtils.equals(intent.getAction(), Intent.ACTION_PACKAGE_REMOVED)) {
            intent1.setAction(REMOVE_SUCCESS);
            Log.e(TAG, "--------卸载成功" + packageName);
        }
        context.sendOrderedBroadcast(intent1, null);
    }

}