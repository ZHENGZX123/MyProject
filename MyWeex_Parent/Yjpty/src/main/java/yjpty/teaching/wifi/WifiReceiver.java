package yjpty.teaching.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Parcelable;

import yjpty.teaching.App;
import yjpty.teaching.acitivity.OnSessionActivity;
import yjpty.teaching.acitivity.TeachingPlansActivity;
import yjpty.teaching.adpater.TeacherTableAdapter;
import yjpty.teaching.tcpudp.HandlerClient;
import yjpty.teaching.util.AppUtil;
import yjpty.teaching.util.IConstant;
import yjpty.teaching.util.SharedPreferencesUtil;

public class WifiReceiver extends BroadcastReceiver {
    App app;
    String wifiName;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (app == null)
            app = (App) context.getApplicationContext();
        if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {// wifi连接上与否
            Parcelable parcelableExtra = intent
                    .getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (null != parcelableExtra) {
                NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                State state = networkInfo.getState();
                boolean isConnected = state == State.CONNECTED;
                if (isConnected && !state.equals(State.CONNECTING)
                        && !state.equals(State.DISCONNECTING)
                        && !state.equals(State.DISCONNECTED)) {
                    if (app.classModels == null || app.classModels.size() <= 0)
                        return;
                    if (app.getChangeWifi()) {
                        wifiName = SharedPreferencesUtil.getString(App
                                        .getInstance().getApplicationContext(),
                                IConstant.WIFI_NEME + app.classModels.get(app.getClassPosition()).getId());
                        if (AppUtil.getConnectWifiSsid(
                                App.getInstance().getApplicationContext()).equals(
                                wifiName)) {
                            app.client = new HandlerClient();
                            app.client.connectTCP(app.getSessionIp());
                            Bundle bundle = new Bundle();
                            bundle.putBoolean(IConstant.BUNDLE_PARAMS, true);// 1上课 2 看备课
                            Intent intent2 = new Intent(context,
                                    TeachingPlansActivity.class);
                            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent2.putExtras(bundle);
                            context.startActivity(intent2);
                        }
                    } else {
                        wifiName = SharedPreferencesUtil.getString(App
                                        .getInstance().getApplicationContext(),
                                IConstant.WIFI_NEME + app.classModels.get(app.getClassPosition()).getId());
                        if (AppUtil.getConnectWifiSsid(
                                App.getInstance().getApplicationContext()).equals(
                                wifiName)
                                && SharedPreferencesUtil.getBoolean(App
                                        .getInstance().getApplicationContext(),
                                IConstant.IS_ON_CLASS)) {// 连接上wifi
                            Bundle bundle = new Bundle();
                            bundle.putBoolean(IConstant.BUNDLE_PARAMS1, true);
                            bundle.putSerializable(IConstant.BUNDLE_PARAMS, app.getVideoModel());
                            Intent intent2 = new Intent(context,
                                    OnSessionActivity.class);
                            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent2.putExtras(bundle);
                            context.startActivity(intent2);
                            if (TeacherTableAdapter.dialog != null)
                                TeacherTableAdapter.dialog.close();
                            SharedPreferencesUtil.save(App.getInstance()
                                            .getApplicationContext(),
                                    IConstant.IS_ON_CLASS, false);
                        } else if (AppUtil.getConnectWifiSsid(
                                App.getInstance().getApplicationContext()).equals(
                                app.getNowWifi())) {
                            if (OnSessionActivity.onSessionActivity != null) {
                                OnSessionActivity.onSessionActivity.finish();
                                SharedPreferencesUtil.save(App.getInstance()
                                                .getApplicationContext(),
                                        IConstant.IS_ON_CLASS, false);
                            }
                        }
                    }
                }
            }
        }
    }
}
