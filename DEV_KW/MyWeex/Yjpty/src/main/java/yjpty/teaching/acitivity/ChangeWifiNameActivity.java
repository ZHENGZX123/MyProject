package yjpty.teaching.acitivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.zk.myweex.R;

import yjpty.teaching.dialog.LoginDialog;
import yjpty.teaching.util.AppUtil;
import yjpty.teaching.util.IConstant;
import yjpty.teaching.util.ViewUtil;
import yjpty.teaching.wifi.WifiAdmin;


/**
 * Created by Administrator on 2017/1/17.
 */

public class ChangeWifiNameActivity extends BaseActivity {
    private WifiManager wm;
    private WifiReceiver wifiReceiver;
    private IntentFilter intentFilter;
    private LoginDialog dialog;
    private WifiAdmin admin;
    String wifiName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wifiName = bundle.getString(IConstant.BUNDLE_PARAMS);
        admin=new WifiAdmin(this);
        setContentView(R.layout.yjpty_activity_send_wifiname);
        try {
            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        super.onClick(v);
        int i = v.getId();
        if (i == R.id.login) {
            if (ViewUtil
                    .getContent(this, R.id.wifiPs).isEmpty()) {
                ViewUtil.showMessage(this, R.string.password);
                return;
            }
            if (ViewUtil.getContent(this, R.id.wifiName).isEmpty()
                    || ViewUtil.getContent(this, R.id.wifiName).equals("")) {
                ViewUtil.showMessage(this, R.string.network_no_k);
                return;
            }
            IConstant.executorService.execute(new Runnable() {
                public void run() {
                    //PushClient.create();
                    admin.addNetwork(admin.CreateWifiInfo(ViewUtil
                            .getContent(ChangeWifiNameActivity.this,
                                    R.id.wifiName), ViewUtil.getContent(
                            ChangeWifiNameActivity.this, R.id.wifiPs), AppUtil.getCipherType
                            (ChangeWifiNameActivity.this, wifiName)));
                }
            });
            if (dialog != null) {
                dialog.setTitle(resources.getString(R.string.run_yz_ps));
                dialog.show();
            }

        }
    }

    @Override
    public void initView() throws Exception {
        super.initView();
        findViewById(R.id.type).setVisibility(View.GONE);
        findViewById(R.id.no_send).setVisibility(View.GONE);
        findViewById(R.id.check).setVisibility(View.GONE);
        wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        findViewById(R.id.login).setOnClickListener(this);
        dialog = new LoginDialog(this);
        ViewUtil.setContent(ChangeWifiNameActivity.this, R.id.wifiName,
                wifiName);
        wifiReceiver = new WifiReceiver();
        intentFilter = new IntentFilter(
                WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        intentFilter
                .addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
        registerReceiver(wifiReceiver, intentFilter);
    }

    class WifiReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(action)) {
            } else if (WifiManager.SUPPLICANT_STATE_CHANGED_ACTION
                    .equals(action)) {
                WifiInfo info = wm.getConnectionInfo();
                SupplicantState state = info.getSupplicantState();
                String str = null;
                if (state == SupplicantState.ASSOCIATED) {
                    str = "关联AP完成";
                    Log.e("Wifi:::", str);
                } else if (state.toString().equals("AUTHENTICATING")) {
                    str = "正在验证";
                    Log.e("Wifi:::", str);
                } else if (state == SupplicantState.ASSOCIATING) {
                    str = "正在关联AP...";
                    Log.e("Wifi:::", str);
                } else if (state == SupplicantState.COMPLETED) {
                    str = "连接成功";
                    Log.e("Wifi:::", str);
                    if (AppUtil.getConnectWifiSsid(
                            ChangeWifiNameActivity.this).equals(wifiName)) {
                       /* PushClient.create();
                        if (PushClient.isOpen())
                            PushClient.close();
                        PushClient.start();*/
                        Bundle bundle = new Bundle();
                        bundle.putBoolean(IConstant.BUNDLE_PARAMS, true);// 1上课 2 看备课
                        startActivity(TeachingPlansActivity.class, bundle);
                    }
                } else if (state == SupplicantState.DISCONNECTED) {
                    str = "已断开";
                    Log.e("Wifi:::", str);
                } else if (state == SupplicantState.DORMANT) {
                    str = "暂停活动";
                    Log.e("Wifi:::", str);
                } else if (state == SupplicantState.FOUR_WAY_HANDSHAKE) {
                    str = "四路握手中...";
                } else if (state == SupplicantState.GROUP_HANDSHAKE) {
                    str = "GROUP_HANDSHAKE";
                    Log.e("Wifi:::", str);
                } else if (state == SupplicantState.INACTIVE) {
                    str = "休眠中...";
                    Log.e("Wifi:::", str);
                } else if (state == SupplicantState.INVALID) {
                    str = "无效";
                    Log.e("Wifi:::", str);
                } else if (state == SupplicantState.SCANNING) {
                    str = "";
                    Log.e("Wifi:::", str);
                } else if (state == SupplicantState.UNINITIALIZED) {
                    str = "未初始化";
                    Log.e("Wifi:::", str);
                }
                final int errorCode = intent.getIntExtra(// 验证失败
                        WifiManager.EXTRA_SUPPLICANT_ERROR, -1);
                if (errorCode == WifiManager.ERROR_AUTHENTICATING) {
                    Log.e("Wifi:::", "验证失败");
                    dialog.close();
                    ViewUtil.showMessage(ChangeWifiNameActivity.this, R.string.ps_error);
                }
            }
        }
    }

    // 暂停监听
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(wifiReceiver);
    }

    // 重启监听
    @Override
    protected void onResume() {
        if (wifiReceiver != null && intentFilter != null)
            IConstant.executorService.execute(new Runnable() {
                public void run() {
                    registerReceiver(wifiReceiver, intentFilter);
                }
            });
        super.onResume();
    }
}

